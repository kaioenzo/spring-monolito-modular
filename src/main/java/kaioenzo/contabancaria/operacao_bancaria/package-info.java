@ApplicationModule(
        allowedDependencies = {"transacao", "saldo", "conta", "common"}
)
package kaioenzo.contabancaria.operacao_bancaria;

import org.springframework.modulith.ApplicationModule;