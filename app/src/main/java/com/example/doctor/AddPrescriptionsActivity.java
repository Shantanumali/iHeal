package com.example.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.constraintlayout.solver.widgets.Snapshot;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AddPrescriptionsActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditTextMedName;
    private EditText mDosage;
    private CheckBox mCheckMorning;
    private CheckBox mCheckNoon;
    private CheckBox mCheckAfterNoon;
    private CheckBox mCheckEvening;
    private Button /*mButtonAdd,*/exitButton;
    private FloatingActionButton floatingActionButton;
    private String patientId;
    private String dateOfVisit;

    private int morn=0,noon=0,afternoon=0,eve=0;

    ListView mListViewMeds;

    List<Prescription> prescriptionList;



    DatabaseReference presRef = FirebaseDatabase.getInstance().getReference("prescriptions");
    DatabaseReference forList=FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescriptions);

        mEditTextMedName = (EditText)findViewById(R.id.editTextMedName);
       mDosage = (EditText)findViewById(R.id.editTextdosage);
        mCheckMorning = (CheckBox)findViewById(R.id.checkBoxMorning);
        mCheckNoon = (CheckBox)findViewById(R.id.checkBoxNoon);
        mCheckAfterNoon = (CheckBox)findViewById(R.id.checkBoxAfterNoon);
        mCheckEvening = (CheckBox)findViewById(R.id.checkBoxEvening);
        mListViewMeds = (ListView)findViewById(R.id.listViewMeds);
        //mButtonAdd=(Button)findViewById(R.id.buttonAddPrescriptions);
        exitButton=(Button)findViewById(R.id.buttonSaveExit);
        prescriptionList = new ArrayList<>();
        floatingActionButton = findViewById(R.id.buttonAddPrescriptions);
        floatingActionButton.setOnClickListener(this);


        //mButtonAdd.setOnClickListener(this);
        exitButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String email=user.getEmail();
        int iend=email.indexOf("@");
        final String username=email.substring(0 , iend);
        Intent intent=getIntent();

        patientId = intent.getStringExtra(AddVisitActivity.PATIENT_ID);
        dateOfVisit = intent.getStringExtra(AddVisitActivity.DATE_OF_VISIT);

        Query myquery= forList.child("prescriptions").child(username).child(patientId).child(dateOfVisit);
        myquery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                prescriptionList.clear();
                for(DataSnapshot Prescription : dataSnapshot.getChildren()){
                    Prescription prescription= Prescription.getValue(Prescription.class);
                    prescriptionList.add(prescription);
                }
                PrescriptionsList adapter= new PrescriptionsList(AddPrescriptionsActivity.this,prescriptionList);
                mListViewMeds.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mListViewMeds.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub

                Prescription prescription=prescriptionList.get(pos);
                presRef.child(username).child(patientId).child(dateOfVisit).child(mEditTextMedName.getText().toString()).setValue(null);

                return true;
            }
        });
/*

        DatabaseReference dbref = presRef.child(username).child(patientId);
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                prescriptionList.clear();
                for(DataSnapshot presShot:dataSnapshot.getChildren())
                {
                    Prescription prescription = presShot.getValue(Prescription.class);
                    prescriptionList.add(prescription);
                }
                PrescriptionsList adapter = new PrescriptionsList(AddPrescriptionsActivity.this,prescriptionList);
                mListViewMeds.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();

            }
        });
*/
    }

    @Override
    public void onClick(View view) {
        if (view==floatingActionButton)
        {
            if(TextUtils.isEmpty(mEditTextMedName.getText()))
            {
                Toast.makeText(this,"Please Enter Medicine Name",Toast.LENGTH_SHORT).show();
                return;
            }
           if(TextUtils.isEmpty(mDosage.getText()))
            {
                Toast.makeText(this,"Please Enter Dosage",Toast.LENGTH_SHORT).show();
                return;
            }
            FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
            String email=user.getEmail();
            int iend=email.indexOf("@");
            final String username=email.substring(0 , iend);
            Intent intent = getIntent();

            patientId = intent.getStringExtra(AddVisitActivity.PATIENT_ID);

            dateOfVisit = intent.getStringExtra(AddVisitActivity.DATE_OF_VISIT);
            if(mCheckMorning.isChecked())
            {
                morn=1;
            }
            if(mCheckAfterNoon.isChecked())
            {
                afternoon=1;
            }
            if(mCheckNoon.isChecked())
            {
                noon=1;
            }
            if(mCheckEvening.isChecked())
            {
                eve=1;
            }

            Prescription prescription = new Prescription(mEditTextMedName.getText().toString(),morn,noon,afternoon,eve,mDosage.getText().toString());

            presRef.child(username).child(patientId).child(dateOfVisit).child(mEditTextMedName.getText().toString()).setValue(prescription);

            Toast.makeText(this,"Details saved",Toast.LENGTH_SHORT).show();
            morn=0;
            noon=0;
            afternoon=0;
            eve=0;
            if(mCheckMorning.isChecked())
            {
                mCheckMorning.toggle();
            }
            if(mCheckAfterNoon.isChecked())
            {
                mCheckAfterNoon.toggle();

            }
            if(mCheckNoon.isChecked())
            {
                mCheckNoon.toggle();
            }
            if(mCheckEvening.isChecked())
            {
                mCheckEvening.toggle();
            }

        }

        if(view==exitButton)
        {
            startActivity(new Intent(AddPrescriptionsActivity.this,Main2Activity.class));
        }
    }
}
