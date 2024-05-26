package org.example.t1.entity;

/**
 * @author 郑悦
 * @Description: 用于处理异常-接口返回的错误码500情况
 * @date 2024/5/25 01:21
 */
public class EIException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public EIException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
