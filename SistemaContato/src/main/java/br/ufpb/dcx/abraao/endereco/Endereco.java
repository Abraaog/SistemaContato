package br.ufpb.dcx.abraao.endereco;

public class Endereco {
    private String logradouro;
    private String numero;
    private String cidade;
    private String estado;
    private String cep;

    // Construtor
    public Endereco(String logradouro, String numero, String cidade, String estado, String cep) {
        setLogradouro(logradouro);
        setNumero(numero);
        setCidade(cidade);
        setEstado(estado);
        setCep(cep);
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
    
    public String getCidade() {
        return cidade;
    }
    
    public void setCidade(String cidade) {
        if (cidade == null || cidade.isEmpty()) {
            throw new IllegalArgumentException("Cidade não pode ser vazia.");
        }
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        if (estado == null || estado.isEmpty()) {
            throw new IllegalArgumentException("Estado não pode ser vazio.");
        }
        this.estado = estado;
    }



    public String getCep() {
        return cep;
    }
    
    public void setCep(String cep) {
        // Validação básica para CEP
        if (cep == null || cep.isEmpty()) {
            throw new IllegalArgumentException("CEP não pode ser vazio.");
        }
        this.cep = cep;
    }
    


    // Método toString para representar o endereço como uma string
    @Override
    public String toString() {
        return logradouro + ", " + numero + ", " + cidade + ", " + estado + ", " + cep;
    }
}