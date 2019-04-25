package com.example.android.rodar.FragmentsPerfil;

import android.content.Context;
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
import com.example.android.rodar.Utils.PreferenceUtils;
import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.activities.IMainActivity;
import com.example.android.rodar.models.Usuario;
import com.example.android.rodar.services.UsuarioService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentMeusDados extends Fragment {

    Button btnSalvar;
    private TextInputLayout nome, sobrenome, cpf, celular, email, senha, senhaConfirma, descricao;
    private IMainActivity mainActivity;
    Usuario user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.atualiza_cadastro,container,false);

        btnSalvar = v.findViewById(R.id.atualiza_salvar);
        btnSalvar.setOnClickListener(salvarListener);

        nome = v.findViewById(R.id.atualiza_nome);
        sobrenome = v.findViewById(R.id.atualiza_sobrenome);
        cpf = v.findViewById(R.id.atualiza_cpf);
        celular = v.findViewById(R.id.atualiza_celular);
        email = v.findViewById(R.id.atualiza_email);
        senha = v.findViewById(R.id.atualiza_senha);
        senhaConfirma = v.findViewById(R.id.atualiza_senha2);
        descricao = v.findViewById(R.id.atualiza_bio);

        nome.getEditText().setEnabled(false);
        sobrenome.getEditText().setEnabled(false);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        carregaDados();
    }

    private void carregaDados() {
        UsuarioService service = RetrofitClient.getClient().create(UsuarioService.class);
        Call<Usuario> call = service.getUser(PreferenceUtils.getToken(getContext()));
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()){
                    user = response.body();

                    nome.getEditText().setText(user.getNome());
                    sobrenome.getEditText().setText(user.getSobrenome());
                    cpf.getEditText().setText(user.getCPF());
                    celular.getEditText().setText(user.getNumeroTelefone());
                    email.getEditText().setText(user.getEmail());
                    senha.getEditText().setText(user.getSenha());
                    senhaConfirma.getEditText().setText(user.getSenha());
                    descricao.getEditText().setText(user.getDescricao());

                }
                else {
                    Toast.makeText(getContext(), "ERRO NOT SUCCESSFUL", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getContext(), "ERRO FALHA CONEXAO", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (IMainActivity) getActivity();
    }

    private View.OnClickListener salvarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (validaSenha()){
            user.setNumeroTelefone(celular.getEditText().getText().toString());
            user.setDescricao(descricao.getEditText().getText().toString());
            user.setSenha(senha.getEditText().getText().toString());

            UsuarioService service = RetrofitClient.getClient().create(UsuarioService.class);
            Call<ResponseBody> call = service.updateUser(PreferenceUtils.getToken(getContext()),user);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getContext(), "Atualizado com sucesso", Toast.LENGTH_LONG).show();
                        mainActivity.inflateFragment("perfil",null);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), "Erro ao se conectar no servidor", Toast.LENGTH_LONG).show();
                }
            });
            }
        }

        private boolean validaSenha() {
            senha.setError(null);
            senhaConfirma.setError(null);

            boolean retorno = true;
            if (senha.getEditText().getText().toString().isEmpty()){
                senha.setError("Informe a senha");
                retorno = false;
            }
            if (senhaConfirma.getEditText().getText().toString().isEmpty()) {
                senhaConfirma.setError("Confirme a senha");
                retorno = false;
            }
            if (!senha.getEditText().getText().toString().equals(senhaConfirma.getEditText().getText().toString())) {
                senha.setError("As senhas são diferentes");
                senhaConfirma.setError("As senhas são diferentes");
                retorno = false;
            }
            return retorno;
        }
    };
}
