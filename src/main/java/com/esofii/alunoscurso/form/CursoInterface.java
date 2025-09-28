package com.esofii.alunoscurso.form;

import com.esofii.alunoscurso.bo.CursoBO;
import com.esofii.alunoscurso.entity.Curso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CursoInterface extends JFrame {
    private JTextField txtNome, txtDescricao;
    private JTable table;
    private DefaultTableModel model;
    private CursoBO cursoBO;

    public CursoInterface() {
        cursoBO = new CursoBO();

        setTitle("CRUD Curso");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(2, 2));
        panelForm.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        panelForm.add(txtNome);

        panelForm.add(new JLabel("Descrição:"));
        txtDescricao = new JTextField();
        panelForm.add(txtDescricao);

        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarCurso());
        panelBotoes.add(btnSalvar);

        JButton btnAtualizar = new JButton("Atualizar Lista");
        btnAtualizar.addActionListener(e -> carregarCursos());
        panelBotoes.add(btnAtualizar);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> excluirCurso());
        panelBotoes.add(btnExcluir);

        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.add(panelForm, BorderLayout.CENTER);
        panelTop.add(panelBotoes, BorderLayout.SOUTH);

        add(panelTop, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Nome", "Descrição"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        carregarCursos();
        setVisible(true);
    }

    private void salvarCurso() {
        if (txtNome.getText().trim().isEmpty() || txtDescricao.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos antes de salvar!");
            return;
        }

        Curso c = new Curso();
        c.setNome(txtNome.getText());
        c.setDescricao(txtDescricao.getText());

        cursoBO.salvar(c);
        carregarCursos();
        txtNome.setText("");
        txtDescricao.setText("");
    }

    private void excluirCurso() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um curso para excluir!");
            return;
        }

        int id = (int) model.getValueAt(selectedRow, 0);
        if (cursoBO.excluir(id)) {
            JOptionPane.showMessageDialog(this, "Curso excluído com sucesso!");
            carregarCursos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao excluir curso!");
        }
    }


    private void carregarCursos() {
        model.setRowCount(0);
        List<Curso> cursos = cursoBO.listarTodos();
        for (Curso c : cursos) {
            model.addRow(new Object[]{c.getId(), c.getNome(), c.getDescricao()});
        }
    }
}
