package br.com.cartao.proposta.model

import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
class Cartao(
        @Id
        val numeroCartao: String,
        val emitidoEm: LocalDateTime,
        val titular: String,
        val limite: BigDecimal,
        val vencimento: String,

        ) {
    @field:OneToMany(cascade = [CascadeType.MERGE])
    val bloqueios: MutableList<BloqueiosCartao> = ArrayList()

    fun bloqueios(bloqueio: BloqueiosCartao) {
        bloqueios?.add(bloqueio)
    }
}