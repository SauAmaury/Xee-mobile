package cnam.myapplication.XeeDev;

import com.xee.sdk.core.auth.OAuth2Client;
import com.xee.sdk.core.auth.XeeAuth;
import com.xee.sdk.core.common.XeeEnv;

import java.util.Arrays;

import cnam.myapplication.BuildConfig;

public class XeeConnect {


    private OAuth2Client oAuthClient;

    private final String idUser = "324b3e00-2ccf-477e-833e-b1c819c6577f";
    private final String userKey = "b3707f5bd3ab8bc767fc7f9982acac85";
    private final String userSecret = "b0aeab4367810e479a4166c1f6668ccffcb1b0a492d8eb92a28699516c2f3751";


    private final String redirectUrl = "http://localhost";

    public XeeConnect(){
         this.oAuthClient = new OAuth2Client.Builder()
                .clientId(this.idUser)
                .clientSecret(this.userSecret)
                 .redirectUri(this.redirectUrl).scopes(Arrays.asList("vehicles.read"))
                .build();
    }

    public OAuth2Client getoAuthClient() {
        return oAuthClient;
    }


}
