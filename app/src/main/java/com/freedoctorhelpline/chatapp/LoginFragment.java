package com.freedoctorhelpline.chatapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginFragment extends Fragment implements View.OnClickListener{
    Button loginButton;
    EditText usernameEdit;
    static SharedPreferences sharedPreferences;
    Context context;
    private DatabaseReference mDatabase;
    CustomCallback callback;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_login, container, false);
        context=view.getContext();
        usernameEdit = (EditText) view.findViewById(R.id.username);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        loginButton = (Button) view.findViewById(R.id.login);
        loginButton.setOnClickListener(this);
        callback = (CustomCallback) context;
        sharedPreferences = context.getSharedPreferences("details", Context.MODE_PRIVATE);

        getActivity().setTitle("Login");
        return view;
    }

    @Override
    public void onClick(View v) {
        String username = usernameEdit.getText().toString().trim();
        Intent intent=new Intent(context,ChatActivity.class);
        intent.putExtra("mobile",username);
        if (loginButton.getId() == v.getId()) {
            if (username.length() != 0) {
                callback.loginActivity(0);
                sharedPreferences.edit().putString("username", username).apply();
            } else {
                Toast.makeText(context, "Enter username", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
