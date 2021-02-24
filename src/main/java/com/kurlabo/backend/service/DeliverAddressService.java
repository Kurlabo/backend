package com.kurlabo.backend.service;

import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.*;
import com.kurlabo.backend.repository.DeliverAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return da;
    }
}
