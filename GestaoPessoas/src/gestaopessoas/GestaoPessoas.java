package gestaopessoas;

import gestaopessoas.dao.DaoPessoa;
import gestaopessoas.file.FilePessoa;
import gestaopessoas.model.Pessoa;
import java.util.List;

/**
 *
 * @author Bruno
 * @see Classe main
 */
public class GestaoPessoas {

    public static void main(String[] args) {
        // Lendo arquivo .txt e gravando no BD
        
        // Instanciando nova leitura de arquivo
        FilePessoa filePessoa = new FilePessoa();
        DaoPessoa daoPessoa = new DaoPessoa();
        //Carrega a lista de pessoas pelo arquivo de leitura
        List<Pessoa> pessoas = filePessoa.readFile();
        //Percorre a listagem depessoas e insere cada no BD
        for(int i = 0; i < pessoas.size(); i++)
            //Obtem cada uma das pessoas da listagem e insere via DAO
            daoPessoa.PostPessoa(pessoas.get(i));
    }
}
