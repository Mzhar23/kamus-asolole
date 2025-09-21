package com.example.kamus;

import com.example.kamus.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kamus.R;

public class MainActivity extends AppCompatActivity {

    private EditText etSearchWord;
    private Button btnSearch;
    private TextView tvWord, tvDefinition, tvSynonyms, tvExample, tvError;
    private ProgressBar pbLoading;
    private Spinner spinnerLanguage;

    private String selectedLanguage = "Bahasa Inggris"; // default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi semua komponen UI
        initViews();

        // Setup Spinner bahasa
        setupSpinner();

        // Setup listener tombol cari
        btnSearch.setOnClickListener(v -> handleSearch());
    }

    private void initViews() {
        etSearchWord = findViewById(R.id.etSearchWord);
        btnSearch = findViewById(R.id.btnSearch);
        tvWord = findViewById(R.id.tvWord);
        tvDefinition = findViewById(R.id.tvDefinition);
        tvSynonyms = findViewById(R.id.tvSynonyms);
        tvExample = findViewById(R.id.tvExample);
        tvError = findViewById(R.id.tvError);
        pbLoading = findViewById(R.id.pbLoading);
        spinnerLanguage = findViewById(R.id.spinnerLanguage);
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.language_options,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLanguage.setAdapter(adapter);

        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedLanguage = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void handleSearch() {
        String word = etSearchWord.getText().toString().trim();

        if (word.isEmpty()) {
            Toast.makeText(this, "Masukkan kata terlebih dahulu!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tampilkan loading
        pbLoading.setVisibility(View.VISIBLE);
        hideAllResults();
        tvError.setVisibility(View.GONE);

        // Simulasi delay 2 detik (nanti diganti dengan API call)
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            pbLoading.setVisibility(View.GONE);

            // Contoh hasil dummy — nanti diganti dengan data dari API
            if (word.equalsIgnoreCase("hello") || word.equalsIgnoreCase("halo")) {
                showResult(
                        word,
                        "Ucapan untuk menyapa seseorang.",
                        "hi, hai, halo",
                        "Hello, how are you today?"
                );
            } else {
                showError("Kata \"" + word + "\" tidak ditemukan dalam kamus.");
            }
        }, 2000);
    }

    private void showResult(String word, String definition, String synonyms, String example) {
        tvWord.setText("Kata: " + word);
        tvDefinition.setText("Definisi: " + definition);
        tvSynonyms.setText("Sinonim: " + synonyms);
        tvExample.setText("Contoh: " + example);

        tvWord.setVisibility(View.VISIBLE);
        tvDefinition.setVisibility(View.VISIBLE);
        tvSynonyms.setVisibility(View.VISIBLE);
        tvExample.setVisibility(View.VISIBLE);
    }

    private void showError(String message) {
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
    }

    private void hideAllResults() {
        tvWord.setVisibility(View.GONE);
        tvDefinition.setVisibility(View.GONE);
        tvSynonyms.setVisibility(View.GONE);
        tvExample.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
    }
}
