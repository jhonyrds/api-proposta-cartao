package br.com.cartao.proposta.controller

import br.com.cartao.proposta.model.Proposta
import br.com.cartao.proposta.repository.PropostaRepository
import br.com.cartao.proposta.request.NovaPropostaRequest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
internal class NovaPropostaControllerTest {

    @field:MockBean
    private lateinit var controller: NovaPropostaController

    private lateinit var mockMvc: MockMvc

    @field:MockBean
    private lateinit var mockRepository: PropostaRepository

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(controller)
            .build()
    }

    @Test
    internal fun `deve cadastrar uma nova proposta`() {

        //cenário
        val request = gerarRequest()

        val proposta = gerarProposta()

        //ação
        `when`(controller.cadastra(request)).thenReturn(createResponseEntitySuccess())

        `when`(mockRepository.save(proposta)).thenReturn(proposta)

        val response = controller.cadastra(request)

        //verificação
        assertNotNull(response)
        assertEquals(HttpStatus.CREATED, response.statusCode)
    }

    @Test
    internal fun `deve gerar status code unprocessable entity quando tentar cadastrar mais de uma proposta para o mesmo cpf`() {

        //cenário
        val request = gerarRequest()

        val proposta = gerarProposta()

        //ação
        `when`(controller.cadastra(request)).thenReturn(statusUnprocessableEntity())

        `when`(mockRepository.save(proposta)).thenReturn(proposta)

        val response = controller.cadastra(request)

        //verificação
        assertNotNull(response)
        assertEquals(response.body, "body")
        assertEquals(response.statusCode, HttpStatus.UNPROCESSABLE_ENTITY)
    }

    private fun createResponseEntitySuccess(): ResponseEntity<Any> {
        return ResponseEntity<Any>("body", HttpStatus.CREATED)
    }

    private fun statusUnprocessableEntity(): ResponseEntity<Any> {
        return ResponseEntity<Any>("body", HttpStatus.UNPROCESSABLE_ENTITY)
    }

    private fun gerarRequest(): NovaPropostaRequest {
        return NovaPropostaRequest(
            "12345678910",
            "teste@teste.com",
            "Jhony Rodrigues",
            "Rua 123",
            "1000.00".toBigDecimal()
        )
    }

    private fun gerarProposta(): Proposta {
        val request = gerarRequest()
        return Proposta(
            documento = request.documento,
            email = request.email,
            nome = request.nome,
            endereco = request.endereco,
            salario = request.salario
        )
    }
}