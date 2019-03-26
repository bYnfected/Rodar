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

public class FragmentCadastro extends Fragment {

    private Button btnCadastra;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cadastro, container, false);

        btnCadastra = v.findViewById(R.id.cadastro_por_email);

        btnCadastra.setOnClickListener(cadastraListener);
        return v;
    }

    private View.OnClickListener cadastraListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.inicial_fragment_container, new FragmentCadastroEmail());
            transaction.addToBackStack(null);
            transaction.commit();
        }
    };
}
