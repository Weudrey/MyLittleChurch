package com.wyz.appdaigreja.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.wyz.appdaigreja.DAO.ConfigurationFireBase;
import com.wyz.appdaigreja.Entidades.Membros;
import com.wyz.appdaigreja.R;

public class CadastroMembros extends Activity {

    private EditText edtNomeMembro, edtCargoMembro, edtDataNascimentoMembro;
    private RadioButton rbFemininoMembro, rbMasculinoMembro;
    private Button btnGravarMembro, btnVoltarDeCadMembro;
    private Membros membros;
    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_membros);

        edtNomeMembro = (EditText) findViewById(R.id.edtNomeMembro);
        edtCargoMembro = (EditText) findViewById(R.id.edtCargoMembro);
        edtDataNascimentoMembro = (EditText) findViewById(R.id.edtDataNascimentoMembro);
        //edtSexoMembro = (EditText) findViewById(R.id.edtSexoMembro);
        rbFemininoMembro = (RadioButton) findViewById(R.id.rbFemininoMembro);
        rbMasculinoMembro = (RadioButton) findViewById(R.id.rbMasculinoMembro);
        btnGravarMembro = (Button) findViewById(R.id.btnGravarMembro);
        btnVoltarDeCadMembro = (Button) findViewById(R.id.btnVoltarDeCadMembro);

        btnGravarMembro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                membros = new Membros();
                membros.setNome_membro(edtNomeMembro.getText().toString());
                membros.setCargo_membro(edtCargoMembro.getText().toString());
                membros.setDataNascimento_membro(edtDataNascimentoMembro.getText().toString());
                //membros.setSexo_membro(edtSexoMembro.getText().toString());

                if (rbFemininoMembro.isChecked()) {
                    membros.setSexo_membro("Feminino");
                } else {
                    membros.setSexo_membro("Masculino");
                }

                salvarMembro(membros);
            }
        });

        btnVoltarDeCadMembro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltarTelaInicial();
            }
        });

    }

    private boolean salvarMembro (Membros membros){
        try {
            firebase = ConfigurationFireBase.getFirebase().child("MEMBROS");
            firebase.child(membros.getNome_membro()).setValue(membros);
            Toast.makeText(this, "Membro cadastrado com Sucesso", Toast.LENGTH_LONG).show();
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
