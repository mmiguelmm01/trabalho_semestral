package model;

import model.enums.Niveis;

import java.time.LocalDate;

public class Administrador extends Trabalhador {
    // ainda sem atributos próprios

    public Administrador(int id, String nomeCompleto, LocalDate dataNascimento, String numeroBi,
                         String enderecoResidencial, String telefone, String nomeUtilizador, String senha,
                         String email, String naturalidade, double salario, double cargaHoraria, boolean activo) {
        super(id, nomeCompleto, dataNascimento, numeroBi, enderecoResidencial, telefone, nomeUtilizador,
                senha, email, naturalidade, salario, cargaHoraria, Niveis.ADMINISTRADOR, activo);
    }
}
