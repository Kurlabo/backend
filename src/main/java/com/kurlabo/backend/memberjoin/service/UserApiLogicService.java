package com.kurlabo.backend.memberjoin.service;

import com.kurlabo.backend.memberjoin.controller.CrudInterface;
import com.kurlabo.backend.memberjoin.model.entity.User;
import com.kurlabo.backend.memberjoin.model.network.Header;
import com.kurlabo.backend.memberjoin.model.network.request.UserApiRequest;
import com.kurlabo.backend.memberjoin.model.network.response.UserApiResponse;
import com.kurlabo.backend.memberjoin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserApiLogicService implements CrudInterface<UserApiRequest, UserApiResponse> {

    @Autowired
    private UserRepository userRepository;

    // 1. request data
    // 2. user 생성
    // 3. 생성된 데이터 -> UserApiResponse return
    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {

        UserApiRequest userApiRequest = request.getData();

        User user = User.builder()
                .uid(userApiRequest.getUid())
                .password(userApiRequest.getPassword())
                .name(userApiRequest.getName())
                .email(userApiRequest.getEmail())
                .phoneNumber(userApiRequest.getPhoneNumber())
                .address(userApiRequest.getAddress())
                .gender(userApiRequest.getGender())
                .dateOfBirth(userApiRequest.getDateOfBirth())
                .grade(userApiRequest.getGrade())
                .registeredAt(LocalDateTime.now())
                .build();

        User newUser = userRepository.save(user);

        return response(newUser);
    }

    @Override
    public Header<UserApiResponse> read(Long id) {
        
        return userRepository.findById(id)
                .map(user -> response(user))
                .orElseGet(
                        ()-> Header.ERROR("데이터 없음")
               );
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {
        return null;
    }

    @Override
    public Header delete(Long id) {
        return null;
    }

    private Header<UserApiResponse> response(User user) {
        // user -> userApiResponse return method

        UserApiResponse userApiResponse = UserApiResponse.builder()
                .uid(user.getUid())
                .password(user.getPassword()) // todo 암호화, 길
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .gender(user.getGender())
                .dateOfBirth(user.getDateOfBirth())
                .grade(user.getGrade())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .build();

        return Header.OK(userApiResponse);
    }
}
