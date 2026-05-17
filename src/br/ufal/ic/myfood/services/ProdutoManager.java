package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.models.Produto;
import br.ufal.ic.myfood.exceptions.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class ProdutoManager {

    private Map<Integer, Produto> produtos;
    private int proximoId;

    public ProdutoManager() {
        this.produtos = new LinkedHashMap<>();
        this.proximoId = 1;
    }

    public void validaProduto(String nome, float valor, String categoria) throws MyFoodException {
        if (nome == null || nome.trim().isEmpty()) throw new ProdutoInvalidoException("Nome invalido");
        if (valor < 0) throw new ProdutoInvalidoException("Valor invalido");
        if (categoria == null || categoria.trim().isEmpty()) throw new ProdutoInvalidoException("Categoria invalido");
    }

    public int criarProduto(int empresa, String nome, float valor, String categoria) throws MyFoodException {
        validaProduto(nome, valor, categoria);

        for (Produto p : produtos.values()) {
            if (p.getNome().equals(nome) && p.getEmpresa() == empresa){
                throw new ProdutoJaExisteException("Ja existe um produto com esse nome para essa empresa");
            }
        }

        Produto novoProduto = new Produto(proximoId, empresa, nome, valor, categoria);
        produtos.put(proximoId, novoProduto);
        int idGerado = proximoId;
        proximoId++;

        return idGerado;
    }

    public void editarProduto(int idProduto, String nome, float valor, String categoria) throws MyFoodException {
        Produto p = produtos.get(idProduto);

        if (p == null){
            throw new ProdutoNaoEncontradoException("Produto nao cadastrado");
        }

        validaProduto(nome, valor, categoria);

        p.setNome(nome);
        p.setValor(valor);
        p.setCategoria(categoria);
    }

    public Produto getProdutoInterno(String nome, int empresa) throws MyFoodException {
        for (Produto p : produtos.values()) {
            if (p.getNome().equals(nome) && p.getEmpresa() == empresa){
                return p;
            }
        }
        throw new ProdutoNaoEncontradoException("Produto nao encontrado");
    }
    public Produto getProdutoInterno(int id) throws MyFoodException {
        Produto p = this.produtos.get(id);
        if (p == null) {
            throw new ProdutoNaoEncontradoException("Produto nao encontrado");
        }
        return p;
    }

    public String getProduto(String nome, int empresa, String atributo) throws MyFoodException {
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

    public Produto getProdutoPorId(int id) throws MyFoodException {
        Produto p = produtos.get(id);
        if (p == null) throw new ProdutoNaoEncontradoException("Produto nao encontrado");
        return p;
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