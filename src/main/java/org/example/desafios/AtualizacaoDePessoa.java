package org.example.desafios;

import org.example.ConnectionFactory2;
import org.example.Pessoa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class AtualizacaoDePessoa {
    static private final Scanner scanner = new Scanner(System.in);
    final private ConnectionFactory2 connection = new ConnectionFactory2("curso_java");

    AtualizacaoDePessoa() {
        while (run()) continue;
        connection.closeConnection();
    }

    public static void main(String[] args) {
        new AtualizacaoDePessoa();
    }

    private boolean run() {
        String nome, novoNome, codigo, operacaoInicial;
        int operacao;
        List<String> codigosList;

        exibirPessoas();
        System.out.println("Digite qualquer caracter para continuar alterando registros...");
        operacaoInicial = criarPergunta("[ENTER] para criar novo registro ou [999] para sair: ");
        if (operacaoInicial.equals("")) {
            System.out.print("Qual o nome? ");
            connection.executeStatementCommand("INSERT INTO PESSOAS (NOME) VALUES (?)", scanner.nextLine());
        } else if (operacaoInicial.equals("999")) {
            return false;

        } else {
            do {
                nome = criarPergunta("Procure por um nome qualquer: ");
                codigosList = getCodigoValuesFromQuery(callInitialQuery(nome));

                if (codigosList.size() == 0) {
                    System.out.println("Nenhum resultado encontrado. Tente novamente.");
                }
            } while (codigosList.size() == 0);
            exibirPessoas(callInitialQuery(nome));

            codigo = criarPergunta("Selecione uma opção: ");
            boolean isCodigoEncontrado = verificarCodigoExistente(codigosList, codigo);

            while (!isCodigoEncontrado) {
                System.out.println("Tente novamente!");
                codigo = criarPergunta("Selecione uma opção: ");
                codigosList = getCodigoValuesFromQuery(callInitialQuery(nome));
                isCodigoEncontrado = verificarCodigoExistente(codigosList, codigo);
            }

            boolean operacaoInvalida = true;
            operacao = 0;
            while (operacaoInvalida) {
                operacao = Integer.parseInt(criarPergunta("Digite [1] ATUALIZAR NOME. [2] EXCLUIR: "));
                operacaoInvalida = operacao != 1 && operacao != 2;
            }
            switch (operacao) {
                case 1 -> {
                    nome = criarPergunta("DIgite o novo NOME da opção selecionada: ");
                    connection.executeStatementCommand("UPDATE PESSOAS SET NOME=(?)  WHERE codigo=(?)", nome, codigo);
                }
                case 2 -> connection.executeStatementCommand("DELETE FROM PESSOAS WHERE codigo=(?)", codigo);
            }
        }
        return true;
    }

    private ResultSet callInitialQuery(String nome) {
        return connection.executeQuery("SELECT * FROM pessoas WHERE nome LIKE ?", "%" + nome + "%");
    }


    private List<String> getCodigoValuesFromQuery(ResultSet initialQuery) {
        List<String> codigosList = new ArrayList<>();

        try {
            while (initialQuery.next()) {
                String codigoNaConsulta = initialQuery.getString("codigo");
                codigosList.add(codigoNaConsulta);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return codigosList;
    }

    private boolean verificarCodigoExistente(List<String> codigosList, String codigo) {
        return codigosList.stream().anyMatch(codigo::equals);
    }

    private String criarPergunta(String pergunta) {
        System.out.print(pergunta);
        String resposta = scanner.nextLine();
        System.out.println();

        return resposta;
    }

    void exibirPessoas() {
        try {

            ResultSet results = connection.executeQuery("SELECT * FROM PESSOAS");

            List<Pessoa> pessoas = new ArrayList<>();

            while (results.next()) {
                int codigo = results.getInt("codigo");
                String nome = results.getString("nome");

                pessoas.add(new Pessoa(codigo, nome));
            }
            Consumer<Pessoa> exibirInformacoes = pessoa -> {
                System.out.println("Código: " + pessoa.getCodigo() + ", Nome: " + pessoa.getNome());
            };
            pessoas.forEach(exibirInformacoes);
            System.out.println();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao exibir pessoas.", e);
        }
    }

    void exibirPessoas(ResultSet results) {
        try {
            List<Pessoa> pessoas = new ArrayList<>();

            while (results.next()) {
                int codigo = results.getInt("codigo");
                String nome = results.getString("nome");

                pessoas.add(new Pessoa(codigo, nome));
            }
            Consumer<Pessoa> exibirInformacoes = pessoa -> {
                System.out.println("Código: " + pessoa.getCodigo() + ", Nome: " + pessoa.getNome());
            };
            pessoas.forEach(exibirInformacoes);
            System.out.println();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao exibir pessoas.", e);
        }
    }
}
