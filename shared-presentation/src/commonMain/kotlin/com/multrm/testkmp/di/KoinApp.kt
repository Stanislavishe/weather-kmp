package com.multrm.testkmp.di

import org.koin.core.Koin
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

expect fun initKoin(config: KoinAppDeclaration? = null)