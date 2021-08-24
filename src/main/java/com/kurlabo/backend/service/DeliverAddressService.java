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

    public List<DeliverAddressDto> getAllAddress (Long id){
        Member member = memberRepository.findById(id).orElseThrow(() -> new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + id));
        return dynamicDeliverAddressRepository.findByMember(member).isEmpty() ? null : dynamicDeliverAddressRepository.findByMember(member);
    }

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
    public void updateDeliverAddress (Long id, DeliverAddressDto deliverAddressDto){
        Member member = memberRepository.findById(id).orElseThrow(() -> new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + id));

        Deliver_Address deliverAddress = deliverAddressRepository.findById(deliverAddressDto.getId()).orElseThrow(
                () -> new DataNotFoundException("해당 ID의 주소를 찾을 수 없습니다. Id = " + deliverAddressDto.getId())
        );

        if(deliverAddressDto.getIs_main() == 1){
            Deliver_Address curMainAddress = deliverAddressRepository.findByMemberAndIs_main(member, 1).orElseThrow(
                    () -> new DataNotFoundException("현재 메인 주소를 찾을 수 없습니다.")
            );
            curMainAddress.resetIsMain();
            deliverAddressRepository.save(curMainAddress);
        }

        deliverAddress.updateAddress(
                deliverAddressDto.getDeliver_detail_address(),
                deliverAddressDto.getReciever(),
                deliverAddressDto.getReciever_phone(),
                deliverAddressDto.getIs_main()
        );

        deliverAddressRepository.save(deliverAddress);
    }

    @Transactional
    public void deleteDeliverAddress(Long id, Long deliverAddressId) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + id));
        Deliver_Address deleteDeliverAddress = deliverAddressRepository.findByIdAndMember(deliverAddressId, member).orElseThrow(
                () -> new DataNotFoundException("해당 ID의 주소를 찾을 수 없습니다. Id = " + deliverAddressId)
        );
        Deliver_Address mainDeliverAddress = deliverAddressRepository.findByMemberAndIs_main(member, 1).orElseThrow(
                () -> new DataNotFoundException("메인 주소를 찾을 수 없습니다.")
        );
        
        mainDeliverAddress.setChecked(1);

        deliverAddressRepository.save(mainDeliverAddress);
        deliverAddressRepository.delete(deleteDeliverAddress);
    }

    @Transactional
    public void checkAddress(Long id, Long deliverAddressId) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + id));
        Deliver_Address curCheckDeliverAddress = deliverAddressRepository.findByMemberAndChecked(member, 1).orElseThrow(
                () -> new DataNotFoundException("현재 선택된 주소가 없습니다.")
        );
        Deliver_Address checkDeliverAddress = deliverAddressRepository.findByIdAndMember(deliverAddressId, member).orElseThrow(
                () -> new DataNotFoundException("해당 ID의 주소를 찾을 수 없습니다. Id = " + deliverAddressId)
        );

        curCheckDeliverAddress.resetChecked();
        checkDeliverAddress.setChecked(1);

        List<Deliver_Address> updatedList = new ArrayList<>();
        updatedList.add(curCheckDeliverAddress);
        updatedList.add(checkDeliverAddress);

        deliverAddressRepository.saveAll(updatedList);
    }
}