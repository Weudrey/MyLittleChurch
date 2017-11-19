package com.wyz.appdaigreja.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wyz.appdaigreja.Entidades.Membros;
import com.wyz.appdaigreja.R;

import java.util.ArrayList;

/**
 * Created by W€µÐr€Y™ on 18/11/2017.
 */

public class MembrosAdapter extends ArrayAdapter<Membros> {

    private ArrayList<Membros> membros;
    private Context context;

    public MembrosAdapter(Context c, ArrayList<Membros> objects) {
        super(c, 0, objects);
        this.context = c;
        this.membros = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = null;

        if(membros != null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_membros, parent, false);

            TextView tvNomeMembro = (TextView) view.findViewById(R.id.tvNomeMembro);
            TextView tvCargoMembro = (TextView) view.findViewById(R.id.tvCargoMembro);
            TextView tvDataNascimentoMembro = (TextView) view.findViewById(R.id.tvDataNascimentoMembro);
            TextView tvSexoMembro = (TextView) view.findViewById(R.id.tvSexoMembro);

            Membros membros2 = membros.get(position);

            tvNomeMembro.setText(membros2.getNome_membro());
            tvCargoMembro.setText(membros2.getCargo_membro());
            tvDataNascimentoMembro.setText(membros2.getDataNascimento_membro());
            tvSexoMembro.setText(membros2.getSexo_membro());
        }

        return view;
    }
}
