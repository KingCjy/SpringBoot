package com.kingcjy.main.component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

//@Component
public class PushUtil {

    @Value("firebaseKey")
    private String firebaseKey;

    @Value("firebaseDatabaseUrl")
    private String firebaseDatabaseUrl;

    public PushUtil() {
        try {
            InputStream is = this.getClass().getClassLoader().getResource(firebaseKey).openStream();

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(is))
                    .setDatabaseUrl(firebaseDatabaseUrl)
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
