package br.com.cartao.proposta.component

import br.com.cartao.proposta.model.AcaoAposGerarProposta
import br.com.cartao.proposta.model.Cartao
import br.com.cartao.proposta.model.Proposta
import br.com.cartao.proposta.model.StatusProposta
import br.com.cartao.proposta.repository.CartaoRepository
import br.com.cartao.proposta.request.AnaliseDePropostaRequest
import br.com.cartao.proposta.servicos.AnaliseCartaoClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class RealizarConsultaParaGerarCartao(private val cartaoClient: AnaliseCartaoClient, private val cartaoRepository: CartaoRepository) : AcaoAposGerarProposta {

    private val LOGGER = LoggerFactory.getLogger(RealizarConsultaParaGerarCartao::class.java)

    override fun executar(proposta: Proposta) {
        if (proposta.statusProposta == StatusProposta.ELEGIVEL) {
            try {
                val consulta = cartaoClient.gerar(AnaliseDePropostaRequest(proposta.documento, proposta.nome, proposta.propostaId))
                val cartao = Cartao(consulta.id, consulta.emitidoEm, consulta.titular, consulta.limite, consulta.vencimento.dia)
                proposta.adicionaCartaoId(cartao)
                cartaoRepository.save(cartao)
                LOGGER.info("Adicionando número de cartão a proposta: ${proposta.propostaId}")
            }catch (e: Exception){
                LOGGER.error("$e, serviço indisponível no momento, aguardando a próxima sincronização!")
            }
        }
    }
}