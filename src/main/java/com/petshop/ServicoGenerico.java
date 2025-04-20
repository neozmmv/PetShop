package com.petshop;

import java.time.LocalDateTime;
import java.sql.Date;

public class ServicoGenerico {
    
    private String nome_dono;
    private String nome_pet;
    private LocalDateTime dataAgendamento;
    private String tipo_servico;
    private double valor_servico;

    //construtor para montar o servico para mostrar na tabela
    public ServicoGenerico(String nome_dono, String nome_pet, Date data, String tipo_servico, double valor_servico) {
        this.nome_dono = nome_dono;
        this.nome_pet = nome_pet;
        this.dataAgendamento = data.toLocalDate().atStartOfDay();
        this.tipo_servico = tipo_servico;
        this.valor_servico = valor_servico;
    }
    
    // Getters and setters
    public String getNome_dono() {
        return nome_dono;
    }
    
    public void setNome_dono(String nome_dono) {
        this.nome_dono = nome_dono;
    }
    
    public String getNome_pet() {
        return nome_pet;
    }
    
    public void setNome_pet(String nome_pet) {
        this.nome_pet = nome_pet;
    }
    
    public LocalDateTime getDataAgendamento() {
        return dataAgendamento;
    }
    
    public void setDataAgendamento(LocalDateTime dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
    }
    
    public String getTipo_servico() {
        return tipo_servico;
    }
    
    public void setTipo_servico(String tipo_servico) {
        this.tipo_servico = tipo_servico;
    }
    
    public double getValor_servico() {
        return valor_servico;
    }
    
    public void setValor_servico(double valor_servico) {
        this.valor_servico = valor_servico;
    }
}