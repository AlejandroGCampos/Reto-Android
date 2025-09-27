package com.example.dailymotivation.repository;

import com.example.dailymotivation.model.QuoteResponse;
import com.example.dailymotivation.network.ZenQuotesApi;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuoteRepository {
    private final ZenQuotesApi api;

    public QuoteRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://zenquotes.io/") // URL base correcta
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(ZenQuotesApi.class);
    }

    public void getRandomQuote(final QuoteCallback callback) {
        api.getRandomQuote().enqueue(new Callback<List<QuoteResponse>>() {
            @Override
            public void onResponse(Call<List<QuoteResponse>> call, Response<List<QuoteResponse>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    callback.onSuccess(response.body().get(0));
                } else {
                    callback.onFailure(new Exception("Respuesta vacía o error de API"));
                }
            }

            @Override
            public void onFailure(Call<List<QuoteResponse>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public interface QuoteCallback {
        void onSuccess(QuoteResponse quote);
        void onFailure(Throwable t);
    }
}
