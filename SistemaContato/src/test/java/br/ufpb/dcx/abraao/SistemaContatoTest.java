package br.ufpb.dcx.abraao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SistemaContatoTest {

    private SistemaContato sistemaContato;
    private Contato contato1;
    private Contato contato2;

    @BeforeEach
    public void setUp() {
        sistemaContato = new SistemaContatoImpl();
        Endereco endereco1 = new Endereco("Rua A", "Cidade A", "Estado A", "00000-000");
        Endereco endereco2 = new Endereco("Rua B", "Cidade B", "Estado B", "11111-111");

        contato1 = new Contato("Abraão", "Silva", "99999-9999", "joao@gmail.com", endereco1);
        contato2 = new Contato("Maria", "Oliveira", "88888-8888", "maria@gmail.com", endereco2);
    }

    @Test
    public void testAdicionarContato() {
        sistemaContato.adicionarContato(contato1);
        assertEquals(1, sistemaContato.getContatos().size());
    }

    @Test
    public void testPesquisarContatoPorNome() {
        sistemaContato.adicionarContato(contato1);
        sistemaContato.adicionarContato(contato2);

        Contato encontrado = sistemaContato.pesquisarContatoPorNome("Abraão");
        assertNotNull(encontrado);
        assertEquals("99999-9999", encontrado.getTelefone());
    }

    @Test
    public void testPesquisarContatoPorTelefone() {
        sistemaContato.adicionarContato(contato1);
        sistemaContato.adicionarContato(contato2);

        Contato encontrado = sistemaContato.pesquisarContatoPorTelefone("88888-8888");
        assertNotNull(encontrado);
        assertEquals("Maria", encontrado.getNome());
    }

    @Test
    public void testGetContatos() {
        sistemaContato.adicionarContato(contato1);
        sistemaContato.adicionarContato(contato2);

        assertEquals(2, sistemaContato.getContatos().size());
    }
}
