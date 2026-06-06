import contas.ContaBancaria;
import contas.ContaCorrente;
import contas.ContaPoupanca;
import contas.ContaSalario;
import exceptions.OperacaoInvalidaException;
import exceptions.SaldoInsuficienteException;
import interfaces.Tributavel;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // ── 1. Criando as contas ───────────────────────────────────────────────
        ContaCorrente  cc  = new ContaCorrente ("001-1", "Alice Souza",  2500.00);
        ContaPoupanca  cp  = new ContaPoupanca ("002-2", "Bruno Lima",   5000.00);
        ContaSalario   cs  = new ContaSalario  ("003-3", "Carlos Matos", 1800.00);

        // ── 2. Polimorfismo (R9): lista de ContaBancaria ──────────────────────
        List<ContaBancaria> contas = new ArrayList<>();
        contas.add(cc);
        contas.add(cp);
        contas.add(cs);

        System.out.println("═══════════════════════════════════════════════");
        System.out.println("  SISTEMA DE CONTA BANCÁRIA DIGITAL");
        System.out.println("═══════════════════════════════════════════════");

        System.out.println("\n── Rendimentos (polimorfismo) ──────────────────");
        for (ContaBancaria conta : contas) {
            System.out.printf("%-15s → calcularRendimento() = R$ %.2f%n",
                    conta.getTitular(), conta.calcularRendimento());
        }

        // ── 3. Operações normais ──────────────────────────────────────────────
        System.out.println("\n── Operações normais ───────────────────────────");
        try {
            cc.depositar(500.00);
            cc.sacar(300.00);

            cp.depositar(1000.00);

            cs.depositar(200.00);
            cs.sacar(500.00);

        } catch (OperacaoInvalidaException e) {
            System.out.println("ERRO (Checked): " + e.getMessage());
        }

        // ── 4. Impostos ────────────────────────────────────────────────────────
        System.out.println("\n── Impostos (Tributavel) ───────────────────────");
        for (ContaBancaria conta : contas) {
            if (conta instanceof Tributavel t) {
                System.out.printf("%-15s → calcularImposto()    = R$ %.2f%n",
                        conta.getTitular(), t.calcularImposto());
            }
        }

        // ── 5. Provocando SaldoInsuficienteException (Unchecked) ──────────────
        System.out.println("\n── Provocando SaldoInsuficienteException ────────");
        try {
            cs.sacar(99999.00); // muito além do saldo
        } catch (SaldoInsuficienteException e) {
            System.out.println("ERRO (Unchecked): " + e.getMessage());
        } catch (OperacaoInvalidaException e) {
            System.out.println("ERRO (Checked): " + e.getMessage());
        }

        // ── 6. Provocando OperacaoInvalidaException (Checked) ─────────────────
        System.out.println("\n── Provocando OperacaoInvalidaException ─────────");
        cc.bloquear();
        try {
            cc.depositar(100.00); // conta está bloqueada
        } catch (OperacaoInvalidaException e) {
            System.out.println("ERRO (Checked): " + e.getMessage());
        }
        cc.desbloquear();

        // ── 7. Titular inválido ────────────────────────────────────────────────
        System.out.println("\n── Provocando IllegalArgumentException ──────────");
        try {
            ContaBancaria invalida = new ContaCorrente("999-9", "", 100.00);
        } catch (IllegalArgumentException e) {
            System.out.println("ERRO: " + e.getMessage());
        }

        // ── 8. Estado final ────────────────────────────────────────────────────
        System.out.println("\n── Estado final das contas ─────────────────────");
        contas.forEach(System.out::println);
    }
}
