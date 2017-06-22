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

import br.com.pk.projetomobilep2.dao.ProdutoDao;
import br.com.pk.projetomobilep2.modelo.Produto;


public class HomeProd extends AppCompatActivity {
    ListView listVisivel;
    Button btnNovoCadastro;
    Produto produto;
    ProdutoDao produtoDao;
    ArrayList<Produto> arrayListProduto;
    ArrayAdapter<Produto> arrayAdapterProduto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_produto);
        listVisivel = (ListView) findViewById(R.id.listProd);
        registerForContextMenu(listVisivel);
        btnNovoCadastro = (Button) findViewById(R.id.btnNovoCadastro);

        listVisivel.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Produto produtoEnviado = arrayAdapterProduto.getItem(position);
                Intent i = new Intent (HomeProd.this, FormProd.class);
                i.putExtra("produto-enviado",produtoEnviado);
                startActivity(i);
            }
        });
        listVisivel.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                produto = arrayAdapterProduto.getItem(position);
                return false;
            }
        });
    }


    public void Cadastrar(View v){
        Intent i = new Intent(HomeProd.this,FormProd.class);
        startActivity(i);
    }

    public void populaLista(){
        produtoDao = new ProdutoDao(HomeProd.this);
        arrayListProduto= produtoDao.selectAllProduto();
        produtoDao.close();

        if (listVisivel !=null){
            arrayAdapterProduto= new ArrayAdapter<>(HomeProd.this,
                    android.R.layout.simple_list_item_1, arrayListProduto);
            listVisivel.setAdapter(arrayAdapterProduto);
        }
    }

    protected void onResume() {
        super.onResume();
        populaLista();
    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        MenuItem mDelete = menu.add("Excluir Item");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                long retornoDB;
                produtoDao = new ProdutoDao(HomeProd.this);
                retornoDB = produtoDao.excluirProduto(produto);
                produtoDao.close();

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