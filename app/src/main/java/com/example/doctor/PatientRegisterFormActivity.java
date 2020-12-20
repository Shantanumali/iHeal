package com.example.doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PatientRegisterFormActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextname,editTextage,editTextheight,editTextweight,editTextphoneno,editTextaddress;
    private DatabaseReference mDatabase;
    private FirebaseDatabase firebaseuser;
    private FirebaseAuth firebaseAuth;
    private String name,address,email,username,sex;
    private int age;
    private float height,weight;
    private long phoneno;
    private Button buttonsave;
    private Spinner getSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

        String email=user.getEmail();
        int iend=email.indexOf("@");
        username=email.substring(0 , iend);
        mDatabase = FirebaseDatabase.getInstance().getReference("Patients").child(username);

        setContentView(R.layout.activity_patient_register_form);
        editTextname=(EditText)findViewById(R.id.edittextname);
        editTextage=(EditText)findViewById(R.id.edittextage);
        editTextheight=(EditText)findViewById(R.id.edittextheight);
        editTextweight=(EditText)findViewById(R.id.edittextweight);
        getSex=(Spinner)findViewById(R.id.edittextsex);
        editTextphoneno=(EditText)findViewById(R.id.edittextphoneno);
        editTextaddress=(EditText)findViewById(R.id.edittextaddress);
        buttonsave=(Button)findViewById(R.id.buttonsave);
        buttonsave.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {

        if(view==buttonsave){

            name=editTextname.getText().toString();
            sex=getSex.getSelectedItem().toString();
            address=editTextaddress.getText().toString();



            char[] nameArray= name.toCharArray();
            if(TextUtils.isEmpty(name))
            {
                Toast.makeText(this, "Please Enter the Name", Toast.LENGTH_LONG).show();
                return;

            }
            if(TextUtils.isEmpty(editTextage.getText().toString()))
            {
                Toast.makeText(this, "Please Enter the age", Toast.LENGTH_LONG).show();
                return;
            }
            age=Integer.parseInt(editTextage.getText().toString());
            if(TextUtils.isEmpty(editTextweight.getText().toString()))
            {
                Toast.makeText(this, "Please Enter the weight", Toast.LENGTH_LONG).show();
                return;

            }
            weight=Float.parseFloat(editTextweight.getText().toString());

            if (TextUtils.isEmpty(editTextphoneno.getText().toString()))
            {
                Toast.makeText(this, "Please Enter the name", Toast.LENGTH_LONG).show();
                return;
            }
            phoneno=Long.parseLong(editTextphoneno.getText().toString());
            if (TextUtils.isEmpty(editTextheight.getText().toString()))
            {
                Toast.makeText(this, "Please Enter the name", Toast.LENGTH_LONG).show();
                return;
            }

            height=Float.parseFloat(editTextheight.getText().toString());
            for (char chr:nameArray) {

                if (((chr >= 'A') && (chr <= 'Z')) || ((chr >= 'a') && (chr <= 'z')) || chr==' ') {
                    continue;
                } else {
                    Toast.makeText(this, "Please Re-enter the name", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            try {
                 /*String phNo = Long.toString(phoneno);
                 char[] phNoArray= phNo.toCharArray();
                    for (char chr:phNoArray) {

                         if((chr>='0')&&(chr<='9'))
                        {
                            continue;
                        }
                         else
                         {
                             Toast.makeText(this, "Please Re-enter the phone no:", Toast.LENGTH_LONG).show();
                             return;
                         }
                    }
*/
                if (phoneno > 7000000000l && phoneno < 10000000000l) {
                    PatientInfo info = new PatientInfo(Long.toString(phoneno), name, sex, address, age, height, weight, phoneno);
                    mDatabase.child(String.valueOf(phoneno)).setValue(info);
                    Toast.makeText(this, "Details saved", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(PatientRegisterFormActivity.this, Main2Activity.class));

                } else {
                    Toast.makeText(this, "Please Re-enter the Phone no.", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                Toast.makeText(this, "Please Re-enter the Phone no.", Toast.LENGTH_LONG).show();

            }

        }

    }
}