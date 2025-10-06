package com.esofii.alunoscurso.dao;

import java.util.List;

public interface iDAO<T> {
    T salvar(T obj);
    List<T> selecionarTodos();
    T selecionarPorId(int id);
    boolean excluir(int id);
}

