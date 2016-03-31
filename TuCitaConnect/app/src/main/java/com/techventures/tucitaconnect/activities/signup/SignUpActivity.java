package com.techventures.tucitaconnect.activities.signup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.techventures.tucitaconnect.R;
import com.techventures.tucitaconnect.activities.splash.SplashActivity;
import com.techventures.tucitaconnect.model.context.user.UserCompletion;
import com.techventures.tucitaconnect.model.context.user.UserContext;
import com.techventures.tucitaconnect.model.domain.user.User;
import com.techventures.tucitaconnect.model.error.AppError;
import com.techventures.tucitaconnect.utils.common.views.AppEditText;


public class SignUpActivity extends AppCompatActivity {

    private AppEditText emailView;
    private AppEditText nameView;
    private EditText passwordView;
    private UserContext userContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        userContext = UserContext.context(userContext);

        passwordView = (EditText) findViewById(R.id.password);

        emailView = (AppEditText) findViewById(R.id.email);

        nameView = (AppEditText) findViewById(R.id.name);

        Button signUpButton = (Button) findViewById(R.id.buttonSignUp);

        signUpButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                signup();

            }

        });

    }

    private void signup() {

        String email = emailView.getText().toString();

        String password = passwordView.getText().toString();

        String name = nameView.getText().toString();

        userContext.signup(email, password, name, new UserCompletion.UserErrorCompletion() {

            @Override
            public void completion(User user, AppError error) {

                if (error != null) {

                    Toast errorSignUp = Toast.makeText(SignUpActivity.this, R.string.error, Toast.LENGTH_SHORT);

                    errorSignUp.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);

                    errorSignUp.show();

                } else {

                    processLoggedUser();

                }

            }

        });

    }

    private void processLoggedUser() {

        SplashActivity.goToStart(getApplicationContext());

        finish();

    }

    private void processBack() {

        SplashActivity.goToStart(getApplicationContext());

        finish();

    }

    @Override
    public void onBackPressed() {

        processBack();

    }

}
