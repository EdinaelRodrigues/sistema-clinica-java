public class Paciente {
    private String nome;
    private int idade;
    private String cpf;
    private int nficha;
    private String endereco;

    public Paciente(String nome, int idade, String cpf, int nficha, String endereco) {
        this.nome = nome;
        this.idade = idade;
        this.cpf = cpf;
        this.nficha = nficha;
        this.endereco = endereco;
    }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getNficha() { return nficha; }
    public void setNficha(int nficha) { this.nficha = nficha; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    @Override
    public String toString() {
        return "nome= " + nome + "\n" +
               "idade=" + idade + "\n" +
               "cpf= " + cpf + "\n" +
               "endereco= " + endereco;
    }
}
