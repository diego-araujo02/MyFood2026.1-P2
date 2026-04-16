package br.ufal.ic.myfood;

import br.ufal.ic.myfood.exceptions.UsuarioJaExisteException;
import br.ufal.ic.myfood.exceptions.UsuarioNaoExisteException;
import br.ufal.ic.myfood.models.EmpresaManager;
import br.ufal.ic.myfood.models.ProdutoManager;
import br.ufal.ic.myfood.models.Usuario;
import br.ufal.ic.myfood.models.UsuarioManager;
import br.ufal.ic.myfood.utils.PersistenciaXML;

import java.util.ArrayList;
import java.util.List;

public class Facade {

    UsuarioManager userManager;
    EmpresaManager empresaManager;
    private ProdutoManager produtoManager;

    public Facade() {
        UsuarioManager uManagerCarregado = (UsuarioManager) PersistenciaXML.carregar("usuarios.xml");
        this.userManager = (uManagerCarregado != null) ? uManagerCarregado : new UsuarioManager();

        EmpresaManager eManagerCarregado = (EmpresaManager) PersistenciaXML.carregar("empresas.xml");
        this.empresaManager = (eManagerCarregado != null) ? eManagerCarregado : new EmpresaManager();

        ProdutoManager pManagerCarregado = (ProdutoManager) PersistenciaXML.carregar("produtos.xml");
        this.produtoManager = (pManagerCarregado != null) ? pManagerCarregado : new ProdutoManager();
    }


    public void zerarSistema() {
        this.userManager.zerarSistema();
        this.empresaManager.zerarSistema();
        this.produtoManager.zerarSistema();

        PersistenciaXML.salvar(this.userManager, "usuarios.xml");
        PersistenciaXML.salvar(this.empresaManager, "empresas.xml");
        PersistenciaXML.salvar(this.produtoManager, "produtos.xml");
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

    public int login(String email, String senha) throws Exception {
        return this.userManager.login(email, senha);
    }

    public void encerrarSistema() {
        PersistenciaXML.salvar(this.userManager, "usuarios.xml");
        PersistenciaXML.salvar(this.empresaManager, "empresas.xml");
        PersistenciaXML.salvar(this.produtoManager, "produtos.xml");
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws Exception {
        this.userManager.verificarPermissao(dono);

        return this.empresaManager.criarEmpresa(dono, nome, endereco, tipoCozinha);
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
}
