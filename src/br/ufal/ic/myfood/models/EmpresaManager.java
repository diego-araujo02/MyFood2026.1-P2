package br.ufal.ic.myfood.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EmpresaManager {

    // Usamos LinkedHashMap para garantir a ordem cronológica de criação
    private Map<Integer, Empresa> empresas;
    private int proximoId;

    public EmpresaManager() {
        this.empresas = new LinkedHashMap<>();
        this.proximoId = 1;
    }

    public Map<Integer, Empresa> getEmpresas() { return empresas; }
    public void setEmpresas(Map<Integer, Empresa> empresas) { this.empresas = empresas; }
    public int getProximoId() { return proximoId; }
    public void setProximoId(int proximoId) { this.proximoId = proximoId; }


    public int criarEmpresa(int idDono, String nome, String endereco, String tipoCozinha) throws Exception {
        for (Empresa e : this.empresas.values()) {
            if (e.getNome().equals(nome)) {
                if (e.getDono() != idDono) {
                    throw new Exception("Empresa com esse nome ja existe");
                } else if (e.getEndereco().equals(endereco)) {
                    throw new Exception("Proibido cadastrar duas empresas com o mesmo nome e local");
                }
            }
        }

        Empresa novaEmpresa = new Empresa(proximoId, idDono, nome, endereco, tipoCozinha);
        this.empresas.put(proximoId, novaEmpresa);
        int idGerado = proximoId;
        proximoId++;

        return idGerado;
    }

    public String getEmpresasDoUsuario(int idDono) {
        StringBuilder sb = new StringBuilder();
        sb.append("{[");

        boolean primeiro = true;
        for (Empresa e : this.empresas.values()) {
            if (e.getDono() == idDono) {
                if (!primeiro) {
                    sb.append(", ");
                }
                sb.append("[").append(e.getNome()).append(", ").append(e.getEndereco()).append("]");
                primeiro = false;
            }
        }

        sb.append("]}");
        return sb.toString();
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws Exception {
        if (nome == null || nome.trim().isEmpty()) {
            throw new Exception("Nome invalido");
        }
        if (indice < 0) {
            throw new Exception("Indice invalido");
        }

        List<Empresa> matches = new ArrayList<>();
        for (Empresa e : this.empresas.values()) {
            if (e.getDono() == idDono && e.getNome().equals(nome)) {
                matches.add(e);
            }
        }

        if (matches.isEmpty()) {
            throw new Exception("Nao existe empresa com esse nome");
        }
        if (indice >= matches.size()) {
            throw new Exception("Indice maior que o esperado");
        }

        return matches.get(indice).getId();
    }

    public String getAtributoEmpresa(int idEmpresa, String atributo) throws Exception {
        Empresa e = this.empresas.get(idEmpresa);
        if (e == null) {
            throw new Exception("Empresa nao cadastrada");
        }

        if (atributo == null || atributo.trim().isEmpty()) {
            throw new Exception("Atributo invalido");
        }

        if (atributo.equals("dono")) {
            return String.valueOf(e.getDono());
        }

        return e.getAtributo(atributo);
    }

    public void zerarSistema() {
        this.empresas.clear();
        this.proximoId = 1;
    }
}