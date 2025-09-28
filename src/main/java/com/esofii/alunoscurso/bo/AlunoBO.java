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
public class AlunoBO {
    private AlunoDAO aDAO;

    public AlunoBO() {
        aDAO = new AlunoDAO();
    }

    public Aluno salvar(Aluno a) {
        String raGerado = a.getCpf().replaceAll("[^0-9]", "");
        a.setRa(raGerado);
        return aDAO.salvar(a);
    }

    public List<Aluno> listarTodos() {
        return aDAO.selecionarTodos();
    }

    public Aluno buscarPorId(int id) {
        return aDAO.selecionarPorId(id);
    }

    public boolean excluir(int id) {
        return aDAO.excluir(id);
    }
}
