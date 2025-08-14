package app.red_panda.domain.usecase

import app.red_panda.domain.model.RedPanda
import app.red_panda.domain.repo.RedPandaRepository
import javax.inject.Inject

class GetRedPandaFactUseCase @Inject constructor(
    private val repository: RedPandaRepository
) {
    suspend operator fun invoke(): Result<RedPanda> {
        return repository.getRedPandaFact()
    }
}