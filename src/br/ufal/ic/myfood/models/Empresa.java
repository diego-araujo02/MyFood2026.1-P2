package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Empresa {
    private int id;
    private int dono;
    private String nome;
    private String endereco;
    private List<Integer> entregadores;

    public Empresa(){
        this.entregadores = new ArrayList<>();
    }

    public Empresa(int id, int dono, String nome, String endereco) throws MyFoodException {
        if (nome == null || nome.trim().isEmpty()){
            throw new EmpresaInvalidaException("Nome invalido");
        }
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new EmpresaInvalidaException("Endereco da empresa invalido");
        }
        this.id = id;
        this.dono = dono;
        this.nome = nome;
        this.endereco = endereco;
        this.entregadores = new ArrayList<>();
    }

    public void alterarHorario(String abre, String fecha) throws MyFoodException {
        throw new EmpresaInvalidaException("Nao e um mercado valido");
    }

    public boolean isFarmacia() { return false; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDono() { return dono; }
    public void setDono(int dono) { this.dono = dono; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public List<Integer> getEntregadores() { return entregadores; }
    public void setEntregadores(List<Integer> entregadores) { this.entregadores = entregadores; }

    public void adicionarEntregador(int entregadorId) {
        if (!this.entregadores.contains(entregadorId)) {
            this.entregadores.add(entregadorId);
        }
    }

    public String getAtributo(String atributo) throws MyFoodException {
        switch (atributo) {
            case "nome":
                return this.nome;
            case "endereco":
                return this.endereco;
            default:
                throw new AtributoInvalidoException("Atributo invalido");
        }
    }
}