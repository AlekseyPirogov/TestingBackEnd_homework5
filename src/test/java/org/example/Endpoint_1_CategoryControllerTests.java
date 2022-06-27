package org.example;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import lombok.SneakyThrows;
import org.example.api.CategoryControllerService;
import org.example.dto.GetCategoryResponse;
import org.example.utils.RetrofitUtils;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.io.StringReader;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.*;

public class Endpoint_1_CategoryControllerTests extends CommonProductController
{
    static CategoryControllerService categoryService;

    @BeforeAll
    static void beforeAll() {
        categoryService = RetrofitUtils.getRetrofit().create(CategoryControllerService.class);
    }

    @Nested
    @DisplayName("1.Тесты для получения товаров по категориям")
    class CategoryController {

        @SneakyThrows
        @Test
        @DisplayName("Mini-market_TC#1.1_Получение товаров из категории \"Food\" (ID=1)")
        @Description("Mini-market_TC#1.1_Получение товаров из категории  \"Food\" (ID=1)")
        @Link("http://localhost:8189/market/swagger-ui.html#/category-controller/getCategoryByIdUsingGET")
        @Issue("localhost")
        @Tag("getCategoryByIdPositiveTestIdEqualFood")
        void getCategoryByIdPositiveTestIdEqualFood() throws IOException {
            // Создание объекта типа Response<GetCategoryResponse> и выполнение метода
            // Get /api/v1/categories/{id} для Category-controller:
            Response<GetCategoryResponse> response = categoryService.getCategory(1).execute();

            // Пример проверки заголовков запроса:
            MatcherAssert.assertThat(response.headers().get("Content-Type"), equalTo("application/json"));
            MatcherAssert.assertThat(response.headers().get("Transfer-Encoding"), equalTo("chunked"));
            MatcherAssert.assertThat(response.headers().get("Connection"), equalTo("keep-alive"));

            // Пример проверки тела запроса на даннные / ошибки:
            MatcherAssert.assertThat(response.body(), not(nullValue()));
            MatcherAssert.assertThat(response.errorBody(), nullValue());

            // Пример проверки данных тела на корректность:
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));
            MatcherAssert.assertThat(response.body().getId(), equalTo(1));
            MatcherAssert.assertThat(response.body().getTitle(), equalTo("Food"));
            response.body().getProducts().forEach(product ->
                    MatcherAssert.assertThat(product.getCategoryTitle(), equalTo("Food")));

            // Пример проверки данных тела на содержимое:
            String result = printResponse(response);
            MatcherAssert.assertThat(result, not(nullValue()));

