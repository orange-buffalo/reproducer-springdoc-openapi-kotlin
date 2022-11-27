package com.example.reproducerspringdocopenapikotlin

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@SpringBootTest
@AutoConfigureWebTestClient
@TestPropertySource(
    properties = [
        "springdoc.api-docs.path=/api-docs",
        "springdoc.default-produces-media-type=application/json"
    ]
)
class SchemaTest(
    @Autowired val client: WebTestClient
) {

    @Test
    fun shouldSupportKotlinCoroutines() {
        client.get()
            .uri("/api-docs.yaml")
            .exchange()
            .expectStatus().isOk
            .expectBody<String>()
            .consumeWith {
                Assertions.assertEquals(
                    it.responseBody, """
                        openapi: 3.0.1
                        info:
                          title: OpenAPI definition
                          version: v0
                        servers:
                        - url: ""
                          description: Generated server url
                        paths:
                          /api/demo:
                            get:
                              tags:
                              - documents-api-controller
                              operationId: getDocuments
                              parameters:
                              - name: "id[in]"
                                in: query
                                required: false
                                schema:
                                  type: array
                                  items:
                                    type: integer
                                    format: int64
                              responses:
                                "200":
                                  description: OK
                                  content:
                                    application/json:
                                      schema:
                                        ${'$'}ref: '#/components/schemas/DemoDto'
                        components:
                          schemas:
                            DemoDto:
                              type: object
                              properties:
                                id:
                                  type: integer
                                  format: int64""".trimIndent()
                )
            }
    }
}
