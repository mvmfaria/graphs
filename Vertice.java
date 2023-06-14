import java.util.Objects;

public class Vertice<T> {
    private T valor;

    public Vertice (T valor) {
        this.valor = valor;
    }

    public T getValor() {
        return this.valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Vertice<?> vertice = (Vertice<?>) obj;
        return Objects.equals(valor, vertice.valor);
    }

    @Override
    public String toString() {
        return "\u001B[32mvalor: (\u001B[0m" + getValor()  + "\u001B[32m)\u001B[0m";
    }
}