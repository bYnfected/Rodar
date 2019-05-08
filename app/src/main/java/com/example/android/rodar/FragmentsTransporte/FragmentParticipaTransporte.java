package com.example.android.rodar.FragmentsTransporte;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.rodar.R;

public class FragmentParticipaTransporte extends Fragment {

    private TextView endereco, mensagem, vagas, valor;
    private Button btnConcluir;
    private int idEventoCarona;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_participa_carona, container, false);

  /*      endereco = v.findViewById(R.id.participa_transporte_endereco);
        mensagem = v.findViewById(R.id.participa_transporte_mensagem);
        vagas = v.findViewById(R.id.participa_transporte_vagas);
        valor = v.findViewById(R.id.participa_transporte_valor);
        btnConcluir = v.findViewById(R.id.participa_transporte_concluir);
        btnConcluir.setOnClickListener(concluirListener);*/

        return v;
    }
}
