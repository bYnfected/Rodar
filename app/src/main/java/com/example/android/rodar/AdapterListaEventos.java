package com.example.android.rodar;

import android.content.Context;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.rodar.models.Evento;

import java.util.ArrayList;
import java.util.List;

public class AdapterListaEventos extends RecyclerView.Adapter<AdapterListaEventos.ViewHolder> {

    private Context mContext;
    private List<Evento> eventos;

    public AdapterListaEventos(Context mContext, List<Evento> eventos) {
        this.mContext = mContext;
        this.eventos = eventos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_evento, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.data.setText(eventos.get(i).getEnderecoCEP());
        viewHolder.cidade.setText(eventos.get(i).getEnderecoCidade());
        viewHolder.evento.setText(eventos.get(i).getDescricaoEvento());

    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView evento,cidade,data;
        CardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            evento = itemView.findViewById(R.id.card_titulo_evento);
            cidade = itemView.findViewById(R.id.card_cidade_evento);
            data = itemView.findViewById(R.id.card_data_evento);
        }
    }
}
