import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

//teste de leitura das informações do arquivo para conectar ao banco
public class FileReader {
    public static void main(String[] args) {
        // Criação do objeto Properties
        Properties config = new Properties();

        // Lendo o arquivo server.conf
        try (FileInputStream inputStream = new FileInputStream("server.conf")) {
            config.load(inputStream);

            // lendo do arquivo
            String host = config.getProperty("host");
            int port = Integer.parseInt(config.getProperty("port"));
            String user = config.getProperty("user");
            String password = config.getProperty("password");
            String database = config.getProperty("database");

            // teste de leitura do arquivo
            System.out.println("Conectando ao banco de dados...");
            System.out.println("Host: " + host);
            System.out.println("Porta: " + port);
            System.out.println("Usuário: " + user);
            System.out.println("Banco de Dados: " + database);

            // ARQUIVO server.conf DENTRO DA PASTA src

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de configuração: " + e.getMessage());
        }
    }
}
