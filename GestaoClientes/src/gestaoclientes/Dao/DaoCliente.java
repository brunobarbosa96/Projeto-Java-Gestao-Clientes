package gestaoclientes.Dao;

import gestaoclientes.model.Cliente;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Bruno
 * @see classe de acesso ao banco de dados
 */
public class DaoCliente {
    //Url de conexão com o usuário e senha de acesso
    private String urlDatabase = "jdbc:mysql://localhost:3306/cliente";
    private String user = "root";
    private String password = "root";
    private Connection connectionDB = null;    
    
    //Construtor vazio
    public DaoCliente(){
        
    }
    
    /**
     * @param cliente recebe Cliente como parâmetro  
     * @return retorna true caso dê tudo certo e fasle caso ocorra algum erro 
     * na hora de inserir no banco de dados
     */
    public boolean PostCliente(Cliente cliente){
        System.out.println("Inserindo cliente: " + cliente.toString());
        
        // abrindo conexão com banco de dados
        Connection connection = this.getconnection();
        
        connectionDB = connection;
        
        // criando tabela de cliente caso não exista
        CreateTableCliente();
        
        // montando query para inserção no banco de dados
        String insertCliente = "INSERT INTO cliente(nome, cpf, dataNascimento, endereco, telefone)"
                + "VALUES(?,?,?,?,?)";
        
        try{
            // passando parâmetor par query
            PreparedStatement command = connection.prepareStatement(insertCliente);
                            //Formata a query de insert substituindo as ? por valores
                              command.setString(1, cliente.getNome());
                              command.setInt(2, cliente.getCpf());
                              command.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(cliente.getDataNascimento()));
                              command.setString(4, cliente.getEndereco());
                              command.setInt(5, cliente.getTelefone());
                            // Executa a query
                            command.execute();
                            //Fecha conexão com BD
                            connection.close();
            return true;
        } catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }      
    }
    
    /**
     * @see método para criar tabela cliente caso não exista
     */
    public void CreateTableCliente(){
        String sql =
                "CREATE TABLE IF NOT EXISTS cliente ("+
                "id BIGINT NOT NULL AUTO_INCREMENT, " + 
                "nome VARCHAR(255) NOT NULL, " + 
                "cpf INTEGER NOT NULL, "+
                "dataNascimento datetime NOT NULL, "+
                "endereco VARCHAR(255) NOT NULL, "+
                "telefone INTEGER NOT NULL, "+
                "PRIMARY KEY (id)" + 
                ")" + 
                "ENGINE InnoDB";
        PreparedStatement command = null; 
        try{
             // Cria um objeto para realizar a ação no banco de dados
            // E cuidar de manter o estado consistente do banco
             command = connectionDB.prepareStatement(sql);
             // Executa a query 
             command.execute();
             //Encerra o comando de query
             command.close();
        }catch(SQLException ex){
            ex.printStackTrace();
            System.err.println(ex);
        }
    }
    
    /**
     * @see método para conectar ao banco
     * connection do Java.Sql - Conection Factory
     * @return conexão com banco de dados
     */
    private Connection getconnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(urlDatabase, user, password);
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return connection;
    }
}
