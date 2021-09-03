package br.com.cartao.proposta.model

import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class BloqueiosCartao(
        val clientIp: String,
        val userAgent: String,
) {
    val bloqueioEm: LocalDateTime = LocalDateTime.now()

    @Id
    val bloqueioId: String = UUID.randomUUID().toString()
}