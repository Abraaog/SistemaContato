package br.ufpb.dcx.abraao.sistema;

import br.ufpb.dcx.abraao.contato.Contato;

import java.util.HashMap;
import java.util.Map;
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
            throw new IllegalArgumentException("Já existe um contato com esse nome.");
        }
        contatos.put(contato.getNome(), contato);
    }  


    public class ContatoNaoEncontradoException extends RuntimeException {
        public ContatoNaoEncontradoException(String mensagem) {
            super(mensagem);
        }
    }
    
    @Override
    public Contato pesquisarContatoPorNome(String nome) {
        Contato contato = contatos.get(nome);
        if (contato == null) {
            throw new ContatoNaoEncontradoException("Contato não encontrado: " + nome);
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
        return null;
    }



    @Override
    public ArrayList<Contato> listarContatos() {
        return new ArrayList<>(contatos.values());
    }


    @Override
    public void removerContatoPorNome(String nome) {
        contatos.remove(nome);
    }

    @Override
    public void removerContatoPorTelefone(String telefone) {
        contatos.values().removeIf(contato -> contato.getTelefone().equals(telefone));
    }

    @Override
    public void atualizarTelefone(String nome, String novoTelefone) {
        Contato contato = contatos.get(nome);
        if (contato != null) {
            contato.setTelefone(novoTelefone);
        }
    }

    @Override
    public int contarContatos() {
        return contatos.size();
    }

    @Override
    public ArrayList<Contato> getContatos() {
        return new ArrayList<>(contatos.values());
    }}

