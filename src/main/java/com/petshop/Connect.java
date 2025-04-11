package com.petshop;

import javafx.scene.control.DatePicker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

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

    //inserir o cliente criado no banco.
    public boolean inserirCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, cpf, telefone, endereco, email) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = getConnection();
            if (conn == null) {
                System.err.println("Erro: conexão não estabelecida.");
                return false;
            }

            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getCpf());
            pstmt.setString(3, cliente.getTelefone());
            pstmt.setString(4, cliente.getEndereco());
            pstmt.setString(5, cliente.getEmail());

            int rows = pstmt.executeUpdate();
            pstmt.close();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean inserirBanhoTosa(Cliente cliente, Pet pet, DatePicker datapicker, boolean tosa_profunda, boolean perfume, boolean gravata, double valor) {
        String sql = "INSERT INTO banho_tosa (nome_cliente, nome_pet, data_servico, tosa_profunda, perfume, gravata, valor_servico) VALUES (?, ?, ?, ?, ?, ?, ?)";
        java.sql.Date data = java.sql.Date.valueOf(datapicker.getValue()); //converte do datapicker para um valor armazenavel no banco

        try {
            Connection conn = getConnection();
            if (conn == null) {
                System.err.println("Erro: conexão não estabelecida.");
                return false;
            }

            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, pet.getNome());
            pstmt.setDate(3, data);
            pstmt.setBoolean(4, tosa_profunda);
            pstmt.setBoolean(5, perfume);
            pstmt.setBoolean(6, gravata);
            pstmt.setDouble(7, valor);

            int rows = pstmt.executeUpdate();
            pstmt.close();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //puxar todos os clientes do banco e retornar um array com eles.
    public List<Cliente> getTodosClientes() {
        List<Cliente> lista = new ArrayList<>();

        try {
            if (conn == null) {
                System.err.println("Erro: Conexão não estabelecida. Chame getConnection() primeiro.");
                return lista;
            }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nome, cpf, telefone, endereco, email FROM clientes");

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone"),
                        rs.getString("endereco")
                );
                cliente.setEmail(rs.getString("email"));
                lista.add(cliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    //inserção do pet na tabela pets do banco.
    public boolean inserirPet(Pet pet) {
        String sql = "INSERT INTO pets (nome, nome_dono, especie, raca, idade, peso) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null) {
                System.err.println("Erro: conexão não estabelecida.");
                return false;
            }

            // Definir os parâmetros da consulta
            pstmt.setString(1, pet.getNome());
            pstmt.setString(2, pet.getDono().getNome());
            pstmt.setString(3, pet.getEspecie());
            pstmt.setString(4, pet.getRaca());
            pstmt.setInt(5, pet.getIdade());
            pstmt.setDouble(6, pet.getPeso());

            // Executar a inserção no banco
            int rows = pstmt.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Pet> getPetsByClientName(String nome_cliente) {
        List<Pet> lista = new ArrayList<>();
        java.sql.PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Connection conn = getConnection();

            if (conn == null) {
                System.err.println("Erro: Conexão não estabelecida. Chame getConnection() primeiro.");
                return lista;
            }

            String sql = "SELECT nome, nome_dono, especie, raca, idade, peso FROM pets WHERE nome_dono = ?";
            pstmt = conn.prepareStatement(sql);

            // Passando o nome do dono como parâmetro do ?
            pstmt.setString(1, nome_cliente);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Pet pet = new Pet(
                        rs.getString("nome"),
                        rs.getString("especie"),
                        rs.getString("raca"),
                        rs.getDouble("peso"),
                        rs.getInt("idade")
                );

                Cliente dono = new Cliente(rs.getString("nome_dono"));
                pet.setDono(dono);  // Associando o dono ao pet

                // Adicionando o pet à lista
                lista.add(pet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fechando os recursos para evitar vazamento
            /*try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }*/
        }

        return lista;
    }





    //Função para retornar uma lista com todos os pets
    public List<Pet> getTodosPets() {
        List<Pet> lista = new ArrayList<>();

        try {
            if (conn == null) {
                System.err.println("Erro: Conexão não estabelecida. Chame getConnection() primeiro.");
                return lista;
            }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nome, nome_dono, especie, raca, idade, peso from pets;");

            while (rs.next()) {
                Pet pet = new Pet(
                        rs.getString("nome"),
                        rs.getString("especie"),
                        rs.getString("raca"),
                        rs.getDouble("peso"),
                        rs.getInt("idade")
                );
                Cliente dono = new Cliente(rs.getString("nome_dono"));
                pet.setDono(dono);
                lista.add(pet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
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