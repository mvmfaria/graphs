/* 
 * Autor: Marcos Vinicius Mendes Faria
 * Data: 01/06/2023
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Grafo<T> {
    private ArrayList<Aresta> arestas;
    private ArrayList<Vertice<T>> vertices;
    
    public Grafo() {
        this.arestas = new ArrayList<>();
        this.vertices = new ArrayList<>();
    }
    
    public ArrayList<Aresta> getArestas() {
        return this.arestas;
    }

    public void setArestas(ArrayList<Aresta> arestas) {
        this.arestas = arestas;
    }

    public ArrayList<Vertice<T>> getVertices() {
        return this.vertices;
    }

    public void setVertices(ArrayList<Vertice<T>> vertices) {
        this.vertices = vertices;
    }
    
    /*
     * Recebe um valor do tipo genérico, cria um novo 
     * vértice com esse valor recebido e, por fim, 
     * adiciona o novo vertice à lista de vértices.
     * O vértice é retornado no final.
     */
    public Vertice<T> adicionaVertice(T valor) {
        Vertice<T> novo = new Vertice<T>(valor);
        this.vertices.add(novo);
        return novo;
    }

    /*
     * Primeiro é criado um vertice auxiliar. Em seguida, 
     * percorremos a lista até seu tamano máximo (.size) e
     * a cada iteração atribuimos para o vértice auxiliar
     * (v) o elemento na posição i da lista. Por fim, 
     * comparamos o valor do vértice com o valor passado
     * como parâmetro, caso seja igual, retornamos o vértice.
     * Se nenhum valor igual ao parâmetro for encontrado,
     * então, é retornado null.
     */
    private Vertice obterVertice(T valor) {
        Vertice v;
        for (int i=0; i<this.vertices.size(); i++) {
            v = this.vertices.get(i);
            if (v.getValor().equals(valor)) return v;
        }

        return null;
    }

    /* 
     * Verifica-se primeiro se ambos vértices (origem, destino) existem.
     * Caso não existam, são criados com os valores passados como parâmetros.
     * Por fim, a nova aresta é instanciada e adicionada a lista.
     */
    public void adicionaAresta(T origem, T destino, float peso) {
        Vertice verticeOrigem, verticeDestino;
        Aresta novaAresta;

        verticeOrigem = obterVertice(origem);

        if (verticeOrigem == null)
            verticeOrigem = adicionaVertice(origem);
        
        verticeDestino = obterVertice(destino);

        if (verticeDestino == null)
            verticeDestino = adicionaVertice(destino);

        novaAresta = new Aresta(verticeOrigem, verticeDestino, peso);

        this.arestas.add(novaAresta);   
    }

    /*
     * Fazendo a sobrecarga do método adicionaAresta(). O objetivo é só agilizar algumas partes.
     */
    public void adicionaAresta(Aresta aresta) {
        this.vertices.add(aresta.getOrigem());
        this.vertices.add(aresta.getDestino());
        this.arestas.add(aresta);
    }

    /*
     * Primeiro, uma lista de "destinos" para que ela possa ser retornada
     * no final. Além disso, um objeto auxiliar do tipo aresta é instaciado para 
     * receber as aresta da lista. Para descobrir os possíveis destinos, 
     * percorremso por cada aresta da lista de arestas e vemos quais tem 
     * como origem o vértice passado como parâmetro. Ao final do loop, a
     * lista de destinos é retornada.
     */

    public ArrayList<Aresta> obterDestinos(Vertice v) {
        ArrayList<Aresta> destinos = new ArrayList<>();
        Aresta atual;
        for (int i=0; i<this.arestas.size(); i++) {
            atual = this.arestas.get(i);
            if (atual.getOrigem().equals(v))
                destinos.add(atual);
        }
        return destinos;
    }

    /*
     * Dois arrays são criados para auxiliar a busca, um deles armazenas
     * os vértices que já foram utilizados e a outro armazena os vértices
     * que serão analisados. Para inicio, utilizamos (adicionamos a "fila")
     * o primeiro vértice da lista de vértices. Enquanto a fila não estiver
     * vazia, o seguinte processo irá se repetir: 
     *  - Extrai o primeiro elemento da lista e e o difine como "marcado"
     *  - Printa seu valor
     *  - Cria uma lista com as arestas de seus possíveis destinos.
     *  - Percorre essa lista por completo analisando os destinos, caso ele
     *  ainda não esteja marcado colocamos na fila para ser percorrido.
     */

    public void buscaEmLargura(int saida) {
        ArrayList<Vertice> marcados = new ArrayList<Vertice>();
        ArrayList<Vertice> fila = new ArrayList<Vertice>();
        Vertice atual = this.vertices.get(saida - 1);
        fila.add(atual);
        while (fila.size()>0) {
            atual = fila.get(0);
            fila.remove(0);
            marcados.add(atual);
            System.out.println(((Cidade) atual.getValor()).getNome() + " | " + ((Cidade) atual.getValor()).getCodigo());
            ArrayList<Aresta> destinos = this.obterDestinos(atual);
            Vertice proximo;
            for (int i=0; i<destinos.size(); i++) {
                proximo = destinos.get(i).getDestino();
                if(!marcados.contains(proximo) && !fila.contains(proximo))
                    fila.add(proximo);
            }
        }
    }

    /*
     * Método criado para extrair o menor elemento (vértice) do array.
     */
    public Integer encontrarIndiceMenorElemento(ArrayList<VerticePeso<T>> lista) {
            
        int menorIndice = 0;
        VerticePeso<T> menorElemento = lista.get(0);

        for (int i = 1; i < lista.size(); i++) {
            VerticePeso<T> elementoAtual = lista.get(i);

            if (elementoAtual.compareTo(menorElemento) < 0) {
                menorIndice = i;
                menorElemento = elementoAtual;
            }
        }

        return menorIndice;
    }

    public Integer encontrarArestaDeMenorElemento(ArrayList<Aresta> lista) {
            
        int menorIndice = 0;
        Aresta menorElemento = lista.get(0);

        for (int i = 1; i < lista.size(); i++) {
            Aresta elementoAtual = lista.get(i);

            if (elementoAtual.compareTo(menorElemento) < 0) {
                menorIndice = i;
                menorElemento = elementoAtual;
            }
        }

        return menorIndice;
    }

    /*
     * A árvore geradora mínima foi construída utilizando o algoritmo de Prim. A abordagem consiste em inicializar todos os 
     * vértices do grafo com dois valores: o vértice predecessor e o peso da aresta para chegar até esse vértice a partir do 
     * predecessor. Inicialmente, todos os pesos são definidos como "infinito" e os predecessores como null. O único vértice 
     * que não terá peso infinito no início é o vértice inicial, que é definido como 0. O processo consiste em identificar 
     * os vértices adjacentes ao vértice atual e verificar se o peso da aresta que os conecta é menor do que o valor de 
     * "peso" do vértice destino. Se for menor, o "peso" do vértice destino é atualizado com o peso da aresta e o predecessor 
     * do destino é definido como o vértice atual. Depois que todos os vizinhos do vértice atual são verificados, o vértice 
     * atual é adicionado à lista de vértices verificados para que ele não seja usado novamente. Em seguida, o próximo 
     * vértice de menor peso é selecionado do grafo. Dessa forma, a árvore geradora mínima é construída passo a passo, 
     * adicionando os vértices com os menores pesos até que todos os vértices estejam incluídos na árvore.
     */
    public Grafo<T> construirArvoreGeradoraMinima() {

        /*
         * A estruturas que foram utilizadas estão sendo instanciadas a seguir. O ponto de atenção aqui é apenas nos arrays
         * "informacoes" e "auxInformacoes", visto que, eles são utilizados para guardar as informações que contém a lógica
         * algoritmo de Prim. O "auxInformacoes" foi necessário para que fosse possivel "inutilizar um vértice" assim que todos 
         * seu destinos tivessem sido verificados.
         */

        ArrayList<VerticePeso<T>> informacoes = new ArrayList<VerticePeso<T>>();
        ArrayList<VerticePeso<T>> auxInformacoes = new ArrayList<VerticePeso<T>>();
        ArrayList<Vertice<T>> verificados = new ArrayList<Vertice<T>>();
        Grafo<T> novoGrafo = new Grafo<T>();
        
        /*
         * Preenchendo ambos arrays que guardam as informações dos vértices. Eu crio dois vértices a cada iteração, pois, dessa forma 
         * eu evito que alteração do objeto em uma lista não afete a outra. Ao final eu inicializo os vértices iniciais.
         */
        for (int i = 0; i < this.vertices.size(); i++) {
            informacoes.add(new VerticePeso<>());
            auxInformacoes.add(new VerticePeso<>());
        }
        informacoes.get(0).setPeso(0.0f);
        auxInformacoes.get(0).setPeso(0.0f);      
        
        /*
         * Aqui ocorre todo o processo. Primeiramente, é definido um loop que continuará até que todos os vértices sejam 
         * adicionados à lista de vértices verificados. Em seguida, o índice do menor elemento na lista "auxInformacoes" é 
         * extraído e o valor desse vértice é definido como máximo, indicando que ele foi "inutilizado". Em seguida, o 
         * vértice atual é obtido utilizando o índice extraído. Para cada destino do vértice atual, verifica-se se o destino 
         * já foi visitado e, em seguida, se o peso da aresta que liga os dois vértices é menor do que o peso atual do vértice 
         * destino. Se essa condição for atendida, as informações do vértice destino são atualizadas. Esse processo continua
         * até que todos os vértices sejam verificados e as informações necessárias sejam atualizadas.
         */
        while (verificados.size() < informacoes.size()) {
            int indice = encontrarIndiceMenorElemento(auxInformacoes);
            auxInformacoes.get(indice).setPeso(Float.MAX_VALUE);

            Vertice<T> atual = this.vertices.get(indice);
            for (Aresta vizinho : this.obterDestinos(atual)) {
                if (!verificados.contains(vizinho.getDestino())) {
                    for (int i = 0; i < this.vertices.size(); i++) {
                        if (this.vertices.get(i).equals(vizinho.getDestino())) {
                            if (vizinho.getPeso() < informacoes.get(i).getPeso()) {
                                informacoes.get(i).setPeso(vizinho.getPeso());
                                auxInformacoes.get(i).setPeso(vizinho.getPeso());
                                informacoes.get(i).setVerticePai(atual);
                            }
                        }
                    }
                }
            }
            verificados.add(this.vertices.get(indice));  
        }

        
        /*
         * Agora que temos as informações sobre os vértices, basta criarmos a árvore. Nesse ponto, como eu quero criar uma árvore
         * geradora mínima, eu preciso duplicar as arestas, invertendo origem e destino, porém, mantendo o peso.
         */
        for (int v = 0; v < this.vertices.size(); v++) {
            novoGrafo.adicionaVertice(this.vertices.get(v).getValor());
        }

        for (int i = 1; i < informacoes.size(); i++) {
            novoGrafo.adicionaAresta(informacoes.get(i).getVerticePai().getValor(), this.vertices.get(i).getValor(), informacoes.get(i).getPeso());
            novoGrafo.adicionaAresta(this.vertices.get(i).getValor(), informacoes.get(i).getVerticePai().getValor(), informacoes.get(i).getPeso());
        }

        return novoGrafo;
    }

    /*
     * Desenvolvendo algortimo de menor distancia entre dois pontos.
     */

    public void menorCaminhoEntreDoisPontos(int indiceInicial, int indiceFinal) {

        /*
         * Estruturas utilizadas para armazenar as informações. 
         */
        ArrayList<VerticePeso<T>> informacoes = new ArrayList<VerticePeso<T>>();
        ArrayList<VerticePeso<T>> auxInformacoes = new ArrayList<VerticePeso<T>>();
        ArrayList<Vertice<T>> verificados = new ArrayList<Vertice<T>>();

        /*
         * Atualizando os vértices com os valores iniciais.
         */
        for (int i = 0; i < this.vertices.size(); i++) {
            informacoes.add(new VerticePeso<>());
            auxInformacoes.add(new VerticePeso<>());
        }

        informacoes.get(indiceInicial - 1).setPeso(0.0f);
        auxInformacoes.get(indiceInicial - 1).setPeso(0.0f);

        /*
         * Temos que executar até que todos os vértices sejam verificados.
         */
        while (verificados.size() < informacoes.size()) {

            /*
             * Armazenamos o indice do vértice de menor valor.
             */
            int indice = encontrarIndiceMenorElemento(auxInformacoes);
            
            /*
             * Inutilizo o vértice atribuindo um valor máximo para ele.
             */
            auxInformacoes.get(indice).setPeso(Float.MAX_VALUE);
            
            /*
             * O primeiro passo é fazer o "relaxamento" das arestas e em seguida verificar menor caminho.
             */
            Vertice<T> atual = this.vertices.get(indice);
            for (Aresta vizinho : this.obterDestinos(atual)) {
                if (!verificados.contains(vizinho.getDestino())) {
                    
                    /*
                     * Identifico o respectivo vértice na lista de informações.
                     */
                    for (int i = 0; i < this.vertices.size(); i++) {
                        if (this.vertices.get(i).equals(vizinho.getDestino())) {
                            
                            /*
                             * Agora verifico o peso do vértice do vizinho e verifico se é vantajosa atualizar.
                             */
                            if ((vizinho.getPeso() + informacoes.get(indice).getPeso())  < informacoes.get(i).getPeso()) {
                                informacoes.get(i).setVerticePai(atual);
                                informacoes.get(i).setPeso(vizinho.getPeso() + informacoes.get(indice).getPeso());
                                auxInformacoes.get(i).setPeso(vizinho.getPeso() + informacoes.get(indice).getPeso());
                            }
                        }
                    }
                }
            }
            verificados.add(this.vertices.get(indice));
        }

        /*
         * Retornando informações de cada vértice.
         */
        VerticePeso<T> infoVerticeAtual = informacoes.get(indiceFinal - 1);
        int indiceVerticeAtual = indiceFinal - 1;
        boolean rodando = true;
        while (rodando)
        for (int i = 0; i < vertices.size(); i++) {
            if (infoVerticeAtual.getVerticePai().equals(vertices.get(i))) {
                /*
                 * O vértice atual é o destino final na primeira execução.
                 */
                System.out.println(vertices.get(indiceVerticeAtual) + "\u001B[34m --> \u001B[0m" + vertices.get(i));
                
                /*
                 * O vértice pai é o destino do vértice atual.
                 */
                indiceVerticeAtual = i;
                infoVerticeAtual = informacoes.get(indiceVerticeAtual);

                /*
                 * Verifico se o predecessor é nulo. 
                 */
                if (infoVerticeAtual.getVerticePai() == null) {
                    rodando = false;
                    break;
                }
            }
        }
        /*
         * Imprimindo o peso.
         */
        System.out.println("\u001B[34mDistancia entre as cidades: \u001B[0m" + informacoes.get(indiceFinal - 1).getPeso());
    }
}