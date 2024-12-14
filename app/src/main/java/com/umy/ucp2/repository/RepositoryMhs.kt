package com.umy.ucp2.repository

import com.umy.ucp2.data.entity.Mahasiswa

interface RepositoryMhs {
    suspend fun insertMhs(mahasiswa: Mahasiswa)
}