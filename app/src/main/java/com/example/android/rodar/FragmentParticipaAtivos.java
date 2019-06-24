package com.example.android.rodar;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.Utils.SPUtil;
import com.example.android.rodar.activities.IMainActivity;
import com.example.android.rodar.adapters.AdapterTranspCarona;
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

    private IMainActivity mainActivity;
    private List<Object> mAtivos;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_participa_ativos_historico, container, false);
        mAtivos = new ArrayList<>();
        CarregaAtivos();

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (IMainActivity) getActivity();
    }

    private void CarregaAtivos() {
        CaronaService cService = RetrofitClient.getClient().create(CaronaService.class);
        Call<List<Carona>> callC = cService.getAtivos(SPUtil.getToken(getContext()));
        callC.enqueue(new Callback<List<Carona>>() {
            @Override
            public void onResponse(Call<List<Carona>> call, Response<List<Carona>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mAtivos.addAll(response.body());
                        CarregaTransportes();
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Carona>> call, Throwable t) {
                Toast.makeText(getContext(), "FALHA AO CARREGAR", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void CarregaTransportes() {
        TransporteService tService = RetrofitClient.getClient().create(TransporteService.class);
        Call<List<Transporte>> callT = tService.getAtivos(SPUtil.getToken(getContext()));
        callT.enqueue(new Callback<List<Transporte>>() {
            @Override
            public void onResponse(Call<List<Transporte>> call, Response<List<Transporte>> response) {
                if (response.isSuccessful()){
                    mAtivos.addAll(response.body());
                    if (mAtivos.size() > 0){
                        RecyclerView recyclerView = getView().findViewById(R.id.fragment_participa_ativosHistorico_recycler);
                        AdapterTranspCarona adapter = new AdapterTranspCarona(mAtivos,listener);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Transporte>> call, Throwable t) {
                Toast.makeText(getContext(), "FALHA AO CARREGAR", Toast.LENGTH_LONG).show();
            }
        });
            }

    AdapterTranspCarona.OnTranspCaronaClickListener listener = new AdapterTranspCarona.OnTranspCaronaClickListener() {
        @Override
        public void onTranspCaronaClick(int position) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("ativo",true);
            if (mAtivos.get(position) instanceof Carona){
                bundle.putSerializable("carona", (Carona) mAtivos.get(position));
                mainActivity.inflateFragment("participaCarona",bundle);
            } else if (mAtivos.get(position) instanceof Transporte) {
                bundle.putSerializable("transporte", (Transporte) mAtivos.get(position));
                mainActivity.inflateFragment("participaTransporte",bundle);
            }
        }
    };

}
