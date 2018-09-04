package com.example.witicar.medbeacon.RetrofitConfiguration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfiguration {

    static String address = "http://192.168.1.113:9999";
    static public Gson gson;

    private RetrofitConfiguration() {}

    static public Retrofit startRetrofit()
    {
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                String stringDate = json.getAsJsonPrimitive().getAsString();
                return new Date(Long.parseLong(stringDate));
            }
        });

        gson = builder
                .setDateFormat("yyyy-MM-dd")
                .setLenient()
                .create();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder().
                baseUrl(address).
                addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit = retrofitBuilder.build();

        return retrofit;
    }
}
