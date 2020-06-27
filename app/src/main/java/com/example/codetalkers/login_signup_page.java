package com.example.codetalkers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class login_signup_page extends AppCompatActivity {
    EditText user_email_id,edt_username,edt_password;
    Button user_signup_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup_page);
        user_email_id=findViewById(R.id.edt_user_email_id);
        edt_username=findViewById(R.id.edt_username);
        edt_password=findViewById(R.id.edt_password);
        user_signup_button=findViewById(R.id.user_signup_button);

    }
    public void userLogin(View view){

        Intent intent=new Intent();
        intent.setClass(this,user_login_page.class);
        startActivity(intent);
    }
    public void userSignup(View btnUserSignup){

        try {
            // Reset errors
//    <Insert User Email Here>.setError(null);
//    <Insert User Password Here>.setError(null);

            // Sign up with Parse
            ParseUser user = new ParseUser();
            user.setUsername(edt_username.getText().toString());
            user.setPassword(edt_password.getText().toString());
            user.setEmail(user_email_id.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        ParseUser.logOut();
                        alertDisplayer("Account Created Successfully!", "Please verify your email before Login", false);
                    } else {
                        ParseUser.logOut();
                        alertDisplayer("Error Account Creation failed", "Account could not be created" + " :" + e.getMessage(), true);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    private void alertDisplayer(String title,String message, final boolean error){
        AlertDialog.Builder builder = new AlertDialog.Builder(login_signup_page.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if(!error) {
                            Intent intent = new Intent(login_signup_page.this,user_login_page.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }



}
