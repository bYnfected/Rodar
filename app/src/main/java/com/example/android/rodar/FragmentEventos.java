package com.example.android.rodar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FragmentEventos extends Fragment {

    private ArrayList<String> mEventos = new ArrayList<>();
    private ArrayList<String> mCidades = new ArrayList<>();
    private ArrayList<String> mDatas = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_eventos, container, false);
        InicilizaLista();
        RecyclerView recyclerView = v.findViewById(R.id.recycler_view_eventos);

        AdapterListaEventos adapter = new AdapterListaEventos(mEventos,mCidades,mDatas,this.getContext());

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void InicilizaLista(){
        mEventos.add("Festa da Uva");
        mEventos.add("Show Racionais MC");
        mEventos.add("Show Comunidade Nin Jitsu");
        mEventos.add("Mano Lima ft Gelain");

        mCidades.add("Caxias do Sul");
        mCidades.add("Caxias do Sul");
        mCidades.add("MALIBU DISCO PUD");
        mCidades.add("BAR DO BRANCHINI");

        mDatas.add("11");
        mDatas.add("12");
        mDatas.add("11/11/2016");
        mDatas.add("04/04/2019");

    }
}

