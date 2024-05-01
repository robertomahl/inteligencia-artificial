/**
 * Exercício 3 - Fazendeiro
 */
import java.util.*;

public class Exercicio3 {

	public static List<Estado> visitados = new ArrayList<>();
	public static Stack<Estado> naoVisitados = new Stack<>();

	static int nrEstado = 0;

	static class Estado {

		String lobo;
		String ovelha;
		String repolho;
		String fazendeiro;
		Estado pai;

		public Estado(String fazendeiro, String lobo, String ovelha, String repolho, Estado pai) {
			this.lobo = lobo;
			this.ovelha = ovelha;
			this.repolho = repolho;
			this.fazendeiro = fazendeiro;
			this.pai = pai;
		}

		public boolean eValido() {
			if (this.lobo.equals(this.ovelha) && !this.fazendeiro.equals(this.lobo)) {
				return false;
			}
			if (this.ovelha.equals(this.repolho) && !this.fazendeiro.equals(this.ovelha)) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			return fazendeiro + " | " + lobo + " | " + ovelha + " | " + repolho;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Estado) {
				Estado estado = (Estado) obj;
				return this.fazendeiro.equals(estado.fazendeiro) && this.lobo.equals(estado.lobo)
						&& this.ovelha.equals(estado.ovelha) && this.repolho.equals(estado.repolho);
			}
			return false;
		}
	}

	private static void concluirBusca(Estado ultimoNo) {
		System.out.println("\nSolução encontrada. Caminho: ");
		imprimirRecursivamente(ultimoNo);
	}

    private static void imprimirRecursivamente(Estado estado) {
        if (estado.pai != null) {
            imprimirRecursivamente(estado.pai);
        }
        System.out.println(estado.toString());
    }

	public static void buscarSolucao(Estado estadoInicial, Estado estadoFinal) {
		System.out.println("Estado " + nrEstado++ + ": " + estadoInicial.toString());

		abrirNo(estadoInicial);
		while (!naoVisitados.isEmpty()) {
			Estado novoNo = naoVisitados.pop();
			System.out.println("Estado " + nrEstado++ + ": " + novoNo.toString());
			
			abrirNo(novoNo);
			if (novoNo.equals(estadoFinal)) {
				concluirBusca(novoNo);
				return;
			}
		}
		System.out.println("Solução não encontrada.");
	}

	private static void abrirNo(Estado estado) {
		Stack<Estado> proximosEstados = new Stack<>();

		String novaMargem = estado.fazendeiro.equals("e") ? "d" : "e";
		proximosEstados.add(new Estado(novaMargem, estado.lobo, estado.ovelha, estado.repolho, estado));
		proximosEstados.add(
				new Estado(novaMargem, estado.lobo.equals("e") ? "d" : "e", estado.ovelha, estado.repolho, estado));
		proximosEstados.add(
				new Estado(novaMargem, estado.lobo, estado.ovelha.equals("e") ? "d" : "e", estado.repolho, estado));
		proximosEstados.add(
				new Estado(novaMargem, estado.lobo, estado.ovelha, estado.repolho.equals("e") ? "d" : "e", estado));

		proximosEstados.removeIf(visitados::contains);
		proximosEstados.removeIf(naoVisitados::contains);
		proximosEstados.removeIf(estadoAtual -> !estadoAtual.eValido());

		while (!proximosEstados.isEmpty()) {
			naoVisitados.add(proximosEstados.pop());
		}
		visitados.add(estado);
	}

	public static void main(String[] args) {
		Estado estadoInicial = new Estado("e", "e", "e", "e", null);
		Estado estadoFinal = new Estado("d", "d", "d", "d", null);

		buscarSolucao(estadoInicial, estadoFinal);
	}

}