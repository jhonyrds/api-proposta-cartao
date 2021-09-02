package br.com.cartao.proposta.model

import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.validation.constraints.NotBlank

@Entity
class Biometria(
        @field:NotBlank val digital: String,
        @field:ManyToOne val cartaoId: Cartao,
        val cadastradaEm: LocalDateTime
) {
    @Id
    val biometriaId: String = UUID.randomUUID().toString()
}
