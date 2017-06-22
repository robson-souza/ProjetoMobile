package br.com.pk.projetomobilep2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.Toast;
import java.util.ArrayList;

import br.com.pk.projetomobilep2.dao.FuncionarioDao;
import br.com.pk.projetomobilep2.modelo.Funcionario;


public class HomeFunc extends AppCompatActivity {
    ListView listVisivel;
    Button btnNovoCadastro;
    Funcionario funcionario;
    FuncionarioDao funcionarioDao;
    ArrayList<Funcionario> arrayListFuncionario;
    ArrayAdapter<Funcionario> arrayAdapterFuncionario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_func);
        listVisivel = (ListView) findViewById(R.id.listFunc);
        registerForContextMenu(listVisivel);
        btnNovoCadastro = (Button) findViewById(R.id.btnNovoCadastro);

        listVisivel.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Funcionario funcionarioEnviado = arrayAdapterFuncionario.getItem(position);
                Intent i = new Intent (HomeFunc.this, FormFunc.class);
                i.putExtra("funcionario-enviado",funcionarioEnviado);
                startActivity(i);
            }
        });
        listVisivel.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                funcionario = arrayAdapterFuncionario.getItem(position);
                return false;
            }
        });
    }


    public void Cadastrar(View v){
        Intent i = new Intent(HomeFunc.this,FormFunc.class);
        startActivity(i);
    }

    public void populaLista(){
        funcionarioDao = new FuncionarioDao(HomeFunc.this);
        arrayListFuncionario = funcionarioDao.selectAllFuncionario();
        funcionarioDao.close();

        if (listVisivel !=null){
            arrayAdapterFuncionario = new ArrayAdapter<>(HomeFunc.this,
                    android.R.layout.simple_list_item_1, arrayListFuncionario);
            listVisivel.setAdapter(arrayAdapterFuncionario);
        }
    }

    protected void onResume() {
        super.onResume();
        populaLista();
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        MenuItem mDelete = menu.add("Excluir Registro");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                long retornoDB;
                funcionarioDao = new FuncionarioDao(HomeFunc.this);
                retornoDB = funcionarioDao.excluirFuncionario(funcionario);
                funcionarioDao.close();

                if(retornoDB == -1){
                    alert("Erro ao excluir");
                }else{
                    alert("Item excluído com sucesso");
                }
                populaLista();
                return false;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    private void alert(String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}