package com.petshop;

import java.time.LocalDateTime;

public class Consulta extends Servico {
    private String veterinario;
    private boolean consultaUrgente;

    public Consulta(String descricao, double preco, LocalDateTime dataAgendamento, Cliente cliente, Pet pet, String veterinario, boolean consultaUrgente) {
        super(descricao, preco, dataAgendamento, cliente, pet);
        this.veterinario = veterinario;
        this.consultaUrgente = consultaUrgente;
    }

    @Override
    public double calcularPreco() {
        //mais 50% no preço se for urgente
        return consultaUrgente ? getPreco() * 1.5 : getPreco();
    }

    public String getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }

    public boolean isConsultaUrgente() {
        return consultaUrgente;
    }

    public void setConsultaUrgente(boolean consultaUrgente) {
        this.consultaUrgente = consultaUrgente;
    }

    @Override
    public String toString() {
        return super.toString() + ", Veterinário: " + veterinario + ", Urgente: " + (consultaUrgente ? "Sim" : "Não");
    }
}
