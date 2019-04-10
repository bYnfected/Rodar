package com.example.android.rodar;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.rodar.R;
import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.models.Evento;
import com.example.android.rodar.services.EventoService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCriaEvento extends Fragment {

    private Button btnConclui;
    private TextInputLayout local, rua, numero, complemento, bairro, cidade, uf, descricao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.cadastro_evento,container,false);

        btnConclui = v.findViewById(R.id.cadastro_evento_but_concluir);
        btnConclui.setOnClickListener(concluirListener);

        local = v.findViewById(R.id.cadastro_evento_local);
        rua = v.findViewById(R.id.cadastro_evento_rua);
        numero = v.findViewById(R.id.cadastro_evento_nr);
        complemento = v.findViewById(R.id.cadastro_evento_complemento);
        bairro = v.findViewById(R.id.cadastro_evento_bairro);
        cidade = v.findViewById(R.id.cadastro_evento_cidade);
        uf = v.findViewById(R.id.cadastro_evento_uf);
        descricao = v.findViewById(R.id.cadastro_evento_desc);

        return v;
    }

    private View.OnClickListener concluirListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            Evento evento = new Evento();
            evento.setEnderecoRua(rua.getEditText().getText().toString());
            evento.setEnderecoNumero(Integer.parseInt(numero.getEditText().getText().toString()));
            evento.setEnderecoComplemento(complemento.getEditText().getText().toString());
            evento.setEnderecoBairro(bairro.getEditText().getText().toString());
            evento.setEnderecoCidade(cidade.getEditText().getText().toString());
            evento.setEnderecoUF(uf.getEditText().getText().toString());
            evento.setDescricaoEvento(descricao.getEditText().getText().toString());


            EventoService service = RetrofitClient.getClient().create(EventoService.class);
            Call<ResponseBody> call = service.createEvento(evento);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        getFragmentManager().popBackStackImmediate();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), "Erro ao se conectar no servidor", Toast.LENGTH_LONG).show();
                }
            });
        }
    };
}
