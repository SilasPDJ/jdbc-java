package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class NovaPessoa {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connection connection = ConnectionFactory.getConnection();

        System.out.println("Informe o nome da pessoa: ");
        String nome = scanner.nextLine();
        String sql;
        // sql = "INSERT INTO pessoas (nome) VALUES('" + nome + "')";
        // isso pode dar sql injection
        sql = "INSERT INTO pessoas (nome) VALUES (?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, nome);
        stmt.execute();

        System.out.println(nome + " inserido(a) com sucesso");

        scanner.close();

    }
}
