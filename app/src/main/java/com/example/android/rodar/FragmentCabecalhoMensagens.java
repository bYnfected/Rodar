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
import com.example.android.rodar.adapters.AdapterListaCabecalhoMensagens;
import com.example.android.rodar.models.MensagemCarona;
import com.example.android.rodar.models.MensagemTransporte;
import com.example.android.rodar.services.CaronaService;
import com.example.android.rodar.services.TransporteService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCabecalhoMensagens extends Fragment {

    private IMainActivity mainActivity;
    private List<Object> mListaMensagens;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_cabecalhomensagens, container, false);
        mListaMensagens = new ArrayList<>();

        CarregarMensagensCarona();

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (IMainActivity) getActivity();
    }

    private void CarregarMensagensCarona() {
        CaronaService cService = RetrofitClient.getClient().create(CaronaService.class);
        Call<List<MensagemCarona>> callC = cService.getCabecalhoMensagensUsuario(SPUtil.getToken(getContext()));
        callC.enqueue(new Callback<List<MensagemCarona>>() {
            @Override
            public void onResponse(Call<List<MensagemCarona>> call, Response<List<MensagemCarona>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mListaMensagens.addAll(response.body());
                    }

                    CarregarMensagensTransporte();
                }
            }
            @Override
            public void onFailure(Call<List<MensagemCarona>> call, Throwable t) {
                Toast.makeText(getContext(), "FALHA AO CARREGAR", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void CarregarMensagensTransporte() {
        TransporteService tService = RetrofitClient.getClient().create(TransporteService.class);
        Call<List<MensagemTransporte>> callT = tService.getCabecalhoMensagensUsuario(SPUtil.getToken(getContext()));
        callT.enqueue(new Callback<List<MensagemTransporte>>() {
            @Override
            public void onResponse(Call<List<MensagemTransporte>> call, Response<List<MensagemTransporte>> response) {

                if (response.isSuccessful()){
                    if (response.body() != null) {
                        mListaMensagens.addAll(response.body());
                    }

                    if (mListaMensagens.size() > 0) {
                        RecyclerView recyclerView = getView().findViewById(R.id.fragment_cabecalhomensagens_recycler);
                        AdapterListaCabecalhoMensagens adapter = new AdapterListaCabecalhoMensagens(mListaMensagens, listener);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));
                    }
                }
            }
            @Override
            public void onFailure(Call<List<MensagemTransporte>> call, Throwable t) {
                Toast.makeText(getContext(), "FALHA AO CARREGAR", Toast.LENGTH_LONG).show();
            }
        });
    }

    AdapterListaCabecalhoMensagens.OnMensagemClickListener listener = new AdapterListaCabecalhoMensagens.OnMensagemClickListener() {
        @Override
        public void onMensagemClick(int position) {

            Bundle bundle = new Bundle();
            if (mListaMensagens.get(position) instanceof MensagemCarona) {
                bundle.putSerializable("idEventoTransporteCarona", (((MensagemCarona) mListaMensagens.get(position)).idEventoCarona));
                bundle.putSerializable("idUsuarioDestino", (((MensagemCarona) mListaMensagens.get(position)).idUsuarioDestino));
                bundle.putSerializable("tipoTransporteCarona", "Carona");
            }
            else if (mListaMensagens.get(position) instanceof MensagemTransporte){
                bundle.putSerializable("idEventoTransporteCarona", (((MensagemTransporte) mListaMensagens.get(position)).idEventoTransporte));
                bundle.putSerializable("idUsuarioDestino", (((MensagemTransporte) mListaMensagens.get(position)).idUsuarioDestino));
                bundle.putSerializable("tipoTransporteCarona", "Transporte");
            }

            mainActivity.inflateFragment("mensagensUsuario", bundle);
        }
    };
}
