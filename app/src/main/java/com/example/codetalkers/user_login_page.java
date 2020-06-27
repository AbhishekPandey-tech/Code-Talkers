package com.example.codetalkers;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class user_login_page extends AppCompatActivity {
    private EditText edtLoginUsername , edtLoginPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_page);
        edtLoginUsername=findViewById(R.id.edt_user_email_id);
        edtLoginPassword=findViewById(R.id.edt_password);
    }
    public void userSignupPage(View view){
        Intent intent=new Intent();
        intent.setClass(this,login_signup_page.class);
        startActivity(intent);
    }
    public void loginIsPressed(View email_login) {


        // (<Insert User Password Here>).setError(null);

// Login with Parse
        ParseUser.logInInBackground( edtLoginUsername.getText().toString(),edtLoginPassword.getText().toString(),
                new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (parseUser != null) {
                            if (parseUser.getBoolean("emailVerified")) {
                                alertDisplayer("Login Sucessful", "Welcome, " + edtLoginUsername.getText() +"!", false);
                            } else {
                                ParseUser.logOut();
                                alertDisplayer("Login Fail", "Please Verify Your Email first", true);
                            }
                        } else {
                            ParseUser.logOut();
                            alertDisplayer("Login Fail", e.getMessage() + " Please re-try", true);
                        }
                    }
                });
    }
    private void alertDisplayer(String title,String message, final boolean error){
        AlertDialog.Builder builder = new AlertDialog.Builder(user_login_page.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if(!error) {
                            Intent intent = new Intent(user_login_page.this, afterLogin.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }
}
