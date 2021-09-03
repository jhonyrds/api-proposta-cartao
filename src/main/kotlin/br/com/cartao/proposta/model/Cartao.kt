package br.com.cartao.proposta.model

import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany
import kotlin.collections.ArrayList

@Entity
class Cartao(
        val numeroCartao: String,
        val emitidoEm: LocalDateTime,
        val titular: String,
        val limite: BigDecimal,
        val vencimento: String,

){
    @field:OneToMany(cascade = arrayOf(CascadeType.MERGE)) val bloqueios: MutableList<BloqueiosCartao> = ArrayList()

    @Id
    val cartaoId: String = UUID.randomUUID().toString()

    fun bloqueios(bloqueio: BloqueiosCartao) {
        bloqueios?.add(bloqueio)
    }
}