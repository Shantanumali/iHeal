package com.example.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class logindoc extends AppCompatActivity implements View.OnClickListener {
    private Button buttonlogin;
    private Button Signup;
    private EditText editTexteEmail,editTextPassword;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private TextView forgotpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logindoc);
        firebaseAuth=FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!=null)
        {
            //start main activity
            finish();
            startActivity(new Intent(getApplicationContext(),Main2Activity.class));
        }

        buttonlogin=(Button)findViewById(R.id.loginbutton);
        editTexteEmail=(EditText)findViewById(R.id.editText);
        editTextPassword=(EditText)findViewById(R.id.editText2);
        forgotpassword = (TextView)findViewById(R.id.textView2);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(logindoc.this,forgotpass.class));
            }
        });
        Signup = (Button) findViewById(R.id.buttonsp);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent su = new Intent(logindoc.this,register.class);
                startActivity(su);
            }
        });

        progressDialog = new ProgressDialog(this);
        buttonlogin.setOnClickListener(this);
    }
    @Override
    public void onClick(View view){

        if (view == buttonlogin) {
            loginuser();

        }
    }


    private void loginuser()
    {

        String email,password;
        email=editTexteEmail.getText().toString().trim();
        password=editTextPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Logging you in doctor");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(logindoc.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(),Main2Activity.class));

                        } else {

                            Toast.makeText(logindoc.this,"Please register or check credentials",Toast.LENGTH_SHORT);

                        }
                        progressDialog.dismiss();


                    }


                });
    }
}


