package br.com.cartao.proposta.response

import br.com.cartao.proposta.model.Proposta

class DadosDaPropostaResponse(proposta: Proposta) {
    val propostaId = proposta.propostaId
    val documento = proposta.documento
    val email = proposta.email
    val endereco = proposta.endereco
    val salario = proposta.salario
    val status = proposta.statusProposta
}






