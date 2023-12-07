package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public abstract class ConnectionFactory implements Connection {
    public static Connection getConnection() {
        Connection conexao;
        try {
            Properties prop = getProperties();
            conexao = DriverManager.getConnection(prop.getProperty("db.url"),
                    prop.getProperty("db.user"),
                    prop.getProperty("db.password"));
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Conexão efetuada com sucesso");
//        Statement connection = conexao.createStatement();
        return conexao;
    }

    private static Properties getProperties() throws IOException {
        Properties prop = new Properties();
        String caminho = "O:\\HACKING\\IdeaProjects\\JDBC\\src\\main\\java\\org\\example\\desafios\\connection.properties";

        try (FileInputStream input = new FileInputStream(caminho)) {
            prop.load(input);
        }

        return prop;
    }
    

}
