package com.example.android.rodar.FragmentsTransporte;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.rodar.R;
import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.Utils.SPUtil;
import com.example.android.rodar.adapters.AdapterPassageiros;
import com.example.android.rodar.models.AvaliacaoTransporte;
import com.example.android.rodar.models.Transporte;
import com.example.android.rodar.models.Usuario;
import com.example.android.rodar.services.TransporteService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentParticipaTransporte extends Fragment {

    private TextView endereco, mensagem, vagas, valor, vagasTotal, nomeMotorista;
    private TextInputLayout msgAvaliacao;
    private Button btnConcluir;
    private Transporte mTransporte;
    private AdapterPassageiros mAdapterPassageiros;
    private RecyclerView recyclerViewPassageiros;
    private RatingBar rating, ratingMotorista;
    private CircularImageView imgMotorista;

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
        rating = v.findViewById(R.id.participa_transporte_rating);
        msgAvaliacao = v.findViewById(R.id.participa_transporte_msgAvaliacao);
        ratingMotorista = v.findViewById(R.id.participa_transporte_rating_motorista);
        imgMotorista = v.findViewById(R.id.participa_transporte_img_motorista);
        nomeMotorista = v.findViewById(R.id.participa_transporte_motorista);

        endereco.setText(mTransporte.getEnderecoPartidaRua() + ", " + mTransporte.getEnderecoPartidaNumero() +
                ", " + mTransporte.getEnderecoPartidaCEP() + ", " + mTransporte.getEnderecoPartidaBairro() +
                " - " + mTransporte.getEnderecoPartidaCidade() + " - " + mTransporte.getEnderecoPartidaUF());


        mensagem.setText(mTransporte.getMensagem());
        vagas.setText(mTransporte.getQuantidadeVagasDisponiveis().toString());
        vagasTotal.setText(mTransporte.getQuantidadeVagas().toString());
        valor.setText(String.format("R$ %.2f", mTransporte.getValorParticipacao()));
        String urlImgMotorista = mTransporte.getUsuarioTransportador().getUrlImagemSelfie();
        if (!urlImgMotorista.isEmpty()){
            Picasso.get().load(getString(R.string.url) + urlImgMotorista).into(imgMotorista);
        }
        nomeMotorista.setText(mTransporte.getUsuarioTransportador().getNome() +
                " " + mTransporte.getUsuarioTransportador().getSobrenome());

        mAdapterPassageiros = new AdapterPassageiros(mTransporte.getPassageiros());
        recyclerViewPassageiros.setAdapter(mAdapterPassageiros);
        recyclerViewPassageiros.setLayoutManager(new LinearLayoutManager(getContext()));

        configuraTela();
        return v;
    }

    private void configuraTela() {

        if (getArguments().getBoolean("ativo")){
            rating.setVisibility(View.GONE);
            msgAvaliacao.setVisibility(View.GONE);
            // Configura nota do motorista
            if (mTransporte.getUsuarioTransportador().getAvaliacao() == -1){
                ratingMotorista.setVisibility(View.GONE);
            } else {
                ratingMotorista.setIsIndicator(true);
                ratingMotorista.setNumStars(5);
                ratingMotorista.setStepSize((float) 0.5);
                ratingMotorista.setRating(mTransporte.getUsuarioTransportador().getAvaliacao());
            }
            // Se é o motorista pode excluir, se esta participando pode cancelar, se esta lotado informa
            if (mTransporte.getIdUsuarioTransportador() == SPUtil.getID(getContext())){
                btnConcluir.setText("Excluir transporte");
                btnConcluir.setOnClickListener(excluirListener);
            } else if (participando()) {
                btnConcluir.setText("Cancelar Participação");
                btnConcluir.setOnClickListener(cancelarListener);
            } else if (mTransporte.getQuantidadeVagasDisponiveis() == 0){
                btnConcluir.setText("transporte Lotada");
                btnConcluir.setEnabled(false);
            } else {
                btnConcluir.setOnClickListener(concluirListener);
            }
        } else { // Se a transporte já esta completa
            ratingMotorista.setVisibility(View.GONE);
            imgMotorista.setVisibility(View.GONE);

            rating.setMax(5);
            rating.setNumStars(5);
            rating.setStepSize((float) 0.5);
            if ((mTransporte.getAvaliacaoTransporte() == null)){
                btnConcluir.setText("Enviar Avaliação");
                btnConcluir.setOnClickListener(avaliarListener);
            } else {
                btnConcluir.setEnabled(false);
                btnConcluir.setText("transporte Avaliada");
                rating.setIsIndicator(true);
                rating.setRating(mTransporte.getAvaliacaoTransporte().getAvaliacao());
                msgAvaliacao.setEnabled(false);
                msgAvaliacao.getEditText().setText(mTransporte.getAvaliacaoTransporte().getMensagem());
            }
        }

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
                        FirebaseMessaging.getInstance().subscribeToTopic("transporte"+mTransporte.getIdEventoTransporte().toString());
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

    View.OnClickListener avaliarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AvaliacaoTransporte avaliacao = new AvaliacaoTransporte(mTransporte.getIdEventoTransporte(),
                    rating.getRating(),msgAvaliacao.getEditText().getText().toString());
            TransporteService service = RetrofitClient.getClient().create(TransporteService.class);
            Call<ResponseBody> call = service.avaliarTransporte(SPUtil.getToken(getContext()), avaliacao);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "AVALIADO COM SUCESSO", Toast.LENGTH_LONG).show();
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
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("transporte"+
                                mTransporte.getIdEventoTransporte().toString());
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
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Deseja excluir esse transporte?");
            builder.setNegativeButton("Não", null);
            builder.setCancelable(true);
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
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
            });
            builder.show();
        }
    };
}
