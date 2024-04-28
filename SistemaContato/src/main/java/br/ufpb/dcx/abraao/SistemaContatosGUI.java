package br.ufpb.dcx.abraao;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SistemaContatosGUI extends JFrame {
    private SistemaContato sistemaContato;

    public SistemaContatosGUI() {
        sistemaContato = new SistemaContatoImpl();

        JMenuBar menuBar = new JMenuBar();

        JMenu arquivoMenu = new JMenu("Arquivo");
        JMenu contatosMenu = new JMenu("Contatos");

        JMenuItem salvarItem = new JMenuItem("Salvar");
        JMenuItem sairItem = new JMenuItem("Sair");
        salvarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarContatos();
            }
        });
        sairItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        arquivoMenu.add(salvarItem);
        arquivoMenu.addSeparator();
        arquivoMenu.add(sairItem);


        JMenuItem adicionarItem = new JMenuItem("Adicionar Contato");
        JMenuItem listarContatosItem = new JMenuItem("Listar Contatos");
        JMenuItem pesquisarContatoButton = new JMenuItem("Pesquisar Contato");

        pesquisarContatoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pesquisarContatoPorNome(); // Ou pesquisarContatoPorTelefone()
            }
        });
        adicionarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarContato();
            }
        });
        listarContatosItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarContatos();
            }
        });
        contatosMenu.add(adicionarItem);
        contatosMenu.add(listarContatosItem);
        contatosMenu.add(pesquisarContatoButton);

        menuBar.add(arquivoMenu);
        menuBar.add(contatosMenu);


        setJMenuBar(menuBar);


        setTitle("Sistema de Contatos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void salvarContatos() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showSaveDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File arquivo = fileChooser.getSelectedFile();

            try (FileWriter writer = new FileWriter(arquivo)) {
                ArrayList<Contato> contatos = sistemaContato.getContatos();
                for (Contato contato : contatos) {
                    writer.write("Nome: " + contato.getNome() + "\n");
                    writer.write("Sobrenome: " + contato.getSobrenome() + "\n");
                    writer.write("Telefone: " + contato.getTelefone() + "\n");
                    writer.write("Email: " + contato.getEmail() + "\n");
                    writer.write("-------------------------\n");
                }
                JOptionPane.showMessageDialog(this, "Contatos salvos com sucesso!", "Salvar Contatos", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar contatos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void adicionarContato() {

        JTextField nomeField = new JTextField(10);
        JTextField sobrenomeField = new JTextField(10);
        JTextField telefoneField = new JTextField(10);
        JTextField emailField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Sobrenome:"));
        panel.add(sobrenomeField);
        panel.add(new JLabel("Telefone:"));
        panel.add(telefoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Adicionar Contato",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {

            String nome = nomeField.getText();
            String sobrenome = sobrenomeField.getText();
            String telefone = telefoneField.getText();
            String email = emailField.getText();


            Contato novoContato = new Contato(nome, sobrenome, telefone, email, null);
            sistemaContato.adicionarContato(novoContato);
            JOptionPane.showMessageDialog(this, "Contato adicionado com sucesso!", "Adicionar Contato", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void exibirDetalhesContato(Contato contato) {
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Nome: ").append(contato.getNome()).append("\n");
        mensagem.append("Sobrenome: ").append(contato.getSobrenome()).append("\n");
        mensagem.append("Telefone: ").append(contato.getTelefone()).append("\n");
        mensagem.append("Email: ").append(contato.getEmail()).append("\n");

        JOptionPane.showMessageDialog(this, mensagem.toString(), "Detalhes do Contato", JOptionPane.INFORMATION_MESSAGE);
    }

    private void pesquisarContatoPorNome() {
        String nomePesquisado = JOptionPane.showInputDialog(this, "Digite o nome do contato:", "Pesquisar por Nome", JOptionPane.PLAIN_MESSAGE);
        if (nomePesquisado != null && !nomePesquisado.isEmpty()) {
            Contato contatoEncontrado = sistemaContato.pesquisarContatoPorNome(nomePesquisado);
            if (contatoEncontrado != null) {
                exibirDetalhesContato(contatoEncontrado);
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum contato encontrado com o nome: " + nomePesquisado, "Contato não encontrado", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void pesquisarContatoPorTelefone() {
        String telefonePesquisado = JOptionPane.showInputDialog(this, "Digite o telefone do contato:", "Pesquisar por Telefone", JOptionPane.PLAIN_MESSAGE);
        if (telefonePesquisado != null && !telefonePesquisado.isEmpty()) {
            Contato contatoEncontrado = sistemaContato.pesquisarContatoPorTelefone(telefonePesquisado);
            if (contatoEncontrado != null) {
                exibirDetalhesContato(contatoEncontrado);
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum contato encontrado com o telefone: " + telefonePesquisado, "Contato não encontrado", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }




    private void listarContatos() {
        StringBuilder mensagem = new StringBuilder("Listando contatos:\n\n");

        ArrayList<Contato> contatos = sistemaContato.getContatos();
        if (contatos == null) {
            mensagem.append("Nenhum contato para exibir.");
        } else {
            for (Contato contato : contatos) {
                mensagem.append("Nome: ").append(contato.getNome()).append("\n");
                mensagem.append("Sobrenome: ").append(contato.getSobrenome()).append("\n");
                mensagem.append("Telefone: ").append(contato.getTelefone()).append("\n");
                mensagem.append("Email: ").append(contato.getEmail()).append("\n");
                mensagem.append("-------------------------\n");
            }
        }

        JOptionPane.showMessageDialog(null, mensagem.toString(), "Lista de Contatos", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                new SistemaContatosGUI();
            }
        });
    }
}



