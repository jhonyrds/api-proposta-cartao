package br.com.cartao.proposta.component.scheduled

import br.com.cartao.proposta.model.Cartao
import br.com.cartao.proposta.model.Proposta
import br.com.cartao.proposta.model.StatusProposta
import br.com.cartao.proposta.repository.CartaoRepository
import br.com.cartao.proposta.repository.PropostaRepository
import br.com.cartao.proposta.request.AnaliseDePropostaRequest
import br.com.cartao.proposta.servicos.CartaoClient
import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class DisponibilizaCartaoParaPropostaElegivelScheduled(private val propostaRepository: PropostaRepository, private val cartaoRepository: CartaoRepository, private val analiseCartao: CartaoClient) {

    private val LOGGER = LoggerFactory.getLogger(DisponibilizaCartaoParaPropostaElegivelScheduled::class.java)

    @Scheduled(initialDelay = 600000, fixedRate = 600000)
    fun disponibilizaCartao() {

        val propostas: List<Proposta> = propostaRepository.findByStatusPropostaEqualsAndCartaoIdNull(StatusProposta.ELEGIVEL);

        for (proposta in propostas) {
            if (proposta.cartaoId == null) {
                try {
                    val response = analiseCartao.gerar(AnaliseDePropostaRequest(proposta.documento, proposta.nome, proposta.propostaId))
                    val cartao = Cartao(response.id, response.emitidoEm, response.titular, response.limite, response.vencimento.dia)
                    cartaoRepository.save(cartao)
                    proposta.adicionaCartaoId(cartao)
                    propostaRepository.save(proposta)
                    LOGGER.info("Adicionando número de cartão a proposta: ${proposta.propostaId}")
                } catch (e: FeignException) {
                    LOGGER.error("$e, serviço indisponível no momento, aguardando a próxima sincronização!")
                }
            }
        }
    }
}