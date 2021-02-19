package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.main.InstaSrcDto;
import com.kurlabo.backend.dto.main.MainPageProductDto;
import com.kurlabo.backend.dto.main.MainResponseDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.model.db.Slide_img;
import com.kurlabo.backend.repository.ProductRepository;
import com.kurlabo.backend.repository.db.InstaSrcRepository;
import com.kurlabo.backend.repository.db.MainSrcRepository;
import com.kurlabo.backend.repository.db.SlideImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MainService {

    private final SlideImgRepository slideImgRepository;
    private final InstaSrcRepository instaSrcRepository;
    private final MainSrcRepository mainSrcRepository;
    private final ProductRepository productRepository;

    public MainResponseDto setMainPage() {
        return new MainResponseDto(
                getSlideImgs(),
                getInstaSrc(),
                getSetbyulImg(),
                setHowAbout(),
                setFrugality(),
                setMdRecommend(0),
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

    public List<MainPageProductDto> setHowAbout(){
        Random random = new Random();
        List<MainPageProductDto> list = new ArrayList<>();
        List<Long> longlist = new ArrayList<>();

        for(int i = 0; i < 8; i++){
            Long n = (long)random.nextInt(321) + 1;
            while (longlist.contains(n)){
                n = (long)random.nextInt(321) + 1;
            }
            longlist.add(n);

            Product product = productRepository.findById(n).orElseThrow(ResourceNotFoundException::new);
            list.add(new MainPageProductDto(
                    product.getId(),
                    product.getOriginal_image_url(),
                    product.getSticker_image_url(),
                    product.getName(),
                    product.getOriginal_price(),
                    product.getDiscounted_price(),
                    product.getDiscount_percent()
            ));
        }

        return list;
    }
    public List<MainPageProductDto> setFrugality(){
        Random random = new Random();
        List<MainPageProductDto> list = new ArrayList<>();
        List<Product> productsList = productRepository.findByDiscount_percent();
        List<Integer> intlist = new ArrayList<>();

        for(int i = 0; i < 8; i++){
            int n = random.nextInt(productsList.size());
            while (intlist.contains(n)){
                n = random.nextInt(productsList.size());
            }
            intlist.add(n);
            Product product = productsList.get(n);

            list.add(new MainPageProductDto(
                    product.getId(),
                    product.getOriginal_image_url(),
                    product.getSticker_image_url(),
                    product.getName(),
                    product.getOriginal_price(),
                    product.getDiscounted_price(),
                    product.getDiscount_percent()
            ));
        }

        return list;
    }
    public List<MainPageProductDto> setMdRecommend(int mainCategory){
        Random random = new Random();
        List<MainPageProductDto> list = new ArrayList<>();
        List<Product> productsList = new ArrayList<>();
        List<Integer> intlist = new ArrayList<>();
        switch (mainCategory){
            case 0:
                productsList = productRepository.findByCategoryVege();
                break;
            case 1:
                productsList = productRepository.findByCategoryFruits();
                break;
            case 2:
                productsList = productRepository.findByCategorySeafood();
                break;
            case 3:
                productsList = productRepository.findByCategoryMeat();
                break;
            case 4:
                productsList = productRepository.findByCategoryMaindish();
                break;
            case 5:
                productsList = productRepository.findByCategoryFastFood();
                break;
            case 6:
                productsList = productRepository.findByCategoryNoodleoil();
                break;
            case 7:
                productsList = productRepository.findByCategoryDring();
                break;
            case 8:
                productsList = productRepository.findByCategorySnacks();
                break;
            case 9:
                productsList = productRepository.findByCategoryBakery();
                break;
            case 10:
                productsList = productRepository.findByCategoryHealthFood();
                break;
            case 11:
                productsList = productRepository.findByCategoryLiving();
                break;
            case 12:
                productsList = productRepository.findByCategoryBeauty();
                break;
            case 13:
                productsList = productRepository.findByCategoryKitchen();
                break;
            case 14:
                productsList = productRepository.findByCategoryHomeAppliance();
                break;
            case 15:
                productsList = productRepository.findByCategoryBabyKiz();
                break;
            case 16:
                productsList = productRepository.findByCategoryPet();
                break;
        }

        for(int i = 0; i < 8; i++){
            int n = random.nextInt(productsList.size());
            while (intlist.contains(n)){
                n = random.nextInt(productsList.size());
            }
            intlist.add(n);
            Product product = productsList.get(n);

            list.add(new MainPageProductDto(
                    product.getId(),
                    product.getOriginal_image_url(),
                    product.getSticker_image_url(),
                    product.getName(),
                    product.getOriginal_price(),
                    product.getDiscounted_price(),
                    product.getDiscount_percent()
            ));
        }

        return list;
    }
    public List<MainPageProductDto> setTodays(){
        return setHowAbout();
    }
    public List<MainPageProductDto> setHots(){
        return setFrugality();
    }
    public List<MainPageProductDto> setGoodPrice(){
        return setFrugality();
    }
    public List<MainPageProductDto> setFastFood(){
        Random random = new Random();
        List<Product> productList = productRepository.findByCategoryFastFood();
        List<Integer> intlist = new ArrayList<>();
        List<MainPageProductDto> list = new ArrayList<>();

        for(int i = 0; i < 8; i++){
            int n = random.nextInt(productList.size());
            while (intlist.contains(n)){
                n = random.nextInt(productList.size());
            }
            intlist.add(n);
            Product product = productList.get(n);

            list.add(new MainPageProductDto(
                    product.getId(),
                    product.getOriginal_image_url(),
                    product.getSticker_image_url(),
                    product.getName(),
                    product.getOriginal_price(),
                    product.getDiscounted_price(),
                    product.getDiscount_percent()
            ));
        }

        return list;
    }
}
