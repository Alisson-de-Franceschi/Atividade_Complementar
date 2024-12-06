package com.example.cadastroalunos.controller;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.cadastroalunos.dao.ProfessorDao;
import com.example.cadastroalunos.model.Professor;

import java.util.ArrayList;

public class ProfessorController {

    private Context context;

    public ProfessorController(Context context) {
        this.context = context;
    }

    public String salvarProfessor(String matricula, String nome, String disciplina, String dataAdmissao){
        try{
            if(TextUtils.isEmpty(matricula)){
                return "Informe a MATRICULA do Professor";
            }
            if(TextUtils.isEmpty(nome)){
                return "Informe o NOME do Professor";
            }
            if(TextUtils.isEmpty(disciplina)){
                return "Informe a DISCIPLINA do Professor";
            }
            if(TextUtils.isEmpty(dataAdmissao)){
                return "Informe a DATA_ADMISSAO do Professor";
            }

            //Verifica se existe um professor cadastrado com
            // a matricula informado
            Professor professor = ProfessorDao
                    .getInstance(context)
                    .getById(Integer.parseInt(matricula));

            if(professor != null){
                return "A MATRICULA ("+matricula+") já está cadastrada.";
            }else{
                professor = new Professor();
                professor.setMatricula(Integer.parseInt(matricula));
                professor.setNome(nome);
                professor.setDisciplina(disciplina);
                professor.setDataAdmissao(dataAdmissao);

                long id = ProfessorDao.getInstance(context).insert(professor);
                if(id > 0){
                    return "Professor cadastrado com sucesso!";
                }else{
                    return "Não foi possível gravar os dados do Professor." +
                            " Solicite suporte técnico.";
                }
            }


        }catch (Exception ex){
            Log.e("Unipar",
                    "Erro salvarProfessor(): "+ex.getMessage());
        }
        return "Erro ao gravar dados do Aluno. " +
                "Solicite suporte técnico.";
    }

    public ArrayList<Professor> retornarTodosProfessores(){
        return ProfessorDao.getInstance(context).getAll();
    }

}
