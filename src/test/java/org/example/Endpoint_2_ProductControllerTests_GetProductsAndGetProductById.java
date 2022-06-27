package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.example.dto.GetProduct;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import retrofit2.Response;

import java.io.IOException;
import java.io.StringReader;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class Endpoint_2_ProductControllerTests_GetProductsAndGetProductById extends CommonProductController
{

    @Nested
    @DisplayName("2.Тест для получения общего списка товаров и тесты для получения товаров по Id")
    class CategoryController {

        @SneakyThrows
        @Test
        @DisplayName("Mini-market_TC#1_Тест для получения общего списка товаров")
        @Description("Mini-market_TC#1_Тест для получения общего списка товаров")
        @Link("http://localhost:8189/market/swagger-ui.html#/product-controller/getProductsUsingGET")
        @Issue("localhost")
        @Tag("getProductInProductController")
        void getProductInProductController() throws IOException {
            Response<ResponseBody> response = productService.getProducts().execute();
            // Пример проверки тела запроса на даннные / ошибки
            MatcherAssert.assertThat(response.body(), not(nullValue()));
            MatcherAssert.assertThat(response.errorBody(), nullValue());
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));
        }

        @SneakyThrows
        @ParameterizedTest
        @DisplayName("Mini-market_TC#2_Параметризованный тест для получения товара по Id (invalid ID)")
        @Description("Mini-market_TC#2_Параметризованный тест для получения товара по Id (invalid ID)")
        @Link("http://localhost:8189/market/swagger-ui.html#/product-controller/getProductByIdUsingGET")
        @Issue("localhost")
        @Tag("getProductByInvalidId")
        @CsvSource({"-1", "0", "6"})
        void getProductByInvalidId(int id) throws IOException {
            Response<GetProduct> response = productService.getProductById(id).execute();
            // Пример проверки тела запроса на даннные / ошибки
            MatcherAssert.assertThat(response.body(), nullValue());
            MatcherAssert.assertThat(response.errorBody(), not(nullValue()));
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(false));

            // Пример проверки данных тела на содержимое:
            String result = printResponse(response);
            MatcherAssert.assertThat(result, not(nullValue()));
        }

        @SneakyThrows
        @ParameterizedTest
        @DisplayName("Mini-market_TC#3_Параметризованный тест для получения товара по Id (valid ID)")
        @Description("Mini-market_TC#3_Параметризованный тест для получения товара по Id (valid ID)")
        @Link("http://localhost:8189/market/swagger-ui.html#/product-controller/getProductByIdUsingGET")
        @Issue("localhost")
        @Tag("getProductByValidId")
        @CsvSource({"1", "5"})
        void getProductByValidId(Integer parameter) throws IOException {
            Response<GetProduct> response = productService.getProductById(parameter).execute();
            // Пример проверки тела запроса на даннные / ошибки
            MatcherAssert.assertThat(response.body(), not(nullValue()));
            MatcherAssert.assertThat(response.errorBody(), nullValue());
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));

            // Пример проверки данных тела на содержимое:
            String result = printResponse(response);
            MatcherAssert.assertThat(result, not(nullValue()));

            // Пример сереализация объекта для тела ответа:
            ObjectMapper mapper = new ObjectMapper();
            StringReader reader = new StringReader(result);
            GetProduct productReader = mapper.readValue(reader, GetProduct.class);
            printBodyProduct(productReader, "Результат: ");
        }
    }
}
