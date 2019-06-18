package com.example.android.rodar.FragmentsTransporte;

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

import com.example.android.rodar.R;
import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.Utils.SPUtil;
import com.example.android.rodar.activities.IMainActivity;
import com.example.android.rodar.adapters.AdapterListaTransportes;
import com.example.android.rodar.models.Transporte;
import com.example.android.rodar.services.TransporteService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentEventoTransportes extends Fragment {

    private IMainActivity mainActivity;
    private List<Transporte> transportes;
    private int idEvento;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_evento_transportes,container,false);
        idEvento = getArguments().getInt("idEvento");

        CarregaTransportes();

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (IMainActivity) getActivity();
    }

    public void CarregaTransportes() {
        TransporteService service = RetrofitClient.getClient().create(TransporteService.class);
        Call<List<Transporte>> call = service.getTransportesEvento(SPUtil.getToken(getContext()),idEvento);
        call.enqueue(new Callback<List<Transporte>>() {
            @Override
            public void onResponse(Call<List<Transporte>> call, Response<List<Transporte>> response) {
                if (response.isSuccessful()){
                    transportes = response.body();
                    RecyclerView recyclerView = getView().findViewById(R.id.recycler_view_evento_detalhe_transportes);
                    AdapterListaTransportes adapter = new AdapterListaTransportes(transportes,listenerTransportes);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));
                }
            }

            @Override
            public void onFailure(Call<List<Transporte>> call, Throwable t) {
                Toast.makeText(getContext(), "FALHA AO CARREGAR TRASNPORTES", Toast.LENGTH_LONG).show();
            }
        });
    }

    AdapterListaTransportes.OnTransporteClickListener listenerTransportes = new AdapterListaTransportes.OnTransporteClickListener() {
        @Override
        public void onTransporteClick(int position) {
            // Listener do card, retorna o objeto do transporte
            Bundle bundle = new Bundle();
            bundle.putBoolean("ativo",true);
            bundle.putSerializable("transporte", transportes.get(position));
            mainActivity.inflateFragment("participaTransporte",bundle);
        }
    };
}
