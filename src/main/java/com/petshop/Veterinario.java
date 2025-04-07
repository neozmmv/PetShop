package com.petshop;

public class Veterinario {
    private String nome;
    private String crmv;
    private String especialidade;
    private double valorConsulta;

    public Veterinario(String nome, String crmv, String especialidade, double valorConsulta) {
        this.nome = nome;
        this.crmv = crmv;
        this.especialidade = especialidade;
        this.valorConsulta = valorConsulta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCrmv() {
        return crmv;
    }

    public void setCrmv(String crmv) {
        this.crmv = crmv;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public double getValorConsulta() {
        return valorConsulta;
    }

    public void setValorConsulta(double valorConsulta) {
        this.valorConsulta = valorConsulta;
    }

    @Override
    public String toString() {
        return "Veterinário: " + nome + 
               "\nCRMV: " + crmv + 
               "\nEspecialidade: " + especialidade + 
               "\nValor da Consulta: R$ " + valorConsulta;
    }
} 