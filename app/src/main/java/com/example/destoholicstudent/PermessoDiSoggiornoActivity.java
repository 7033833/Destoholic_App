package com.example.destoholicstudent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.example.destoholicstudent.retrofit.APIClient;
import com.example.destoholicstudent.retrofit.APIInterface;
import com.example.destoholicstudent.util.ConnectivityHelper;
import com.example.destoholicstudent.util.FileCreate;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PermessoDiSoggiornoActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txt_link1, txt_link2;
    TextView txt_file1;

    private ProgressDialog progressDialog;
    private final Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permesso_di_soggiorno);

        txt_link1 = findViewById(R.id.txt_link1);
        txt_link2 = findViewById(R.id.txt_link2);
        txt_file1 = findViewById(R.id.txt_file1);

        txt_link1.setOnClickListener(this);
        txt_link2.setOnClickListener(this);
        txt_file1.setOnClickListener(this);
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

        if (v.getId() == R.id.txt_file1){
            downloadFile();
        }
    }

    private void downloadFile() {

        if (ConnectivityHelper.isConnectingToInternet(getApplicationContext())) {

            showProgressDialog(PermessoDiSoggiornoActivity.this);
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<ResponseBody> call = apiInterface.getCodiceFiscale2();

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                    hideProgressDialog(0);
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            try {
                                FileCreate fileCreate = new FileCreate(PermessoDiSoggiornoActivity.this);
                                String appName = "PermessoDiSoggiorno";
                                String fileName = appName + ".pdf";

                                File file = new File(fileCreate.getMainfolder(), fileName);
                                file.createNewFile();
                                FileOutputStream fos = new FileOutputStream(file);
                                fos.write(response.body().bytes());
                                fos.flush();
                                fos.close();
                                try {

                                    Uri uri = FileProvider.getUriForFile(PermessoDiSoggiornoActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);
                                    //Uri uri = Uri.fromFile(file);

                                    /*Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setDataAndType(uri, "application/vnd.android.package-archive");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);*/

                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setDataAndType(uri,"application/pdf");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } else {

                    }
                }

                @Override
                public void onFailure(@NotNull Call<ResponseBody> call,
                                      @NotNull Throwable t) {
                    hideProgressDialog(0);
                }

            });

        } else {

        }

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