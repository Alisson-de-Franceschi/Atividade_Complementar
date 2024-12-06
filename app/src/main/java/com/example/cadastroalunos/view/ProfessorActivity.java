package com.example.cadastroalunos.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cadastroalunos.R;
import com.example.cadastroalunos.adapter.ProfessorListAdapter;
import com.example.cadastroalunos.controller.ProfessorController;
import com.example.cadastroalunos.model.Professor;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProfessorActivity extends AppCompatActivity {

    private FloatingActionButton btAddProfessor;
    private RecyclerView rvProfessores;
    private ProfessorController controller;
    private AlertDialog dialog;
    private View viewCadastro;
    private EditText edMatricula, edNome, edDisciplina, edDataAdmissao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_professor);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        controller = new ProfessorController(this);
        rvProfessores = findViewById(R.id.rvProfessores);
        btAddProfessor = findViewById(R.id.btAddProfessor);

        btAddProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCadastro();
            }
        });

        atualizarListaProfessores();

    }

    private void atualizarListaProfessores(){
        ArrayList<Professor> listaProfessores =
                controller.retornarTodosProfessores();

        ProfessorListAdapter adapter =
                new ProfessorListAdapter(listaProfessores, this);

        rvProfessores.setLayoutManager(new LinearLayoutManager(this));
        rvProfessores.setAdapter(adapter);
    }

    private void abrirCadastro(){
        //Carregar o xml do layout do cadastro
        viewCadastro = getLayoutInflater()
                .inflate(R.layout.dialog_cadastro_professor, null);

        edMatricula = viewCadastro.findViewById(R.id.edMatricula);
        edNome = viewCadastro.findViewById(R.id.edNome);
        edDisciplina = viewCadastro.findViewById(R.id.edDisciplina);
        edDataAdmissao = viewCadastro.findViewById(R.id.edDataAdmissao);

        //Carregando o popup
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(this);

        builder.setTitle("CADASTRO DE PROFESSOR"); //setando o titulo da tela
        builder.setView(viewCadastro); //setando o layout carregado
        builder.setCancelable(false);//não deixa o popup fechar ao clicar fora

        //Adicionando o botão cancelar, que fechará o dialog
        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.dismiss();
            }
        });

        //Adicionando o botão salvar
        //nesse ponto nào vamos adicionar o método
        //onclick, pois é necessário que os EditTexts
        // da tela estejam criados, e nesse momento
        //não estão criados
        builder.setPositiveButton("SALVAR", null);

        //Cria o dialog com o layout e os campos carrgados
        dialog = builder.create();

        //Após carregar o dialog precisamos adicionar
        // o método onClickListener no no botão SALVAR
        // agora os EditTexts estão criados em memória
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button bt = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        salvarDados();
                    }
                });
            }
        });

        //Exibe o dialog na tela
        dialog.show();

    }

    private void salvarDados(){
        //Envia os dados de matricula, nome, disciplina e data de admissao
        //para gravar o professor, validando
        //os campos e se existe o professor cadastrado
        String retorno = controller.salvarProfessor(edMatricula.getText().toString(),
                edNome.getText().toString(), edDisciplina.getText().toString(), edDataAdmissao.getText().toString());

        //Informa o erro caso no campo MATRICULA
        // tenha problema
        //com a matricula do professor
        if(retorno.contains("MATRICULA")){
            edMatricula.setError(retorno);
            edMatricula.requestFocus();
            return;
        }

        //Informa o erro no campo Nome
        // caso tenha algum problema com
        //nome do professor
        if(retorno.contains("NOME")){
            edNome.setError(retorno);
            edNome.requestFocus();
            return;
        }

        //Informa o erro no campo DISCIPLINA
        // caso tenha algum problema com
        //a disciplina do professor
        if(retorno.contains("DISCIPLINA")){
            edDisciplina.setError(retorno);
            edDisciplina.requestFocus();
            return;
        }

        //Informa o erro no campo DATA_ADMISSAO
        // caso tenha algum problema com
        //a data de admissao do professor
        if(retorno.contains("DATA_ADMISSAO")){
            edDataAdmissao.setError(retorno);
            edDataAdmissao.requestFocus();
            return;
        }

        //Ao gravar o professor atualiza a lista
        //e fecha o dialog
        if(retorno.contains("sucesso")){
            atualizarListaProfessores();
            dialog.dismiss();
        }

        //Exibe mensagem
        Toast.makeText(this,
                retorno,
                Toast.LENGTH_LONG).show();
    }








}