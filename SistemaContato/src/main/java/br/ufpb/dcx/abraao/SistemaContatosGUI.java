package br.ufpb.dcx.abraao;

import br.ufpb.dcx.abraao.contato.Contato;
import br.ufpb.dcx.abraao.endereco.Endereco;
import br.ufpb.dcx.abraao.sistema.SistemaContato;
import br.ufpb.dcx.abraao.sistema.SistemaContatoImpl;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class SistemaContatosGUI extends JFrame {
    private SistemaContato sistemaContato;

    public SistemaContatosGUI() {
        sistemaContato = new SistemaContatoImpl();
        setUpMenuBar();
        setUpFrame();
    }

    private void setUpFrame() {
        setTitle("Sistema de Contatos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setUpMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu arquivoMenu = createArquivoMenu();
        JMenu contatosMenu = createContatosMenu();

        menuBar.add(arquivoMenu);
        menuBar.add(contatosMenu);
        setJMenuBar(menuBar);
    }

    private JMenu createArquivoMenu() {
        JMenu arquivoMenu = new JMenu("Arquivo");

        JMenuItem exportarItem = new JMenuItem("Exportar Contatos");
        exportarItem.addActionListener(e -> exportarContatos());

        JMenuItem importarItem = new JMenuItem("Importar Contatos");
        importarItem.addActionListener(e -> importarContatos());

        JMenuItem sairItem = new JMenuItem("Sair");
        sairItem.addActionListener(e -> System.exit(0));

        arquivoMenu.add(exportarItem);
        arquivoMenu.add(importarItem);
        arquivoMenu.addSeparator();
        arquivoMenu.add(sairItem);

        return arquivoMenu;
    }

    private JMenu createContatosMenu() {
        JMenu contatosMenu = new JMenu("Contatos");

        JMenuItem adicionarItem = new JMenuItem("Adicionar Contato");
        adicionarItem.addActionListener(e -> adicionarContato());

        JMenuItem listarContatosItem = new JMenuItem("Listar Contatos");
        listarContatosItem.addActionListener(e -> listarContatos());

        JMenuItem pesquisarContatoButton = new JMenuItem("Pesquisar Contato");
        pesquisarContatoButton.addActionListener(e -> pesquisarContatoPorNome());

        JMenuItem pesquisarPorTelefoneItem = new JMenuItem("Pesquisar Contato por Telefone");
        pesquisarPorTelefoneItem.addActionListener(e -> pesquisarContatoPorTelefone());

        contatosMenu.add(adicionarItem);
        contatosMenu.add(listarContatosItem);
        contatosMenu.add(pesquisarContatoButton);
        contatosMenu.add(pesquisarPorTelefoneItem);

        return contatosMenu;
    }

    private void adicionarContato() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JTextField nomeField = new JTextField(10);
        JTextField sobrenomeField = new JTextField(10);
        JTextField telefoneField = new JTextField(10);
        JTextField emailField = new JTextField(10);
        JTextField logradouroField = new JTextField(10);
        JTextField numeroField = new JTextField(10);
    
        panel.add(new JLabel("Nome:"));
        panel.add(nomeField);
        panel.add(new JLabel("Sobrenome:"));
        panel.add(sobrenomeField);
        panel.add(new JLabel("Telefone:"));
        panel.add(telefoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Logradouro:"));
        panel.add(logradouroField);
        panel.add(new JLabel("Número:"));
        panel.add(numeroField);
    
        int result = JOptionPane.showConfirmDialog(null, panel, "Adicionar Contato", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String nome = nomeField.getText().trim();
            String sobrenome = sobrenomeField.getText().trim();
            String telefone = telefoneField.getText().trim();
            String email = emailField.getText().trim();
            String logradouro = logradouroField.getText().trim();
            String numero = numeroField.getText().trim();
    
            if (nome.isEmpty() || sobrenome.isEmpty() || telefone.isEmpty() || email.isEmpty() || logradouro.isEmpty() || numero.isEmpty()) {
                showErrorDialog("Todos os campos devem ser preenchidos.");
                return;
            }
    
            if (sistemaContato.pesquisarContatoPorNome(nome) != null) {
                showErrorDialog("Contato já existe com o nome: " + nome);
                return;
            }
    
            Endereco endereco = new Endereco(logradouro, numero);
            Contato novoContato = new Contato(nome, sobrenome, telefone, email, endereco);
            try {
                sistemaContato.adicionarContato(novoContato);
                showSuccessDialog("Contato adicionado com sucesso!");
            } catch (IllegalArgumentException ex) {
                showErrorDialog(ex.getMessage());
            }
        }
    }


private void exibirDetalhesContato(Contato contato) {
    String mensagem = String.format("Nome: %s\nSobrenome: %s\nTelefone: %s\nEmail: %s\nEndereço: %s, %s",
                                     contato.getNome(), contato.getSobrenome(), contato.getTelefone(), 
                                     contato.getEmail(), contato.getEndereco().getLogradouro(), 
                                     contato.getEndereco().getNumero());
    JOptionPane.showMessageDialog(this, mensagem, "Detalhes do Contato", JOptionPane.INFORMATION_MESSAGE);
}


    private void pesquisarContatoPorNome() {
        String nomePesquisado = JOptionPane.showInputDialog(this, "Digite o nome do contato:", "Pesquisar por Nome", JOptionPane.PLAIN_MESSAGE);
        if (nomePesquisado != null && !nomePesquisado.isEmpty()) {
            try {
                Contato contatoEncontrado = sistemaContato.pesquisarContatoPorNome(nomePesquisado);
                if (contatoEncontrado != null) {
                    exibirDetalhesContato(contatoEncontrado);
                } else {
                    showInfoDialog("Nenhum contato encontrado com o nome: " + nomePesquisado);
                }
            } catch (Exception e) {
                showErrorDialog("Erro ao pesquisar contato: " + e.getMessage());
            }
        } else {
            showErrorDialog("Nome não pode ser vazio.");
        }
    }

    private void pesquisarContatoPorTelefone() {
        String telefonePesquisado = JOptionPane.showInputDialog(this, "Digite o telefone do contato:", "Pesquisar por Telefone", JOptionPane.PLAIN_MESSAGE);
        if (telefonePesquisado != null && !telefonePesquisado.isEmpty()) {
            try {
                Contato contatoEncontrado = sistemaContato.pesquisarContatoPorTelefone(telefonePesquisado);
                if (contatoEncontrado != null) {
                    exibirDetalhesContato(contatoEncontrado);
                } else {
                    showInfoDialog("Nenhum contato encontrado com o telefone: " + telefonePesquisado);
                }
            } catch (Exception e) {
                showErrorDialog("Erro ao pesquisar contato: " + e.getMessage());
            }
        } else {
            showErrorDialog("Telefone não pode ser vazio.");
        }
    }

    private void listarContatos() {
        StringBuilder mensagem = new StringBuilder("Listando contatos:\n\n");
        List<Contato> contatos = sistemaContato.getContatos(); // Agora deve funcionar corretamente
    
        if (contatos.isEmpty()) {
            mensagem.append("Nenhum contato para exibir.");
        } else {
            for (Contato contato : contatos) {
                mensagem.append(String.format("Nome: %s\nSobrenome: %s\nTelefone: %s\nEmail: %s\n-------------------------\n",
                                               contato.getNome(), contato.getSobrenome(), contato.getTelefone(), contato.getEmail()));
            }
        }
    
        JOptionPane.showMessageDialog(null, mensagem.toString(), "Lista de Contatos", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void exportarContatos() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showSaveDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File arquivo = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(arquivo)) {
                List<Contato> contatos = sistemaContato.getContatos(); // Use List<Contato> em vez de ArrayList<Contato>
                JSONArray jsonArray = new JSONArray();
    
                for (Contato contato : contatos) {
                    JSONObject contatoJson = new JSONObject();
                    contatoJson.put("nome", contato.getNome());
                    contatoJson.put("sobrenome", contato.getSobrenome());
                    contatoJson.put("telefone", contato.getTelefone());
                    contatoJson.put("email", contato.getEmail());
                    jsonArray.put(contatoJson);
                }
                writer.write(jsonArray.toString(2)); // Escreve o JSON com indentação
                showSuccessDialog("Contatos exportados com sucesso!");
            } catch (IOException e) {
                showErrorDialog("Erro ao exportar contatos: " + e.getMessage());
            }
        }
    }

    private void importarContatos() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(this);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File arquivo = fileChooser.getSelectedFile();
            try {
                String json = new String(Files.readAllBytes(Paths.get(arquivo.getAbsolutePath())));
                JSONArray jsonArray = new JSONArray(json);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject contatoJson = jsonArray.getJSONObject(i);
                    String nome = contatoJson.getString("nome");
                    String sobrenome = contatoJson.getString("sobrenome");
                    String telefone = contatoJson.getString("telefone");
                    String email = contatoJson.getString("email");

                    Contato contato = new Contato(nome, sobrenome, telefone, email, null);
                    sistemaContato.adicionarContato(contato);
                }
                showSuccessDialog("Contatos importados com sucesso!");
            } catch (IOException e) {
                showErrorDialog("Erro ao importar contatos: " + e.getMessage());
            } catch (Exception e) {
                showErrorDialog("Erro ao processar contatos: " + e.getMessage());
            }
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void showSuccessDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showInfoDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SistemaContatosGUI::new);
    }
}
