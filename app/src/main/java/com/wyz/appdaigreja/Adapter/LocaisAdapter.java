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

import com.wyz.appdaigreja.Entidades.Locais;
import com.wyz.appdaigreja.R;

import java.util.ArrayList;

/**
 * Created by W€µÐr€Y™ on 18/11/2017.
 */

public class LocaisAdapter extends ArrayAdapter<Locais> {


    private ArrayList<Locais> locais;
    private Context context;

    public LocaisAdapter(Context c, ArrayList<Locais> objects) {
        super(c, 0, objects);
        this.context = c;
        this.locais = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if(locais != null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_locais, parent, false);

            TextView tvNomeLocal = (TextView) view.findViewById(R.id.tvNomeLocal);
            TextView tvCidadeLocal = (TextView) view.findViewById(R.id.tvCidadeLocal);
            TextView tvBairroLocal = (TextView) view.findViewById(R.id.tvBairroLocal);
            TextView tvRuaLocal = (TextView) view.findViewById(R.id.tvRuaLocal);

            Locais locais2 = locais.get(position);

            tvNomeLocal.setText(locais2.getNome_local());
            tvCidadeLocal.setText(locais2.getCidade_local());
            tvBairroLocal.setText(locais2.getBairro_local());
            tvRuaLocal.setText(locais2.getRua_local());
        }

        return view;
    }
}
