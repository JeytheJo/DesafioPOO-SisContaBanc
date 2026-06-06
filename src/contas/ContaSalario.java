package contas;

import exceptions.OperacaoInvalidaException;
import exceptions.SaldoInsuficienteException;

import java.time.LocalDate;

/**
 * Conta Salário.
 * - Sem rendimento
 * - Sem imposto (não implementa Tributavel)
 * - Máximo 1 saque por mês
 */
public class ContaSalario extends ContaBancaria {

    private LocalDate ultimoSaque;

    public ContaSalario(String numero, String titular, double saldo) {
        super(numero, titular, saldo);
        this.ultimoSaque = null;
    }

    /**
     * Conta Salário não possui rendimento.
     */
    @Override
    public double calcularRendimento() {
        return 0.0;
    }

    /**
     * Saque com limite de 1 por mês (mesmo mês/ano).
     */
    @Override
    public void sacar(double valor) throws OperacaoInvalidaException {
        verificarContaAtiva();
        if (valor <= 0) {
            throw new IllegalArgumentException("O valor do saque deve ser maior que zero.");
        }
        if (ultimoSaque != null) {
            LocalDate hoje = LocalDate.now();
            if (ultimoSaque.getMonth() == hoje.getMonth()
                    && ultimoSaque.getYear() == hoje.getYear()) {
                throw new OperacaoInvalidaException(numero,
                    "Conta Salário permite apenas 1 saque por mês.");
            }
        }
        if (valor > saldo) {
            throw new SaldoInsuficienteException(saldo, valor);
        }
        this.saldo -= valor;
        this.ultimoSaque = LocalDate.now();
        System.out.printf("[%s] Saque de R$ %.2f realizado. Novo saldo: R$ %.2f%n",
                numero, valor, saldo);
    }
}
