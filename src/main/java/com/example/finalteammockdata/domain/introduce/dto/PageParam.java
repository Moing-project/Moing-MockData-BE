package com.example.finalteammockdata.domain.introduce.dto;

public class PageParam {
    private String field = "";

    private Integer page = 1;

    private Integer size = 8;

    public String getField(){
        return field;
    }

    public Integer getPage(){
        if(page == 0){
            return page;
        }
        return this.page-1;
    }
    public Integer getSize(){
        return size;
    }
}
