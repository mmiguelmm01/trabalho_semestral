package repository;

import model.Administrador;
import model.Enfermeiro;
import model.Medico;
import model.Recepcionista;
import model.Trabalhador;
import model.enums.Niveis;
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
 * Todos os trabalhadores vivem na tabela "trabalhador". A coluna "nivel" decide
 * que subclasse Java se cria ao ler (ver mapear) e que colunas extra se preenchem ao gravar.
 * Mesmo padrão do PacienteRepository.
 */
public class TrabalhadorRepository {

    private static final String COLUNAS =
            "id, nome_completo, data_nascimento, numero_bi, endereco_residencial, telefone, email, username, " +
            "senha_hash, naturalidade, salario, carga_horaria, nivel, activo, especialidade, numero_ordem, " +
            "numero_registo_profissional, sector, categoria, balcao";

    public Trabalhador inserir(Trabalhador t) throws SQLException {
        String sql = "INSERT INTO trabalhador (nome_completo, data_nascimento, numero_bi, endereco_residencial, " +
                "telefone, email, username, senha_hash, naturalidade, salario, carga_horaria, nivel, activo, " +
                "especialidade, numero_ordem, numero_registo_profissional, sector, categoria, balcao) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.obter();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preencherComuns(ps, t);   // parâmetros 1 a 13
            preencherEspecificos(ps, t, 14);   // parâmetros 14 a 19

            ps.executeUpdate();
            try (ResultSet chaves = ps.getGeneratedKeys()) {
                if (chaves.next()) {
                    t.setId(chaves.getInt(1));
                }
            }
            return t;
        }
    }

    public Optional<Trabalhador> buscarPorId(int id) throws SQLException {
        String sql = "SELECT " + COLUNAS + " FROM trabalhador WHERE id = ?";
        try (Connection conn = ConexaoDB.obter();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        }
    }

    /** Para o login (RNF01). */
    public Optional<Trabalhador> buscarPorUsername(String username) throws SQLException {
        String sql = "SELECT " + COLUNAS + " FROM trabalhador WHERE username = ?";
        try (Connection conn = ConexaoDB.obter();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        }
    }

    /** Atalhos para quando o chamador precisa de uma subclasse concreta (ex.: Triagem exige Enfermeiro). */
    public Optional<Medico> buscarMedicoPorId(int id) throws SQLException {
        return buscarPorId(id).filter(Medico.class::isInstance).map(Medico.class::cast);
    }

    public Optional<Enfermeiro> buscarEnfermeiroPorId(int id) throws SQLException {
        return buscarPorId(id).filter(Enfermeiro.class::isInstance).map(Enfermeiro.class::cast);
    }

    public List<Trabalhador> listarTodos() throws SQLException {
        String sql = "SELECT " + COLUNAS + " FROM trabalhador ORDER BY nome_completo";
        try (Connection conn = ConexaoDB.obter();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return lerLista(ps);
        }
    }

    public List<Trabalhador> listarPorNivel(Niveis nivel) throws SQLException {
        String sql = "SELECT " + COLUNAS + " FROM trabalhador WHERE nivel = ? ORDER BY nome_completo";
        try (Connection conn = ConexaoDB.obter();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nivel.name());
            return lerLista(ps);
        }
    }

    /** Actualiza tudo menos id, nivel e data_criacao (o nível de um trabalhador não muda aqui). */
    public boolean actualizar(Trabalhador t) throws SQLException {
        String sql = "UPDATE trabalhador SET nome_completo = ?, data_nascimento = ?, numero_bi = ?, " +
                "endereco_residencial = ?, telefone = ?, email = ?, username = ?, senha_hash = ?, " +
                "naturalidade = ?, salario = ?, carga_horaria = ?, activo = ?, especialidade = ?, " +
                "numero_ordem = ?, numero_registo_profissional = ?, sector = ?, categoria = ?, balcao = ? " +
                "WHERE id = ?";
        try (Connection conn = ConexaoDB.obter();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, t.getNomeCompleto());
            ps.setDate(2, Date.valueOf(t.getDataNascimento()));
            ps.setString(3, t.getNumeroBi());
            ps.setString(4, t.getEnderecoResidencial());
            ps.setString(5, t.getTelefone());
            ps.setString(6, t.getEmail());
            ps.setString(7, t.getNomeUtilizador());
            ps.setString(8, t.getSenha());
            ps.setString(9, t.getNaturalidade());
            ps.setDouble(10, t.getSalario());
            ps.setDouble(11, t.getCargaHoraria());
            ps.setBoolean(12, t.isActivo());
            preencherEspecificos(ps, t, 13);   
            ps.setInt(19, t.getId());
            return ps.executeUpdate() > 0;
        }
    }

    /** Um trabalhador não se apaga (tem histórico); desactiva-se. */
    public boolean definirActivo(int id, boolean activo) throws SQLException {
        String sql = "UPDATE trabalhador SET activo = ? WHERE id = ?";
        try (Connection conn = ConexaoDB.obter();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, activo);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        }
    }

    

    private void preencherComuns(PreparedStatement ps, Trabalhador t) throws SQLException {
        ps.setString(1, t.getNomeCompleto());
        ps.setDate(2, Date.valueOf(t.getDataNascimento()));
        ps.setString(3, t.getNumeroBi());
        ps.setString(4, t.getEnderecoResidencial());
        ps.setString(5, t.getTelefone());
        ps.setString(6, t.getEmail());
        ps.setString(7, t.getNomeUtilizador());
        ps.setString(8, t.getSenha());
        ps.setString(9, t.getNaturalidade());
        ps.setDouble(10, t.getSalario());
        ps.setDouble(11, t.getCargaHoraria());
        ps.setString(12, t.getNiveis().name());
        ps.setBoolean(13, t.isActivo());
    }

    /**
     * Preenche, a partir da posição "inicio", as 6 colunas específicas das subclasses:
     * especialidade, numero_ordem, numero_registo_profissional, sector, categoria, balcao.
     * As que não pertencem à subclasse ficam NULL (o CHECK da tabela exige-o).
     */
    private void preencherEspecificos(PreparedStatement ps, Trabalhador t, int inicio) throws SQLException {
        String especialidade = null, numeroOrdem = null, numeroRegisto = null;
        String sector = null, categoria = null, balcao = null;

        if (t instanceof Medico m) {             // "instanceof Medico m": testa o tipo e já te dá a variável m
            especialidade = m.getEspecialidade();
            numeroOrdem = m.getNumeroOrdem();
            numeroRegisto = m.getNumeroRegistoProfissional();
        } else if (t instanceof Enfermeiro e) {
            numeroRegisto = e.getNumeroRegistoProfissional();
            sector = e.getSector();
            categoria = e.getCategoria();
        } else if (t instanceof Recepcionista r) {
            balcao = r.getBalcao();
        }

        ps.setString(inicio, especialidade);
        ps.setString(inicio + 1, numeroOrdem);
        ps.setString(inicio + 2, numeroRegisto);
        ps.setString(inicio + 3, sector);
        ps.setString(inicio + 4, categoria);
        ps.setString(inicio + 5, balcao);
    }

    private List<Trabalhador> lerLista(PreparedStatement ps) throws SQLException {
        List<Trabalhador> lista = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

  
    static Trabalhador mapear(ResultSet rs) throws SQLException {
        Niveis nivel = Niveis.valueOf(rs.getString("nivel"));

        int id = rs.getInt("id");
        String nome = rs.getString("nome_completo");
        var dataNascimento = rs.getDate("data_nascimento").toLocalDate();
        String bi = rs.getString("numero_bi");
        String endereco = rs.getString("endereco_residencial");
        String telefone = rs.getString("telefone");
        String email = rs.getString("email");
        String username = rs.getString("username");
        String senha = rs.getString("senha_hash");
        String naturalidade = rs.getString("naturalidade");
        double salario = rs.getDouble("salario");
        double cargaHoraria = rs.getDouble("carga_horaria");
        boolean activo = rs.getBoolean("activo");

        return switch (nivel) {
            case ADMINISTRADOR -> new Administrador(id, nome, dataNascimento, bi, endereco, telefone,
                    username, senha, email, naturalidade, salario, cargaHoraria, activo);
            case MEDICO -> new Medico(id, nome, dataNascimento, bi, endereco, telefone,
                    username, senha, email, naturalidade, salario, cargaHoraria, activo,
                    rs.getString("especialidade"), rs.getString("numero_ordem"),
                    rs.getString("numero_registo_profissional"));
            case ENFERMEIRO -> new Enfermeiro(id, nome, dataNascimento, bi, endereco, telefone,
                    username, senha, email, naturalidade, salario, cargaHoraria, activo,
                    rs.getString("sector"), rs.getString("numero_registo_profissional"),
                    rs.getString("categoria"));
            case RECEPCIONISTA -> new Recepcionista(id, nome, dataNascimento, bi, endereco, telefone,
                    username, senha, email, naturalidade, salario, cargaHoraria, activo,
                    rs.getString("balcao"));
        };
    }
}
