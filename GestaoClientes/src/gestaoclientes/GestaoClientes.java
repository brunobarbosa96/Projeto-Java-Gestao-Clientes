package gestaoclientes;

import gestaoclientes.Dao.DaoCliente;
import gestaoclientes.file.FileCliente;
import gestaoclientes.model.Cliente;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
        System.out.println("-------------- [ATIVIDADE PRÁTICA 001] --------------");
        //Percorre a listagem de clientes e insere cada no BD
        clientes.stream().forEach((cliente) -> {
            //Obtem cada um dos clientes da listagem e insere via DAO
            daoCliente.PostCliente(cliente);
            
            //Exibindo registros de cliente no console
            System.out.println("********************************************************");
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("CPF: " + cliente.getCpf());
            System.out.println("Data de Nascimento: " + cliente.getDataNascimento());
            System.out.println("Endereço: " + cliente.getEndereco());
            System.out.println("Telefone: " + cliente.getTelefone());
            System.out.println("********************************************************");
        });
        
        System.out.println();
        System.out.println("-------------- [ATIVIDADE PRÁTICA 002] --------------");
        
        //Scanner lê o teclado
        Scanner scanner = new Scanner(System.in);
        System.out.println("1. Listar Todos");
        System.out.println("2. Listar por ID");
        System.out.println("3. Inserir");
        System.out.println("4. Atualizar");
        System.out.println("5. Deletar");
        System.out.println("6. Sair");
        System.out.println("Opção: ");
        int choice = scanner.nextInt();
        
        switch(choice){
            case 1:
                getAll();
                break;
            case 2:
                getById(0);
                break;
            case 3:
                post();
                break;
            case 4:
                put();
                break;
            case 5:
                delete();
                break;
            default:
                break;
        }
    }
    
    /**
     * @see Método que irá no banco e listar todos os clientes cadastrados
     */
    public static void getAll(){
        System.out.println("------- Listar Todos Clientes -------");
        
        // Instanciando a classe que trabalha com os métodos que vão no banco
        DaoCliente daoCliente = new DaoCliente();
        
        // Variável que será usada para receber o retorno do BD
        List<Cliente> clientes = daoCliente.getAll();
        
        // Verificando se veio algum retorno do banco de dados
        if (clientes.isEmpty()){
            System.out.println("Nenhum cliente encontrado...");
            return;
        }
        
        // Listando clientes que retornaram do banco de dados
        System.out.println("Clientes:");
        clientes.stream().forEach((cliente) -> {
            System.out.println("********************************************************");
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("CPF: " + cliente.getCpf());
            System.out.println("Data de Nascimento: " + cliente.getDataNascimento());
            System.out.println("Endereço: " + cliente.getEndereco());
            System.out.println("Telefone: " + cliente.getTelefone());
            System.out.println("********************************************************");
        });
    }
    
    /**
     * @param parameterId inteiro - Caso for a opção de o usuáro informar por console informar 0
     * @return Cliente
     * @see Método que irá no banco e listar o cliente desejado
     */
    public static Cliente getById(int parameterId) {
        // Instanciando a classe que trabalha com os métodos que vão no banco
        DaoCliente daoCliente = new DaoCliente();
        
        //Declarando variável Id
        int id;
        
        // Caso não seja informado o parâmetro. Ler o que o usuário informar
        if (parameterId == 0){
            System.out.println("------- Listar Cliente Por ID -------");
            System.out.println("Informe o id do cliente: ");
            Scanner scanner = new Scanner(System.in);
            id = scanner.nextInt();
        }
        else
            id = parameterId;
        
        // Variável que será usada para receber o retorno do BD
        Cliente cliente = daoCliente.getClienteById(id);
        
        //Verificando se retornou alguma coisa do banco de dados
        if (cliente == null){
            System.out.println("Cliente não encontrado...");
            return cliente;
        }
        
        // Mostrando cliente na tela
        System.out.println("********************************************************");
        System.out.println("Nome: " + cliente.getNome());
        System.out.println("CPF: " + cliente.getCpf());
        System.out.println("Data de Nascimento: " + cliente.getDataNascimento());
        System.out.println("Endereço: " + cliente.getEndereco());
        System.out.println("Telefone: " + cliente.getTelefone());
        System.out.println("********************************************************");
        return cliente;
    }
    
    /**
     * @see Método que irá inserir cliente no banco de dados
     */
    public static void post(){
        System.out.println("------- Inserir Cliente -------");
        
        // Instanciando a classe que trabalha com os métodos que vão no banco
        DaoCliente daoCliente = new DaoCliente();
        
        // Instanciando cliente que será usado para inserir no banco
        Cliente cliente = new Cliente();
        
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Informe o nome do cliente: ");
        cliente.setNome(scanner.nextLine()); 
        System.out.println("Informe o CPF do cliente: ");
        cliente.setCpf(scanner.nextInt());
        System.out.println("Informe a Data de Nascimento do cliente no formato (dd/MM/yyyy): ");
        try {
            cliente.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse(scanner.next()));
        } catch (ParseException ex) {
            Logger.getLogger(FileCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Informe o endereço do cliente: ");
        cliente.setEndereco(scanner.nextLine()); // para burlar bug do java (famosa gambi)
        cliente.setEndereco(scanner.nextLine());
        System.out.println("Informe o telefone do cliente: ");
        cliente.setTelefone(scanner.nextInt());
        
        //Inserindo cliente
        daoCliente.PostCliente(cliente);
        System.out.println("Cliente inserido com sucesso!");
    }
    
    /**
     * @see Método que irá atualizar cliente no banco de dados
     */
    public static void put(){
        System.out.println("------- Atualiar Cliente -------");
        
        // Instanciando a classe que trabalha com os métodos que vão no banco
        DaoCliente daoCliente = new DaoCliente();
        
        // Instanciando cliente que será usado para inserir no banco
        Cliente cliente = new Cliente();
        
        System.out.println("Insira o Id do cliente: ");
        Scanner scanner = new Scanner(System.in);
        cliente.setId(scanner.nextInt());
        
        System.out.println("Cliente Id = " + cliente.getId() + ": ");
        cliente = getById(cliente.getId());
        
        // Caso o cliente informado não seja encontrado, retornar
        if(cliente == null)
            return;
        
        System.out.println("Informe o nome do cliente: ");
        cliente.setNome(scanner.nextLine()); // para burlar bug do java (famosa gambi)
        cliente.setNome(scanner.nextLine());
        System.out.println("Informe o CPF do cliente: ");
        cliente.setCpf(scanner.nextInt());
        System.out.println("Informe a Data de Nascimento do cliente no formato (dd/MM/yyyy): ");
        try {
            cliente.setDataNascimento(new SimpleDateFormat("dd/MM/yyyy").parse(scanner.next()));
        } catch (ParseException ex) {
            Logger.getLogger(FileCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Informe o endereço do cliente: ");
        cliente.setEndereco(scanner.nextLine()); // para burlar bug do java (famosa gambi)
        cliente.setEndereco(scanner.nextLine());
        System.out.println("Informe o telefone do cliente: ");
        cliente.setTelefone(scanner.nextInt());
        
        //Atualizando cliente na base
        daoCliente.PutCliente(cliente);
        System.out.println("Cliente atualizado com sucesso");
        System.out.println("Cliente atualizado: ");
        getById(cliente.getId());
    }
    
    /**
     * @see Método que irá deletar cliente no banco de dados
     */
    public static void delete(){
        System.out.println("------- Excluir Cliente -------");
         
        // Instanciando a classe que trabalha com os métodos que vão no banco
        DaoCliente daoCliente = new DaoCliente();
        
        System.out.println("Informe o id do cliente: ");
        Scanner scanner = new Scanner(System.in);
        int id = scanner.nextInt();
        
        System.out.println("Cliente Id = " + id + ": ");
        Cliente cliente = getById(id);
        
        // Caso o cliente informado não seja encontrado, retornar
        if(cliente == null)
            return;
        
        daoCliente.deleteCliente(id);
        System.out.println("Cliente excluído com sucesso");
    }
}

