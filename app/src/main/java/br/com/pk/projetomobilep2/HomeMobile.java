package br.com.pk.projetomobilep2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeMobile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_mobile);
    }
    public void Pessoa(View v){
        Intent ipessoa = new Intent(HomeMobile.this,HomePessoa.class);
        startActivity(ipessoa);
    }

    public void Produto(View v){
        Intent iproduto = new Intent(HomeMobile.this,HomeProd.class);
        startActivity(iproduto);
    }

    public void Funcionario(View v){
        Intent ifunc = new Intent(HomeMobile.this,HomeFunc.class);
        startActivity(ifunc);
    }


}
