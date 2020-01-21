package com.sergio.helpelders.map;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class UsuarioFragment extends DialogFragment {
    public static final String ARGUMENTO_TITLE = "TITLE";
    public static final String ARGUMENTO_FULL_SNIРРET = "FULL_SNIРРET";

    private static String title, fullSnippet;

    public static UsuarioFragment newInstance(String title, String fullSnippet) {
        UsuarioFragment usuarioFragment = new UsuarioFragment();
        Bundle b = new Bundle();
        b.putString(ARGUMENTO_TITLE, title);
        b.putString(ARGUMENTO_FULL_SNIРРET, fullSnippet);
        usuarioFragment.setArguments(b);

        return usuarioFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        title = args.getString(ARGUMENTO_TITLE);
        fullSnippet = args.getString(ARGUMENTO_FULL_SNIРРET);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new AlertDialog.Builder(getActivity()).setTitle(title).setMessage(fullSnippet).create();
        return dialog;
    }
}