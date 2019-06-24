package com.example.android.rodar.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.rodar.R;
import com.example.android.rodar.models.MensagemCarona;
import com.example.android.rodar.models.MensagemTransporte;

import java.util.List;

public class AdapterListaMensagens extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private AdapterListaMensagens.OnMensagemClickListener mOnMensagemClickListener;
    //private static final int TRANSPORTE = 0;
    //private static final int CARONA = 1;
    private List<Object> mListaMensagens;

    public AdapterListaMensagens(List<Object> mListaMensagens, AdapterListaMensagens.OnMensagemClickListener mOnMensagemClickListener) {
        this.mOnMensagemClickListener = mOnMensagemClickListener;
        this.mListaMensagens = mListaMensagens;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        /*
        if (i == TRANSPORTE){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_transporte, viewGroup, false);
            AdapterListaMensagens.ViewHolderT holder = new AdapterListaMensagens.ViewHolderT(view, mOnMensagemClickListener);
            return holder;

        } else if (i == CARONA){
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_carona, viewGroup, false);
            AdapterListaMensagens.ViewHolderC holder = new AdapterListaMensagens.ViewHolderC(view, mOnMensagemClickListener);
            return holder;
        }
        */

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_mensagem, viewGroup, false);
        AdapterListaMensagens.ViewHolderCarona holder = new AdapterListaMensagens.ViewHolderCarona(view, mOnMensagemClickListener);
        return holder;


        //return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        /*
        if (viewHolder instanceof AdapterListaMensagens.ViewHolderT){
            ((AdapterTranspCarona.ViewHolderT) viewHolder).bind(i);
        } else if (viewHolder instanceof AdapterListaMensagens.ViewHolderCarona){
            ((AdapterTranspCarona.ViewHolderC) viewHolder).bind(i);
        }
        */

        ((AdapterListaMensagens.ViewHolderCarona) viewHolder).bind(i);

    }

    @Override
    public int getItemCount() {
        return mListaMensagens.size();
    }

    public class ViewHolderCarona extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView usuarioNome, dataMensagem, descricaoMensagem;
        OnMensagemClickListener onMensagemClickListener;

        public ViewHolderCarona(@NonNull View itemView, AdapterListaMensagens.OnMensagemClickListener onMensagemClickListener) {
            super(itemView);

            usuarioNome = itemView.findViewById(R.id.card_mensagem_usuario_nome);
            dataMensagem = itemView.findViewById(R.id.card_mensagem_data);
            descricaoMensagem = itemView.findViewById(R.id.card_mensagem_descricao);

            this.onMensagemClickListener = onMensagemClickListener;

            itemView.setOnClickListener(this);
        }

        public void bind(int i){

            if (mListaMensagens.get(i) instanceof  MensagemCarona) {
                MensagemCarona mensagem = (MensagemCarona) mListaMensagens.get(i);

                usuarioNome.setText(mensagem.getUsuarioOrigem().getNome());
                dataMensagem.setText(mensagem.getDataHoraInclusaoMensagem());
                descricaoMensagem.setText(mensagem.getMensagem());
            }
            else{
                MensagemTransporte mensagem = (MensagemTransporte) mListaMensagens.get(i);

                usuarioNome.setText(mensagem.getUsuarioOrigem().getNome());
                dataMensagem.setText(mensagem.getDataHoraInclusaoMensagem());
                descricaoMensagem.setText(mensagem.getMensagem());
            }
        }

        @Override
        public void onClick(View v) {
            onMensagemClickListener.onMensagemClick(getAdapterPosition());
        }
    }

    /*
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
    */

    public interface OnMensagemClickListener{
        void onMensagemClick(int position);
    }
}