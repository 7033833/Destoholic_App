package com.example.destoholicstudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AccomodationActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txt_link1, txt_link2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accomodation);
        txt_link1 = findViewById(R.id.txt_link1);
        txt_link2 = findViewById(R.id.txt_link2);

        txt_link1.setOnClickListener(this);
        txt_link2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txt_link1){
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(txt_link1.getText().toString())) ;
            startActivity(i);
        }
        if (v.getId() == R.id.txt_link2){
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(txt_link2.getText().toString())) ;
            startActivity(i);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}