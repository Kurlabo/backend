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
        List<Long> longList = new ArrayList<>();

        for(int i = 0; i < 16; i++){
            Long n = (long)random.nextInt(321) + 1;
            while (longList.contains(n)){
                n = (long)random.nextInt(321) + 1;
            }
            longList.add(n);

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

        for(int i = 0; i < 16; i++){
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
        List<Integer> intList = new ArrayList<>();
        productsList = getProducts(mainCategory, productsList, productRepository);

        for(int i = 0; i < 16; i++){
            int n = random.nextInt(productsList.size());
            while (intList.contains(n)){
                n = random.nextInt(productsList.size());
            }
            intList.add(n);
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

        for(int i = 0; i < 16; i++){
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

    private List<Product> getProducts(int category, List<Product> productList, ProductRepository productRepository) {
        switch (category){
            case 0: productList = productRepository.findByCategoryVege();           break;
            case 1: productList = productRepository.findByCategoryFruits();         break;
            case 2: productList = productRepository.findByCategorySeafood();        break;
            case 3: productList = productRepository.findByCategoryMeat();           break;
            case 4: productList = productRepository.findByCategoryMaindish();       break;
            case 5: productList = productRepository.findByCategoryFastFood();       break;
            case 6: productList = productRepository.findByCategoryNoodleoil();      break;
            case 7: productList = productRepository.findByCategoryDring();          break;
            case 8: productList = productRepository.findByCategorySnacks();         break;
            case 9: productList = productRepository.findByCategoryBakery();         break;
            case 10: productList = productRepository.findByCategoryHealthFood();    break;
            case 11: productList = productRepository.findByCategoryLiving();        break;
            case 12: productList = productRepository.findByCategoryBeauty();        break;
            case 13: productList = productRepository.findByCategoryKitchen();       break;
            case 14: productList = productRepository.findByCategoryHomeAppliance(); break;
            case 15: productList = productRepository.findByCategoryBabyKiz();       break;
            case 16: productList = productRepository.findByCategoryPet();           break;
        }
        return productList;
    }
}
