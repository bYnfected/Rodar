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
import com.example.android.rodar.models.Evento;
import com.example.android.rodar.services.EventoService;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentEventos extends Fragment {

    private IMainActivity mainActivity;
    private RecyclerView mRecyclerView;
    private FloatingActionButton btnCadastro;
    private List<Evento> mEventos;
    private boolean somenteMeusEventos = false;
    private boolean somenteMeusFavoritos = false;
    private AdapterListaEventos mEventosAdapter;


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
                mainActivity.inflateFragment("eventos_pesquisaEvento",null);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_eventos, container, false);

        mRecyclerView = v.findViewById(R.id.recycler_view_eventos);
        btnCadastro = v.findViewById(R.id.cria_evento_btn);
        btnCadastro.setOnClickListener(criaEventoListener);

        if (SPUtil.getOrganizador(getContext()))
            btnCadastro.show();

        if (getArguments() != null){
            mEventos = (List<Evento>) getArguments().getSerializable("eventos");
            preencheRecycler();
        } else {
            CarregarEventos();
        }

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
            mainActivity.inflateFragment("eventos_criaEvento",null);
        }
    };

    private void CarregarEventos(){

        EventoService service = RetrofitClient.getClient().create(EventoService.class);
        Call<List<Evento>> call = service.getEventos(SPUtil.getToken(getContext()),
                somenteMeusEventos, somenteMeusFavoritos,null,null,null);
        call.enqueue(new Callback<List<Evento>>() {
            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "CARREGOU EVENTOS", Toast.LENGTH_LONG).show();
                    mEventos = response.body();
                    preencheRecycler();
                }
            }

            @Override
            public void onFailure(Call<List<Evento>> call, Throwable t) {
                Toast.makeText(getContext(), "ERRO FALHA", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void preencheRecycler() {
        mEventosAdapter = new AdapterListaEventos(mEventos, teste);
        mRecyclerView.setAdapter(mEventosAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    AdapterListaEventos.OnEventoClickListener teste = new AdapterListaEventos.OnEventoClickListener() {
        @Override
        public void onEventoClick(int position, View view) {
            if (view.getId() == R.id.card_evento_deletar) {
                deletarEvento(position);
            } else {
                Bundle bundle = new Bundle();
                bundle.putInt("id", mEventos.get(position).getIdEvento());
                bundle.putSerializable("evento", mEventos.get(position));
                mainActivity.inflateFragment("evento_detalhe",bundle);
            }
        }
    };

    private void deletarEvento(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Deseja excluir esse evento? Todas os transportes e " +
                "caronas para ele serão excluídos");
        builder.setNegativeButton("Não", null);
        builder.setCancelable(true);
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EventoService service = RetrofitClient.getClient().create(EventoService.class);
                Call<ResponseBody> call = service.deleteEvento(SPUtil.getToken(getContext()),
                        mEventos.get(position).getIdEvento());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getContext(), "Evento deletado", Toast.LENGTH_LONG).show();
                            mEventos.remove(position);
                            mEventosAdapter.notifyItemRemoved(position);
                            mEventosAdapter.notifyItemRangeChanged(position, mEventos.size());
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), "Erro ao se conectar no servidor", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        builder.show();
    }

};