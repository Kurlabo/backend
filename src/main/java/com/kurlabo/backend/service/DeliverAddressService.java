package com.kurlabo.backend.service;

import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.model.Deliver_Address;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.repository.DeliverAddressRepository;
import com.kurlabo.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliverAddressService {

    private final DeliverAddressRepository deliverAddressRepository;
    private final MemberRepository memberRepository;

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

    // 기본 배송지, 선택 배송지 체크
    public void checkIsMain (Deliver_Address deliverAddress) {
        List<Deliver_Address> isMainChk = deliverAddressRepository.findByMember(deliverAddress.getMember());
        for (Deliver_Address mainChk : isMainChk) {
            if (mainChk.getIs_main() == 1) {
                mainChk.setIs_main(deliverAddress.updateIsMain());
            }

            if (mainChk.getChecked() == 1) {
                mainChk.setChecked(deliverAddress.updateChecked());
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
        newAddress.setChecked(1);
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

        Deliver_Address address = deliverAddressRepository.findById(deliverAddress.getId()).orElseThrow(
                () -> new DataNotFoundException("Address is not existed.")
        );

        // 해당 멤버가 가진 다른 주소
        List<Deliver_Address> daList = deliverAddressRepository.findByMember(member);

        for (Deliver_Address da : daList) {
            if (deliverAddress.getIs_main() == 1) { // 기본 배송지로 지정
                if (da.getId().equals(deliverAddress.getId())) {
                    address.setIs_main(deliverAddress.getIs_main());
                } else {
                    da.setIs_main(0);
                }
            }
        } // end for

        address.setChecked(deliverAddress.getChecked());
        address.setReciever(deliverAddress.getReciever());
        address.setReciever_phone(deliverAddress.getReciever_phone());
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

    @Transactional
    public void updateChkAddress(Long id, Deliver_Address deliverAddress) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Member is not existed.")
        );

        Deliver_Address address = deliverAddressRepository.findById(deliverAddress.getId()).orElseThrow(
                () -> new DataNotFoundException("Address is not existed.")
        );

        List<Deliver_Address> daList = deliverAddressRepository.findByMember(member);

        for (Deliver_Address da : daList) {
            if (deliverAddress.getChecked() == 1 ) { // 배송지 선택 (리스트 가장 위로 고정)
                if (da.getId().equals(deliverAddress.getId())) {
                    address.setChecked(deliverAddress.getChecked());
                } else {
                    da.setChecked(0);
                }
            }
        } // end for

        // return address;

    }




}