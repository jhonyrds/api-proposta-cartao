package br.com.cartao.proposta.controller

import br.com.cartao.proposta.repository.PropostaRepository
import br.com.cartao.proposta.request.NovaPropostaRequest
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.validation.Valid

@RestController
@RequestMapping("/api/proposta")
class NovaPropostaController(val propostaRepository: PropostaRepository) {

    val logger = LoggerFactory.getLogger(NovaPropostaController::class.java)

    @PostMapping
    fun cadastra(@RequestBody @Valid request: NovaPropostaRequest): ResponseEntity<Any> {

        val proposta = request.toModel()

        propostaRepository.save(proposta)

        val uri = ServletUriComponentsBuilder
            .fromCurrentRequestUri()
            .path("/${proposta.propostaId}")
            .buildAndExpand().toUri()

        logger.info("Gerando nova proposta: $request, propostaId: ${proposta.propostaId}")

        return ResponseEntity.created(uri).build()
    }
}