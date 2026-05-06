package br.ufal.ic.myfood.models;

public class Entrega {
    private int id;
    private int idCliente;
    private int idEmpresa;
    private int idPedido;
    private int idEntregador;
    private String destino;

    public Entrega() {}

    public Entrega(int id, int idCliente, int idEmpresa, int idPedido, int idEntregador, String destino) {
        this.id = id;
        this.idCliente = idCliente;
        this.idEmpresa = idEmpresa;
        this.idPedido = idPedido;
        this.idEntregador = idEntregador;
        this.destino = destino;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public int getIdEmpresa() { return idEmpresa; }
    public void setIdEmpresa(int idEmpresa) { this.idEmpresa = idEmpresa; }
    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }
    public int getIdEntregador() { return idEntregador; }
    public void setIdEntregador(int idEntregador) { this.idEntregador = idEntregador; }
    public String getDestino() { return destino; }
    public void setDestino(String destino) { this.destino = destino; }
}