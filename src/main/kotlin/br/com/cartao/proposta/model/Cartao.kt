package br.com.cartao.proposta.model

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Cartao(
        val numeroCartao: String,
        val emitidoEm: LocalDateTime,
        val titular: String,
        val limite: BigDecimal,
        val vencimento: String,
){
    @Id
    val cartaoId: String = UUID.randomUUID().toString()
}