public enum ArquivosDataSet {
    BRASIL_BR01("Paths_D - Brazil - BR01.txt"),
    CHINA_CN01("Paths_D - China - CN-01.txt"),
    FRANCA_FR01("Paths_D - France - FR-01.txt"),
    TURQUIA_TR01("Paths_D - Turkey - TR01.txt");

    private String nome;

    ArquivosDataSet(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
