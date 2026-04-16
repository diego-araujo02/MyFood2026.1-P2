package br.ufal.ic.myfood.models;

public class Dono extends Usuario{
    private String cpf;

    public Dono(int id, String nome, String email, String senha, String endereco, String cpf) throws Exception {
        super(id, nome, email, senha, endereco);

        if (cpf == null || cpf.trim().isEmpty() || cpf.length() != 14) {
            throw new Exception("CPF invalido");
        }

        this.cpf = cpf;
    }

    public Dono() {
        super();
    }

    @Override
    public String getAtributo(String atributo) throws Exception{
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
                throw new Exception("Atributo invalido");
        }
    }

    @Override
    public void verificarPermissaoEmpresa() throws Exception {
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
