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

public class adapterMember extends RecyclerView.Adapter<adapterMember.ViewHolder> {

    private ArrayList<String> firmname;
    private ArrayList<String> memid;



    public adapterMember(ArrayList<String> firmname, ArrayList<String> memid) {
        this.firmname = firmname;
        this.memid = memid;



    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.layout_member, parent, false);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();

        final String FirmName = firmname.get(position);
        final String Id = memid.get(position);

        holder.firmname.setText(FirmName);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, memberdetails.class);

                intent.putExtra("id", Id);

                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return memid.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView firmname;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview_mem);
            firmname = itemView.findViewById(R.id.firmname);


        }
    }

}