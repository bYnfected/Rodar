package com.example.android.rodar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FragmentInicial extends Fragment {

    private Button cadastra,entra;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.pagina_inicial, container, false);
        cadastra = v.findViewById(R.id.inicial_cadastrar) ;
        entra = v.findViewById(R.id.inicial_entrar);

        cadastra.setOnClickListener(cadastraListener);
        entra.setOnClickListener(entraListener);

        return v;
    }


    private View.OnClickListener cadastraListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.inicial_fragment_container, new FragmentCadastro());
            transaction.addToBackStack(null);
            transaction.commit();
        }
    };

    private View.OnClickListener entraListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.inicial_fragment_container, new FragmentLogin());
            transaction.addToBackStack(null);
            transaction.commit();
        }
    };
}
