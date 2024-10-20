package br.ufpb.dcx.abraao.contato;

import br.ufpb.dcx.abraao.endereco.Endereco;
import java.util.regex.Pattern;

public class Contato {
    private String nome;
    private String sobrenome;
    private String telefone;
    private String email;
    private Endereco endereco;

    public Contato(String nome, String sobrenome, String telefone, String email, Endereco endereco) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
        setEmail(email);  // Usando o setter para garantir a validação
        this.endereco = endereco;
    }

    // Update contact details
    public void updateContactDetails(String nome, String sobrenome, String telefone, String email, Endereco endereco) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
        setEmail(email);  // Usando o setter para garantir a validação
        this.endereco = endereco;
    }

    // Get full name
    public String getFullName() {
        return this.nome + " " + this.sobrenome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (isValidEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Email inválido");
        }
    }

    private boolean isValidEmail(String email) {
        // Regex para validação de email
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return email != null && pattern.matcher(email).matches();
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    // Método de validação do telefone
    public boolean isValidTelefone(String telefone) {
        String telefoneRegex = "^\\(?(\\d{2})\\)? ?9?\\d{4}-?\\d{4}$"; // Exemplo de validação (XX) 9XXXX-XXXX
        Pattern pattern = Pattern.compile(telefoneRegex);
        return telefone != null && pattern.matcher(telefone).matches();
    }
}