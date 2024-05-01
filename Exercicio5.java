/**
 * Exercício 5 - Voos 2
 */
import java.util.*;

public class Exercicio5 {

	static final String CIDADE_INICIAL = "a";
	static final String CIDADE_FINAL = "h";
	
	static List<Estado> possiveisEstados = new ArrayList<>();
	static List<String> visitados = new ArrayList<>();
	static List<Estado> naoVisitados = new ArrayList<>();

	static int nrEstado = 0;

	static class Estado {
		int voo;
		String cidade1;
		String cidade2;
		Estado pai;

		public Estado(int voo, String cidade1, String cidade2) {
			this.voo = voo;
			this.cidade1 = cidade1;
			this.cidade2 = cidade2;
		}

		public void addPai(Estado pai) {
			this.pai = pai;
		}

		@Override
		public String toString() {
			return "voo(" + cidade1 + ", " + cidade2 + ", " + voo + ")";
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
				estado.addPai(estadoOrigem);
				naoVisitados.add(estado);
			}
			if (estado.cidade2.equals(no) && !visitados.contains(estado.cidade1)) {
				estado.addPai(estadoOrigem);
				naoVisitados.add(estado);
			}
		}
		visitados.add(no);
	}
	
	private static void abrirNoGuloso(String no, Estado estadoOrigem) {
		int menorCusto = possiveisEstados.stream()
			.filter(estado -> (estado.cidade1.equals(no) && !visitados.contains(estado.cidade2)) || (estado.cidade2.equals(no) && !visitados.contains(estado.cidade1)))
			.map(estado -> estado.voo)
			.min(Integer::compare)
			.orElse(-1);

		for (Estado estado : possiveisEstados) {
			if (estado.voo == menorCusto && ((estado.cidade1.equals(no) && !visitados.contains(estado.cidade2)) || (estado.cidade2.equals(no) && !visitados.contains(estado.cidade1)))) {
				estado.addPai(estadoOrigem);
				naoVisitados.add(estado);
			}
		}
		
		visitados.add(no);
	}

	private static String getCidadeNaoVisitada(Estado estado) {
		return !visitados.contains(estado.cidade1) ? estado.cidade1 : !visitados.contains(estado.cidade2) ? estado.cidade2 : null;
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
 
	public static void buscarSolucaoProfundidade(String cidade) {
		reinicializarVariaveis();

		abrirNo(cidade, null);
		while (!naoVisitados.isEmpty()) {
			Estado novoNo = naoVisitados.remove(naoVisitados.size() - 1);
			String destino = getCidadeNaoVisitada(novoNo);
			
			if (destino != null) {
			    System.out.println("Estado " + nrEstado++ + ": " + novoNo.toString());
				
				abrirNo(destino, novoNo);
				if (destino.equals(CIDADE_FINAL)) {
					concluirBusca(novoNo);
					return;
				}
			}
		}
		System.out.println("Cidade final não encontrada");
	}

	public static void buscarSolucaoLargura(String cidade) {
		reinicializarVariaveis();

		abrirNo(cidade, null);
		while (!naoVisitados.isEmpty()) {
			Estado novoNo = naoVisitados.remove(0);
			String destino = getCidadeNaoVisitada(novoNo);
			
			if (destino != null) {
			    System.out.println("Estado " + nrEstado++ + ": " + novoNo.toString());
				
				abrirNo(destino, novoNo);
				if (destino.equals(CIDADE_FINAL)) {
					concluirBusca(novoNo);
					return;
				}
			}
		}
		System.out.println("Cidade final não encontrada");
	}

	public static void buscarSolucaoGulosa(String cidade) {
		reinicializarVariaveis();
		
		abrirNoGuloso(cidade, null);
		while (!naoVisitados.isEmpty()) {
			Estado novoNo = naoVisitados.remove(0);
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
		possiveisEstados.add(new Estado(9, "a", "c"));
		possiveisEstados.add(new Estado(4, "a", "d"));
		possiveisEstados.add(new Estado(7, "b", "c"));
		possiveisEstados.add(new Estado(6, "b", "e"));
		possiveisEstados.add(new Estado(1, "b", "f"));
		possiveisEstados.add(new Estado(7, "c", "f"));
		possiveisEstados.add(new Estado(4, "d", "f"));
		possiveisEstados.add(new Estado(5, "d", "g"));
		possiveisEstados.add(new Estado(9, "e", "h"));
		possiveisEstados.add(new Estado(4, "f", "h"));
		possiveisEstados.add(new Estado(1, "g", "h"));

		System.out.println("\nBusca em profundidade: ");
		buscarSolucaoProfundidade(CIDADE_INICIAL);

		System.out.println("\nBusca em largura: ");
		buscarSolucaoLargura(CIDADE_INICIAL);

		System.out.println("\nBusca gulosa: ");
		buscarSolucaoGulosa(CIDADE_INICIAL);
	}

}
