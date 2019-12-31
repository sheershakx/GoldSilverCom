package com.sheershakx.goldsilvercom;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adapterNotice extends RecyclerView.Adapter<adapterNotice.ViewHolder> {

    private ArrayList<String> date;
    private ArrayList<String> name;
    private ArrayList<String> notice;
    private ArrayList<String> memidd;


    public adapterNotice(ArrayList<String> date, ArrayList<String> name, ArrayList<String> notice,ArrayList<String> memid) {
        this.date = date;
        this.name = name;
        this.notice = notice;
        this.memidd = memid;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_notice, parent, false);
        return new ViewHolder(view);


    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();
        final String Date = date.get(position);
        final String Name = name.get(position);
        final String Notice = notice.get(position);
        final String Memid = memidd.get(position);


        holder.date.setText(Date);
        holder.name.setText(Name);
        holder.notice.setText(Notice);

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,memberdetails.class);
                intent.putExtra("id", Memid);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notice.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        TextView name;
        TextView notice;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.noticeDate);
            name = itemView.findViewById(R.id.shopname);
            notice = itemView.findViewById(R.id.noticetext);


        }
    }
}