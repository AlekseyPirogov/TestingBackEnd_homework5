package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import okhttp3.ResponseBody;
import org.example.api.ProductControllerService;
import org.example.dto.GetCategoryResponse;
import org.example.dto.GetProduct;
import org.example.utils.RetrofitUtils;
import org.junit.jupiter.api.BeforeAll;
import retrofit2.Response;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

public class CommonProductController {

    // Стаческое поле для сервиса "ProductControllerService":
    static ProductControllerService productService;

    // Инициализация поля один раз, перед всеми тестами:
    @BeforeAll
    static void beforeAll() {
        // У класса Retrofit вызываем метод create, куда рефлексивно добавляем
        // наименование интерфейса сервиса. Далее в тесте будет вызываться подходящий
        // метод из интерфейса сервиса с соответсвующими параметрами
        productService = RetrofitUtils.getRetrofit()
                .create(ProductControllerService.class);
    }

    static String printResponse(Response<GetCategoryResponse> response, ResultCategory... noParam) throws IOException {

        String result = null;
        if(response.body() != null) {
            //Объект для хранения строки
            StringWriter writer = new StringWriter();
            //Объект Jackson, который выполняет сериализацию
            ObjectMapper mapper = new ObjectMapper();
            //Сама сериализация: 1-куда, 2-что
            mapper.writeValue(writer, response.body());
            //преобразовываем все записанное во StringWriter в строку
            result = writer.toString();
            System.out.println("Результат запроса:\n" + result);
        } else {
            result = response.errorBody().string();
            System.out.println("Результат запроса:\n" + result);
        }
        return result;
    }

    static String printResponse(Response<GetProduct> response, ResultProduct... noParam) throws IOException {

        String result = null;
        if(response.body() != null) {
            //Объект для хранения строки
            StringWriter writer = new StringWriter();
            //Объект Jackson, который выполняет сериализацию
            ObjectMapper mapper = new ObjectMapper();
            //Сама сериализация: 1-куда, 2-что
            mapper.writeValue(writer, response.body());
            //преобразовываем все записанное во StringWriter в строку
            result = writer.toString();
            System.out.println("Результат запроса:\n" + result);
        } else {
            result = response.errorBody().string();
            System.out.println("Результат запроса:\n" + result);
        }
        return result;
    }

    static String printResponse(Response<ResponseBody> response) throws IOException {

        String result = null;
        if(response.body() != null) {
            //Объект для хранения строки
            StringWriter writer = new StringWriter();
            //Объект Jackson, который выполняет сериализацию
            ObjectMapper mapper = new ObjectMapper();
            //Сама сериализация: 1-куда, 2-что
            mapper.writeValue(writer, response.body());
            //преобразовываем все записанное во StringWriter в строку
            result = writer.toString();
            System.out.println("Результат запроса:\n" + result);
        } else {
            result = response.errorBody().string();
            System.out.println("Результат запроса:\n" + result);
        }
        return result;
    }

    //serialization
    GetProduct[] deserialization(String responseString) throws IOException {
        // Десериализация:
        ObjectMapper mapper = new ObjectMapper();
        StringReader reader = new StringReader(responseString);
        GetProduct[] arrayProduct = mapper.readValue(reader, GetProduct[].class);
        return arrayProduct;
    }

//    static void printBodyResponce(Response<GetProduct> response) {
//        System.out.println(response.body().getId() + ", " +
//                response.body().getTitle() + ", " +
//                response.body().getPrice() + ", " +
//                response.body().getCategoryTitle());
//    }

    static void printBodyProduct(GetProduct product, String ... msg){
        System.out.println(msg[msg.length-1] + product.getId() + ", " +
                           product.getTitle() + ", " +
                           product.getPrice() + ", " +
                           product.getCategoryTitle());
    }

    GetProduct setProductFood() {
        Faker faker = new Faker();
        return new GetProduct()
                .withTitle(faker.food().ingredient())
                .withCategoryTitle("Food")
                .withPrice((int) (Math.random() * 10000));
    }

    GetProduct setProductElectronic() {
        Faker faker = new Faker();
        return new GetProduct()
                .withTitle(faker.commerce().productName())
                .withCategoryTitle("Electronic")
                .withPrice((int) (Math.random() * 10000));
    }

    GetProduct setProduct(Integer id, String title, String category, Integer price) {
        GetProduct product = new GetProduct()
                .withId(id)
                .withTitle(title)
                .withCategoryTitle(category)
                .withPrice(price);
        return product;
    }

    public GetProduct modify(Integer id, String title, Integer price, String categoryTitle){
        return new GetProduct()
                .withId(id)
                .withTitle(title)
                .withPrice(price)
                .withCategoryTitle(categoryTitle);
    }
}
