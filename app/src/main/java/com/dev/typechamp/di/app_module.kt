package com.dev.typechamp.di

import android.content.Context
import android.content.SharedPreferences
import com.dev.typechamp.repositories.BaseRepository
import com.dev.typechamp.repositories.BaseRepositoryImpl
import com.dev.typechamp.viewmodels.BaseViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<SharedPreferences> {
        androidApplication().getSharedPreferences("App", Context.MODE_PRIVATE)
    }

    viewModel { BaseViewModel(get()) }

    single<BaseRepository> { BaseRepositoryImpl(get()) }
}