package com.umy.ucp2.repository

import com.umy.ucp2.data.dao.MahasiswaDao
import com.umy.ucp2.data.entity.Mahasiswa
import kotlinx.coroutines.flow.Flow

class LocalRepositoryMhs (
    private val mahasiswaDao: MahasiswaDao
): RepositoryMhs {
    override suspend fun insertMhs(mahasiswa: Mahasiswa) {
        mahasiswaDao.insertMahasiswa(mahasiswa)
    }

    fun getAllMhs() : Flow<List<Mahasiswa>>
}