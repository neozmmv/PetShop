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

//TODAS AS FUNCOES DO BANCO DE DADOS DEVEM ESTAR AQUI!
public class Connect {

    private String url;
    private String user;
    private String password;

    // PORTA PADRÃO MYSQL = 3306

    private Connection conn;

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

    public boolean inserirConsulta(Cliente cliente, Pet pet, DatePicker datapicker, Veterinario veterinario, boolean urgente, double precoTotal) {
        String sql = "INSERT INTO consulta (nome_cliente, nome_pet, data_servico, veterinario, urgente, valor_consulta) VALUES (?, ?, ?, ?, ?, ?)";
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
            pstmt.setString(4, veterinario.getNome());
            pstmt.setBoolean(5, urgente);
            pstmt.setDouble(6, precoTotal);

            int rows = pstmt.executeUpdate();
            pstmt.close();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean inserirServico(Cliente cliente, Pet pet, DatePicker datapicker, String tipo_servico, double precoTotal) {
        String sql = "INSERT INTO servicos (nome_cliente, nome_pet, data, tipo_servico, valor_servico) VALUES (?, ?, ?, ?, ?)";
        java.sql.Date data = java.sql.Date.valueOf(datapicker.getValue()); //converte do datapicker para um valor armazenavel no banco

        try {
            Connection conn = getConnection();
            if (conn == null) {
                System.err.println("Erro: conexão não estabelecida.");
                throw new SQLException("Conexão não estabelecida");
            }

            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, pet.getNome());
            pstmt.setDate(3, data);
            pstmt.setString(4, tipo_servico);
            pstmt.setDouble(5, precoTotal);


            int rows = pstmt.executeUpdate();
            pstmt.close();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean inserirHospedagem(Cliente cliente, Pet pet, DatePicker datapicker, int numeroDias, boolean suiteLuxo, boolean servicoSpa, boolean alimentacao, double precoTotal) {
        String sql = "INSERT INTO hospedagem (nome_cliente, nome_pet, data_servico, numero_dias, suite_luxo, servico_spa, alimentacao_especial, valor_servico) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
            pstmt.setInt(4, numeroDias);
            pstmt.setBoolean(5, suiteLuxo);
            pstmt.setBoolean(6, servicoSpa);
            pstmt.setBoolean(7, alimentacao);
            pstmt.setDouble(8, precoTotal);

            int rows = pstmt.executeUpdate();
            pstmt.close();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean inserirAdestramento(Cliente cliente, Pet pet, DatePicker datapicker, int numeroSessoes, String tipoTreinamento, boolean domiciliar, double precoTotal) {
        String sql = "INSERT INTO adestramento (nome_cliente, nome_pet, data_servico, numero_sessoes, tipo_treinamento, domiciliar, valor_servico) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
            pstmt.setInt(4, numeroSessoes);
            pstmt.setString(5, tipoTreinamento);
            pstmt.setBoolean(6, domiciliar);
            pstmt.setDouble(7, precoTotal);

            int rows = pstmt.executeUpdate();
            pstmt.close();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getId(Cliente cliente) {
        String sql = "SELECT id FROM clientes WHERE NOME = ? and TELEFONE = ?";

        try {
            if (conn == null) {
                System.err.println("Erro: conexão não estabelecida.");
                return -1;
            }

            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getTelefone());

            ResultSet rs = pstmt.executeQuery();
            int id = -1;

            if (rs.next()) {
                id = rs.getInt("id");
            }

            rs.close();
            pstmt.close();
            return id;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    //puxar todos os clientes do banco e retornar um array com eles.
    public List<Cliente> getTodosClientes() {
        List<Cliente> lista = new ArrayList<>();

        try {
            if (conn == null) {
                System.err.println("Erro: Conexão não estabelecida. Chame getConnection() primeiro.");
                throw new SQLException("Conexão não estabelecida");
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

    public List<ServicoGenerico> getTodosServicos() {
        List<ServicoGenerico> lista = new ArrayList<>();

        try {
            if (conn == null) {
                System.err.println("Erro: Conexão não estabelecida. Chame getConnection() primeiro.");
                throw new SQLException("Conexão não estabelecida");
            }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select nome_cliente, nome_pet, data, tipo_servico, valor_servico FROM servicos");

            while (rs.next()) {
                ServicoGenerico servico = new ServicoGenerico(
                        rs.getString("nome_cliente"),
                        rs.getString("nome_pet"),
                        rs.getDate("data"),
                        rs.getString("tipo_servico"),
                        rs.getDouble("valor_servico")
                );
                lista.add(servico);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean inserirVeterinario(Veterinario vet) {
        String sql = "INSERT INTO veterinarios (nome, crmv, especialidade, valor_consulta) VALUES (?, ?, ?, ?)";

        try {
            Connection conn = getConnection();
            if (conn == null) {
                System.err.println("Erro: conexão não estabelecida.");
                throw new SQLException("Conexão não estabelecida");
            }

            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, vet.getNome());
            pstmt.setString(2, vet.getCrmv());
            pstmt.setString(3, vet.getEspecialidade());
            pstmt.setDouble(4, vet.getValorConsulta());
            

            int rows = pstmt.executeUpdate();
            pstmt.close();

            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Veterinario> getTodosVeterinarios() {
        List<Veterinario> lista = new ArrayList<>();

        try {
            if (conn == null) {
                System.err.println("Erro: Conexão não estabelecida. Chame getConnection() primeiro.");
                throw new SQLException("Conexão não estabelecida");
            }

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nome, crmv, especialidade, valor_consulta FROM veterinarios");

            while (rs.next()) {
                Veterinario vet = new Veterinario(
                        rs.getString("nome"),
                        rs.getString("crmv"),
                        rs.getString("especialidade"),
                        rs.getDouble("valor_consulta")
                );
                lista.add(vet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean deleteClientePorId(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try {
            Connection conn = getConnection();
            if (conn == null) {
                System.err.println("Erro: conexão não estabelecida.");
                return false;
            }

            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            int rows = pstmt.executeUpdate();
            pstmt.close();

            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //inserção do pet na tabela pets do banco.
    public boolean inserirPet(Pet pet) {
        String sql = "INSERT INTO pets (nome, nome_dono, especie, raca, idade, peso) VALUES (?, ?, ?, ?, ?, ?)";

        try {

            Connection conn = getConnection();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
            /* if (conn == null) {
                System.err.println("Erro: conexão não estabelecida.");
                return false;
            } */

            // Definir os parâmetros da consulta
            pstmt.setString(1, pet.getNome());
            pstmt.setString(2, pet.getDono().getNome());
            pstmt.setString(3, pet.getEspecie());
            pstmt.setString(4, pet.getRaca());
            pstmt.setInt(5, pet.getIdade());
            pstmt.setDouble(6, pet.getPeso());

            // Executar a inserção no banco
            int rows = pstmt.executeUpdate();
            pstmt.close();

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
            FileReader fileReader = new FileReader("server.conf");
            Connect connection = new Connect();
            connection.setUser(fileReader.get_User());
            connection.setPassword(fileReader.get_Password());
            connection.mount_Url(fileReader.get_Host(), fileReader.get_Port(), fileReader.get_Database());
        System.out.println(connection.getConnection());
        connection.deleteClientePorId(3);
    }
}