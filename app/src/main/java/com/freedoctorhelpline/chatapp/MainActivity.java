package com.freedoctorhelpline.chatapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.pubnub.api.PNConfiguration;

public class MainActivity extends AppCompatActivity implements CustomCallback{
    public static SharedPreferences sharedPreferences;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PNConfiguration pnConfiguration = new PNConfiguration();
        pnConfiguration.setSubscribeKey("sub-c-1841cae2-7ff9-11e7-bdc2-6e5eeb112503");
        pnConfiguration.setPublishKey("pub-c-89f2c4b0-ca91-4e0c-9e93-50fa66053e96");

        sharedPreferences = getSharedPreferences("details", MODE_PRIVATE);
        fragmentManager = getSupportFragmentManager();
    }
    @Override
    protected void onStart() {
        super.onStart();
        changeLogin();
    }

    public void changeLogin() {
        if (sharedPreferences.getString("username", null) == null) {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new LoginFragment(), "Login");
            fragmentTransaction.commit();
        } else {
            startActivity(new Intent(MainActivity.this,Brands.class));
        }
    }


    @Override
    public void loginActivity(int LOGIN_STATE) {
        if (LOGIN_STATE == 0) {
            startActivity(new Intent(MainActivity.this,Brands.class));
        } else {
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, new LoginFragment(), "Login");
            fragmentTransaction.commit();
        }
    }


}