            // Сереализация ответа:
            ObjectMapper mapper = new ObjectMapper();
            StringReader reader = new StringReader(result);
            GetCategoryResponse getCategoryResponseReader = mapper.readValue(reader, GetCategoryResponse.class);
            System.out.println(getCategoryResponseReader.toString());
        }

        @SneakyThrows
        @Test
        @DisplayName("Mini-market_TC#1.2_Получение товаров из категории \"Electronic\" (ID=2)")
        @Description("Mini-market_TC#1.2_Получение товаров из категории \"Electronic\" (ID=2)")
        @Link("http://localhost:8189/market/swagger-ui.html#/category-controller/getCategoryByIdUsingGET")
        @Issue("localhost")
        @Tag("getCategoryByIdPositiveTestIdEqualElectronic")
        void getCategoryByIdPositiveTestIdEqualElectronic() throws IOException {
            // Создание объекта типа Response<GetCategoryResponse> и выполнение метода
            // Get /api/v1/categories/{id} для Category-controller:
            Response<GetCategoryResponse> response = categoryService.getCategory(2).execute();

            // Пример проверки заголовков запроса:
            MatcherAssert.assertThat(response.headers().get("Content-Type"), equalTo("application/json"));
            MatcherAssert.assertThat(response.headers().get("Transfer-Encoding"), equalTo("chunked"));
            MatcherAssert.assertThat(response.headers().get("Connection"), equalTo("keep-alive"));

            // Пример проверки тела запроса на даннные / ошибки:
            MatcherAssert.assertThat(response.body(), not(nullValue()));
            MatcherAssert.assertThat(response.errorBody(), nullValue());

            // Пример проверки данных тела на корректность:
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));
            MatcherAssert.assertThat(response.body().getId(), equalTo(2));
            MatcherAssert.assertThat(response.body().getTitle(), equalTo("Electronic"));
            response.body().getProducts().forEach(product ->
                    MatcherAssert.assertThat(product.getCategoryTitle(), equalTo("Electronic")));

            // Пример проверки данных тела на содержимое:
            String result = printResponse(response);
            MatcherAssert.assertThat(result, not(nullValue()));

            // Сереализация ответа:
            ObjectMapper mapper = new ObjectMapper();
            StringReader reader = new StringReader(result);
            GetCategoryResponse getCategoryResponseReader = mapper.readValue(reader, GetCategoryResponse.class);
            System.out.println(getCategoryResponseReader.toString());
        }

        @SneakyThrows
        @Test
        @DisplayName("Mini-market_TC#1.3_Получение товаров из категории (ID=0) - негативный тест")
        @Description("Mini-market_TC#1.3_Получение товаров из категории (ID=0) - негативный тест")
        @Link("http://localhost:8189/market/swagger-ui.html#/category-controller/getCategoryByIdUsingGET")
        @Issue("localhost")
        @Tag("getCategoryByIdNegativeTestId0")
        void getCategoryByIdNegativeTestId0() {
            // Создание объекта типа Response<GetCategoryResponse> и выполнение метода
            // Get /api/v1/categories/{id} для Category-controller:
            Response<GetCategoryResponse> response = categoryService.getCategory(0).execute();

            // Пример проверки заголовков запроса:
            MatcherAssert.assertThat(response.headers().get("Content-Type"), equalTo("application/json"));
            MatcherAssert.assertThat(response.headers().get("Transfer-Encoding"), equalTo("chunked"));
            MatcherAssert.assertThat(response.headers().get("Connection"), equalTo("keep-alive"));

            // Пример проверки тела запроса на даннные / ошибки:
            MatcherAssert.assertThat(response.body(), nullValue());
            MatcherAssert.assertThat(response.errorBody(), not(nullValue()));

            // Пример проверки данных тела на корректность: ожидаемый результат ошибка 404
            MatcherAssert.assertThat(printResponse(response), containsString("404"));
        }

        @SneakyThrows
        @Test
        @DisplayName("Mini-market_TC#1.4_Получение товаров из категории (ID=3)")
        @Description("Mini-market_TC#1.4_Получение товаров из категории (ID=3)")
        @Link("http://localhost:8189/market/swagger-ui.html#/category-controller/getCategoryByIdUsingGET")
        @Issue("localhost")
        @Tag("getCategoryByIdNegativeTestId3")
        void getCategoryByIdNegativeTestId3() {
            // Создание объекта типа Response<GetCategoryResponse> и выполнение метода
            // Get /api/v1/categories/{id} для Category-controller:
            Response<GetCategoryResponse> response = categoryService.getCategory(3).execute();

            // Пример проверки заголовков запроса:
            MatcherAssert.assertThat(response.headers().get("Content-Type"), equalTo("application/json"));
            MatcherAssert.assertThat(response.headers().get("Transfer-Encoding"), equalTo("chunked"));
            MatcherAssert.assertThat(response.headers().get("Connection"), equalTo("keep-alive"));

            // Пример проверки тела запроса на даннные / ошибки:
            MatcherAssert.assertThat(printResponse(response), containsString("404"));
        }

        @SneakyThrows
        @Test
        @DisplayName("Mini-market_TC#1.5_Get invalid category (ID=-1)")
        @Description("Mini-market_TC#1.5_Get invalid category (ID=-1)")
        @Link("http://localhost:8189/market/swagger-ui.html#/category-controller/getCategoryByIdUsingGET")
        @Issue("localhost")
        @Tag("getCategoryByIdNegativeTestIdNeg1")
        void getCategoryByIdNegativeTestIdNeg1() {
            // Создание объекта типа Response<GetCategoryResponse> и выполнение метода
            // Get /api/v1/categories/{id} для Category-controller:
            Response<GetCategoryResponse> response = categoryService.getCategory(-1).execute();

            // Пример проверки заголовков запроса:
            MatcherAssert.assertThat(response.headers().get("Content-Type"), equalTo("application/json"));
            MatcherAssert.assertThat(response.headers().get("Transfer-Encoding"), equalTo("chunked"));
            MatcherAssert.assertThat(response.headers().get("Connection"), equalTo("keep-alive"));

            // Пример проверки тела запроса на даннные / ошибки:
            MatcherAssert.assertThat(printResponse(response), containsString("404"));
        }
    }
}
