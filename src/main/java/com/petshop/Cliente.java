package com.petshop;

import java.util.ArrayList;
import java.util.List;
//import java.sql.Connection; //Bibliotecas de acesso ao banco de dados
//import java.sql.DriverManager; //Descomentar quando for implementar o acesso ao banco

public class Cliente {
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