/**
 * Exercício 2 - Jarros 
 */
import java.util.*;

public class Exercicio2 {

    public static final int OBJETIVO = 2;

    static class Estado {
        int jarro3;
        int jarro4;

        public Estado(int jarro3, int jarro4) {
            this.jarro3 = jarro3;
            this.jarro4 = jarro4;
        }

        public boolean eValido() {
            return this.jarro3 >= 0 && this.jarro3 <= 3 && this.jarro4 >= 0 && this.jarro4 <= 4;
        }

        public boolean eObjetivo() {
            return this.jarro4 == OBJETIVO;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Estado) {
                Estado estado = (Estado) obj;
                return this.jarro3 == estado.jarro3 && this.jarro4 == estado.jarro4;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(jarro3, jarro4);
        }

        @Override
        public String toString() {
            return "Jarro 3: " + jarro3 + " | Jarro 4: " + jarro4;
        }
    }

    public static Queue<List<Estado>> buscarSolucao() {
        Set<Estado> visitados = new HashSet<>();

        Estado inicial = new Estado(0, 0);
        List<Estado> caminhoInicial = new ArrayList<>();
        caminhoInicial.add(inicial);

        Queue<List<Estado>> resultado = new LinkedList<>();
        Queue<List<Estado>> fronteira = new LinkedList<>();
        fronteira.add(caminhoInicial);

        while (!fronteira.isEmpty()) {
            List<Estado> caminho = fronteira.poll();
            Estado estadoAtual = caminho.get(caminho.size() - 1);
            visitados.add(estadoAtual);

            System.out.println(estadoAtual);

            if (estadoAtual.eObjetivo()) {
                resultado.add(caminho);
                System.out.println("");
                continue;
            }

            List<Estado> proximosEstados = gerarEstadosValidos(estadoAtual, visitados);
            for (Estado proximoEstado : proximosEstados) {
                List<Estado> novoCaminho = new ArrayList<>(caminho);
                novoCaminho.add(proximoEstado);
                fronteira.add(novoCaminho);
            }
        }

        return resultado;
    }

    private static List<Estado> gerarEstadosValidos(Estado estado, Set<Estado> visitados) {
        List<Estado> proximosEstados = new ArrayList<>();

        if (estado.jarro3 < 3)
            proximosEstados.add(new Estado(3, estado.jarro4));
        if (estado.jarro4 < 4)
            proximosEstados.add(new Estado(estado.jarro3, 4));
        if (estado.jarro3 > 0)
            proximosEstados.add(new Estado(0, estado.jarro4));
        if (estado.jarro4 > 0)
            proximosEstados.add(new Estado(estado.jarro3, 0));
        if (estado.jarro3 > 0 && estado.jarro4 < 4)
            proximosEstados.add(new Estado(0, estado.jarro3 + estado.jarro4));
        if (estado.jarro3 > 0 && estado.jarro4 < 4)
            proximosEstados.add(new Estado(estado.jarro3 + estado.jarro4 - 4, 4));
        if (estado.jarro3 < 3 && estado.jarro4 > 0)
            proximosEstados.add(new Estado(estado.jarro3 + estado.jarro4, 0));
        if (estado.jarro3 < 3 && estado.jarro4 > 0)
            proximosEstados.add(new Estado(3, estado.jarro3 + estado.jarro4 - 3));

        proximosEstados.removeIf(visitados::contains);
        proximosEstados.removeIf(estadoAtual -> !estadoAtual.eValido());

        return proximosEstados;
    }

    public static void main(String[] args) {
        Queue<List<Estado>> solucao = buscarSolucao();

        if (solucao.isEmpty()) {
            System.out.println("Não foi encontrada uma solução.");
        } else {
            for (List<Estado> caminho : solucao) {
                System.err.println("\nSolução encontrada:");
                int nrEstado = 1;
                for (Estado estado : caminho) {
                    System.out.println("Estado " + nrEstado + ": " + estado);
                    nrEstado++;
                }
            }
        }
    }
}
