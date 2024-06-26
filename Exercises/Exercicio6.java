/**
 * Exercício 6 - Rotas
 */
import java.util.*;

public class Exercicio6 {

	public static final String CIDADE_INICIAL = "a";
	public static final String CIDADE_FINAL = "k";

	public static List<Estado> possiveisEstados = new ArrayList<>();
	public static List<String> visitados = new ArrayList<>();
	public static List<Estado> naoVisitados = new ArrayList<>();

	static int nrEstado = 0;

	static class No {
		String cidade;
		int heuristica;

		public No(String cidade, int heuristica) {
			this.cidade = cidade;
			this.heuristica = heuristica;
		}

		@Override
		public String toString() {
			return cidade + " (h=" + heuristica + ")";
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof No) {
				No other = (No) obj;
				return this.cidade.equals(other.cidade);
			}
			return false;
		}
	}

	static class Estado {
		No cidade1;
		No cidade2;
		Estado pai;

		public Estado(No cidade1, No cidade2) {
			this.cidade1 = cidade1;
			this.cidade2 = cidade2;
		}

		public void setPai(Estado pai) {
			this.pai = pai;
		}

		@Override
		public String toString() {
			return "rota(" + cidade1 + ", " + cidade2 + ")";
		} 
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Estado) {
				Estado other = (Estado) obj;
				return this.cidade1.equals(other.cidade1) && this.cidade2.equals(other.cidade2);
			}
			return false;
		}
	}

	private static void abrirNoGuloso(String no, Estado estadoOrigem) {
		List<Estado> filhosCidade2 = new ArrayList<>();
		List<Estado> filhosCidade1 = new ArrayList<>();
		for (Estado estado : possiveisEstados) {
			if (estado.cidade1.cidade.equals(no) && !visitados.contains(estado.cidade2.cidade)) {
                estado.setPai(estadoOrigem);
				filhosCidade2.add(estado);
			} else if (estado.cidade2.cidade.equals(no) && !visitados.contains(estado.cidade1.cidade)) {
                estado.setPai(estadoOrigem);
				filhosCidade1.add(estado);
			}
		}
		filhosCidade2.sort((a, b) -> b.cidade2.heuristica - a.cidade2.heuristica);
		filhosCidade1.sort((a, b) -> b.cidade1.heuristica - a.cidade1.heuristica);

		List<Estado> filhos = new ArrayList<>();
		if (filhosCidade1.isEmpty() || filhosCidade2.isEmpty()) {
			filhos.addAll(filhosCidade1);
			filhos.addAll(filhosCidade2);
		} else {
			int i = 0, j = 0;
			while (i < filhosCidade1.size() && j < filhosCidade2.size()) {
				if (filhosCidade1.get(i).cidade1.heuristica > filhosCidade2.get(j).cidade2.heuristica) {
					filhos.add(filhosCidade1.get(i++));
				} else {
					filhos.add(filhosCidade2.get(j++));
				}
			}
		}

		naoVisitados.addAll(filhos);
		visitados.add(no);
	}

	private static String getCidadeNaoVisitada(Estado estado) {
		return !visitados.contains(estado.cidade1.cidade) ? estado.cidade1.cidade : !visitados.contains(estado.cidade2.cidade) ? estado.cidade2.cidade : null;
	}

	private static void concluirBusca(Estado ultimoNo) {
		System.out.println("Cidade final encontrada. Percurso: ");
		imprimirRecursivamente(ultimoNo);
	}

    private static void imprimirRecursivamente(Estado estado) {
        if (estado.pai != null) {
            imprimirRecursivamente(estado.pai);
        }
        System.out.println(estado);
    }

	public static void buscarSolucaoGulosa(String cidade) {		
		abrirNoGuloso(cidade, null);
		while (!naoVisitados.isEmpty()) {
			Estado novoNo = naoVisitados.remove(naoVisitados.size() - 1);
			String destino = getCidadeNaoVisitada(novoNo);
			
			if (destino != null) {
			    System.out.println("Estado " + nrEstado++ + ": " + novoNo.toString());
				
				abrirNoGuloso(destino, novoNo);
				if (destino.equals(CIDADE_FINAL)) {
					concluirBusca(novoNo);
					return;
				}
			}
		}
		System.out.println("Cidade final não encontrada");
	}


	public static void main(String[] args) {
		No a = new No("a", 15);
		No b = new No("b", 7);
		No c = new No("c", 6);
		No d = new No("d", 14);
		No e = new No("e", 15);
		No f = new No("f", 7);
		No g = new No("g", 8);
		No h = new No("h", 5);
		No i = new No("i", 5);
		No j = new No("j", 3);
		No k = new No("k", 0);
		No l = new No("l", 4);
		
		possiveisEstados.add(new Estado(a, b));
		possiveisEstados.add(new Estado(a, c));
		possiveisEstados.add(new Estado(a, d));
		possiveisEstados.add(new Estado(b, f));
		possiveisEstados.add(new Estado(b, i));
		possiveisEstados.add(new Estado(c, j));
		possiveisEstados.add(new Estado(d, e));
		possiveisEstados.add(new Estado(i, k));
		possiveisEstados.add(new Estado(f, g));
		possiveisEstados.add(new Estado(g, h));
		possiveisEstados.add(new Estado(j, l));
		possiveisEstados.add(new Estado(l, k));

		System.out.println("\nBusca gulosa: ");
		buscarSolucaoGulosa(CIDADE_INICIAL);
	}

}
