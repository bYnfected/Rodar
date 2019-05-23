package com.example.android.rodar.FragmentsTransporte;

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
import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.Utils.SPUtil;
import com.example.android.rodar.adapters.AdapterPassageiros;
import com.example.android.rodar.models.Transporte;
import com.example.android.rodar.models.Usuario;
import com.example.android.rodar.services.TransporteService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentParticipaTransporte extends Fragment {

    private TextView endereco, mensagem, vagas, valor, vagasTotal;
    private Button btnConcluir;
    private Transporte mTransporte;
    private AdapterPassageiros mAdapterPassageiros;
    private RecyclerView recyclerViewPassageiros;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_participa_transporte, container, false);

        mTransporte = (Transporte) getArguments().getSerializable("transporte");

        endereco = v.findViewById(R.id.participa_transporte_endereco);
        mensagem = v.findViewById(R.id.participa_transporte_mensagem);
        vagas = v.findViewById(R.id.participa_transporte_vagas_disp);
        vagasTotal = v.findViewById(R.id.participa_transporte_vagas_total);
        valor = v.findViewById(R.id.participa_transporte_valor);
        btnConcluir = v.findViewById(R.id.participa_transporte_concluir);
        recyclerViewPassageiros = v.findViewById(R.id.participa_transporte_passageiros);

        endereco.setText(mTransporte.getEnderecoPartidaRua() + ", " + mTransporte.getEnderecoPartidaNumero() +
                ", " + mTransporte.getEnderecoPartidaCEP() + ", " + mTransporte.getEnderecoPartidaBairro() +
                " - " + mTransporte.getEnderecoPartidaCidade() + " - " + mTransporte.getEnderecoPartidaUF());


        mensagem.setText(mTransporte.getMensagem());
        vagas.setText(mTransporte.getQuantidadeVagasDisponiveis().toString());
        vagasTotal.setText(mTransporte.getQuantidadeVagas().toString());
        valor.setText(String.format("R$ %.2f", mTransporte.getValorParticipacao()));

        mAdapterPassageiros = new AdapterPassageiros(mTransporte.getPassageiros());
        recyclerViewPassageiros.setAdapter(mAdapterPassageiros);
        recyclerViewPassageiros.setLayoutManager(new LinearLayoutManager(getContext()));


        // Se é o motorista pode excluir, se esta participando pode cancelar, se esta lotado informa
        if (mTransporte.getIdUsuarioTransportador() == SPUtil.getID(getContext())) {
            btnConcluir.setText("Excluir Transporte");
            btnConcluir.setOnClickListener(excluirListener);
        } else if (participando()) {
            btnConcluir.setText("Cancelar Participação");
            btnConcluir.setOnClickListener(cancelarListener);
        } else if (mTransporte.getQuantidadeVagasDisponiveis() == 0) {
            btnConcluir.setText("Transporte Lotado");
            btnConcluir.setEnabled(false);
        } else {
            btnConcluir.setOnClickListener(concluirListener);
        }


        return v;
    }

    private boolean participando() {
        if (mTransporte.getPassageiros() != null) {
            for (Usuario usuario : mTransporte.getPassageiros()) {
                if (usuario.getIdUsuario() == SPUtil.getID(getContext()))
                    return true;
            }
        }
        return false;
    }


    View.OnClickListener concluirListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TransporteService service = RetrofitClient.getClient().create(TransporteService.class);
            Call<ResponseBody> call = service.participarTransporte(SPUtil.getToken(getContext()), mTransporte.getIdEventoTransporte());

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
            TransporteService service = RetrofitClient.getClient().create(TransporteService.class);
            Call<ResponseBody> call = service.sairTransporte(SPUtil.getToken(getContext()), mTransporte.getIdEventoTransporte());
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
            TransporteService service = RetrofitClient.getClient().create(TransporteService.class);
            Call<ResponseBody> call = service.deleteTransporte(SPUtil.getToken(getContext()), mTransporte.getIdEventoTransporte());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "TRANSPORTE EXCLUIDO", Toast.LENGTH_LONG).show();
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
