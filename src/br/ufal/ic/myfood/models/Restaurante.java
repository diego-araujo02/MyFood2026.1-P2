package br.ufal.ic.myfood.models;

public class Restaurante extends Empresa {
    private String tipoCozinha;

    public Restaurante() {}

    public Restaurante(int id, int dono, String nome, String endereco, String tipoCozinha) throws Exception {
        super(id, dono, nome, endereco);
        this.tipoCozinha = tipoCozinha;
    }

    public String getTipoCozinha() { return tipoCozinha; }
    public void setTipoCozinha(String tipoCozinha) { this.tipoCozinha = tipoCozinha; }

    @Override
    public String getAtributo(String atributo) throws Exception {
        if (atributo.equals("tipoCozinha")) {
            return this.tipoCozinha;
        }
        return super.getAtributo(atributo);
    }
}
