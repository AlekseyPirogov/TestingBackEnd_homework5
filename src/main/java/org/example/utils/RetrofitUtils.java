package org.example.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import static okhttp3.logging.HttpLoggingInterceptor.Level.BODY;

// Класс для определения Retrofit-клиента:
@UtilityClass
public class RetrofitUtils {


    // Настройки клиента:
    Properties properties = new Properties();
    private static InputStream configFile;

    // Файл с базовым URL, который вызывается во всех API-запросах.
    // Открытие файла:
    static {
        try {
            configFile = new FileInputStream("src/main/resources/mini-market-app.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new PrettyLogger());
    LoggingInterceptor loggingInterceptor2 = new LoggingInterceptor();
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    @SneakyThrows
    public String getBaseUrl() {
        properties.load(configFile);
        return properties.getProperty("url");
    }

    public Retrofit getRetrofit(){
        // Уровень логирования для запросов и ответов:
        loggingInterceptor.setLevel(BODY);
        httpClient.addInterceptor(loggingInterceptor);
        return new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }
}
