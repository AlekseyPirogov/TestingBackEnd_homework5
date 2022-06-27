package org.example;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.example.dto.GetProduct;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;

public class Endpoint_3_ProductControllerTests_CreateProduct extends CommonProductController {

    GetProduct product = null;
    int id;

    @Nested
    @DisplayName("3.1.Тесты для добавления и удаления записей по категориям Food/Electronic (без указания id)")
    class AddItemInCategory {

        @SneakyThrows
        @Test
        @DisplayName("Mini-market_TC#3.1.1_Создание нового продукта (category = Food)")
        @Description("Mini-market_TC#3.1.1_Создание нового продукта (category = Food)")
        @Link("http://localhost:8189/market/swagger-ui.html#/product-controller/createNewProductUsingPOST")
        @Issue("localhost")
        @Tag("createProductInFoodCategoryTest")
        void createProductInFoodCategoryTest() throws IOException {
            product = setProductFood();
            Response<GetProduct> response = productService.createProduct(product).execute();
            id = response.body().getId();
            assertThat(response.isSuccessful(), CoreMatchers.is(true));
        }

        @SneakyThrows
        @Test
        @DisplayName("Mini-market_TC#3.1.2_Создание нового продукта (category = Electronic)")
        @Description("Mini-market_TC#3.1.2_Создание нового продукта (category = Electronic)")
        @Link("http://localhost:8189/market/swagger-ui.html#/product-controller/createNewProductUsingPOST")
        @Issue("localhost")
        @Tag("createProductInElectronicCategoryTest")
        void createProductInElectronicCategoryTest() throws IOException {
            product = setProductElectronic();
            Response<GetProduct> response = productService.createProduct(product).execute();
            id = response.body().getId();
            assertThat(response.isSuccessful(), CoreMatchers.is(true));
        }

        @SneakyThrows
        @AfterEach
        void tearDown() {
            Response<ResponseBody> response = productService.deleteProduct(id).execute();
            assertThat(response.isSuccessful(), CoreMatchers.is(true));
        }
    }

    @Nested
    @DisplayName("3.2.Тесты для добавления и удаления записей по категориям Food/Electronic (с указанием id)")
    class GroupTests {

        @SneakyThrows
        @Test
        @DisplayName("Mini-market_TC#3.2.1_Создание нового продукта (category = Food)")
        @Description("Mini-market_TC#3.2.1_Создание нового продукта (category = Food)")
        @Link("http://localhost:8189/market/swagger-ui.html#/product-controller/createNewProductUsingPOST")
        @Issue("localhost")
        @Tag("createProductInFoodCategoryTestWithID")
        void createProductInFoodCategoryTestWithID() throws IOException {
            Faker faker = new Faker();
            product = setProduct((int) (Math.random() * 10000), faker.food().ingredient(), "Food", (int) (Math.random() * 10000));
            Response<GetProduct> response = productService.createProduct(product).execute();
            assertThat(response.isSuccessful(), CoreMatchers.is(false));
        }

        @SneakyThrows
        @Test
        @DisplayName("Mini-market_TC#3.2.2_Создание нового продукта (category = Electronic)")
        @Description("Mini-market_TC#3.2.2_Создание нового продукта (category = Electronic)")
        @Link("http://localhost:8189/market/swagger-ui.html#/product-controller/createNewProductUsingPOST")
        @Issue("localhost")
        @Tag("createProductInElectronicCategoryTestWithID")
        void createProductInElectronicCategoryTestWithID() throws IOException {
            Faker faker = new Faker();
            product = setProduct((int) (Math.random() * 10000), faker.commerce().productName(), "Electronic", (int) (Math.random() * 10000));
            Response<GetProduct> response = productService.createProduct(product).execute();
            assertThat(response.isSuccessful(), CoreMatchers.is(false));
        }
    }
}
