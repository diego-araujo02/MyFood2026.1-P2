package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.models.Pedido;
import br.ufal.ic.myfood.models.Produto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PedidoManager {
    private Map<Integer, Pedido> pedidos;
    private int proximoNumero;

    public PedidoManager() {
        this.pedidos = new LinkedHashMap<>();
        this.proximoNumero = 1;
    }

    public int criarPedido(int cliente, int empresa) throws Exception {
        for (Pedido p : pedidos.values()) {
            if (p.getCliente() == cliente && p.getEmpresa() == empresa && p.getEstado().equals("aberto")) {
                throw new Exception("Nao e permitido ter dois pedidos em aberto para a mesma empresa");
            }
        }
        Pedido novo = new Pedido(proximoNumero, cliente, empresa);
        pedidos.put(proximoNumero, novo);
        return proximoNumero++;
    }

    public void adicionarProduto(int numero, Produto produto) throws Exception {
        Pedido p = pedidos.get(numero);

        if (p == null) {
            throw new Exception("Nao existe pedido em aberto");
        }
        if (!p.getEstado().equals("aberto")) {
            throw new Exception("Nao e possivel adcionar produtos a um pedido fechado");
        }
        if (produto.getEmpresa() != p.getEmpresa()) {
            throw new Exception("O produto nao pertence a essa empresa");
        }

        p.getProdutos().add(produto);
    }

    public void removerProduto(int numero, String nomeProduto) throws Exception {
        Pedido p = getPedidoInterno(numero);
        if (!p.getEstado().equals("aberto")) {
            throw new Exception("Nao e possivel remover produtos de um pedido fechado");
        }

        boolean removido = false;
        for (int i = 0; i < p.getProdutos().size(); i++) {
            if (p.getProdutos().get(i).getNome().equals(nomeProduto)) {
                p.getProdutos().remove(i);
                removido = true;
                break;
            }
        }
        if (!removido) throw new Exception("Produto nao encontrado");
    }

    public void fecharPedido(int numero) throws Exception {
        Pedido p = getPedidoInterno(numero);
        p.setEstado("preparando");
    }

    public int getIdClienteDoPedido(int numero) throws Exception {
        return getPedidoInterno(numero).getCliente();
    }

    public int getIdEmpresaDoPedido(int numero) throws Exception {
        return getPedidoInterno(numero).getEmpresa();
    }

    public String getAtributoPedido(int numero, String atributo) throws Exception {
        Pedido p = getPedidoInterno(numero);
        switch (atributo) {
            case "estado":
                return p.getEstado();
            case "valor":
                return String.format(Locale.US, "%.2f", p.calcularValorTotal());
            case "produtos":
                List<String> nomes = new ArrayList<>();
                for (Produto prod : p.getProdutos()) nomes.add(prod.getNome());
                return "{[" + String.join(", ", nomes) + "]}";
            default:
                throw new Exception("Atributo nao existe");
        }
    }

    public int getNumeroPedido(int cliente, int empresa, int indice) throws Exception {
        List<Integer> encontrados = new ArrayList<>();
        for (Pedido p : pedidos.values()) {
            if (p.getCliente() == cliente && p.getEmpresa() == empresa) {
                encontrados.add(p.getNumero());
            }
        }
        if (indice < 0 || indice >= encontrados.size()) {
            throw new Exception("Pedido nao encontrado");
        }
        return encontrados.get(indice);
    }

    private Pedido getPedidoInterno(int numero) throws Exception {
        Pedido p = pedidos.get(numero);
        if (p == null) throw new Exception("Pedido nao encontrado");
        return p;
    }

    public void zerarSistema() {
        this.pedidos.clear();
        this.proximoNumero = 1;
    }

    public Map<Integer, Pedido> getPedidos() { return pedidos; }
    public void setPedidos(Map<Integer, Pedido> pedidos) { this.pedidos = pedidos; }

    public int getProximoNumero() { return proximoNumero; }
    public void setProximoNumero(int proximoNumero) { this.proximoNumero = proximoNumero; }
}