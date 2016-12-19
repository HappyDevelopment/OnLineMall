package com.explore.wang.exception;

/**
 * Created by 王兆琦  on 2016/12/3 18.39.
 */
public class NullDataException extends RuntimeException {


    /**
     * 默认无参的构造方法
     */
    public NullDataException() {

    }


    /**
     * 进行传递错误信息
     * @param msg error message
     */
    public NullDataException(String msg) {
        super(msg);
    }




}
