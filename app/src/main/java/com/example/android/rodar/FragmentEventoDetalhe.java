package com.example.android.rodar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.rodar.R;

@SuppressLint("ValidFragment")
public class FragmentEventoDetalhe extends Fragment {

    CollapsingToolbarLayout teste;
    private String testeTitulo;

    @SuppressLint("ValidFragment")
    public FragmentEventoDetalhe(String testeTitulo) {
        this.testeTitulo = testeTitulo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_evento_detalhe, container, false);

        teste = v.findViewById(R.id.evento_detalhe_titulo);

        teste.setTitle(testeTitulo);

        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();

    }
}
