//criei a classe serviço, aí tem q criar as classes de extensão, que são os serviços mesmo
import java.time.LocalDateTime;

public abstract class Servico {
    private String descricao;
    private double preco;
    private LocalDateTime dataAgendamento;
    private Cliente cliente;
    private Pet pet;
    
    public Servico(String descricao, double preco, LocalDateTime dataAgendamento, Cliente cliente, Pet pet) {
        this.descricao = descricao;
        this.preco = preco;
        this.dataAgendamento = dataAgendamento;
        this.cliente = cliente;
        this.pet = pet;
    }
    
    public abstract double calcularPreco();
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public double getPreco() {
        return preco;
    }
    
    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    public LocalDateTime getDataAgendamento() {
        return dataAgendamento;
    }
    
    public void setDataAgendamento(LocalDateTime dataAgendamento) {
        this.dataAgendamento = dataAgendamento;
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
        return "Serviço: " + descricao + ", Preço: R$" + preco + 
               ", Data: " + dataAgendamento + ", Cliente: " + cliente.getNome() + 
               ", Pet: " + pet.getNome();
    }
}
