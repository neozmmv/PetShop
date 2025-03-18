import java.util.ArrayList;
import java.util.List;
//import java.sql.Connection; //Bibliotecas de acesso ao banco de dados
//import java.sql.DriverManager; //Descomentar quando for implementar o acesso ao banco

public class Cliente {
    private String nome;
    private String telefone;
    private String email;
    private List<Pet> pets;
    // adicionei a lista de pets e importei as bibliotecas pra isso

    // Construtor do cliente
    public Cliente(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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