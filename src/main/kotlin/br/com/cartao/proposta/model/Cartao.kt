package br.com.cartao.proposta.model

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Cartao(
        @field:Id val numeroCartao: String,
        val emitidoEm: LocalDateTime,
        val titular: String,
        val limite: BigDecimal,
        val vencimento: String,
        val propostaId: String
)