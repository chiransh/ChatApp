package com.freedoctorhelpline.chatapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import static android.content.ContentValues.TAG;

/**
 * Created by User on 8/13/2017.
 */

public class RecyclerBrands extends RecyclerView.Adapter<RecyclerBrands.MyHolder> {
    ArrayList brand_name;
    ArrayList brand_icon;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;
    DatabaseReference mDatabase;
    SharedPreferences sharedPreferences;
    View view;
    Context context;
    public RecyclerBrands(ArrayList brand_name, ArrayList brand_icon) {
        this.brand_icon=brand_icon;
        this.brand_name=brand_name;
    }

    @Override
    public RecyclerBrands.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_brands,parent,false);
        context=view.getContext();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerBrands.MyHolder holder, final int position) {
        holder.brandName.setText(brand_name.get(position).toString());
        holder.brandsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent=new Intent(context,ChatActivity.class);
                final ProgressDialog pDialog = new ProgressDialog(view.getContext());
                pDialog.setMessage("Loading...");
                pDialog.show();
                sharedPreferences = context.getSharedPreferences("details", Context.MODE_PRIVATE);
                final String username=sharedPreferences.getString("username", null);
                final ArrayList <String> agent=new ArrayList<String>();
                final ArrayList <String> me=new ArrayList<String>();
                final ArrayList <Integer> count=new ArrayList<Integer>();
                String url=PubnubKeys.BASE_URL+username+"_"+brand_name.get(position).toString()+".json";
                Log.w("RecyclerBrands","url="+url);

                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                        url, null,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, response.toString());
                                if(response!=null){
                                    Log.w("response",response.toString());
                                    Iterator<?> keys = response.keys();
                                    while( keys.hasNext() ) {
                                        String key = (String) keys.next();
                                        Log.w("Abcd","keys"+key);
                                            agent.add(response.optString(key));
                                            me.add(key);
                                        Log.w("Abcd",response.optString(key));
                                    }
                                    Log.w("Abcd","me"+me);
                                    Log.w("Abcd","agent"+agent);
                                }
                                else
                                    mDatabase.child(username+"_"+brand_name.get(position).toString()).setValue(null);
                                pDialog.hide();
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        // hide the progress dialog
                        pDialog.hide();
                    }
                });


// Adding request to request queue
                AppController.getInstance(context).addToRequestQueue(jsonObjReq);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Do something after 100ms
                        intent.putExtra("agent",agent);
                        intent.putExtra("me",me);
                        intent.putExtra("brand",brand_name.get(position).toString());
                        context.startActivity(intent);
                    }
                }, 3000);


            }
        });
    }

    @Override
    public int getItemCount() {
        return brand_name.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView brandIcon;
        TextView brandName;
        LinearLayout brandsLayout;
        public MyHolder(View itemView) {
            super(itemView);
            brandIcon=(ImageView)itemView.findViewById(R.id.brandimg);
            brandName=(TextView)itemView.findViewById(R.id.brandname);
            brandsLayout=(LinearLayout)itemView.findViewById(R.id.brands_layout);
        }
    }
}
