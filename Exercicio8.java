/**
 * Exercício 8 - Quebra cabeça
 */
import java.util.*;

public class Exercicio8 {

	public static List<Estado> visitados = new ArrayList<>();
	public static List<Estado> naoVisitados = new ArrayList<>();

	public static int[][] matrizFinal = {{1, 2, 3}, {8, 0, 4}, {7, 6, 5}};
	public static Estado estadoFinal = new Estado(matrizFinal);

	static int nrEstado = 0;

	static class Estado {
		int[][] matriz;
		Estado pai;
        int distancia;

		public Estado(int[][] matriz) {
			this.matriz = matriz;
		}
		
		public void setPai(Estado pai) {
			this.pai = pai;
		}

		public void imprime() {
			System.out.println("Distância: " + distancia);
			System.out.println(this.toString());	
		}

		public void calcularDistancia() {
			this.distancia = 0;
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					int valorAtual = this.matriz[i][j];
					if (valorAtual != 0) {
						int[] posicaoFinal = estadoFinal.encontrarPosicao(valorAtual);
						this.distancia += Math.abs(i - posicaoFinal[0]) + Math.abs(j - posicaoFinal[1]);
					}
				}
			}
		}	

		public int[] encontrarPosicao(int numero) {
			int[] posicao = new int[2];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (this.matriz[i][j] == numero) {
						posicao[0] = i;
						posicao[1] = j;
						return posicao;
					}
				}
			}
			return null;
		}	

		@Override
		public String toString() {
			String saida = "";
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (matriz[i][j] == 0) {
						saida += "  ";
					} else {
						saida += matriz[i][j] + " ";
					}
				}
				saida += "\n";
			}
			return saida;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Estado estado = (Estado) o;
			return Arrays.deepEquals(matriz, estado.matriz);
		}

		@Override
		public int hashCode() {
			return Arrays.deepHashCode(matriz);
		}
	
	}

	private static List<Estado> obterProximosEstados(Estado expandido) {
		List<Estado> estados = new ArrayList<>();

		int[] posicaoZero = expandido.encontrarPosicao(0);
        int linha = posicaoZero[0];
        int coluna = posicaoZero[1];

        // Cima
        if (linha > 0) {
            estados.add(criarEstadoTroca(expandido, linha, coluna, linha - 1, coluna));
        }
        // Baixo
        if (linha < 2) {
            estados.add(criarEstadoTroca(expandido, linha, coluna, linha + 1, coluna));
        }
        // Esquerda
        if (coluna > 0) {
            estados.add(criarEstadoTroca(expandido, linha, coluna, linha, coluna - 1));
        }
        // Direita
        if (coluna < 2) {
            estados.add(criarEstadoTroca(expandido, linha, coluna, linha, coluna + 1));
        }
		estados.forEach(e -> e.setPai(expandido));
		
		return estados;
	}

	private static void abrirNoGuloso(Estado expandido) {		
		visitados.add(expandido);
		
		List<Estado> estados = obterProximosEstados(expandido);

		estados.forEach(Estado::calcularDistancia);
		
		naoVisitados.addAll(estados);
		
		Collections.sort(naoVisitados, Comparator.comparingInt((Estado e) -> e.distancia).reversed());
   }

	private static void abrirNo(Estado expandido) {		
		visitados.add(expandido);
		
		List<Estado> estados = obterProximosEstados(expandido);

		naoVisitados.addAll(estados);
   }

	private static Estado criarEstadoTroca(Estado expandido, int linhaAtual, int colunaAtual, int linhaNova, int colunaNova) {
		int[][] novaMatriz = new int[3][3];
		for (int i = 0; i < 3; i++) {
			System.arraycopy(expandido.matriz[i], 0, novaMatriz[i], 0, 3);
		}
		
		int temp = novaMatriz[linhaAtual][colunaAtual];
		novaMatriz[linhaAtual][colunaAtual] = novaMatriz[linhaNova][colunaNova];
		novaMatriz[linhaNova][colunaNova] = temp;
		return new Estado(novaMatriz);
	}

	private static void concluirBusca(Estado ultimoNo) {
		System.out.println("Solução encontrada. Percurso: ");
		imprimirRecursivamente(ultimoNo);
	}

    private static void imprimirRecursivamente(Estado estado) {
        if (estado.pai != null) {
            imprimirRecursivamente(estado.pai);
        }
        System.out.println(estado);
    }

	public static void buscarSolucaoGulosa(Estado estadoInicial) {		
        reinicializarVariaveis();
		
		abrirNoGuloso(estadoInicial);
		while (!naoVisitados.isEmpty()) {
			Estado estado = naoVisitados.remove(naoVisitados.size() - 1);
			
			if (estado != null) {
			    estado.imprime();
				
				abrirNoGuloso(estado);
				if (estado.equals(estadoFinal)) {
					concluirBusca(estado);
					return;
				}
			}
		}
		System.out.println("Solução não encontrada.");
	}

	public static void buscarSolucaoLargura(Estado estadoInicial) {
        reinicializarVariaveis();

        abrirNo(estadoInicial);
        while (!naoVisitados.isEmpty()) {
            Estado estado = naoVisitados.remove(0);
            
            if (!visitados.contains(estado) && !naoVisitados.contains(estadoFinal)) {
			    estado.imprime();

                abrirNo(estado);
                if (estado.equals(estadoFinal)) {
                    concluirBusca(estado);
                    return;
                }
            }
        }
		System.out.println("Solução não encontrada.");
    }

    private static void reinicializarVariaveis() {
		visitados.clear();
		naoVisitados.clear();
		nrEstado = 0;
	}

	public static void main(String[] args) {
		System.out.println("\nCusto 1: ");
        
		int[][] matrizInicial1 = {{1, 3, 4}, {8, 2, 5}, {7, 6, 0}};
		Estado estadoInicial1 = new Estado(matrizInicial1);
		buscarSolucaoLargura(estadoInicial1);


		System.out.println("\nHeurística: ");
        
		int[][] matrizInicial2 = {{1, 3, 4}, {8, 2, 5}, {7, 6, 0}};
		Estado estadoInicial2 = new Estado(matrizInicial2);
		buscarSolucaoGulosa(estadoInicial2);
	}

}
