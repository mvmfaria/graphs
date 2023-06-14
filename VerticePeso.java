/*
 * Motivo da criação dessa classe: A implementação do algoritmo que estou fazendo é necessário que para cada vertice do grafo
 * eu guarde as seguintes informações: (menorDistancia, predecessor). Dessa forma, criar uma classe que tenha essas informações 
 * como atributos foi a solução que achei mais adequada.
 */

public class VerticePeso<T> implements Comparable<VerticePeso<T>> {

    private Float peso;
    private Vertice<T> verticePai;

    public VerticePeso() {
        this.peso = Float.MAX_VALUE;
        this.verticePai = null;
    }

    public Float getPeso() {
        return peso;
    }

    public void setPeso(Float peso) {
        this.peso = peso;
    }

    public Vertice<T> getVerticePai() {
        return verticePai;
    }

    public void setVerticePai(Vertice<T> verticePai) {
        this.verticePai = verticePai;
    }

    @Override
    public int compareTo(VerticePeso<T> outroVertice) {
        return this.peso.compareTo(outroVertice.getPeso());
    }
}