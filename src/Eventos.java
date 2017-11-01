import java.util.ArrayList;

public class Eventos {
    private ArrayList<Coordenada> listaCoordenadasFrame;
    private int pixelsPorMetro;
    private int cinqCentim;
    private int umMetroEmeio;
    private int doisMetros;

    private int qtdeEspacoIntimo, getQtdeEspacoPessoal, getQtdeEspacoSocial, getQtdeEspacoPublico;
    private int qtdeEspacoIntimoTotal, getQtdeEspacoPessoalTotal, getQtdeEspacoSocialTotal, getQtdeEspacoPublicoTotal;

    public Eventos(ArrayList<ArrayList<Coordenada>> matrizPessoasPorFrame, int pixelsPorMetro) {
        cinqCentim = pixelsPorMetro/2;
        umMetroEmeio = pixelsPorMetro + pixelsPorMetro/2;
        doisMetros = pixelsPorMetro*2;

        qtdeEspacoIntimo = 0;
        getQtdeEspacoPessoal = 0;
        getQtdeEspacoSocial = 0;
        getQtdeEspacoPublico = 0;
        int cont = 0;

        for (ArrayList<Coordenada> listaCoordenadas : matrizPessoasPorFrame) {
            this.listaCoordenadasFrame = listaCoordenadas;
            qtdeEspacoIntimo = 0;
            getQtdeEspacoPessoal = 0;
            getQtdeEspacoSocial = 0;
            getQtdeEspacoPublico = 0;
            for (int i = 0; i < listaCoordenadasFrame.size() - 1; i++) {
                for (int j = i + 1; j < listaCoordenadasFrame.size(); j++) {
                    if (i != j) {
                        calculaEspacoPessoal(listaCoordenadasFrame.get(i), listaCoordenadasFrame.get(j));
                    }
                }
            }
            System.out.println();
            System.out.println("Frame " + ++cont);
            System.out.println("Eventos de espaço íntimo: " + qtdeEspacoIntimo);
            System.out.println("Eventos de espaço pessoal: " + getQtdeEspacoPessoal);
            System.out.println("Eventos de espaço social: " + getQtdeEspacoSocial);
            System.out.println("Eventos de espaço público: " + getQtdeEspacoPublico);
        }
        imprime();
    }

    private void calculaEspacoPessoal(Coordenada b1, Coordenada b2){
        int distancia = (int) distanciaEntreDoisPontos(b1, b2);

        if (distancia <= cinqCentim){
            qtdeEspacoIntimo++;
            qtdeEspacoIntimoTotal++;
        }
        else if(distancia > cinqCentim && distancia <= umMetroEmeio){
            getQtdeEspacoPessoal++;
            getQtdeEspacoPessoalTotal++;
        }
        else if(distancia > umMetroEmeio && distancia <= doisMetros){
            getQtdeEspacoSocial++;
            getQtdeEspacoSocialTotal++;
        } else{
          getQtdeEspacoPublico++;
          getQtdeEspacoPublicoTotal++;
        }
    }

    private double distanciaEntreDoisPontos(Coordenada c1, Coordenada c2){
        return Math.sqrt(
                Math.pow((c1.getX() - c2.getX()),2) +
                        Math.pow((c1.getY() - c2.getY()),2));
    }

    private void imprime(){
        System.out.println();
        System.out.println("/---------------------------------------/\n");
        System.out.println("TOTAIS");
        System.out.println("Eventos de espaço íntimo: " + qtdeEspacoIntimoTotal);
        System.out.println("Eventos de espaço pessoal: " + getQtdeEspacoPessoalTotal);
        System.out.println("Eventos de espaço social: " + getQtdeEspacoSocialTotal);
        System.out.println("Eventos de espaço público: " + getQtdeEspacoPublicoTotal);
    }
}
