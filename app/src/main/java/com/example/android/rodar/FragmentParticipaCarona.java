package com.example.android.rodar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class FragmentParticipaCarona extends Fragment {

    private TextView endereco, mensagem, vagas, valor;
    private Button concluir;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_participa_carona, container, false);

        endereco = v.findViewById(R.id.participa_carona_endereco);
        mensagem = v.findViewById(R.id.participa_carona_mensagem);
        vagas = v.findViewById(R.id.participa_carona_vagas);
        valor = v.findViewById(R.id.participa_carona_valor);


        return v;

    }


}
