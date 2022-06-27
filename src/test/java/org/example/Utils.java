package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.dto.GetCategoryResponse;
import org.junit.jupiter.api.Test;

import java.io.StringReader;
import java.io.StringWriter;

public class Utils {

    @SneakyThrows
    @Test
    void test() {

        // Пример сереализации:
        // Объект в представлении класса GetCategoryResponse
        GetCategoryResponse exampleGetCategoryResponse = new GetCategoryResponse();
        exampleGetCategoryResponse.setId(1);
        exampleGetCategoryResponse.setTitle("Food");
        //Объект Jackson, который выполняет сериализацию/десерализацию
        ObjectMapper mapper = new ObjectMapper();
        //Объект для хранение строки Jackson
        StringWriter writer = new StringWriter();
        //Cериализация: 1-куда, 2-что
        mapper.writeValue(writer, exampleGetCategoryResponse);
        //преобразовываем все записанное во StringWriter в строку
        String result = writer.toString();
        System.out.println("Сереализация: " + result);

        // Пример десереализации:
        StringReader reader = new StringReader("{\"id\":1,\"title\":\"Food\",\"products\":[" +
                "{\"id\":1,\"title\":\"Milk\",\"price\":95,\"categoryTitle\":\"Food\"}," +
                "{\"id\":2,\"title\":\"Bread\",\"price\":25,\"categoryTitle\":\"Food\"}," +
                "{\"id\":3,\"title\":\"Cheese\",\"price\":360,\"categoryTitle\":\"Food\"}]}");
        GetCategoryResponse getCategoryResponseReader = mapper.readValue(reader, GetCategoryResponse.class);
        result = getCategoryResponseReader.toString();
        System.out.println("\nДесереализация: " + result);
    }
}