package br.com.cartao.proposta.model

enum class StatusProposta {
    EM_ANALISE,
    NAO_ELEGIVEL,
    ELEGIVEL
}

fun converte(resultadoSolicitacao: String) =
        when (resultadoSolicitacao) {
            "SEM_RESTRICAO" -> StatusProposta.ELEGIVEL
            else -> StatusProposta.NAO_ELEGIVEL
        }