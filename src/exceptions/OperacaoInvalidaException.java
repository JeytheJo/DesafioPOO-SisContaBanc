package exceptions;

/**
 * Exceção lançada ao tentar realizar qualquer operação em uma conta bloqueada.
 *
 * CHECKED (extends Exception) — Justificativa:
 * Operar sobre uma conta bloqueada é uma situação RECUPERÁVEL e previsível:
 * o sistema pode (e deve) tratar isso apresentando uma mensagem ao usuário,
 * tentando desbloquear, ou redirecionando o fluxo. Por isso, o compilador
 * força o chamador a declarar "throws" ou capturar o erro — garantindo que
 * nenhum ponto do sistema ignore silenciosamente essa condição.
 */
public class OperacaoInvalidaException extends Exception {

    private final String numeroConta;

    public OperacaoInvalidaException(String numeroConta) {
        super(String.format(
            "Operação inválida: a conta '%s' está bloqueada.", numeroConta
        ));
        this.numeroConta = numeroConta;
    }

    public OperacaoInvalidaException(String numeroConta, String detalhes) {
        super(String.format(
            "Operação inválida na conta '%s': %s", numeroConta, detalhes
        ));
        this.numeroConta = numeroConta;
    }

    public String getNumeroConta() {
        return numeroConta;
    }
}
