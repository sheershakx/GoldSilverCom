package com.sheershakx.goldsilvercom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapterLebhi extends RecyclerView.Adapter<adapterLebhi.ViewHolder> {

    private ArrayList<String> date;
    private ArrayList<String> year;
    private ArrayList<String> month;
    private ArrayList<String> amount;
    private ArrayList<String> rashid;
    String[] stringArray;


    public adapterLebhi(ArrayList<String> date, ArrayList<String> year, ArrayList<String> month, ArrayList<String> amount, ArrayList<String> rashid) {
        this.date = date;
        this.year = year;
        this.month = month;
        this.amount = amount;
        this.rashid = rashid;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_lebhi, parent, false);
        stringArray = view.getResources().getStringArray(R.array.month);
        return new ViewHolder(view);


    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final String Year = year.get(position);
        final String Month = month.get(position);
        final String Amount = amount.get(position);
        final String Rashid = rashid.get(position);
        final String Date = date.get(position);


        holder.year.setText(Year);
        holder.month.setText(stringArray[Integer.parseInt(Month)]);
    //    holder.month.setText(Month);
        holder.amount.setText(Amount);
        holder.rashid.setText(Rashid);
        holder.date.setText(Date);


    }

    @Override
    public int getItemCount() {
        return month.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView year;
        TextView month;
        TextView amount;
        TextView date;
        TextView rashid;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            year = itemView.findViewById(R.id.yearLebhi);
            month = itemView.findViewById(R.id.monthLebhi);
            amount = itemView.findViewById(R.id.amountLebhi);
            date = itemView.findViewById(R.id.dateLebhi);
            rashid = itemView.findViewById(R.id.rashidLebhi);


        }
    }
}