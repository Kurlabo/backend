package com.kurlabo.backend.memberjoin.repository;

import com.kurlabo.backend.BackendApplicationTests;
//import com.kurlabo.backend.memberjoin.model.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;


public class UserRepositoryTest extends BackendApplicationTests {

    // todo Make the Test works
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    public void create() {
//        User user = new User();
//        user.setUid("TestUser01");
//        user.setPassword("1234");
//        user.setName("테스터");
//        user.setEmail("tester@test.com");
//        user.setPhoneNumber("010-1111-1111");
//        user.setGender("남자");
//        user.setDateOfBirth("1990.01.01");
//        user.setGrade("신규");
//        user.setRegisteredAt(LocalDateTime.now());
//        user.setUnregisteredAt(LocalDateTime.now());
//        user.setCreatedAt(LocalDateTime.now());
//        user.setCreatedBy("admin");
//        user.setUpdatedAt(LocalDateTime.now());
//        user.setUpdatedBy("admin1");
//
//        User newUser = userRepository.save(user);
//        System.out.println("newUser : "+newUser);
//    }
//
//    @Test

//    public void read() {
//        Optional<User> user = userRepository.findById(2L);
//
//        user.ifPresent(selectUser -> {
//            System.out.println("user : " +selectUser);
//        });
//
//    }
//
//    @Test
//    @Transactional
//    public void update() {
//
//        Optional<User> user = userRepository.findById(2L);
//
//        user.ifPresent(selectUser -> {
//            selectUser.setUid("TestUser01");
//            selectUser.setUpdatedAt(LocalDateTime.now());
//            selectUser.setUpdatedBy("update method()");
//
//            userRepository.save(selectUser);
//        });
//    }
//
//    @Test
//    @Transactional
//    public void delete() {
//        Optional<User> user = userRepository.findById(2L);
//
//        Assertions.assertThat(user.isPresent()).isTrue();
//
//        user.ifPresent(selectUser-> {
//            userRepository.delete(selectUser);
//        });
//
//        Optional<User> deleteUser = userRepository.findById(2L);
//
//        Assertions.assertThat(deleteUser.isPresent()).isFalse();
//
//    }

}
