package com.example.android.rodar.FragmentsTransporte;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.android.rodar.R;

public class FragmentPesquisaTransporte extends Fragment {

    private Button btnPesquisar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_pesquisa_carona, container, false);

        btnPesquisar = v.findViewById(R.id.pesquisa_evento_btnPesquisar);
        //btnPesquisar.setOnClickListener(pesquisarListener);

        return v;
    }

}
