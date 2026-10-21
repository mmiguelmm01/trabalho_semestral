package model;

import model.enums.NiveisRisco;

import java.time.LocalDateTime;

public class Triagem {
    private int id;
    private Paciente paciente;
    private Enfermeiro enfermeiro;
    private NiveisRisco nivelRisco;
    private String queixaPrincipal;
    private Double temperatura;         // Double (e não double): pode ser null na base de dados
    private String pressaoArterial;     // pode ser null
    private Integer frequenciaCardiaca; // pode ser null
    private LocalDateTime dataHora;     // coluna data_criacao; a BD preenche

    /** Construtor completo (leitura da BD). Para uma triagem nova usa id = 0 e dataHora = null. */
    public Triagem(int id, Paciente paciente, Enfermeiro enfermeiro, NiveisRisco nivelRisco,
                   String queixaPrincipal, Double temperatura, String pressaoArterial,
                   Integer frequenciaCardiaca, LocalDateTime dataHora) {
        this.id = id;
        this.paciente = paciente;
        this.enfermeiro = enfermeiro;
        this.nivelRisco = nivelRisco;
        this.queixaPrincipal = queixaPrincipal;
        this.temperatura = temperatura;
        this.pressaoArterial = pressaoArterial;
        this.frequenciaCardiaca = frequenciaCardiaca;
        this.dataHora = dataHora;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Enfermeiro getEnfermeiro() { return enfermeiro; }
    public void setEnfermeiro(Enfermeiro enfermeiro) { this.enfermeiro = enfermeiro; }

    public NiveisRisco getNivelRisco() { return nivelRisco; }
    public void setNivelRisco(NiveisRisco nivelRisco) { this.nivelRisco = nivelRisco; }

    public String getQueixaPrincipal() { return queixaPrincipal; }
    public void setQueixaPrincipal(String queixaPrincipal) { this.queixaPrincipal = queixaPrincipal; }

    public Double getTemperatura() { return temperatura; }
    public void setTemperatura(Double temperatura) { this.temperatura = temperatura; }

    public String getPressaoArterial() { return pressaoArterial; }
    public void setPressaoArterial(String pressaoArterial) { this.pressaoArterial = pressaoArterial; }

    public Integer getFrequenciaCardiaca() { return frequenciaCardiaca; }
    public void setFrequenciaCardiaca(Integer frequenciaCardiaca) { this.frequenciaCardiaca = frequenciaCardiaca; }

    public LocalDateTime getDataHora() { return dataHora; }

    @Override
    public String toString() {
        return "Triagem{id=" + id +
                ", paciente=" + (paciente == null ? null : paciente.getNomeCompleto()) +
                ", enfermeiro=" + (enfermeiro == null ? null : enfermeiro.getNomeCompleto()) +
                ", nivelRisco=" + nivelRisco +
                ", queixaPrincipal='" + queixaPrincipal + '\'' +
                ", temperatura=" + temperatura +
                ", pressaoArterial='" + pressaoArterial + '\'' +
                ", frequenciaCardiaca=" + frequenciaCardiaca +
                ", dataHora=" + dataHora + '}';
    }
}
