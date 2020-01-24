package com.example.fitnesscrew.util;

import com.example.fitnesscrew.bean.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Test {

    @GET("teste")
    Call<String> findTest();

    @POST("insertEstoque")
    Call<String> getTest(@Body User user);

    //@GET("cep/find/{cep}/json")
    //Call<CEP> buscarCEP(@Path("cep") String cep);

}
