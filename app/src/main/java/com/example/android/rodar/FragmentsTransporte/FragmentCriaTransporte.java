package com.example.android.rodar.FragmentsTransporte;

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
import com.example.android.rodar.Utils.SPUtil;
import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.models.Transporte;
import com.example.android.rodar.services.TransporteService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCriaTransporte extends Fragment {

    private Button btnConclui;
    private TextInputLayout mensagem, rua, numero, complemento, bairro, cidade, uf, valor, vagas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cadastro_transporte,container,false);

        btnConclui = v.findViewById(R.id.cadastro_transporte_concluir);
        btnConclui.setOnClickListener(concluirListener);

        mensagem = v.findViewById(R.id.cadastro_transporte_mensagem);
        rua = v.findViewById(R.id.cadastro_transporte_rua);
        numero = v.findViewById(R.id.cadastro_transporte_num);
        complemento = v.findViewById(R.id.cadastro_transporte_complemento);
        bairro = v.findViewById(R.id.cadastro_transporte_bairro);
        cidade = v.findViewById(R.id.cadastro_transporte_cidade);
        uf = v.findViewById(R.id.cadastro_transporte_uf);
        valor = v.findViewById(R.id.cadastro_transporte_valor);
        vagas = v.findViewById(R.id.cadastro_transporte_vagas);

        return v;
    }

    private View.OnClickListener concluirListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if (validaCampos()){
                Transporte transporte = new Transporte();
                transporte.setMensagem(mensagem.getEditText().getText().toString());
                transporte.setEnderecoPartidaRua(rua.getEditText().getText().toString());
                transporte.setEnderecoPartidaNumero(Integer.parseInt(numero.getEditText().getText().toString()));
                if (!complemento.getEditText().getText().toString().isEmpty()){
                    transporte.setEnderecoPartidaComplemento(complemento.getEditText().getText().toString());
                }
                transporte.setEnderecoPartidaBairro(bairro.getEditText().getText().toString());
                transporte.setEnderecoPartidaCidade(cidade.getEditText().getText().toString());
                transporte.setEnderecoPartidaUF(uf.getEditText().getText().toString());
                transporte.setValorParticipacao(Double.parseDouble(valor.getEditText().getText().toString()));
                transporte.setQuantidadeVagas(Integer.parseInt(vagas.getEditText().getText().toString()));
                transporte.setIdEvento(16);

                TransporteService service = RetrofitClient.getClient().create(TransporteService.class);
                Call<ResponseBody> call = service.createTransporte(SPUtil.getToken(getContext()), transporte);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Transporte cadastrado", Toast.LENGTH_LONG).show();
                            getFragmentManager().popBackStackImmediate();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });


            }
        }
    };

    private boolean validaCampos(){
        boolean ok = true;

        mensagem.setError(null);
        rua.setError(null);
        numero.setError(null);
        bairro.setError(null);
        cidade.setError(null);
        uf.setError(null);
        valor.setError(null);

        if (mensagem.getEditText().getText().toString().isEmpty()) {
            mensagem.setError("Campo obrigatório");
            ok = false;
        }
        if (rua.getEditText().getText().toString().isEmpty()) {
            rua.setError("Campo obrigatório");
            ok = false;
        }
        if (numero.getEditText().getText().toString().isEmpty()) {
            numero.setError("Campo obrigatório");
            ok = false;
        }
        if (bairro.getEditText().getText().toString().isEmpty()) {
            bairro.setError("Campo obrigatório");
            ok = false;
        }
        if (cidade.getEditText().getText().toString().isEmpty()) {
            cidade.setError("Campo obrigatório");
            ok = false;
        }
        if (uf.getEditText().getText().toString().isEmpty()) {
            uf.setError("Campo obrigatório");
            ok = false;
        }
        if (valor.getEditText().getText().toString().isEmpty()) {
            valor.setError("Campo obrigatório");
            ok = false;
        }
        try {
            int i = Integer.parseInt(vagas.getEditText().getText().toString());
            if (!(i > 0) && (i < 45)){
                vagas.setError("Valor entre 1 e 45");
                ok = false;
            }
        }
        catch (NumberFormatException nfe){
            vagas.setError("Entre 1 e 45");
            ok = false;
        }

        return ok;
    }
}
