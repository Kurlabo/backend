package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.mypage.DeliverAddressDto;
import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.model.Deliver_Address;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.repository.DeliverAddressRepository;
import com.kurlabo.backend.repository.MemberRepository;
import com.kurlabo.backend.repository.dynamic.DynamicDeliverAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliverAddressService {

    private final DeliverAddressRepository deliverAddressRepository;
    private final DynamicDeliverAddressRepository dynamicDeliverAddressRepository;
    private final MemberRepository memberRepository;

    // DeliverAddressDto로 바꿔야함
    public List<Deliver_Address> getAllAddress (Long id){
        Member member = memberRepository.findById(id).orElseThrow(() -> new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + id));

        return deliverAddressRepository.findByMember(member).isEmpty() ? null : deliverAddressRepository.findByMember(member);
    }

    // 수정완료 테스트 필요
    @Transactional
    public void creatAddress(Long id, DeliverAddressDto deliverAddressDto) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + id));

        List<Deliver_Address> updateDA = new ArrayList<>();

        if(deliverAddressDto.getIs_main() == 1){
            Deliver_Address curMainAddress = deliverAddressRepository.findByMemberAndIs_main(member, 1).orElse(null);
            if(curMainAddress != null){
                curMainAddress.resetIsMain();
                updateDA.add(curMainAddress);
            }
        }

        Deliver_Address curCheckedAddress = deliverAddressRepository.findByMemberAndChecked(member, 1).orElseThrow(() ->
                new DataNotFoundException("해당 주소가 존재하지 않습니다."));
        curCheckedAddress.resetChecked();
        updateDA.add(curCheckedAddress);

        Deliver_Address newAddress = Deliver_Address.builder()
                .id(null)
                .deliver_address(deliverAddressDto.getDeliver_address())
                .deliver_detail_address(deliverAddressDto.getDeliver_detail_address())
                .is_main(deliverAddressDto.getIs_main())
                .reciever(null)
                .reciever_phone(null)
                .checked(1)
                .member(member)
                .build();
        updateDA.add(newAddress);

        deliverAddressRepository.saveAll(updateDA);
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
        String msg;

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