package br.ufpb.dcx.abraao.endereco;

public class Endereco {
    private String logradouro;
    private String numero;

    // Construtor
    public Endereco(String logradouro, String numero) {
        setLogradouro(logradouro); // Usando o setter para validação
        setNumero(numero); // Usando o setter para validação
    }

    // Métodos get e set que permanecem
    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        if (logradouro == null || logradouro.isEmpty()) {
            throw new IllegalArgumentException("Logradouro não pode ser vazio.");
        }
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        if (numero == null || numero.isEmpty()) {
            throw new IllegalArgumentException("Número não pode ser vazio.");
        }
        this.numero = numero;
    }

    // Método toString para representar o endereço como uma string
    @Override
    public String toString() {
        return logradouro + ", " + numero;
    }
}
