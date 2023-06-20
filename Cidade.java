public class Cidade implements Comparable<Cidade> {

    private Integer codigo;
    private String nome;

    public Cidade(Integer codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public Integer getCodigo() {
        return this.codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "\u001B[31mcodigo: \u001B[0m" + getCodigo() + "\u001B[31m, nome: \u001B[0m" + getNome();
    }

    @Override
    public int compareTo(Cidade outraCidade) {
        return Integer.compare(this.codigo, outraCidade.getCodigo());
    }
}