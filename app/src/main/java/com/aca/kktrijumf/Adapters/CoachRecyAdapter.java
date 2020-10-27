package com.aca.kktrijumf.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aca.kktrijumf.Models.Coach;
import com.aca.kktrijumf.Models.Player;
import com.aca.kktrijumf.R;

import java.util.ArrayList;
import java.util.List;

public class CoachRecyAdapter extends RecyclerView.Adapter<CoachRecyAdapter.MViewHolder> {
    List<Coach> listaTrenera;
    private ItemClickListener mClickListener;

    public void setList(List<Coach> lista) {
        listaTrenera = lista;
    }

    public CoachRecyAdapter(List<Coach> listaTrenera){
        this.listaTrenera = listaTrenera;
    }
    public CoachRecyAdapter(){
    }

    @NonNull
    @Override
    public MViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item,parent,false);
        MViewHolder mViewHolder = new MViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MViewHolder holder, int position) {
        Coach c = listaTrenera.get(position);
        holder.coachName.setText(c.getName() + " " + c.getSurName());
    }

    @Override
    public int getItemCount() {
        return listaTrenera.size();
    }
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public class MViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView coachName;
        public MViewHolder(@NonNull View itemView) {
            super(itemView);
            coachName = itemView.findViewById(R.id.coachName);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
