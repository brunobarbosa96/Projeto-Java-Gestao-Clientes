package gestaoclientes;

import gestaoclientes.Dao.DaoCliente;
import gestaoclientes.file.FileCliente;
import gestaoclientes.model.Cliente;
import java.util.List;

/**
 *
 * @author Bruno
 * @see Classe main
 */
public class GestaoClientes {
    /**
     * @see Lê arquivo .txt, grava no BD e mostra os dados lidos no console
     * @param args 
     */
    public static void main(String[] args) {
        // Instanciando nova leitura de arquivo
        FileCliente fileCliente = new FileCliente();
        DaoCliente daoCliente = new DaoCliente();
        
        //Carrega a lista de clientes pelo arquivo de leitura
        List<Cliente> clientes = fileCliente.readFile();
        
        //Percorre a listagem de clientes e insere cada no BD
        clientes.stream().forEach((cliente) -> {
            //Obtem cada um dos clientes da listagem e insere via DAO
            daoCliente.PostCliente(cliente);
            
            //Exibindo registros de cliente no console
            System.out.println("Nome: " + cliente.getNome());
            System.out.println();
            System.out.println("CPF: " + cliente.getCpf());
            System.out.println();
            System.out.println("Data de Nascimento: " + cliente.getDataNascimento());
            System.out.println();
            System.out.println("Endereço: " + cliente.getEndereco());
            System.out.println();
            System.out.println("Telefone: " + cliente.getTelefone());
            System.out.println("********************************************************");
        });
    }
}

