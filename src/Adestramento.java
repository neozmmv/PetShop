import java.time.LocalDateTime;

public class Adestramento extends Servico {
    private int numeroSessoes;
    private String tipoTreinamento;
    private boolean atendimentoDomiciliar;
    
    public Adestramento(String descricao, double preco, LocalDateTime dataAgendamento, 
                       Cliente cliente, Pet pet, int numeroSessoes, 
                       String tipoTreinamento, boolean atendimentoDomiciliar) {
        super(descricao, preco, dataAgendamento, cliente, pet);
        this.numeroSessoes = numeroSessoes;
        this.tipoTreinamento = tipoTreinamento;
        this.atendimentoDomiciliar = atendimentoDomiciliar;
    }
    
    @Override
    public double calcularPreco() {
        double precoBase = getPreco();
        
        // Multiplica o preço pelo número de sessões
        precoBase = precoBase * numeroSessoes;
        
        // Taxa adicional para adestramento especializado
        if (tipoTreinamento.equalsIgnoreCase("Avançado") || 
            tipoTreinamento.equalsIgnoreCase("Comportamental")) {
            precoBase += 50.0 * numeroSessoes;
        }
        
        // Taxa para atendimento domiciliar
        if (atendimentoDomiciliar) {
            precoBase += 30.0 * numeroSessoes;
        }
        
        return precoBase;
    }
    
    // Getters e Setters
    public int getNumeroSessoes() {
        return numeroSessoes;
    }
    
    public void setNumeroSessoes(int numeroSessoes) {
        this.numeroSessoes = numeroSessoes;
    }
    
    public String getTipoTreinamento() {
        return tipoTreinamento;
    }
    
    public void setTipoTreinamento(String tipoTreinamento) {
        this.tipoTreinamento = tipoTreinamento;
    }
    
    public boolean isAtendimentoDomiciliar() {
        return atendimentoDomiciliar;
    }
    
    public void setAtendimentoDomiciliar(boolean atendimentoDomiciliar) {
        this.atendimentoDomiciliar = atendimentoDomiciliar;
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Número de Sessões: " + numeroSessoes + 
               ", Tipo de Treinamento: " + tipoTreinamento + 
               ", Atendimento Domiciliar: " + atendimentoDomiciliar;
    }
} 
