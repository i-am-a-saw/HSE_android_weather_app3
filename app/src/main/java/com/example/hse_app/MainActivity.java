package com.example.hse_app;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Console;
import java.security.KeyException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int oldrandNumber, randNumber;
    Random r = new Random();
    private final String[] CITIES = new String[]{"Москва", "Нижний Новгород", "Казань", "Санкт-Петербург", "Калининград"};

    private AutoCompleteTextView textView;
    private TextView period_input;
    private Button search, new_activity;
    private RadioButton b1, b2, b3, b4, b5;
    private RadioGroup rg;

    private Dictionary<String, String> codes = new Hashtable<>();
    private Dictionary<String, String> periods = new Hashtable<>();

    private static final int R1 = 1001;
    private static final int R2 = 1002;
    private static final int R3 = 1003;
    private static final int R4 = 1004;
    private static final int R5 = 1005;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        codes.put("Санкт-Петербург", "saint-petersburg");
        codes.put("Нижний Новгород", "nizhny-novgorod");
        codes.put("Москва", "moscow");
        codes.put("Казань", "kazan");
        codes.put("Калининград", "kaliningrad");

        periods.put("today", "details/today");
        periods.put("tomorrow", "details/tomorrow");
        periods.put("3 days", "details/3-day-weather");
        periods.put("10 days", "details");
        periods.put("weekends", "details/weekend");


        textView = findViewById(R.id.inputEmail);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        rg = findViewById(R.id.group);

        b1.setId(R1);
        b2.setId(R2);
        b3.setId(R3);
        b4.setId(R4);
        b5.setId(R5);

        search = findViewById(R.id.get_forecast_button);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, CITIES);
        textView.setAdapter(adapter);

        textView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(arg1.getApplicationWindowToken(), 0);

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rg.getCheckedRadioButtonId() == -1) {
                } else {
                    String period = "";
                    switch (rg.getCheckedRadioButtonId()) {
                        case R1:
                            period = periods.get("today");
                            break;
                        case R2:
                            period = periods.get("tomorrow");
                            break;
                        case R3:
                            period = periods.get("3 days");
                            break;
                        case R4:
                            period = periods.get("10 days");
                            break;
                        case R5:
                            period = periods.get("weekends");
                            break;
                    }


                    String city = codes.get(textView.getText().toString());

                    String url = "https://yandex.ru/pogoda/ru/" + city + "/" + period;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getApplicationContext(), "Sorry, error occured :( You have no browser!", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });

//        randNumber = r.nextInt(99);
//        if (savedInstanceState != null) {
//            oldrandNumber = savedInstanceState.getInt("1");
//        }

    }

}