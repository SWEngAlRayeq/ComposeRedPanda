package app.red_panda.data.repo_impl

import app.red_panda.data.remote.RedPandaApi
import app.red_panda.domain.model.RedPanda
import app.red_panda.domain.repo.RedPandaRepository
import javax.inject.Inject

class RedPandaRepositoryImpl @Inject constructor(
    private val api: RedPandaApi
) : RedPandaRepository {
    override suspend fun getRedPandaFact(): Result<RedPanda> {
        return try {
            val dto = api.getRedPandaFact()
            val fact = dto.fact?.takeIf { it.isNotBlank() } ?: "Red pandas are adorable and shy."
            Result.success(RedPanda(fact = fact, imageUrl = dto.image))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}