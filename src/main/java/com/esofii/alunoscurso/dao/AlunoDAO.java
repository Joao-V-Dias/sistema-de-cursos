package com.esofii.alunoscurso.dao;

import com.esofii.alunoscurso.entity.Aluno;
import com.esofii.alunoscurso.entity.Curso;
import com.esofii.alunoscurso.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JoaoDias
 */
public class AlunoDAO implements iDAO<Aluno>{
    private Connection conn;

    public AlunoDAO() {
        conn = new Conexao().conectar();
    }

    @Override
    public Aluno salvar(Aluno a) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO aluno (nome, email, cpf, ra, curso_id) VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getEmail());
            stmt.setString(3, a.getCpf());
            stmt.setString(4, a.getRa());
            stmt.setInt(5, a.getCurso().getId());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                a.setId(rs.getInt(1)); // seta o id gerado
            }

            rs.close();
            stmt.close();
            return a;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Aluno> selecionarTodos() {
        List<Aluno> alunos = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(
                "SELECT a.*, c.nome AS curso_nome " +
                "FROM aluno a LEFT JOIN curso c ON a.curso_id = c.id"
            );
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                alunos.add(mapearAluno(rs));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alunos;
    }


    @Override
    public Aluno selecionarPorId(int id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM aluno WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            Aluno a = null;
            if (rs.next()) {
                a = mapearAluno(rs);
            }

            rs.close();
            stmt.close();
            return a;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean excluir(int id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM aluno WHERE id = ?");
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private Aluno mapearAluno(ResultSet rs) throws SQLException {
        Aluno a = new Aluno();
        a.setId(rs.getInt("id"));
        a.setNome(rs.getString("nome"));
        a.setEmail(rs.getString("email"));
        a.setCpf(rs.getString("cpf"));
        a.setRa(rs.getString("ra"));

        int cursoId = rs.getInt("curso_id");
        if (cursoId > 0) {
            Curso c = new Curso();
            c.setId(cursoId);
            c.setNome(rs.getString("curso_nome"));
            a.setCurso(c);
        }
        return a;
    }
}
