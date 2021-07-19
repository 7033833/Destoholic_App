package com.example.destoholicstudent;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.destoholicstudent.model.LoginResponse;
import com.example.destoholicstudent.retrofit.APIClient;
import com.example.destoholicstudent.retrofit.APIInterface;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends Activity implements View.OnClickListener {

    RelativeLayout relativeLayout;
    TextInputEditText et_user_name, et_email, et_password, et_re_password;
    MaterialButton btn_signup;
    AppCompatTextView tv_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        relativeLayout = findViewById(R.id.relativeLayout);
        et_user_name = findViewById(R.id.et_user_name);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_re_password = findViewById(R.id.et_re_password);
        btn_signup = findViewById(R.id.btn_signup);
        tv_signin = findViewById(R.id.tv_signin);

        btn_signup.setOnClickListener(this);
        tv_signin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_signup){
            clickSignup();
        }
        if (v.getId() == R.id.tv_signin){
            clickSignIn();
        }
    }

    private void clickSignIn() {
        finish();
    }

    private void clickSignup() {
        if (TextUtils.isEmpty(et_user_name.getText().toString())){
            Snackbar.make(relativeLayout, "Please enter User name.", Snackbar.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(et_email.getText().toString())){
            Snackbar.make(relativeLayout, "Please enter Email.", Snackbar.LENGTH_SHORT).show();
        }else if (!isValidEmail(et_email.getText().toString())){
            Snackbar.make(relativeLayout, "Please enter valid Email.", Snackbar.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(et_password.getText().toString())){
            Snackbar.make(relativeLayout, "Please enter password.", Snackbar.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(et_re_password.getText().toString())){
            Snackbar.make(relativeLayout, "Please enter re-password.", Snackbar.LENGTH_SHORT).show();
        }else if (!et_password.getText().toString().equalsIgnoreCase(et_re_password.getText().toString())){
            Snackbar.make(relativeLayout, "password is not match..", Snackbar.LENGTH_SHORT).show();
        }else {
            loginRetrofit2Api(et_email.getText().toString(),
                    et_user_name.getText().toString(),
                    et_password.getText().toString());
        }
    }

    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void loginRetrofit2Api(String email, String username, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", email);
            jsonObject.put("username", username);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        Call<LoginResponse> call1 = apiInterface.createUser(body);
        call1.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (loginResponse == null){
                    return;
                }
                Log.e("Register", "registerResponse 1 --> " + loginResponse.toString());
                Snackbar.make(relativeLayout, loginResponse.message, Snackbar.LENGTH_SHORT).show();
                if (loginResponse != null && loginResponse.status){
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
//                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                Log.e("Register", "onFailure 1 --> " + t.getMessage());
                call.cancel();
            }
        });
    }
}