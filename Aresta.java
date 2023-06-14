public class Aresta implements Comparable<Aresta> {
    private Vertice origem, destino;
    private float peso;

    public Aresta (Vertice verticeOrigem, Vertice verticeDestino, float peso) {
        this.origem = verticeOrigem;
        this.destino = verticeDestino;
        this.peso = peso;
    }

    public Vertice getOrigem() {
        return this.origem;
    }

    public void setOrigem(Vertice origem) {
        this.origem = origem;
    }

    public Vertice getDestino() {
        return this.destino;
    }

    public void setDestino(Vertice destino) {
        this.destino = destino;
    }

    public float getPeso() {
        return this.peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    @Override
    public int compareTo(Aresta outraAresta) {
        return Float.compare(this.peso, outraAresta.getPeso());
    }

    @Override
    public String toString() {
        return "\u001B[34morigem: \u001B[0m" + getOrigem() + "\u001B[34m, destino: \u001B[0m" + getDestino() + "\u001B[34m, peso: \u001B[0m" + getPeso();
    }


}