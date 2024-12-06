package com.example.cadastroalunos.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.cadastroalunos.helper.SQLiteDataHelper;
import com.example.cadastroalunos.model.Professor;

import java.util.ArrayList;

public class ProfessorDao implements IGenericDao<Professor>{

    //Variável responsável por abri a conexão com a BD
    private SQLiteOpenHelper openHelper;

    //Base de Dados
    private SQLiteDatabase baseDados;

    //nome das colunas da tabela
    private String[]colunas = {"MATRICULA","NOME", "DISCIPLINA", "DATA_ADMISSAO"};

    //Nome da tabela
    private String tabela = "PROFESSOR";

    //Contexto
    private Context context;

    private static ProfessorDao instancia;

    public static ProfessorDao getInstance(Context context){
        if(instancia == null) {
            instancia = new ProfessorDao(context);
        }
        return instancia;
    }

    private ProfessorDao(Context context) {
        this.context = context;

        //Abrir a Conexao com a base de dados
        openHelper = new SQLiteDataHelper(this.context,
                "UNIPARCVEL_BD", null, 1);

        baseDados = openHelper.getWritableDatabase();

    }

    /**
     * Método para inserir dados na tabela PROFESSOR
     * @param obj Professor
     * @return a linha que foi inserido o registro
     */
    @Override
    public long insert(Professor obj) {
        try{
            ContentValues valores = new ContentValues();
            valores.put(colunas[0], obj.getMatricula());
            valores.put(colunas[1], obj.getNome());
            valores.put(colunas[2], obj.getDisciplina());
            valores.put(colunas[3], obj.getDataAdmissao());

            return baseDados.insert(tabela, null, valores);

        }catch (SQLException ex){
            Log.e("UNIPAR",
                    "ERRO: ProfessorDao.insert() "+
                    ex.getMessage());
        }

        return 0;
    }

    /**
     * Atualizar registro na tabela Professor
     * @param obj Professor
     * @return
     */
    @Override
    public long update(Professor obj) {
        try{
            ContentValues valores = new ContentValues();
            valores.put(colunas[1], obj.getNome());
            valores.put(colunas[2], obj.getDisciplina());
            valores.put(colunas[3], obj.getDataAdmissao());

            String[]identificador =
                    {String.valueOf(obj.getMatricula())};

            return baseDados.update(tabela,
                    valores,
                    colunas[0]+"= ?",
                    identificador);

        }catch (SQLException ex){
            Log.e("UNIPAR",
                    "ERRO: ProfessorDao.update() "+
                            ex.getMessage());
        }
        return 0;
    }

    @Override
    public long delete(Professor obj) {
        try{
            String[]identificador =
                    {String.valueOf(obj.getMatricula())};

            return baseDados.delete(tabela,
                    colunas[0]+" = ?",
                    identificador);

        }catch (SQLException ex){
            Log.e("UNIPAR",
                    "ERRO: ProfessorDao.delete() "+
                            ex.getMessage());
        }
        return 0;
    }

    @Override
    public ArrayList<Professor> getAll() {
        ArrayList lista = new ArrayList<>();
        try{
            //SELECT * FROM ALUNO
            Cursor cursor = baseDados.query(tabela,
                    colunas, null, null,
                    null, null, colunas[0]);

            while(cursor.moveToNext()){
                Professor aluno = new Professor();
                aluno.setMatricula(cursor.getInt(0));
                aluno.setNome(cursor.getString(1));
                aluno.setDisciplina(cursor.getString(2));
                aluno.setDataAdmissao(cursor.getString(3));

                lista.add(aluno);
            }

            cursor.close();
            return lista;

        }catch (SQLException ex){
            Log.e("UNIPAR",
                    "ERRO: ProfessorDao.getAll() "+
                            ex.getMessage());
        }

        return null;
    }

    @Override
    public Professor getById(int id) {
        try{
            String[]identificador =
                    {String.valueOf(id)};

            Cursor cursor = baseDados.query(tabela,
                    colunas, colunas[0]+" = ?",
                    identificador,
                    null, null, null);

            if(cursor.moveToFirst()){
                Professor aluno = new Professor();
                aluno.setMatricula(cursor.getInt(0));
                aluno.setNome(cursor.getString(1));
                aluno.setDisciplina(cursor.getString(2));
                aluno.setDataAdmissao(cursor.getString(3));

                cursor.close();
                return aluno;
            }
        }catch (SQLException ex){
            Log.e("UNIPAR",
                    "ERRO: ProfessorDao.getById() "+
                            ex.getMessage());
        }
        return null;
    }
}
