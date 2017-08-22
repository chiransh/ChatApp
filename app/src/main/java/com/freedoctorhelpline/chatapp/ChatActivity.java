package com.freedoctorhelpline.chatapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.pubnub.api.Callback;
import com.pubnub.api.Pubnub;
import com.pubnub.api.PubnubError;
import com.pubnub.api.PubnubException;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "ChatFragment";
    SharedPreferences sharedPreferences;
    CustomCallback callback;
    Context context;
    Pubnub pubnub;
    EditText chatMessage;
    String brand;
    Button send;
    DatabaseReference mDatabase;
    RecyclerView chatList;
    ArrayList<String> chatMessageList;
    ChatAdaptor chatAdapter;
    Gson gson;
    int count;
    HashMap<String, java.util.ArrayList<java.lang.String> > map = new HashMap<String, java.util.ArrayList<java.lang.String>>();
    JSONObject messageObject;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        context = getApplicationContext();
        sharedPreferences = context.getSharedPreferences("details", Context.MODE_PRIVATE);

        username = sharedPreferences.getString("username", null);
        setTitle(username);

        chatMessageList = new ArrayList<>();
        gson = new Gson();
        chatAdapter = new ChatAdaptor(chatMessageList, username);

        chatList = (RecyclerView) findViewById(R.id.chatlist);
        chatList.setLayoutManager(new LinearLayoutManager(context));
        chatList.setAdapter(chatAdapter);

        chatMessage = (EditText) findViewById(R.id.message);
        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(this);

        Intent intent = getIntent();
        ArrayList agent=new ArrayList();
        ArrayList me=new ArrayList();
        agent=intent.getStringArrayListExtra("agent");
        Log.w("Abcd","chatAgent"+agent.toString());
        me=intent.getStringArrayListExtra("me");
        Log.w("Abcd","chatme"+me.toString());
        for(int i=0;i<me.size();i++) {
            String a=me.get(i).toString();
            String b=a.substring(0,2);
            if(b.equals("me")){
                String c=a.substring(2);
                count=Integer.parseInt(c);

            }
        }
        for(int i=0;i<agent.size();i++){
            chatMessageList.add(agent.get(i).toString());
        }
        String mobile = intent.getStringExtra("mobile");
        brand = intent.getStringExtra("brand");
        Log.w("Abc",brand);
        mDatabase= FirebaseDatabase.getInstance().getReference().child(username+"_"+brand);
        pubnub = new Pubnub(PubnubKeys.PUBLISH_KEY, PubnubKeys.SUBSCRIBE_KEY);
//        pubnub.addListener(new SubscribeCallback() {
//            @Override
//            public void message(PubNub pubnub , PNMessageResult message) {
//                System.out.println(message);
//            }
//        });
        try {
            pubnub.subscribe(brand, new Callback() {
                @Override
                public void successCallback(String channel, Object message) {
                    super.successCallback(channel, message);
                    chatMessageList.add(message.toString());
                    Log.w("Abcd","CallBack"+message);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            chatAdapter.notifyItemInserted(chatMessageList.size() - 1);
                            Log.w("Abc", String.valueOf(chatMessageList.size()));
                            chatList.scrollToPosition(chatMessageList.size() - 1);
                        }
                    });
                    Log.d("Abc", "message " + message);
                }

                @Override
                public void successCallback(String channel, Object message, String timetoken) {
                    super.successCallback(channel, message, timetoken);
                    Log.w("Abcd","callback message  "+message.toString());
                }

                @Override
                public void errorCallback(String channel, PubnubError error) {
                    super.errorCallback(channel, error);
                    Log.d("Abc", "error " + error);
                }

                @Override
                public void connectCallback(String channel, Object message) {
                    super.connectCallback(channel, message);
                    Log.d("connectCallback", "message " + message);
                }

                @Override
                public void reconnectCallback(String channel, Object message) {
                    super.reconnectCallback(channel, message);
                    Log.d("reconnectCallback", "message " + message);
                }

                @Override
                public void disconnectCallback(String channel, Object message) {
                    super.disconnectCallback(channel, message);
                    Log.d("disconnectCallback", "message " + message);
                }
            });
        } catch (PubnubException pe) {
            Log.d(TAG, pe.toString());
        }
    }

    @Override
    public void onClick(View view) {
        if (send.getId() == view.getId()) {
            String message = chatMessage.getText().toString().trim();
            int c=count+1;
            mDatabase.child("me"+String.valueOf(c)).setValue(message);
            if (message.length() != 0) {
//                message = gson.toJson(new Message(username, message));
//                try {
//                    messageObject = new JSONObject(message);
//                } catch (JSONException je) {
//                    Log.d(TAG, je.toString());
//                }
                //chatMessageList.add(message);
                Log.w("Abcd","chatMessageList.add()"+message);
                count++;
                chatMessage.setText("");
//                PNConfiguration pnc = new PNConfiguration().setSubscribeKey(PubnubKeys.SUBSCRIBE_KEY).setPublishKey(PubnubKeys.PUBLISH_KEY);
//                PubNub pubnub = new PubNub(pnc);
//                pubnub.addListener(new SubscribeCallback() {
//                    @Override
//                    public void status(PubNub pubnub, PNStatus status) {
//
//                    }
//
//                    @Override
//                    public void message(PubNub pubnub, PNMessageResult message) {
//                        System.out.println(message);
//                    }
//
//                    @Override
//                    public void presence(PubNub pubnub, PNPresenceEventResult presence) {
//
//                    }
//                });
//                pubnub.subscribe().channels(Arrays.asList("Rebbok","Nike")).execute();
//                Map message2 = new HashMap();
//                message2.put("Hello","world");
//                pubnub.publish()
//                        .channel(brand)
//                        .message(messageObject)
//                        .async(new PNCallback<PNPublishResult>() {
//                            @Override
//                            public void onResponse(PNPublishResult result, PNStatus status) {
//                                if (status.isError()) {
//                                    Log.w("Abc", String.valueOf(status));
//                                } else {
//                                    Log.w("Abc","Published!");
//                                }
//                            }
//                        });
                pubnub.publish(brand, message, new Callback() {
                    @Override
                    public void successCallback(String channel, Object message) {
                        super.successCallback(channel, message);
                        Log.d("successCallback", "message " + message);
                    }

                    @Override
                    public void errorCallback(String channel, PubnubError error) {
                        super.errorCallback(channel, error);
                        Log.d("errorCallback", "error " + error);
                    }
                });
            } else {
                Toast.makeText(context, "Please enter message", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "Un subscribed");
    }
}
