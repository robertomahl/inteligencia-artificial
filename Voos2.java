// TODO: não feito

import java.util.*;

public class Voos2 {

	// Classe para representar um estado
	class Estado {
		int voo;
		String cidade1;
		String cidade2;
		Estado pai;

		// Construtor
		Estado(int voo, String cidade1, String cidade2) {
			this.voo = voo;
			this.cidade1 = cidade1;
			this.cidade2 = cidade2;
		}

		void printaEstado(int numeroestado) {
			if (numeroestado > 0) {
				System.out.println("voo(" + cidade1 + ", " + cidade2 + ", " + voo + ") = Numero: " + numeroestado);
			} else {
				System.out.println("voo(" + cidade1 + ", " + cidade2 + ", " + voo + ")");
			}
		}

		void addPai(Estado pai) {
			this.pai = pai;
		}

		// Verifica se o estado é igual
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Estado) {
				Estado other = (Estado) obj;
				// Verifica se a voo é igual
				if (this.voo != other.voo) {
					return false;
				}
				// Verifica se a cidade1 é igual
				if (!this.cidade1.equals(other.cidade1)) {
					return false;
				}
				// Verifica se a cidade2 é igual
				if (!this.cidade2.equals(other.cidade2)) {
					return false;
				}
				return true;
			}
			return false;
		}
	}

	// Lista de todos os estados
	static List<Estado> estados = new ArrayList<>();
	static {
		estados.add(new Estado(1, "a", "b"));
		estados.add(new Estado(9, "a", "c"));
		estados.add(new Estado(4, "a", "d"));
		estados.add(new Estado(7, "b", "c"));
		estados.add(new Estado(6, "b", "e"));
		estados.add(new Estado(1, "b", "f"));
		estados.add(new Estado(7, "c", "f"));
		estados.add(new Estado(4, "d", "f"));
		estados.add(new Estado(5, "d", "g"));
		estados.add(new Estado(9, "e", "h"));
		estados.add(new Estado(4, "f", "h"));
		estados.add(new Estado(1, "g", "h"));
	}

	// Cidade inicial
	static String cidade_inicial = "a";

	// Cidade final
	static String cidade_final = "h";

	// Cidades visitadas
	static List<String> cidades_visitadas = new ArrayList<>();

	// Cidade atual
	static String cidade_atual = null;

	// Pilha de estados
	static Stack<Estado> estados_nao_visitados = new Stack<>();

	// Estado acessado número de forma global
	static int numeroestado = 1;

	// Ultimo estado acessado
	static Estado ultimoestado = null;

	static void abreno(String no, Estado estadoOrigem) {
		for (Estado estado : estados) {
			if (estado.cidade1.equals(no) && !cidades_visitadas.contains(estado.cidade2)) {
				estado.addPai(estadoOrigem);
				estados_nao_visitados.push(estado);
			}
			if (estado.cidade2.equals(no) && !cidades_visitadas.contains(estado.cidade1)) {
				estado.addPai(estadoOrigem);
				estados_nao_visitados.push(estado);
			}
		}
		cidades_visitadas.add(no);
	}

	// Abre o nó guloso
	static void abrenoGuloso(String no, Estado estadoOrigem) {
		Estado menorCusto = null;
		for (Estado estado : estados) {
			if (estado.cidade1.equals(no) && !cidades_visitadas.contains(estado.cidade2)) {
				if (menorCusto == null || estado.voo < menorCusto.voo) {
					menorCusto = estado;
				}
			}
			if (estado.cidade2.equals(no) && !cidades_visitadas.contains(estado.cidade1)) {
				if (menorCusto == null || estado.voo < menorCusto.voo) {
					menorCusto = estado;
				}
			}
		}

		if (menorCusto != null) {
			menorCusto.addPai(estadoOrigem);
			estados_nao_visitados.push(menorCusto);
		}
		cidades_visitadas.add(no);
	}

	// Cidade que esta na lista de cidades visitadas
	static String cidade_visitada(Estado estado) {
		if (cidades_visitadas.contains(estado.cidade1)) {
			return estado.cidade1;
		}
		if (cidades_visitadas.contains(estado.cidade2)) {
			return estado.cidade2;
		}
		return null;
	}

	// Cidade que nao esta na lista de cidades visitadas
	static String cidade_nao_visitada(Estado estado) {
		if (!cidades_visitadas.contains(estado.cidade1)) {
			return estado.cidade1;
		}
		if (!cidades_visitadas.contains(estado.cidade2)) {
			return estado.cidade2;
		}
		return null;
	}

	// Acessa o estado
	static void acessa_estado_busca_profundidade(String cidade) {
		cidade_atual = cidade;
		String destino = null;
		if (verifica_fim(cidade)) {
			return;
		}
		abreno(cidade, null);
		while (!estados_nao_visitados.isEmpty()) {
			Estado nonovo = estados_nao_visitados.pop();
			cidade_atual = cidade_visitada(nonovo);
			destino = cidade_nao_visitada(nonovo);
			if (destino != null && !cidades_visitadas.contains(destino)) {
				numeroestado++;
				nonovo.printaEstado(numeroestado);
				abreno(destino, nonovo);
				if (verifica_fim(destino)) {
					ultimoestado = nonovo;
					break;
				}
			}
		}
		caminho();
	}

	// Pega o caminho
	static void caminho() {
		// Mostra o caminho considerando o pai
		System.out.println("\tCaminho");
		boolean fim = false;
		while (!fim) {
			System.out.print("\t\t");
			ultimoestado.printaEstado(0);
			if (ultimoestado.pai == null) {
				fim = true;
			}
			ultimoestado = ultimoestado.pai;
		}
	}

	static boolean verifica_fim(String cidade) {
		if (cidade.equals(cidade_final)) {
			System.out.println("Cidade final encontrada");
			return true;
		}
		return false;
	}

	// Busca em Largura
	static void acessa_estado_busca_largura(String cidade) {
		cidade_atual = cidade;
		String destino = null;
		if (verifica_fim(cidade)) {
			return;
		}
		abreno(cidade, null);
		while (!estados_nao_visitados.isEmpty()) {
			Estado nonovo = estados_nao_visitados.remove();
			cidade_atual = cidade_visitada(nonovo);
			destino = cidade_nao_visitada(nonovo);
			if (destino != null && !cidades_visitadas.contains(destino)) {
				numeroestado++;
				nonovo.printaEstado(numeroestado);
				abreno(destino, nonovo);
				if (verifica_fim(destino)) {
					ultimoestado = nonovo;
					break;
				}
			}
		}
		caminho();
	}

	// Busca gulosa
	static void acessa_estado_busca_gulosa(String cidade) {
		cidade_atual = cidade;
		String destino = null;
		if (verifica_fim(cidade)) {
			return;
		}
		abrenoGuloso(cidade, null);
		while (estados_nao_visitados.size() == 1) {
			Estado nonovo = estados_nao_visitados.pop();
			cidade_atual = cidade_visitada(nonovo);
			destino = cidade_nao_visitada(nonovo);
			if (destino != null && !cidades_visitadas.contains(destino)) {
				numeroestado++;
				nonovo.printaEstado(numeroestado);
				abrenoGuloso(destino, nonovo);
				if (verifica_fim(destino)) {
					ultimoestado = nonovo;
					break;
				}
			}
		}
		caminho();
	}

	// Apaga as informações
	static void apaga_Informacoes() {
		for (Estado estado : estados) {
			estado.pai = null;
		}
		cidades_visitadas.clear();
		estados_nao_visitados.clear();
		numeroestado = 0;
		cidade_atual = null;
	}

	public static void main(String[] args) {
		// Apaga as informações
		apaga_Informacoes();

		System.out.println("Busca em profundidade");
		// Acessa o estado inicial
		acessa_estado_busca_profundidade(cidade_inicial);

		// Apaga as informações
		apaga_Informacoes();

		System.out.println("\nBusca em largura");
		// Acessa o estado inicial
		acessa_estado_busca_largura(cidade_inicial);

		// Apaga as informações
		apaga_Informacoes();

		System.out.println("\nBusca gulosa");
		// Acessa o estado inicial
		acessa_estado_busca_gulosa(cidade_inicial);
	}

}
