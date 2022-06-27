package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import lombok.SneakyThrows;
import org.example.dto.GetProduct;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;
import retrofit2.Response;

import java.io.IOException;
import java.io.StringReader;

import static org.hamcrest.Matchers.*;

public class Endpoint_4_ProductControllerTests_ModifyProduct extends CommonProductController {

    GetProduct product = null;
    Faker faker = new Faker();
    int id, price;
    String title, categoryTitle;

    @Nested
    @DisplayName("4.1.Тесты для добавления и удаления записей по категориям (Food)")
    class ModifyItemInFood {

        @SneakyThrows
        @Test
        @DisplayName("Mini-market_TC#4.1.1_Изменение id продукта (ID=1)")
        @Description("Mini-market_TC#4.1.1_Изменение id продукта (ID=1)")
        @Link("http://localhost:8189/market/swagger-ui.html#/product-controller/modifyProductUsingPUT")
        @Issue("localhost")
        @Tag("modifyPriceProductInFoodCategoryTest")
        void modifyIdProductInFoodCategoryTest() throws IOException {
            // Запрос товара с ID=1:
            Response<GetProduct> response = productService.getProductById(1).execute();
            id = response.body().getId();               // сохранение ID продукта
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));
            String result = printResponse(response);

            // Пример сереализация тела ответа:
            ObjectMapper mapper = new ObjectMapper();
            StringReader reader = new StringReader(result);
            GetProduct productReader = mapper.readValue(reader, GetProduct.class);

            Integer newID = (int) (Math.random() * 1000);
            // Изменение price продукта:
            product = modify(newID,
                             productReader.getTitle(),
                             productReader.getPrice(),
                             productReader.getCategoryTitle());

