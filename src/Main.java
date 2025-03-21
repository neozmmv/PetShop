public class Main {
    public static void main(String[] args) {

        FileReader fileReader = new FileReader("server.conf");
        Connect connection = new Connect();
        connection.setUser(fileReader.get_User());
        connection.setPassword(fileReader.get_Password());
        connection.mount_Url(fileReader.get_Host(), fileReader.get_Port(), fileReader.get_Database());
        System.out.println(connection.getConnection());
        connection.executeQuery("SHOW TABLES;", true); // essa funcao retorna um ResultSet (resultado da query)

    }
}
