package com.example.transformapp.di

import com.example.transformapp.repository.MemberRepositoryImpl
import com.example.transformapp.repository.TrainingRepositoryImpl
import com.example.transformapp.repository.UserRepositoryImpl
import com.example.transformapp.viewModel.TransformViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single {
        MemberRepositoryImpl()

    }
    single {
        TrainingRepositoryImpl()
    }
    single {
        UserRepositoryImpl()
    }

}
val viewModelModule = module {
    viewModel{
        TransformViewModel(get(), get(), get())
    }
}