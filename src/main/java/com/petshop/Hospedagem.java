package com.petshop;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Hospedagem extends Servico {
    private boolean suiteLuxo;
    private boolean servicoSpa;
    private boolean alimentacaoEspecial;
    private double valorDiaria;

    public Hospedagem(String descricao, double preco, LocalDateTime dataAgendamento, Cliente cliente, Pet pet,
                      boolean suiteLuxo, boolean servicoSpa, boolean alimentacaoEspecial, double valorDiaria) {
        super(descricao, preco, dataAgendamento, cliente, pet);
        this.suiteLuxo = suiteLuxo;
        this.servicoSpa = servicoSpa;
        this.alimentacaoEspecial = alimentacaoEspecial;
        this.valorDiaria = valorDiaria;
    }

    private int diasEstadia(LocalDateTime dataInicio, LocalDateTime dataFim) {
        int dias = (int) ChronoUnit.DAYS.between(dataInicio, dataFim); // converte o retorno long para int
        return Math.max(dias, 1); // garante pelo menos 1 dia
    }

    public double valorEstadia(LocalDateTime dataInicio, LocalDateTime dataFim)
    {
        int dias = diasEstadia(dataInicio, dataFim);
        double valor = calcularPreco();
        return valor * dias;
    }

    @Override
    public double calcularPreco() {
        double valorTotal = getPreco();
        if (suiteLuxo) {
            valorTotal += 50;
        }
        if (servicoSpa) {
            valorTotal += 25;
        }
        if (alimentacaoEspecial) {
            valorTotal += 30;
        }

        return valorTotal;
    }

    public boolean isSuiteLuxo() {
        return suiteLuxo;
    }

    public void setSuiteLuxo(boolean suiteLuxo) {
        this.suiteLuxo = suiteLuxo;
    }

    public boolean isServicoSpa() {
        return servicoSpa;
    }

    public void setServicoSpa(boolean servicoSpa) {
        this.servicoSpa = servicoSpa;
    }

    public boolean isAlimentacaoEspecial() {
        return alimentacaoEspecial;
    }

    public void setAlimentacaoEspecial(boolean alimentacaoEspecial) {
        this.alimentacaoEspecial = alimentacaoEspecial;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", Suíte Luxo: " + (suiteLuxo ? "Sim" : "Não") +
                ", Serviço Spa: " + (servicoSpa ? "Sim" : "Não") +
                ", Alimentação Especial: " + (alimentacaoEspecial ? "Sim" : "Não") +
                ", Valor Diária: R$" + valorDiaria;
    }
}
