package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.models.Empresa;
import br.ufal.ic.myfood.models.Mercado;
import br.ufal.ic.myfood.models.Restaurante;
import br.ufal.ic.myfood.models.Farmacia;
import br.ufal.ic.myfood.exceptions.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EmpresaManager {
    private Map<Integer, Empresa> empresas;
    private int proximoId;

    public EmpresaManager() {
        this.empresas = new LinkedHashMap<>();
        this.proximoId = 1;
    }

    public int criarRestaurante(int dono, String nome, String endereco, String tipoCozinha) throws MyFoodException {
        Restaurante r = new Restaurante(proximoId, dono, nome, endereco, tipoCozinha);
        validarUnicidade(dono, nome, endereco);
        empresas.put(proximoId, r);
        return proximoId++;
    }

    public int criarMercado(int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws MyFoodException {
        Mercado m = new Mercado(proximoId, dono, nome, endereco, abre, fecha, tipoMercado);
        validarUnicidade(dono, nome, endereco);
        empresas.put(proximoId, m);
        return proximoId++;
    }

    public int criarFarmacia(int dono, String nome, String endereco, boolean aberto24Horas, int numeroFuncionarios) throws MyFoodException {
        Farmacia f = new Farmacia(proximoId, dono, nome, endereco, aberto24Horas, numeroFuncionarios);
        validarUnicidade(dono, nome, endereco);
        empresas.put(proximoId, f);
        return proximoId++;
    }

    private void validarUnicidade(int dono, String nome, String endereco) throws MyFoodException {
        for (Empresa e : empresas.values()) {
            if (e.getNome().equals(nome)) {
                if (e.getDono() != dono) {
                    throw new EmpresaJaExisteException("Empresa com esse nome ja existe");
                } else if (e.getEndereco().equals(endereco)) {
                    throw new EmpresaJaExisteException("Proibido cadastrar duas empresas com o mesmo nome e local");
                }
            }
        }
    }

    public void alterarFuncionamento(int mercadoId, String abre, String fecha) throws MyFoodException {
        Empresa e = empresas.get(mercadoId);

        if (e == null) {
            throw new EmpresaNaoEncontradaException("Empresa nao cadastrada");
        }

        e.alterarHorario(abre, fecha);
    }

    public String getAtributoEmpresa(int id, String atributo) throws MyFoodException {
        Empresa e = empresas.get(id);

        if (e == null) throw new EmpresaNaoEncontradaException("Empresa nao cadastrada");
        if (atributo == null || atributo.trim().isEmpty()) throw new AtributoInvalidoException("Atributo invalido");

        if (atributo.equals("dono")) {
            return String.valueOf(e.getDono());
        }

        return e.getAtributo(atributo);
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws MyFoodException {
        if (nome == null || nome.trim().isEmpty()) throw new EmpresaInvalidaException("Nome invalido");
        if (indice < 0) throw new AtributoInvalidoException("Indice invalido");

        List<Integer> encontradas = new ArrayList<>();
        for (Empresa e : empresas.values()) {
            if (e.getDono() == idDono && e.getNome().equals(nome)) {
                encontradas.add(e.getId());
            }
        }

        if (encontradas.isEmpty()) throw new EmpresaNaoEncontradaException("Nao existe empresa com esse nome");
        if (indice >= encontradas.size()) throw new AtributoInvalidoException("Indice maior que o esperado");

        return encontradas.get(indice);
    }

    public String getEmpresasDoUsuario(int idDono) {
        List<String> lista = new ArrayList<>();
        for (Empresa e : empresas.values()) {
            if (e.getDono() == idDono) {
                lista.add("[" + e.getNome() + ", " + e.getEndereco() + "]");
            }
        }
        return "{[" + String.join(", ", lista) + "]}";
    }

    public void adicionarEntregadorNaEmpresa(int empresaId, int entregadorId) {
        getEmpresas().get(empresaId).adicionarEntregador(entregadorId);
    }

    public List<Integer> getEntregadoresDaEmpresa(int empresaId) {
        return new ArrayList<>(empresas.get(empresaId).getEntregadores());
    }

    public boolean isFarmacia(int idEmpresa) {
        return empresas.get(idEmpresa).isFarmacia();
    }

    public void zerarSistema() {
        this.empresas.clear();
        this.proximoId = 1;
    }

    public Map<Integer, Empresa> getEmpresas() { return empresas; }
    public void setEmpresas(Map<Integer, Empresa> empresas) { this.empresas = empresas; }
    public int getProximoId() { return proximoId; }
    public void setProximoId(int proximoId) { this.proximoId = proximoId; }
}