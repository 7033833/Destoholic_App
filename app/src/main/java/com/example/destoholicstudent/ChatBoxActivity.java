package com.example.destoholicstudent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.destoholicstudent.adpter.ChatAdapter;
import com.example.destoholicstudent.model.Chat;
import com.example.destoholicstudent.model.LoginRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.events.Event;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.destoholicstudent.PrefHelper.KEY_LOGIN_RESPONSE;

public class ChatBoxActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG = "ChatBoxActivity";
    private ArrayList<Chat> chatList = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    LoginRequest loginRequest;

    AppCompatEditText et_msg;
    AppCompatImageView iv_send;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        loginRequest = new Gson().fromJson(PrefHelper.getString(KEY_LOGIN_RESPONSE, null, getApplicationContext()), LoginRequest.class);


        myRef = database.getReference("chatbox");

        initView();
    }

    private void initView() {
        et_msg = findViewById(R.id.et_msg);
        iv_send = findViewById(R.id.iv_send);
        recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        iv_send.setOnClickListener(this);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatList.clear();
                for(DataSnapshot postSnapshot  : dataSnapshot.getChildren()) {
                    Chat chat = postSnapshot .getValue(Chat.class);
                    //here
                    chatList.add(chat);
                }
                Log.e(TAG, "list::::::::::"+chatList.toString());
                //Do what you need to do with your list
                ChatAdapter patientSearchAdapter = new ChatAdapter(getApplicationContext(), chatList);
                recyclerView.setAdapter(patientSearchAdapter);
                if (!chatList.isEmpty()){
                    recyclerView.smoothScrollToPosition(chatList.size());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        };
        myRef.addValueEventListener(valueEventListener);
    }


    private void addDocumentToCollection(String msg){
        long datetime = new Date().getTime();
        Chat chat = new Chat(msg, loginRequest.getUsername(), loginRequest.id, datetime);
        myRef.child(String.valueOf(datetime)).setValue(chat);
        et_msg.setText("");
        hideKeyboardFrom(getApplicationContext(), et_msg);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_send){
            if (!TextUtils.isEmpty(et_msg.getText()))
                addDocumentToCollection(et_msg.getText().toString());
        }
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}