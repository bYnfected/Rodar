package com.example.android.rodar.FragmentsCarona;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.rodar.R;
import com.example.android.rodar.Utils.DatePickerFragment;
import com.example.android.rodar.Utils.SPUtil;
import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.Utils.TimePickerFragment;
import com.example.android.rodar.models.Carona;
import com.example.android.rodar.services.CaronaService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCriaCarona extends Fragment {

    private Button btnConclui;
    private TextInputLayout mensagem, rua, numero, complemento, bairro, cidade, uf, valor;
    private Spinner vagas;
    private EditText dataHoraInicio,dataHoraFim;
    private int tmpAno,tmpMes,tmpDia,tmpHora,tmpMiuto;
    private String tmpDataHrIni,tmpDataHrFim;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cadastro_carona,container,false);

        btnConclui = v.findViewById(R.id.cadastro_carona_concluir);
        btnConclui.setOnClickListener(concluirListener);

        mensagem = v.findViewById(R.id.cadastro_carona_mensagem);
        rua = v.findViewById(R.id.cadastro_carona_rua);
        numero = v.findViewById(R.id.cadastro_carona_num);
        complemento = v.findViewById(R.id.cadastro_carona_complemento);
        bairro = v.findViewById(R.id.cadastro_carona_bairro);
        cidade = v.findViewById(R.id.cadastro_carona_cidade);
        uf = v.findViewById(R.id.cadastro_carona_uf);
        valor = v.findViewById(R.id.cadastro_carona_valor);
        vagas = v.findViewById(R.id.cadastro_carona_vagas);
        dataHoraInicio = v.findViewById(R.id.cadastro_carona_dataIni);
        dataHoraInicio.setOnFocusChangeListener(dataListener);
        dataHoraFim = v.findViewById(R.id.cadastro_carona_dataFim);
        dataHoraFim.setOnFocusChangeListener(dataListener);

        return v;
    }


    private View.OnClickListener concluirListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            if (validaCampos()){
                Carona carona = new Carona();
                carona.setMensagem(mensagem.getEditText().getText().toString());
                carona.setEnderecoPartidaRua(rua.getEditText().getText().toString());
                carona.setEnderecoPartidaNumero(Integer.parseInt(numero.getEditText().getText().toString()));
                if (!complemento.getEditText().getText().toString().isEmpty()){
                    carona.setEnderecoPartidaComplemento(complemento.getEditText().getText().toString());
                }
                carona.setEnderecoPartidaBairro(bairro.getEditText().getText().toString());
                carona.setEnderecoPartidaCidade(cidade.getEditText().getText().toString());
                carona.setEnderecoPartidaUF(uf.getEditText().getText().toString());
                carona.setValorParticipacao(Double.parseDouble(valor.getEditText().getText().toString()));
                carona.setQuantidadeVagas(Integer.parseInt(vagas.getSelectedItem().toString()));
                carona.setIdEvento(getArguments().getInt("idEvento"));
                carona.setDataHoraPartida(tmpDataHrIni);
                carona.setDataHoraPrevisaoChegada(tmpDataHrFim);

                CaronaService service = RetrofitClient.getClient().create(CaronaService.class);
                Call<ResponseBody> call = service.createCarona(SPUtil.getToken(getContext()), carona);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Carona cadastrada", Toast.LENGTH_LONG).show();
                            getFragmentManager().popBackStackImmediate();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), "Falha ao criar carona", Toast.LENGTH_LONG).show();
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

        return ok;
    }



    private View.OnFocusChangeListener dataListener = new View.OnFocusChangeListener(){
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                if (v.getId() == R.id.cadastro_carona_dataIni){
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
            tmpDataHrIni = transformaDataHrString(dataHoraInicio);
        }
    };

    TimePickerDialog.OnTimeSetListener ontimeFim = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            tmpHora = hourOfDay;
            tmpMiuto = minute;
            tmpDataHrFim = transformaDataHrString(dataHoraFim);
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
