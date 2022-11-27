package com.example.reproducerspringdocopenapikotlin

import io.swagger.v3.oas.annotations.Parameter
import org.springdoc.core.annotations.ParameterObject
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/demo")
class DocumentsApiController {

    @GetMapping
    suspend fun getDocuments(
        @ParameterObject request: DemoRequest
    ): DemoDto = DemoDto(42)
}

data class DemoDto(
    var id: Long,
)

class DemoRequest {
    @field:Parameter(name = "id[in]")
    var idIn: List<Long>? = null
}
