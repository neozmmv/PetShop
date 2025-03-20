import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

//teste de leitura das informações do arquivo para conectar ao banco
public class FileReader {

    private String file_Path;
    private Properties config;

    // Construtor
    public FileReader(String filePath) {
        this.file_Path = filePath;
        this.config = new Properties();
        read_File();
    }

    private void read_File() {

        // Lendo o arquivo
        try (FileInputStream inputStream = new FileInputStream(this.file_Path)) {
            config.load(inputStream);
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de configuração: " + e.getMessage());
        }
    }

    int get_Port() {
        return Integer.parseInt(config.getProperty("port"));
    }

    String get_Host() {
        return config.getProperty("host");
    }

    String get_User() {
        return config.getProperty("user");
    }

    String get_Password() {
        return config.getProperty("password");
    }

    String get_Database() {
        return config.getProperty("database");
    }

    /*
     * public static void main(String[] args) {
     * // Criação do objeto Properties
     * Properties config = new Properties();
     * 
     * // Lendo o arquivo server.conf
     * try (FileInputStream inputStream = new FileInputStream("server.conf")) {
     * config.load(inputStream);
     * 
     * // lendo do arquivo
     * String host = config.getProperty("host");
     * int port = Integer.parseInt(config.getProperty("port"));
     * String user = config.getProperty("user");
     * String password = config.getProperty("password");
     * String database = config.getProperty("database");
     */

    // teste de leitura do arquivo
    /*
     * System.out.println("Conectando ao banco de dados...");
     * System.out.println("Host: " + host);
     * System.out.println("Porta: " + port);
     * System.out.println("Usuário: " + user);
     * System.out.println("Banco de Dados: " + database);
     */

    // ARQUIVO server.conf DENTRO DA PASTA src

    /*
     * } catch (IOException e) {
     * System.err.println("Erro ao ler o arquivo de configuração: " +
     * e.getMessage());
     * }
     * }
     */
}
