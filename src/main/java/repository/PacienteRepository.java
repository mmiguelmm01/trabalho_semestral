package repository;

import model.Paciente;
import model.enums.EstadoPaciente;
import model.enums.GrupoSanguineo;
import model.enums.Sexo;
import util.ConexaoDB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Único sítio do sistema que "fala SQL" sobre a tabela paciente.
 * O Service chama estes métodos e nunca vê SQL nem ResultSet.
 *
 * Padrão que se repete em TODOS os métodos (aprende-o aqui):
 *   1. abrir a ligação num try-with-resources (fecha sozinha, mesmo com erro);
 *   2. preparar o SQL com "?" nos valores (PreparedStatement: evita SQL injection);
 *   3. preencher os "?" pela ordem (a contagem começa em 1);
 *   4. executar (executeQuery para SELECT, executeUpdate para INSERT/UPDATE);
 *   5. converter o resultado em objectos Java.
 */
public class PacienteRepository {

    
    private static final String COLUNAS =
            "id, nome_completo, data_nascimento, numero_bi, endereco_residencial, telefone, sexo, " +
            "grupo_sanguineo, nome_acompanhante, papel_acompanhante, estado, registado_por, data_criacao";

    /** Grava um paciente novo e devolve-o já com o id que a base de dados gerou. */
    public Paciente inserir(Paciente p) throws SQLException {
        String sql = "INSERT INTO paciente (nome_completo, data_nascimento, numero_bi, endereco_residencial, " +
                "telefone, sexo, grupo_sanguineo, nome_acompanhante, papel_acompanhante, registado_por) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.obter();
             
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getNomeCompleto());
            ps.setDate(2, Date.valueOf(p.getDataNascimento()));
            ps.setString(3, p.getNumeroBi());                 // null é aceite: vira NULL na BD
            ps.setString(4, p.getEnderecoResidencial());
            ps.setString(5, p.getTelefone());
            ps.setString(6, p.getSexo().name());
            ps.setString(7, p.getGrupoSanguineo() == null ? null : p.getGrupoSanguineo().getSimbolo());
            ps.setString(8, p.getNomeAcompanhante());
            ps.setString(9, p.getPapelAcompanhante());
            ps.setInt(10, p.getRegistadoPor());

            ps.executeUpdate();

            try (ResultSet chaves = ps.getGeneratedKeys()) {
                if (chaves.next()) {
                    p.setId(chaves.getInt(1));
                }
            }
           
            return buscarPorId(p.getId()).orElse(p);
        }
    }

    public Optional<Paciente> buscarPorId(int id) throws SQLException {
        String sql = "SELECT " + COLUNAS + " FROM paciente WHERE id = ?";
        try (Connection conn = ConexaoDB.obter();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        }
    }

    /** RN04: o BI identifica o paciente. Útil para evitar duplicados antes de inserir (RN02). */
    public Optional<Paciente> buscarPorBi(String numeroBi) throws SQLException {
        String sql = "SELECT " + COLUNAS + " FROM paciente WHERE numero_bi = ?";
        try (Connection conn = ConexaoDB.obter();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, numeroBi);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        }
    }

    /** RF02: pesquisa por parte do nome. O '%' do LIKE é "qualquer texto". */
    public List<Paciente> pesquisarPorNome(String parteDoNome) throws SQLException {
        String sql = "SELECT " + COLUNAS + " FROM paciente WHERE nome_completo LIKE ? ORDER BY nome_completo";
        try (Connection conn = ConexaoDB.obter();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + parteDoNome + "%");
            return lerLista(ps);
        }
    }

    public List<Paciente> listarTodos() throws SQLException {
        String sql = "SELECT " + COLUNAS + " FROM paciente ORDER BY nome_completo";
        try (Connection conn = ConexaoDB.obter();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return lerLista(ps);
        }
    }

    /**
     * RF04: actualiza os dados cadastrais. Não mexe em id, registado_por nem data_criacao
     * (quem registou e quando nunca muda) nem no estado (para isso há actualizarEstado).
     * Devolve true se encontrou e alterou o paciente.
     */
    public boolean actualizar(Paciente p) throws SQLException {
        String sql = "UPDATE paciente SET nome_completo = ?, data_nascimento = ?, numero_bi = ?, " +
                "endereco_residencial = ?, telefone = ?, sexo = ?, grupo_sanguineo = ?, " +
                "nome_acompanhante = ?, papel_acompanhante = ? WHERE id = ?";
        try (Connection conn = ConexaoDB.obter();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNomeCompleto());
            ps.setDate(2, Date.valueOf(p.getDataNascimento()));
            ps.setString(3, p.getNumeroBi());
            ps.setString(4, p.getEnderecoResidencial());
            ps.setString(5, p.getTelefone());
            ps.setString(6, p.getSexo().name());
            ps.setString(7, p.getGrupoSanguineo() == null ? null : p.getGrupoSanguineo().getSimbolo());
            ps.setString(8, p.getNomeAcompanhante());
            ps.setString(9, p.getPapelAcompanhante());
            ps.setInt(10, p.getId());
            return ps.executeUpdate() > 0;  
        }
    }

    /** RN09: um paciente nunca se apaga; muda-se o estado. Por isso não existe método eliminar(). */
    public boolean actualizarEstado(int id, EstadoPaciente novoEstado) throws SQLException {
        String sql = "UPDATE paciente SET estado = ? WHERE id = ?";
        try (Connection conn = ConexaoDB.obter();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, novoEstado.name());
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        }
    }

   

    private List<Paciente> lerLista(PreparedStatement ps) throws SQLException {
        List<Paciente> lista = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {        
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    /**
     * Converte a linha ACTUAL do ResultSet num objecto Paciente.
     * É o caminho inverso do INSERT: cada coluna volta ao seu tipo Java.
     * Este método fica package-private para os outros repositories o poderem reutilizar.
     */
    static Paciente mapear(ResultSet rs) throws SQLException {
        return new Paciente(
                rs.getInt("id"),
                rs.getString("nome_completo"),
                rs.getDate("data_nascimento").toLocalDate(),
                rs.getString("numero_bi"),
                rs.getString("endereco_residencial"),
                rs.getString("telefone"),
                Sexo.valueOf(rs.getString("sexo")),
                GrupoSanguineo.deSimbolo(rs.getString("grupo_sanguineo")),  // aceita null
                rs.getString("nome_acompanhante"),
                rs.getString("papel_acompanhante"),
                EstadoPaciente.valueOf(rs.getString("estado")),
                rs.getInt("registado_por"),
                rs.getTimestamp("data_criacao").toLocalDateTime()
        );
    }
}
