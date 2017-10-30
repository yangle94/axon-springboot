package cn.ylapl.util.resultBean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Angle
 * Date: 2017/9/4
 * Time: 上午10:38
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageResultBean<T> implements Serializable{

    /**
     * 分页信息
     */
    private PageInfo pageInfo;

    private static final int SUCCESS = 1;

    private static final int FAIL = 0;

    private int code;

    private List<T> object;

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
    public static <T> PageResultBean<T> newSuccess() {
        PageResultBean<T> result = new PageResultBean<>();
        result.setCode(SUCCESS);
        return result;
    }

    /**
     * 接口调用成功，有返回对象
     */
    public static <T> PageResultBean<T> newSuccess(List<T> object, PageInfo<T> pageInfo) {
        PageResultBean<T> result = new PageResultBean<>();
        result.setPageInfo(pageInfo);
        result.setCode(SUCCESS);
        result.setObject(object);
        return result;
    }

    /**
     * 接口调用失败，有错误码和描述，没有返回对象
     */
    public static <T> PageResultBean<T> newFailure(String message) {
        PageResultBean<T> result = new PageResultBean<>();
        result.setCode(FAIL);
        result.setMessage(message);
        return result;
    }
}