package com.example.android.rodar;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.rodar.Utils.PreferenceUtils;
import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.activities.IMainActivity;
import com.example.android.rodar.models.Evento;
import com.example.android.rodar.services.EventoService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentEventos extends Fragment {

    private IMainActivity mainActivity;
    private FloatingActionButton btnCadastro;
    private List<Evento> eventos;
    private boolean somenteMeusEventos = false;
    private boolean somenteMeusFavoritos = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_pesquisa_eventos, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.menu_eventos_meuseventos:
                if(item.isChecked()){
                    item.setChecked(false);
                }else{
                    item.setChecked(true);
                }

                somenteMeusEventos = item.isChecked();
                CarregarEventos();

                break;
            case R.id.menu_eventos_favoritos:
                if(item.isChecked()){
                    item.setChecked(false);
                }else{
                    item.setChecked(true);
                }

                somenteMeusFavoritos = item.isChecked();
                CarregarEventos();

                break;
            case R.id.menu_eventos_filtros:
                mainActivity.inflateFragment("eventos_pesquisaEvento","");
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_eventos, container, false);

        btnCadastro = v.findViewById(R.id.cria_evento_btn);
        btnCadastro.setOnClickListener(criaEventoListener);

        CarregarEventos();

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

    private void CarregarEventos(){

        EventoService service = RetrofitClient.getClient().create(EventoService.class);
        Call<List<Evento>> call = service.getEventos(PreferenceUtils.getToken(getContext()), somenteMeusEventos, somenteMeusFavoritos);
        call.enqueue(new Callback<List<Evento>>() {
            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "CARREGOU EVENTOS", Toast.LENGTH_LONG).show();
                    eventos = response.body();
                    RecyclerView recyclerView = getView().findViewById(R.id.recycler_view_eventos);
                    AdapterListaEventos adapter = new AdapterListaEventos(getView().getContext(),eventos, teste);
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

    AdapterListaEventos.OnEventoClickListener teste = new AdapterListaEventos.OnEventoClickListener() {
        @Override
        public void onEventoClick(int position) {
            FragmentEventoDetalhe fragmentEventoDetalhe = new FragmentEventoDetalhe(eventos.get(position).getNomeEvento());
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragmentEventoDetalhe);
            transaction.addToBackStack(null);
            transaction.commit();

            // manda pro fragment de evento detalhado
            // exemplo array.get(position) pq é o mesmo que ta na lista
            Log.d("a", "onEventoClick: clicado" + position);
        }
    };


}

