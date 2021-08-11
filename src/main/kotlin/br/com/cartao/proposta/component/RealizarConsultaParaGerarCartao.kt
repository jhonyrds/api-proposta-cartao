package br.com.cartao.proposta.component

import br.com.cartao.proposta.model.AcaoAposGerarProposta
import br.com.cartao.proposta.model.Cartao
import br.com.cartao.proposta.model.Proposta
import br.com.cartao.proposta.model.StatusProposta
import br.com.cartao.proposta.repository.CartaoRepository
import br.com.cartao.proposta.request.AnaliseDePropostaRequest
import br.com.cartao.proposta.servicos.AnaliseCartaoClient
import org.springframework.stereotype.Component

@Component
class RealizarConsultaParaGerarCartao(private val cartaoClient: AnaliseCartaoClient, private val cartaoRepository: CartaoRepository) : AcaoAposGerarProposta {
    override fun executar(proposta: Proposta) {
        if (proposta.statusProposta == StatusProposta.ELEGIVEL) {
            val consulta = cartaoClient.gerar(AnaliseDePropostaRequest(proposta.documento, proposta.nome, proposta.propostaId))
            val cartao = Cartao(consulta.id, consulta.emitidoEm, consulta.titular, consulta.limite, consulta.vencimento.dia, consulta.idProposta)
            cartaoRepository.save(cartao)
        }
    }
}