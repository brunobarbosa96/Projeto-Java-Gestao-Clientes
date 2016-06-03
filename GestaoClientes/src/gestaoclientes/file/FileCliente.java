package gestaoclientes.file;

import gestaoclientes.model.Cliente;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bruno
 * @see Classe especialista em manipulação de arquivos
 */
public class FileCliente {
    //Recuperando diretório atual
    private String pathDirectory = Paths.get("").toAbsolutePath().toString();
    
    //Caminho do arquivo que será lido
    private String pathFile = pathDirectory + "\\arquivo.txt";
    
    /**
     * 
     * @see Método para leitura do arquivo
     * @return retorna uma lista de clientes lida no arquivo informado
     */
    public List<Cliente> readFile(){
        String row; //onde será atribuído o resultado da leitura de cada linha do arquivo
        List<Cliente> clientes = new ArrayList<Cliente>();
        
        try{
            //Fluxo de entrada
            InputStream fis = new FileInputStream(pathFile);
            
            //Leitor de fluxo de entrada
            InputStreamReader isr = new InputStreamReader(fis);
            
            //Leitor de fluxo com buffer
            BufferedReader br = new BufferedReader(isr);
            
            //While para percorrer todas as linhas do arquivo
            while((row = br.readLine()) != null){
                
                //Cada execução do while é a leitura de uma linha
                Cliente cliente = new Cliente();
                System.out.println("Linha: " + row);
                
                //"picota" a linha separando as informações por ','
                //O retorno é em um array de String
                String [] fields = row.split(",");
                
                //Setando os atributos da pessoa conforme os campos
                cliente.setNome(fields[0].trim());
                cliente.setCpf(Integer.parseInt(fields[1].trim()));
                
                // variável que cuidará da conversão de datas
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    cliente.setDataNascimento(sdf.parse(fields[2].trim()));
                } catch (ParseException ex) {
                    Logger.getLogger(FileCliente.class.getName()).log(Level.SEVERE, null, ex);
                }
                cliente.setEndereco(fields[3].trim());
                cliente.setTelefone(Integer.parseInt(fields[4].trim()));
                
                //Adicionamos o cliente a listagem
                clientes.add(cliente);              
                
                //Imprimindo cada campo separado por , 
                for(String aux : fields) 
                    System.out.println(aux + " ");
                System.out.println();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return clientes;
    }
}
