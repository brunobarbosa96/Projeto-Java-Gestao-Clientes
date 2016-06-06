package gestaoclientes.Dao;

import gestaoclientes.model.Cliente;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
     * @see Método para buscar um determinado cliente no banco
     * @param id
     * @return Cliente - retorna um cliente específico
     */
    public Cliente getClienteById(int id){
        Cliente cliente = new Cliente(); // Objeto "cobaia" para retorno
        
        System.out.println("Localizando cliente com Id: " + id);
        // abrindo conexão com banco
        Connection connection = this.getconnection();
        // declarando string que contém o comando a ser executado no BD   
        String querySelect = "SELECT * FROM cliente WHERE id = ? ";
        
        try{
            PreparedStatement ps = connection.prepareStatement(querySelect);
            ps.setInt(1, id);// substitui a interrogação pelo id do parametro
            //resultset é a estrutura que armazena o retorno do BD
            ResultSet rs = ps.executeQuery();
            //E enquanto tiver dados no banco de dados..
            while(rs.next()){
                // neste caso espera se que o result set tenha só 1 registro
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setDataNascimento(rs.getDate("dataNascimento"));
                cliente.setCpf(rs.getInt("cpf"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setTelefone(rs.getInt("telefone"));
            } 
            rs.close();
            connection.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return cliente;
    }
    
    /**
     * @see Método para buscar todos os clientes do banco 
     * @return List<Cliente>
     */
    public List<Cliente> getAll(){
        List<Cliente> clientes = new ArrayList<Cliente>();//objeto cobaia para retorno
        System.out.println("Obetendo toda a listagem de clientes...");
        Connection connection = this.getconnection();

        String querySelect = "SELECT * FROM cliente";
        
        try{
            PreparedStatement ps = connection.prepareStatement(querySelect);
            ResultSet rs = ps.executeQuery();
            //E enquanto tiver dados no banco de dados..
            while(rs.next()){
                //cria novo objeto pessoa
                Cliente cliente = new Cliente();
                cliente.setNome(rs.getString("nome"));
                cliente.setDataNascimento(rs.getDate("dataNascimento"));
                cliente.setCpf(rs.getInt("cpf"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setTelefone(rs.getInt("telefone"));
                clientes.add(cliente);//adicona a listagem
            }   //no inicio desse metodo, crie um novo objeto cliente Cliente cliente = new Cliente();     
            rs.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return clientes;
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
     * @see Método para atualizar o cliente do BD
     * @Return true caso dê certo e false caso dê errado 
     * @param Cliente
     */
     public boolean PutCliente(Cliente cliente){
        boolean status = false;
        System.out.println("Atualizando Cliente");
        Connection connection = this.getconnection();
        
        String updateCliente = "UPDATE cliente SET nome = ?, cpf = ?, endereco = ?, dataNascimento = ?, telefone = ? WHERE id = ? ";
        
        try{
            PreparedStatement ps = connection.prepareStatement(updateCliente);
            ps.setString(1, cliente.getNome());
            ps.setInt(2, cliente.getCpf());
            ps.setString(3, cliente.getEndereco());
            ps.setString(4, new SimpleDateFormat("yyyy-MM-dd").format(cliente.getDataNascimento()));
            ps.setInt(5, cliente.getTelefone());
            ps.setInt(6, cliente.getId());
            ps.execute();
            connection.close();
            status = true; 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return status;
     }
     
    /**
     * @see Método para excluir um cliente da base
     * @param id
     * @return true caso dê certo e false caso dê errado
     */
      public boolean deleteCliente(int id){
        boolean status = false;
        System.out.println("Exclundo cliente: " + id);
        
        // abrindo conexão com banco de dados
        Connection connection = this.getconnection();
        
        connectionDB = connection;
        
        //montando query para deletar cliente do banco de dados
        String queryDelete = "DELETE FROM cliente WHERE id = ? ";
        try{
            // Passando parâmetro para a query
            PreparedStatement ps = connection.prepareStatement(queryDelete);
            // Formata a query de delete substituindo as ? por valor
            ps.setInt(1, id);
            ps.execute();
            connection.close();
            status = true;            
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return status;
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
