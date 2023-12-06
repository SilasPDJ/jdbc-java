package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class ConnectionFactory implements Connection {
    public static Connection getConnection() {
        final String url = "jdbc:mysql://localhost/curso_java";
        final String usuario = "root";
        final String senha = "";

        Connection conexao = null;
        try {
            conexao = DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Conexão efetuada com sucesso");
//        Statement connection = conexao.createStatement();
        return conexao;
    }

    public static Statement getStatement(Connection connection) throws SQLException {
        return connection.createStatement();
    }

}
