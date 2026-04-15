package br.ufal.ic.myfood.models;

public class Customer extends Usuario{

    public Customer(int id, String nome, String email, String senha, String endereco) throws Exception {
        super(id, nome, email, senha, endereco);
    }

    public Customer() {}
}
