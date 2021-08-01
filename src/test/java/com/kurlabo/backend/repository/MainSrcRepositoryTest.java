package com.kurlabo.backend.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class MainSrcRepositoryTest {

    @Autowired
    private MainSrcRepository mainSrcRepository;

    @DisplayName("메인페이지 샛별배송 이미지 조회")
    @Test
    void findAllBySetbyulImg(){
        String url = mainSrcRepository.findAll().get(0).getSetbyul_img();

        assertThat(url).isEqualTo("https://img-cf.kurly.com/shop/data/main/15/pc_img_1568875999.png");
    }

}