package com.example.android.rodar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.rodar.Utils.DatePickerFragment;
import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.Utils.TimePickerFragment;
import com.example.android.rodar.models.Evento;
import com.example.android.rodar.services.EventoService;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCriaEvento extends Fragment {

    private Button btnConclui;
    private TextInputLayout local, rua, numero, complemento, bairro, cidade, uf, descricao;
    private EditText dataHoraInicio,dataHoraFim;
    private Timestamp tempDataInicio;
    private Date tempDataFim;
    private int tmpAno,tmpMes,tmpDia,tmpHora,tmpMiuto;


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
        dataHoraInicio = v.findViewById(R.id.cadastro_evento_dataInicio);
        dataHoraInicio.setOnFocusChangeListener(dataListener);
        dataHoraFim = v.findViewById(R.id.cadastro_evento_dataFim);
        dataHoraFim.setOnFocusChangeListener(dataListener);
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

            GregorianCalendar calendar;
            calendar = new GregorianCalendar(tmpAno, tmpMes, tmpDia, tmpHora, tmpMiuto, 0);
            Date date = calendar.getTime();
            // Conversao em formato padrao
            SimpleDateFormat sdf;
            sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSXXX");
            sdf.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
            String text = sdf.format(date);
            evento.setDataHoraInicio(text);


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

    private View.OnFocusChangeListener dataListener = new View.OnFocusChangeListener(){
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                if (v.getId() == R.id.cadastro_evento_dataInicio){
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
            tmpMes = monthOfYear+1;
            tmpDia = dayOfMonth;
            showTimePicker("ini");
        }
    };

    DatePickerDialog.OnDateSetListener ondateFim = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                tempDataFim = format.parse (String.valueOf(year) + "-" + String.valueOf(monthOfYear+1) + "-"  + String.valueOf(dayOfMonth));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            dataHoraFim.setText(String.valueOf(dayOfMonth) + "-" +String.valueOf(monthOfYear+1) + "-" + String.valueOf(year));
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

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

            tempDataInicio = new Timestamp(tmpAno-1900,tmpMes,tmpDia,tmpHora,tmpMiuto,0,0);



            /*try {
                tempDataInicio = format.parse (String.valueOf(tmpAno) + "-" + String.valueOf(tmpMes+1) + "-"  + String.valueOf(tmpDia) + "T" +  String.valueOf(tmpHora) + ":" + String.valueOf(tmpMiuto));
            } catch (ParseException e) {
                e.printStackTrace();
            }*/

            // Somente data de exibicao
            dataHoraInicio.setText(String.valueOf(tmpDia) + "-" +String.valueOf(tmpMes+1) + "-" + String.valueOf(tmpAno));
        }
    };

    TimePickerDialog.OnTimeSetListener ontimeFim = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        }
    };

}
