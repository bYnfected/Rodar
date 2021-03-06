package com.example.android.rodar.adapters;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.rodar.R;
import com.example.android.rodar.models.Transporte;

import java.util.ArrayList;
import java.util.List;


public class AdapterListaTransportes extends RecyclerView.Adapter<AdapterListaTransportes.ViewHolder> {

    private List<Transporte> transportes;
    private OnTransporteClickListener mOnTransporteClickListener;

    public AdapterListaTransportes(List<Transporte> transportes, OnTransporteClickListener mOnTransporteClickListener) {
        if (transportes == null){
            transportes = new ArrayList<>();
        }
        this.transportes = transportes;
        this.mOnTransporteClickListener = mOnTransporteClickListener;
    }

    @NonNull
    @Override
    public AdapterListaTransportes.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_transporte, viewGroup, false);
        AdapterListaTransportes.ViewHolder holder = new AdapterListaTransportes.ViewHolder(view, mOnTransporteClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.cidadeUF.setText(transportes.get(i).getEnderecoPartidaCidade() + " - " +
                transportes.get(i).getEnderecoPartidaUF());
        viewHolder.valor.setText("R$ " + transportes.get(i).getValorParticipacao().toString());
        viewHolder.endereco.setText(transportes.get(i).getEnderecoPartidaRua() + ", " +
                transportes.get(i).getEnderecoPartidaBairro());
        viewHolder.barraVagas.setProgress(transportes.get(i).getQuantidadeVagasOcupadas());
        viewHolder.barraVagas.setMax(transportes.get(i).getQuantidadeVagas());
        viewHolder.vagas.setText("Vagas Diponíveis: " + transportes.get(i).getQuantidadeVagasDisponiveis()
                + " de " + transportes.get(i).getQuantidadeVagas());
    }

    @Override
    public int getItemCount() {
        return transportes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView endereco, valor, dataHora, vagas, cidadeUF;
        ProgressBar barraVagas;
        OnTransporteClickListener onTransporteClickListener;

        public ViewHolder(@NonNull View itemView, OnTransporteClickListener onTransporteClickListener) {
            super(itemView);

            endereco = itemView.findViewById(R.id.card_evento_transporte_origem);
            valor = itemView.findViewById(R.id.card_evento_transporte_valor);
            dataHora = itemView.findViewById(R.id.card_evento_transporte_dataHora);
            vagas = itemView.findViewById(R.id.card_evento_transporte_vagas);
            barraVagas = itemView.findViewById(R.id.card_evento_transporte_barra);
            cidadeUF = itemView.findViewById(R.id.card_evento_transporte_cidadeUF);

            this.onTransporteClickListener = onTransporteClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onTransporteClickListener.onTransporteClick(getAdapterPosition());
        }


    }

    public interface OnTransporteClickListener {
        void onTransporteClick(int position);
    }
}