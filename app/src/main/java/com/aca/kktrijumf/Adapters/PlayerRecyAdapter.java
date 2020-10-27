package com.aca.kktrijumf.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aca.kktrijumf.Models.Coach;
import com.aca.kktrijumf.Models.Placanje;
import com.aca.kktrijumf.Models.Player;
import com.aca.kktrijumf.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PlayerRecyAdapter extends RecyclerView.Adapter<PlayerRecyAdapter.PlayerViewHolder> {

    ItemClickListener listener;
    List<Player> playerList;
    String formattedDate;

    public PlayerRecyAdapter(List<Player> playerList) {
        this.playerList = playerList;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_item,parent,false);
        PlayerViewHolder viewHolder = new PlayerViewHolder(view);
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        formattedDate = df.format(c);
        return viewHolder;
    }

    public void setList(List<Player> lista) {
        playerList = lista;
    }
    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player p = playerList.get(position);
        holder.playerName.setText(p.getName() + " " + p.getSurname());
        if(p.getPayments().isEmpty()){
            holder.didPayImg.setVisibility(View.VISIBLE);
        }else {
            for (Placanje placanje : p.getPayments()) {
                if (placanje.platioZaMesec(formattedDate)) {
                    Log.d("dolarTest", "onBindViewHolder: NIJE PLATIO");
                    holder.didPayImg.setVisibility(View.GONE);
                    return;
                }
            }
        }

    }
    public void setItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }


    class PlayerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView playerName;
        ImageView didPayImg;
        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            playerName = itemView.findViewById(R.id.playerName);
            didPayImg = itemView.findViewById(R.id.didPayImg);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) listener.onItemClick(view, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
