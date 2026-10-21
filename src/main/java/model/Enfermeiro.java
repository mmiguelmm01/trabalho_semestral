package model;

import model.enums.Niveis;

import java.time.LocalDate;

public class Enfermeiro extends Trabalhador {
    private String sector;
    private String numeroRegistoProfissional;   // antes "nomeRegistoProfissional" (parecia gralha)
    private String categoria;

    public Enfermeiro(int id, String nomeCompleto, LocalDate dataNascimento, String numeroBi,
                      String enderecoResidencial, String telefone, String nomeUtilizador, String senha,
                      String email, String naturalidade, double salario, double cargaHoraria, boolean activo,
                      String sector, String numeroRegistoProfissional, String categoria) {
        super(id, nomeCompleto, dataNascimento, numeroBi, enderecoResidencial, telefone, nomeUtilizador,
                senha, email, naturalidade, salario, cargaHoraria, Niveis.ENFERMEIRO, activo);
        this.sector = sector;
        this.numeroRegistoProfissional = numeroRegistoProfissional;
        this.categoria = categoria;
    }

    public String getSector() { return sector; }
    public void setSector(String sector) { this.sector = sector; }

    public String getNumeroRegistoProfissional() { return numeroRegistoProfissional; }
    public void setNumeroRegistoProfissional(String numeroRegistoProfissional) { this.numeroRegistoProfissional = numeroRegistoProfissional; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}
