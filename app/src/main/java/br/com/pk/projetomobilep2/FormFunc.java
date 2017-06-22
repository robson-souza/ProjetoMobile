package br.com.pk.projetomobilep2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.pk.projetomobilep2.dao.FuncionarioDao;
import br.com.pk.projetomobilep2.modelo.Funcionario;

public class FormFunc extends AppCompatActivity {
    EditText editNome,editSalario,editSetor,editRamal;
    Button btnVariavel;
    Funcionario funcionario,altfuncionario;
    FuncionarioDao funcionarioDao;
    long retornoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_func);

        Intent i = getIntent();
        altfuncionario = (Funcionario) i.getSerializableExtra("funcionario-enviado");
        funcionario = new Funcionario();
        funcionarioDao = new FuncionarioDao(FormFunc.this);

        editNome = (EditText) findViewById(R.id.editNome);
        editSalario = (EditText) findViewById(R.id.editSalario);
        editSetor= (EditText) findViewById(R.id.editSetor);
        editRamal = (EditText) findViewById(R.id.editRamal);
        btnVariavel = (Button) findViewById(R.id.btnVariavel);

        if (altfuncionario != null){

            btnVariavel.setText("Alterar");
            editNome.setText(altfuncionario.getNome());
            editSalario.setText(altfuncionario.getSalario()+"");
            editSetor.setText(altfuncionario.getSetor());
            editRamal.setText(altfuncionario.getRamal());

            funcionario.setId(altfuncionario.getId());
        }else{
            btnVariavel.setText("Salvar");
        }

        btnVariavel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                funcionario.setNome(editNome.getText().toString());
                funcionario.setSalario(Integer.parseInt(editSalario.getText().toString()));
                funcionario.setSetor(editSetor.getText().toString());
                funcionario.setRamal(editRamal.getText().toString());

                if(btnVariavel.getText().toString().equals("Salvar")){
                    retornoDB = funcionarioDao.salvarFuncionario(funcionario);
                    funcionarioDao.close();
                    if (retornoDB == -1){
                        alert("Erro ao cadastrar");
                    }else{
                        alert("Cadastro realizado com sucesso");
                    }
                }else{
                    retornoDB = funcionarioDao.alterarFuncionario(funcionario);
                    funcionarioDao.close();
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

