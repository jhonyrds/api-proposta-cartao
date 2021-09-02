package br.com.cartao.proposta.controller

import br.com.cartao.proposta.repository.BiometriaRepository
import br.com.cartao.proposta.repository.CartaoRepository
import br.com.cartao.proposta.request.BiometriaRequest
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/biometria")
class CadastraBiometriaController(private val biometriaRepository: BiometriaRepository, val cartaoRepository: CartaoRepository) {

    private val LOGGER = LoggerFactory.getLogger(CadastraBiometriaController::class.java)

    @PostMapping("{cartaoId}")
    fun cadastra(@PathVariable cartaoId: String, biometriaRequest: BiometriaRequest): ResponseEntity<Any> {

        val biometria = biometriaRequest.toModel(cartaoId, cartaoRepository)

        biometriaRepository.save(biometria)

        LOGGER.info("Cadastrando biometria para o cartãoId: $cartaoId")

        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}