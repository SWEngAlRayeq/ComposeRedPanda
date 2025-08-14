package app.red_panda.domain.repo

import app.red_panda.domain.model.RedPanda

interface RedPandaRepository {
    suspend fun getRedPandaFact(): Result<RedPanda>
}