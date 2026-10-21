package model;

import model.enums.Niveis;

import java.time.LocalDate;

public class Recepcionista extends Trabalhador {
    private String balcao;

    public Recepcionista(int id, String nomeCompleto, LocalDate dataNascimento, String numeroBi,
                         String enderecoResidencial, String telefone, String nomeUtilizador, String senha,
                         String email, String naturalidade, double salario, double cargaHoraria, boolean activo,
                         String balcao) {
        super(id, nomeCompleto, dataNascimento, numeroBi, enderecoResidencial, telefone, nomeUtilizador,
                senha, email, naturalidade, salario, cargaHoraria, Niveis.RECEPCIONISTA, activo);
        this.balcao = balcao;
    }

    public String getBalcao() { return balcao; }
    public void setBalcao(String balcao) { this.balcao = balcao; }
}
