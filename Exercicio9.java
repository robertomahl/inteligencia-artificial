import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Exercicio9 {

    static class Nodo {
        int valor;
        List<Nodo> filhos;

        public Nodo(int valor) {
            this.valor = valor;
            this.filhos = new ArrayList<>();
        }
    }

    private static int max(Nodo nodo, int alpha, int beta, int profundidade, boolean inverter, boolean poda) {
        if (nodo.filhos.isEmpty()) {
            System.out.println("Profundidade: " + profundidade + " Valor: " + nodo.valor);
            return nodo.valor;
        }

        int valor = Integer.MIN_VALUE;
        if (inverter) {
            Collections.reverse(nodo.filhos);
        }
        for (int i = 0; i < nodo.filhos.size(); i++) {
            valor = Math.max(valor, min(nodo.filhos.get(i), alpha, beta, profundidade, inverter, poda));
            alpha = Math.max(alpha, valor);
            // A condição && i < nodo.filhos.size() - 1 é para evitar que contemos a última iteração como uma poda, 
            // já que o laço não seria executado de novo de qualquer jeito
            if (poda) {
                if (alpha >= beta && i < nodo.filhos.size() - 1) {
                    System.out.println("Poda alfa-beta ocorreu no max. Alpha = " + alpha + " Beta = " + beta);
                    break;
                }
            }
        }
        System.out.println("Profundidade: " + profundidade + " Alpha: " + alpha);
        nodo.valor = valor;
        return valor;
    }

    private static int min(Nodo nodo, int alpha, int beta, int profundidade, boolean inverter, boolean poda) {
        if (nodo.filhos.isEmpty()) {
            System.out.println("Profundidade: " + profundidade + " Valor: " + nodo.valor);
            return nodo.valor;
        }

        int valor = Integer.MAX_VALUE;
        if (inverter) {
            Collections.reverse(nodo.filhos);
        }
        for (int i = 0; i < nodo.filhos.size(); i++) {
            valor = Math.min(valor, max(nodo.filhos.get(i), alpha, beta, profundidade, inverter, poda));
            beta = Math.min(beta, valor);
            // A condição && i < nodo.filhos.size() - 1 é para evitar que contemos a última iteração como uma poda, 
            // já que o laço não seria executado de novo de qualquer jeito
            if (poda) {
                if (alpha >= beta && i < nodo.filhos.size() - 1) {
                    System.out.println("Poda alfa-beta ocorreu no min. Alpha = " + alpha + " Beta = " + beta);
                    break;
                }
            }
        }
        System.out.println("Profundidade: " + profundidade + " Beta: " + beta);
        nodo.valor = valor;
        return valor;
    }

    private static Nodo criarArvore() {
        Nodo raiz = new Nodo(0);

        Nodo a = new Nodo(0);
        Nodo b = new Nodo(0);
        
        Nodo c = new Nodo(0);
        Nodo d = new Nodo(0);
        Nodo e = new Nodo(0);
        Nodo f = new Nodo(0);

        Nodo g = new Nodo(0);
        Nodo h = new Nodo(0);
        Nodo i = new Nodo(0);
        Nodo j = new Nodo(0);
        Nodo k = new Nodo(0);
        Nodo l = new Nodo(0);
        Nodo m = new Nodo(0);
        Nodo n = new Nodo(0);

        Nodo ad = new Nodo(20);
        Nodo o = new Nodo(33);
        Nodo p = new Nodo(-45);
        Nodo q = new Nodo(31);
        Nodo r = new Nodo(24);
        Nodo s = new Nodo(25);
        Nodo t = new Nodo(-10);
        Nodo u = new Nodo(20);
        Nodo v = new Nodo(40);
        Nodo x = new Nodo(-25);
        Nodo y = new Nodo(18);
        Nodo z = new Nodo(-42);
        Nodo w = new Nodo(24);
        Nodo aa = new Nodo(-19);
        Nodo ab = new Nodo(36);
        Nodo ac = new Nodo(-41);

        raiz.filhos.add(a);
        raiz.filhos.add(b);

        a.filhos.add(c);
        a.filhos.add(d);
        b.filhos.add(e);
        b.filhos.add(f);
        
        c.filhos.add(g);
        c.filhos.add(h);
        d.filhos.add(i);
        d.filhos.add(j);
        e.filhos.add(k);
        e.filhos.add(l);
        f.filhos.add(m);
        f.filhos.add(n);
        
        g.filhos.add(ad);
        g.filhos.add(o);
        h.filhos.add(p);
        h.filhos.add(q);
        i.filhos.add(r);
        i.filhos.add(s);
        j.filhos.add(t);
        j.filhos.add(u);
        k.filhos.add(v);
        k.filhos.add(x);
        l.filhos.add(y);
        l.filhos.add(z);
        m.filhos.add(w);
        m.filhos.add(aa);
        n.filhos.add(ab);
        n.filhos.add(ac);

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
        Nodo raiz = criarArvore();
        
        System.out.println("Árvore de busca:");
        desenharArvore(raiz, "");

        raiz = criarArvore();

        System.out.println("\nMin-max sem poda:");
        int resultado0 = max(raiz, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, false, false);
        System.out.println("Resultado: " + resultado0);

        raiz = criarArvore();

        System.out.println("\nMin-max com poda alfa-beta da esquerda para direita:");
        int resultado1 = max(raiz, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, false, true);
        System.out.println("Resultado: " + resultado1);

        raiz = criarArvore();

        System.out.println("\nMin-max com poda alfa-beta da direita para esquerda:");
        int resultado2 = max(raiz, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, true, true);
        System.out.println("Resultado: " + resultado2);
    }

}
