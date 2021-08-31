package br.com.cartao.proposta.controller

import br.com.cartao.proposta.model.Proposta
import br.com.cartao.proposta.repository.PropostaRepository
import br.com.cartao.proposta.request.NovaPropostaRequest
import br.com.cartao.proposta.response.DadosDaPropostaResponse
import org.junit.Assert.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*

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

    @Test
    internal fun `deve buscar informaçoes do status da proposta`() {

        //cenário
        val propostaId = UUID.randomUUID().toString()

        val proposta: Optional<Proposta> = mockRepository.findById(propostaId)

        //ação
        `when`(controller.consulta(propostaId)).thenReturn(createResponseEntityOk())

        `when`(mockRepository.findById(propostaId)).thenReturn(proposta)

        val response = controller.consulta(propostaId)

        //verificação
        assertNotNull(response)
        assertEquals(response.statusCode, HttpStatus.OK)
    }

    private fun createResponseEntitySuccess(): ResponseEntity<Any> {
        return ResponseEntity<Any>("body", HttpStatus.CREATED)
    }

    private fun createResponseEntityOk(): ResponseEntity<DadosDaPropostaResponse> {
        return ResponseEntity<DadosDaPropostaResponse>(HttpStatus.OK)
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
                salario = request.salario,
        )
    }
}