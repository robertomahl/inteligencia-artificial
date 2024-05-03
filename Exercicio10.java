import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Exercicio10 {

    public static final int QUANT_PALITOS = 5;
    public static final boolean DIREITA_PARA_ESQUERDA = false;

    static class Nodo {
        int valor;
        List<Nodo> filhos;

        public Nodo(int valor) {
            this.valor = valor;
            this.filhos = new ArrayList<>();
        }
    }

    private static int max(Nodo nodo, int alpha, int beta, int profundidade) {
        if (nodo.filhos.isEmpty()) {
            return nodo.valor;
        }
        int valor = Integer.MIN_VALUE;
        if (DIREITA_PARA_ESQUERDA) {
            Collections.reverse(nodo.filhos);
        }
        for (Nodo filho : nodo.filhos) {
            valor = Math.max(valor, min(filho, alpha, beta, profundidade + 1));
            alpha = Math.max(alpha, valor);
            if (alpha >= beta) {
                System.out.println("Poda alfa-beta ocorreu no max. Alpha = " + alpha + " Beta = " + beta + " Profundidade = " + profundidade);
                break;
            }
        }
        nodo.valor = valor;
        return valor;
    }

    private static int min(Nodo nodo, int alpha, int beta, int profundidade) {
        if (nodo.filhos.isEmpty()) {
            return nodo.valor;
        }
        int valor = Integer.MAX_VALUE;
        if (DIREITA_PARA_ESQUERDA) {
            Collections.reverse(nodo.filhos);
        }
        for (Nodo filho : nodo.filhos) {
            valor = Math.min(valor, max(filho, alpha, beta, profundidade + 1));
            beta = Math.min(beta, valor);
            if (alpha >= beta) {
                System.out.println("Poda alfa-beta ocorreu no min. Alpha = " + alpha + " Beta = " + beta + " Profundidade = " + profundidade);
                break;
            }
        }
        nodo.valor = valor;
        return valor;
    }

    private static Nodo criarArvore(int palitos, boolean max) {
        int valor = 0;
        if (palitos == 0) {
            valor = max ? 1 : -1;
        }
        Nodo raiz = new Nodo(valor);
        for (int i = 1; i <= 3; i++) {
            if (palitos - i >= 0) {
                raiz.filhos.add(criarArvore(palitos - i, !max));
            }
        }
        return raiz;
    }

    private static void desenharArvore(Nodo raiz, String prefix) {
        if (raiz == null) return;

        System.out.println(prefix + raiz.valor);
        for (Nodo filho : raiz.filhos) {
            desenharArvore(filho, prefix + "\t");
        }
    }

    public static void main(String[] args) {
        Nodo raiz = criarArvore(QUANT_PALITOS, true);
        
        System.out.println("Árvore de busca:");
        desenharArvore(raiz, "");

        int resultado = max(raiz, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        System.out.println("Resultado: " + resultado);
        desenharArvore(raiz, "");
    }
}
