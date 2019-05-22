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
import com.example.android.rodar.models.Transporte;

import java.util.List;

public class AdapterTranspCarona extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> mAtivos;
    private AdapterTranspCarona.OnTranspCaronaClickListener mOnTranspCaronaClickListener;
    private static final int TRANSPORTE = 0;
    private static final int CARONA = 1;



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (i == TRANSPORTE){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_transporte, viewGroup, false);
            AdapterTranspCarona.ViewHolderT holder = new AdapterTranspCarona.ViewHolderT(view, mOnTranspCaronaClickListener);
            return holder;
        } else if (i == CARONA){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_carona, viewGroup, false);
            AdapterTranspCarona.ViewHolderC holder = new AdapterTranspCarona.ViewHolderC(view, mOnTranspCaronaClickListener);
            return holder;
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ViewHolderT){
            ((ViewHolderT) viewHolder).bind(i);
        } else if (viewHolder instanceof ViewHolderC){
            ((ViewHolderC) viewHolder).bind(i);
        }

    }

    @Override
    public int getItemCount() {
        return mAtivos.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mAtivos.get(position) instanceof Transporte)
            return TRANSPORTE;
        else
            return CARONA;
        }

    public class ViewHolderT extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView endereco, valor, dataHora, vagas, cidadeUF;
        ProgressBar barraVagas;
        OnTranspCaronaClickListener onTranspCaronaClickListener;

        public ViewHolderT(@NonNull View itemView, OnTranspCaronaClickListener onTranspCaronaClickListener) {
            super(itemView);

            endereco = itemView.findViewById(R.id.card_evento_transporte_origem);
            valor = itemView.findViewById(R.id.card_evento_transporte_valor);
            dataHora = itemView.findViewById(R.id.card_evento_transporte_dataHora);
            vagas = itemView.findViewById(R.id.card_evento_transporte_vagas);
            barraVagas = itemView.findViewById(R.id.card_evento_transporte_barra);
            cidadeUF = itemView.findViewById(R.id.card_evento_transporte_cidadeUF);

            this.onTranspCaronaClickListener = onTranspCaronaClickListener;

            itemView.setOnClickListener(this);

        }

        public void bind(int i){
            Transporte transp = (Transporte) mAtivos.get(i);
            endereco.setText(transp.getEnderecoPartidaRua() + ", " +
                    transp.getEnderecoPartidaBairro());
            cidadeUF.setText(transp.getEnderecoPartidaCidade() + " - " +
                    transp.getEnderecoPartidaUF());
            valor.setText("R$ " + transp.getValorParticipacao().toString());
            barraVagas.setProgress(transp.getQuantidadeVagasOcupadas());
            barraVagas.setMax(transp.getQuantidadeVagas());
            vagas.setText("Vagas Diponíveis: " + transp.getQuantidadeVagasDisponiveis()
                    + " de " + transp.getQuantidadeVagas());
        }

        @Override
        public void onClick(View v) {
            onTranspCaronaClickListener.onTranspCaronaClick(getAdapterPosition());
        }


    }

    public class ViewHolderC extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView endereco, valor, dataHora, vagas, cidadeUF;
        ProgressBar barraVagas;
        OnTranspCaronaClickListener onTranspCaronaClickListener;

        public ViewHolderC(@NonNull View itemView, OnTranspCaronaClickListener onTranspCaronaClickListener) {
            super(itemView);

            endereco = itemView.findViewById(R.id.card_evento_carona_origem);
            valor = itemView.findViewById(R.id.card_evento_carona_valor);
            dataHora = itemView.findViewById(R.id.card_evento_carona_dataHora);
            vagas = itemView.findViewById(R.id.card_evento_carona_vagas);
            barraVagas = itemView.findViewById(R.id.card_evento_carona_barra);
            cidadeUF = itemView.findViewById(R.id.card_evento_carona_cidadeUF);

            this.onTranspCaronaClickListener = onTranspCaronaClickListener;

            itemView.setOnClickListener(this);
        }

        public void bind(int i){
            Carona carona = (Carona) mAtivos.get(i);
            endereco.setText(carona.getEnderecoPartidaRua() + ", " +
                    carona.getEnderecoPartidaBairro());
            cidadeUF.setText(carona.getEnderecoPartidaCidade() + " - " +
                    carona.getEnderecoPartidaUF());
            valor.setText("R$ " + carona.getValorParticipacao().toString());
            barraVagas.setProgress(carona.getQuantidadeVagasOcupadas());
            barraVagas.setMax(carona.getQuantidadeVagas());
            vagas.setText("Vagas Diponíveis: " + carona.getQuantidadeVagasDisponiveis()
                    + " de " + carona.getQuantidadeVagas());
        }

        @Override
        public void onClick(View v) {
            onTranspCaronaClickListener.onTranspCaronaClick(getAdapterPosition());
        }


    }

    public interface OnTranspCaronaClickListener{
        void onTranspCaronaClick(int position);
    }
}
