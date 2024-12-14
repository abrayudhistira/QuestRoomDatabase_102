package com.umy.ucp2.dependenciesinjection

import android.content.Context
import com.umy.ucp2.data.database.KrsDatabase
import com.umy.ucp2.repository.LocalRepositoryMhs
import com.umy.ucp2.repository.RepositoryMhs

interface InterfaceContainerApp {
    val repositoryMhs: RepositoryMhs
}

class ContainerApp(private val context: Context) : InterfaceContainerApp {
    override val repositoryMhs: RepositoryMhs by lazy {
        LocalRepositoryMhs(KrsDatabase.getDatabase(context).mahasiswaDao())
    }
}