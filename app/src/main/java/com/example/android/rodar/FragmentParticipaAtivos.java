package com.example.android.rodar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.Utils.SPUtil;
import com.example.android.rodar.adapters.AdapterListaTransportes;
import com.example.android.rodar.models.Carona;
import com.example.android.rodar.models.Transporte;
import com.example.android.rodar.services.CaronaService;
import com.example.android.rodar.services.TransporteService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentParticipaAtivos extends Fragment {

    private List<Object> mAtivos = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_participa_ativos, container, false);

        CarregaAtivos();

        return v;
    }



    private void CarregaAtivos() {
        TransporteService tService = RetrofitClient.getClient().create(TransporteService.class);
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
        });


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


        if (mAtivos.size() > 0){
            RecyclerView recyclerView = getView().findViewById(R.id.fragment_participa_ativos_recycler);
            AdapterListaTransportes adapter = new AdapterListaTransportes(mAtivos,listener);

            R.layout.

        }
    }

    AdapterListaTransportes.OnTransporteClickListener listener =
            new AdapterListaTransportes.OnTransporteClickListener() {
                @Override
                public void onTransporteClick(int position) {
                    if (mAtivos.get(position) instanceof Transporte) {
                        Toast.makeText(getContext(), "TRANSPORTE", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "CARONA", Toast.LENGTH_LONG).show();
                    }

                }
            }
}
