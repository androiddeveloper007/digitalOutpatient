package com.cybermax.digitaloutpatient.httpapi;

import com.cybermax.digitaloutpatient.bean.CallNumber;
import com.cybermax.digitaloutpatient.bean.Child;
import com.cybermax.digitaloutpatient.bean.Inoculation;
import com.cybermax.digitaloutpatient.bean.Operator;
import com.cybermax.digitaloutpatient.bean.BatchInfo;
import com.cybermax.digitaloutpatient.bean.InoculatePlace;
import com.cybermax.digitaloutpatient.bean.PretestHistory;
import com.cybermax.digitaloutpatient.bean.Question;
import com.cybermax.digitaloutpatient.bean.StayObserveScreenBean;
import com.cybermax.digitaloutpatient.bean.CallNumber;
import com.cybermax.digitaloutpatient.bean.QueueInfo;
import com.cybermax.digitaloutpatient.bean.Ticket;
import com.cybermax.digitaloutpatient.bean.VacccrkBarcode;
import com.cybermax.digitaloutpatient.bean.share.User;
import com.cybermax.digitaloutpatient.bean.share.Workstation;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;
import retrofit2.http.Url;

public interface HttpInterface {

    @GET("sysConfig/getServerTime")
    Call<ResponseBody> getServerTime();

    @GET("sysConfig/checkPassword")
    Call<ResponseBody> checkPassword(@QueryMap Map<String, Object> fields);

    @FormUrlEncoded
    @POST("appFileManage/query")
    Call<ResponseBody> queryMonthStepsByDeviceID(@FieldMap Map<String, Object> fields);

    @FormUrlEncoded
    @POST("appFileManage/query")
    Call<ResponseBody> serverLogin(@FieldMap Map<String, Object> fields);

    @GET("workstation/queryList")
    Call<HttpResult<List<Workstation>>> getWorkstation(@QueryMap Map<String, Object> fields);

    @FormUrlEncoded
    @POST("device/bindDevice")//设备与工作台绑定接口
    Call<ResponseBody> bindDevice(@FieldMap Map<String, Object> fields);

    @FormUrlEncoded
    @POST("user/checkPwd")//设备与工作台绑定接口
    Call<HttpResult<User>> doctorLogin(@FieldMap Map<String, Object> fields);

    @FormUrlEncoded
    @POST("user/checkPwd")//设备与工作台绑定接口
    Call<HttpResult<PretestHistory>> pretestHistory(@FieldMap Map<String, Object> fields);

//    @FormUrlEncoded
//    @POST("{cmd}")
//    Call<ResponseBody> commonRequest(@Path("cmd") String cmd, @FieldMap Map<String, Object> fields);

    @POST()
    Call<ResponseBody> commonRequest(@Url String url, @QueryMap Map<String, Object> fields);

    @GET()
    Call<ResponseBody> commonRequest(@Url String url);

    @GET()
    Call<ResponseBody> commonGetRequest(@Url String url, @QueryMap Map<String, Object> fields);

    @GET("inoculationStation/listUsableBatchNos")
    Call<HttpResult<List<BatchInfo>>> getBatchNo(@QueryMap Map<String, Object> fields);

    @GET("inoculationStation/listInocPlaces")
    Call<HttpResult<List<InoculatePlace>>> getInoculatePlace(@QueryMap Map<String, Object> fields);

    @POST()
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<ResponseBody> commonRequestContentJSON(@Url String url, @Body RequestBody body);


    @GET("workstation/queryList")
    Call<HttpResult<List<Workstation>>>  getWorkstationNew(@QueryMap Map<String, Object> fields);


    //////////////////////ticketAction////////////////////

    @GET("ticketAction/listTicketQueue")
    Call<HttpResult<List<Ticket>>> listTicketQueue(@Query("tiacStatus") Integer tiacStatus, @Query("prtyCode") String prtyCode);

    @GET("ticketAction/getProcessingTicket")
    Call<HttpResult<CallNumber>> getProcessingTicket(@Query("wostId") Integer wostId);

    @POST("ticketAction/callNumber")
    Call<HttpResult<CallNumber>> callNumber(@QueryMap Map<String, Object>  operator);

    @POST("ticketAction/passNumber")
    Call<HttpResult<Object>> passNumber(@QueryMap Map<String, Object>  operator);

    @POST("ticketAction/recallNumber")
    Call<HttpResult<Object>> recallNumber(@QueryMap Map<String, Object>  operator);

    @GET("ticketAction/countQueue")
    Call<HttpResult<QueueInfo>> countQueue(@Query("wostId")Integer wostId);

    @POST("ticketAction/completeInoc")
    Call<HttpResult<Object>> completeInoc(@Body RequestBody body);


    @GET("ticketAction/getCallingTicket")
    Call<HttpResult<Ticket>> getCallingTicket(@QueryMap Map<String, Object> fields);

    @POST("ticketAction/listSynQueues")
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    Call<HttpResult<List<Ticket>>> listSynQueues( @Body RequestBody body);

    @GET("ticket/listObservers")
    Call<HttpResult<List<StayObserveScreenBean>>> listObservers();

    @POST("inoculationStation/confirmSingleInoc")
    Call<HttpResult<Inoculation>> confirmSingleInoc(@Body Inoculation inoculation);

    @POST("ticketAction/complete")
    Call<HttpResult<Object>> complete(@Body Map<String, Object> operator);

    @POST("inoculationStation/cancel")
    Call<HttpResult<Inoculation>> cancel(@Body Inoculation inoculation);

    //////////////////////////////

    @GET("inocExam/listQuestions")
    Call<HttpResult<List<Question>>> listQuestions(@Query("queType")Integer queType);

    @GET("child/queryFuzzy")
    Call<HttpResult<List<Child>>> queryFuzzy(@Query("querystr")String keywords);

    @POST("ticketAction/bindChild")
    Call<HttpResult<CallNumber>> bindChild(@Query("ticketId")Integer ticketId, @Query("chilNo")String chilNo, @Query("chilCardNo")String chilCardNo, @Query("wostId")Integer wostId);

    @POST("inputStock/getByBarcode")
    Call<HttpResult<VacccrkBarcode>> scanVaccineBarCode(@QueryMap Map<String,Object> params);
}
