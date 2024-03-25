package id.web.dedekurniawan.moviexplorer.core.data.di

import org.koin.core.module.Module

interface KoinModuleProvider {
    fun getModule(): Module
}