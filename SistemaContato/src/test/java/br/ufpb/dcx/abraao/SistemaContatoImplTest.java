package br.ufpb.dcx.abraao;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class SistemaContatoImplTest implements SistemaContato {
    private Map<String, Contato> contatos;

    public SistemaContatoImplTest() {
        this.contatos = new HashMap<>();
    }

    @Override
    public void adicionarContato(Contato contato) {
        contatos.put(contato.getNome(), contato);
    }

    @Override
    public Contato pesquisarContatoPorNome(String nome) {
        return contatos.get(nome);
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
        ArrayList<Contato> listaContatos = new ArrayList<>(contatos.values());
        listaContatos.sort((contato1, contato2) -> contato1.getNome().compareTo(contato2.getNome()));
        return listaContatos;
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
}

