package com.example.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class update_patient extends AppCompatActivity {
    String patient_Name, patient_id;
    String value,Field;
    TextView viewName;
    EditText Value;
    Spinner spinner;
    DatabaseReference databasePatients;

    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_patient);
        Intent intent = getIntent();
        patient_Name = intent.getStringExtra(PatientProfileActivity.PATIENT_NAME);
        patient_id = intent.getStringExtra(PatientProfileActivity.PATIENT_ID);
        viewName = (TextView) findViewById(R.id.textViewName);
        Value = (EditText) findViewById(R.id.value);
        spinner = (Spinner) findViewById(R.id.spinner);
        update = (Button) findViewById(R.id.button5);
        viewName.setText(patient_Name);
        databasePatients= FirebaseDatabase.getInstance().getReference();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        int iend = email.indexOf("@");
        final String username = email.substring(0, iend);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query myTopPostsQuery = databasePatients.child("Patients").child(username);
                myTopPostsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot patientSnapshot:dataSnapshot.getChildren()){
                            PatientInfo patientInfo=patientSnapshot.getValue(PatientInfo.class);

                            if(patientInfo.getId().equals(patient_id)) {
                                changevalue(patientInfo,username);
                            }
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Toast.makeText(update_patient.this, "Update Pressed", Toast.LENGTH_SHORT).show();
            }
        });




    }

    public void changevalue(final PatientInfo p1,String username){
        value = Value.getText().toString();
        Field = spinner.getSelectedItem().toString();

        if (!TextUtils.isEmpty(value)) {
            if (Field.equals("Name")) {
                databasePatients.child("Patients").child(username).child(p1.getId()).child("name").setValue(value);
            }

            if (Field.equals("Age")) {
                databasePatients.child("Patients").child(username).child(p1.getId()).child("age").setValue(Integer.parseInt(value));
            }

            if (Field.equals("Height")) {
                databasePatients.child("Patients").child(username).child(p1.getId()).child("height").setValue(Integer.parseInt(value));
            }

            if (Field.equals("Weight")) {
                databasePatients.child("Patients").child(username).child(p1.getId()).child("weight").setValue(Integer.parseInt(value));
            }

            if (Field.equals("Address")) {
                databasePatients.child("Patients").child(username).child(p1.getId()).child("address").setValue(value);

            }

        }
    }


}


