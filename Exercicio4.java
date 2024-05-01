/**
 * Exercício 4 - Voos 1
 */
import java.util.*;

public class Exercicio4 {

    public static final String CIDADE_INICIAL = "a";
    public static final String CIDADE_FINAL = "j";
    
    public static List<Estado> possiveisEstados = new ArrayList<>();
    public static List<String> visitados = new ArrayList<>();
    public static List<Estado> naoVisitados = new ArrayList<>();

	static int nrEstado = 0;

    static class Estado {
        int voo;
        String cidade1;
        String cidade2;
        Estado pai = null;

        public Estado(int voo, String cidade1, String cidade2) {
            this.voo = voo;
            this.cidade1 = cidade1;
            this.cidade2 = cidade2;
        }

        public void setPai(Estado pai) {
            this.pai = pai;
        }

        @Override
        public String toString() {
            return "oper(" + voo + ", " + cidade1 + ", " + cidade2 + ")";
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

    private static void abrirNo(String no, Estado estadoOrigem) {
        for (Estado estado : possiveisEstados) {
            if (estado.cidade1.equals(no) && !visitados.contains(estado.cidade2)) {
                estado.setPai(estadoOrigem);
                naoVisitados.add(estado);
            }
        }
        visitados.add(no);
    }

    private static void concluirBusca(Estado ultimoNo) {
		System.out.println("\nCidade final encontrada. Percurso: ");
		imprimirRecursivamente(ultimoNo);
	}

    private static void imprimirRecursivamente(Estado estado) {
        if (estado.pai != null) {
            imprimirRecursivamente(estado.pai);
        }
        System.out.println(estado.toString());
    }

    public static void buscarSolucaoProfundidade(String cidade) {
        reinicializarVariaveis();

        abrirNo(cidade, null);
        while (!naoVisitados.isEmpty()) {
            Estado novoNo = naoVisitados.remove(naoVisitados.size() - 1);
            
            if (!visitados.contains(novoNo.cidade2)) {
                System.out.println("Estado " + nrEstado++ + ": " + novoNo.toString());
                abrirNo(novoNo.cidade2, novoNo);
                if (novoNo.cidade2.equals(CIDADE_FINAL)) {
                    concluirBusca(novoNo);
                    return;
                }
            }
        }
    }

    public static void buscarSolucaoLargura(String cidade) {
        reinicializarVariaveis();

        abrirNo(cidade, null);
        while (!naoVisitados.isEmpty()) {
            Estado novoNo = naoVisitados.remove(0);
            
            if (!visitados.contains(novoNo.cidade2)) {
			    System.out.println("Estado " + nrEstado++ + ": " + novoNo.toString());
                abrirNo(novoNo.cidade2, novoNo);
                if (novoNo.cidade2.equals(CIDADE_FINAL)) {
                    concluirBusca(novoNo);
                    return;
                }
            }
        }
    }

    private static void reinicializarVariaveis() {
		for (Estado estado : possiveisEstados) {
			estado.pai = null;
		}
		visitados.clear();
		naoVisitados.clear();
		nrEstado = 0;
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

        System.out.println("\nBusca em profundidade:");
        buscarSolucaoProfundidade(CIDADE_INICIAL);

        System.out.println("\nBusca em largura:");
        buscarSolucaoLargura(CIDADE_INICIAL);
    }

}
