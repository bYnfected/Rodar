package com.example.android.rodar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.rodar.Utils.DatePickerFragment;
import com.example.android.rodar.Utils.FileUtil;
import com.example.android.rodar.Utils.RetrofitClient;
import com.example.android.rodar.Utils.SPUtil;
import com.example.android.rodar.Utils.TimePickerFragment;
import com.example.android.rodar.models.Evento;
import com.example.android.rodar.services.EventoService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCriaEvento extends Fragment {

    private static final int GALLERY_REQUEST_CODE = 1001;
    private Button btnConclui;
    private TextInputLayout nome, rua, numero, complemento, bairro, cidade, uf, descricao;
    private EditText dataHoraInicio,dataHoraFim;
    private int tmpAno,tmpMes,tmpDia,tmpHora,tmpMiuto;
    private String tmpDataHrIni,tmpDataHrFim;
    private ImageView img;
    private Uri uriImg;
    private ProgressBar progressBar;
    private ConstraintLayout layout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.cadastro_evento,container,false);

        btnConclui = v.findViewById(R.id.cadastro_evento_but_concluir);
        btnConclui.setOnClickListener(concluirListener);

        img = v.findViewById(R.id.cadastro_evento_img);
        img.setOnClickListener(carregarImg);
        nome = v.findViewById(R.id.cadastro_evento_nome);
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
        progressBar = v.findViewById(R.id.cadastro_evento_progressBar);
        layout = v.findViewById(R.id.cria_evento_layout);

        return v;
    }

    private View.OnClickListener concluirListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (validaCampos()) {
                progressBar.setVisibility(View.VISIBLE);
                layout.setBackgroundColor(000000);
                layout.setAlpha((float) 0.4);
                Evento evento = new Evento();
                evento.setNomeEvento(nome.getEditText().getText().toString());
                evento.setEnderecoRua(rua.getEditText().getText().toString());
                if (numero.getEditText().getText().toString().isEmpty()){
                    evento.setEnderecoNumero(null);
                } else {
                    evento.setEnderecoNumero(Integer.parseInt(numero.getEditText().getText().toString()));
                }
                evento.setEnderecoComplemento(complemento.getEditText().getText().toString());
                evento.setEnderecoBairro(bairro.getEditText().getText().toString());
                evento.setEnderecoCidade(cidade.getEditText().getText().toString());
                evento.setEnderecoUF(uf.getEditText().getText().toString());
                evento.setDescricaoEvento(descricao.getEditText().getText().toString());
                evento.setDataHoraInicio(tmpDataHrIni);
                evento.setDataHoraTermino(tmpDataHrFim);


                EventoService service = RetrofitClient.getClient().create(EventoService.class);
                Call<Integer> call = service.createEvento(SPUtil.getToken(getContext()), evento);
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if (response.isSuccessful()) {
                            if (uriImg != null) {
                                uploadFoto(uriImg, response.body());
                            } else {
                                Toast.makeText(getContext(), "Evento criado com sucesso!", Toast.LENGTH_LONG).show();
                                getFragmentManager().popBackStackImmediate();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Toast.makeText(getContext(), "Falha ao criar evento!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        layout.setBackgroundColor(000000);
                        layout.setAlpha(0);

                    }
                });
            }
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

    private boolean validaCampos(){
        boolean ok = true;

        nome.setError(null);
        rua.setError(null);
        bairro.setError(null);
        cidade.setError(null);
        uf.setError(null);
        descricao.setError(null);
        dataHoraFim.setError(null);
        dataHoraInicio.setError(null);

        if (nome.getEditText().getText().toString().isEmpty()) {
            nome.setError("Campo obrigatório");
            ok = false;
        }
        if (rua.getEditText().getText().toString().isEmpty()){
            rua.setError("Campo obrigatório");
            ok = false;
        }
        if (bairro.getEditText().getText().toString().isEmpty()){
            bairro.setError("Campo obrigatório");
            ok = false;
        }
        if (cidade.getEditText().getText().toString().isEmpty()) {
            cidade.setError("Campo obrigatório");
            ok = false;
        }
        if (uf.getEditText().getText().toString().isEmpty()){
            uf.setError("Campo obrigatório");
            ok = false;
        }
        if (descricao.getEditText().getText().toString().isEmpty()){
            descricao.setError("Campo obrigatório");
            ok = false;
        }
        if (tmpDataHrIni.isEmpty()){
            dataHoraInicio.setError("Campo obrigatório");
            ok = false;
        }
        if (tmpDataHrFim.isEmpty()) {
            dataHoraFim.setError("Campo obrigatório");
            ok = false;
        }
        return ok;
    }

    private View.OnClickListener carregarImg = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            pickFromGallery();
        }
    };

    private void pickFromGallery(){
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
                    //data.getData returns the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    img.setImageURI(selectedImage);

                    uriImg = selectedImage;
                    break;
            }
    }

    private void uploadFoto(Uri imagemSelecionada, Integer idEvento) {
        File fotoFile = null;
        try {
            fotoFile = FileUtil.from(getContext(), imagemSelecionada);
            Log.d("file", "File...:::: uti - " + fotoFile.getPath() + " file -" + fotoFile + " : " + fotoFile.exists());

        } catch (IOException e) {
            e.printStackTrace();
        }


        // Cria o PART foto com esse arquivo
        RequestBody fotoPart = RequestBody.create(
                MediaType.parse(getContext().getContentResolver().getType(imagemSelecionada)), fotoFile);

        MultipartBody.Part multiPart = MultipartBody.Part.createFormData("foto", fotoFile.getName(), fotoPart);

        EventoService service = RetrofitClient.getClient().create(EventoService.class);
        Call<ResponseBody> call = service.enviarFoto(SPUtil.getToken(getContext()),idEvento,multiPart);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getContext(), "Evento criado com sucesso!", Toast.LENGTH_LONG).show();
                    getFragmentManager().popBackStackImmediate();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
