package com.vijayrawatsan.easyproxy.demo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.vijayrawatsan.easyproxy.EasyProxy;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Ion.with(this).load("http://jsonplaceholder.typicode.com/posts/1").noCache().asJsonObject().setCallback(new FutureCallback<JsonObject>() {
            @Override
            public void onCompleted(Exception e, JsonObject result) {
                if(result != null) {
                    Toast.makeText(MainActivity.this, "SUCCESS : \n" +result.toString(), Toast.LENGTH_LONG).show();
                } else {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "EXCEPTION : \n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
