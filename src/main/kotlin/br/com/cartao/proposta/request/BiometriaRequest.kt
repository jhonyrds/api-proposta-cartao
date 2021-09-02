package br.com.cartao.proposta.request

import br.com.cartao.proposta.model.Biometria
import br.com.cartao.proposta.repository.CartaoRepository
import java.time.LocalDateTime

data class BiometriaRequest(val digital: String) {

    fun toModel(cartaoId: String, cartaoRepository: CartaoRepository): Biometria {
        val cartao = cartaoRepository.findById(cartaoId)
        if (cartao.isPresent) {
            return Biometria(digital, cartao.get(), LocalDateTime.now())
        } else {
            throw Exception("Problema ao cadastrar biometria - $digital")
        }
    }
}