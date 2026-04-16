package br.ufal.ic.myfood.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class ProdutoManager {

    private Map<Integer, Produto> produtos;
    private int proximoId;

    public ProdutoManager() {
        this.produtos = new LinkedHashMap<>();
        this.proximoId = 1;
    }

    public void validaProduto(String nome, float valor, String categoria) throws Exception {
        if (nome == null || nome.trim().isEmpty()) throw new Exception("Nome invalido");
        if (valor < 0) throw new Exception("Valor invalido");
        if (categoria == null || categoria.trim().isEmpty()) throw new Exception("Categoria invalido");
    }

    public int criarProduto(int empresa, String nome, float valor, String categoria) throws Exception{
        validaProduto(nome, valor, categoria);

        for (Produto p : produtos.values()) {
            if (p.getNome().equals(nome) && p.getEmpresa() == empresa){
                throw new Exception("Ja existe um produto com esse nome para essa empresa");
            }
        }

        Produto novoProduto = new Produto(proximoId, empresa, nome, valor, categoria);
        produtos.put(proximoId, novoProduto);
        int idGerado = proximoId;
        proximoId++;

        return idGerado;
    }

    public void editarProduto(int idProduto, String nome, float valor, String categoria) throws Exception{
        Produto p = produtos.get(idProduto);

        if (p == null){
            throw new Exception("Produto nao cadastrado");
        }

        validaProduto(nome, valor, categoria);

        p.setNome(nome);
        p.setValor(valor);
        p.setCategoria(categoria);
    }

    public Produto getProdutoInterno(String nome, int empresa) throws Exception{
        for (Produto p : produtos.values()) {
            if (p.getNome().equals(nome) && p.getEmpresa() == empresa){
                return p;
            }
        }
        throw new Exception("Produto nao encontrado");
    }

    public String getProduto(String nome, int empresa, String atributo) throws Exception {
        Produto p = getProdutoInterno(nome, empresa);

        if (atributo.equals("empresa")) {
            return String.valueOf(p.getEmpresa());
        }

        return p.getAtributo(atributo);
    }

    public String listarProdutos(int idEmpresa) {
        StringBuilder sb = new StringBuilder();
        sb.append("{[");

        boolean primeiro = true;

        for (Produto p : this.produtos.values()) {
            if (p.getEmpresa() == idEmpresa) {
                if (!primeiro) {
                    sb.append(", ");
                }
                sb.append(p.getNome());
                primeiro = false;
            }
        }

        sb.append("]}");

        return sb.toString();
    }

    public void zerarSistema() {
        this.produtos.clear();
        this.proximoId = 1;
    }


    public Map<Integer, Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(Map<Integer, Produto> produtos) {
        this.produtos = produtos;
    }

    public int getProximoId() {
        return proximoId;
    }

    public void setProximoId(int proximoId) {
        this.proximoId = proximoId;
    }
}
