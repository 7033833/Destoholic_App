package com.example.destoholicstudent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.destoholicstudent.model.LoginResponse;
import com.example.destoholicstudent.retrofit.APIClient;
import com.example.destoholicstudent.retrofit.APIInterface;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.destoholicstudent.PrefHelper.KEY_IS_LOGIN;
import static com.example.destoholicstudent.PrefHelper.KEY_LOGIN_RESPONSE;

public class SignInActivity extends Activity implements View.OnClickListener {

    RelativeLayout relativeLayout;
    TextInputEditText et_email, et_password;
    MaterialButton btn_signin;
    AppCompatTextView tv_signup;
    private ProgressDialog progressDialog;
    private final Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        if (PrefHelper.getBoolean(KEY_IS_LOGIN, false, getApplicationContext())){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            initView();
        }
    }

    private void initView() {

        relativeLayout = findViewById(R.id.relativeLayout);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        btn_signin = findViewById(R.id.btn_signin);
        tv_signup = findViewById(R.id.tv_signup);

        btn_signin.setOnClickListener(this);
        tv_signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_signin) {
            clickSignIn();
        }
        if (v.getId() == R.id.tv_signup) {
            clickSignup();
        }
    }

    private void clickSignup() {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }

    private void clickSignIn() {
        if (TextUtils.isEmpty(et_email.getText().toString())) {
            Snackbar.make(relativeLayout, "Please enter Email.", Snackbar.LENGTH_SHORT).show();
        } else if (!isValidEmail(et_email.getText().toString())) {
            Snackbar.make(relativeLayout, "Please enter valid Email.", Snackbar.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(et_password.getText().toString())) {
            Snackbar.make(relativeLayout, "Please enter password.", Snackbar.LENGTH_SHORT).show();
        } else {
            if (CommonMethod.isNetworkAvailable(SignInActivity.this))
                loginRetrofit2Api(et_email.getText().toString(), et_password.getText().toString());
            else
                CommonMethod.showAlert("Internet Connectivity Failure", SignInActivity.this);
        }
    }

    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void loginRetrofit2Api(String userId, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", userId);
            jsonObject.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        showProgressDialog(SignInActivity.this);
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        Call<LoginResponse> call1 = apiInterface.loginUser(body);
        call1.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (loginResponse == null){
                    return;
                }
                Snackbar.make(relativeLayout, loginResponse.message, Snackbar.LENGTH_SHORT).show();
                if (loginResponse != null && loginResponse.status) {
                    PrefHelper.setBoolean(KEY_IS_LOGIN, true, getApplicationContext());
                    PrefHelper.setString(KEY_LOGIN_RESPONSE, new Gson().toJson(loginResponse.data), getApplicationContext());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                hideProgressDialog(0);
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
//                Toast.makeText(getApplicationContext(), "onFailure called ", Toast.LENGTH_SHORT).show();
                Log.e("Login", "onFailure 1 --> " + t.getMessage());
                call.cancel();
                hideProgressDialog(0);
            }
        });
    }

    public void showProgressDialog(@Nullable final Activity context) {
        if (context != null) {
            context.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (progressDialog == null) {
                        progressDialog = new ProgressDialog(context);
                        progressDialog.setMessage("Please Wait...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                    } else if (!progressDialog.isShowing()) {
                        progressDialog.show();
                    }
                }
            });
        }
    }

    public void hideProgressDialog(final long delayResponseTime) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if ((progressDialog != null) && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            }
        }, delayResponseTime);
    }
}