package br.ufal.ic.myfood.models;

import java.util.UUID;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String endereco;

    public Usuario() {}

    public Usuario(int id, String nome, String email, String senha, String endereco) throws Exception {
        if (nome == null || nome.isEmpty()) {
            throw new Exception("Nome invalido");
        }
        if (email == null || email.isEmpty() || !email.contains("@")) {
                throw new Exception("Email invalido");
        }
        if (senha == null || senha.isEmpty()) {
            throw new Exception("Senha invalido");
        }
        if (endereco == null || endereco.isEmpty()) {
            throw new Exception("Endereco invalido");
        }

        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
        this.id = id;
    }

    public String getAtributo(String atributo) throws Exception {
        switch (atributo) {
            case "nome":
                return this.nome;
            case "email":
                return this.email;
            case "senha":
                return this.senha;
            case "endereco":
                return this.endereco;
            default:
                throw new Exception("Atributo invalido");
        }
    }

    public void verificarPermissaoEmpresa() throws Exception {
        throw new Exception("Usuario nao pode criar uma empresa");
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
