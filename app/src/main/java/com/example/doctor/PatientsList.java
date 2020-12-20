package com.example.doctor;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class PatientsList extends ArrayAdapter<PatientInfo> {
    private Activity context;
    private List<PatientInfo> patientInfoList;
    public PatientsList(Activity context, List<PatientInfo> patientInfoList){
        super(context, R.layout.list_layout,patientInfoList);
        this.context=context;
        this.patientInfoList=patientInfoList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout,null,true);
        TextView textViewName=(TextView)listViewItem.findViewById(R.id.textViewDosage);
        TextView textViewphno=(TextView)listViewItem.findViewById(R.id.textViewphno);

        PatientInfo patientInfo=patientInfoList.get(position);
        textViewName.setText(patientInfo.getName());
        textViewphno.setText(Long.toString(patientInfo.getPhoneno()));
        return listViewItem;
    }
}
