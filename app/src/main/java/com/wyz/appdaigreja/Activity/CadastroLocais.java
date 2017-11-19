package com.wyz.appdaigreja.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.wyz.appdaigreja.DAO.ConfigurationFireBase;
import com.wyz.appdaigreja.Entidades.Locais;
import com.wyz.appdaigreja.Entidades.Membros;
import com.wyz.appdaigreja.R;

public class CadastroLocais extends Activity {

    private EditText edtNomeLocal, edtRua, edtBairro, edtCidade;
    private Button btnGravarLocal, btnVoltarDeCadLocal;
    private Locais locais;
    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_locais);


        edtNomeLocal = (EditText) findViewById(R.id.edtNomeLocal);
        edtRua = (EditText) findViewById(R.id.edtRua);
        edtBairro = (EditText) findViewById(R.id.edtBairro);
        edtCidade = (EditText) findViewById(R.id.edtCidade);
        btnGravarLocal = (Button) findViewById(R.id.btnGravarLocal);
        btnVoltarDeCadLocal = (Button) findViewById(R.id.btnVoltarDeCadLocal);

        btnGravarLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locais = new Locais();
                locais.setNome_local(edtNomeLocal.getText().toString());
                locais.setCidade_local(edtCidade.getText().toString());
                locais.setBairro_local(edtBairro.getText().toString());
                locais.setRua_local(edtRua.getText().toString());

                salvarLocal(locais);
            }
        });

        btnVoltarDeCadLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltarTelaInicial();
            }
        });

    }

    private boolean salvarLocal (Locais locais){
        try {
            firebase = ConfigurationFireBase.getFirebase().child("LOCAL");
            firebase.child(locais.getNome_local()).setValue(locais);
            Toast.makeText(this, "Local cadastrado com Sucesso", Toast.LENGTH_LONG).show();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private void voltarTelaInicial(){
        Intent intentAbrirTelaPrincipal = new Intent(this, PrincipalActivity.class);
        startActivity(intentAbrirTelaPrincipal);
        finish();

    }
}
