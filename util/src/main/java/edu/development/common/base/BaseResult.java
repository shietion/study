package edu.development.common.base;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一返回结果类
 * Created by atminer on 2017/2/18.
 */
public class BaseResult {

    // 状态码：1成功，其他为失败
    public int code;

    // 成功为success，其他为失败原因
    public String message;

    // 数据结果集
    public Map<String,Object> result;
    
    public BaseResult() {}

    public BaseResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    


    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public void add(String key,Object value){
	if(null == result ) {
	    result = new HashMap<String,Object>() ;
	}
	result.put(key, value) ;
    }
    
    public Map<String, Object> modelMap() {
	if(null == result ) {
	    result = new HashMap<String,Object>() ;
	}
	result.put("code", code) ;
	result.put("message", message) ;
	return result ;
    }
    

}
