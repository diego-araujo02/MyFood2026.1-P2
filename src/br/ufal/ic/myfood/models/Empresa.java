package br.ufal.ic.myfood.models;

public class Empresa {
    private int id;
    private int dono;
    private String nome;
    private String endereco;
    private String tipoCozinha;


    public Empresa(){}

    public Empresa(int id, int dono, String nome, String endereco, String tipoCozinha) throws Exception{
        if (nome == null || nome.trim().isEmpty()){
            throw new Exception("Nome Invalido");
        }
        this.id = id;
        this.dono = dono;
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDono() { return dono; }
    public void setDono(int dono) { this.dono = dono; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTipoCozinha() { return tipoCozinha; }
    public void setTipoCozinha(String tipoCozinha) { this.tipoCozinha = tipoCozinha; }

    public String getAtributo(String atributo) throws Exception {
        switch (atributo) {
            case "nome":
                return this.nome;
            case "endereco":
                return this.endereco;
            case "tipoCozinha":
                return this.tipoCozinha;
            default:
                throw new Exception("Atributo invalido");
        }
    }
}
