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

import com.example.android.rodar.models.Usuario;
import com.example.android.rodar.services.UsuarioService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCadastro extends Fragment {

    private Button btnConclui;
    private TextInputLayout nome, sobrenome, genero, cpf, celular, email, senha, senhaConfirma;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cria_cadastro, container, false);

        btnConclui = v.findViewById(R.id.cadastro_concluir);
        btnConclui.setOnClickListener(concluiListener);

        nome = v.findViewById(R.id.cadastro_nome);
        sobrenome = v.findViewById(R.id.cadastro_sobrenome);
        cpf = v.findViewById(R.id.cadastro_cpf);
        celular = v.findViewById(R.id.cadastro_celular);
        email = v.findViewById(R.id.cadastro_email);
        senha = v.findViewById(R.id.cadastro_senha);
        senhaConfirma = v.findViewById(R.id.cadastro_senha2);

        return v;
    }


    private View.OnClickListener concluiListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Usuario novoUsuario = new Usuario();
            novoUsuario.setNomeCompleto(nome.getEditText().getText().toString() + sobrenome.getEditText().getText().toString());
            novoUsuario.setCPF(cpf.getEditText().getText().toString());
            novoUsuario.setNumeroTelefone(celular.getEditText().getText().toString());
            novoUsuario.setEmail(email.getEditText().getText().toString());
            novoUsuario.setSenha(senha.getEditText().getText().toString());

            UsuarioService usrService = RetrofitClient.getClient().create(UsuarioService.class);

            Call<Integer> call = usrService.createUser(novoUsuario);

            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                    Toast.makeText(getContext(), response.toString(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                    Toast.makeText(getContext(), "ERRO FALHA", Toast.LENGTH_LONG).show();
                }
            });
        }
    };

}
