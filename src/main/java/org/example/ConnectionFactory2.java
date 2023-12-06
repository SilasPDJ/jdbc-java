package org.example;

import java.sql.*;

public class ConnectionFactory2 {
    private final String databaseName;
    private final Connection connection;

    public ConnectionFactory2(String databaseName) {
        this.databaseName = databaseName;
        this.connection = createConnection();

    }


    public ResultSet executeQuery(String query, String... parameters) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set parameters if any
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setString(i + 1, parameters[i]);
            }
            // Execute the query and return the ResultSet
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int executeStatementCommand(String query, String... parameters) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set parameters if any
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setString(i + 1, parameters[i]);
            }

            // Execute the update and return the number of affected rows
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //    public Connection getConnection() {
//        return connection;
//    }
//
    private Connection createConnection() {
        final String url = "jdbc:mysql://localhost/" + databaseName;
        final String usuario = "root";
        final String senha = "";

        try {
            // System.out.println("Conexão efetuada com sucesso");
            return DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
