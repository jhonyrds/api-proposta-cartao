package br.com.cartao.proposta.component

import br.com.cartao.proposta.model.AcaoAposGerarProposta
import br.com.cartao.proposta.model.Proposta
import br.com.cartao.proposta.model.StatusProposta
import br.com.cartao.proposta.model.converte
import br.com.cartao.proposta.request.AnaliseDePropostaRequest
import br.com.cartao.proposta.servicos.AnalisePropostaClient
import feign.FeignException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class RealizarAnaliseDaProposta(private val analiseClient: AnalisePropostaClient) : AcaoAposGerarProposta {

    private val LOGGER = LoggerFactory.getLogger(RealizarAnaliseDaProposta::class.java)

    override fun executar(proposta: Proposta) {
        try {
            val analise = analiseClient.analise(AnaliseDePropostaRequest(proposta.documento, proposta.nome, proposta.propostaId))
            proposta.adicionaStatus(converte(analise.resultadoSolicitacao))
            LOGGER.info("Adicionando status da proposta: ${proposta.propostaId}")
        } catch (e: FeignException) {
            LOGGER.error("Proposta ${proposta.propostaId} com restrição: ${e.status()}")
        }catch (exception: Exception){
            LOGGER.error("$exception, serviço indisponível no momento, aguardando a próxima sincronização!")
        }
    }
}