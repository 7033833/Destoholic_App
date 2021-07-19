package com.example.destoholicstudent.adpter;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.destoholicstudent.R;
import com.example.destoholicstudent.model.Chat;

import java.util.ArrayList;
import java.util.Date;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private final ArrayList<Chat> chats;


    public ChatAdapter(final Context context,
                       final ArrayList<Chat> billSummaryReports) {
        this.chats = billSummaryReports;
        LayoutInflater mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Chat chat = chats.get(position);

        if (!TextUtils.isEmpty(chat.getMsg()))
            holder.txt_msg.setText(chat.getMsg());

        if (!TextUtils.isEmpty(chat.getName()))
            holder.txt_name.setText(chat.getName());

        if (chat.getDatetime() != 0) {
            String dateString = DateFormat.format("MMM dd, yyyy HH:mm", new Date(chat.getDatetime())).toString();
            holder.txt_date.setText(dateString);
        }

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView txt_date;
        private final TextView txt_msg;
        private final TextView txt_name;

        public MyViewHolder(@NonNull View view) {
            super(view);

            txt_date = view.findViewById(R.id.txt_date);
            txt_msg = view.findViewById(R.id.txt_msg);
            txt_name = view.findViewById(R.id.txt_name);

        }
    }
}