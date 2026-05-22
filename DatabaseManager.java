import java.sql.*;

/**
 * Gerencia a conexão e operações com o banco de dados PostgreSQL.
 */
public class DatabaseManager {

    // ---------------------------------------------------------------
    // CONFIGURAÇÕES – altere conforme seu PostgreSQL
    // ---------------------------------------------------------------
    private static final String HOST     = "localhost";
    private static final String PORTA    = "5432";
    private static final String BANCO    = "clinica";       // nome do banco que você criar
    private static final String USUARIO  = "seu_usuario_aqui";      // seu usuário do postgres
    private static final String SENHA    = "sua_seha_aqui";          // sua senha do postgres

    private static final String URL = "jdbc:postgresql://" + HOST + ":" + PORTA + "/" + BANCO;

    private Connection conn;

    public void conectar() throws SQLException {
        conn = DriverManager.getConnection(URL, USUARIO, SENHA);
        criarTabelaSeNaoExistir();
        System.out.println("Banco de dados PostgreSQL conectado!");
    }

    public void desconectar() {
        try {
            if (conn != null && !conn.isClosed()) conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao desconectar: " + e.getMessage());
        }
    }

    private void criarTabelaSeNaoExistir() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS pacientes (
                    nficha   SERIAL PRIMARY KEY,
                    nome     VARCHAR(100) NOT NULL,
                    idade    INTEGER      NOT NULL,
                    cpf      VARCHAR(14)  NOT NULL UNIQUE,
                    endereco VARCHAR(200) NOT NULL
                );
                """;
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    public int inserirPaciente(String nome, int idade, String cpf, String endereco) throws SQLException {
        String sql = "INSERT INTO pacientes (nome, idade, cpf, endereco) VALUES (?, ?, ?, ?) RETURNING nficha";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setInt(2, idade);
            ps.setString(3, cpf);
            ps.setString(4, endereco);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("nficha");
            throw new SQLException("Falha ao obter número de ficha.");
        }
    }

    public Paciente[] listarPacientes() throws SQLException {
        String sql = "SELECT nficha, nome, idade, cpf, endereco FROM pacientes ORDER BY nficha";
        int total = 0;
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) total++;
        }
        Paciente[] lista = new Paciente[total];
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            int i = 0;
            while (rs.next()) {
                lista[i++] = new Paciente(
                    rs.getString("nome"), rs.getInt("idade"),
                    rs.getString("cpf"), rs.getInt("nficha"), rs.getString("endereco")
                );
            }
        }
        return lista;
    }

    public boolean excluirPorCpf(String cpf) throws SQLException {
        String sql = "DELETE FROM pacientes WHERE cpf = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cpf);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean cpfExiste(String cpf) throws SQLException {
        String sql = "SELECT COUNT(*) FROM pacientes WHERE cpf = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cpf);
            ResultSet rs = ps.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    public int contarPacientes() throws SQLException {
        String sql = "SELECT COUNT(*) FROM pacientes";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }
}
