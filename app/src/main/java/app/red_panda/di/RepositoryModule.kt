package app.red_panda.di

import app.red_panda.data.repo_impl.RedPandaRepositoryImpl
import app.red_panda.domain.repo.RedPandaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRedPandaRepo(impl: RedPandaRepositoryImpl): RedPandaRepository
}
