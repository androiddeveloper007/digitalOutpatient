package com.cybermax.digitaloutpatient.httpapi;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import lombok.Data;

@Data
public class HttpResult<T> {
    private String ecode = "1000";
    private String msg = "操作成功";
    private  T data;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public  boolean isBizSuccessFull(){
        if("1000".equals(ecode)){
            return true;
        }
        return false;
    }
}
