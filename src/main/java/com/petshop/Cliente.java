package com.petshop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
//import java.sql.Connection; //Bibliotecas de acesso ao banco de dados
//import java.sql.DriverManager; //Descomentar quando for implementar o acesso ao banco

public class Cliente {
    private int id; //id do cliente no banco de dados.
    private String nome;
    private String cpf;
    private String telefone;
    private String endereco;
    private String email;
    private List<Pet> pets;
    // adicionei a lista de pets e importei as bibliotecas pra isso

    // Construtor do cliente
    public Cliente(String nome, String cpf, String telefone, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        this.email = "";
        this.pets = new ArrayList<>();
    }

    // Funções de pegar informação (cliente.nome(), cliente.email(), etc...)
    // adicionei as funções relacionadas à classe pet e ao cliente
    public void adicionarPet(Pet pet) {
        pets.add(pet);
    }

    public int getId() {
        String sql = "SELECT id FROM clientes WHERE NOME = ? and TELEFONE = ?";

        try {
            Connect connection = new Connect();
            Connection conn = connection.getConnection();
            if (conn == null) {
                System.err.println("Erro: conexão não estabelecida.");
                return -1;
            }

            java.sql.PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nome);
            pstmt.setString(2, telefone);

            ResultSet rs = pstmt.executeQuery();  // <- Aqui é a correção principal
            int id = -1;

            if (rs.next()) {
                id = rs.getInt("id");
            }

            rs.close();
            pstmt.close();
            conn.close();

            return id;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void removerPet(Pet pet) {
        pets.remove(pet);
    }

    public List<Pet> getPets() {
        return pets;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Cliente: " + nome + 
               "\nCPF: " + cpf + 
               "\nTelefone: " + telefone + 
               "\nEndereço: " + endereco + 
               "\nEmail: " + email;
    }

    /*
     * public static void main(String args[]) {
     * // TESTE
     * Cliente cliente = new Cliente("Enzo", "123456789", "enzo@teste.com");
     * System.out.println(cliente.nome());
     * System.out.println(cliente.telefone());
     * System.out.println(cliente.email());
     * }
     * 
     */
}