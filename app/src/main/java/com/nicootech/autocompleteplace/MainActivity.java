package com.nicootech.autocompleteplace;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    TextView textView1, textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edit_text);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);

        // Initialize the SDK
        Places.initialize(getApplicationContext(),"");//put apiKey

        editText.setFocusable(false);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS
                ,Place.Field.LAT_LNG,Place.Field.NAME);

                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY
                ,fieldList).build(MainActivity.this);

                startActivityForResult(intent,100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode == RESULT_OK){

            Place place = Autocomplete.getPlaceFromIntent(data);

            editText.setText(place.getAddress());

            textView1.setText(String.format("Locality Name : %s", place.getName()));

            textView2.setText(String.valueOf(place.getLatLng()));
        }else if(resultCode == AutocompleteActivity.RESULT_ERROR){

            Status status = Autocomplete.getStatusFromIntent(data);

            Toast.makeText(getApplicationContext(),status.getStatusMessage()
                    ,Toast.LENGTH_SHORT).show();
            // do not forget Build APK

        }
    }
}