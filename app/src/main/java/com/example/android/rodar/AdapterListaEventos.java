package com.example.android.rodar;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.rodar.models.Evento;

import java.util.List;

public class AdapterListaEventos extends RecyclerView.Adapter<AdapterListaEventos.ViewHolder> {

    private Context mContext;
    private List<Evento> eventos;
    private OnEventoClickListener mOnEventoClickListener;

    public AdapterListaEventos(Context mContext, List<Evento> eventos, OnEventoClickListener onEventoClickListener) {
        this.mContext = mContext;
        this.eventos = eventos;
        this.mOnEventoClickListener = onEventoClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_evento, viewGroup, false);
        ViewHolder holder = new ViewHolder(view, mOnEventoClickListener);
        return holder;
    }

    // Seta os dados apos a criacao
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.evento.setText(eventos.get(i).getNomeEvento());


        //viewHolder.cidade.setText(eventos.get(i).getEnderecoCidade() +);
        viewHolder.data.setText(eventos.get(i).getEnderecoCEP());
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    // Definicao de views
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView evento,cidade,data;
        OnEventoClickListener onEventoClickListener;

        public ViewHolder(@NonNull View itemView, OnEventoClickListener onEventoClickListener) {
            super(itemView);
            evento = itemView.findViewById(R.id.card_evento_titulo);
            cidade = itemView.findViewById(R.id.card_evento_local);
            data = itemView.findViewById(R.id.card_evento_data);
            this.onEventoClickListener = onEventoClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onEventoClickListener.onEventoClick(getAdapterPosition());
        }
    }

    public interface OnEventoClickListener{
        void onEventoClick(int position);
    }

}
