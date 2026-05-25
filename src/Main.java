import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main{

    public static void Usarios(Connection conn) throws SQLException {
        String sql= "CREATE TABLE IF NOT EXISTS usuarios(" +
                "id SERIAL PRIMARY KEY, " +
                "nomeCompleto TEXT NOT NULL, " +
                "cpf TEXT NOT NULL, " +
                "email TEXT NOT NULL, "+
                "senha TEXT NOT NULL, "+
                "telefone TEXT NOT NULL, "+
                "tipoUsuario ENUM('Diretora','Funcionario', 'Voluntario') NOT NULL "+
                "statusUsuario ENUM('Ativo', 'Inativo') NOT NULL )";
        //Criar  objeto de instrução sql
        Statement stmt = conn.createStatement();
        stmt.execute(sql); // executa comando SQL
        stmt.close();// fecha instrucao SQL
    }

    public static void categorias(Connection conn) throws SQLException {
        String sql= "CREATE TABLE IF NOT EXISTS usuarios(" +
                "id SERIAL PRIMARY KEY, " +
                "categoria ENUM('Alimenticio','Movel') NOT NULL "+
                "descricao TEXT NOT NULL )";
        //Criar  objeto de instrução sql
        Statement stmt = conn.createStatement();
        stmt.execute(sql); // executa comando SQL
        stmt.close();// fecha instrucao SQL
    }

    public static void EstoqueItens(Connection conn) throws SQLException {
        //cria tabela dos itens em estoque
        String sql= "CREATE TABLE IF NOT EXISTS usuarios(" +
                "id SERIAL PRIMARY KEY, " +
                "nomeItem TEXT NOT NULL, " +
                "itenDescricao TEXT NOT NULL, " +
                "quantidade INTEGER NOT NULL, "+
                "origem_item VARCHAR(20) NOT NULL " +
                "CHECK (origem_item IN ('Doacao', 'Compra')), " +
                "valor_unitario DECIMAL(10,2) NOT NULL, " +
                "desconto_aplicado DECIMAL(10,2), " +
                "data_entrada DATE NOT NULL, " +
                "data_validade DATE, " +
                "status_item VARCHAR(20) NOT NULL " +
                "CHECK (status_item IN ('Disponivel', 'Baixo Estoque', 'Vencido')), " +
                "FOREIGN KEY (categoria_id) REFERENCES categorias(id)";
        //Criar  objeto de instrução sql
        Statement stmt = conn.createStatement();
        stmt.execute(sql); // executa comando SQL
        stmt.close();// fecha instrucao SQL
    }

    public static void Fornecedores(Connection conn) throws SQLException {
        //cria tabela dos fornecedores

        String sql = "CREATE TABLE IF NOT EXISTS fornecedores (" +
                "id SERIAL PRIMARY KEY, " +
                "nome_fornecedor TEXT NOT NULL, " +
                "tipo_fornecedor VARCHAR(20) NOT NULL " +
                "CHECK (tipo_fornecedor IN ('Fornecedor', 'Doador')), " +
                "telefone TEXT NOT NULL, " +
                "email TEXT" +
                ");";

        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();

        System.out.println("Tabela fornecedores criada!");
    }
    public static void MovimentacoesEstoque(Connection conn) throws SQLException {

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

    public static void avisosInternos(Connection conn) throws SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS avisos_internos (" +
                "id SERIAL PRIMARY KEY, " +
                "autor_id INTEGER NOT NULL, " +
                "titulo TEXT NOT NULL, " +
                "conteudo TEXT NOT NULL, " +
                "data_postagem TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +

                "FOREIGN KEY (autor_id) REFERENCES usuarios(id)" +
                ");";

        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }

    public static void HistoricoAcessos(Connection conn) throws SQLException {

        String sql = "CREATE TABLE IF NOT EXISTS historico_acessos (" +
                "id SERIAL PRIMARY KEY, " +
                "usuario_id INTEGER NOT NULL, " +
                "acao_realizada TEXT NOT NULL, " +
                "data_acao TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "endereco_ip TEXT, " +
                "FOREIGN KEY (usuario_id) REFERENCES usuarios(id)" +
                ");";

        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }


}

