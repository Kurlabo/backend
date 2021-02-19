package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.main.InstaSrcDto;
import com.kurlabo.backend.dto.main.MainPageProductDto;
import com.kurlabo.backend.dto.main.MainResponseDto;
import com.kurlabo.backend.model.db.Slide_img;
import com.kurlabo.backend.repository.db.InstaSrcRepository;
import com.kurlabo.backend.repository.db.MainSrcRepository;
import com.kurlabo.backend.repository.db.SlideImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sun.rmi.rmic.Main;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

    private final SlideImgRepository slideImgRepository;
    private final InstaSrcRepository instaSrcRepository;
    private final MainSrcRepository mainSrcRepository;

    public MainResponseDto setMainPage() {
        return new MainResponseDto(
                getSlideImgs(),
                getInstaSrc(),
                getSetbyulImg(),
                setHowAbout(),
                setFrugality(),
                setMdRecommend(),
                setTodays(),
                setHots(),
                setGoodPrice(),
                setFastFood()
        );
    }

    public List<String> getSlideImgs(){
        List<Slide_img> slideImgLists = slideImgRepository.findAll();
        List<String> imgsList = new ArrayList<>();
        for(Slide_img list : slideImgLists){
            imgsList.add(list.getUrl());
        }
        return imgsList;
    }

    public InstaSrcDto getInstaSrc(){
        return new InstaSrcDto(instaSrcRepository.findAllByLandingUrl(), instaSrcRepository.findAllByThumbnail_img_url());
    }

    public String getSetbyulImg(){
        return mainSrcRepository.findAllBySetbyulImg();
    }

    public MainPageProductDto setHowAbout(){

        return new MainPageProductDto();
    }
    public MainPageProductDto setFrugality(){

        return new MainPageProductDto();
    }
    public MainPageProductDto setMdRecommend(){

        return new MainPageProductDto();
    }
    public MainPageProductDto setTodays(){

        return new MainPageProductDto();
    }
    public MainPageProductDto setHots(){

        return new MainPageProductDto();
    }
    public MainPageProductDto setGoodPrice(){

        return new MainPageProductDto();
    }
    public MainPageProductDto setFastFood(){

        return new MainPageProductDto();
    }
}
