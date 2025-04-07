package com.petshop;

import java.util.ArrayList;
import java.util.List;

public class PacoteServicos {
    private String nome;
    private List<Servico> servicos;
    private double percentualDesconto;
    private Cliente cliente;
    private Pet pet;

    public PacoteServicos(String nome, double percentualDesconto, Cliente cliente, Pet pet){
        this.nome = nome;
        this.percentualDesconto = percentualDesconto;
        this.cliente = cliente;
        this.pet = pet;
        this.servicos = new ArrayList<>();

    }

    public void adicionarServico(Servico servico){
        if (servico.getCliente().equals(cliente) && servico.getPet().equals(pet)) {
            servicos.add(servico);
        } else {
            throw new IllegalArgumentException("Serviço deve ser para o mesmo cliente e pet do pacote");
        }
        }
    
    public void removerServico(Servico servico){
        servicos.remove(servico);
    }    

    public double calcularPrecoTotal() {
        double precoTotal = 0.0;
        
        for (Servico servico : servicos) {
            precoTotal += servico.calcularPreco();
        }

        // Aplica o desconto do pacote
        if (percentualDesconto > 0) {
            precoTotal = precoTotal * (1 - (percentualDesconto/100.0));
        }
        
        return precoTotal;
    }

    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public List<Servico> getServicos() {
        return servicos;
    }
    
    public double getPercentualDesconto() {
        return percentualDesconto;
    }
    
    public void setPercentualDesconto(double percentualDesconto) {
        this.percentualDesconto = percentualDesconto;
    }
    
    public Cliente getCliente() {
        return cliente;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    public Pet getPet() {
        return pet;
    }
    
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pacote: ").append(nome).append("\n");
        sb.append("Cliente: ").append(cliente.getNome()).append("\n");
        sb.append("Pet: ").append(pet.getNome()).append("\n");
        sb.append("Desconto: ").append(String.format("%.1f", percentualDesconto)).append("%\n");
        sb.append("Preço Total: R$ ").append(String.format("%.2f", calcularPrecoTotal())).append("\n");
        sb.append("Serviços incluídos:\n");
        
        for (Servico servico : servicos) {
            sb.append("- ").append(servico.getDescricao())
              .append(" (R$ ").append(String.format("%.2f", servico.calcularPreco())).append(")\n");
        }
        
        return sb.toString();
    }

    }
    

