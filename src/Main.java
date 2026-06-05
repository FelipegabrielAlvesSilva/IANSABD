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
                "CHECK (status_usuario IN ('Ativo','Inativo')))" ;


        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }


public static void categorias(Connection conn) throws SQLException {

    String sql = "CREATE TABLE IF NOT EXISTS categorias(" +
            "id SERIAL PRIMARY KEY, " +
            "categoria VARCHAR(20) NOT NULL " +
            "CHECK (categoria IN ('Alimenticio','Movel')), " +
            "descricao TEXT NOT NULL)" ;


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
            "FOREIGN KEY (categoria_id) REFERENCES categorias(id))";


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
                "email TEXT NOT NULL)" ;


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
                "FOREIGN KEY (fornecedor_id) REFERENCES fornecedores(id))" ;


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
    public static void alterarUsuario(Connection conn, Scanner sc)
            throws SQLException {

        System.out.println("Informe o ID do usuario:");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.println("Novo nome completo:");
        String nome = sc.nextLine();

        System.out.println("Novo CPF:");
        String cpf = sc.nextLine();

        System.out.println("Novo email:");
        String email = sc.nextLine();

        System.out.println("Nova senha:");
        String senha = sc.nextLine();

        System.out.println("Novo telefone:");
        String telefone = sc.nextLine();

        System.out.println("Novo tipo de usuario (Diretora, Funcionario, Voluntario):");
        String tipo = sc.nextLine();

        System.out.println("Novo status (Ativo, Inativo):");
        String status = sc.nextLine();

        String sql = "UPDATE usuarios SET " +
                "nome_completo = ?, " +
                "cpf = ?, " +
                "email = ?, " +
                "senha = ?, " +
                "telefone = ?, " +
                "tipo_usuario = ?, " +
                "status_usuario = ? " +
                "WHERE id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, nome);
        ps.setString(2, cpf);
        ps.setString(3, email);
        ps.setString(4, senha);
        ps.setString(5, telefone);
        ps.setString(6, tipo);
        ps.setString(7, status);
        ps.setInt(8, id);

        int linhasAfetadas = ps.executeUpdate();

        if (linhasAfetadas > 0) {
            System.out.println("Usuario atualizado com sucesso!");
        } else {
            System.out.println("ID nao encontrado.");
        }

        ps.close();
    }


    //======== CRUD DA TABELA DE CATEGORIAS ========

    public static void cadastrarCategoria(Connection conn, Scanner sc)
            throws SQLException {

        System.out.println("Categoria (Alimenticio ou Movel):");
        String categoria = sc.nextLine();

        System.out.println("Descricao:");
        String descricao = sc.nextLine();

        String sql = "INSERT INTO categorias (categoria, descricao) " +
                "VALUES (?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, categoria);
        ps.setString(2, descricao);

        ps.executeUpdate();

        System.out.println("Categoria cadastrada!");

        ps.close();
    }

    public static void consultarCategorias(Connection conn)
            throws SQLException {

        String sql = "SELECT * FROM categorias ORDER BY id";

        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {

            int id = rs.getInt("id");
            String categoria = rs.getString("categoria");
            String descricao = rs.getString("descricao");

            System.out.printf(
                    "[%d] %s - %s%n",
                    id, categoria, descricao
            );
        }

        rs.close();
        stmt.close();
    }


    //======== CRUD DA TABELA DE ITENS ========

    public static void cadastrarItem(Connection conn, Scanner sc)
            throws SQLException {

        System.out.println("ID da categoria:");
        int categoriaId = sc.nextInt();
        sc.nextLine();

        System.out.println("Nome do item:");
        String nome = sc.nextLine();

        System.out.println("Descricao do item:");
        String descricao = sc.nextLine();

        System.out.println("Quantidade:");
        int quantidade = sc.nextInt();
        sc.nextLine();

        System.out.println("Origem do item (Doacao ou Compra):");
        String origem = sc.nextLine();

        Double valor = null;

        if (origem.equalsIgnoreCase("Compra")) {
            System.out.println("Valor unitario:");
            valor = sc.nextDouble();
            sc.nextLine();
        }

        System.out.println("Desconto aplicado:");
        double desconto = sc.nextDouble();
        sc.nextLine();

        System.out.println("Data entrada (AAAA-MM-DD):");
        String dataEntrada = sc.nextLine();

        System.out.println("Data validade (AAAA-MM-DD) ou ENTER:");
        String dataValidade = sc.nextLine();

        System.out.println("Status item (Disponivel, Baixo Estoque, Vencido):");
        String status = sc.nextLine();

        String sql = "INSERT INTO itens_estoque (" +
                "categoria_id, nome_item, item_descricao, quantidade, " +
                "origem_item, valor_unitario, desconto_aplicado, " +
                "data_entrada, data_validade, status_item" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, categoriaId);
        ps.setString(2, nome);
        ps.setString(3, descricao);
        ps.setInt(4, quantidade);
        ps.setString(5, origem);

        if (valor != null) {
            ps.setDouble(6, valor);
        } else {
            ps.setNull(6, Types.DECIMAL);
        }

        ps.setDouble(7, desconto);
        ps.setDate(8, Date.valueOf(dataEntrada));

        if (dataValidade.isEmpty()) {
            ps.setNull(9, Types.DATE);
        } else {
            ps.setDate(9, Date.valueOf(dataValidade));
        }

        ps.setString(10, status);

        ps.executeUpdate();

        System.out.println("Item cadastrado com sucesso!");

        ps.close();
    }

    public static void consultarItens(Connection conn)
            throws SQLException {

        String sql = "SELECT * FROM itens_estoque ORDER BY nome_item";

        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {

            int id = rs.getInt("id");
            int categoria = rs.getInt("categoria_id");
            String nome = rs.getString("nome_item");
            String descricao = rs.getString("item_descricao");
            int quantidade = rs.getInt("quantidade");
            String origem = rs.getString("origem_item");
            double valor = rs.getDouble("valor_unitario");
            double desconto = rs.getDouble("desconto_aplicado");
            Date entrada = rs.getDate("data_entrada");
            Date validade = rs.getDate("data_validade");
            String status = rs.getString("status_item");

            System.out.printf(
                    "[%d] Categoria:%d | %s | %s | Qtd:%d | Origem:%s | Valor: %.2f | Desconto: %.2f | Entrada:%s | Validade:%s | Status:%s%n",
                    id, categoria, nome, descricao,
                    quantidade, origem, valor,
                    desconto, entrada,
                    validade, status
            );
        }

        rs.close();
        stmt.close();
    }

    public static void alterarItem(Connection conn, Scanner sc)
            throws SQLException {

        System.out.println("Informe o ID do item:");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.println("Novo ID categoria:");
        int categoriaId = sc.nextInt();
        sc.nextLine();

        System.out.println("Novo nome item:");
        String nome = sc.nextLine();

        System.out.println("Nova descricao:");
        String descricao = sc.nextLine();

        System.out.println("Nova quantidade:");
        int quantidade = sc.nextInt();
        sc.nextLine();

        System.out.println("Nova origem (Doacao ou Compra):");
        String origem = sc.nextLine();

        Double valor = null;

        if (origem.equalsIgnoreCase("Compra")) {
            System.out.println("Novo valor unitario:");
            valor = sc.nextDouble();
            sc.nextLine();
        }

        System.out.println("Novo desconto:");
        double desconto = sc.nextDouble();
        sc.nextLine();

        System.out.println("Nova data entrada (AAAA-MM-DD):");
        String entrada = sc.nextLine();

        System.out.println("Nova data validade (AAAA-MM-DD) ou ENTER:");
        String validade = sc.nextLine();

        System.out.println("Novo status:");
        String status = sc.nextLine();

        String sql = "UPDATE itens_estoque SET " +
                "categoria_id=?, " +
                "nome_item=?, " +
                "item_descricao=?, " +
                "quantidade=?, " +
                "origem_item=?, " +
                "valor_unitario=?, " +
                "desconto_aplicado=?, " +
                "data_entrada=?, " +
                "data_validade=?, " +
                "status_item=? " +
                "WHERE id=?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, categoriaId);
        ps.setString(2, nome);
        ps.setString(3, descricao);
        ps.setInt(4, quantidade);
        ps.setString(5, origem);

        if (valor != null) {
            ps.setDouble(6, valor);
        } else {
            ps.setNull(6, Types.DECIMAL);
        }

        ps.setDouble(7, desconto);
        ps.setDate(8, Date.valueOf(entrada));

        if (validade.isEmpty()) {
            ps.setNull(9, Types.DATE);
        } else {
            ps.setDate(9, Date.valueOf(validade));
        }

        ps.setString(10, status);
        ps.setInt(11, id);

        int linhasAfetadas = ps.executeUpdate();

        if (linhasAfetadas > 0) {
            System.out.println("Item atualizado!");
        } else {
            System.out.println("ID nao encontrado.");
        }

        ps.close();
    }

    //======== CRUD DA TABELA DE FORNECEDORES ========

    public static void cadastrarFornecedor(Connection conn, Scanner sc)
            throws SQLException {

        System.out.println("Nome do fornecedor:");
        String nome = sc.nextLine();

        System.out.println("Tipo (Fornecedor ou Doador):");
        String tipo = sc.nextLine();

        System.out.println("Telefone:");
        String telefone = sc.nextLine();

        System.out.println("Email:");
        String email = sc.nextLine();

        String sql = "INSERT INTO fornecedores " +
                "(nome_fornecedor, tipo_fornecedor, telefone, email) " +
                "VALUES (?, ?, ?, ?)";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, nome);
        ps.setString(2, tipo);
        ps.setString(3, telefone);
        ps.setString(4, email);

        ps.executeUpdate();

        System.out.println("Fornecedor cadastrado com sucesso!");

        ps.close();
    }
    public static void consultarFornecedores(Connection conn)
            throws SQLException {

        String sql = "SELECT * FROM fornecedores " +
                "ORDER BY nome_fornecedor";

        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {

            int id = rs.getInt("id");
            String nome = rs.getString("nome_fornecedor");
            String tipo = rs.getString("tipo_fornecedor");
            String telefone = rs.getString("telefone");
            String email = rs.getString("email");

            System.out.printf(
                    "[%d] %s | Tipo: %s | Telefone: %s | Email: %s%n",
                    id, nome, tipo, telefone, email
            );
        }

        rs.close();
        stmt.close();
    }

    public static void alterarFornecedor(Connection conn, Scanner sc)
            throws SQLException {

        System.out.println("Informe o ID do fornecedor:");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.println("Novo nome:");
        String nome = sc.nextLine();

        System.out.println("Novo tipo (Fornecedor ou Doador):");
        String tipo = sc.nextLine();

        System.out.println("Novo telefone:");
        String telefone = sc.nextLine();

        System.out.println("Novo email:");
        String email = sc.nextLine();

        String sql = "UPDATE fornecedores SET " +
                "nome_fornecedor = ?, " +
                "tipo_fornecedor = ?, " +
                "telefone = ?, " +
                "email = ? " +
                "WHERE id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, nome);
        ps.setString(2, tipo);
        ps.setString(3, telefone);
        ps.setString(4, email);
        ps.setInt(5, id);

        int linhasAfetadas = ps.executeUpdate();

        if (linhasAfetadas > 0) {
            System.out.println("Fornecedor atualizado com sucesso!");
        } else {
            System.out.println("ID não encontrado.");
        }

        ps.close();
    }

    //======== CRUD DA TABELA DE MOVIMENTAÇÕES ========

    public static void registrarMovimentacao(Connection conn, Scanner sc)
            throws SQLException {

        System.out.println("ID do item:");
        int itemId = sc.nextInt();

        System.out.println("ID do usuario:");
        int usuarioId = sc.nextInt();

        System.out.println("ID do fornecedor (0 se nao houver):");
        int fornecedorId = sc.nextInt();
        sc.nextLine();

        System.out.println("Tipo (Entrada ou Saida):");
        String tipo = sc.nextLine();

        System.out.println("Quantidade movimentada:");
        int quantidade = sc.nextInt();
        sc.nextLine();

        System.out.println("Destino do item:");
        String destino = sc.nextLine();

        System.out.println("Observacoes:");
        String observacoes = sc.nextLine();

        // Buscar quantidade atual do item
        String consulta = "SELECT quantidade FROM itens_estoque WHERE id = ?";

        PreparedStatement psConsulta =
                conn.prepareStatement(consulta);

        psConsulta.setInt(1, itemId);

        ResultSet rs = psConsulta.executeQuery();

        if (!rs.next()) {
            System.out.println("Item nao encontrado!");
            rs.close();
            psConsulta.close();
            return;
        }

        int quantidadeAtual = rs.getInt("quantidade");

        rs.close();
        psConsulta.close();

        int novaQuantidade;

        if (tipo.equalsIgnoreCase("Entrada")) {

            novaQuantidade = quantidadeAtual + quantidade;

        } else {

            if (quantidade > quantidadeAtual) {
                System.out.println("Estoque insuficiente!");
                return;
            }

            novaQuantidade = quantidadeAtual - quantidade;
        }

        // Atualiza estoque
        String atualiza =
                "UPDATE itens_estoque SET quantidade = ? WHERE id = ?";

        PreparedStatement psAtualiza =
                conn.prepareStatement(atualiza);

        psAtualiza.setInt(1, novaQuantidade);
        psAtualiza.setInt(2, itemId);

        psAtualiza.executeUpdate();

        psAtualiza.close();

        // Registra movimentacao
        String sql =
                "INSERT INTO movimentacoes_estoque (" +
                        "item_id, usuario_id, fornecedor_id, " +
                        "tipo_movimentacao, quantidade_movimentada, " +
                        "destino_item, observacoes) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps =
                conn.prepareStatement(sql);

        ps.setInt(1, itemId);
        ps.setInt(2, usuarioId);

        if (fornecedorId == 0) {
            ps.setNull(3, Types.INTEGER);
        } else {
            ps.setInt(3, fornecedorId);
        }

        ps.setString(4, tipo);
        ps.setInt(5, quantidade);
        ps.setString(6, destino);
        ps.setString(7, observacoes);

        ps.executeUpdate();

        System.out.println("Movimentacao registrada!");

        ps.close();
    }

    public static void consultarMovimentacoes(Connection conn)
            throws SQLException {

        String sql =
                "SELECT * FROM movimentacoes_estoque " +
                        "ORDER BY data_movimentacao DESC";

        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {

            int id = rs.getInt("id");
            int itemId = rs.getInt("item_id");
            int usuarioId = rs.getInt("usuario_id");

            Integer fornecedorId =
                    (Integer) rs.getObject("fornecedor_id");

            String tipo =
                    rs.getString("tipo_movimentacao");

            int quantidade =
                    rs.getInt("quantidade_movimentada");

            String destino =
                    rs.getString("destino_item");

            Timestamp data =
                    rs.getTimestamp("data_movimentacao");

            String observacoes =
                    rs.getString("observacoes");

            System.out.printf(
                    "[%d] Item:%d | Usuario:%d | Fornecedor:%s | Tipo:%s | Quantidade:%d | Destino:%s | Data:%s | Obs:%s%n",
                    id,
                    itemId,
                    usuarioId,
                    fornecedorId,
                    tipo,
                    quantidade,
                    destino,
                    data,
                    observacoes
            );
        }

        rs.close();
        stmt.close();
    }

    public static void cancelarMovimentacao(Connection conn, Scanner sc)
            throws SQLException {

        System.out.println("Informe o ID da movimentacao:");
        int movimentacaoId = sc.nextInt();
        sc.nextLine();

        // Buscar movimentacao
        String buscaMov =
                "SELECT item_id, tipo_movimentacao, quantidade_movimentada " +
                        "FROM movimentacoes_estoque WHERE id = ?";

        PreparedStatement psBusca =
                conn.prepareStatement(buscaMov);

        psBusca.setInt(1, movimentacaoId);

        ResultSet rs = psBusca.executeQuery();

        if (!rs.next()) {
            System.out.println("Movimentacao nao encontrada!");
            rs.close();
            psBusca.close();
            return;
        }

        int itemId = rs.getInt("item_id");
        String tipo = rs.getString("tipo_movimentacao");
        int quantidade = rs.getInt("quantidade_movimentada");

        rs.close();
        psBusca.close();

        // Buscar estoque atual
        String buscaEstoque =
                "SELECT quantidade FROM itens_estoque WHERE id = ?";

        PreparedStatement psEstoque =
                conn.prepareStatement(buscaEstoque);

        psEstoque.setInt(1, itemId);

        ResultSet rsEstoque = psEstoque.executeQuery();

        if (!rsEstoque.next()) {
            System.out.println("Item nao encontrado!");
            rsEstoque.close();
            psEstoque.close();
            return;
        }

        int estoqueAtual = rsEstoque.getInt("quantidade");

        rsEstoque.close();
        psEstoque.close();

        int novoEstoque;

        if (tipo.equalsIgnoreCase("Entrada")) {

            if (estoqueAtual < quantidade) {
                System.out.println(
                        "Nao e possivel cancelar. Estoque inconsistente."
                );
                return;
            }

            novoEstoque = estoqueAtual - quantidade;

        } else {

            novoEstoque = estoqueAtual + quantidade;
        }

        // Atualizar estoque
        String atualiza =
                "UPDATE itens_estoque SET quantidade = ? WHERE id = ?";

        PreparedStatement psAtualiza =
                conn.prepareStatement(atualiza);

        psAtualiza.setInt(1, novoEstoque);
        psAtualiza.setInt(2, itemId);

        psAtualiza.executeUpdate();

        psAtualiza.close();

        // Excluir movimentacao
        String delete =
                "DELETE FROM movimentacoes_estoque WHERE id = ?";

        PreparedStatement psDelete =
                conn.prepareStatement(delete);

        psDelete.setInt(1, movimentacaoId);

        int linhasAfetadas = psDelete.executeUpdate();

        if (linhasAfetadas > 0) {
            System.out.println("Movimentacao cancelada com sucesso!");
            System.out.println("Estoque atualizado para: " + novoEstoque);
        } else {
            System.out.println("Erro ao cancelar movimentacao.");
        }

        psDelete.close();
    }

    public static void exibirMenu() {

        System.out.println("\n===== SISTEMA DE ESTOQUE =====");

        System.out.println("\n--- USUARIOS ---");
        System.out.println("1 - Cadastrar usuario");
        System.out.println("2 - Consultar usuarios");
        System.out.println("3 - Alterar usuario");

        System.out.println("\n--- CATEGORIAS ---");
        System.out.println("4 - Cadastrar categoria");
        System.out.println("5 - Consultar categorias");

        System.out.println("\n--- ITENS DO ESTOQUE ---");
        System.out.println("6 - Cadastrar item");
        System.out.println("7 - Consultar itens");
        System.out.println("8 - Alterar item");

        System.out.println("\n--- FORNECEDORES ---");
        System.out.println("9 - Cadastrar fornecedor");
        System.out.println("10 - Consultar fornecedores");
        System.out.println("11 - Alterar fornecedor");

        System.out.println("\n--- MOVIMENTACOES ---");
        System.out.println("12 - Registrar movimentacao");
        System.out.println("13 - Consultar movimentacoes");
        System.out.println("14 - Cancelar movimentacao");

        System.out.println("\n0 - Sair");

        System.out.print("Opcao: ");
    }

    public static void processarOpcao(
            Connection conn,
            Scanner sc,
            int opcao) throws SQLException {

        switch (opcao) {

            // USUARIOS
            case 1:
                cadastrarUsuario(conn, sc);
                break;

            case 2:
                consultarUsuarios(conn);
                break;

            case 3:
                alterarUsuario(conn, sc);
                break;

            // CATEGORIAS
            case 4:
                cadastrarCategoria(conn, sc);
                break;

            case 5:
                consultarCategorias(conn);
                break;

            // ITENS
            case 6:
                cadastrarItem(conn, sc);
                break;

            case 7:
                consultarItens(conn);
                break;

            case 8:
                alterarItem(conn, sc);
                break;

            // FORNECEDORES
            case 9:
                cadastrarFornecedor(conn, sc);
                break;

            case 10:
                consultarFornecedores(conn);
                break;

            case 11:
                alterarFornecedor(conn, sc);
                break;

            // MOVIMENTACOES
            case 12:
                registrarMovimentacao(conn, sc);
                break;

            case 13:
                consultarMovimentacoes(conn);
                break;

            case 14:
                cancelarMovimentacao(conn, sc);
                break;

            case 0:
                System.out.println("Sistema encerrado.");
                break;

            default:
                System.out.println("Opcao invalida.");
        }
    }

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/iansa";
        try {

            Connection conn = DriverManager.getConnection(
                    url, "postgres", "bungas");
            System.out.println("Conexão com sucesso");

            usuarios(conn);
            categorias(conn);
            estoqueItens(conn);
            fornecedores(conn);
            movimentacoesEstoque(conn);

            Scanner sc = new Scanner(System.in);

            int opcao;

            do {

                exibirMenu();

                opcao = sc.nextInt();
                sc.nextLine();

                processarOpcao(conn, sc, opcao);

            } while (opcao != 0);

            sc.close();
            conn.close();

        } catch (SQLException e) {

            System.out.println("Erro ao conectar com o banco: " + e.getMessage());
        }
    }




}






