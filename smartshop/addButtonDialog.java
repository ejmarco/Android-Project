package com.example.erikk.smartshop;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;


/**
 * Created by Erikk on 26/02/2016.
 */
public class addButtonDialog extends DialogFragment implements Const {

    //creamos una interfaz para que al salir del dialgo (ya que este es propio) obtener los datos
    public interface addButtonDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog,Boton btn);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    //usamos esta instancia de la interfaz para enviar los eventos
    addButtonDialogListener mListener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (addButtonDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement addButtonDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View vistaItems = inflater.inflate(R.layout.addpersianas_dialog, null);//Aqui obtenemos la vista (layout) customizado para el dialogo, y asi poder coger sus items
        final NumberPicker npLed = (NumberPicker) vistaItems.findViewById(R.id.npPin);
        npLed.setMinValue(1);
        npLed.setMaxValue(10);
        final RadioButton rbOnOff = (RadioButton) vistaItems.findViewById(R.id.rbOnOff);
        rbOnOff.setChecked(true);
        final RadioButton rbPulsador = (RadioButton) vistaItems.findViewById(R.id.rbPulsador);
        rbOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbPulsador.isChecked()){
                    rbPulsador.setChecked(false);
                }
            }
        });
        rbPulsador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbOnOff.isChecked()){
                    rbOnOff.setChecked(false);
                }
            }
        });
                // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(vistaItems);
        builder.setMessage("Personaliza el Boton")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the positive button event back to the host activity
                        Boton btn = new Boton(rbOnOff.isChecked() ? TYPE_INTERRUPTOR : TYPE_PULSADOR,npLed.getValue());
                        mListener.onDialogPositiveClick(addButtonDialog.this,btn);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(addButtonDialog.this);
                    }
                });
        return builder.create();
    }
}
