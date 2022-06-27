package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import lombok.SneakyThrows;
import okhttp3.ResponseBody;
import org.example.dto.GetProduct;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import retrofit2.Response;

import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;

public class Endpoint_5_ProductControllerTests_DeleteProduct extends CommonProductController {

    @Nested
    @DisplayName("5.Тесты для удаления и добавления записей о товарах")
    class DeleteItem {

        @SneakyThrows
        @Test
        @DisplayName("Mini-market_TC#5.1_Удаление и повтороное добавлени элемента с минимальным id (category = Food/Electronic)")
        @Description("Mini-market_TC#5.1_Удаление и повтороное добавлени элемента с минимальным id (category = Food/Electronic)")
        @Link("http://localhost:8189/market/swagger-ui.html#/product-controller/deleteByIdUsingDELETE")
        @Issue("localhost")
        @Tag("deleteProductsWithMinId")
        void deleteProductsWithMinId() throws IOException {
            // Создание объекта типа Response<GetCategoryResponse> и выполнение метода
            // Get /api/v1/products для Category-controller:
            Response<ResponseBody> response = productService.getProducts().execute();
            assertThat(response.isSuccessful(), CoreMatchers.is(true));
            // Десереализация:
            GetProduct[] arrayProduct = deserialization(response.body().string());
            // Получение элемента c минимальным id до удаления всех данных
            Integer idFirstItemBeforeDeleteProduct = arrayProduct[0].getId();
            GetProduct product = arrayProduct[0];
            // Удаление продукта:
            response = productService.deleteProduct(idFirstItemBeforeDeleteProduct).execute();
            assertThat(response.isSuccessful(), CoreMatchers.is(true));
            // Цикл по всем элементам для повторной записи продуктов в БД
            product.setId(null);
            Response<GetProduct> responseAddProduct = productService.createProduct(product).execute();
            assertThat(responseAddProduct.isSuccessful(), CoreMatchers.is(true));
            // Выполнение метода Get /api/v1/products для Category-controller:
            response = productService.getProducts().execute();
            assertThat(response.isSuccessful(), CoreMatchers.is(true));
            // Десериализация:
            arrayProduct = deserialization(response.body().string());
            // Получение id максимального элемента после удаления всех данных:
            Integer idFirstItemAfterDeleteProduct = arrayProduct[0].getId();
            // Сравление id элемента до удаления и после удаления:
            assertThat(idFirstItemAfterDeleteProduct.equals(idFirstItemBeforeDeleteProduct), CoreMatchers.is(false));
        }

        @SneakyThrows
        @Test
        @DisplayName("Mini-market_TC#5.2_Удаление и повтороное добавлени элемента с максимальным id (category = Food/Electronic)")
        @Description("Mini-market_TC#5.2_Удаление и повтороное добавлени элемента с максимальным id (category = Food/Electronic)")
        @Link("http://localhost:8189/market/swagger-ui.html#/product-controller/deleteByIdUsingDELETE")
        @Issue("localhost")
        @Tag("deleteProductsWithMinId")
        void deleteProductsWithMaxId() throws IOException {
            // Создание объекта типа Response<GetCategoryResponse> и выполнение метода
            // Get /api/v1/products для Category-controller:
            Response<ResponseBody> response = productService.getProducts().execute();
            assertThat(response.isSuccessful(), CoreMatchers.is(true));
            // Десереализация:
            GetProduct[] arrayProduct = deserialization(response.body().string());
            // Получение элемента c минимальным id до удаления всех данных
            Integer idLastItemBeforeDeleteProduct = arrayProduct[arrayProduct.length - 1].getId();
            GetProduct product = arrayProduct[arrayProduct.length - 1];
            // Удаление продукта:
            response = productService.deleteProduct(idLastItemBeforeDeleteProduct).execute();
            assertThat(response.isSuccessful(), CoreMatchers.is(true));
            // Цикл по всем элементам для повторной записи продуктов в БД
            product.setId(null);
            Response<GetProduct> responseAddProduct = productService.createProduct(product).execute();
            assertThat(responseAddProduct.isSuccessful(), CoreMatchers.is(true));
            // Выполнение метода Get /api/v1/products для Category-controller:
            response = productService.getProducts().execute();
            assertThat(response.isSuccessful(), CoreMatchers.is(true));
            // Десериализация:
            arrayProduct = deserialization(response.body().string());
            // Получение id максимального элемента после удаления всех данных:
            Integer idLastItemAfterDeleteProduct = arrayProduct[arrayProduct.length - 1].getId();
            // Сравление id элемента до удаления и после удаления:
            assertThat(idLastItemAfterDeleteProduct.equals(idLastItemBeforeDeleteProduct), CoreMatchers.is(true));
        }

        @SneakyThrows
        @Test
        @DisplayName("Mini-market_TC#5.3_Удаление и повтороное добавлени всего перечня продуктов (category = Food/Electronic)")
        @Description("Mini-market_TC#5.3_Удаление и повтороное добавлени всего перечня продуктов (category = Food/Electronic)")
        @Link("http://localhost:8189/market/swagger-ui.html#/product-controller/deleteByIdUsingDELETE")
        @Issue("localhost")
        @Tag("deleteAllProducts")
        void deleteAllProducts() throws IOException {
            // Создание объекта типа Response<GetCategoryResponse> и выполнение метода
            // Get /api/v1/products для Category-controller:
            Response<ResponseBody> response = productService.getProducts().execute();
            assertThat(response.isSuccessful(), CoreMatchers.is(true));
            // Десереализация:
            GetProduct[] arrayProduct = deserialization(response.body().string());
            Integer idFirstItemBeforeDeleteProduct = arrayProduct[0].getId();
            // Цикл для удаления продуктов:
            for (GetProduct product : arrayProduct) {
                response = productService.deleteProduct(product.getId()).execute();
                assertThat(response.isSuccessful(), CoreMatchers.is(true));
            }
            // Цикл для создания "нового" перечня продуктов с новыми id
            for (GetProduct product : arrayProduct) {
                product.setId(null);
                Response<GetProduct> responseAddProduct = productService.createProduct(product).execute();
                ;
                assertThat(responseAddProduct.isSuccessful(), CoreMatchers.is(true));
            }
            // Запрос всего перечня продуктов:
            response = productService.getProducts().execute();
            assertThat(response.isSuccessful(), CoreMatchers.is(true));
            // Десереализация:
            arrayProduct = deserialization(response.body().string());
            Integer idFirstItemAfterDeleteProduct = arrayProduct[0].getId();
            // Сравление id 1-го элемента до удаления и после удаления:
            assertThat(idFirstItemAfterDeleteProduct.equals(idFirstItemBeforeDeleteProduct), CoreMatchers.is(true));
        }
    }
}
