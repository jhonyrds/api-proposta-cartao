package br.com.cartao.proposta.shared

import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component
open class ExecutorDeAcoes {
    @Transactional
    open fun executa(funcao: () -> Unit) {
        funcao()
    }
}