package com.kurlabo.backend.service;

import com.kurlabo.backend.model.Deliver_Address;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.repository.DeliverAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliverAddressService {

    private final DeliverAddressRepository deliverAddressRepository;

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
}
