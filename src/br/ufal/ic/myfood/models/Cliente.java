package br.ufal.ic.myfood.models;

import br.ufal.ic.myfood.exceptions.*;

public class Cliente extends Usuario{

    public Cliente(int id, String nome, String email, String senha, String endereco) throws MyFoodException {
        super(id, nome, email, senha, endereco);
    }

    public Cliente() {
        super();
    }
}
