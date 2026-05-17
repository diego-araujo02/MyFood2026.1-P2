package br.ufal.ic.myfood;

import br.ufal.ic.myfood.services.*;
import br.ufal.ic.myfood.exceptions.*;
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

    public String getAtributoUsuario(int id, String atributo) throws MyFoodException {
        return this.userManager.getAtributoUsuario(id, atributo);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws MyFoodException {
        this.userManager.criarUsuario(nome, email, senha, endereco);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws MyFoodException {
        this.userManager.criarUsuario(nome, email, senha, endereco, cpf);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa) throws MyFoodException {
        userManager.criarEntregador(nome, email, senha, endereco, veiculo, placa);
    }

    public void cadastrarEntregador(int empresa, int entregador) throws MyFoodException {
        userManager.validarSeEEntregador(entregador);
        empresaManager.adicionarEntregadorNaEmpresa(empresa, entregador);
        userManager.adicionarEmpresaAoEntregador(entregador, empresa);
    }

    public String getEntregadores(int empresa) throws MyFoodException {
        List<Integer> ids = empresaManager.getEntregadoresDaEmpresa(empresa);
        List<String> emails = new ArrayList<>();

        for (int id : ids) {
            emails.add(userManager.getAtributoUsuario(id, "email"));
        }
        return "{[" + String.join(", ", emails) + "]}";
    }

    public String getEmpresas(int entregador) throws MyFoodException {
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

    public int login(String email, String senha) throws MyFoodException {
        return this.userManager.login(email, senha);
    }

    public void encerrarSistema() {
        PersistenciaXML.salvar(this.userManager, "data/usuarios.xml");
        PersistenciaXML.salvar(this.empresaManager, "data/empresas.xml");
        PersistenciaXML.salvar(this.produtoManager, "data/produtos.xml");
        PersistenciaXML.salvar(this.pedidoManager, "data/pedidos.xml");
        PersistenciaXML.salvar(this.entregaManager, "data/entregas.xml");
    }

    private void validarParametrosBasicos(String tipoEmpresa, String nome, String endereco) throws MyFoodException {
        if (tipoEmpresa == null || tipoEmpresa.trim().isEmpty()) {
            throw new EmpresaInvalidaException("Tipo de empresa invalido");
        }
        if (nome == null || nome.trim().isEmpty()) {
            throw new EmpresaInvalidaException("Nome invalido");
        }
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new EmpresaInvalidaException("Endereco da empresa invalido");
        }
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws MyFoodException {
        validarParametrosBasicos(tipoEmpresa, nome, endereco);
        userManager.validarSeEDono(dono);
        return empresaManager.criarRestaurante(dono, nome, endereco, tipoCozinha);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws MyFoodException {
        validarParametrosBasicos(tipoEmpresa, nome, endereco);
        userManager.validarSeEDono(dono);
        return empresaManager.criarMercado(dono, nome, endereco, abre, fecha, tipoMercado);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, boolean aberto24Horas, int numeroFuncionarios) throws MyFoodException {
        validarParametrosBasicos(tipoEmpresa, nome, endereco);
        userManager.validarSeEDono(dono);

        return empresaManager.criarFarmacia(dono, nome, endereco, aberto24Horas, numeroFuncionarios);
    }

    public void alterarFuncionamento(int mercado, String abre, String fecha) throws MyFoodException {
        empresaManager.alterarFuncionamento(mercado, abre, fecha);
    }

    public String getEmpresasDoUsuario(int idDono) throws MyFoodException {
        this.userManager.verificarPermissao(idDono);
        return this.empresaManager.getEmpresasDoUsuario(idDono);
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws MyFoodException {
        return this.empresaManager.getIdEmpresa(idDono, nome, indice);
    }

    public String getAtributoEmpresa(int empresa, String atributo) throws MyFoodException {
        String resultado = this.empresaManager.getAtributoEmpresa(empresa, atributo);
        if (atributo.equals("dono")) {
            int idDono = Integer.parseInt(resultado);
            return this.userManager.getAtributoUsuario(idDono, "nome");
        }

        return resultado;
    }

    public int criarProduto(int empresa, String nome, float valor, String categoria) throws MyFoodException {
        return this.produtoManager.criarProduto(empresa, nome, valor, categoria);
    }

    public void editarProduto(int produto, String nome, float valor, String categoria) throws MyFoodException {
        this.produtoManager.editarProduto(produto, nome, valor, categoria);
    }

    public String getProduto(String nome, int empresa, String atributo) throws MyFoodException {
        String resultado = this.produtoManager.getProduto(nome, empresa, atributo);

        if (atributo.equals("empresa")) {
            int idEmpresa = Integer.parseInt(resultado);
            return this.empresaManager.getAtributoEmpresa(idEmpresa, "nome");
        }

        return resultado;
    }

    public String listarProdutos(int empresa) throws MyFoodException {
        try {
            this.empresaManager.getAtributoEmpresa(empresa, "nome");
        } catch (MyFoodException e) {
            if (e.getMessage().equals("Empresa nao cadastrada")) {
                throw new EmpresaNaoEncontradaException("Empresa nao encontrada");
            }
            throw e;
        }

        return this.produtoManager.listarProdutos(empresa);
    }

    public int criarPedido(int cliente, int empresa) throws MyFoodException {
        if (userManager.isDono(cliente)) {
            throw new PermissaoNegadaException("Dono de empresa nao pode fazer um pedido");
        }
        return pedidoManager.criarPedido(cliente, empresa);
    }

    public int getNumeroPedido(int cliente, int empresa, int indice) throws MyFoodException {
        return pedidoManager.getNumeroPedido(cliente, empresa, indice);
    }

    public void adicionarProduto(int numero, int produtoId) throws MyFoodException {
        pedidoManager.adicionarProduto(numero, produtoManager.getProdutoInterno(produtoId));
    }

    public String getPedidos(int numero, String atributo) throws MyFoodException {
        if (atributo == null || atributo.trim().isEmpty()) {
            throw new AtributoInvalidoException("Atributo invalido");
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

    public void fecharPedido(int numero) throws MyFoodException {
        pedidoManager.fecharPedido(numero);
    }

    public void removerProduto(int pedido, String nomeProduto) throws MyFoodException {
        if (nomeProduto == null || nomeProduto.trim().isEmpty()) {
            throw new ProdutoInvalidoException("Produto invalido");
        }
        pedidoManager.removerProduto(pedido, nomeProduto);
    }

    public void liberarPedido(int numero) throws MyFoodException {
        pedidoManager.liberarPedido(numero);
    }

    public int obterPedido(int entregador) throws MyFoodException {
        userManager.validarSeEEntregador(entregador);

        List<Integer> empresasEntregador = userManager.getEmpresasDoEntregador(entregador);
        if (empresasEntregador.isEmpty()) throw new EntregaInvalidaException("Entregador nao estar em nenhuma empresa.");

        int idPedidoFarmacia = -1;
        int idPedidoOutro = -1;

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

        throw new EstadoPedidoInvalidoException("Nao existe pedido para entrega");
    }

    public int criarEntrega(int pedido, int entregador, String destino) throws MyFoodException {
        if (!pedidoManager.getEstado(pedido).equals("pronto")) {
            throw new EstadoPedidoInvalidoException("Pedido nao esta pronto para entrega");
        }

        if (!userManager.isEntregadorValido(entregador)) {
            throw new PermissaoNegadaException("Nao e um entregador valido");
        }

        if (userManager.isEntregadorEmEntrega(entregador)) {
            throw new EntregaInvalidaException("Entregador ainda em entrega");
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

    public String getEntrega(int id, String atributo) throws MyFoodException {
        if (atributo == null || atributo.trim().isEmpty()) throw new AtributoInvalidoException("Atributo invalido");
        if (!entregaManager.existeEntrega(id)) throw new EntregaNaoEncontradaException("Nao existe entrega com esse id");

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
                throw new AtributoInvalidoException("Atributo nao existe");
        }
    }

    public int getIdEntrega(int pedido) throws MyFoodException {
        return entregaManager.getIdEntrega(pedido);
    }

    public void entregar(int entrega) throws MyFoodException {
        if (!entregaManager.existeEntrega(entrega)) throw new EntregaNaoEncontradaException("Nao existe nada para ser entregue com esse id");

        int idPedido = entregaManager.getIdPedidoDaEntrega(entrega);
        pedidoManager.alterarEstadoPedido(idPedido, "entregue");

        int idEntregador = entregaManager.getIdEntregadorDaEntrega(entrega);
        userManager.setEntregadorEmEntrega(idEntregador, false);
    }
}