package com.sergio.helpelders.home.principal;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.ornach.nobobutton.NoboButton;
import com.sergio.helpelders.R;
import com.sergio.helpelders.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PublishFragment extends Util {
    private Spinner spinnerDesplegable;
    private String opcionSeleccionada;
    private EditText tituloTextView, fechaServicio, descripcionTextView;
    private NoboButton publishButton;

    /**
     * Constructor
     */
    public PublishFragment() {}

    /**
     * Métodos
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_publish, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tituloTextView = view.findViewById(R.id.text_titulo);
        descripcionTextView = view.findViewById(R.id.text_descripcion);

        spinnerDesplegable = view.findViewById(R.id.lista_cuidados);

        fechaServicio = view.findViewById(R.id.text_fechaserv);

        publishButton = view.findViewById(R.id.boton_publicar);

        setFechaServicio();

        // https://developer.android.com/guide/topics/ui/controls/spinner
        ArrayAdapter adapterLista = ArrayAdapter.createFromResource(requireContext(), R.array.lista_cuidados, android.R.layout.simple_spinner_item);
        adapterLista.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerDesplegable.setAdapter(adapterLista);

        spinnerDesplegable.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                opcionSeleccionada = (String)spinnerDesplegable.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(SweetAlertDialog.NORMAL_TYPE, "Descripción del servicio:",
                        "Título: " + tituloTextView.getText().toString()
                                + "\nCuidados que necesitas: " + opcionSeleccionada
                                + "\nFecha del servicio: " + fechaServicio.getText().toString()
                                + "\nDescripción: " + descripcionTextView.getText().toString());

                getFragmentManager().popBackStack();
            }
        });

    }

    private void setFormatoFecha() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.UK);

        fechaServicio.setText(sdf.format(calendar.getTime()));
    }

    private void setFechaServicio() {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                setFormatoFecha();
            }
        };

        fechaServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(requireContext(), date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
}