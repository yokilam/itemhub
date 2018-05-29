package nyc.c4q.itemhub.network;

import android.support.annotation.NonNull;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpcService {
    public static final String BASE_URL= "https://api.upcitemdb.com/prod/trial/";

    private final Retrofit retrofit;

    public UpcService() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @NonNull
    public UpcItemDbApi getUpcItemDbApi() {
        return retrofit.create(UpcItemDbApi.class);
    }
}
