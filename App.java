import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {

        Grafo<Cidade> grafo = new Grafo<>();
        Grafo<Cidade> AGM = new Grafo<>();

        String nomeArquivo = "entrada.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {

            String linha;
            int numeroDeVertices = Integer.parseInt(br.readLine());
            int i = 0;

            /*
             * No trecho a seguir, os vértices estão sendo adicionados ao grafo. Para isso, é feito a leitura de n linhas do arquivo, sendo n a 
             * quantidade de vértices.
             */
            int aux = 0;
            while (numeroDeVertices > aux) {
                linha = br.readLine();
                String[] segmentos = linha.split(",");
                Cidade cidade = new Cidade(Integer.parseInt(segmentos[0]), segmentos[1]);
                grafo.adicionaVertice(cidade);
                aux++;
            }
            
            /*
             * A leitura da matriz de adjacência está sendo feita de modo que o a coluna i esteja associado a cidade de origem, enquanto, a coluna j
             * esteja associado a cidade de destino.
             */
            while (aux > 0) {
                linha = br.readLine();
                String[] segmentos = linha.split(",");
                int j = 0;
                for (String segmento : segmentos) {
                    Float valor = Float.parseFloat(segmento);
                    if (valor != 0) {
                        grafo.adicionaAresta(grafo.getVertices().get(i).getValor(), grafo.getVertices().get(j).getValor(), valor);
                    }
                    j++;
                }
                i++;
                aux--;
            }
            
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        int opcao;
        boolean rodando = true;
        /*
         * A seguir está sendo construido toda a estrutura do menu principal.
         */
        do {
            System.out.println("\n\u001B[32mMenu principal:\u001B[0m");
            System.out.println("\n1. Obter cidades vizinhas.");
            System.out.println("2. Caminhos possiveis.");
            System.out.println("3. Calcular arvore geradora minima.");
            System.out.println("4. Distancia entre duas cidades (Grafo normal).");
            System.out.println("5. Distancia entre duas cidades (AGM).");
            System.out.println("6. Sair.");
            
            System.out.print("\n\u001B[32mO que voce deseja fazer: \u001B[0m");
            opcao = scanner.nextInt();
            int codigo;

            switch (opcao) {

                /*
                 * Vértices vizinhos. Para esta etapa o processamento consiste em receber o código da cidade e, em seguida, achar o respectivo
                 * vértice na lista de vértices do grafo e, por fim, exibir todos os vizinhos chamando a função "obterDestinos()"
                 */
                case 1:
                    System.out.print("\nDigite o codigo da cidade: ");
                    codigo = scanner.nextInt();

                    System.out.println("\nCidade | Codigo | Distancia");

                    for (Vertice<Cidade> vertice : grafo.getVertices()) {
                        if (vertice.getValor().getCodigo() == codigo) {
                            for (Aresta caminho : grafo.obterDestinos(vertice)) {
                                System.out.println(((Cidade) caminho.getDestino().getValor()).getNome() + " | " + ((Cidade) caminho.getDestino().getValor()).getCodigo() + " | " + caminho.getPeso());
                            }
                        }
                    }
                    break;

                /*
                 * Nesta etapa basicamente precisamos exibir o a busca em largura no grafo obter os caminhos possíveis nesse grafo.
                 */
                case 2:
                    System.out.println("\nDigite o codigo da cidade: ");
                    codigo = scanner.nextInt();
                    System.out.println("\nCaminhos possíveis:\n");
                    grafo.buscaEmLargura(codigo);
                    break;
                
                /*
                 * A seguir chamamos o método para retornar a arvore geradora minima do grafo. (a explicação está no método).
                 */
                case 3:
                    
                    AGM = grafo.construirArvoreGeradoraMinima();

                    /*
                     * Tendo a arvore geradora minima retornada pelo método, bastou percorrer a lista de arestas e exibir
                     * as informações que foram solicitadas. O único ponto de atenção aqui é que eu estou iterando de 2 em 2
                     * no indice i, pois, assim eu não exibo as "arestas duplicas".
                     */
                    System.out.println("\nArestas da arvore geradora minima: \n");
                    float peso = 0.0f;
                    for (int i = 0; i < AGM.getArestas().size(); i+=2) {
                        System.out.println(AGM.getArestas().get(i));
                        peso += AGM.getArestas().get(i).getPeso();
                    }
                    System.out.println("\nSoma total dos pesos das arestas: \u001B[31m" + peso + "\u001B[0m");
                    break;

                case 4:
                    
                    /*
                     * Recebendo os códigos das cidades de inicio e fim.
                     */
                    System.out.print("\nCodigo da cidade inicial: ");
                    int cidadeInicial = scanner.nextInt();

                    System.out.print("\nCodigo da cidade final: ");
                    int cidadeFinal = scanner.nextInt();

                    int existe = 0;

                    /*
                     * Faz uma verficação simples saber se as cidades informadas exitem.
                     */
                    for (Vertice<Cidade> vertice : grafo.getVertices()) {
                        if (vertice.getValor().getCodigo() == cidadeInicial || vertice.getValor().getCodigo() == cidadeFinal) {
                            existe++;
                        }
                    }
                    
                    if (existe < 2) {
                        System.out.println("\n\u001B[31mAlguma cidade não foi encontrada.\u001B[0m");
                    } else {

                        /*
                         * Chamo o método de caminho mínimo entre pontos.
                         */
                        grafo.menorCaminhoEntreDoisPontos(cidadeInicial, cidadeFinal);
                    }
                    break;

                case 5:
                    
                    /*
                     * Recebendo os códigos das cidades de inicio e fim.
                     */
                    System.out.print("\nCodigo da cidade inicial: ");
                    int cidadeInicialAGM = scanner.nextInt();

                    System.out.print("\nCodigo da cidade final: ");
                    int cidadeFinalAGM = scanner.nextInt();

                    int existeAGM = 0;

                    /*
                     * Faz uma verficação simples saber se as cidades informadas exitem.
                     */
                    for (Vertice<Cidade> vertice : grafo.getVertices()) {
                        if (vertice.getValor().getCodigo() == cidadeInicialAGM || vertice.getValor().getCodigo() == cidadeFinalAGM) {
                            existeAGM++;
                        }
                    }

                    if (existeAGM < 2) {
                        System.out.println("\n\u001B[31mAlguma cidade não foi encontrada.\u001B[0m");
                    } else {
                        
                        /*
                         * Preciso verificar antes se a AGM já foi criada.
                         */
                        if (AGM.getVertices().size() == 0) {
                            AGM = grafo.construirArvoreGeradoraMinima();
                        }
                        
                        /*
                         * Chamo o método de caminho mínimo entre pontos.
                         */
                        AGM.menorCaminhoEntreDoisPontos(cidadeInicialAGM, cidadeFinalAGM);
                    }
                    break;
                
                case 6:
                    rodando = false;
                    break;

                default:
                    System.out.println("\n\u001B[31mOpção inválida. Digite um valor de 1 a 4.\u001B[0m");
                    break;
            }
        } while (rodando);
        scanner.close();
    }
}
