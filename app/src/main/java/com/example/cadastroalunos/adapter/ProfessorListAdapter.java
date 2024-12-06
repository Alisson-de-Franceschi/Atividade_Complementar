package com.example.cadastroalunos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cadastroalunos.R;
import com.example.cadastroalunos.model.Professor;

import java.util.ArrayList;

public class ProfessorListAdapter extends
        RecyclerView.Adapter<ProfessorListAdapter.ViewHolder> {

    private ArrayList<Professor> listaProfessores;
    private Context context;

    public ProfessorListAdapter(ArrayList<Professor> listaProfessores,
                                Context context) {
        this.listaProfessores = listaProfessores;
        this.context = context;
    }

    /**
     * Método responsável em carregar o
     * arquivo xml para cada elemento da lista
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =
                LayoutInflater.from(parent.getContext());

        View listItem =
                inflater.inflate(R.layout.item_list_professor,
                        parent, false);

        return new ViewHolder(listItem);
    }

    /**
     * Método responsável em escrever os dados no layout
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Professor professor = listaProfessores.get(position);
        holder.tvMatricula.setText(String.valueOf(professor.getMatricula()));
        holder.tvNome.setText(professor.getNome());
        holder.tvDisciplina.setText(professor.getDisciplina());
        holder.tvDataAdmissao.setText(professor.getDataAdmissao());
    }

    @Override
    public int getItemCount() {
        return listaProfessores.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvMatricula, tvNome, tvDisciplina, tvDataAdmissao;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.tvMatricula = itemView.findViewById(R.id.tvMatricula);
            this.tvNome = itemView.findViewById(R.id.tvNome);
            this.tvDisciplina = itemView.findViewById(R.id.tvDisciplina);
            this.tvDataAdmissao = itemView.findViewById(R.id.tvDataAdmissao);
        }
    }
}
