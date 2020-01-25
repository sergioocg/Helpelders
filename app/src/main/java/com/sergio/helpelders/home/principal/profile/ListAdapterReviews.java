package com.sergio.helpelders.home.principal.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.sergio.helpelders.R;

public class ListAdapterReviews extends BaseAdapter {
    /**
     * Atributos
     */
    private Context contextComentarios;
    private String[] arrayNombres, arrayComentarios;
    private int[] arrayImagenes;

    private TextView nombreTextView, comentarioTextView;
    private CircularImageView imagenPerfil;

    /**
     * Constructor
     */
    public ListAdapterReviews(Context contextComentarios, String[] arrayNombres, String[] arrayComentarios, int[] arrayImagenes) {
        this.contextComentarios = contextComentarios;
        this.arrayNombres = arrayNombres;
        this.arrayComentarios = arrayComentarios;
        this.arrayImagenes = arrayImagenes;
    }

    @Override
    public int getCount() {
        return arrayNombres.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private void setInitWidgets(View itemView) {
        nombreTextView = (TextView) itemView.findViewById(R.id.txt_nombre);
        comentarioTextView = (TextView) itemView.findViewById(R.id.txt_comentario);
        imagenPerfil = (CircularImageView) itemView.findViewById(R.id.imageChat);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflaterComentarios = (LayoutInflater) contextComentarios.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflaterComentarios.inflate(R.layout.item_list_review, parent, false);

        setInitWidgets(itemView);

        /**
         * Dependiendo de la posiciónn se asigna un nombre, comentario, imagen, etc...
         */
        nombreTextView.setText(arrayNombres[position]);
        comentarioTextView.setText(arrayComentarios[position]);
        imagenPerfil.setImageResource(arrayImagenes[position]);

        return itemView;
    }
}
