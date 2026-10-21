package model;

import model.enums.EstadoConsulta;

import java.time.LocalDateTime;

public class Consulta {
    private int id;                     // 0 = ainda não gravada
    private Paciente paciente;
    private Medico medico;
    private Triagem triagem;            // pode ser null (consulta sem triagem prévia)
    private boolean urgencia;
    private EstadoConsulta estado;
    private String diagnostico;         // pode ser null enquanto a consulta não termina
    private String tratamento;          // pode ser null
    private LocalDateTime dataHoraInicio;   // pode ser null
    private LocalDateTime dataHoraFim;      // pode ser null

    public Consulta(int id, Paciente paciente, Medico medico, Triagem triagem, boolean urgencia,
                    EstadoConsulta estado, String diagnostico, String tratamento,
                    LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim) {
        this.id = id;
        this.paciente = paciente;
        this.medico = medico;
        this.triagem = triagem;
        this.urgencia = urgencia;
        this.estado = estado;
        this.diagnostico = diagnostico;
        this.tratamento = tratamento;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }

    public Triagem getTriagem() { return triagem; }
    public void setTriagem(Triagem triagem) { this.triagem = triagem; }

    public boolean isUrgencia() { return urgencia; }
    public void setUrgencia(boolean urgencia) { this.urgencia = urgencia; }

    public EstadoConsulta getEstado() { return estado; }
    public void setEstado(EstadoConsulta estado) { this.estado = estado; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getTratamento() { return tratamento; }
    public void setTratamento(String tratamento) { this.tratamento = tratamento; }

    public LocalDateTime getDataHoraInicio() { return dataHoraInicio; }
    public void setDataHoraInicio(LocalDateTime dataHoraInicio) { this.dataHoraInicio = dataHoraInicio; }

    public LocalDateTime getDataHoraFim() { return dataHoraFim; }
    public void setDataHoraFim(LocalDateTime dataHoraFim) { this.dataHoraFim = dataHoraFim; }

    @Override
    public String toString() {
        return "Consulta{id=" + id +
                ", paciente=" + (paciente == null ? null : paciente.getNomeCompleto()) +
                ", medico=" + (medico == null ? null : medico.getNomeCompleto()) +
                ", triagemId=" + (triagem == null ? null : triagem.getId()) +
                ", urgencia=" + urgencia +
                ", estado=" + estado +
                ", diagnostico='" + diagnostico + '\'' +
                ", tratamento='" + tratamento + '\'' +
                ", dataHoraInicio=" + dataHoraInicio +
                ", dataHoraFim=" + dataHoraFim + '}';
    }
}
