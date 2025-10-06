/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esofii.alunoscurso.bo;

import com.esofii.alunoscurso.dao.CursoDAO;
import com.esofii.alunoscurso.entity.Curso;
import java.util.List;

/**
 *
 * @author JoaoDias
 */
public class CursoBO implements iBO<Curso>{
    private CursoDAO cDAO;

    public CursoBO() {
        cDAO = new CursoDAO();
    }

    @Override
    public Curso salvar(Curso c) {
        return cDAO.salvar(c);
    }

    @Override
    public List<Curso> listarTodos() {
        return cDAO.selecionarTodos();
    }

    @Override
    public Curso buscarPorId(int id) {
        return cDAO.selecionarPorId(id);
    }

    @Override
    public boolean excluir(int id) {
        return cDAO.excluir(id);
    }
}
