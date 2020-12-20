package com.example.doctor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

public class Main2Activity extends AppCompatActivity {
    public static final String patientName="name";
    public static final String weight="weight";
    public static final String age="age";
    public static final String id="id";

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    ListView listViewpatients;
    List<PatientInfo> patientInfoList;
    DatabaseReference databasePatients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        databasePatients= FirebaseDatabase.getInstance().getReference();
        listViewpatients=(ListView)findViewById(R.id.listviewPatients);
        patientInfoList=new ArrayList<>();
        firebaseAuth= FirebaseAuth.getInstance();
        FloatingActionButton floatingActionButton = findViewById(R.id.fabAddPatient);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main2Activity.this,PatientRegisterFormActivity.class));
            }
        });
        listViewpatients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PatientInfo patient=patientInfoList.get(i);
                Intent intent=new Intent(getApplicationContext(), PatientProfileActivity.class);
                intent.putExtra(id,patient.getId());
                intent.putExtra(patientName,patient.getName());
                intent.putExtra(weight,Float.toString(patient.getWeight()));
                intent.putExtra(age,Integer.toString(patient.getAge()));
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
        String username;
        username=email.substring(0 , iend);
        Query myTopPostsQuery = databasePatients.child("Patients").child(username).orderByChild("name");
        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                patientInfoList.clear();
                for(DataSnapshot patientSnapshot:dataSnapshot.getChildren()){
                    PatientInfo patientInfo=patientSnapshot.getValue(PatientInfo.class);
                    patientInfoList.add(patientInfo);

                }

                PatientsList adapter=new PatientsList(Main2Activity.this,patientInfoList);
                listViewpatients.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.e_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.item0:
                Toast.makeText(this,"Refresh",Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(Main2Activity.this,Main2Activity.class));
                return true;

            case R.id.item1:
                Toast.makeText(this,"Change Password",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Main2Activity.this,updatepassword.class));
                return true;

            case R.id.item2:
                Toast.makeText(this,"Logout",Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), logindoc.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
