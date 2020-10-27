package com.aca.kktrijumf.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aca.kktrijumf.Models.Placanje;
import com.aca.kktrijumf.R;

import java.util.List;

public class PlacanjaAdapter extends RecyclerView.Adapter<PlacanjaAdapter.PlacanjaViewHolder> {
    List<Placanje> listaPlacanja;

    public PlacanjaAdapter(List<Placanje> listaPlacanja) {
        this.listaPlacanja = listaPlacanja;

    }

    @NonNull
    @Override
    public PlacanjaAdapter.PlacanjaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.placanje_item,parent,false);
        PlacanjaViewHolder viewHolder = new PlacanjaViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlacanjaViewHolder holder, int position) {
        holder.napomenaTv.setText(listaPlacanja.get(position).getNapomena());
        holder.datumTv.setText(listaPlacanja.get(position).getDatum());
    }


    @Override
    public int getItemCount() {
        return listaPlacanja.size();
    }

    public class PlacanjaViewHolder extends RecyclerView.ViewHolder{

        TextView datumTv;
        TextView napomenaTv;

        public PlacanjaViewHolder(@NonNull View itemView) {
            super(itemView);
            datumTv = itemView.findViewById(R.id.datumPlacanjaTv);
            napomenaTv = itemView.findViewById(R.id.napomenaTv);
        }
    }
}
