package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.io.IOException;
import java.io.StringWriter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class GetProduct {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("price")
    private Integer price;
    @JsonProperty("categoryTitle")
    private String categoryTitle;
}


