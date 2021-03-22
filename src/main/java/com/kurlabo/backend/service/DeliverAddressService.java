package com.kurlabo.backend.service;

import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Deliver_Address;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.repository.DeliverAddressRepository;
import com.kurlabo.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliverAddressService {

    private final DeliverAddressRepository deliverAddressRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Deliver_Address setDeliverAddress(Member member, String address, String detail_addr){
        if(member == null){
            return null;
        }

        Deliver_Address da = new Deliver_Address(
                null,
                address,
                detail_addr,
                1,
                "",
                "",
                member
        );

        return deliverAddressRepository.save(da);
    }

    public Deliver_Address selectMainDeliverAddress(Member member){
        List<Deliver_Address> lists = deliverAddressRepository.findByMember(member);
        Deliver_Address da = null;
        for(Deliver_Address list : lists){
            if(list.getIs_main() == 1)
                da = list;
        }
        if(da == null){
            da = new Deliver_Address();
            da.setDeliver_address("등록된 주소가 없습니다");
        }
        return da;
    }

    // 기본 배송지 체크
    public void checkIsMain (Deliver_Address deliverAddress) {
        List<Deliver_Address> isMainChk = deliverAddressRepository.findByMember(deliverAddress.getMember());
        for (Deliver_Address mainChk : isMainChk) {
            if (mainChk.getIs_main() == 1) {
                mainChk.setIs_main(deliverAddress.updateIsMain());
            }
        } // end for
    }

    public List<Deliver_Address> getAllAddress (Long id){
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Member is not existed.")
        );

        if (deliverAddressRepository.findByMember(member).isEmpty()) {
            return null;
        }
        return deliverAddressRepository.findByMember(member);
    }

    @Transactional
    public void creatAddress(Long id, Deliver_Address deliverAddress) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Member is not existed.")
        );

        Deliver_Address newAddress = new Deliver_Address();

        checkIsMain(deliverAddress);

        newAddress.setIs_main(1);
        newAddress.setDeliver_address(deliverAddress.getDeliver_address());
        newAddress.setDeliver_detail_address(deliverAddress.getDeliver_detail_address());
        newAddress.setReciever(deliverAddress.getReciever());
        newAddress.setReciever_phone(deliverAddress.getReciever_phone());
        newAddress.setMember(member);

        deliverAddressRepository.save(newAddress);
    }

    @Transactional
    public Deliver_Address updateDeliverAddress (Long id, Deliver_Address deliverAddress){
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Member is not existed.")
        );

        Deliver_Address address = deliverAddressRepository.findById(deliverAddress.getId())
                            .orElseThrow(ResourceNotFoundException::new);

        List<Deliver_Address> daList = deliverAddressRepository.findByMember(member);
        int cnt = 0;

        if (deliverAddress.getIs_main() == 1) {
            for (Deliver_Address da : daList) {
                if (da.getIs_main() == 1) {
                    checkIsMain(deliverAddress);
                    address.setIs_main(1);
                }
            } // end for
        } else {
            for (Deliver_Address da : daList) {
                if (da.getIs_main() == 0) {
                    cnt ++;
                }
            } // end for

            if (cnt == daList.size()-1) {
                throw new DataNotFoundException("There is no default shipping address.");
            } else {
                address.setIs_main(0);
            }

        }

        if (deliverAddress.getReciever() != null) { // 넘어온 값이 있음
            address.setReciever(deliverAddress.getReciever());
        } else {
            address.setReciever(null);
        }

        if (deliverAddress.getReciever_phone() != null) {
            address.setReciever_phone(deliverAddress.getReciever_phone());
        }else {
            address.setReciever_phone(null);
        }

        address.setDeliver_address(deliverAddress.getDeliver_address());
        address.setDeliver_detail_address(deliverAddress.getDeliver_detail_address());

        return address;
    }

    @Transactional
    public String deleteDeliverAddress(Long id, Deliver_Address deliverAddress) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Member is not existed.")
        );

        List<Deliver_Address> daList = deliverAddressRepository.findByMember(member);
        String msg = "";

        if (daList.size() <= 1) { // 저장된 주소지가 하나 이하면 삭제할 수 없음
            msg = "failed";
        } else {
            deliverAddressRepository.delete(deliverAddress);
            msg = "success";
        }

        return msg;
    }

}