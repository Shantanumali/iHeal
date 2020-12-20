package com.example.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class updatepassword extends AppCompatActivity {

    private Button updatebtn;
    private EditText newpassword;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=!])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{6,}" +               //at least 6 characters
                    "$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatepassword);
        updatebtn = (Button)findViewById(R.id.button2);
        newpassword = (EditText)findViewById(R.id.editText4);



        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String UserPasswordNew = newpassword.getText().toString();
                if (TextUtils.isEmpty(UserPasswordNew)) {
                    Toast.makeText(updatepassword.this,"Please enter New Password",Toast.LENGTH_LONG).show();
                    return;
                } else {
                    if(!validatePassword()){
                        return;
                    }else {
                        firebaseUser.updatePassword(UserPasswordNew).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(updatepassword.this, "Password Changed", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(updatepassword.this, "Password Could Not Be Changed", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
                    }
                }
            }
        });

    }

    private boolean validatePassword() {
        String passwordInput = newpassword.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            newpassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            newpassword.setError("Password too weak "+
                    "Password Should Contain At Least:"+
                    "1 Character, " +
                    "1 Digit, " +
                    "1 Special Character, " +
                    "And Length:6");
            return false;
        } else {
            newpassword.setError(null);
            return true;
        }
    }


}
