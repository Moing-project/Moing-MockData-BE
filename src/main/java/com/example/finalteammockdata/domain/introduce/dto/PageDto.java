package com.example.finalteammockdata.domain.introduce.dto;

import lombok.Getter;

@Getter
public class PageDto<T> {
    private boolean hasNextPage;
    private T data;

    public PageDto(boolean hasNextPage, T data){
        this.hasNextPage=hasNextPage;
        this.data=data;
    }
}
