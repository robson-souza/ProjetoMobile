package br.com.pk.projetomobilep2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.pk.projetomobilep2.dao.ProdutoDao;
import br.com.pk.projetomobilep2.modelo.Produto;

public class FormProd extends AppCompatActivity {
    EditText editNome,editQtd,editValor;
    Button btnVariavel;
    Produto produto,altproduto;
    ProdutoDao produtoDao;
    long retornoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_prod);

        Intent i = getIntent();
        altproduto = (Produto) i.getSerializableExtra("produto-enviado");
        produto = new Produto();
        produtoDao = new ProdutoDao(FormProd.this);

        editNome = (EditText) findViewById(R.id.editNome);
        editQtd = (EditText) findViewById(R.id.editQtd);
        editValor= (EditText) findViewById(R.id.editValor);
        btnVariavel = (Button) findViewById(R.id.btnVariavel);

        if (altproduto != null){

            btnVariavel.setText("Alterar");
            editNome.setText(altproduto.getNome());
            editQtd.setText(altproduto.getQuantidade()+"");
            editValor.setText(altproduto.getValor());
            produto.setId(altproduto.getId());
        }else{
            btnVariavel.setText("Salvar");
            //
        }

        btnVariavel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                produto.setNome(editNome.getText().toString());
                produto.setQuantidade(editQtd.getText().toString());
                produto.setValor(editValor.getText().toString());

                if(btnVariavel.getText().toString().equals("Salvar")){
                    retornoDB = produtoDao.salvarProduto(produto);
                    produtoDao.close();
                    if (retornoDB == -1){
                        alert("Erro ao cadastrar");
                    }else{
                        alert("Cadastro realizado com sucesso");
                    }
                }else{
                    retornoDB = produtoDao.alterarProduto(produto);
                    produtoDao.close();
                    if (retornoDB == -1){
                        alert("Erro ao alterar");
                    }else{
                        alert("Item atualizado com sucesso");
                    }
                }
                finish();
            }
        });
    }

    private void alert (String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();

    }

}