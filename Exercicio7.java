/**
 * Exercício 7 - A*
 */
import java.util.*;

public class Exercicio7 {

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
        int custo;

		public Estado(No cidade1, No cidade2, int custo) {
			this.cidade1 = cidade1;
			this.cidade2 = cidade2;
            this.custo = custo;
		}

		public void setPai(Estado pai) {
			this.pai = pai;
		}

		@Override
		public String toString() {
			return "rota(" + cidade1 + ", " + cidade2 + ", custo = " + custo + ")";
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
		List<Estado> filhos = new ArrayList<>();
		for (Estado estado : possiveisEstados) {
			if (estado.cidade1.cidade.equals(no) && !visitados.contains(estado.cidade2.cidade)) {
                estado.setPai(estadoOrigem);
				filhos.add(estado);
			} else if (estado.cidade2.cidade.equals(no) && !visitados.contains(estado.cidade1.cidade)) {
                estado.setPai(estadoOrigem);
				filhos.add(estado);
			}
		}
        
        filhos.sort(Comparator.comparingInt((Estado a) -> (a.custo + getNoNaoVisitado(a).heuristica)).reversed());

		naoVisitados.addAll(filhos);
		visitados.add(no);
	}

	private static String getCidadeNaoVisitada(Estado estado) {
		return getNoNaoVisitado(estado)!=null ? getNoNaoVisitado(estado).cidade : null;
	}

	private static No getNoNaoVisitado(Estado estado) {
		return !visitados.contains(estado.cidade1.cidade) ? estado.cidade1 : !visitados.contains(estado.cidade2.cidade) ? estado.cidade2 : null;
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
		
		possiveisEstados.add(new Estado(a, b, 7));
		possiveisEstados.add(new Estado(a, c, 9));
		possiveisEstados.add(new Estado(a, d, 3));
		possiveisEstados.add(new Estado(b, f, 3));
		possiveisEstados.add(new Estado(b, i, 4));
		possiveisEstados.add(new Estado(c, j, 5));
		possiveisEstados.add(new Estado(d, e, 1));
		possiveisEstados.add(new Estado(i, k, 5));
		possiveisEstados.add(new Estado(f, g, 2));
		possiveisEstados.add(new Estado(g, h, 3));
		possiveisEstados.add(new Estado(j, l, 6));
		possiveisEstados.add(new Estado(l, k, 4));

		System.out.println("\nBusca A*: ");
		buscarSolucaoGulosa(CIDADE_INICIAL);
	}

}
