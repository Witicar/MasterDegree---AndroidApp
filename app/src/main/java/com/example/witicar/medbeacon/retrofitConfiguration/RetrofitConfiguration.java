package com.example.witicar.medbeacon.retrofitConfiguration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfiguration {

    static String address = "http://192.168.1.234:9999";
    static public Gson gson;

    private RetrofitConfiguration() {}

    static public Retrofit startRetrofit()
    {
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                String stringDate = json.getAsJsonPrimitive().getAsString();
                try {
                    return new Date(Long.parseLong(stringDate));
                } catch (NumberFormatException e)
                {
                    Date date = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    try {
                        date = format.parse(stringDate);
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    return date;
                }
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
