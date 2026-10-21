package model;

import model.enums.Niveis;

import java.time.LocalDate;

public class Medico extends Trabalhador {
    private String especialidade;
    private String numeroOrdem;
    private String numeroRegistoProfissional;

    public Medico(int id, String nomeCompleto, LocalDate dataNascimento, String numeroBi,
                  String enderecoResidencial, String telefone, String nomeUtilizador, String senha,
                  String email, String naturalidade, double salario, double cargaHoraria, boolean activo,
                  String especialidade, String numeroOrdem, String numeroRegistoProfissional) {
        super(id, nomeCompleto, dataNascimento, numeroBi, enderecoResidencial, telefone, nomeUtilizador,
                senha, email, naturalidade, salario, cargaHoraria, Niveis.MEDICO, activo);
        this.especialidade = especialidade;
        this.numeroOrdem = numeroOrdem;
        this.numeroRegistoProfissional = numeroRegistoProfissional;
    }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public String getNumeroOrdem() { return numeroOrdem; }
    public void setNumeroOrdem(String numeroOrdem) { this.numeroOrdem = numeroOrdem; }

    public String getNumeroRegistoProfissional() { return numeroRegistoProfissional; }
    public void setNumeroRegistoProfissional(String numeroRegistoProfissional) { this.numeroRegistoProfissional = numeroRegistoProfissional; }
}
