import java.sql.*;
import java.util.Scanner;

public class Main{

    public static void usuarios(Connection conn) throws SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS usuarios(" +
                "id SERIAL PRIMARY KEY, " +
                "nome_completo TEXT NOT NULL, " +
                "cpf TEXT NOT NULL UNIQUE, " +
                "email TEXT NOT NULL UNIQUE, " +
                "senha TEXT NOT NULL, " +
                "telefone TEXT NOT NULL, " +
                "tipo_usuario VARCHAR(20) NOT NULL " +
                "CHECK (tipo_usuario IN ('Diretora','Funcionario','Voluntario')), " +
                "status_usuario VARCHAR(20) NOT NULL " +
                "CHECK (status_usuario IN ('Ativo','Inativo'))" +
                ");";

        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }


public static void categorias(Connection conn) throws SQLException {

    String sql = "CREATE TABLE IF NOT EXISTS categorias(" +
            "id SERIAL PRIMARY KEY, " +
            "categoria VARCHAR(20) NOT NULL " +
            "CHECK (categoria IN ('Alimenticio','Movel')), " +
            "descricao TEXT NOT NULL" +
            ");";

    Statement stmt = conn.createStatement();
    stmt.execute(sql);
    stmt.close();
}


public static void estoqueItens(Connection conn) throws SQLException {

    String sql = "CREATE TABLE IF NOT EXISTS itens_estoque(" +
            "id SERIAL PRIMARY KEY, " +
            "categoria_id INTEGER NOT NULL, " +
            "nome_item TEXT NOT NULL, " +
            "item_descricao TEXT NOT NULL, " +
            "quantidade INTEGER NOT NULL, " +
            "origem_item VARCHAR(20) NOT NULL " +
            "CHECK (origem_item IN ('Doacao', 'Compra')), " +
            "valor_unitario DECIMAL(10,2) NOT NULL, " +
            "desconto_aplicado DECIMAL(10,2), " +
            "data_entrada DATE NOT NULL, " +
            "data_validade DATE, " +
            "status_item VARCHAR(20) NOT NULL " +
            "CHECK (status_item IN ('Disponivel','Baixo Estoque','Vencido')), " +
            "FOREIGN KEY (categoria_id) REFERENCES categorias(id)" +
            ");";

    Statement stmt = conn.createStatement();
    stmt.execute(sql);
    stmt.close();
}


    public static void fornecedores(Connection conn) throws SQLException {
        //cria tabela dos fornecedores

        String sql = "CREATE TABLE IF NOT EXISTS fornecedores (" +
                "id SERIAL PRIMARY KEY, " +
                "nome_fornecedor TEXT NOT NULL, " +
                "tipo_fornecedor VARCHAR(20) NOT NULL " +
                "CHECK (tipo_fornecedor IN ('Fornecedor', 'Doador')), " +
                "telefone TEXT NOT NULL, " +
                "email TEXT NOT NULL" +
                ");";

        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();

        System.out.println("Tabela fornecedores criada!");
    }
    public static void movimentacoesEstoque(Connection conn) throws SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS movimentacoes_estoque (" +
                "id SERIAL PRIMARY KEY, " +
                "item_id INTEGER NOT NULL, " +
                "usuario_id INTEGER NOT NULL, " +
                "fornecedor_id INTEGER, " +
                "tipo_movimentacao VARCHAR(10) NOT NULL CHECK (tipo_movimentacao IN ('Entrada', 'Saida')), " +
                "quantidade_movimentada INTEGER NOT NULL, " +
                "destino_item TEXT, " +
                "data_movimentacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "observacoes TEXT, " +

                "FOREIGN KEY (item_id) REFERENCES itens_estoque(id), " +
                "FOREIGN KEY (usuario_id) REFERENCES usuarios(id), " +
                "FOREIGN KEY (fornecedor_id) REFERENCES fornecedores(id)" +
                ");";

        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }

    //======== CRUD DA TABELA DE USUARIOS ========

    public static void cadastrarUsuario(Connection conn, Scanner sc)
            throws SQLException {

        System.out.println("Nome completo:");
        String nome = sc.nextLine();

        System.out.println("CPF:");
        String cpf = sc.nextLine();

        System.out.println("Email:");
        String email = sc.nextLine();

        System.out.println("Senha:");
        String senha = sc.nextLine();

        System.out.println("Telefone:");
        String telefone = sc.nextLine();

        System.out.println("Tipo usuario (Diretora, Funcionario, Voluntario):");
        String tipo = sc.nextLine();

        System.out.println("Status usuario (Ativo, Inativo):");
        String status = sc.nextLine();

        String sql = "INSERT INTO usuarios " +
                "(nome_completo, cpf, email, senha, telefone, tipo_usuario, status_usuario) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, nome);
        ps.setString(2, cpf);
        ps.setString(3, email);
        ps.setString(4, senha);
        ps.setString(5, telefone);
        ps.setString(6, tipo);
        ps.setString(7, status);

        ps.executeUpdate();

        System.out.println("Usuario cadastrado!");

        ps.close();
    }
    public static void consultarUsuarios(Connection conn)
            throws SQLException {

        String sql = "SELECT * FROM usuarios ORDER BY nome_completo";

        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {

            int id = rs.getInt("id");
            String nome = rs.getString("nome_completo");
            String cpf = rs.getString("cpf");
            String email = rs.getString("email");
            String telefone = rs.getString("telefone");
            String tipo = rs.getString("tipo_usuario");
            String status = rs.getString("status_usuario");

            System.out.printf(
                    "[%d] %s | CPF: %s | Email: %s | Tel: %s | Tipo: %s | Status: %s%n",
                    id, nome, cpf, email, telefone, tipo, status
            );
        }

        rs.close();
        stmt.close();
    }

}




