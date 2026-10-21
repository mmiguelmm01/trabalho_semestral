package repository;

import model.Enfermeiro;
import model.Paciente;
import model.Triagem;
import model.enums.NiveisRisco;
import util.ConexaoDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Uma Triagem guarda ids de paciente e enfermeiro na BD, mas o objecto Java guarda os
 * objectos completos. Por isso, ao ler, este repository pede ao PacienteRepository e ao
 * TrabalhadorRepository que reconstruam esses objectos a partir dos ids.
 */
public class TriagemRepository {

    private final PacienteRepository pacienteRepo = new PacienteRepository();
    private final TrabalhadorRepository trabalhadorRepo = new TrabalhadorRepository();

    private static final String COLUNAS =
            "id, paciente_id, enfermeiro_id, nivel_risco, queixa_principal, temperatura, " +
            "pressao_arterial, freq_cardiaca, data_criacao";

    public Triagem inserir(Triagem t) throws SQLException {
        String sql = "INSERT INTO triagem (paciente_id, enfermeiro_id, nivel_risco, queixa_principal, " +
                "temperatura, pressao_arterial, freq_cardiaca) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoDB.obter();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, t.getPaciente().getId());       // do objecto só interessa o id
            ps.setInt(2, t.getEnfermeiro().getId());
            ps.setString(3, t.getNivelRisco().getLetra());
            ps.setString(4, t.getQueixaPrincipal());
            
            if (t.getTemperatura() == null) ps.setNull(5, Types.DECIMAL); else ps.setDouble(5, t.getTemperatura());
            ps.setString(6, t.getPressaoArterial());
            if (t.getFrequenciaCardiaca() == null) ps.setNull(7, Types.SMALLINT); else ps.setInt(7, t.getFrequenciaCardiaca());

            ps.executeUpdate();
            try (ResultSet chaves = ps.getGeneratedKeys()) {
                if (chaves.next()) {
                    t.setId(chaves.getInt(1));
                }
            }
            return buscarPorId(t.getId()).orElse(t);   
        }
    }

    public Optional<Triagem> buscarPorId(int id) throws SQLException {
        String sql = "SELECT " + COLUNAS + " FROM triagem WHERE id = ?";
        try (Connection conn = ConexaoDB.obter();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        }
    }

    /** RF10/RF11: histórico de triagens de um paciente, da mais recente para a mais antiga. */
    public List<Triagem> listarPorPaciente(int pacienteId) throws SQLException {
        String sql = "SELECT " + COLUNAS + " FROM triagem WHERE paciente_id = ? ORDER BY data_criacao DESC";
        try (Connection conn = ConexaoDB.obter();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pacienteId);
            List<Triagem> lista = new ArrayList<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
            return lista;
        }
    }

    private Triagem mapear(ResultSet rs) throws SQLException {
        Paciente paciente = pacienteRepo.buscarPorId(rs.getInt("paciente_id"))
                .orElseThrow(() -> new SQLException("Triagem aponta para um paciente que não existe"));
        Enfermeiro enfermeiro = trabalhadorRepo.buscarEnfermeiroPorId(rs.getInt("enfermeiro_id"))
                .orElseThrow(() -> new SQLException("Triagem aponta para um enfermeiro que não existe"));

       
        double temp = rs.getDouble("temperatura");
        Double temperatura = rs.wasNull() ? null : temp;
        int fc = rs.getInt("freq_cardiaca");
        Integer frequencia = rs.wasNull() ? null : fc;

        return new Triagem(
                rs.getInt("id"), paciente, enfermeiro,
                NiveisRisco.deLetra(rs.getString("nivel_risco")),
                rs.getString("queixa_principal"), temperatura,
                rs.getString("pressao_arterial"), frequencia,
                rs.getTimestamp("data_criacao").toLocalDateTime());
    }
}
