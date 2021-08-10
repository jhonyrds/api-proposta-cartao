package br.com.cartao.proposta.controller

import br.com.cartao.proposta.model.StatusProposta
import br.com.cartao.proposta.model.converte
import br.com.cartao.proposta.repository.PropostaRepository
import br.com.cartao.proposta.request.AnaliseDePropostaRequest
import br.com.cartao.proposta.request.NovaPropostaRequest
import br.com.cartao.proposta.servicos.AnalisePropostaClient
import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping("/api/proposta")
class NovaPropostaController(private val propostaRepository: PropostaRepository, private val analiseClient: AnalisePropostaClient) {

    private val logger = LoggerFactory.getLogger(NovaPropostaController::class.java)

    @PostMapping
    @Transactional
    fun cadastra(@RequestBody @Valid request: NovaPropostaRequest): ResponseEntity<Any> {

        val proposta = request.toModel(propostaRepository)
                ?: return ResponseEntity.unprocessableEntity()
                        .body("Proposta para o documento: ${request.documento} já cadastrada!")

        try {
            val analise = analiseClient.analise(AnaliseDePropostaRequest(proposta.documento, proposta.nome, proposta.propostaId))
            proposta.adicionaStatus(converte(analise.resultadoSolicitacao))
        } catch (feignException: FeignException) {
            proposta.adicionaStatus(StatusProposta.NAO_ELEGIVEL)
        }

        propostaRepository.save(proposta)

        val uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/${proposta.propostaId}")
                .buildAndExpand().toUri()

        logger.info("Gerando nova proposta: $request, propostaId: ${proposta.propostaId}")

        return ResponseEntity.created(uri).build()
    }
}