package br.ufal.ic.myfood.models;

import java.util.Locale;

public class Produto {
    private int id;
    private String nome;
    private float valor;
    private String categoria;
    private int empresa;

    public Produto(){}

    public Produto(int id, int empresa, String nome, float valor, String categoria) throws Exception{
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.categoria = categoria;
        this.empresa = empresa;
    }

    public String getAtributo(String atributo) throws Exception{
        switch (atributo){
            case "id":
                return String.valueOf(this.id);
            case "nome":
                return this.nome;
            case "valor":
                return String.format(Locale.US, "%.2f", valor);
            case "categoria":
                return this.categoria;
            default:
                throw new Exception("Atributo nao existe");
        }
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float preco) {
        this.valor = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmpresa() {
        return empresa;
    }

    public void setEmpresa(int empresa) {
        this.empresa = empresa;
    }
}
