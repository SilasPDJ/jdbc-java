package org.example;

import java.sql.Connection;
import java.sql.SQLException;

public class CriarBanco {
    public static void main(String[] args) throws SQLException {
        final Connection connection = ConnectionFactory.getConnection();
    }
}
