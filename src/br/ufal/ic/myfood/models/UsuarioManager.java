package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.UsuarioJaExisteException;
import br.ufal.ic.myfood.exceptions.UsuarioNaoExisteException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsuarioManager {

    private Map<Integer, Usuario> usuarios;
    private int proximoId;


    public UsuarioManager() {
        this.usuarios = new HashMap<>();
        this.proximoId = 1;
    }

    private void verificarEmailExistente(String email) throws UsuarioJaExisteException {
        for (Usuario u : this.usuarios.values()) {
            if (u.getEmail().equals(email)) {
                throw new UsuarioJaExisteException();
            }
        }
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws Exception {
        Customer novoCliente = new Customer(proximoId, nome, email, senha, endereco);
        verificarEmailExistente(email);
        this.usuarios.put(proximoId, novoCliente);
        proximoId++;
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws Exception {
        Owner novoDono = new Owner(proximoId, nome, email, senha, endereco, cpf);
        verificarEmailExistente(email);
        this.usuarios.put(proximoId, novoDono);
        proximoId++;
    }

    public String getAtributoUsuario(int id, String atributo) throws Exception {
        Usuario usuario = this.usuarios.get(id);
        if (usuario == null) {
            throw new UsuarioNaoExisteException();
        }
        return usuario.getAtributo(atributo);
    }

    public int login(String email, String senha) throws Exception{
        for (Usuario u : this.usuarios.values()) {
            if (u.getEmail().equals(email) && u.getSenha().equals(senha)) {
                return u.getId();
            }
        }
        throw new Exception("Login ou senha invalidos");
    }

    public void zerarSistema() {
        this.usuarios.clear();
        this.proximoId = 1;
    }

    public void encerrarSistema() {}

    public void verificarPermissao(int id) throws Exception {
        Usuario usuario = this.usuarios.get(id);
        if (usuario == null) {
            throw new UsuarioNaoExisteException();
        } else {
            usuario.verificarPermissaoEmpresa();
        }
    }

    public Map<Integer, Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(Map<Integer, Usuario> usuarios) { this.usuarios = usuarios; }

    public int getProximoId() { return proximoId; }
    public void setProximoId(int proximoId) { this.proximoId = proximoId; }
}
