package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.exceptions.*;
import br.ufal.ic.myfood.models.Cliente;
import br.ufal.ic.myfood.models.Dono;
import br.ufal.ic.myfood.models.Entregador;
import br.ufal.ic.myfood.models.Usuario;

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

    private void verificarEmailExistente(String email) throws MyFoodException {
        for (Usuario u : this.usuarios.values()) {
            if (u.getEmail().equals(email)) {
                throw new UsuarioJaExisteException("Conta com esse email ja existe");
            }
        }
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws MyFoodException {
        Cliente novoCliente = new Cliente(proximoId, nome, email, senha, endereco);
        verificarEmailExistente(email);
        this.usuarios.put(proximoId, novoCliente);
        proximoId++;
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws MyFoodException {
        Dono novoDono = new Dono(proximoId, nome, email, senha, endereco, cpf);
        verificarEmailExistente(email);
        this.usuarios.put(proximoId, novoDono);
        proximoId++;
    }

    public boolean isEntregadorValido(int id) {
        if (!usuarios.containsKey(id)) {
            return false;
        }
        return usuarios.get(id).isEntregador();
    }

    public String getAtributoUsuario(int id, String atributo) throws MyFoodException {
        Usuario usuario = this.usuarios.get(id);
        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("Usuario nao cadastrado.");
        }
        return usuario.getAtributo(atributo);
    }

    public int login(String email, String senha) throws MyFoodException {
        for (Usuario u : this.usuarios.values()) {
            if (u.getEmail().equals(email) && u.getSenha().equals(senha)) {
                return u.getId();
            }
        }
        throw new UsuarioNaoEncontradoException("Login ou senha invalidos");
    }

    public void zerarSistema() {
        this.usuarios.clear();
        this.proximoId = 1;
    }

    public void verificarPermissao(int id) throws MyFoodException {
        Usuario usuario = this.usuarios.get(id);
        if (usuario == null) {
            throw new UsuarioNaoEncontradoException("Usuario nao cadastrado.");
        } else {
            usuario.verificarPermissaoEmpresa();
        }
    }

    public void validarSeEDono(int idDono) throws MyFoodException {
        Usuario u = usuarios.get(idDono);
        if (u == null || !u.isDono()) {
            throw new PermissaoNegadaException("Usuario nao pode criar uma empresa");
        }
    }

    public void criarEntregador(String nome, String email, String senha, String endereco, String veiculo, String placa) throws MyFoodException {
        Entregador e = new Entregador(proximoId, nome, email, senha, endereco, veiculo, placa);

        for (Usuario u : usuarios.values()) {
            if (u.isEntregador() && placa.equals(((Entregador) u).getPlaca())) {
                throw new UsuarioInvalidoException("Placa invalido");
            }
        }

        verificarEmailExistente(email);

        usuarios.put(proximoId, e);
        proximoId++;
    }

    public void validarSeEEntregador(int id) throws MyFoodException {
        Usuario u = usuarios.get(id);
        if (u == null || !u.isEntregador()) {
            throw new PermissaoNegadaException("Usuario nao e um entregador");
        }
    }

    public boolean isDono(int idUsuario) {
        return usuarios.get(idUsuario).isDono();
    }

    public List<Integer> getEmpresasDoEntregador(int entregador) throws MyFoodException {
        return new ArrayList<>(usuarios.get(entregador).getEmpresas());
    }

    public void adicionarEmpresaAoEntregador(int entregadorId, int empresaId) throws MyFoodException {
        getUsuarios().get(entregadorId).adicionarEmpresa(empresaId);
    }

    public boolean isEntregadorEmEntrega(int entregador) throws MyFoodException {
        Entregador ent = (Entregador) usuarios.get(entregador);
        return ent.isEmEntrega();
    }

    public void setEntregadorEmEntrega(int entregador, boolean status) throws MyFoodException {
        Entregador ent = (Entregador) usuarios.get(entregador);
        ent.setEmEntrega(status);
    }

    public Map<Integer, Usuario> getUsuarios() { return usuarios; }
    public void setUsuarios(Map<Integer, Usuario> usuarios) { this.usuarios = usuarios; }

    public int getProximoId() { return proximoId; }
    public void setProximoId(int proximoId) { this.proximoId = proximoId; }
}