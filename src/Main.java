import java.util.ArrayList;

/**
 * Created by allan on 30/08/17.
 */
public class Main {
    public static void main(String[] args) {
//        LeituraArquivo leituraArquivo = new LeituraArquivo(ArquivosDataSet.BRASIL_BR01);
//        LeituraArquivo leituraArquivo = new LeituraArquivo(ArquivosDataSet.CHINA_CN01);
        LeituraArquivo leituraArquivo = new LeituraArquivo(ArquivosDataSet.FRANCA_FR01);
//        LeituraArquivo leituraArquivo = new LeituraArquivo(ArquivosDataSet.TURQUIA_TR01);
        ArrayList<Pessoa> listaPessoas = leituraArquivo.ler();

        Desenho desenho = new Desenho(leituraArquivo.getMaxY(),leituraArquivo.getMaxX(), listaPessoas, leituraArquivo.getQtdeFrames(), leituraArquivo.getPixelsPorMetro());
        desenho.play();
    }
}
