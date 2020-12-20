package com.example.doctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddVisitActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButtonAddMeds;
    private TextView mTextViewName;
    private EditText mEditTextSymptoms;
    private EditText mEditTextReason;

    private String symptoms;
    private String reasonOfVisit;

    private String docId;
    private String patientId;
    private String patientName;

    public static final String PATIENT_ID = "id";
    public static final String DATE_OF_VISIT = "null";

    DatabaseReference mPatientsRef = FirebaseDatabase.getInstance().getReference("Patients");

    DatabaseReference mVisitRef = FirebaseDatabase.getInstance().getReference("visits");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visit);

        mButtonAddMeds = (Button) findViewById(R.id.buttonAddMeds);
        mTextViewName = (TextView) findViewById(R.id.textViewPatientName);
        mEditTextSymptoms = (EditText) findViewById(R.id.editTextSymptoms);
        mEditTextReason = (EditText) findViewById(R.id.EditTextReasonOfVisit);


        Intent intent = getIntent();

        patientName = intent.getStringExtra(PatientProfileActivity.PATIENT_NAME);
        patientId = intent.getStringExtra(PatientProfileActivity.PATIENT_ID);

        mTextViewName.setText(patientName);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        int iend = email.indexOf("@");
        final String username = email.substring(0, iend);
        docId = username;

        mButtonAddMeds.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

        if (view == mButtonAddMeds) {
            symptoms = mEditTextSymptoms.getText().toString();
            reasonOfVisit = mEditTextReason.getText().toString();

            Visit visit = new Visit(docId, patientId, symptoms, reasonOfVisit);
            mVisitRef.child(docId).child(patientId).child(Long.toString(visit.getDateOfVisit())).setValue(visit);


            Intent intent = new Intent(getApplicationContext(), AddPrescriptionsActivity.class);
            intent.putExtra(DATE_OF_VISIT, Long.toString(visit.getDateOfVisit()));
            intent.putExtra(PATIENT_ID, patientId);
            finish();
            startActivity(intent);
        }
    }
}

