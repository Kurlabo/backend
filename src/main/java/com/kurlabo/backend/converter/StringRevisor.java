package com.kurlabo.backend.converter;

public class StringRevisor {
    public String reviseBackSlash(String origin){
        return origin.replace("\\", "");
    }
    public String reviseDoubleQuotes(String origin){
        return origin.replace("\"", "");
    }
}