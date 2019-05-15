package com.example.android.rodar.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.rodar.R;
import com.example.android.rodar.Utils.PreferenceUtils;
import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.models.Evento;
import com.example.android.rodar.services.FavoritoService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterListaEventos extends RecyclerView.Adapter<AdapterListaEventos.ViewHolder> {

    private List<Evento> eventos;
    private OnEventoClickListener mOnEventoClickListener;

    public AdapterListaEventos(List<Evento> eventos, OnEventoClickListener onEventoClickListener) {
        if (eventos == null){
            eventos = new ArrayList<>();
        }
        this.eventos = eventos;
        this.mOnEventoClickListener = onEventoClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_evento, viewGroup, false);
        ViewHolder holder = new ViewHolder(view, mOnEventoClickListener);

        return holder;
    }

    // Seta os dados apos a criacao
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.textViewEvento.setText(eventos.get(position).getNomeEvento());
        viewHolder.textViewData.setText(eventos.get(position).getEnderecoCEP());
        viewHolder.textViewCidade.setText(eventos.get(position).getEnderecoRua() + ", " +
                eventos.get(position).getEnderecoCidade() + " - " +
                eventos.get(position).getEnderecoUF());

        Evento evento =  eventos.get(position);

        FavoritoData favoritoData = new FavoritoData();
        favoritoData.setListPosition(position);
        favoritoData.setIsFavorito(evento.getFavorito());
        favoritoData.setIdEvento(evento.getIdEvento());


        if (PreferenceUtils.getID(viewHolder.btnDeletar.getContext()) != evento.getIdUsuarioCriacao()){
            viewHolder.btnDeletar.setVisibility(View.GONE);
        }


        viewHolder.buttonFavoritar.setTag(favoritoData);
        viewHolder.buttonFavoritar.setBackgroundResource(evento.getFavorito() ? R.drawable.ic_favorite_red_36dp : R.drawable.ic_favorite_border_black_36dp);
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    // Definicao de views
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewEvento, textViewCidade, textViewData;
        ImageButton buttonFavoritar,btnDeletar;
        OnEventoClickListener onEventoClickListener;

        public ViewHolder(@NonNull View itemView, OnEventoClickListener onEventoClickListener) {

            super(itemView);

            textViewEvento = itemView.findViewById(R.id.card_evento_titulo);
            textViewCidade = itemView.findViewById(R.id.card_evento_local);
            textViewData = itemView.findViewById(R.id.card_evento_data);
            buttonFavoritar = itemView.findViewById(R.id.card_evento_favoritar);
            buttonFavoritar.setOnClickListener(this);

            btnDeletar = itemView.findViewById(R.id.card_evento_deletar);
            btnDeletar.setOnClickListener(this);

            this.onEventoClickListener = onEventoClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(final View view) {

            if (view.getId() == R.id.card_evento_favoritar)
            {
                final FavoritoData favoritoData = (FavoritoData)view.getTag();

                FavoritoService service = RetrofitClient.getClient().create(FavoritoService.class);
                Call<ResponseBody> call;

                if (!favoritoData.isFavorito) {
                    call = service.adicionarFavorito(PreferenceUtils.getToken(view.getContext()), favoritoData.getIdEvento());
                }
                else {
                    call = service.removerFavorito(PreferenceUtils.getToken(view.getContext()), favoritoData.getIdEvento());
                }

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            eventos.get(favoritoData.listPosition).setFavorito(!favoritoData.isFavorito);
                            Toast.makeText(view.getContext(), "Adicionado aos favoritos", Toast.LENGTH_LONG).show();
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(view.getContext(), "Erro ao se conectar no servidor", Toast.LENGTH_LONG).show();
                    }
                });
            }
            else
            {
                onEventoClickListener.onEventoClick(getAdapterPosition(), view);
            }
        }
    }

    public interface OnEventoClickListener{
        void onEventoClick(int position, View view);
    }

    public class FavoritoData
    {
        private boolean isFavorito;
        private Integer listPosition;
        private Integer idEvento;

        public boolean getIsFavorito() {
            return isFavorito;
        }
        public void setIsFavorito(boolean isFavorito) {
            this.isFavorito = isFavorito;
        }

        public Integer getListPosition() {
            return listPosition;
        }
        public void setListPosition(Integer listPosition) {  this.listPosition = listPosition;  }

        public Integer getIdEvento() {
            return idEvento;
        }
        public void setIdEvento(Integer idEvento) {
            this.idEvento = idEvento;
        }
    }
}
