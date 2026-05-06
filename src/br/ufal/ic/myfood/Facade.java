package br.ufal.ic.myfood;

import br.ufal.ic.myfood.services.*;
import br.ufal.ic.myfood.utils.PersistenciaXML;
import java.util.ArrayList;
import java.util.List;

public class Facade {

    private UsuarioManager userManager;
    private EmpresaManager empresaManager;
    private ProdutoManager produtoManager;
    private PedidoManager pedidoManager;
    private EntregaManager entregaManager;

    public Facade() {
        UsuarioManager uManagerCarregado = (UsuarioManager) PersistenciaXML.carregar("data/usuarios.xml");
        this.userManager = (uManagerCarregado != null) ? uManagerCarregado : new UsuarioManager();

        EmpresaManager eManagerCarregado = (EmpresaManager) PersistenciaXML.carregar("data/empresas.xml");
        this.empresaManager = (eManagerCarregado != null) ? eManagerCarregado : new EmpresaManager();

        ProdutoManager pManagerCarregado = (ProdutoManager) PersistenciaXML.carregar("data/produtos.xml");
        this.produtoManager = (pManagerCarregado != null) ? pManagerCarregado : new ProdutoManager();

        PedidoManager peManagerCarregado = (PedidoManager) PersistenciaXML.carregar("data/pedidos.xml");
        this.pedidoManager = (peManagerCarregado != null) ? peManagerCarregado : new PedidoManager();

        EntregaManager etManagerCarregado = (EntregaManager) PersistenciaXML.carregar("data/entregas.xml");
        this.entregaManager = (etManagerCarregado != null) ? etManagerCarregado : new EntregaManager();
    }


    public void zerarSistema() {
        this.userManager.zerarSistema();
        this.empresaManager.zerarSistema();
        this.produtoManager.zerarSistema();
        this.pedidoManager.zerarSistema();
        this.entregaManager.zerarSistema();

        encerrarSistema();
    }

    public String getAtributoUsuario(int id, String atributo) throws Exception {
        return this.userManager.getAtributoUsuario(id, atributo);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco)
            throws Exception {
        this.userManager.criarUsuario(nome, email, senha, endereco);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf)
            throws Exception {
        this.userManager.criarUsuario(nome, email, senha, endereco, cpf);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa) throws Exception {
        userManager.criarEntregador(nome, email, senha, endereco, veiculo, placa);
    }

    public void cadastrarEntregador(int empresa, int entregador) throws Exception {
        userManager.validarSeEEntregador(entregador);
        empresaManager.adicionarEntregadorNaEmpresa(empresa, entregador);
        userManager.adicionarEmpresaAoEntregador(entregador, empresa);
    }

    public String getEntregadores(int empresa) throws Exception {
        List<Integer> ids = empresaManager.getEntregadoresDaEmpresa(empresa);
        List<String> emails = new ArrayList<>();

        for (int id : ids) {
            emails.add(userManager.getAtributoUsuario(id, "email"));
        }
        return "{[" + String.join(", ", emails) + "]}";
    }

    public String getEmpresas(int entregador) throws Exception {
        userManager.validarSeEEntregador(entregador);

        List<Integer> ids = userManager.getEmpresasDoEntregador(entregador);
        List<String> dados = new ArrayList<>();

        for (int id : ids) {
            String nome = empresaManager.getAtributoEmpresa(id, "nome");
            String end = empresaManager.getAtributoEmpresa(id, "endereco");
            dados.add("[" + nome + ", " + end + "]");
        }
        return "{[" + String.join(", ", dados) + "]}";
    }

    public int login(String email, String senha) throws Exception {
        return this.userManager.login(email, senha);
    }

    public void encerrarSistema() {
        PersistenciaXML.salvar(this.userManager, "data/usuarios.xml");
        PersistenciaXML.salvar(this.empresaManager, "data/empresas.xml");
        PersistenciaXML.salvar(this.produtoManager, "data/produtos.xml");
        PersistenciaXML.salvar(this.pedidoManager, "data/pedidos.xml");
        PersistenciaXML.salvar(this.entregaManager, "data/entregas.xml");
    }

