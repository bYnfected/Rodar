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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.Utils.SPUtil;
import com.example.android.rodar.activities.IMainActivity;

import com.example.android.rodar.adapters.AdapterListaMensagens;

import com.example.android.rodar.models.MensagemCarona;
import com.example.android.rodar.models.MensagemTransporte;

import com.example.android.rodar.services.CaronaService;
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
    private int idEventoTransporteCarona;
    private int idUsuarioDestino;
    private int idUsuarioOrigem;
    private String tipoTransporteCarona;
    Button buttonEnviarMensagem;
    EditText textBoxMensagem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_mensagens, container, false);

        idUsuarioOrigem = (int) getArguments().getSerializable("idUsuarioOrigem");
        idUsuarioDestino = (int) getArguments().getSerializable("idUsuarioDestino");
        idEventoTransporteCarona = (int) getArguments().getSerializable("idEventoTransporteCarona");
        tipoTransporteCarona = (String) getArguments().getSerializable("tipoTransporteCarona");

        mListaMensagens = new ArrayList<>();

        buttonEnviarMensagem = v.findViewById(R.id.fragment_mensagens_botao_enviar);
        buttonEnviarMensagem.setOnClickListener(enviarMensagemListener);

        textBoxMensagem = v.findViewById(R.id.fragment_mensagens_textbox_mensagem);

        if (tipoTransporteCarona == "Carona")
            CarregarMensagensCarona();
        else if (tipoTransporteCarona == "Transporte")
            CarregarMensagensTransporte();

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (IMainActivity) getActivity();
    }

    private void CarregarMensagensCarona() {
        CaronaService cService = RetrofitClient.getClient().create(CaronaService.class);
        Call<List<MensagemCarona>> callC = cService.getMensagensUsuario(SPUtil.getToken(getContext()), idEventoTransporteCarona);
        callC.enqueue(new Callback<List<MensagemCarona>>() {
            @Override
            public void onResponse(Call<List<MensagemCarona>> call, Response<List<MensagemCarona>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mListaMensagens.addAll(response.body());

                        if (mListaMensagens.size() > 0) {
                            RecyclerView recyclerView = getView().findViewById(R.id.fragment_mensagens_recycler);
                            AdapterListaMensagens adapter = new AdapterListaMensagens(mListaMensagens, listener);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<MensagemCarona>> call, Throwable t) {
                Toast.makeText(getContext(), "FALHA AO CARREGAR CAROPNAS", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void CarregarMensagensTransporte() {
        TransporteService cService = RetrofitClient.getClient().create(TransporteService.class);
        Call<List<MensagemTransporte>> callC = cService.getMensagensUsuario(SPUtil.getToken(getContext()), idEventoTransporteCarona);
        callC.enqueue(new Callback<List<MensagemTransporte>>() {
            @Override
            public void onResponse(Call<List<MensagemTransporte>> call, Response<List<MensagemTransporte>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mListaMensagens.addAll(response.body());

                        if (mListaMensagens.size() > 0) {
                            RecyclerView recyclerView = getView().findViewById(R.id.fragment_mensagens_recycler);
                            AdapterListaMensagens adapter = new AdapterListaMensagens(mListaMensagens, listener);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getView().getContext()));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<MensagemTransporte>> call, Throwable t) {
                Toast.makeText(getContext(), "FALHA AO CARREGAR TRANSPORTES", Toast.LENGTH_LONG).show();
            }
        });
    }


    AdapterListaMensagens.OnMensagemClickListener listener = new AdapterListaMensagens.OnMensagemClickListener() {
        @Override
        public void onMensagemClick(int position) {
            Toast.makeText(getContext(), "entrei", Toast.LENGTH_LONG).show();
        }
    };


    private View.OnClickListener enviarMensagemListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if (validaCampos()) {
                if (tipoTransporteCarona == "Carona")
                    EnviarMensagemCarona();
                else if (tipoTransporteCarona == "Transporte")
                    EnviarMensagemTransporte();
            }
        }
    };

    private void EnviarMensagemCarona()
    {
        MensagemCarona novaMensagem = new MensagemCarona();
        novaMensagem.setIdEventoCarona(idEventoTransporteCarona);
        novaMensagem.setIdUsuarioOrigem(idUsuarioOrigem);
        novaMensagem.setIdUsuarioDestino(idUsuarioDestino);
        novaMensagem.setMensagem(textBoxMensagem.getText().toString());

        CaronaService service = RetrofitClient.getClient().create(CaronaService.class);
        Call<ResponseBody> call = service.enviarMensagem(SPUtil.getToken(getContext()), novaMensagem);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    mListaMensagens = new ArrayList<>();
                    CarregarMensagensCarona();
                    limpaCampos();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Erro ao se conectar no servidor", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void EnviarMensagemTransporte()
    {
        MensagemTransporte novaMensagem = new MensagemTransporte();
        novaMensagem.setIdEventoTransporte(idEventoTransporteCarona);
        novaMensagem.setIdUsuarioOrigem(idUsuarioOrigem);
        novaMensagem.setIdUsuarioDestino(idUsuarioDestino);
        novaMensagem.setMensagem(textBoxMensagem.getText().toString());

        TransporteService service = RetrofitClient.getClient().create(TransporteService.class);
        Call<ResponseBody> call = service.enviarMensagem(SPUtil.getToken(getContext()), novaMensagem);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    //getFragmentManager().popBackStackImmediate();
                    mListaMensagens = new ArrayList<>();
                    CarregarMensagensTransporte();
                    limpaCampos();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Erro ao se conectar no servidor", Toast.LENGTH_LONG).show();
            }
        });
    }


    private boolean validaCampos(){
        boolean ok = true;

        textBoxMensagem.setError(null);

        if (textBoxMensagem.getText().toString().isEmpty()) {
            textBoxMensagem.setError("Campo obrigatório");
            ok = false;
        }
        return ok;
    }

    private void limpaCampos()
    {
        textBoxMensagem.setText("");
    }

}