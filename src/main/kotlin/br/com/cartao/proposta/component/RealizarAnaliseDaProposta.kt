package br.com.cartao.proposta.component

import br.com.cartao.proposta.model.AcaoAposGerarProposta
import br.com.cartao.proposta.model.Proposta
import br.com.cartao.proposta.model.StatusProposta
import br.com.cartao.proposta.model.converte
import br.com.cartao.proposta.request.AnaliseDePropostaRequest
import br.com.cartao.proposta.servicos.AnalisePropostaClient
import feign.FeignException
import org.springframework.stereotype.Component

@Component
class RealizarAnaliseDaProposta(val analiseClient: AnalisePropostaClient) : AcaoAposGerarProposta {
    override fun executar(proposta: Proposta) {
        try {
            val analise = analiseClient.analise(AnaliseDePropostaRequest(proposta.documento, proposta.nome, proposta.propostaId))
            proposta.adicionaStatus(converte(analise.resultadoSolicitacao))
        } catch (feignException: FeignException) {
            proposta.adicionaStatus(StatusProposta.NAO_ELEGIVEL)
        }
    }
}