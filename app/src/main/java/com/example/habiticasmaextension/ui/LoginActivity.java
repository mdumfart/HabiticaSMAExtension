package com.example.habiticasmaextension.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.habiticasmaextension.R;
import com.example.habiticasmaextension.core.models.User;
import com.example.habiticasmaextension.core.proxy.HabiticaProxyFactory;
import com.example.habiticasmaextension.core.services.PreferencesServiceFactory;

import java.io.IOException;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUserId;
    private EditText edtApiToken;
    private Button btnAuthenticate;

    private String userId;
    private String apiToken;

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean loggingOut = getIntent().getBooleanExtra("loggingOut", false);

        if (!loggingOut){
            checkAuthentication();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUserId = findViewById(R.id.userId);
        edtApiToken = findViewById(R.id.apiToken);
        btnAuthenticate = findViewById(R.id.authenticate);

        userId = edtUserId.getText().toString();
        apiToken = edtApiToken.getText().toString();
        validateAuthentication();

        edtUserId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                userId = s.toString();
                validateAuthentication();
            }
        });

        edtApiToken.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                apiToken = s.toString();
                validateAuthentication();
            }
        });

        btnAuthenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticate(userId, apiToken, false);
            }
        });
    }

    private void checkAuthentication() {
        Context c = getApplicationContext();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(c);

        String savedUserId = PreferencesServiceFactory.createService().get("userId", c);
        String savedApiToken = PreferencesServiceFactory.createService().get("apiToken", c);

        if (!savedUserId.isEmpty() && !savedApiToken.isEmpty()){
            authenticate(savedUserId, savedApiToken, true);
        }
    }

    @SuppressWarnings("StaticFieldLeak")
    private void authenticate(String userId, String apiToken, boolean isSaved) {
        new AsyncTask<String, Void, User>() {

            @Override
            protected User doInBackground(String... strings) {
                try {
                    return HabiticaProxyFactory.createProxy(strings[0], strings[1]).getUserDetails();
                } catch (IOException e) {
                    Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
                return null;
            }

            @Override
            protected void onPostExecute(User user) {
                if (user != null) {

                    if (!isSaved){
                        setPreferences();
                    }

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("user", user);
                    LoginActivity.this.startActivity(intent);

                    setResult(Activity.RESULT_OK);
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Authentication not successful", Toast.LENGTH_LONG).show();
                }
            }
        }.execute(userId, apiToken);
    }

    private void setPreferences() {
        Context c = getApplicationContext();

        PreferencesServiceFactory.createService().put("userId", userId, c);
        PreferencesServiceFactory.createService().put("apiToken", apiToken, c);
    }

    private void validateAuthentication() {
        if (userId != null && apiToken != null){
            btnAuthenticate.setEnabled(userId.length() == 36 && apiToken.length() == 36);
        }
        else {
            btnAuthenticate.setEnabled(false);
        }
    }
}