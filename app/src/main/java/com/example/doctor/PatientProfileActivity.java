package com.example.doctor;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PatientProfileActivity extends AppCompatActivity {

    TextView mTextViewName, mTextViewAge, mTextViewWeight;
    FloatingActionButton edit,delete;

    ListView mListViewVisits;

    DatabaseReference mVisitRef,databaseReference;

    public static final String PATIENT_ID="id";
    public static final String PATIENT_NAME = "name";

    private String patientName;
    private String patientId;
    public static final String dateofvisit = "date";
    public static final String year = "y";
    public static final String dayOfTheMonth = "m";
    public static final String month = "d";
    public static final String symptoms = "symptoms";



    List<Visit> visitList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientprofile);

        mVisitRef = FirebaseDatabase.getInstance().getReference("visits");
        databaseReference=FirebaseDatabase.getInstance().getReference();
        visitList = new ArrayList<>();

        Intent intent=getIntent();
        final String id = intent.getStringExtra(Main2Activity.id);
        String name = intent.getStringExtra(Main2Activity.patientName);
        String age = intent.getStringExtra(Main2Activity.age);
        String weight = intent.getStringExtra(Main2Activity.weight);

         mTextViewAge =(TextView)findViewById(R.id.patientAge);
         mTextViewName =(TextView)findViewById(R.id.patientName);
         mTextViewWeight =(TextView)findViewById(R.id.patientWeight);
         mTextViewName.setText(name);
         mTextViewWeight.setText(weight);
         mTextViewAge.setText(age);
        edit=(FloatingActionButton)findViewById(R.id.edit);
        delete=(FloatingActionButton)findViewById(R.id.delete);
         mListViewVisits = (ListView)findViewById(R.id.listViewVisits);

         patientId = id;
         patientName = name;


        FloatingActionButton floatingActionButton1 = findViewById(R.id.fabAddVisit);
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientProfileActivity.this, AddVisitActivity.class);
                intent.putExtra(PATIENT_NAME,patientName);
                intent.putExtra(PATIENT_ID,id);
                startActivity(intent);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientProfileActivity.this, update_patient.class);
                intent.putExtra(PATIENT_NAME,patientName);
                intent.putExtra(PATIENT_ID,id);
                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String email = user.getEmail();
                int iend = email.indexOf("@");
                final String username = email.substring(0, iend);
                databaseReference.child("Patients").child(username).child(patientId).removeValue();
                databaseReference.child("prescriptions").child(username).child(patientId).removeValue();
                databaseReference.child("visits").child(username).child(patientId).removeValue();
                finish();
            }
        });
        mListViewVisits.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Visit visit = visitList.get(i);
                Intent intent = new Intent(getApplicationContext(),ViewVisitActivity.class);

                intent.putExtra(year,Integer.toString(visit.getYear()));
                intent.putExtra(dateofvisit,Long.toString(visit.getDateOfVisit()));
                intent.putExtra(dayOfTheMonth,Integer.toString(visit.getDayOfTheMonth()));
                intent.putExtra(month,Integer.toString(visit.getMonth()));
                intent.putExtra(PATIENT_ID,id);
                intent.putExtra(symptoms,visit.getSymptoms());

                startActivity(intent);


            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String email=user.getEmail();
        int iend=email.indexOf("@");
        final String username=email.substring(0 , iend);

        Query visitsQuery = mVisitRef.child(username).child(patientId).orderByChild("dateOfVisit");
        visitsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                visitList.clear();
                for(DataSnapshot visitShot : dataSnapshot.getChildren())
                {
                    Visit visit = visitShot.getValue(Visit.class);
                    visitList.add(visit);
                }

                VisitsList adapter = new VisitsList(PatientProfileActivity.this,visitList);
                mListViewVisits.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*DatabaseReference dbRef = mVisitRef.child(username).child(patientId);
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot visitShot : dataSnapshot.getChildren())
                {
                    visitList.clear();
                    Visit visit = visitShot.getValue(Visit.class);
                    visitList.add(visit);

                }

                VisitsList adapter = new VisitsList(PatientProfileActivity.this, visitList);
                mListViewVisits.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }
}
