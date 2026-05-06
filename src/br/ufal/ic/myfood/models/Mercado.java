package br.ufal.ic.myfood.models;

public class Mercado extends Empresa {
    private String abre;
    private String fecha;
    private String tipoMercado;

    public Mercado() {}

    public Mercado(int id, int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws Exception {
        super(id, dono, nome, endereco);

        if (abre == null || fecha == null) {
            throw new Exception("Horario invalido");
        }

        if (!abre.matches("\\d{2}:\\d{2}") || !fecha.matches("\\d{2}:\\d{2}")) {
            throw new Exception("Formato de hora invalido");
        }

        String[] a = abre.split(":");
        String[] f = fecha.split(":");
        int hA = Integer.parseInt(a[0]);
        int mA = Integer.parseInt(a[1]);
        int hF = Integer.parseInt(f[0]);
        int mF = Integer.parseInt(f[1]);

        if (hA > 23 || mA > 59 || hF > 23 || mF > 59) {
            throw new Exception("Horario invalido");
        }

        if ((hA * 60 + mA) >= (hF * 60 + mF)) {
            throw new Exception("Horario invalido");
        }

        if (tipoMercado == null || tipoMercado.trim().isEmpty()) {
            throw new Exception("Tipo de mercado invalido");
        }

        this.abre = abre;
        this.fecha = fecha;
        this.tipoMercado = tipoMercado;
    }

    public String getAbre() { return abre; }
    public void setAbre(String abre) { this.abre = abre; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getTipoMercado() { return tipoMercado; }
    public void setTipoMercado(String tipoMercado) { this.tipoMercado = tipoMercado; }

    @Override
    public void alterarHorario(String abre, String fecha) throws Exception {
        if (abre == null || fecha == null) throw new Exception("Horario invalido");
        if (!abre.matches("\\d{2}:\\d{2}") || !fecha.matches("\\d{2}:\\d{2}")) throw new Exception("Formato de hora invalido");

        String[] a = abre.split(":");
        String[] f = fecha.split(":");
        int hA = Integer.parseInt(a[0]), mA = Integer.parseInt(a[1]);
        int hF = Integer.parseInt(f[0]), mF = Integer.parseInt(f[1]);

        if (hA > 23 || mA > 59 || hF > 23 || mF > 59) throw new Exception("Horario invalido");
        if ((hA * 60 + mA) >= (hF * 60 + mF)) throw new Exception("Horario invalido");

        this.abre = abre;
        this.fecha = fecha;
    }

    @Override
    public String getAtributo(String atributo) throws Exception {
        switch (atributo) {
            case "abre": return this.abre;
            case "fecha": return this.fecha;
            case "tipoMercado": return this.tipoMercado;
            default: return super.getAtributo(atributo);
        }
    }
}