package br.com.pk.projetomobilep2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import java.util.ArrayList;

import br.com.pk.projetomobilep2.modelo.Pessoa;

public class PessoaDao extends SQLiteOpenHelper{

    private static final String NOME_BD = "BD_Mobile";
    private static final int VERSION = 1;
    private static final String TABELA1 = "tb_func";
    private static final String TABELA2= "tb_pessoa";
    private static final String TABELA3= "tb_prod";
    private static final String ID = "id";
    private static final String NOME= "nome";
    private static final String SALARIO= "salario";
    private static final String SETOR= "setor";
    private static final String RAMAL = "ramal";
    private static final String IDADE = "idade";
    private static final String ENDERECO = "endereco";
    private static final String TELEFONE = "telefone";
    private static final String QUANTIDADE = "quantidade";
    private static final String VALOR = "valor";

    public PessoaDao(Context context) {

        super(context,NOME_BD,null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS "+TABELA1+" ( "+
                " "+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                " "+NOME+" TEXT, "+SALARIO+" INTEGER, "+SETOR+" TEXT, "+RAMAL+" TEXT );";
        db.execSQL(sql);

        String sql2 = "CREATE TABLE IF NOT EXISTS "+TABELA2+" ( "+
                " "+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                " "+NOME+" TEXT, "+IDADE+" INTEGER, "+ENDERECO+" TEXT, "+TELEFONE+" TEXT );";
        db.execSQL(sql2);

        String sql3 = "CREATE TABLE IF NOT EXISTS "+TABELA3+" ( "+
                " "+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                " "+NOME+" TEXT, "+QUANTIDADE+" TEXT, "+VALOR+" TEXT );";
        db.execSQL(sql3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS "+TABELA1;
        String sql2 = "DROP TABLE IF EXISTS "+TABELA2;
        String sql3 = "DROP TABLE IF EXISTS "+TABELA3;
        db.execSQL(sql);
        db.execSQL(sql2);
        db.execSQL(sql3);
        onCreate(db);
    }
    public long salvarPessoa(Pessoa p){
        ContentValues values = new ContentValues();
        long retornoDB;

        values.put(NOME,p.getNome());
        values.put(IDADE,p.getIdade());
        values.put(ENDERECO,p.getEndereco());
        values.put(TELEFONE,p.getTelefone());

        retornoDB = getWritableDatabase().insert(TABELA2,null,values);

        return retornoDB;
    }


    public long alterarPessoa (Pessoa p){
        ContentValues values = new ContentValues();
        long retornoDB;

        values.put(NOME,p.getNome());
        values.put(IDADE,p.getIdade());
        values.put(ENDERECO,p.getEndereco());
        values.put(TELEFONE,p.getTelefone());

        String[] args = {String.valueOf(p.getId())};
        retornoDB = getWritableDatabase().update(TABELA2,values,"id=?",args);

        return retornoDB;
    }

    public long excluirPessoa (Pessoa p){
        long retornoDB;

         String[] args = {String.valueOf(p.getId())};
        retornoDB = getWritableDatabase().delete(TABELA2,ID+ "=?",args);

        return retornoDB;
    }

    public ArrayList<Pessoa>selectAllPessoa(){
        String[] coluns = {ID,NOME,IDADE,ENDERECO,TELEFONE};

        Cursor cursor = getWritableDatabase().query(TABELA2,coluns,null,null,null,null,"upper (nome)",null);

        ArrayList<Pessoa> listPessoa = new ArrayList<Pessoa>();

        while (cursor.moveToNext()){
            Pessoa p = new Pessoa();
            p.setId(cursor.getInt(0));
            p.setNome(cursor.getString(1));
            p.setIdade(cursor.getInt(2));
            p.setEndereco(cursor.getString(3));
            p.setTelefone(cursor.getString(4));

            listPessoa.add(p);

        }
    return listPessoa;
    }

}
