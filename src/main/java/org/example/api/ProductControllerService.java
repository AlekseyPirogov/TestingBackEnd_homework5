package org.example.api;

import okhttp3.ResponseBody;
import org.example.dto.GetProduct;
import retrofit2.Call;
import retrofit2.http.*;

public interface ProductControllerService {

    @POST("products")
    Call<GetProduct> createProduct(@Body GetProduct createProductRequest);

    @DELETE("products/{id}")
    Call<ResponseBody> deleteProduct(@Path("id") int id);

    @PUT("products")
    Call<GetProduct> modifyProduct(@Body GetProduct modifyProductRequest);

    @GET("products/{id}")
    Call<GetProduct> getProductById(@Path("id") int id);

    @GET("products")
    Call<ResponseBody> getProducts();
}
