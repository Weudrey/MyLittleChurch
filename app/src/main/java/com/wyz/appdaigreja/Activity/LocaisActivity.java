package com.wyz.appdaigreja.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.wyz.appdaigreja.Adapter.LocaisAdapter;
import com.wyz.appdaigreja.DAO.ConfigurationFireBase;
import com.wyz.appdaigreja.Entidades.Locais;
import com.wyz.appdaigreja.R;

import java.util.ArrayList;

public class LocaisActivity extends Activity {

    private ListView listViewLocais;
    private ArrayList<Locais> locais;
    private ArrayAdapter<Locais> adapter;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;
    private Button btnVoltarDeListaLocais;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locais);

        locais = new ArrayList<>();

        listViewLocais = (ListView) findViewById(R.id.listViewLocais);
        adapter =  new LocaisAdapter(this, locais);
        listViewLocais.setAdapter(adapter);

        firebase = ConfigurationFireBase.getFirebase().child("LOCAL");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                locais.clear();

                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    Locais locaisNovo = dados.getValue(Locais.class);
                    locais.add(locaisNovo);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        btnVoltarDeListaLocais = (Button) findViewById(R.id.btnVoltarDeListaLocais);
        btnVoltarDeListaLocais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltarTelaInicial();
            }
        });

    }
    private void voltarTelaInicial(){
        Intent intentAbrirTelaPrincipal = new Intent(this, PrincipalActivity.class);
        startActivity(intentAbrirTelaPrincipal);
        finish();

    }
    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListener);
    }
}
