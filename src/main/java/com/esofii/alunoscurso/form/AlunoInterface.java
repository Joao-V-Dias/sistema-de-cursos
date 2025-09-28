package com.esofii.alunoscurso.form;

import com.esofii.alunoscurso.bo.AlunoBO;
import com.esofii.alunoscurso.bo.CursoBO;
import com.esofii.alunoscurso.entity.Aluno;
import com.esofii.alunoscurso.entity.Curso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AlunoInterface extends JFrame {
    private JTextField txtNome, txtEmail, txtCpf;
    private JComboBox<Curso> comboCurso;
    private JTable table;
    private DefaultTableModel model;
    private AlunoBO alunoBO;
    private CursoBO cursoBO;

    public AlunoInterface() {
        alunoBO = new AlunoBO();
        cursoBO = new CursoBO();

        setTitle("CRUD Aluno");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(4, 2));
        panelForm.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        panelForm.add(txtNome);

        panelForm.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        panelForm.add(txtEmail);

        panelForm.add(new JLabel("CPF:"));
        txtCpf = new JTextField();
        panelForm.add(txtCpf);

        panelForm.add(new JLabel("Curso:"));
        comboCurso = new JComboBox<>();
        carregarCursosCombo();
        panelForm.add(comboCurso);

        JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarAluno());
        panelBotoes.add(btnSalvar);

        JButton btnAtualizar = new JButton("Atualizar Lista");
        btnAtualizar.addActionListener(e -> carregarAlunos());
        panelBotoes.add(btnAtualizar);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> excluirAluno());
        panelBotoes.add(btnExcluir);

        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.add(panelForm, BorderLayout.CENTER);
        panelTop.add(panelBotoes, BorderLayout.SOUTH);

        add(panelTop, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Nome", "Email", "CPF", "RA", "Curso"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        carregarAlunos();
        setVisible(true);
    }

    private void salvarAluno() {
        if (txtNome.getText().trim().isEmpty() || 
            txtEmail.getText().trim().isEmpty() || 
            txtCpf.getText().trim().isEmpty() ||
            comboCurso.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(this, "Preencha todos os campos antes de salvar!");
            return;
        }

        Aluno a = new Aluno();
        a.setNome(txtNome.getText());
        a.setEmail(txtEmail.getText());
        a.setCpf(txtCpf.getText());
        a.setCurso((Curso) comboCurso.getSelectedItem());

        alunoBO.salvar(a);
        carregarAlunos();

        txtNome.setText("");
        txtEmail.setText("");
        txtCpf.setText("");
    }

    private void excluirAluno() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno para excluir!");
            return;
        }

        int id = (int) model.getValueAt(selectedRow, 0);
        if (alunoBO.excluir(id)) {
            JOptionPane.showMessageDialog(this, "Aluno excluído com sucesso!");
            carregarAlunos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao excluir aluno!");
        }
    }

    private void carregarCursosCombo() {
        comboCurso.removeAllItems();
        List<Curso> cursos = cursoBO.listarTodos();
        for (Curso c : cursos) {
            comboCurso.addItem(c);
        }
    }

    private void carregarAlunos() {
        model.setRowCount(0);
        List<Aluno> alunos = alunoBO.listarTodos();
        for (Aluno a : alunos) {
            model.addRow(new Object[]{
                    a.getId(), a.getNome(), a.getEmail(), a.getCpf(),
                    a.getRa(), (a.getCurso() != null ? a.getCurso().getNome() : "Sem curso")
            });
        }
    }
}
