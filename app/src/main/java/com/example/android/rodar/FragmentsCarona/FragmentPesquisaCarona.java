package com.example.android.rodar.FragmentsCarona;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.android.rodar.R;
import com.example.android.rodar.Utils.DatePickerFragment;
import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.Utils.SPUtil;
import com.example.android.rodar.activities.IMainActivity;
import com.example.android.rodar.models.Carona;
import com.example.android.rodar.services.CaronaService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPesquisaCarona extends Fragment {

    private IMainActivity mainActivity;
    private TextView valorMin,valorMax, minVagas;
    private Button btnPesquisar;
    private TextInputLayout cidade;
    private TextInputEditText dataPartida;
    private SeekBar barValorMin, barValorMax, barVagasMin;
    private String mIdEvento;
    private String tmpDataHrIni = "";
    private int tmpAno,tmpMes,tmpDia;
    private List<Carona> mCaronas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_pesquisa_carona, container, false);

        mIdEvento = getArguments().getString("idEvento");

        cidade = v.findViewById(R.id.pesq_carona_cidade);
        valorMin = v.findViewById(R.id.pesq_carona_valorMin);
        valorMax = v.findViewById(R.id.pesq_carona_valorMax);
        minVagas = v.findViewById(R.id.pesq_carona_minVagas);
        dataPartida = v.findViewById(R.id.pesq_carona_data);
        dataPartida.setOnFocusChangeListener(dataListener);
        barValorMin = v.findViewById(R.id.pesq_carona_bar_valor_min);
        barValorMin.setOnSeekBarChangeListener(minListener);
        barValorMax = v.findViewById(R.id.pesq_carona_bar_valor_max);
        barValorMax.setOnSeekBarChangeListener(maxListener);
        barVagasMin = v.findViewById(R.id.pesq_carona_bar_min_vagas);
        barVagasMin.setOnSeekBarChangeListener(minVagasListener);
        btnPesquisar = v.findViewById(R.id.pesq_carona_btn_pesquisar);
        btnPesquisar.setOnClickListener(pesquisarListener);
        barValorMin.setProgress(0);
        barValorMax.setProgress(500);
        barVagasMin.setProgress(0);

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (IMainActivity) getActivity();
    }

    SeekBar.OnSeekBarChangeListener minListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            valorMin.setText(String.valueOf(progress));
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    SeekBar.OnSeekBarChangeListener maxListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            valorMax.setText(String.valueOf(progress));
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    SeekBar.OnSeekBarChangeListener minVagasListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            minVagas.setText(String.valueOf(progress));
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    View.OnClickListener pesquisarListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (barValorMin.getProgress() > barValorMax.getProgress()){
                barVagasMin.setProgress(0);
            }

            String cidadeEvento = cidade.getEditText().getText().toString();
            String dataEvento = tmpDataHrIni;

            if (cidadeEvento.length() == 0) cidadeEvento = null;
            if (dataEvento.length() == 0) dataEvento = null;

            CaronaService service = RetrofitClient.getClient().create(CaronaService.class);
            Call<List<Carona>> call = service.getCaronasFiltro(SPUtil.getToken(getContext()),
                    mIdEvento,null,null,String.valueOf(barValorMin.getProgress()),
                    String.valueOf(barValorMax.getProgress()),String.valueOf(barVagasMin.getProgress()));
            call.enqueue(new Callback<List<Carona>>() {
                @Override
                public void onResponse(Call<List<Carona>> call, Response<List<Carona>> response) {
                    if (response.isSuccessful()){
                        mCaronas = response.body();
                    }
                }

                @Override
                public void onFailure(Call<List<Carona>> call, Throwable t) {

                }
            });
        }
    };

    private View.OnFocusChangeListener dataListener = new View.OnFocusChangeListener(){
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                showDatePicker();
            }
        }
    };

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        // Informa a data atual como inicial
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        // Indica quem ira receber o retorno da chamada
        date.setCallBack(ondateIni);
        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondateIni = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            tmpAno = year;
            tmpMes = monthOfYear;
            tmpDia = dayOfMonth;
            tmpDataHrIni = transformaDataHrString(dataPartida);
        }
    };


    private String transformaDataHrString(EditText dataHora){
        GregorianCalendar calendar;
        calendar = new GregorianCalendar(tmpAno, tmpMes, tmpDia, 0, 0, 0);
        Date date = calendar.getTime();
        // Conversao em formato padrao para o banco
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSXXX");
        // Formato para exibir
        SimpleDateFormat sdfExibicao = new SimpleDateFormat("dd/MMM/yy" ,new Locale("pt", "BR"));
        dataHora.setText(sdfExibicao.format(date));

        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        return sdf.format(date);
    }
}
