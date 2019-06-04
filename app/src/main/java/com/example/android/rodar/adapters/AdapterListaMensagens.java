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
import com.example.android.rodar.models.MensagemCarona;
import com.example.android.rodar.models.MensagemTransporte;
import com.example.android.rodar.models.Transporte;

import java.util.List;

public class AdapterListaMensagens extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private AdapterListaMensagens.OnMensagemClickListener mOnMensagemClickListener;
    private static final int TRANSPORTE = 0;
    private static final int CARONA = 1;
    private List<Object> mListaMensagens;

    public AdapterListaMensagens(List<Object> mListaMensagens, AdapterListaMensagens.OnMensagemClickListener mOnMensagemClickListener) {
        this.mOnMensagemClickListener = mOnMensagemClickListener;
        this.mListaMensagens = mListaMensagens;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (i == TRANSPORTE){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_transporte, viewGroup, false);
            AdapterListaMensagens.ViewHolderT holder = new AdapterListaMensagens.ViewHolderT(view, mOnMensagemClickListener);
            return holder;

        } else if (i == CARONA){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_carona, viewGroup, false);
            AdapterListaMensagens.ViewHolderC holder = new AdapterListaMensagens.ViewHolderC(view, mOnMensagemClickListener);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof AdapterTranspCarona.ViewHolderT){
            ((AdapterTranspCarona.ViewHolderT) viewHolder).bind(i);
        } else if (viewHolder instanceof AdapterTranspCarona.ViewHolderC){
            ((AdapterTranspCarona.ViewHolderC) viewHolder).bind(i);
        }
    }

    @Override
    public int getItemCount() {
        return mListaMensagens.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mListaMensagens.get(position) instanceof Transporte)
            return TRANSPORTE;
        else
            return CARONA;
    }

    public class ViewHolderT extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView evento, partida, chegada, usuario;
        OnMensagemClickListener onMensagemClickListener;

        public ViewHolderT(@NonNull View itemView, AdapterListaMensagens.OnMensagemClickListener onMensagemClickListener) {
            super(itemView);

            evento = itemView.findViewById(R.id.card_mensagem_evento_nome);
            partida = itemView.findViewById(R.id.card_mensagem_evento_partida);
            chegada = itemView.findViewById(R.id.card_mensagem_evento_chegada);
            usuario = itemView.findViewById(R.id.card_mensagem_evento_usuario);

            this.onMensagemClickListener = onMensagemClickListener;

            itemView.setOnClickListener(this);
        }

        public void bind(int i){

            MensagemTransporte mensagem = (MensagemTransporte)mListaMensagens.get(i);

            evento.setText(mensagem.getEventoTransporte().getEvento().getNomeEvento());
            partida.setText(mensagem.getEventoTransporte().getDataHoraPartida());
            chegada.setText(mensagem.getEventoTransporte().getDataHoraPrevisaoChegada());
            usuario.setText(mensagem.getEventoTransporte().getUsuarioTransportador().getNome());
        }

        @Override
        public void onClick(View v) {
            onMensagemClickListener.onMensagemClick(getAdapterPosition());
        }
    }

    public class ViewHolderC extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView evento, partida, chegada, usuario;
        OnMensagemClickListener onMensagemClickListener;

        public ViewHolderC(@NonNull View itemView, AdapterListaMensagens.OnMensagemClickListener onMensagemClickListener) {
            super(itemView);

            evento = itemView.findViewById(R.id.card_mensagem_evento_nome);
            partida = itemView.findViewById(R.id.card_mensagem_evento_partida);
            chegada = itemView.findViewById(R.id.card_mensagem_evento_chegada);
            usuario = itemView.findViewById(R.id.card_mensagem_evento_usuario);

            this.onMensagemClickListener = onMensagemClickListener;

            itemView.setOnClickListener(this);
        }

        public void bind(int i){
            MensagemCarona mensagem = (MensagemCarona)mListaMensagens.get(i);

            evento.setText(mensagem.getEventoCarona().getEvento().getNomeEvento());
            partida.setText(mensagem.getEventoCarona().getDataHoraPartida());
            chegada.setText(mensagem.getEventoCarona().getDataHoraPrevisaoChegada());
            usuario.setText(mensagem.getEventoCarona().getUsuarioMotorista().getNome());
        }

        @Override
        public void onClick(View v) {
            onMensagemClickListener.onMensagemClick(getAdapterPosition());
        }
    }

    public interface OnMensagemClickListener{
        void onMensagemClick(int position);
    }
}