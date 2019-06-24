package com.example.android.rodar.FragmentsCarona;

import android.app.AlertDialog;
import android.content.Context;
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
import com.example.android.rodar.activities.IMainActivity;
import com.example.android.rodar.adapters.AdapterPassageiros;
import com.example.android.rodar.models.AvaliacaoCarona;
import com.example.android.rodar.models.Carona;
import com.example.android.rodar.models.MensagemCarona;
import com.example.android.rodar.models.Usuario;
import com.example.android.rodar.services.CaronaService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentParticipaCarona extends Fragment {

    private TextView endereco, mensagem, vagas, valor, vagasTotal, nomeMotorista;
    private TextInputLayout msgAvaliacao;
    private Button btnConcluir;
    private Carona mCarona;
    private AdapterPassageiros mAdapterPassageiros;
    private RecyclerView recyclerViewPassageiros;
    private RatingBar rating, ratingMotorista;
    private CircularImageView imgMotorista;
    private Button btnMensagens;
    private IMainActivity mainActivity;

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
        btnMensagens = v.findViewById(R.id.participa_carona_mensagens);
        btnMensagens.setOnClickListener(mensagensListener);

        recyclerViewPassageiros = v.findViewById(R.id.participa_carona_passageiros);
        rating = v.findViewById(R.id.participa_carona_rating);
        msgAvaliacao = v.findViewById(R.id.participa_carona_msgAvaliacao);
        ratingMotorista = v.findViewById(R.id.participa_carona_rating_motorista);
        imgMotorista = v.findViewById(R.id.participa_carona_img_motorista);
        nomeMotorista = v.findViewById(R.id.participa_carona_motorista);

        endereco.setText(mCarona.getEnderecoPartidaRua() + ", " + mCarona.getEnderecoPartidaNumero() +
                ", " + mCarona.getEnderecoPartidaCEP() + ", " + mCarona.getEnderecoPartidaBairro() +
                " - " + mCarona.getEnderecoPartidaCidade() + " - " + mCarona.getEnderecoPartidaUF());

        mensagem.setText(mCarona.getMensagem());
        vagas.setText(mCarona.getQuantidadeVagasDisponiveis().toString());
        vagasTotal.setText(mCarona.getQuantidadeVagas().toString());
        valor.setText(String.format("R$ %.2f",mCarona.getValorParticipacao()));
        String urlImgMotorista = mCarona.getUsuarioMotorista().getUrlImagemSelfie();
        if (!urlImgMotorista.isEmpty()){
            Picasso.get().load(getString(R.string.url) + urlImgMotorista).into(imgMotorista);
        }

        nomeMotorista.setText(mCarona.getUsuarioMotorista().getNome() +
                " " + mCarona.getUsuarioMotorista().getSobrenome());


        mAdapterPassageiros = new AdapterPassageiros(mCarona.getPassageiros());
        recyclerViewPassageiros.setAdapter(mAdapterPassageiros);
        recyclerViewPassageiros.setLayoutManager(new LinearLayoutManager(getContext()));

        configuraTela();
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (IMainActivity) getActivity();
    }

    private void configuraTela() {

        if (getArguments().getBoolean("ativo")){
            rating.setVisibility(View.GONE);
            msgAvaliacao.setVisibility(View.GONE);
            // Configura nota do motorista
            if (mCarona.getUsuarioMotorista().getAvaliacao() == -1){
                ratingMotorista.setVisibility(View.GONE);
            } else {
                ratingMotorista.setIsIndicator(true);
                ratingMotorista.setNumStars(5);
                ratingMotorista.setStepSize((float) 0.5);
                ratingMotorista.setRating(mCarona.getUsuarioMotorista().getAvaliacao());
            }
            // Se é o motorista pode excluir, se esta participando pode cancelar, se esta lotado informa
            if (mCarona.getIdUsuarioMotorista() == SPUtil.getID(getContext())){
                btnConcluir.setText("Excluir Carona");
                btnConcluir.setOnClickListener(excluirListener);
                btnMensagens.setVisibility(View.INVISIBLE);

            } else if (participando()) {
                btnConcluir.setText("Cancelar Participação");
                btnConcluir.setOnClickListener(cancelarListener);
            } else if (mCarona.getQuantidadeVagasDisponiveis() == 0){
                btnConcluir.setText("Carona Lotada");
                btnConcluir.setEnabled(false);
            } else {
                btnConcluir.setOnClickListener(concluirListener);
            }
        } else { // Se a carona já esta completa
            ratingMotorista.setVisibility(View.GONE);
            imgMotorista.setVisibility(View.GONE);

            rating.setMax(5);
            rating.setNumStars(5);
            rating.setStepSize((float) 0.5);
            if ((mCarona.getAvaliacaoCarona() == null)){
                btnConcluir.setText("Enviar Avaliação");
                btnConcluir.setOnClickListener(avaliarListener);
            } else {
                btnConcluir.setEnabled(false);
                btnConcluir.setText("Carona Avaliada");
                rating.setIsIndicator(true);
                rating.setRating(mCarona.getAvaliacaoCarona().getAvaliacao());
                msgAvaliacao.setEnabled(false);
                msgAvaliacao.getEditText().setText(mCarona.getAvaliacaoCarona().getMensagem());
            }
        }
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


    View.OnClickListener mensagensListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Bundle bundle = new Bundle();

            bundle.putSerializable("idEventoTransporteCarona", mCarona.getIdEventoCarona());
            bundle.putSerializable("tipoTransporteCarona", "Carona");
            bundle.putSerializable("idUsuarioDestino", mCarona.getIdUsuarioMotorista());
            mainActivity.inflateFragment("mensagensUsuario",bundle);
        }
    };


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
                        FirebaseMessaging.getInstance().subscribeToTopic("carona"+mCarona.getIdEventoCarona().toString());
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
            AvaliacaoCarona avaliacao = new AvaliacaoCarona(mCarona.getIdEventoCarona(),
                    rating.getRating(),msgAvaliacao.getEditText().getText().toString());
            CaronaService service = RetrofitClient.getClient().create(CaronaService.class);
            Call<ResponseBody> call = service.avaliarCarona(SPUtil.getToken(getContext()), avaliacao);

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
            CaronaService service = RetrofitClient.getClient().create(CaronaService.class);
            Call<ResponseBody> call = service.sairCarona(SPUtil.getToken(getContext()), mCarona.getIdEventoCarona());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "PARTICIPAÇÃO CANCELADA", Toast.LENGTH_LONG).show();
                        FirebaseMessaging.getInstance().unsubscribeFromTopic("carona"+
                                mCarona.getIdEventoCarona().toString());
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
            });
        }
    };
}
