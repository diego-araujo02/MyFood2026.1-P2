package br.ufal.ic.myfood.models;

public class Farmacia extends Empresa {
    private boolean aberto24Horas;
    private int numeroFuncionarios;

    public Farmacia() {}

    public Farmacia(int id, int dono, String nome, String endereco, boolean aberto24Horas, int numeroFuncionarios) throws Exception {
        super(id, dono, nome, endereco);
        this.aberto24Horas = aberto24Horas;
        this.numeroFuncionarios = numeroFuncionarios;
    }

    @Override
    public boolean isFarmacia() { return true; }

    public boolean isAberto24Horas() { return aberto24Horas; }
    public void setAberto24Horas(boolean aberto24Horas) { this.aberto24Horas = aberto24Horas; }

    public int getNumeroFuncionarios() { return numeroFuncionarios; }
    public void setNumeroFuncionarios(int numeroFuncionarios) { this.numeroFuncionarios = numeroFuncionarios; }

    @Override
    public String getAtributo(String atributo) throws Exception {
        switch (atributo) {
            case "aberto24Horas":
                return String.valueOf(this.aberto24Horas);
            case "numeroFuncionarios":
                return String.valueOf(this.numeroFuncionarios);
            default:
                return super.getAtributo(atributo);
        }
    }
}
