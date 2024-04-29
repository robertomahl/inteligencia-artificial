/**
 * Exercício 5 - Voos 2
 */
import java.util.*;

public class Voos2 {

	static final String CIDADE_INICIAL = "a";
	static final String CIDADE_FINAL = "h";
	static List<Estado> possiveisEstados = new ArrayList<>();
	static List<String> visitados = new ArrayList<>();
	static List<Estado> naoVisitados = new ArrayList<>();

	static String cidadeAtual = null;
	static int nrEstado = 0;

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
			System.out.println("voo(" + cidade1 + ", " + cidade2 + ", " + voo + ")");
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

	static void abreNo(String no, Estado estadoOrigem) {
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

	static void abreNoGuloso(String no, Estado estadoOrigem) {
		Estado menorCusto = null;
		for (Estado estado : possiveisEstados) {
			if (estado.cidade1.equals(no) && !visitados.contains(estado.cidade2)) {
				if (menorCusto == null || estado.voo < menorCusto.voo) {
					menorCusto = estado;
				}
			}
			if (estado.cidade2.equals(no) && !visitados.contains(estado.cidade1)) {
				if (menorCusto == null || estado.voo < menorCusto.voo) {
					menorCusto = estado;
				}
			}
		}
		if (menorCusto != null) {
			menorCusto.addPai(estadoOrigem);
			naoVisitados.add(menorCusto);
		}
		visitados.add(no);
	}

	static String getCidadeJaVisitada(Estado estado) {
		return visitados.contains(estado.cidade1) ? estado.cidade1 : estado.cidade2;
	}

	static String getCidadeNaoVisitada(Estado estado) {
		return visitados.contains(estado.cidade1) ? estado.cidade2 : estado.cidade1;
	}

	static void concluirBusca(Estado ultimoNo) {
		System.out.println("Cidade final encontrada. Percurso: ");
		boolean fim = false;
		while (!fim) {
			ultimoNo.imprime();
			if (ultimoNo.pai == null) {
				fim = true;
			}
			ultimoNo = ultimoNo.pai;
		}
	}
 
	static void buscarSolucaoProfundidade(String cidade) {
		cidadeAtual = cidade;
		String destino = null;

		abreNo(cidade, null);
		while (!naoVisitados.isEmpty()) {
			Estado novoNo = naoVisitados.remove(naoVisitados.size() - 1);
			cidadeAtual = getCidadeJaVisitada(novoNo);
			destino = getCidadeNaoVisitada(novoNo);
			if (destino != null && !visitados.contains(destino)) {
				nrEstado++;
				novoNo.imprime();
				abreNo(destino, novoNo);
				if (destino.equals(CIDADE_FINAL)) {
					concluirBusca(novoNo);
					return;
				}
			}
		}
		System.out.println("Cidade final não encontrada");
	}

	static void buscarSolucaoLargura(String cidade) {
		cidadeAtual = cidade;
		String destino = null;

		abreNo(cidade, null);
		while (!naoVisitados.isEmpty()) {
			Estado novoNo = naoVisitados.remove(0);
			cidadeAtual = getCidadeJaVisitada(novoNo);
			destino = getCidadeNaoVisitada(novoNo);
			if (destino != null && !visitados.contains(destino)) {
				nrEstado++;
				novoNo.imprime();
				abreNo(destino, novoNo);
				if (destino.equals(CIDADE_FINAL)) {
					concluirBusca(novoNo);
					return;
				}
			}
		}
		System.out.println("Cidade final não encontrada");
	}

	static void buscarSolucaoGulosa(String cidade) {
		cidadeAtual = cidade;
		String destino = null;
		
		abreNoGuloso(cidade, null);
		while (naoVisitados.size() == 1) {
			Estado novoNo = naoVisitados.remove(0);
			cidadeAtual = getCidadeJaVisitada(novoNo);
			destino = getCidadeNaoVisitada(novoNo);
			if (destino != null && !visitados.contains(destino)) {
				nrEstado++;
				novoNo.imprime();
				abreNoGuloso(destino, novoNo);
				
				if (destino.equals(CIDADE_FINAL)) {
					concluirBusca(novoNo);
					return;
				}
			}
		}
		System.out.println("Cidade final não encontrada");
	}

	static void reinicializarVariaveis() {
		for (Estado estado : possiveisEstados) {
			estado.pai = null;
		}
		visitados.clear();
		naoVisitados.clear();
		nrEstado = 0;
		cidadeAtual = null;
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

		reinicializarVariaveis();

		System.out.println("\nBusca em largura: ");
		buscarSolucaoLargura(CIDADE_INICIAL);

		reinicializarVariaveis();

		System.out.println("\nBusca gulosa: ");
		buscarSolucaoGulosa(CIDADE_INICIAL);
	}

}
