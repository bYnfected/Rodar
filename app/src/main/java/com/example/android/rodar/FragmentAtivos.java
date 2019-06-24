package com.example.android.rodar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.Utils.SPUtil;
import com.example.android.rodar.models.Carona;
import com.example.android.rodar.services.CaronaService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAtivos extends Fragment {

    private List<Object> mAtivos = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ativos,container,false);
        CarregaAtivos();
        return v;
    }

    private void CarregaAtivos() {
/*        TransporteService tService = RetrofitClient.getClient().create(TransporteService.class);
        Call<List<Transporte>> callT = tService.getAtivos(SPUtil.getToken(getContext()));
        callT.enqueue(new Callback<List<Transporte>>() {
            @Override
            public void onResponse(Call<List<Transporte>> call, Response<List<Transporte>> response) {
                if (response.isSuccessful()){
                    mAtivos.addAll(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Transporte>> call, Throwable t) {
                Toast.makeText(getContext(), "FALHA AO CARREGAR TRASNPORTES", Toast.LENGTH_LONG).show();
            }
        });*/


        CaronaService cService = RetrofitClient.getClient().create(CaronaService.class);
        Call<List<Carona>> callC = cService.getAtivos(SPUtil.getToken(getContext()));
        callC.enqueue(new Callback<List<Carona>>() {
            @Override
            public void onResponse(Call<List<Carona>> call, Response<List<Carona>> response) {
                if (response.isSuccessful()) {
                    mAtivos.addAll(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Carona>> call, Throwable t) {
                Toast.makeText(getContext(), "FALHA AO CARREGAR CARONAS", Toast.LENGTH_LONG).show();
            }
        });

    }
}
