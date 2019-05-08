package com.example.android.rodar.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.rodar.R;
import com.example.android.rodar.models.Carona;

import java.util.List;


public class AdapterListaCaronas extends RecyclerView.Adapter<AdapterListaCaronas.ViewHolder> {

    private List<Carona> caronas;
    private OnCaronaClickListener mOnCaronaClickListener;

    public AdapterListaCaronas(List<Carona> caronas, OnCaronaClickListener mOnCaronaClickListener) {
        this.caronas = caronas;
        this.mOnCaronaClickListener = mOnCaronaClickListener;
    }

    @NonNull
    @Override
    public AdapterListaCaronas.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_carona, viewGroup, false);
        AdapterListaCaronas.ViewHolder holder = new AdapterListaCaronas.ViewHolder(view, mOnCaronaClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.endereco.setText(caronas.get(i).getEnderecoPartidaRua() + ", " +
                caronas.get(i).getEnderecoPartidaBairro());
        viewHolder.cidadeUF.setText(caronas.get(i).getEnderecoPartidaCidade() + " - " +
                caronas.get(i).getEnderecoPartidaUF());
        viewHolder.valor.setText("R$ " + caronas.get(i).getValorParticipacao().toString());
        viewHolder.barraVagas.setProgress(caronas.get(i).getQuantidadeVagasOcupadas());
        viewHolder.barraVagas.setMax(caronas.get(i).getQuantidadeVagas());
        viewHolder.vagas.setText("Vagas Diponíveis: " + caronas.get(i).getQuantidadeVagasDisponiveis()
                + " de " + caronas.get(i).getQuantidadeVagas());

    }

    @Override
    public int getItemCount() {
        return caronas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView endereco, valor, dataHora, vagas, cidadeUF;
        ProgressBar barraVagas;
        OnCaronaClickListener onCaronaClickListener;

        public ViewHolder(@NonNull View itemView, OnCaronaClickListener onCaronaClickListener) {
            super(itemView);

            endereco = itemView.findViewById(R.id.card_evento_carona_origem);
            valor = itemView.findViewById(R.id.card_evento_carona_valor);
            dataHora = itemView.findViewById(R.id.card_evento_carona_dataHora);
            vagas = itemView.findViewById(R.id.card_evento_carona_vagas);
            barraVagas = itemView.findViewById(R.id.card_evento_carona_barra);
            cidadeUF = itemView.findViewById(R.id.card_evento_carona_cidadeUF);

            this.onCaronaClickListener = onCaronaClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCaronaClickListener.onCaronaClick(getAdapterPosition());
        }


    }

    public interface OnCaronaClickListener {
        void onCaronaClick(int position);
    }
}