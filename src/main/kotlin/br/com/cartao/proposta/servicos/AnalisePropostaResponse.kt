package br.com.cartao.proposta.servicos

data class AnalisePropostaResponse(
        val documento: String,
        val nome: String,
        val resultadoSolicitacao: String,
        val idProposta: String
)
