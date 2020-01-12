package com.sheershakx.goldsilvercom;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapterDarti extends RecyclerView.Adapter<adapterDarti.ViewHolder> {

    private ArrayList<String> sn;
    private ArrayList<String> name;
    private ArrayList<String> rate;


    public adapterDarti(ArrayList<String> sn, ArrayList<String> name, ArrayList<String> rate) {
        this.sn = sn;
        this.name = name;
        this.rate = rate;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.darti_layout, parent, false);
        return new ViewHolder(view);


    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final String Sn = sn.get(position);
        final String Name = name.get(position);
        final String Rate = rate.get(position);


        holder.sn.setText(Sn);
        holder.name.setText(Name);
        holder.rate.setText(Rate);


    }

    @Override
    public int getItemCount() {
        return name.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView sn;
        TextView name;
        TextView rate;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sn = itemView.findViewById(R.id.sn_darti);
            name = itemView.findViewById(R.id.name_darti);
            rate = itemView.findViewById(R.id.rate_darti);


        }
    }
}