package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.*;

public class Dono extends Usuario{
    private String cpf;

    public Dono(int id, String nome, String email, String senha, String endereco, String cpf) throws MyFoodException {
        super(id, nome, email, senha, endereco);

        if (cpf == null || cpf.trim().isEmpty() || cpf.length() != 14) {
            throw new UsuarioInvalidoException("CPF invalido");
        }

        this.cpf = cpf;
    }

    public Dono() {
        super();
    }

    @Override
    public String getAtributo(String atributo) throws MyFoodException{
        switch (atributo){
            case "nome":
                return super.getAtributo(atributo);
            case "email":
                return super.getAtributo(atributo);
            case "senha":
                return super.getAtributo(atributo);
            case "endereco":
                return super.getAtributo(atributo);
            case "cpf":
                return this.cpf;
            default:
                throw new AtributoInvalidoException("Atributo invalido");
        }
    }

    @Override
    public void verificarPermissaoEmpresa() throws MyFoodException {
    }

    @Override
    public boolean isDono() {
        return true;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
