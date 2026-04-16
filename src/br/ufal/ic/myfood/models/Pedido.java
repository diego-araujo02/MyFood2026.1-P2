package br.ufal.ic.myfood.models;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int numero;
    private int cliente;
    private int empresa;
    private String estado;
    private List<Produto> produtos;

    public Pedido() {
        this.produtos = new ArrayList<>();
    }

    public Pedido(int numero, int cliente, int empresa) {
        this.numero = numero;
        this.cliente = cliente;
        this.empresa = empresa;
        this.estado = "aberto";
        this.produtos = new ArrayList<>();
    }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public int getCliente() { return cliente; }
    public void setCliente(int cliente) { this.cliente = cliente; }

    public int getEmpresa() { return empresa; }
    public void setEmpresa(int empresa) { this.empresa = empresa; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public List<Produto> getProdutos() { return produtos; }
    public void setProdutos(List<Produto> produtos) { this.produtos = produtos; }

    public float calcularValorTotal() {
        float total = 0;
        for (Produto p : produtos) {
            total += p.getValor();
        }
        return total;
    }
}