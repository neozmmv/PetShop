package com.petshop;

import java.time.LocalDateTime;

public class BanhoETosa extends Servico {
    private boolean tosaProfunda;
    private boolean perfume;
    private boolean lacoOuGravata;

    public BanhoETosa(String descricao, double preco, LocalDateTime dataAgendamento, 
                    Cliente cliente, Pet pet, boolean tosaProfunda,
                    boolean perfume, boolean lacoOuGravata){
        super(descricao, preco, dataAgendamento, cliente, pet);
        this.tosaProfunda = tosaProfunda;
        this.perfume = perfume;
        this.lacoOuGravata = lacoOuGravata;
    }

    @Override
    public double calcularPreco() {
        double precoBase = getPreco();

        if (tosaProfunda) {
            precoBase += 20.0;
        }
        if (perfume) {
            precoBase += 10.0;
        }
        if (lacoOuGravata) {
            precoBase += 5.0;
        }
        if (getPet().getPeso() > 15) {
            precoBase += 15.0;
        }

        return precoBase;
    }
    public boolean isTosaProfunda() {
        return tosaProfunda;
    }
    public void setTosaProfunda(boolean tosaProfunda) {
        this.tosaProfunda = tosaProfunda;
    }
    public boolean isPerfume() {
        return perfume;
    }
    public void setPerfume(boolean perfume){
        this.perfume = perfume;
    }
    public boolean isLacoOuGravata(){
        return lacoOuGravata;
    }
    public void setLacoOuGravata(boolean LacoOuGravata){
        this.lacoOuGravata = lacoOuGravata;
    }

    @Override
    public String toString() {
        return super.toString() + ", Tosa Profunda: " + tosaProfunda +
        ", Perfume: " + perfume + ", Laco/Gravata: " + lacoOuGravata;
    }

}