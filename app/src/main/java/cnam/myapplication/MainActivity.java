package cnam.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xee.sdk.core.auth.AuthenticationCallback;

import org.jetbrains.annotations.NotNull;

import cnam.myapplication.Classes.ApiService;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ApiService.getInstance().getXenv() == null) {
            ApiService.getInstance().initEnv(this);
        }
    }

    public void logIn(View v) {
        ApiService.getInstance().getXeau().connect(new AuthenticationCallback() {
            @Override
            public void onError(@NotNull Throwable throwable) {
                Log.i("RES-Errreur", throwable.getMessage());
            }
            @Override
            public void onSuccess() {
                Log.i("RES-Ok", "Utilisateur connecté");
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
            }
        });
    }
}

