package cnam.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.xee.sdk.api.XeeApi;
import com.xee.sdk.core.auth.AuthenticationCallback;
import com.xee.sdk.core.auth.XeeAuth;
import com.xee.sdk.core.common.XeeEnv;

import cnam.myapplication.XeeDev.XeeConnect;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XeeConnect xe = new XeeConnect();
        XeeEnv xeeEnv = new XeeEnv(this, xe.getoAuthClient());
        XeeApi xapi = new XeeApi(xeeEnv, true);
        XeeAuth xeau = new XeeAuth(xeeEnv, true);
       /* AuthenticationCallback authcall = new AuthenticationCallback() {
            @Override
            public void onError(Throwable throwable) {
                Log.i("Erreur",throwable.getMessage());
            }

            @Override
            public void onSuccess() {
                Log.i("ok","Connexion réussi !");
            }
        };
        xeau.connect(authcall);*/


    }
}
