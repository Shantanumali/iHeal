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

public class PrescriptionsList  extends ArrayAdapter<Prescription> {

    private Activity context;
    private List<Prescription> prescriptionList;

    public PrescriptionsList(Activity context, List<Prescription> prescriptionList) {
        super(context, R.layout.prescription_list_layout,prescriptionList);
        this.prescriptionList = prescriptionList;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.prescription_list_layout,null,true);
        TextView mTextViewName =(TextView) listViewItem.findViewById(R.id.textViewMedName);
        TextView mTextViewDosage = (TextView) listViewItem.findViewById(R.id.textViewDosage);
        TextView mTextViewDosageNew = (TextView) listViewItem.findViewById(R.id.dosage);

        Prescription prescription = prescriptionList.get(position);

        mTextViewName.setText(prescription.getName());
        mTextViewDosageNew.setText(prescription.getDosage());


        String tmp="";
       if(prescription.getIsMorning()==1)
        {
            tmp += "Morning";
        }
        if(prescription.getIsNoon()==1)
        {
            tmp+="-Noon";
        }
        if(prescription.getIsAfternoon()==1)
        {
            tmp+="-Afternoon";
        }
        if(prescription.getIsEvening()==1)
        {
            tmp+="-Evening";
        }
        mTextViewDosage.setText(tmp);
        return listViewItem;

    }
}
