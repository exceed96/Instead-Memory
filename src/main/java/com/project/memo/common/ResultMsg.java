package com.project.memo.common;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResultMsg<T> {
    private String code;   // success or error
    private String msg;    // when you want to deliever some messages
    private Boolean status; // related to code but boolean
    private List<T> data;  // When data is list
    private T item;        // When data is an object

    public ResultMsg(boolean status, String msg){
        this.code = (status) ? "success" : "error";
        this.msg = msg;
        this.status = status;
    }

    public ResultMsg(boolean status, String msg, List<T> data){
        this.code = (status) ? "success" : "error";
        this.msg = msg;
        this.status = status;
        this.data = data;
    }

    public ResultMsg(boolean status, String msg, T item){
        this.code = (status) ? "success" : "error";
        this.msg = msg;
        this.status = status;
        this.item = item;
    }

}
