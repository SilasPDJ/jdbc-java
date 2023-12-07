package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ConnectionFactory2 {
    private final String databaseName;
    private final Connection connection;

    public ConnectionFactory2(String databaseName) {
        this.databaseName = databaseName;
        this.connection = createConnection();

    }

    private static Properties getProperties() throws IOException {
        Properties prop = new Properties();
        String caminho = "O:\\HACKING\\IdeaProjects\\JDBC\\src\\main\\java\\org\\example\\desafios\\connection.properties";

        try (FileInputStream input = new FileInputStream(caminho)) {
            prop.load(input);
        }

        return prop;
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

    public void executeStatementCommand(String query, String... parameters) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set parameters if any
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setString(i + 1, parameters[i]);
            }

            // Execute the update and return the number of affected rows
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection createConnection() {
        try {
            Properties props = getProperties();

            final String url = props.getProperty("db.url") + databaseName;
            final String usuario = props.getProperty("db.user");
            final String senha = props.getProperty("db.password");

            // System.out.println("Conexão efetuada com sucesso");
            return DriverManager.getConnection(url, usuario, senha);


        } catch (SQLException | IOException e) {
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