            // Запрос на запись обновленных в базу данных (запрещено):
            response = productService.modifyProduct(product).execute();
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(false));

            // Получение объекта с обновлёнными данными (запрещено):
            response = productService.getProductById(newID).execute();
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(false));
        }

        @SneakyThrows
        @Test
        @DisplayName("Mini-market_TC#4.1.2_Изменение id продукта на одно и то же значение (ID=1)")
        @Description("Mini-market_TC#4.1.2_Изменение id продукта на одно и то же значение (ID=1)")
        @Link("http://localhost:8189/market/swagger-ui.html#/product-controller/modifyProductUsingPUT")
        @Issue("localhost")
        @Tag("modifyPriceProductInFoodCategoryTest")
        void modifyIdProductInFoodCategoryTest_() throws IOException {
            // Запрос товара с ID=1:
            Response<GetProduct> response = productService.getProductById(1).execute();
            id = response.body().getId();               // сохранение ID продукта
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));
            String result = printResponse(response);

            // Пример сереализация тела ответа:
            ObjectMapper mapper = new ObjectMapper();
            StringReader reader = new StringReader(result);
            GetProduct productReader = mapper.readValue(reader, GetProduct.class);

            // Изменение price продукта:
            product = modify(productReader.getId(),
                             productReader.getTitle(),
                             productReader.getPrice(),
                             productReader.getCategoryTitle());

            // Запрос на запись обновленных в базу данных (запрещено):
            response = productService.modifyProduct(product).execute();
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));

            // Получение объекта с обновлёнными данными (запрещено):
            response = productService.getProductById(id).execute();
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));
        }

        @SneakyThrows
        @Test
        @DisplayName("Mini-market_TC#4.1.3_Изменение цены продукта (ID=1)")
        @Description("Mini-market_TC#4.1.3_Изменение цены продукта (ID=1)")
        @Link("http://localhost:8189/market/swagger-ui.html#/product-controller/modifyProductUsingPUT")
        @Issue("localhost")
        @Tag("modifyPriceProductInFoodCategoryTest")
        void modifyPriceProductInFoodCategoryTest() throws IOException {
            // Запрос товара с ID=1:
            Response<GetProduct> response = productService.getProductById(1).execute();
            id = response.body().getId();               // сохранение ID продукта
            price = response.body().getPrice();         // сохранение цены продукта
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));
            String result = printResponse(response);

            // Пример сереализация тела ответа:
            ObjectMapper mapper = new ObjectMapper();
            StringReader reader = new StringReader(result);
            GetProduct productReader = mapper.readValue(reader, GetProduct.class);

            // Изменение price продукта:
            product = modify(productReader.getId(),
                             productReader.getTitle(),
                             (int) (Math.random() * 10000),
                             productReader.getCategoryTitle());

            // Запрос на запись обновленных в базу данных:
            response = productService.modifyProduct(product).execute();
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));

            // Получение объекта с обновлёнными данными:
            response = productService.getProductById(id).execute();
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));

            // Сравнение старой и новый цены:
            MatcherAssert.assertThat(response.body().getPrice(), not(price));
            System.out.println("Новая/старая цена: " + response.body().getPrice() + " / " + price + "\n\n");

            // Обновление поля цены:
            product = modify(productReader.getId(),
                             productReader.getTitle(),
                             price,
                             productReader.getCategoryTitle());

            // Запрос на запись обновленных в базу данных:
            response = productService.modifyProduct(product).execute();

            // Сравнение старой и новый цены:
            MatcherAssert.assertThat(response.body().getPrice(), equalTo(price));
            System.out.println("Новая/старая цена: " + response.body().getPrice() + " / " + price + "\n\n");
        }

        @SneakyThrows
        @Test
        @DisplayName("Mini-market_TC#4.1.4_Изменение заголовка продукта (ID=1)")
        @Description("Mini-market_TC#4.1.4_Изменение заголовка продукта (ID=1)")
        @Link("http://localhost:8189/market/swagger-ui.html#/product-controller/modifyProductUsingPUT")
        @Issue("localhost")
        @Tag("modifyTitleProductInFoodCategoryTest")
        void modifyTitleProductInFoodCategoryTest() throws IOException {
            // Запрос товара с ID=1:
            Response<GetProduct> response = productService.getProductById(1).execute();
            id = response.body().getId();               // сохранение ID продукта
            title = response.body().getTitle();         // сохранение Title продукта
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));
            String result = printResponse(response);

            // Пример сереализация тела ответа:
            ObjectMapper mapper = new ObjectMapper();
            StringReader reader = new StringReader(result);
            GetProduct productReader = mapper.readValue(reader, GetProduct.class);

            // Изменение price продукта:
            product = modify(productReader.getId(),
                    faker.food().ingredient(),
                    productReader.getPrice(),
                    productReader.getCategoryTitle());

            // Запрос на запись обновленных в базу данных:
            response = productService.modifyProduct(product).execute();
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));

            // Получение объекта с обновлёнными данными:
            response = productService.getProductById(id).execute();
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));

            // Сравнение старой и новый цены:
            MatcherAssert.assertThat(response.body().getTitle(), not(title));
            System.out.println("Новый/старый заголовок: " + response.body().getTitle() + " / " + title + "\n\n");

            // Обновление поля цены:
            product = modify(productReader.getId(),
                    title,
                    productReader.getPrice(),
                    productReader.getCategoryTitle());

            // Запрос на запись обновленных в базу данных:
            response = productService.modifyProduct(product).execute();

            // Сравнение старой и новый цены:
            MatcherAssert.assertThat(response.body().getTitle(), equalTo(title));
            System.out.println("Новый/старый заголовок: " + response.body().getTitle() + " / " + title + "\n\n");
        }

        @SneakyThrows
        @Test
        @DisplayName("Mini-market_TC#4.1.5_Изменение категории продукта (ID=1)")
        @Description("Mini-market_TC#4.1.5_Изменение категории продукта (ID=1)")
        @Link("http://localhost:8189/market/swagger-ui.html#/product-controller/modifyProductUsingPUT")
        @Issue("localhost")
        @Tag("modifyTitleProductInFoodCategoryTest")
        void modifyCategoryTitleProductInFoodCategoryTest() throws IOException {
            // Запрос товара с ID=1:
            Response<GetProduct> response = productService.getProductById(1).execute();
            id = response.body().getId();                           // сохранение ID продукта
            categoryTitle = response.body().getCategoryTitle();     // сохранение Category Title продукта
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));
            String result = printResponse(response);

            // Пример сереализация тела ответа:
            ObjectMapper mapper = new ObjectMapper();
            StringReader reader = new StringReader(result);
            GetProduct productReader = mapper.readValue(reader, GetProduct.class);

            // Изменение price продукта:
            product = modify(productReader.getId(),
                             productReader.getTitle(),
                             productReader.getPrice(),
                            "newCategory");

            // Запрос на запись обновленных в базу данных:
            response = productService.modifyProduct(product).execute();
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(false));

            // Получение объекта с обновлёнными данными:
            response = productService.getProductById(id).execute();
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));
        }
    }

    @Nested
    @DisplayName("4.2.Тесты для добавления и удаления записей по категориям (Electronic)")
    class ModifyItemInElectronic {

        @SneakyThrows
        @Test
        @DisplayName("Mini-market_TC#4.2.1_Изменение id продукта (ID=4)")
        @Description("Mini-market_TC#4.2.1_Изменение id продукта (ID=4)")
        @Link("http://localhost:8189/market/swagger-ui.html#/product-controller/modifyProductUsingPUT")
        @Issue("localhost")
        @Tag("modifyPriceProductInElectronicCategoryTest")
        void modifyIdProductInElectronicCategoryTest() throws IOException {
            // Запрос товара с ID=4:
            Response<GetProduct> response = productService.getProductById(4).execute();
            id = response.body().getId();               // сохранение ID продукта
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));
            String result = printResponse(response);

            // Пример сереализация тела ответа:
            ObjectMapper mapper = new ObjectMapper();
            StringReader reader = new StringReader(result);
            GetProduct productReader = mapper.readValue(reader, GetProduct.class);

            Integer newID = (int) (Math.random() * 1000);
            // Изменение price продукта:
            product = modify(newID,
                    productReader.getTitle(),
                    productReader.getPrice(),
                    productReader.getCategoryTitle());

            // Запрос на запись обновленных в базу данных (запрещено):
            response = productService.modifyProduct(product).execute();
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(false));

            // Получение объекта с обновлёнными данными (запрещено):
            response = productService.getProductById(newID).execute();
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(false));
        }

        @SneakyThrows
        @Test
        @DisplayName("Mini-market_TC#4.2.2_Изменение id продукта на одно и то же значение (ID=4)")
        @Description("Mini-market_TC#4.2.2_Изменение id продукта на одно и то же значение (ID=4)")
        @Link("http://localhost:8189/market/swagger-ui.html#/product-controller/modifyProductUsingPUT")
        @Issue("localhost")
        @Tag("modifyIdProductInElectronicCategoryTest2")
        void modifyIdProductInElectronicCategoryTest2() throws IOException {
            // Запрос товара с ID=4:
            Response<GetProduct> response = productService.getProductById(4).execute();
            id = response.body().getId();               // сохранение ID продукта
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));
            String result = printResponse(response);

            // Пример сереализация тела ответа:
            ObjectMapper mapper = new ObjectMapper();
            StringReader reader = new StringReader(result);
            GetProduct productReader = mapper.readValue(reader, GetProduct.class);

            // Изменение price продукта:
            product = modify(productReader.getId(),
                    productReader.getTitle(),
                    productReader.getPrice(),
                    productReader.getCategoryTitle());

            // Запрос на запись обновленных в базу данных (запрещено):
            response = productService.modifyProduct(product).execute();
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));

            // Получение объекта с обновлёнными данными (запрещено):
            response = productService.getProductById(id).execute();
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));
        }

        @SneakyThrows
        @Test
        @DisplayName("Mini-market_TC#4.2.3_Изменение цены продукта (ID=4)")
        @Description("Mini-market_TC#4.2.3_Изменение цены продукта (ID=4)")
        @Link("http://localhost:8189/market/swagger-ui.html#/product-controller/modifyProductUsingPUT")
        @Issue("localhost")
        @Tag("modifyPriceProductInElectronicCategoryTest")
        void modifyPriceProductInElectronicCategoryTest() throws IOException {
            // Запрос товара с ID=4:
            Response<GetProduct> response = productService.getProductById(4).execute();
            id = response.body().getId();               // сохранение ID продукта
            price = response.body().getPrice();         // сохранение цены продукта
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));
            String result = printResponse(response);

            // Пример сереализация тела ответа:
            ObjectMapper mapper = new ObjectMapper();
            StringReader reader = new StringReader(result);
            GetProduct productReader = mapper.readValue(reader, GetProduct.class);

            // Изменение price продукта:
            product = modify(productReader.getId(),
                    productReader.getTitle(),
                    (int) (Math.random() * 10000),
                    productReader.getCategoryTitle());

            // Запрос на запись обновленных в базу данных:
            response = productService.modifyProduct(product).execute();
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));

            // Получение объекта с обновлёнными данными:
            response = productService.getProductById(id).execute();
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));

            // Сравнение старой и новый цены:
            MatcherAssert.assertThat(response.body().getPrice(), not(price));
            System.out.println("Новая/старая цена: " + response.body().getPrice() + " / " + price + "\n\n");

            // Обновление поля цены:
            product = modify(productReader.getId(),
                    productReader.getTitle(),
                    price,
                    productReader.getCategoryTitle());

            // Запрос на запись обновленных в базу данных:
            response = productService.modifyProduct(product).execute();

            // Сравнение старой и новый цены:
            MatcherAssert.assertThat(response.body().getPrice(), equalTo(price));
            System.out.println("Новая/старая цена: " + response.body().getPrice() + " / " + price + "\n\n");
        }

        @SneakyThrows
        @Test
        @DisplayName("Mini-market_TC#4.2.4_Изменение заголовка продукта (ID=4)")
        @Description("Mini-market_TC#4.2.4_Изменение заголовка продукта (ID=4)")
        @Link("http://localhost:8189/market/swagger-ui.html#/product-controller/modifyProductUsingPUT")
        @Issue("localhost")
        @Tag("modifyTitleProductInElectronicCategoryTest")
        void modifyTitleProductInElectronicCategoryTest() throws IOException {
            // Запрос товара с ID=4:
            Response<GetProduct> response = productService.getProductById(4).execute();
            id = response.body().getId();               // сохранение ID продукта
            title = response.body().getTitle();         // сохранение Title продукта
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));
            String result = printResponse(response);

            // Пример сереализация тела ответа:
            ObjectMapper mapper = new ObjectMapper();
            StringReader reader = new StringReader(result);
            GetProduct productReader = mapper.readValue(reader, GetProduct.class);

            // Изменение price продукта:
            product = modify(productReader.getId(),
                    faker.food().ingredient(),
                    productReader.getPrice(),
                    productReader.getCategoryTitle());

            // Запрос на запись обновленных в базу данных:
            response = productService.modifyProduct(product).execute();
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));

            // Получение объекта с обновлёнными данными:
            response = productService.getProductById(id).execute();
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));

            // Сравнение старой и новый цены:
            MatcherAssert.assertThat(response.body().getTitle(), not(title));
            System.out.println("Новый/старый заголовок: " + response.body().getTitle() + " / " + title + "\n\n");

            // Обновление поля цены:
            product = modify(productReader.getId(),
                    title,
                    productReader.getPrice(),
                    productReader.getCategoryTitle());

            // Запрос на запись обновленных в базу данных:
            response = productService.modifyProduct(product).execute();

            // Сравнение старой и новый цены:
            MatcherAssert.assertThat(response.body().getTitle(), equalTo(title));
            System.out.println("Новый/старый заголовок: " + response.body().getTitle() + " / " + title + "\n\n");
        }

        @SneakyThrows
        @Test
        @DisplayName("Mini-market_TC#4.2.5_Изменение категории продукта (ID=4)")
        @Description("Mini-market_TC#4.2.5_Изменение категории продукта (ID=4)")
        @Link("http://localhost:8189/market/swagger-ui.html#/product-controller/modifyProductUsingPUT")
        @Issue("localhost")
        @Tag("modifyCategoryTitleProductInElectronicCategoryTest")
        void modifyCategoryTitleProductInElectronicCategoryTest() throws IOException {
            // Запрос товара с ID=1:
            Response<GetProduct> response = productService.getProductById(4).execute();
            id = response.body().getId();                           // сохранение ID продукта
            categoryTitle = response.body().getCategoryTitle();     // сохранение Category Title продукта
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));
            String result = printResponse(response);

            // Пример сереализация тела ответа:
            ObjectMapper mapper = new ObjectMapper();
            StringReader reader = new StringReader(result);
            GetProduct productReader = mapper.readValue(reader, GetProduct.class);

            // Изменение price продукта:
            product = modify(productReader.getId(),
                    productReader.getTitle(),
                    productReader.getPrice(),
                    "newCategory");

            // Запрос на запись обновленных в базу данных:
            response = productService.modifyProduct(product).execute();
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(false));

            // Получение объекта с обновлёнными данными:
            response = productService.getProductById(id).execute();
            MatcherAssert.assertThat(response.isSuccessful(), CoreMatchers.is(true));
        }
    }
}
