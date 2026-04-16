package br.ufal.ic.myfood.models;

public class Cliente extends Usuario{

    public Cliente(int id, String nome, String email, String senha, String endereco) throws Exception {
        super(id, nome, email, senha, endereco);
    }

    public Cliente() {
        super();
    }
}
