package cnam.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.ListFormatter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.xee.sdk.api.model.Vehicle;
import com.xee.sdk.core.auth.DisconnectCallback;

import java.util.List;
import java.util.Observable;
import java.util.function.Consumer;

import cnam.myapplication.Classes.ApiService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MenuActivity extends AppCompatActivity {
    List<Vehicle> lve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void getVehicles(View v)
    {
        startActivity(new Intent(MenuActivity.this, VehicleActivity.class));
    }

    public void logOut(View v) {
        ApiService.getInstance().getXeau().disconnect(new DisconnectCallback() {
            @Override
            public void onCompleted() {
                startActivity(new Intent(MenuActivity.this, MainActivity.class));
            }
        });
    }

}
