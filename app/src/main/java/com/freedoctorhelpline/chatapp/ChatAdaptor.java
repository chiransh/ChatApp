package com.freedoctorhelpline.chatapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by User on 8/13/2017.
 */

public class ChatAdaptor extends RecyclerView.Adapter<ChatAdaptor.MyViewHolder>{
    ArrayList<String> chatMessageList;
    Gson gson = new Gson();
    String message;
    String username;
    String myUsername;
    Context context;
    public ChatAdaptor(ArrayList<String> chatMessageList, String myUsername) {
        this.chatMessageList = chatMessageList;
        this.myUsername = myUsername;
    }
    @Override
    public ChatAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new MyViewHolder(LayoutInflater.from(context).inflate
                (R.layout.chat_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(ChatAdaptor.MyViewHolder holder, int position) {
        message = chatMessageList.get(position);

//        Message messageObject = gson.fromJson(message, Message.class);

//        username = messageObject.getUsername();
//        holder.username.setText(messageObject.getUsername());
        //if (!(username.equals(myUsername))) {
            holder.chatHolder.setBackgroundColor(context.getResources().getColor(R.color.blue_200));
        //}
        holder.message.setText(chatMessageList.get(position));
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout chatHolder;
        TextView username;
        TextView message;

        public MyViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);
            message = (TextView) itemView.findViewById(R.id.message);
            chatHolder = (LinearLayout) itemView.findViewById(R.id.chatholder);
        }
    }

}
