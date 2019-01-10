package cnam.myapplication;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.xee.sdk.api.model.User;

import cnam.myapplication.Classes.ApiService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ProfilActivity extends AppCompatActivity {

    /**
     * Appel a la création de la page Profil
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        this.getProfil();
    }


    /**
     * Gestion du profil
     */
    @SuppressLint("CheckResult")
    public void getProfil() {
        ApiService.getInstance().getXapi().getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new io.reactivex.functions.Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        ProfilActivity.this.addProfilToUi(user);
                    }
                }, new io.reactivex.functions.Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("FAIL", throwable.getMessage());

                    }
                });
    }

    /**
     * Ajoute sur la page, les informations de l'utilisateur
     *
     * @param user utilisateur de l'application
     */
    public void addProfilToUi(User user){
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(user.getLastName() +" "+ user.getFirstName());

        TextView email = (TextView) findViewById(R.id.email);
        email.setText(user.getEmail());

        TextView createDate = (TextView) findViewById(R.id.createDate);

        createDate.setText(ApiService.getInstance().getXapi().DATE_FORMATTER.format(user.getCreatedAt() ) );
    }
}
