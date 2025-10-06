/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esofii.alunoscurso.bo;

import com.esofii.alunoscurso.dao.AlunoDAO;
import com.esofii.alunoscurso.entity.Aluno;
import java.util.List;

/**
 *
 * @author JoaoDias
 */
public class AlunoBO implements iBO<Aluno> {
    private AlunoDAO aDAO;

    public AlunoBO() {
        aDAO = new AlunoDAO();
    }

    @Override
    public Aluno salvar(Aluno a) {
        String raGerado = a.getCpf().replaceAll("[^0-9]", "");
        a.setRa(raGerado);
        return aDAO.salvar(a);
    }

    @Override
    public List<Aluno> listarTodos() {
        return aDAO.selecionarTodos();
    }

    @Override
    public Aluno buscarPorId(int id) {
        return aDAO.selecionarPorId(id);
    }

    @Override
    public boolean excluir(int id) {
        return aDAO.excluir(id);
    }
}
