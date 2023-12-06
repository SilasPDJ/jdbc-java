package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ConsultarPessoas1 {
    public static void main(String[] args) throws SQLException {
        ConnectionFactory2 cnx = new ConnectionFactory2("curso_java");
        ResultSet results1 = cnx.executeQuery("SELECT * FROM PESSOAS");

        List<Pessoa> pessoas = new ArrayList<>();

        while (results1.next()) {
            int codigo = results1.getInt("codigo");
            String nome = results1.getString("nome");

            pessoas.add(new Pessoa(codigo, nome));
        }
        Consumer<Pessoa> exibirInformacoes = pessoa -> {
            System.out.println("Código: " + pessoa.getCodigo() + ", Nome: " + pessoa.getNome());
        };
        pessoas.forEach(exibirInformacoes);

        cnx.closeConnection();
    }
}
