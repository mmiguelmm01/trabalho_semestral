package model;

import java.time.LocalDate;
import java.time.Period;

/**
 * Superclasse de Paciente e Trabalhador. Fica aqui o que as duas tabelas partilham,
 * incluindo o id (0 = ainda não gravado na base de dados).
 */
public abstract class Pessoa {
    protected int id;
    protected String nomeCompleto;
    protected LocalDate dataNascimento;
    protected String numeroBi;
    protected String enderecoResidencial;
    protected String telefone;

    public Pessoa(int id, String nomeCompleto, LocalDate dataNascimento, String numeroBi,
                  String enderecoResidencial, String telefone) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.numeroBi = numeroBi;
        this.enderecoResidencial = enderecoResidencial;
        this.telefone = telefone;
    }

    public Period calcularIdade() {
        return Period.between(dataNascimento, LocalDate.now());
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getNumeroBi() { return numeroBi; }
    public void setNumeroBi(String numeroBi) { this.numeroBi = numeroBi; }

    public String getEnderecoResidencial() { return enderecoResidencial; }
    public void setEnderecoResidencial(String enderecoResidencial) { this.enderecoResidencial = enderecoResidencial; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    @Override
    public String toString() {
        return "Pessoa{id=" + id +
                ", nomeCompleto='" + nomeCompleto + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", numeroBi='" + numeroBi + '\'' +
                ", enderecoResidencial='" + enderecoResidencial + '\'' +
                ", telefone='" + telefone + '\'' +
                '}';
    }
}
