package com.example.android.rodar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.android.rodar.Utils.DatePickerFragment;
import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.Utils.SPUtil;
import com.example.android.rodar.Utils.TimePickerFragment;
import com.example.android.rodar.activities.IMainActivity;
import com.example.android.rodar.models.Evento;
import com.example.android.rodar.services.EventoService;

import java.io.Serializable;
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

public class FragmentPesquisaEventos extends Fragment {

    private IMainActivity mainActivity;
    AutoCompleteTextView cidadeAutoComplete;
    TextInputLayout nome;
    Button btnPesquisar;
    private int tmpAno,tmpMes,tmpDia,tmpHora,tmpMiuto;
    private String tmpDataHrIni = "",tmpDataHrFim = "";
    TextInputEditText dataIni,dataFim;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_pesquisa_evento, container, false);
        nome = v.findViewById(R.id.pesquisa_evento_nome);
        cidadeAutoComplete = v.findViewById(R.id.pesquisa_evento_cidade);
        dataIni = v.findViewById(R.id.pesquisa_evento_dataIni);
        dataFim = v.findViewById(R.id.pesquisa_evento_dataFim);
        btnPesquisar = v.findViewById(R.id.pesquisa_evento_btnPesquisar);
        btnPesquisar.setOnClickListener(pesquisarListener);

        dataIni.setOnFocusChangeListener(dataListener);
        dataFim.setOnFocusChangeListener(dataListener);

        carregaCidades();
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (IMainActivity) getActivity();
    }

    private View.OnClickListener pesquisarListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            String nomeEvento = nome.getEditText().getText().toString();
            String cidadeEvento = cidadeAutoComplete.getText().toString();
            String dataIniEvento = tmpDataHrIni;
            String dataFimEvento = tmpDataHrFim;
            if (nomeEvento.length() == 0) nomeEvento = null;
            if (cidadeEvento.length() == 0) cidadeEvento = null;
            if (dataIniEvento.length() == 0) dataIniEvento = null;
            if (dataFimEvento.length() == 0) dataFimEvento = null;


            EventoService service = RetrofitClient.getClient().create(EventoService.class);
            Call<List<Evento>> call = service.getEventos(SPUtil.getToken(getContext()),
                    false, false, nomeEvento,
                    cidadeEvento,dataIniEvento,dataFimEvento);
            call.enqueue(new Callback<List<Evento>>() {
                @Override
                public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                    if (response.isSuccessful()){
                        List<Evento> eventosPesquisa = response.body();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("eventos", (Serializable) eventosPesquisa);
                        mainActivity.inflateFragment("eventos",bundle);
                    }
                }

                @Override
                public void onFailure(Call<List<Evento>> call, Throwable t) {

                }
            });
        }
    };

    private void carregaCidades() {
        EventoService service = RetrofitClient.getClient().create(EventoService.class);
        Call<List<String>> call = service.getCidadesEventos(SPUtil.getToken(getContext()));
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()){
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                            android.R.layout.simple_dropdown_item_1line, response.body());
                    cidadeAutoComplete.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {

            }
        });
    }

    private View.OnFocusChangeListener dataListener = new View.OnFocusChangeListener(){
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                if (v.getId() == R.id.pesquisa_evento_dataIni){
                    showDatePicker("ini");
                } else {
                    showDatePicker("fim");
                }
            }
        }
    };


    private void showDatePicker(String tipo) {
        DatePickerFragment date = new DatePickerFragment();
        // Informa a data atual como inicial
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        // Indica quem ira receber o retorno da chamada
        if (tipo == "ini") {
            date.setCallBack(ondateIni);
        } else {
            date.setCallBack(ondateFim);
        }
        date.show(getFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondateIni = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            tmpAno = year;
            tmpMes = monthOfYear;
            tmpDia = dayOfMonth;
            showTimePicker("ini");
        }
    };

    DatePickerDialog.OnDateSetListener ondateFim = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            tmpAno = year;
            tmpMes = monthOfYear;
            tmpDia = dayOfMonth;
            showTimePicker("fim");
        }
    };

    private void showTimePicker(String tipo) {
        TimePickerFragment time = new TimePickerFragment();
        // Informa a data atual como inicial
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("hora", calender.get(Calendar.HOUR_OF_DAY));
        args.putInt("minuto", calender.get(Calendar.MINUTE));
        time.setArguments(args);
        // Indica quem ira receber o retorno da chamada
        if (tipo == "ini") {
            time.setCallBack(ontimeIni);
        } else {
            time.setCallBack(ontimeFim);
        }
        time.show(getFragmentManager(), "Time Picker");
    }

    TimePickerDialog.OnTimeSetListener ontimeIni = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            tmpHora = hourOfDay;
            tmpMiuto = minute;
            tmpDataHrIni = transformaDataHrString(dataIni);
        }
    };

    TimePickerDialog.OnTimeSetListener ontimeFim = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            tmpHora = hourOfDay;
            tmpMiuto = minute;
            tmpDataHrFim = transformaDataHrString(dataFim);
        }
    };

    private String transformaDataHrString(EditText dataHora){
        GregorianCalendar calendar;
        calendar = new GregorianCalendar(tmpAno, tmpMes, tmpDia, tmpHora, tmpMiuto, 0);
        Date date = calendar.getTime();
        // Conversao em formato padrao para o banco
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSXXX");
        // Formato para exibir
        SimpleDateFormat sdfExibicao = new SimpleDateFormat("dd/MMM/yy HH:mm" ,new Locale("pt", "BR"));
        dataHora.setText(sdfExibicao.format(date));

        sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        return sdf.format(date);
    }


}
