package com.aca.kktrijumf.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aca.kktrijumf.Models.Coach;
import com.aca.kktrijumf.Models.Player;
import com.aca.kktrijumf.R;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NisuPlatiliAdapter extends RecyclerView.Adapter<NisuPlatiliAdapter.ViewHolder> {

    List<Player> igraci;
    List<Player> checkedPlayers;
    List<Coach> treneri;
    boolean nisuVidljivePoruke = false;
    boolean nextShow = false;

    public NisuPlatiliAdapter(List<Player> igraci, Map<String, List<Player>> trenerIgracMapa) {
        this.igraci = igraci;
       // this.trenerIgracMapa = trenerIgracMapa;
        checkedPlayers = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nije_platio_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String ime = igraci.get(position).getName() + " " + igraci.get(position).getSurname() +
                "  (" + igraci.get(position).getGroup() +")" + "\n - " + igraci.get(position).getCoach()+ " - ";
        holder.nijePlatioIme.setText(ime);

//        if(position == 0) {
//            nextShow = true;
//        }
//        if(nextShow){
//            holder.titelCoach.setVisibility(View.VISIBLE);
//            TextView title = (TextView) holder.titelCoach.getChildAt(0);
//            title.setText(igraci.get(position).getCoach());
//            nextShow = false;
//        }
//
//        if(position+1 < igraci.size()){
//            if(!igraci.get(position).getCoach().equals(igraci.get(position+1).getCoach())){
//                nextShow = true;
//            }
//        }
        if(nisuVidljivePoruke){
          holder.sendMessageHolder.setVisibility(View.GONE);
        }



        holder.posaljiSmsCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkedPlayers.add(igraci.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return igraci.size();
    }

    public void setList(List<Player> listaIgraca, Map<String, List<Player>> trenerIgracMapa){
        igraci = listaIgraca;
      //  this.trenerIgracMapa = trenerIgracMapa;
    }

    public void setList2(List<Player> listaIgraca, List<Coach> treneri){
        igraci = listaIgraca;
        this.treneri = treneri;
    }

    public void noSendMessage() {
        nisuVidljivePoruke = true;
    }
    public List<Player> getCheckedPlayers(){
        return checkedPlayers;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView nijePlatioIme;
        MaterialCheckBox posaljiSmsCheck;
        LinearLayout titelCoach;
        RelativeLayout sendMessageHolder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nijePlatioIme = itemView.findViewById(R.id.nijePlatioIme);
            posaljiSmsCheck = itemView.findViewById(R.id.posaljiSmsCheck);
            titelCoach = itemView.findViewById(R.id.titelCoach);
            sendMessageHolder = itemView.findViewById(R.id.sendMessageHolder);


        }
    }
}
