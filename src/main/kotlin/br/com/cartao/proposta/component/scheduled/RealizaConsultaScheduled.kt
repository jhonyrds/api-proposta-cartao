package br.com.cartao.proposta.component.scheduled

import br.com.cartao.proposta.model.Proposta
import br.com.cartao.proposta.model.StatusProposta
import br.com.cartao.proposta.model.converte
import br.com.cartao.proposta.repository.PropostaRepository
import br.com.cartao.proposta.request.AnaliseDePropostaRequest
import br.com.cartao.proposta.servicos.AnalisePropostaClient
import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RealizaConsultaScheduled(private val repository: PropostaRepository, private val analiseClient: AnalisePropostaClient) {

    private val LOGGER = LoggerFactory.getLogger(RealizaConsultaScheduled::class.java)

    @Scheduled(initialDelay = 10000, fixedDelay = 10000)
    fun realizarConsulta() {

        val propostas: List<Proposta> = repository.findAll()

        for (proposta in propostas) {
            if (proposta.statusProposta == StatusProposta.EM_ANALISE) {
                try {
                    val response = analiseClient.analise(AnaliseDePropostaRequest(proposta.documento, proposta.nome, proposta.propostaId))
                    proposta.adicionaStatus(converte(response.resultadoSolicitacao))
                    repository.save(proposta)
                    LOGGER.info("Adicionando o status da proposta: ${proposta.propostaId}")
                } catch (e: FeignException) {
                    LOGGER.error("$e, serviço indisponível no momento, aguardando a próxima sincronização!")
                }
            }
        }
    }
}