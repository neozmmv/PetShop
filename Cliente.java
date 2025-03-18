
public class Cliente {
    private String nome;
    private String telefone;
    private String email;

    // FALTA ADICIONAR A LISTA DE PETS.

    // Construtor do cliente
    public Cliente(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    // Funções de pegar informação (cliente.nome(), cliente.email(), etc...)
    public String nome() {
        return nome;
    }

    public String telefone() {
        return telefone;
    }

    public String email() {
        return email;
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