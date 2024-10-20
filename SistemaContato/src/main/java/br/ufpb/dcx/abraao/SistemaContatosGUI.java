package br.ufpb.dcx.abraao;

import br.ufpb.dcx.abraao.contato.Contato;
import br.ufpb.dcx.abraao.endereco.Endereco;
import br.ufpb.dcx.abraao.sistema.SistemaContato;
import br.ufpb.dcx.abraao.sistema.SistemaContatoImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SistemaContatosGUI extends JFrame {
    private SistemaContato sistemaContato;

    public SistemaContatosGUI() {
        sistemaContato = new SistemaContatoImpl();

        // Adiciona a barra de menu
        JMenuBar menuBar = new JMenuBar();
        JMenu arquivoMenu = new JMenu("Arquivo");
        JMenu contatosMenu = new JMenu("Contatos");

        JMenuItem salvarItem = new JMenuItem("Salvar");
        JMenuItem sairItem = new JMenuItem("Sair");
        salvarItem.addActionListener(e -> salvarContatos());
        sairItem.addActionListener(e -> System.exit(0));
        arquivoMenu.add(salvarItem);
        arquivoMenu.addSeparator();
        arquivoMenu.add(sairItem);

        JMenuItem adicionarItem = new JMenuItem("Adicionar Contato");
        JMenuItem listarContatosItem = new JMenuItem("Listar Contatos");
        JMenuItem pesquisarPorNomeItem = new JMenuItem("Pesquisar Contato por Nome");
        JMenuItem pesquisarPorTelefoneItem = new JMenuItem("Pesquisar Contato por Telefone");

        adicionarItem.addActionListener(e -> adicionarContato());
        listarContatosItem.addActionListener(e -> listarContatos());
        pesquisarPorNomeItem.addActionListener(e -> pesquisarContatoPorNome());
        pesquisarPorTelefoneItem.addActionListener(e -> pesquisarContatoPorTelefone());

        contatosMenu.add(adicionarItem);
        contatosMenu.add(listarContatosItem);
        contatosMenu.add(pesquisarPorNomeItem);
        contatosMenu.add(pesquisarPorTelefoneItem);

        menuBar.add(arquivoMenu);
        menuBar.add(contatosMenu);
        setJMenuBar(menuBar);

        // Adiciona a imagem como fundo
        setBackgroundImage();

        // Configurações da janela
        setTitle("Sistema de Contatos");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setBackgroundImage() {
        try {
            ImageIcon imagemCapa = new ImageIcon("SistemaContato/src/imagemjava.png");
            JLabel labelImagem = new JLabel(imagemCapa);
            labelImagem.setLayout(new BorderLayout());
            labelImagem.setHorizontalAlignment(JLabel.CENTER);
            this.add(labelImagem);
        } catch (Exception e) {
            System.out.println("Erro ao carregar a imagem.");
            e.printStackTrace();
        }
    }

    private void salvarContatos() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showSaveDialog(this);
    
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File arquivo = fileChooser.getSelectedFile();
    
            try (FileWriter writer = new FileWriter(arquivo)) {
                List<Contato> contatos = sistemaContato.getContatos();
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

    private void adicionarContato(String nomePreenchido, String sobrenomePreenchido, String telefonePreenchido, String emailPreenchido, String logradouroPreenchido, String numeroPreenchido, String cidadePreenchida, String estadoPreenchido, String cepPreenchido) {
        JTextField nomeField = new JTextField(nomePreenchido, 10);
        JTextField sobrenomeField = new JTextField(sobrenomePreenchido, 10);
        JTextField telefoneField = new JTextField(telefonePreenchido, 10);
        JTextField emailField = new JTextField(emailPreenchido, 10);
        JTextField logradouroField = new JTextField(logradouroPreenchido, 10);
        JTextField numeroField = new JTextField(numeroPreenchido, 10);
        JTextField cidadeField = new JTextField(cidadePreenchida, 10);
        JTextField estadoField = new JTextField(estadoPreenchido, 10);
        JTextField cepField = new JTextField(cepPreenchido, 10);
        JPanel panel = new JPanel(new GridLayout(0, 1));
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
        panel.add(new JLabel("Cidade:"));
        panel.add(cidadeField);
        panel.add(new JLabel("Estado:"));
        panel.add(estadoField);
        panel.add(new JLabel("CEP:"));
        panel.add(cepField);
        
        int result = JOptionPane.showConfirmDialog(null, panel, "Adicionar Contato", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String nome = nomeField.getText();
                String sobrenome = sobrenomeField.getText();
                String telefone = telefoneField.getText();
                String email = emailField.getText();
                String logradouro = logradouroField.getText();
                String numero = numeroField.getText();
                String cidade = cidadeField.getText();
                String estado = estadoField.getText();
                String cep = cepField.getText();
                
                
                // Validação do e-mail e telefone
                if (!isValidEmail(email)) {
                    throw new IllegalArgumentException("Email inválido");
                }
            
                if (!isValidTelefone(telefone)) {
            
                    throw new IllegalArgumentException("Telefone inválido");
            
                }
                // Criando o novo contato com endereço completo
            
                Contato novoContato = new Contato(nome, sobrenome, telefone, email, new Endereco(logradouro, numero, cidade, estado, cep));
                sistemaContato.adicionarContato(novoContato);
                JOptionPane.showMessageDialog(this, "Contato adicionado com sucesso!", "Adicionar Contato", JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException e) {
                // Exibe uma mensagem amigável para o usuário com o erro ocorrido e mantém os dados preenchidos
                JOptionPane.showMessageDialog(this, "Erro ao adicionar contato: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);           
                // Chama o método novamente, preservando os valores preenchidos           
                adicionarContato(nomeField.getText(), sobrenomeField.getText(), telefoneField.getText(), emailField.getText(),           
                logradouroField.getText(), numeroField.getText(), cidadeField.getText(),            
                estadoField.getText(), cepField.getText());       
            }   
        }
    }


    // Sobrecarga do método para facilitar chamadas

    private void adicionarContato() {

        adicionarContato("", "", "", "", "", "", "", "", "");

    }

    
    // Método de validação do e-mail
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }
    
    // Método de validação do telefone
    private boolean isValidTelefone(String telefone) {
        String telefoneRegex = "^\\d{2}9\\d{8}$"; // Formato XX9XXXXXXXX
        Pattern pattern = Pattern.compile(telefoneRegex);
        return telefone != null && pattern.matcher(telefone).matches();
    }

    private void exibirDetalhesContato(Contato contato) {
        StringBuilder mensagem = new StringBuilder();
        mensagem.append("Nome: ").append(contato.getNome()).append("\n");
        mensagem.append("Sobrenome: ").append(contato.getSobrenome()).append("\n");
        mensagem.append("Telefone: ").append(contato.getTelefone()).append("\n");
        mensagem.append("Email: ").append(contato.getEmail()).append("\n");

        // Adicionando os detalhes de endereço
        Endereco endereco = contato.getEndereco();
        if (endereco != null) {
            mensagem.append("Cidade: ").append(endereco.getCidade()).append("\n");
            mensagem.append("Estado: ").append(endereco.getEstado()).append("\n");
            mensagem.append("CEP: ").append(endereco.getCep()).append("\n");
        }

        JOptionPane.showMessageDialog(this, mensagem.toString(), "Detalhes do Contato", JOptionPane.INFORMATION_MESSAGE);
    }

    private void pesquisarContatoPorNome() {
        String nomePesquisado = JOptionPane.showInputDialog(this, "Digite o nome do contato:", "Pesquisar por Nome", JOptionPane.PLAIN_MESSAGE);
        if (nomePesquisado != null && !nomePesquisado.isEmpty()) {
            Contato contatoEncontrado = sistemaContato.pesquisarContatoPorNome(nomePesquisado);
            if (contatoEncontrado != null) {
                exibirDetalhesContato(contatoEncontrado);
            } else {
                JOptionPane.showMessageDialog(this, "Contato não encontrado!", "Resultado da Pesquisa", JOptionPane.WARNING_MESSAGE);
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
                JOptionPane.showMessageDialog(this, "Contato não encontrado!", "Resultado da Pesquisa", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void listarContatos() {
        List<Contato> contatos = sistemaContato.getContatos();
        if (contatos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum contato cadastrado!", "Lista de Contatos", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder listaContatos = new StringBuilder();
        for (Contato contato : contatos) {
            listaContatos.append(contato.getNome()).append(" ").append(contato.getSobrenome()).append("\n");
        }
        JOptionPane.showMessageDialog(this, listaContatos.toString(), "Lista de Contatos", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SistemaContatosGUI::new);
    }
}
