import java.util.Queue;

/**
 * Created by allanmoreira on 30/08/16.
 */
public class Pessoa {
    private static final int TAMANHO = 15;
    private int qtdeMovimentos;
    private Queue<Coordenada> filaCoordenadas;

    public int getQtdeMovimentos() {
        return qtdeMovimentos;
    }

    public void setQtdeMovimentos(int qtdeMovimentos) {
        this.qtdeMovimentos = qtdeMovimentos;
    }

    public Queue<Coordenada> getFilaCoordenadas() {
        return filaCoordenadas;
    }

    public void setFilaCoordenadas(Queue<Coordenada> filaCoordenadas) {
        this.filaCoordenadas = filaCoordenadas;
    }

}