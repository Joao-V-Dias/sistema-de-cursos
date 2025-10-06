/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esofii.alunoscurso.dao;

import com.esofii.alunoscurso.entity.Curso;
import com.esofii.alunoscurso.util.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JoaoDias
 */
public class CursoDAO implements iDAO<Curso> {
    private Connection conn;

    public CursoDAO() {
        conn = new Conexao().conectar();
    }

    @Override
    public Curso salvar(Curso c) {
        try {
            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO curso (nome, descricao) VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, c.getNome());
            stmt.setString(2, c.getDescricao());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                c.setId(rs.getInt(1));
            }

            rs.close();
            stmt.close();
            return c;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Curso> selecionarTodos() {
        List<Curso> cursos = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM curso");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                cursos.add(mapearCurso(rs));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cursos;
    }

    @Override
    public Curso selecionarPorId(int id) {
        Curso c = null;
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM curso WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                c = mapearCurso(rs);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }
    
    private Curso mapearCurso(ResultSet rs) throws SQLException {
        Curso c = new Curso();
        c.setId(rs.getInt("id"));
        c.setNome(rs.getString("nome"));
        c.setDescricao(rs.getString("descricao"));
        return c;
    }

    @Override
    public boolean excluir(int id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM curso WHERE id = ?");
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            stmt.close();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
