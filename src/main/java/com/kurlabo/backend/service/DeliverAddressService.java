package com.kurlabo.backend.service;

import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Deliver_Address;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.repository.DeliverAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliverAddressService {

    private final DeliverAddressRepository deliverAddressRepository;

    @Transactional
    public Deliver_Address setDeliverAddress(Member member, String address){
        if(member == null){
            return null;
        }

        Deliver_Address da = new Deliver_Address(
                null,
                address,
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

    public List<Deliver_Address> getAllAddress (Member member){
        if (deliverAddressRepository.findByMember(member).isEmpty()) {
            return null;
        }
        return deliverAddressRepository.findByMember(member);
    }

    @Transactional
    public void creatAddress(Deliver_Address deliverAddress) {
        Deliver_Address newAddress = new Deliver_Address();

        checkIsMain(deliverAddress);

        newAddress.setIs_main(1);
        newAddress.setDeliver_address(deliverAddress.getDeliver_address());
        newAddress.setReciever(deliverAddress.getReciever());
        newAddress.setReciever_phone(deliverAddress.getReciever_phone());
        newAddress.setMember(deliverAddress.getMember());

        deliverAddressRepository.save(newAddress);
    }

    @Transactional
    public Deliver_Address updateDeliverAddress (Deliver_Address deliverAddress){
        Deliver_Address address = deliverAddressRepository.findById(deliverAddress.getId())
                .orElseThrow(ResourceNotFoundException::new);

        // 기본 배송지로 변경
        if (deliverAddress.getIs_main() == 1) {
            checkIsMain(deliverAddress);
            address.setIs_main(1);
        }

        // 기본 배송지가 없다면
        if (address.getIs_main() == 0) {
            System.out.println("기본 배송지 없음");
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

        return address;
    }

    @Transactional
    public String deleteDeliverAddress(Deliver_Address deliverAddress) {
        List<Deliver_Address> daList = deliverAddressRepository.findByMember(deliverAddress.getMember());
        String msg = "";

        if (daList.size() <= 1) {
            msg = "삭제할 수 없습니다";
        } else {
            deliverAddressRepository.delete(deliverAddress);
            msg = "삭제되었습니다.";
        }
        return msg;
    }

}
