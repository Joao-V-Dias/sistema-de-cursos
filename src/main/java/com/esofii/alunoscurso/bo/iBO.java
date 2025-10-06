package com.esofii.alunoscurso.bo;

import java.util.List;

public interface iBO<T> {
    T salvar(T obj);
    List<T> listarTodos();
    T buscarPorId(int id);
    boolean excluir(int id);
}
