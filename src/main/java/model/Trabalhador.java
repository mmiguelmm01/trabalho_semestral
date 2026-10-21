package model;

import model.enums.Niveis;

import java.time.LocalDate;

/**
 * Base de Administrador, Medico, Enfermeiro e Recepcionista (todos na tabela "trabalhador").
 * O nível é fixado pela própria subclasse, por isso já não é parâmetro dos construtores.
 */
public abstract class Trabalhador extends Pessoa {
    protected String nomeUtilizador;
    protected String senha;          // aqui vai o HASH da senha (coluna senha_hash), nunca a senha em claro
    protected String email;          // pode ser null
    protected String naturalidade;   // pode ser null
    protected double salario;
    protected double cargaHoraria;   // em horas, ex: 40.0
    protected Niveis niveis;
    protected boolean activo;

    public Trabalhador(int id, String nomeCompleto, LocalDate dataNascimento, String numeroBi,
                       String enderecoResidencial, String telefone, String nomeUtilizador, String senha,
                       String email, String naturalidade, double salario, double cargaHoraria,
                       Niveis niveis, boolean activo) {
        super(id, nomeCompleto, dataNascimento, numeroBi, enderecoResidencial, telefone);
        this.nomeUtilizador = nomeUtilizador;
        this.senha = senha;
        this.email = email;
        this.naturalidade = naturalidade;
        this.salario = salario;
        this.cargaHoraria = cargaHoraria;
        this.niveis = niveis;
        this.activo = activo;
    }

    public String getNomeUtilizador() { return nomeUtilizador; }
    public void setNomeUtilizador(String nomeUtilizador) { this.nomeUtilizador = nomeUtilizador; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNaturalidade() { return naturalidade; }
    public void setNaturalidade(String naturalidade) { this.naturalidade = naturalidade; }

    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }

    public double getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(double cargaHoraria) { this.cargaHoraria = cargaHoraria; }

    public Niveis getNiveis() { return niveis; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    @Override
    public String toString() {
        // a senha fica de fora de propósito: nunca deve aparecer em logs
        return getClass().getSimpleName() + "{id=" + id +
                ", nomeCompleto='" + nomeCompleto + '\'' +
                ", nomeUtilizador='" + nomeUtilizador + '\'' +
                ", niveis=" + niveis +
                ", activo=" + activo + '}';
    }
}
