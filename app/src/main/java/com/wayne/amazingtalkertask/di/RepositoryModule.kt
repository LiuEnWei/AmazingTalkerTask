package com.wayne.amazingtalkertask.di

import com.wayne.amazingtalkertask.repository.AmazingTalkerRepository
import com.wayne.amazingtalkertask.repository.AmazingTalkerRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<AmazingTalkerRepository> {
        AmazingTalkerRepositoryImpl()
    }
}
