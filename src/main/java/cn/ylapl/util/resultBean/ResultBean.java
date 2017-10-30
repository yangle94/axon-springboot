package cn.ylapl.util.resultBean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回值封装类
 * Created by Angle on 2017/3/3.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultBean<T> implements Serializable {

    private static final int SUCCESS = 1;

    private static final int FAIL = 0;

    private int code;

    private T object;

    private String error;

    private String message;

    private Exception exception;

    /**
     * 判断返回结果是否成功 0是失败 1是成功
     */
    public boolean isSuccess() {
        return code == SUCCESS;
    }

    /**
     * 判断返回结果是否有结果对象
     */
    public boolean hasObject() {
        return code == 0 && object != null;
    }

    /**
     * 判断返回结果是否有异常
     */
    public boolean hasException() {
        return exception != null;
    }

    /**
     * 接口调用成功，不需要返回对象
     */
    public static <T> ResultBean<T> newSuccess() {
        ResultBean<T> result = new ResultBean<>();
        result.setCode(SUCCESS);
        return result;
    }

    /**
     * 接口调用成功，有返回对象
     */
    public static <T> ResultBean<T> newSuccess(T object) {
        ResultBean<T> result = new ResultBean<>();
        result.setCode(SUCCESS);
        result.setObject(object);
        return result;
    }

    /**
     * 接口调用失败，有错误码和描述，没有返回对象
     */
    public static <T> ResultBean<T> newFailure(String message) {
        ResultBean<T> result = new ResultBean<>();
        result.setCode(FAIL);
        result.setMessage(message);
        return result;
    }

    /**
     * 接口调用失败，有错误字符串码和描述，没有返回对象
     */
    public static <T> ResultBean<T> newFailure(String error, String message) {
        ResultBean<T> result = new ResultBean<>();
        result.setCode(FAIL);
        result.setError(error);
        result.setMessage(message);
        return result;
    }

    /**
     * 转换或复制错误结果
     */
    public static <T> ResultBean<T> newFailure(ResultBean<?> failure) {
        ResultBean<T> result = new ResultBean<>();
        result.setCode(failure.getCode());
        result.setError(failure.getError());
        result.setMessage(failure.getMessage());
        result.setException(failure.getException());
        return result;
    }

    /**
     * 接口调用失败，返回异常信息
     */
    public static <T> ResultBean<T> newException(Exception e) {
        ResultBean<T> result = new ResultBean<>();
        result.setCode(FAIL);
        result.setException(e);
        result.setMessage(e.getMessage());
        return result;
    }
}
