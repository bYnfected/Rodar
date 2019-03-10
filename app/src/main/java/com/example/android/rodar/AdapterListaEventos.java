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

import java.util.ArrayList;

public class AdapterListaEventos extends RecyclerView.Adapter<AdapterListaEventos.ViewHolder> {

    private ArrayList<String> mEventos = new ArrayList<>();
    private ArrayList<String> mCidades = new ArrayList<>();
    private ArrayList<String> mDatas = new ArrayList<>();
    private Context mContext;

    public AdapterListaEventos(ArrayList<String> mEventos, ArrayList<String> mCidades, ArrayList<String> mDatas, Context mContext) {
        this.mEventos = mEventos;
        this.mCidades = mCidades;
        this.mDatas = mDatas;
        this.mContext = mContext;
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
        viewHolder.data.setText(mDatas.get(i));
        viewHolder.cidade.setText(mCidades.get(i));
        viewHolder.evento.setText(mEventos.get(i));

    }

    @Override
    public int getItemCount() {
        return mEventos.size();
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
