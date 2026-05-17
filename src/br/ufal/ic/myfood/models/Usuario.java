package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.*;

import java.util.List;

public abstract class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String endereco;

    public Usuario() {}

    public Usuario(int id, String nome, String email, String senha, String endereco) throws MyFoodException {
        if (nome == null || nome.isEmpty()) {
            throw new UsuarioInvalidoException("Nome invalido");
        }
        if (email == null || email.isEmpty() || !email.contains("@")) {
                throw new UsuarioInvalidoException("Email invalido");
        }
        if (senha == null || senha.isEmpty()) {
            throw new UsuarioInvalidoException("Senha invalido");
        }
        if (endereco == null || endereco.isEmpty()) {
            throw new UsuarioInvalidoException("Endereco invalido");
        }

        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
        this.id = id;
    }

    public String getAtributo(String atributo) throws MyFoodException {
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
                throw new AtributoInvalidoException("Atributo invalido");
        }
    }

    public void verificarPermissaoEmpresa() throws MyFoodException {
        throw new PermissaoNegadaException("Usuario nao pode criar uma empresa");
    }

    public boolean isDono() {
        return false;
    }

    public boolean isEntregador() {
        return false;
    }

    public List<Integer> getEmpresas() throws MyFoodException {
        throw new PermissaoNegadaException("Usuario nao e um entregador");
    }

    public void adicionarEmpresa(int empresaId) throws MyFoodException {
        throw new PermissaoNegadaException("Usuario nao e um entregador");
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
