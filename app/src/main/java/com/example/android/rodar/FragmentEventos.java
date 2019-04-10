package com.example.android.rodar;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.rodar.Utils.PreferenceUtils;
import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.activities.IMainActivity;
import com.example.android.rodar.models.Evento;
import com.example.android.rodar.models.Usuario;
import com.example.android.rodar.services.EventoService;
import com.example.android.rodar.services.UsuarioService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentEventos extends Fragment {

    private IMainActivity mainActivity;
    private FloatingActionButton btnCadastro;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_eventos, container, false);

        btnCadastro = v.findViewById(R.id.cria_evento_btn);
        btnCadastro.setOnClickListener(criaEventoListener);

        InicilizaLista();

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (IMainActivity) getActivity();
    }
    private View.OnClickListener criaEventoListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            mainActivity.inflateFragment("eventos_criaEvento","");
        }
    };

    private void InicilizaLista(){


        EventoService service = RetrofitClient.getClient().create(EventoService.class);
        Call<List<Evento>> call = service.getEventos(PreferenceUtils.getToken(getContext()));
        call.enqueue(new Callback<List<Evento>>() {
            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "CARREGOU EVENTOS", Toast.LENGTH_LONG).show();
                    List<Evento> eventos = response.body();
                    RecyclerView recyclerView = getView().findViewById(R.id.recycler_view_eventos);

                    AdapterListaEventos adapter = new AdapterListaEventos(getView().getContext(),eventos);

                    recyclerView.setAdapter(adapter);

                    recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));

                }
            }

            @Override
            public void onFailure(Call<List<Evento>> call, Throwable t) {
                Toast.makeText(getContext(), "ERRO FALHA", Toast.LENGTH_LONG).show();
            }
        });
    }
}

