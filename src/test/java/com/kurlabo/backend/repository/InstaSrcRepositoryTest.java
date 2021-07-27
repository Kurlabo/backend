package com.kurlabo.backend.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class InstaSrcRepositoryTest {

    @Autowired
    private InstaSrcRepository instaSrcRepository;

    @DisplayName("랜딩URL 조회")
    @Test
    void findAllByLandingUrl(){
        List<String> landingList = instaSrcRepository.findAllByLandingUrl();

        assertThat(landingList.get(0)).isEqualTo("https://www.instagram.com/p/CRTd4kIJcLB/");
        assertThat(landingList.get(1)).isEqualTo("https://www.instagram.com/p/CRQhtrjpX17/");
        assertThat(landingList.get(2)).isEqualTo("https://www.instagram.com/p/CRAj6bdpk-G/");
    }

    @DisplayName("썸네일 조회")
    @Test
    void findAllByThumbnail_img_url(){
        List<String> thumbnailList = instaSrcRepository.findAllByThumbnail_img_url();

        assertThat(thumbnailList.get(0)).contains("25aa3253fd2b66f20e325e3e3ac50ec2&oe=60FC6927");
        assertThat(thumbnailList.get(1)).contains("ae05c84c9dec21d692a47ef5889e2e2b&oe=60FD822A");
        assertThat(thumbnailList.get(2)).contains("75c7122773dd560507f17926975081aa&oe=60FD1EC5");
    }

}