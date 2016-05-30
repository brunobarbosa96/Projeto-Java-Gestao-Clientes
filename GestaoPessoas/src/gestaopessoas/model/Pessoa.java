package gestaopessoas.model;

/**
 *
 * @author Bruno
 * @see Classe de persistência para guardar propriedades e métodos relacionados a Pessoa
 */
public class Pessoa {
    private String nome;
    private int cpf;
    private int idade;
    private String endereco;
    private int telefone;

    public Pessoa() {
    }
    
    public Pessoa(String nome, int cpf, int idade, String endereco, int telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }
}
