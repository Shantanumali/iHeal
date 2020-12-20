package com.example.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class register extends AppCompatActivity implements View.OnClickListener {
    private Button buttonRegister;
    private EditText editTexteEmail,editTextPassword,editTextName,editTextQualification;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private Button signin;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=!])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null)
        {
            //start main activity
            finish();
            startActivity(new Intent(getApplicationContext(),Main2Activity.class));
        }
        buttonRegister=(Button)findViewById(R.id.button3);

        editTexteEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        editTextName=(EditText)findViewById(R.id.editTextName);
        editTextQualification=(EditText)findViewById(R.id.editTextQualification);

        signin=(Button)findViewById(R.id.buttonsi);
        progressDialog = new ProgressDialog(this);
        buttonRegister.setOnClickListener(this);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(register.this,logindoc.class));
            }
        });

    }
    @Override
    public void onClick(View view){
        if (view == buttonRegister) {
            registerUser();
        }
    }

    private void registerUser()
    {
        final DatabaseReference databasedoctor;
        databasedoctor= FirebaseDatabase.getInstance().getReference("Doctor");

        final String email,password,name,qualification;
        if(!validateEmail()){
            return;
        }


        email=editTexteEmail.getText().toString().trim();
        password=editTextPassword.getText().toString().trim();
        if(!validatePassword()){
            return;
        }
        name=editTextName.getText().toString().trim();
        qualification=editTextQualification.getText().toString().trim();
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

        if(TextUtils.isEmpty(name))
        {
            editTextName.setError("Field can't be empty");
            Toast.makeText(this,"Please enter name",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(qualification))
    {
        editTextQualification.setError("Field can't be empty");
        Toast.makeText(this,"Please enter qualification",Toast.LENGTH_LONG).show();
        return;
    }

        progressDialog.setMessage("Registering the doctor");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //user is registered and logged in we can go to the main page which has two buttons
                    int iend=email.indexOf("@");
                    String username;
                    username=email.substring(0 , iend);
                    DoctorClass doctor= new DoctorClass(username,email,name,qualification);
                    databasedoctor.child(username).setValue(doctor);
                    finish();
                    startActivity(new Intent(getApplicationContext(), Main2Activity.class));

                } else {
                    Toast.makeText(register.this, "Registration unsuccessful please try again", Toast.LENGTH_SHORT).show();

                }
                progressDialog.dismiss();
            }
        });



    }
    private boolean validateEmail() {
        String emailInput = editTexteEmail.getText().toString().trim();

        if (emailInput.isEmpty()) {
            editTexteEmail.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            editTexteEmail.setError("Please enter a valid email address");
            return false;
        } else {
            editTexteEmail.setError(null);
            return true;
        }
    }


    private boolean validatePassword() {
        String passwordInput = editTextPassword.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            editTextPassword.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            editTextPassword.setError("Password too weak");
            return false;
        } else {
            editTextPassword.setError(null);
            return true;
        }
    }


}