    private void validarParametrosBasicos(String tipoEmpresa, String nome, String endereco) throws Exception {
        if (tipoEmpresa == null || tipoEmpresa.trim().isEmpty()) {
            throw new Exception("Tipo de empresa invalido");
        }
        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("Nome invalido");
        }
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new Exception("Endereco da empresa invalido");
        }
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws Exception {
        validarParametrosBasicos(tipoEmpresa, nome, endereco);
        userManager.validarSeEDono(dono);
        return empresaManager.criarRestaurante(dono, nome, endereco, tipoCozinha);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws Exception {
        validarParametrosBasicos(tipoEmpresa, nome, endereco);
        userManager.validarSeEDono(dono);
        return empresaManager.criarMercado(dono, nome, endereco, abre, fecha, tipoMercado);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, boolean aberto24Horas, int numeroFuncionarios) throws Exception {
        validarParametrosBasicos(tipoEmpresa, nome, endereco);
        userManager.validarSeEDono(dono);

        return empresaManager.criarFarmacia(dono, nome, endereco, aberto24Horas, numeroFuncionarios);
    }

    public void alterarFuncionamento(int mercado, String abre, String fecha) throws Exception {
        empresaManager.alterarFuncionamento(mercado, abre, fecha);
    }

    public String getEmpresasDoUsuario(int idDono) throws Exception {
        this.userManager.verificarPermissao(idDono);
        return this.empresaManager.getEmpresasDoUsuario(idDono);
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws Exception {
        return this.empresaManager.getIdEmpresa(idDono, nome, indice);
    }

    public String getAtributoEmpresa(int empresa, String atributo) throws Exception {
        String resultado = this.empresaManager.getAtributoEmpresa(empresa, atributo);
        if (atributo.equals("dono")) {
            int idDono = Integer.parseInt(resultado);
            return this.userManager.getAtributoUsuario(idDono, "nome");
        }

        return resultado;
    }

    public int criarProduto(int empresa, String nome, float valor, String categoria) throws Exception {
        return this.produtoManager.criarProduto(empresa, nome, valor, categoria);
    }

    public void editarProduto(int produto, String nome, float valor, String categoria) throws Exception {
        this.produtoManager.editarProduto(produto, nome, valor, categoria);
    }

    public String getProduto(String nome, int empresa, String atributo) throws Exception {
        String resultado = this.produtoManager.getProduto(nome, empresa, atributo);

        if (atributo.equals("empresa")) {
            int idEmpresa = Integer.parseInt(resultado);
            return this.empresaManager.getAtributoEmpresa(idEmpresa, "nome");
        }

        return resultado;
    }

    public String listarProdutos(int empresa) throws Exception {
        try {
            this.empresaManager.getAtributoEmpresa(empresa, "nome");
        } catch (Exception e) {
            if (e.getMessage().equals("Empresa nao cadastrada")) {
                throw new Exception("Empresa nao encontrada");
            }
            throw e;
        }

        return this.produtoManager.listarProdutos(empresa);
    }

    public int criarPedido(int cliente, int empresa) throws Exception {
        // Sem instanceof!
        if (userManager.isDono(cliente)) {
            throw new Exception("Dono de empresa nao pode fazer um pedido");
        }
        return pedidoManager.criarPedido(cliente, empresa);
    }

    public int getNumeroPedido(int cliente, int empresa, int indice) throws Exception {
        return pedidoManager.getNumeroPedido(cliente, empresa, indice);
    }

    public void adicionarProduto(int numero, int produtoId) throws Exception {
        pedidoManager.adicionarProduto(numero, produtoManager.getProdutoInterno(produtoId));
    }

    public String getPedidos(int numero, String atributo) throws Exception {
        if (atributo == null || atributo.trim().isEmpty()) {
            throw new Exception("Atributo invalido");
        }

        if (atributo.equals("cliente")) {
            int idCliente = pedidoManager.getIdClienteDoPedido(numero);
            return userManager.getAtributoUsuario(idCliente, "nome");
        }
        if (atributo.equals("empresa")) {
            int idEmpresa = pedidoManager.getIdEmpresaDoPedido(numero);
            return empresaManager.getAtributoEmpresa(idEmpresa, "nome");
        }

        return pedidoManager.getAtributoPedido(numero, atributo);
    }

    public void fecharPedido(int numero) throws Exception {
        pedidoManager.fecharPedido(numero);
    }

    public void removerProduto(int pedido, String nomeProduto) throws Exception {
        if (nomeProduto == null || nomeProduto.trim().isEmpty()) {
            throw new Exception("Produto invalido");
        }
        pedidoManager.removerProduto(pedido, nomeProduto);
    }

    public void liberarPedido(int numero) throws Exception {
        pedidoManager.liberarPedido(numero);
    }

    public int obterPedido(int entregador) throws Exception {
        userManager.validarSeEEntregador(entregador);

        List<Integer> empresasEntregador = userManager.getEmpresasDoEntregador(entregador);
        if (empresasEntregador.isEmpty()) throw new Exception("Entregador nao estar em nenhuma empresa.");

        int idPedidoFarmacia = -1;
        int idPedidoOutro = -1;

        // A Facade orquestra o cruzamento de dados entre os Managers sem tocar nos Models
        List<Integer> pedidosProntos = pedidoManager.getPedidosProntos();

        for (int idPedido : pedidosProntos) {
            int idEmpresa = pedidoManager.getIdEmpresaDoPedido(idPedido);

            if (empresasEntregador.contains(idEmpresa)) {
                if (empresaManager.isFarmacia(idEmpresa)) {
                    if (idPedidoFarmacia == -1) idPedidoFarmacia = idPedido;
                } else {
                    if (idPedidoOutro == -1) idPedidoOutro = idPedido;
                }
            }
        }

        if (idPedidoFarmacia != -1) return idPedidoFarmacia;
        if (idPedidoOutro != -1) return idPedidoOutro;

        throw new Exception("Nao existe pedido para entrega");
    }

    public int criarEntrega(int pedido, int entregador, String destino) throws Exception {
        if (!pedidoManager.getEstado(pedido).equals("pronto")) {
            throw new Exception("Pedido nao esta pronto para entrega");
        }

        if (!userManager.isEntregadorValido(entregador)) {
            throw new Exception("Nao e um entregador valido");
        }

        if (userManager.isEntregadorEmEntrega(entregador)) {
            throw new Exception("Entregador ainda em entrega");
        }

        int idCliente = pedidoManager.getIdClienteDoPedido(pedido);
        int idEmpresa = pedidoManager.getIdEmpresaDoPedido(pedido);

        String destinoFinal = destino;
        if (destinoFinal == null || destinoFinal.trim().isEmpty()) {
            destinoFinal = userManager.getAtributoUsuario(idCliente, "endereco");
        }

        pedidoManager.alterarEstadoPedido(pedido, "entregando");
        userManager.setEntregadorEmEntrega(entregador, true);

        return entregaManager.criarEntrega(idCliente, idEmpresa, pedido, entregador, destinoFinal);
    }

    public String getEntrega(int id, String atributo) throws Exception {
        if (atributo == null || atributo.trim().isEmpty()) throw new Exception("Atributo invalido");
        if (!entregaManager.existeEntrega(id)) throw new Exception("Nao existe entrega com esse id");

        switch (atributo) {
            case "cliente":
                return userManager.getAtributoUsuario(entregaManager.getIdClienteDaEntrega(id), "nome");
            case "empresa":
                return empresaManager.getAtributoEmpresa(entregaManager.getIdEmpresaDaEntrega(id), "nome");
            case "pedido":
                return String.valueOf(entregaManager.getIdPedidoDaEntrega(id));
            case "entregador":
                return userManager.getAtributoUsuario(entregaManager.getIdEntregadorDaEntrega(id), "nome");
            case "destino":
                return entregaManager.getDestinoDaEntrega(id);
            case "produtos":
                return getPedidos(entregaManager.getIdPedidoDaEntrega(id), "produtos");
            default:
                throw new Exception("Atributo nao existe");
        }
    }

    public int getIdEntrega(int pedido) throws Exception {
        return entregaManager.getIdEntrega(pedido);
    }

    public void entregar(int entrega) throws Exception {
        if (!entregaManager.existeEntrega(entrega)) throw new Exception("Nao existe nada para ser entregue com esse id");

        int idPedido = entregaManager.getIdPedidoDaEntrega(entrega);
        pedidoManager.alterarEstadoPedido(idPedido, "entregue");

        int idEntregador = entregaManager.getIdEntregadorDaEntrega(entrega);
        userManager.setEntregadorEmEntrega(idEntregador, false);
    }
}
