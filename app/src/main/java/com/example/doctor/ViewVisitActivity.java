package com.example.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewVisitActivity extends AppCompatActivity {

    private TextView textViewDate;
    private Button buttonSeeMeds;
    private TextView textViewSymptoms;
    ListView mListViewMeds;

    List<Prescription> prescriptionList;

    String patientId, dateOfVisit;
    DatabaseReference forList = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_visit);
        prescriptionList = new ArrayList<>();

        textViewDate = (TextView) findViewById(R.id.textViewDate);


        textViewSymptoms = (TextView) findViewById(R.id.textViewSymptoms);
        mListViewMeds = (ListView) findViewById(R.id.listViewtabs);

        Intent intent = getIntent();

        String year = intent.getStringExtra(PatientProfileActivity.year);
        String dayOfTheMonth = intent.getStringExtra(PatientProfileActivity.dayOfTheMonth);
        String month = intent.getStringExtra(PatientProfileActivity.month);
        String symptoms = intent.getStringExtra(PatientProfileActivity.symptoms);

        textViewDate.setText(dayOfTheMonth + "-" + month + "-" + year);
        textViewSymptoms.setText(symptoms);


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        int iend = email.indexOf("@");
        final String username = email.substring(0, iend);
        Intent intent = getIntent();

        patientId = intent.getStringExtra(PatientProfileActivity.PATIENT_ID);
        dateOfVisit = intent.getStringExtra(PatientProfileActivity.dateofvisit);

        Query myquery = forList.child("prescriptions").child(username).child(patientId).child(dateOfVisit);
        myquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                prescriptionList.clear();
                for (DataSnapshot Prescription : dataSnapshot.getChildren()) {
                    Prescription prescription = Prescription.getValue(Prescription.class);
                    prescriptionList.add(prescription);
                }
                PrescriptionsList adapter = new PrescriptionsList(ViewVisitActivity.this, prescriptionList);
                mListViewMeds.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
