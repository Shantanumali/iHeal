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

public class VisitsList extends ArrayAdapter<Visit>  {

    private Activity context;
    private List<Visit> visitList;

    public VisitsList(Activity context, List<Visit> visitList){
        super(context, R.layout.visit_list_layout,visitList);
        this.context=context;
        this.visitList = visitList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.visit_list_layout,null,true);
        TextView textViewDate =(TextView) listViewItem.findViewById(R.id.textViewDate);
        TextView mTextViewReason = (TextView) listViewItem.findViewById(R.id.textViewReason);


        Visit visit = visitList.get(position);

        String visitDate = Integer.toString(visit.getDayOfTheMonth()) + "-" +
                Integer.toString(visit.getMonth()) + "-" + Integer.toString(visit.getYear())+"-"+visit.getHour()+":"+visit.getMinute();

        textViewDate.setText(visitDate);
        mTextViewReason.setText(visit.getReasonOfVisit());

        return listViewItem;

    }
}
