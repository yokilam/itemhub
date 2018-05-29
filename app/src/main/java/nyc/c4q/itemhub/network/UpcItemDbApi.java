package nyc.c4q.itemhub.network;

import nyc.c4q.itemhub.model.ProductSearchResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UpcItemDbApi {

    @GET("lookup")
    Call<ProductSearchResult> getProductSearchResult(@Query("upc") long upc);
}
