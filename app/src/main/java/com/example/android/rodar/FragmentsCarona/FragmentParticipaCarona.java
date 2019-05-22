package com.example.android.rodar.FragmentsCarona;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.rodar.R;
import com.example.android.rodar.Utils.SPUtil;
import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.adapters.AdapterPassageiros;
import com.example.android.rodar.models.Carona;
import com.example.android.rodar.models.Usuario;
import com.example.android.rodar.services.CaronaService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentParticipaCarona extends Fragment {

    private TextView endereco, mensagem, vagas, valor, vagasTotal;
    private Button btnConcluir;
    private Carona mCarona;
    private AdapterPassageiros mAdapterPassageiros;
    private RecyclerView recyclerViewPassageiros;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_participa_carona, container, false);

        mCarona = (Carona) getArguments().getSerializable("carona");

        endereco = v.findViewById(R.id.participa_carona_endereco);
        mensagem = v.findViewById(R.id.participa_carona_mensagem);
        vagas = v.findViewById(R.id.participa_carona_vagas_disp);
        vagasTotal = v.findViewById(R.id.participa_carona_vagas_total);
        valor = v.findViewById(R.id.participa_carona_valor);
        btnConcluir = v.findViewById(R.id.participa_carona_concluir);
        recyclerViewPassageiros = v.findViewById(R.id.participa_carona_passageiros);

        endereco.setText(mCarona.getEnderecoPartidaRua() + ", " + mCarona.getEnderecoPartidaNumero() +
                ", " + mCarona.getEnderecoPartidaCEP() + ", " + mCarona.getEnderecoPartidaBairro() +
                " - " + mCarona.getEnderecoPartidaCidade() + " - " + mCarona.getEnderecoPartidaUF());


        mensagem.setText(mCarona.getMensagem());
        vagas.setText(mCarona.getQuantidadeVagasDisponiveis().toString());
        vagasTotal.setText(mCarona.getQuantidadeVagas().toString());
        valor.setText(String.format("R$ %.2f",mCarona.getValorParticipacao()));

        mAdapterPassageiros = new AdapterPassageiros(mCarona.getPassageiros());
        recyclerViewPassageiros.setAdapter(mAdapterPassageiros);
        recyclerViewPassageiros.setLayoutManager(new LinearLayoutManager(getContext()));


        // Se é o motorista pode excluir, se esta participando pode cancelar, se esta lotado informa
        if (mCarona.getIdUsuarioMotorista() == SPUtil.getID(getContext())){
            btnConcluir.setText("Excluir Carona");
            btnConcluir.setOnClickListener(excluirListener);
        } else if (participando()) {
            btnConcluir.setText("Cancelar Participação");
            btnConcluir.setOnClickListener(cancelarListener);
        } else if (mCarona.getQuantidadeVagasDisponiveis() == 0){
            btnConcluir.setText("Carona Lotada");
            btnConcluir.setEnabled(false);
        } else {
            btnConcluir.setOnClickListener(concluirListener);
        }


        return v;
    }

    private boolean participando() {
        if (mCarona.getPassageiros() != null){
            for (Usuario usuario : mCarona.getPassageiros()) {
                if (usuario.getIdUsuario() == SPUtil.getID(getContext()))
                    return true;
            }
        }
        return false;
    }


    View.OnClickListener concluirListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CaronaService service = RetrofitClient.getClient().create(CaronaService.class);
            Call<ResponseBody> call = service.participarCarona(SPUtil.getToken(getContext()), mCarona.getIdEventoCarona());

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "PARTICIPAÇÃO CONFIRMADA", Toast.LENGTH_LONG).show();
                        getFragmentManager().popBackStackImmediate();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), "Falha ao conectar ao servidor", Toast.LENGTH_LONG).show();
                }
            });

        }
    };

    View.OnClickListener cancelarListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            CaronaService service = RetrofitClient.getClient().create(CaronaService.class);
            Call<ResponseBody> call = service.sairCarona(SPUtil.getToken(getContext()), mCarona.getIdEventoCarona());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "PARTICIPAÇÃO CANCELADA", Toast.LENGTH_LONG).show();
                        getFragmentManager().popBackStackImmediate();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), "Falha ao conectar ao servidor", Toast.LENGTH_LONG).show();
                }
            });

        }
    };

    View.OnClickListener excluirListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // FALTA ADICIONAR A CONFIRMACAO AQUI
            CaronaService service = RetrofitClient.getClient().create(CaronaService.class);
            Call<ResponseBody> call = service.deleteCarona(SPUtil.getToken(getContext()), mCarona.getIdEventoCarona());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()){
                        Toast.makeText(getContext(), "CARONA EXCLUIDA", Toast.LENGTH_LONG).show();
                        getFragmentManager().popBackStackImmediate();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), "Falha ao conectar ao servidor", Toast.LENGTH_LONG).show();
                }
            });

        }
    };


}
