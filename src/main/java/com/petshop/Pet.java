package com.petshop;

public class Pet {
    private String nome;
    private String especie;
    private String raca;
    private int idade;
    private double peso;
    private Cliente dono;
    
    public Pet(String nome, String raca, double peso, int idade) {
        this.nome = nome;
        this.especie = "";
        this.raca = raca;
        this.idade = idade;
        this.peso = peso;
        this.dono = null;
    }
    //crie a classe pet e as funções básicas
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getEspecie() {
        return especie;
    }
    
    public void setEspecie(String especie) {
        this.especie = especie;
    }
    
    public String getRaca() {
        return raca;
    }
    
    public void setRaca(String raca) {
        this.raca = raca;
    }
    
    public int getIdade() {
        return idade;
    }
    
    public void setIdade(int idade) {
        this.idade = idade;
    }
    
    public double getPeso() {
        return peso;
    }
    
    public void setPeso(double peso) {
        this.peso = peso;
    }
    
    public Cliente getDono() {
        return dono;
    }
    
    public void setDono(Cliente dono) {
        this.dono = dono;
    }

    @Override
    public String toString() {
        return "Pet: " + nome + ", Espécie: " + especie + ", Raça: " + raca + 
               ", Idade: " + idade + ", Peso: " + peso + "kg";
    }
}
