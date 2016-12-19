package com.explore.wang.exception;

/**
 * 自定义消息异常
 * Created by 王兆琦  on 2016/12/15 15.07.
 */
public class MsgException extends RuntimeException {


    public MsgException(){

    }

    public MsgException(String msg) {
        super(msg);
    }
}
