package com.example.android.rodar.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.rodar.R;
import com.example.android.rodar.models.Usuario;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterPassageiros extends RecyclerView.Adapter<AdapterPassageiros.ViewHolder> {

    private List<Usuario> passageiros;

    public AdapterPassageiros(List<Usuario> passageiros) {
        if (passageiros == null) {
            passageiros = new ArrayList<>();
        }

        this.passageiros = passageiros;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_passageiro, viewGroup, false);
        AdapterPassageiros.ViewHolder holder = new AdapterPassageiros.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.nomePassageiro.setText(passageiros.get(i).getNome() + " " +
                passageiros.get(i).getSobrenome());

        // Verifica se existe selfie e carrega
        String imgUrl = passageiros.get(i).getUrlImagemSelfie();
        if (!imgUrl.isEmpty()){
            Picasso.get().load(viewHolder.itemView.getContext().getString(R.string.url) +
                    imgUrl).into(viewHolder.imagem);
        }
    }

    @Override
    public int getItemCount() {
        return passageiros.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nomePassageiro;
        CircularImageView imagem;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nomePassageiro = itemView.findViewById(R.id.passageiro_nomeCompleto);
            imagem = itemView.findViewById(R.id.passageiro_imagem);
        }
    }
}
