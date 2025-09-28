package com.esofii.alunoscurso.form;

import javax.swing.*;

public class MenuInterface {
    public MenuInterface() {
        SwingUtilities.invokeLater(() -> {
            JFrame menu = new JFrame("Sistema Alunos & Cursos");
            menu.setSize(400, 200);
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.setLayout(null);

            JButton btnCursos = new JButton("Gerenciar Cursos");
            btnCursos.setBounds(100, 30, 200, 40);
            btnCursos.addActionListener(e -> new CursoInterface());
            menu.add(btnCursos);

            JButton btnAlunos = new JButton("Gerenciar Alunos");
            btnAlunos.setBounds(100, 90, 200, 40);
            btnAlunos.addActionListener(e -> new AlunoInterface());
            menu.add(btnAlunos);

            menu.setVisible(true);
        });
    }
}
