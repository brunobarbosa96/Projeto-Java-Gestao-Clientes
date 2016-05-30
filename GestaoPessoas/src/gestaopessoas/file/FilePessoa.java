package gestaopessoas.file;

import gestaopessoas.model.Pessoa;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bruno
 * @see Classe especialista em manipulação de arquivos
 */
public class FilePessoa {
    //Caminho do arquivo que será lido
    private String pathFile = "C:\\Users\\Bruno\\Documents\\UNIFRAN\\2016\\1 º Semestre\\POO\\Arquivos\\coisa.txt";
    
    /**
     * 
     * @see Método para leitura do arquivo
     * @return retorna uma lista de pessoas lida no arquivo informado
     */
    public List<Pessoa> readFile(){
        String row; //onde será atribuído o resultado da leitura de cada linha do arquivo
        List<Pessoa> pessoas = new ArrayList<Pessoa>();
        
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
                Pessoa pessoa = new Pessoa();
                System.out.println("Linha: " + row);
                
                //"picota" a linha separando as informações por ','
                //O retorno é em um array de String
                String [] fields = row.split(",");
                
                //Setando os atributos da pessoa conforme os campos
                pessoa.setNome(fields[0].trim());
                pessoa.setCpf(Integer.parseInt(fields[1].trim()));
                pessoa.setIdade(Integer.parseInt(fields[2].trim()));
                pessoa.setEndereco(fields[3].trim());
                pessoa.setTelefone(Integer.parseInt(fields[4].trim()));
                
                //Adicionamos a pessoa a listagem
                pessoas.add(pessoa);              
                
                //Imprimindo cada campo separado por , 
                for(String aux : fields) 
                    System.out.println(aux + " ");
                System.out.println();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return pessoas;
    }
}
