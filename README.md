# Trabalho 1 — Programação Orientada a Objetos

**Disciplina:** Programação Orientada a Objetos  
**Professora:** Ma. Heloisa Guimarães  
**Instituição:** Centro Universitário Santo Agostinho — UNIFSA  

---

## Sistema de Conta Bancária Digital

### Estrutura do projeto

```
DesafioPOO-SisContaBanc/
├── src/
│   ├── Main.java                          ← Demonstração do polimorfismo + exceções
│   ├── contas/
│   │   ├── ContaBancaria.java             ← Classe abstrata base (R1, R2, R3, R5)
│   │   ├── ContaCorrente.java             ← Cheque especial + imposto 0,38% (R8)
│   │   ├── ContaPoupanca.java             ← Rendimento 0,5% + imposto 22,5% (R8)
│   │   └── ContaSalario.java              ← Máx. 1 saque/mês, sem rendimento (R8)
│   ├── interfaces/
│   │   ├── Bloqueavel.java                ← bloquear(), desbloquear(), isAtiva() (R5)
│   │   └── Tributavel.java                ← calcularImposto() (R4)
│   └── exceptions/
│       ├── SaldoInsuficienteException.java ← Unchecked (R6)
│       └── OperacaoInvalidaException.java  ← Checked (R7)
└── docs/
    └── respostas_trabalho1_POO.docx       ← Respostas escritas (Parte 1 e Parte 2)
```

### Como compilar e executar

```bash
# A partir da pasta src/
javac -d ../out exceptions/*.java interfaces/*.java contas/*.java Main.java

# Executar
cd ../out
java Main
```

### Conceitos demonstrados

| Requisito | Conceito | Implementado em |
|-----------|----------|-----------------|
| R1 | Classe abstrata | `ContaBancaria` |
| R2 | Método abstrato | `calcularRendimento()` |
| R3 | Herança / polimorfismo | `depositar()`, `sacar()` |
| R4 | Interface | `Tributavel` |
| R5 | Interface | `Bloqueavel` |
| R6 | Exceção unchecked | `SaldoInsuficienteException` |
| R7 | Exceção checked | `OperacaoInvalidaException` |
| R8 | Herança concreta | `ContaCorrente`, `ContaPoupanca`, `ContaSalario` |
| R9 | Polimorfismo com lista | `Main.java` — `List<ContaBancaria>` |
