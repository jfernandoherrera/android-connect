package com.techventures.tucitaconnect.activities.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.techventures.tucitaconnect.R;
import com.techventures.tucitaconnect.activities.signup.SignUpActivity;
import com.techventures.tucitaconnect.activities.splash.SplashActivity;
import com.techventures.tucitaconnect.model.context.user.UserCompletion;
import com.techventures.tucitaconnect.model.context.user.UserContext;
import com.techventures.tucitaconnect.model.domain.user.User;
import com.techventures.tucitaconnect.model.error.AppError;
import com.parse.ParseFacebookUtils;
import com.techventures.tucitaconnect.utils.common.views.AppEditText;

public class LoginActivity extends AppCompatActivity {

    private AppEditText emailView;
    private EditText passwordView;
    private UserContext userContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        userContext = UserContext.context(userContext);

        overridePendingTransition(R.anim.login_animation_pull_in, R.anim.animation_hold);

        setContentView(R.layout.activity_login);

        emailView = (AppEditText) findViewById(R.id.email);

        passwordView = (EditText) findViewById(R.id.password);

    }

    public void close(View v){

        SplashActivity.goToStart(getApplicationContext());

    }

    public void login(View view) {

        String email = emailView.getText().toString();

        String password = passwordView.getText().toString();

        userContext.login(email, password, new UserCompletion.UserErrorCompletion() {

            @Override
            public void completion(User user, AppError error) {

                if (error != null) {

                    Toast errorLogin = Toast.makeText(LoginActivity.this, R.string.error, Toast.LENGTH_SHORT);

                    errorLogin.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);

                    errorLogin.show();

                } else {

                    SplashActivity.goToStart(getApplicationContext());

                }

            }

        });

    }

    public void loginWithFacebook(View view) {

        userContext.loginWithFacebook(this, new UserCompletion.UserErrorCompletion() {

            @Override
            public void completion(final User user, AppError error) {

                if (error != null) {

                    Toast errorLogin = Toast.makeText(LoginActivity.this, R.string.error, Toast.LENGTH_SHORT);

                    errorLogin.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);

                    errorLogin.show();

                } else {

                    SplashActivity.goToStart(getApplicationContext());

                }

            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);

    }

    public static void goToLogin(Context context) {

        Intent intent = new Intent(context, LoginActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);

    }

    public void signUp(View view) {

        Intent intent = new Intent(this, SignUpActivity.class);

        startActivity(intent);

        finish();

    }

    @Override
    public void onBackPressed() {

        SplashActivity.goToStart(getApplicationContext());

        super.onBackPressed();

    }

}
