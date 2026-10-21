package repository;

import model.Consulta;
import model.Medico;
import model.Paciente;
import model.Triagem;
import model.enums.EstadoConsulta;
import util.ConexaoDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * RN08: uma consulta concluída não se elimina, por isso este repository não tem eliminar().
 * Para cancelar usa-se o estado CANCELADA.
 */
public class ConsultaRepository {

    private final PacienteRepository pacienteRepo = new PacienteRepository();
    private final TrabalhadorRepository trabalhadorRepo = new TrabalhadorRepository();
    private final TriagemRepository triagemRepo = new TriagemRepository();

    private static final String COLUNAS =
            "id, paciente_id, medico_id, triagem_id, urgencia, estado, diagnostico, tratamento, " +
            "data_hora_inicio, data_hora_fim";

    public Consulta inserir(Consulta c) throws SQLException {
        String sql = "INSERT INTO consulta (paciente_id, medico_id, triagem_id, urgencia, estado, " +
                "diagnostico, tratamento, data_hora_inicio, data_hora_fim) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoDB.obter();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, c.getPaciente().getId());
            ps.setInt(2, c.getMedico().getId());
            if (c.getTriagem() == null) ps.setNull(3, Types.INTEGER); else ps.setInt(3, c.getTriagem().getId());
            ps.setBoolean(4, c.isUrgencia());
            ps.setString(5, c.getEstado().name());
            ps.setString(6, c.getDiagnostico());
            ps.setString(7, c.getTratamento());
            ps.setTimestamp(8, paraTimestamp(c.getDataHoraInicio()));
            ps.setTimestamp(9, paraTimestamp(c.getDataHoraFim()));

            ps.executeUpdate();
            try (ResultSet chaves = ps.getGeneratedKeys()) {
                if (chaves.next()) {
                    c.setId(chaves.getInt(1));
                }
            }
            return c;
        }
    }

    public Optional<Consulta> buscarPorId(int id) throws SQLException {
        String sql = "SELECT " + COLUNAS + " FROM consulta WHERE id = ?";
        try (Connection conn = ConexaoDB.obter();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        }
    }

    /** RF10/RF11: histórico de consultas do paciente, da mais recente para a mais antiga. */
    public List<Consulta> listarPorPaciente(int pacienteId) throws SQLException {
        String sql = "SELECT " + COLUNAS + " FROM consulta WHERE paciente_id = ? ORDER BY data_criacao DESC";
        try (Connection conn = ConexaoDB.obter();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pacienteId);
            List<Consulta> lista = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
            return lista;
        }
    }

    /** Guarda o que pode mudar durante a vida da consulta (RF07, RF09). Paciente e médico não mudam. */
    public boolean actualizar(Consulta c) throws SQLException {
        String sql = "UPDATE consulta SET urgencia = ?, estado = ?, diagnostico = ?, tratamento = ?, " +
                "data_hora_inicio = ?, data_hora_fim = ? WHERE id = ?";
        try (Connection conn = ConexaoDB.obter();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, c.isUrgencia());
            ps.setString(2, c.getEstado().name());
            ps.setString(3, c.getDiagnostico());
            ps.setString(4, c.getTratamento());
            ps.setTimestamp(5, paraTimestamp(c.getDataHoraInicio()));
            ps.setTimestamp(6, paraTimestamp(c.getDataHoraFim()));
            ps.setInt(7, c.getId());
            return ps.executeUpdate() > 0;
        }
    }

    

    /** LocalDateTime -> Timestamp, aceitando null (coluna sem data). */
    private static Timestamp paraTimestamp(LocalDateTime data) {
        return data == null ? null : Timestamp.valueOf(data);
    }

    private static LocalDateTime paraLocalDateTime(Timestamp ts) {
        return ts == null ? null : ts.toLocalDateTime();
    }

    private Consulta mapear(ResultSet rs) throws SQLException {
        Paciente paciente = pacienteRepo.buscarPorId(rs.getInt("paciente_id"))
                .orElseThrow(() -> new SQLException("Consulta aponta para um paciente que não existe"));
        Medico medico = trabalhadorRepo.buscarMedicoPorId(rs.getInt("medico_id"))
                .orElseThrow(() -> new SQLException("Consulta aponta para um médico que não existe"));

        // triagem_id pode ser NULL
        int triagemId = rs.getInt("triagem_id");
        Triagem triagem = rs.wasNull() ? null : triagemRepo.buscarPorId(triagemId).orElse(null);

        return new Consulta(
                rs.getInt("id"), paciente, medico, triagem,
                rs.getBoolean("urgencia"),
                EstadoConsulta.valueOf(rs.getString("estado")),
                rs.getString("diagnostico"), rs.getString("tratamento"),
                paraLocalDateTime(rs.getTimestamp("data_hora_inicio")),
                paraLocalDateTime(rs.getTimestamp("data_hora_fim")));
    }
}
