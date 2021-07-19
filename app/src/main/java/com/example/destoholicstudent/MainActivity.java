package com.example.destoholicstudent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import static com.example.destoholicstudent.PrefHelper.KEY_IS_LOGIN;
import static com.example.destoholicstudent.PrefHelper.KEY_LOGIN_RESPONSE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout rl_Codice_Fiscale, rl_Permesso_Di_Soggiorno, rl_International_Desk,
            rl_Bank_Account, rl_Accomodation, rl_Erasmus, rl_Internship, rl_Job_Placement,
            rl_Chat_Box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        rl_Codice_Fiscale = findViewById(R.id.rl_Codice_Fiscale);
        rl_Permesso_Di_Soggiorno = findViewById(R.id.rl_Permesso_Di_Soggiorno);
        rl_International_Desk = findViewById(R.id.rl_International_Desk);
        rl_Bank_Account = findViewById(R.id.rl_Bank_Account);
        rl_Accomodation = findViewById(R.id.rl_Accomodation);
        rl_Erasmus = findViewById(R.id.rl_Erasmus);
        rl_Internship = findViewById(R.id.rl_Internship);
        rl_Job_Placement = findViewById(R.id.rl_Job_Placement);
        rl_Chat_Box = findViewById(R.id.rl_Chat_Box);

        rl_Codice_Fiscale.setOnClickListener(this);
        rl_Permesso_Di_Soggiorno.setOnClickListener(this);
        rl_International_Desk.setOnClickListener(this);
        rl_Bank_Account.setOnClickListener(this);
        rl_Accomodation.setOnClickListener(this);
        rl_Erasmus.setOnClickListener(this);
        rl_Internship.setOnClickListener(this);
        rl_Job_Placement.setOnClickListener(this);
        rl_Chat_Box.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_Codice_Fiscale) {
            Intent intent = new Intent(MainActivity.this, CodiceFiscaleActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.rl_Permesso_Di_Soggiorno) {
            Intent intent = new Intent(MainActivity.this, PermessoDiSoggiornoActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.rl_International_Desk) {
            Intent intent = new Intent(MainActivity.this, InternationalDeskActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.rl_Bank_Account) {
            Intent intent = new Intent(MainActivity.this, BankAccountActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.rl_Accomodation) {
            Intent intent = new Intent(MainActivity.this, AccomodationActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.rl_Erasmus) {
            Intent intent = new Intent(MainActivity.this, ErasmusActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.rl_Internship) {
            Intent intent = new Intent(MainActivity.this, InternshipActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.rl_Job_Placement) {
            Intent intent = new Intent(MainActivity.this, JobPlacementActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.rl_Chat_Box) {
            Intent intent = new Intent(MainActivity.this, ChatBoxActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            PrefHelper.setBoolean(KEY_IS_LOGIN, false, getApplicationContext());
            PrefHelper.setString(KEY_LOGIN_RESPONSE, null, getApplicationContext());
            Intent intent = new Intent(getApplicationContext(), SignInActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}