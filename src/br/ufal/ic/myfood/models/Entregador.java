package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.*;
import java.util.ArrayList;
import java.util.List;

public class Entregador extends Usuario {
    private String veiculo;
    private String placa;
    private List<Integer> empresas;
    private boolean emEntrega;

    public Entregador() {
        super();
        this.empresas = new ArrayList<>();
    }

    public Entregador(int id, String nome, String email, String senha, String endereco, String veiculo, String placa) throws MyFoodException {
        super(id, nome, email, senha, endereco);

        if (veiculo == null || veiculo.trim().isEmpty()) throw new UsuarioInvalidoException("Veiculo invalido");
        if (placa == null || placa.trim().isEmpty()) throw new UsuarioInvalidoException("Placa invalido");

        this.veiculo = veiculo;
        this.placa = placa;
        this.empresas = new ArrayList<>();
    }

    public String getVeiculo() { return veiculo; }
    public void setVeiculo(String veiculo) { this.veiculo = veiculo; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public boolean isEmEntrega() { return emEntrega; }
    public void setEmEntrega(boolean emEntrega) { this.emEntrega = emEntrega; }

    @Override
    public List<Integer> getEmpresas() { return empresas; }
    public void setEmpresas(List<Integer> empresas) { this.empresas = empresas; }

    @Override
    public void adicionarEmpresa(int empresaId) {
        if (!this.empresas.contains(empresaId)) {
            this.empresas.add(empresaId);
        }
    }

    @Override
    public boolean isEntregador() {
        return true;
    }

    @Override
    public String getAtributo(String atributo) throws MyFoodException {
        switch (atributo) {
            case "veiculo": return this.veiculo;
            case "placa": return this.placa;
            default: return super.getAtributo(atributo);
        }
    }
}