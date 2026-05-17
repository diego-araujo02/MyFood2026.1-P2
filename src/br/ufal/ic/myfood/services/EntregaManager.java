package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.models.Entrega;
import br.ufal.ic.myfood.exceptions.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class EntregaManager {
    private Map<Integer, Entrega> entregas;
    private int proximoId;

    public EntregaManager() {
        this.entregas = new LinkedHashMap<>();
        this.proximoId = 1;
    }

    public int criarEntrega(int idCliente, int idEmpresa, int idPedido, int idEntregador, String destino) {
        Entrega e = new Entrega(proximoId, idCliente, idEmpresa, idPedido, idEntregador, destino);
        entregas.put(proximoId, e);
        return proximoId++;
    }

    public int getIdEntrega(int pedido) throws MyFoodException {
        for (Entrega e : entregas.values()) {
            if (e.getIdPedido() == pedido) {
                return e.getId();
            }
        }
        throw new EntregaNaoEncontradaException("Nao existe entrega com esse id");
    }

    public Entrega getEntrega(int id) {
        return entregas.get(id);
    }

    public boolean existeEntrega(int id) {
        return entregas.containsKey(id);
    }

    public int getIdClienteDaEntrega(int id) { return entregas.get(id).getIdCliente(); }
    public int getIdEmpresaDaEntrega(int id) { return entregas.get(id).getIdEmpresa(); }
    public int getIdPedidoDaEntrega(int id) { return entregas.get(id).getIdPedido(); }
    public int getIdEntregadorDaEntrega(int id) { return entregas.get(id).getIdEntregador(); }
    public String getDestinoDaEntrega(int id) { return entregas.get(id).getDestino(); }

    public void zerarSistema() {
        this.entregas.clear();
        this.proximoId = 1;
    }

    public Map<Integer, Entrega> getEntregas() { return entregas; }
    public void setEntregas(Map<Integer, Entrega> entregas) { this.entregas = entregas; }
    public int getProximoId() { return proximoId; }
    public void setProximoId(int proximoId) { this.proximoId = proximoId; }
}