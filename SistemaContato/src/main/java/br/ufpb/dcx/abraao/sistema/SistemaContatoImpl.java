package br.ufpb.dcx.abraao.sistema;

import br.ufpb.dcx.abraao.contato.Contato;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SistemaContatoImpl implements SistemaContato {
    private Map<String, Contato> contatos;

    public SistemaContatoImpl() {
        this.contatos = new HashMap<>();
    }

    public void validarDados(Contato contato) {
        if (contato.getNome() == null || contato.getNome().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");
        }
        if (contato.getSobrenome() == null || contato.getSobrenome().isEmpty()) {
            throw new IllegalArgumentException("Sobrenome não pode ser nulo ou vazio");
        }
        if (contato.getTelefone() == null || contato.getTelefone().isEmpty()) {
            throw new IllegalArgumentException("Telefone não pode ser nulo ou vazio");
        }
        if (contato.getEmail() == null || contato.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser nulo ou vazio");
        }
    }

    @Override
    public void adicionarContato(Contato contato) {
        validarDados(contato);
        if (contatos.containsKey(contato.getNome())) {
            throw new IllegalArgumentException("Contato já existe com o nome " + contato.getNome());
        }
        contatos.put(contato.getNome(), contato);
    }

    @Override
    public Contato pesquisarContatoPorNome(String nome) {
        Contato contato = contatos.get(nome);
        if (contato == null) {
            throw new IllegalArgumentException("Contato com o nome " + nome + " não encontrado.");
        }
        return contato;
    }

    @Override
    public Contato pesquisarContatoPorTelefone(String telefone) {
        for (Contato contato : contatos.values()) {
            if (contato.getTelefone().equals(telefone)) {
                return contato;
            }
        }
        throw new IllegalArgumentException("Contato com o telefone " + telefone + " não encontrado.");
    }

    @Override
    public List<Contato> listarContatos() {
        return new ArrayList<>(contatos.values());
    }

    @Override
    public void removerContatoPorNome(String nome) {
        if (contatos.remove(nome) == null) {
            throw new IllegalArgumentException("Contato com o nome " + nome + " não encontrado.");
        }
    }

    @Override
    public void removerContatoPorTelefone(String telefone) {
        boolean removed = contatos.values().removeIf(contato -> contato.getTelefone().equals(telefone));
        if (!removed) {
            throw new IllegalArgumentException("Contato com o telefone " + telefone + " não encontrado.");
        }
    }

    @Override
    public void atualizarTelefone(String nome, String novoTelefone) {
        Contato contato = contatos.get(nome);
        if (contato == null) {
            throw new IllegalArgumentException("Contato com o nome " + nome + " não encontrado.");
        }
        contato.setTelefone(novoTelefone);
    }

    @Override
    public int contarContatos() {
        return contatos.size();
    }

    @Override
    public List<Contato> getContatos() {
        return new ArrayList<>(contatos.values());
    }

    @Override
    public void exportarContatos(String caminhoArquivo) throws IOException {
        try (FileWriter writer = new FileWriter(caminhoArquivo)) {
            for (Contato contato : contatos.values()) {
                writer.write(contato.getNome() + "," + contato.getSobrenome() + "," +
                    contato.getTelefone() + "," + contato.getEmail() + "," +
                    (contato.getEndereco() != null ? contato.getEndereco().getLogradouro() + ", " + contato.getEndereco().getNumero() : "Sem Endereço") + "\n");
            }
        }
    }

    @Override
    public void importarContatos(String caminhoArquivo) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length >= 4) { // Considera que o endereço pode ser opcional
                    String nome = dados[0];
                    String sobrenome = dados[1];
                    String telefone = dados[2];
                    String email = dados[3];
                    Contato contato = new Contato(nome, sobrenome, telefone, email, null);
                    adicionarContato(contato);
                }
            }
        }
    }
}
