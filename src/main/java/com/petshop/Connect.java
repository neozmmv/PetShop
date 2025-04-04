package com.petshop;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/* IMPORTANTE:
Só vai funcionar se tiver o arquivo mysql-connector.j.9.2.0.jar na pasta lib. 
Compilar usando: 
javac -cp "libs/*;." -d bin src/*.java
Rodar usando: 
java -cp "libs/*;bin" Connect 
*/

public class Connect {

    private String url;
    private String user;
    private String password;

    // PORTA PADRÃO MYSQL = 3306

    private static Connection conn;

    // Funções Setters

    /*
     * public void setUrl(String url) {
     * this.url = url;
     * }
     */

    public void mount_Url(String ip, int port, String database) {
        this.url = "jdbc:mysql://" + ip + ":" + Integer.toString(port) + "/" + database;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection getConnection() {
        try {
            if (conn == null) {
                conn = DriverManager.getConnection(url, user, password);
                return conn;
            } else {
                return conn;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    //Função para BUSCAR informações no banco.
    public ResultSet executeQuery(String query, Boolean show_result) {
        try {
            if (conn == null) {
                System.err.println("Erro: Conexão não estabelecida. Chame getConnection() primeiro.");
                return null;
            }

            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);

            if (show_result) {
                while (resultSet.next()) {
                    System.out.println(resultSet.getString(1)); // Exibe a primeira coluna
                }
            }

            return stmt.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getIntByQuery(String query) {
        try {
            if (conn == null) {
                System.err.println("Erro: Conexão não estabelecida. Chame getConnection() primeiro.");
                return -1;
            }

            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);

            // Verifica se há pelo menos uma linha no ResultSet
            if (resultSet.next()) {
                // Retorna o valor da primeira coluna da primeira linha
                return resultSet.getInt(1);
            } else {
                //Retorna -1 se não tiver nenhum resultado
                System.out.println("Nenhum resultado encontrado para a consulta.");
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; //Retorna -1 se der erro.
        }
    }

    public String getStringByQuery(String query) {
        try {
            if (conn == null) {
                System.err.println("Erro: Conexão não estabelecida. Chame getConnection() primeiro.");
                return "";
            }

            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);

            //Mesma lógica do getIntByQuery()
            if (resultSet.next()) {
                return resultSet.getString(1);
            } else {
                System.out.println("Nenhum resultado encontrado para a consulta.");
                return "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }


    //Função para INSERIR ou ATUALIZAR informações do banco.
    public int executeUpdate(String query, Boolean show_result) {
        try {
            if (conn == null) {
                System.err.println("Erro: Conexão não estabelecida. Chame getConnection() primeiro.");
                return -1;
            }

            Statement stmt = conn.createStatement();
            int rowsAffected = stmt.executeUpdate(query);

            if (show_result) {
                System.out.println("Linhas afetadas: " + rowsAffected);
            }

            stmt.close();
            return rowsAffected;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    // printa o resultado da query

    // Função main dessa classe, só para ver o funcionamento, usar isso na funço
    // main do arquivo principal.
    public static void main(String args[]) {
        // Criando o objeto e configurando
        // informações não funcionais, só para visualização
        Connect connect = new Connect();
        connect.setUser("usuario_do_banco");
        connect.setPassword("SENHA DO USUARIO");
        connect.mount_Url("IP DO SERVIDOR", 3306, "db_NOME");
        System.out.println(connect.getConnection());
    }
}