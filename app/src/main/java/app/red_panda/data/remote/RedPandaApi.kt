package app.red_panda.data.remote

import app.red_panda.data.model.RedPandaDto
import retrofit2.http.GET

interface RedPandaApi {

    @GET("animal/red_panda")
    suspend fun getRedPandaFact(): RedPandaDto

}