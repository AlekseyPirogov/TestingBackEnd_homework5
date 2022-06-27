package org.example.api;

import org.example.dto.GetCategoryResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface CategoryControllerService {

    // Каждый метод интерфейса представляет собой один возможный вызов API.
    // Он должен иметь HTTP: GET, POST и т.д. Главное - указать тип запроса
    // и относительный URL. Возвращаемое значение помещает ответ в объект
    // Call с типом возвращаемого результат:
    @GET("categories/{id}")
    // Обычно используются замещающие блоки и параметры запроса для настройки
    // URI-адреса. Замещающие блоки добавляются к относительному URL путём применения {}
    Call<GetCategoryResponse> getCategory(@Path("id") int id);
    // Через аннотацию @Path к параметру метода значение этого параметра
    // привязывается к конкретному блоку замены.
}
