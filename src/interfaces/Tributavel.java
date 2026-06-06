package interfaces;

/**
 * Interface que representa contas sujeitas à cobrança de imposto.
 * Implementada apenas por ContaCorrente e ContaPoupanca.
 */
public interface Tributavel {

    /**
     * Calcula o imposto devido pela conta.
     * Cada tipo de conta define sua própria alíquota e base de cálculo.
     *
     * @return valor do imposto em reais
     */
    double calcularImposto();
}
