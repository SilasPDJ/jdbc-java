package org.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTablePessoas {
    public static void main(String[] args) throws SQLException {
        Connection conexao = ConnectionFactory.getConnection();

        String sql = "CREATE TABLE IF NOT EXISTS pessoas ("
                + "codigo INT AUTO_INCREMENT PRIMARY KEY,"
                + "nome VARCHAR(80) NOT NULL"
                + ")";

        Statement stmt = conexao.createStatement();
        stmt.execute(sql);

        System.out.println("Tabela criada com sucesso!");


    }
}
