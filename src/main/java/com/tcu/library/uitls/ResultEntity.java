package com.tcu.library.uitls;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yjn
 * @Date: 2020/9/29 20:24
 */
@Data
public class ResultEntity {
    private Boolean success;
    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<String, Object>();

    private ResultEntity(){}
    public static ResultEntity ok(){
        ResultEntity resultEntity =new ResultEntity();
        resultEntity.setSuccess(true);
        resultEntity.setCode(ResultCode.SUCCESS);
        resultEntity.setMessage("成功");
        return resultEntity;
    }
    public static ResultEntity error(){
        ResultEntity resultEntity =new ResultEntity();
        resultEntity.setSuccess(false);
        resultEntity.setCode(ResultCode.ERROR);
        resultEntity.setMessage("失败");
        return resultEntity;
    }
    public ResultEntity success(Boolean success){
        this.setSuccess(success);
        return this;
    }
    public ResultEntity message(String message){
        this.setMessage(message);
        return this;
    }
    public ResultEntity code(Integer code){
        this.setCode(code);
        return this;
    }
    public ResultEntity data(String key, Object value){
        this.data.put(key, value);
        return this;
    }
    public ResultEntity data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
