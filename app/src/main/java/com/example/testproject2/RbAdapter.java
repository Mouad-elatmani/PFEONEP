package com.example.testproject2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RbAdapter  extends RecyclerView.Adapter<RbAdapter.VH> {
    Context context;
    ArrayList<Data> data;
    RbAdapter(Context context,ArrayList<Data> data){
        this.context=context;
        this.data=data;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_adapter,null,false);
       VH myVH= new VH(v);
       return  myVH;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Data d =data.get(position);
        holder.tv_heure.setText(d.getHeure());
        holder.tv_date.setText(d.getDate());
        holder.tv_niveau.setText(d.getNiveau());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class VH extends RecyclerView.ViewHolder{
        TextView tv_niveau,tv_date,tv_heure;
        public VH(@NonNull View itemView) {
            super(itemView);
            tv_niveau=itemView.findViewById(R.id.NV);
            tv_date=itemView.findViewById(R.id.date);
            tv_heure=itemView.findViewById(R.id.heure);

        }
    }
}
