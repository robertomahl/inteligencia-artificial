import java.util.*;

public class Voos1 {

    static ArrayList<Estado> possiveisEstados = new ArrayList<>();
    public static final String CIDADE_INICIAL = "a";
    public static final String CIDADE_FINAL = "j";
    static List<String> visitados = new ArrayList<>();
    static List<Estado> naoVisitados = new ArrayList<>();

    static int nrEstado;

    static class Estado {
        int voo;
        String cidade1;
        String cidade2;
        Estado pai;

        Estado(int voo, String cidade1, String cidade2) {
            this.voo = voo;
            this.cidade1 = cidade1;
            this.cidade2 = cidade2;
        }

        void imprime() {
            System.out.println("Estado " + nrEstado + ": \toper(" + voo + ", " + cidade1 + ", " + cidade2 + ")");
        }

        void addPai(Estado pai) {
            this.pai = pai;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Estado) {
                Estado other = (Estado) obj;
                return this.voo == other.voo && this.cidade1.equals(other.cidade1)
                        && this.cidade2.equals(other.cidade2);
            }
            return false;
        }
    }

    static void abreNo(String no) {
        for (Estado estado : possiveisEstados) {
            if (estado.cidade1.equals(no) && !visitados.contains(estado.cidade2)) {
                estado.addPai(new Estado(-1, no, null));
                naoVisitados.add(estado);
            }
        }
        visitados.add(no);
    }

    static void buscarSolucaoProfundidade(String cidade) {
        if (cidade.equals(CIDADE_FINAL)) {
            return;
        }

        abreNo(cidade);
        while (!naoVisitados.isEmpty()) {
            Estado novoNo = naoVisitados.remove(naoVisitados.size() - 1);
            if (!visitados.contains(novoNo.cidade2)) {
                nrEstado++;
                novoNo.imprime();
                abreNo(novoNo.cidade2);
                if (novoNo.cidade2.equals(CIDADE_FINAL)) {
                    return;
                }
            }
        }
    }

    static void buscarSolucaoLargura(String cidade) {
        if (cidade.equals(CIDADE_FINAL)) {
            return;
        }

        abreNo(cidade);
        while (!naoVisitados.isEmpty()) {
            Estado novoNo = naoVisitados.remove(0);
            if (!visitados.contains(novoNo.cidade2)) {
                nrEstado++;
                novoNo.imprime();
                abreNo(novoNo.cidade2);
                if (novoNo.cidade2.equals(CIDADE_FINAL)) {
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        possiveisEstados.add(new Estado(1, "a", "b"));
        possiveisEstados.add(new Estado(2, "a", "b"));
        possiveisEstados.add(new Estado(3, "a", "d"));
        possiveisEstados.add(new Estado(4, "b", "e"));
        possiveisEstados.add(new Estado(5, "b", "f"));
        possiveisEstados.add(new Estado(6, "c", "g"));
        possiveisEstados.add(new Estado(7, "c", "h"));
        possiveisEstados.add(new Estado(8, "c", "i"));
        possiveisEstados.add(new Estado(9, "d", "j"));
        possiveisEstados.add(new Estado(10, "e", "k"));
        possiveisEstados.add(new Estado(11, "e", "l"));
        possiveisEstados.add(new Estado(12, "g", "m"));
        possiveisEstados.add(new Estado(13, "j", "n"));
        possiveisEstados.add(new Estado(14, "j", "o"));
        possiveisEstados.add(new Estado(15, "k", "f"));
        possiveisEstados.add(new Estado(16, "l", "h"));
        possiveisEstados.add(new Estado(17, "m", "d"));
        possiveisEstados.add(new Estado(18, "o", "a"));
        possiveisEstados.add(new Estado(19, "n", "b"));

        nrEstado = 0;
        System.out.println("\nBusca em profundidade:");
        buscarSolucaoProfundidade(CIDADE_INICIAL);

        visitados.clear();
        naoVisitados.clear();

        nrEstado = 0;
        System.out.println("\nBusca em largura:");
        buscarSolucaoLargura(CIDADE_INICIAL);
    }

}
