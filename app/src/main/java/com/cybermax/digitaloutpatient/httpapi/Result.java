package com.cybermax.digitaloutpatient.httpapi;

import lombok.Data;
import lombok.EqualsAndHashCode;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import retrofit2.Response;

@EqualsAndHashCode(callSuper = true)
@Data
public class Result<T>  extends ResponseBody {
    private String ecode = "1000";
    private String msg = "操作成功";
    private  T data;

    @Override
    public MediaType contentType() {
        return null;
    }

    @Override
    public long contentLength() {
        return 0;
    }

    @Override
    public BufferedSource source() {
        return null;
    }
}
