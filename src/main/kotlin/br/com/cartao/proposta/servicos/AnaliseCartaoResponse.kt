package br.com.cartao.proposta.servicos

import java.math.BigDecimal
import java.time.LocalDateTime

data class AnaliseCartaoResponse(
        val id: String,
        val emitidoEm: LocalDateTime = LocalDateTime.now(),
        val titular: String,
        val limite: BigDecimal,
        val vencimento: Vencimento,
        val idProposta: String)

data class Vencimento(val id: String, val dia: String, val dataDeCriacao: LocalDateTime)