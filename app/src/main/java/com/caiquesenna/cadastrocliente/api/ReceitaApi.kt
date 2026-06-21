package com.caiquesenna.cadastrocliente.api

import com.caiquesenna.cadastrocliente.model.ReceitaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ReceitaApi {
    @GET ("v1/cnpj/{cnpj}")
    suspend fun buscarCnpj(
        @Path("cnpj") cnpj: String
    ) : Response<ReceitaResponse>
}