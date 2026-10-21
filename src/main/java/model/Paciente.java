package model;

import model.enums.EstadoPaciente;
import model.enums.GrupoSanguineo;
import model.enums.Sexo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Paciente extends Pessoa {
    private Sexo sexo;
    private GrupoSanguineo grupoSanguineo;   // pode ser null (desconhecido)
    private String nomeAcompanhante;         // pode ser null
    private String papelAcompanhante;        // pode ser null
    private EstadoPaciente estado;
    private int registadoPor;                // id do trabalhador que fez o registo
    private LocalDateTime dataCriacao;       // preenchida pela base de dados

    /** Construtor completo: usado pelo repository ao ler uma linha da base de dados. */
    public Paciente(int id, String nomeCompleto, LocalDate dataNascimento, String numeroBi,
                    String enderecoResidencial, String telefone, Sexo sexo, GrupoSanguineo grupoSanguineo,
                    String nomeAcompanhante, String papelAcompanhante, EstadoPaciente estado,
                    int registadoPor, LocalDateTime dataCriacao) {
        super(id, nomeCompleto, dataNascimento, numeroBi, enderecoResidencial, telefone);
        this.sexo = sexo;
        this.grupoSanguineo = grupoSanguineo;
        this.nomeAcompanhante = nomeAcompanhante;
        this.papelAcompanhante = papelAcompanhante;
        this.estado = estado;
        this.registadoPor = registadoPor;
        this.dataCriacao = dataCriacao;
    }

    /** Construtor para um paciente novo: ainda sem id, VIVO, e sem data de criação (a BD preenche). */
    public Paciente(String nomeCompleto, LocalDate dataNascimento, String numeroBi,
                    String enderecoResidencial, String telefone, Sexo sexo, GrupoSanguineo grupoSanguineo,
                    String nomeAcompanhante, String papelAcompanhante, int registadoPor) {
        this(0, nomeCompleto, dataNascimento, numeroBi, enderecoResidencial, telefone, sexo, grupoSanguineo,
                nomeAcompanhante, papelAcompanhante, EstadoPaciente.VIVO, registadoPor, null);
    }

    public Sexo getSexo() { return sexo; }
    public void setSexo(Sexo sexo) { this.sexo = sexo; }

    public GrupoSanguineo getGrupoSanguineo() { return grupoSanguineo; }
    public void setGrupoSanguineo(GrupoSanguineo grupoSanguineo) { this.grupoSanguineo = grupoSanguineo; }

    public String getNomeAcompanhante() { return nomeAcompanhante; }
    public void setNomeAcompanhante(String nomeAcompanhante) { this.nomeAcompanhante = nomeAcompanhante; }

    public String getPapelAcompanhante() { return papelAcompanhante; }
    public void setPapelAcompanhante(String papelAcompanhante) { this.papelAcompanhante = papelAcompanhante; }

    public EstadoPaciente getEstado() { return estado; }
    public void setEstado(EstadoPaciente estado) { this.estado = estado; }

    public int getRegistadoPor() { return registadoPor; }
    public void setRegistadoPor(int registadoPor) { this.registadoPor = registadoPor; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }

    @Override
    public String toString() {
        return "Paciente{id=" + id +
                ", nomeCompleto='" + nomeCompleto + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", numeroBi='" + numeroBi + '\'' +
                ", enderecoResidencial='" + enderecoResidencial + '\'' +
                ", telefone='" + telefone + '\'' +
                ", sexo=" + sexo +
                ", grupoSanguineo=" + grupoSanguineo +
                ", nomeAcompanhante='" + nomeAcompanhante + '\'' +
                ", papelAcompanhante='" + papelAcompanhante + '\'' +
                ", estado=" + estado +
                ", registadoPor=" + registadoPor +
                '}';
    }
}
