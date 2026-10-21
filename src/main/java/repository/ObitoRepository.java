package repository;

import model.Consulta;
import model.Medico;
import model.Obito;
import model.Paciente;
import util.ConexaoDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Optional;

public class ObitoRepository {

    private final PacienteRepository pacienteRepo = new PacienteRepository();
    private final TrabalhadorRepository trabalhadorRepo = new TrabalhadorRepository();
    private final ConsultaRepository consultaRepo = new ConsultaRepository();

    /**
     * RN09: registar um óbito tem DOIS efeitos que têm de acontecer juntos ou nenhum:
     *   1. inserir a linha em obito;
     *   2. passar o paciente a FALECIDO.
     * Isto é uma TRANSACÇÃO: desligamos o auto-commit, fazemos os dois passos e só no fim
     * confirmamos (commit). Se algo falhar, rollback desfaz tudo e a BD fica como estava.
     */
    public Obito inserir(Obito o) throws SQLException {
        String sqlObito = "INSERT INTO obito (paciente_id, consulta_id, medico_id, data_obito, causa, observacoes) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        String sqlPaciente = "UPDATE paciente SET estado = 'FALECIDO' WHERE id = ?";

        try (Connection conn = ConexaoDB.obter()) {
            conn.setAutoCommit(false);
            try (PreparedStatement psObito = conn.prepareStatement(sqlObito, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement psPaciente = conn.prepareStatement(sqlPaciente)) {

                psObito.setInt(1, o.getPaciente().getId());
                if (o.getConsulta() == null) psObito.setNull(2, Types.INTEGER); else psObito.setInt(2, o.getConsulta().getId());
                psObito.setInt(3, o.getMedico().getId());
                psObito.setTimestamp(4, Timestamp.valueOf(o.getDataHora()));
                psObito.setString(5, o.getCausa());
                psObito.setString(6, o.getObservacoes());
                psObito.executeUpdate();

                try (ResultSet chaves = psObito.getGeneratedKeys()) {
                    if (chaves.next()) {
                        o.setId(chaves.getInt(1));
                    }
                }

                psPaciente.setInt(1, o.getPaciente().getId());
                psPaciente.executeUpdate();

                conn.commit();         
                return o;
            } catch (SQLException e) {
                conn.rollback();       
                throw e;
            }
        }
    }

    /** Cada paciente tem no máximo um óbito (paciente_id é UNIQUE). */
    public Optional<Obito> buscarPorPaciente(int pacienteId) throws SQLException {
        String sql = "SELECT id, paciente_id, consulta_id, medico_id, data_obito, causa, observacoes " +
                "FROM obito WHERE paciente_id = ?";
        try (Connection conn = ConexaoDB.obter();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pacienteId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        }
    }

    private Obito mapear(ResultSet rs) throws SQLException {
        Paciente paciente = pacienteRepo.buscarPorId(rs.getInt("paciente_id"))
                .orElseThrow(() -> new SQLException("Óbito aponta para um paciente que não existe"));
        Medico medico = trabalhadorRepo.buscarMedicoPorId(rs.getInt("medico_id"))
                .orElseThrow(() -> new SQLException("Óbito aponta para um médico que não existe"));

        int consultaId = rs.getInt("consulta_id");
        Consulta consulta = rs.wasNull() ? null : consultaRepo.buscarPorId(consultaId).orElse(null);

        return new Obito(rs.getInt("id"), paciente, consulta, medico,
                rs.getTimestamp("data_obito").toLocalDateTime(),
                rs.getString("causa"), rs.getString("observacoes"));
    }
}
