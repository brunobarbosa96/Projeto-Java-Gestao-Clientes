package gestaopessoas.dao;

import gestaopessoas.model.Pessoa;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Bruno
 * @see classe de acesso ao banco de dados
 */
public class DaoPessoa {
    //Url de conexão com o usuário e senha de acesso
    private String urlDatabase = "jdbc:mysql://localhost:3306/pessoa";
    private String user = "root";
    private String password = "root";
    
    //Construtor vazio
    public DaoPessoa(){
        
    }
    
    /**
     * @param pessoa recebe Pessoa como parâmetro  
     * @return retorna true caso dê tudo certo e fasle caso ocorra algum erro 
     * na hora de inserir no banco de dados
     */
    public boolean PostPessoa(Pessoa pessoa){
        System.out.println("Inserindo pessoa: " + pessoa.toString());
        
        // abrindo conexão com banco de dados
        Connection connection = this.getconnection();
        
        // montando query para inserção no banco de dados
        String insertPessoa = "INSERT INTO pessoa(nome, cpf, idade, endereco, telefone)"
                + "VALUES(?,?,?,?,?)";
        
        try{
            // passando parâmetor par query
            PreparedStatement command = connection.prepareStatement(insertPessoa);
                            //Formata a query de insert substituindo as ? por valores
                              command.setString(1, pessoa.getNome());
                              command.setInt(2, pessoa.getCpf());
                              command.setInt(3, pessoa.getIdade());
                              command.setString(4, pessoa.getEndereco());
                              command.setInt(5, pessoa.getTelefone());
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
