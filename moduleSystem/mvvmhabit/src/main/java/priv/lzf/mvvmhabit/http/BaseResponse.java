package priv.lzf.mvvmhabit.http;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/5.
 */
public class BaseResponse<T> {
    private int result;
    private String msg;
    private T data;
    private boolean success;
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isOk() {
        return result == 0;
    }

    public boolean isFaceOk(){
        return code==0;
    }
}
