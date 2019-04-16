package com.example.android.rodar.Utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class TimePickerFragment extends DialogFragment {
    TimePickerDialog.OnTimeSetListener ontimeSet;
    private int hora, minuto;

    public TimePickerFragment() {}

    public void setCallBack(TimePickerDialog.OnTimeSetListener ontime) {
        ontimeSet = ontime;
    }

    @SuppressLint("NewApi")
    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        hora = args.getInt("hora");
        minuto = args.getInt("minuto");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), ontimeSet, hora, minuto, true);
    }
}
