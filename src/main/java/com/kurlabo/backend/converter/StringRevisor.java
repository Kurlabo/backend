package com.kurlabo.backend.converter;

public class StringRevisor {
    public String StringRevise(String origin){
        return origin.replace("\\", "");
    }
}