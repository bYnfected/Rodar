package com.example.android.rodar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.Utils.SPUtil;
import com.example.android.rodar.activities.IMainActivity;
import com.example.android.rodar.adapters.AdapterListaEventos;
import com.example.android.rodar.adapters.AdapterListaMensagens;
import com.example.android.rodar.adapters.AdapterTranspCarona;
import com.example.android.rodar.models.Carona;
import com.example.android.rodar.models.Evento;
import com.example.android.rodar.models.MensagemCarona;
import com.example.android.rodar.models.MensagemTransporte;
import com.example.android.rodar.models.Transporte;
import com.example.android.rodar.services.CaronaService;
import com.example.android.rodar.services.EventoService;
import com.example.android.rodar.services.TransporteService;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMensagens extends Fragment {

    private IMainActivity mainActivity;
    private List<Object> mListaMensagens;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mensagens, container, false);
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
        Call<List<MensagemCarona>> callC = cService.getMensagensEnviadasUsuario(SPUtil.getToken(getContext()));
        callC.enqueue(new Callback<List<MensagemCarona>>() {
            @Override
            public void onResponse(Call<List<MensagemCarona>> call, Response<List<MensagemCarona>> response) {
                if (response.isSuccessful()) {
                    mListaMensagens.addAll(response.body());
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
        Call<List<MensagemTransporte>> callT = tService.getMensagensEnviadasUsuario(SPUtil.getToken(getContext()));
        callT.enqueue(new Callback<List<MensagemTransporte>>() {
            @Override
            public void onResponse(Call<List<MensagemTransporte>> call, Response<List<MensagemTransporte>> response) {

                if (response.isSuccessful()){

                    mListaMensagens.addAll(response.body());

                    if (mListaMensagens.size() > 0){
                        RecyclerView recyclerView = getView().findViewById(R.id.fragment_mensagens_recycler);
                        AdapterListaMensagens adapter = new AdapterListaMensagens(mListaMensagens,listener);
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

    AdapterListaMensagens.OnMensagemClickListener listener = new AdapterListaMensagens.OnMensagemClickListener() {
        @Override
        public void onMensagemClick(int position) {

            //Bundle bundle = new Bundle();
            //bundle.putBoolean("ativo",true);
            //if (mAtivos.get(position) instanceof Carona){
                //bundle.putSerializable("carona", (Carona) mAtivos.get(position));
                //mainActivity.inflateFragment("participaCarona",bundle);
            //} else if (mAtivos.get(position) instanceof Transporte) {
                //bundle.putSerializable("transporte", (Transporte) mAtivos.get(position));
                //mainActivity.inflateFragment("participaTransporte",bundle);
            //}
        }
    };
}
