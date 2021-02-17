package com.kurlabo.backend.service;

import com.kurlabo.backend.model.Cart;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final CartRepository cartRepository;


}
