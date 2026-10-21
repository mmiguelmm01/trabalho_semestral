package model;

import java.time.LocalDateTime;

public class Obito {
    private int id;                 // 0 = ainda não gravado
    private Paciente paciente;
    private Consulta consulta;      // pode ser null
    private Medico medico;
    private LocalDateTime dataHora; // coluna data_obito
    private String causa;
    private String observacoes;     // pode ser null

    public Obito(int id, Paciente paciente, Consulta consulta, Medico medico,
                 LocalDateTime dataHora, String causa, String observacoes) {
        this.id = id;
        this.paciente = paciente;
        this.consulta = consulta;
        this.medico = medico;
        this.dataHora = dataHora;
        this.causa = causa;
        this.observacoes = observacoes;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Consulta getConsulta() { return consulta; }
    public void setConsulta(Consulta consulta) { this.consulta = consulta; }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public String getCausa() { return causa; }
    public void setCausa(String causa) { this.causa = causa; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    @Override
    public String toString() {
        return "Obito{id=" + id +
                ", paciente=" + (paciente == null ? null : paciente.getNomeCompleto()) +
                ", medico=" + (medico == null ? null : medico.getNomeCompleto()) +
                ", dataHora=" + dataHora +
                ", causa='" + causa + '\'' +
                ", observacoes='" + observacoes + '\'' + '}';
    }
}
