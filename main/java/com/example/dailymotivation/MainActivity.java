package com.example.dailymotivation;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dailymotivation.databinding.ActivityMainBinding;
import com.example.dailymotivation.model.QuoteResponse;
import com.example.dailymotivation.repository.QuoteRepository;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private QuoteRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = new QuoteRepository();

        binding.btnNewQuote.setOnClickListener(v -> fetchQuote());
        fetchQuote();
    }

    private void fetchQuote() {
        showLoading(true);
        repository.getRandomQuote(new QuoteRepository.QuoteCallback() {
            @Override
            public void onSuccess(QuoteResponse quote) {
                runOnUiThread(() -> {
                    showLoading(false);
                    binding.tvQuote.setText(quote.getQ());
                    binding.tvAuthor.setText("- " + quote.getA());
                });
            }

            @Override
            public void onFailure(Throwable t) {
                runOnUiThread(() -> {
                    showLoading(false);
                    Toast.makeText(MainActivity.this, "Error al obtener frase", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void showLoading(boolean show) {
        binding.progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        binding.btnNewQuote.setEnabled(!show);
    }
}