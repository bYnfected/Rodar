package com.example.android.rodar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.android.rodar.activities.IMainActivity;

public class FragmentPesquisaEventos extends Fragment {

    private IMainActivity mainActivity;
    AutoCompleteTextView autoCompleteTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pesquisa_evento, container, false);
        autoCompleteTextView = (AutoCompleteTextView)view.findViewById(R.id.pesquisa_evento_cidade);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_dropdown_item_1line, CIDADES);

        autoCompleteTextView.setAdapter(adapter);

        return view;
    }

    private static final String[] CIDADES  = new String[] {
            "Caxias do Sul", "Bento Gonçalves", "Farroupilha", "Flores da Cunha", "Gramado"
    };



}
