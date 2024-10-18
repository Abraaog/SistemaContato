package br.ufpb.dcx.abraao.sistema;

import br.ufpb.dcx.abraao.contato.Contato;

import java.io.IOException;
import java.util.List;

public interface SistemaContato {
    void adicionarContato(Contato contato);
    Contato pesquisarContatoPorNome(String nome);
    Contato pesquisarContatoPorTelefone(String telefone);
    List<Contato> listarContatos(); // Alterado para List<Contato>
    void removerContatoPorNome(String nome);
    void removerContatoPorTelefone(String telefone);
    void atualizarTelefone(String nome, String novoTelefone); // Mantenha apenas um método para atualizar o telefone
    int contarContatos();
    void exportarContatos(String caminhoArquivo) throws IOException;
    void importarContatos(String caminhoArquivo) throws IOException;
    List<Contato> getContatos();
}
