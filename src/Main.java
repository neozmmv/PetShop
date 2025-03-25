import java.sql.*;

public class Main {
    public static void main(String[] args) {
            //ler o arquivo com as configs do banco
            FileReader fileReader = new FileReader("server.conf");
            Connect connection = new Connect();
            connection.setUser(fileReader.get_User());
            connection.setPassword(fileReader.get_Password());
            connection.mount_Url(fileReader.get_Host(), fileReader.get_Port(), fileReader.get_Database());

            System.out.println(connection.getConnection());

            //apenas para consultas no banco
            //ResultSet result = connection.executeQuery("SELECT id from tabela2 where nome = 'NOME';", false); //resultado da query



            //--------------- O QUE IMPORTA É ISSO AQUI, O RESTO É TESTE --------------------------
            int test = connection.getIntByQuery("SELECT id from tabela2 where nome = 'Joao';"); //Joao só nome para testar
            System.out.println("VALOR DE TEST:" + test);

            String nome = connection.getStringByQuery("Select nome from tabela2 where id = 1");
            System.out.println("VALOR DE NOME: " + nome);

            //Adicionar informação no banco:
            connection.executeUpdate("(COMANDO PARA INSERIR ALGO NO BANCO)", true);
            //-------------------------------------------------------------------------------------




            //executa a query e retorna a quantidade de linhas afetadas.
            //int rows = connection.executeUpdate("Insert into tabela2 values(2,'Joao');", true);
            //QueryResult resultado = new QueryResult(result);
            //resultado.printQuery("id");

            //MOSTRAR RESULTADOS DA QUERY DE CONSULTA (RESULTSET)
            //MESMA COISA DE "resultado.printQuery()"
            /*if (result != null) {
                while (result.next()) {
                    System.out.println("Nome: " + result.getString("nome"));
                }
                result.close(); // Fechar o ResultSet após o uso
            }*/

            //System.out.println("Linhas: " + rows);

    }
}
