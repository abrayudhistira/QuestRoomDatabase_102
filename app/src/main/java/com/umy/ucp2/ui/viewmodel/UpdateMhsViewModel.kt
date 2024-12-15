package com.umy.ucp2.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umy.ucp2.repository.RepositoryMhs
import com.umy.ucp2.ui.navigation.DestinasiUpdate
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMhsViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryMhs: RepositoryMhs
): ViewModel() {
    var updateUiState by mutableStateOf(MhsUIState())
        private set
    private val _nim: String = checkNotNull(savedStateHandle[DestinasiUpdate.NIM])

    init {
        viewModelScope.launch {
            updateUiState = repositoryMhs.getMhs(_nim)
                .filterNotNull()
                .first()
                .toUIStateMhs()
        }
    }

    fun updateState (mahasiswaEvent: MahasiswaEvent){
        updateUIState = updateUIState.copy(
            mahasiswaEvent = mahasiswaEvent,
        )
    }

    fun validateFields():Boolean{
        val event = updateUIState.mahasiswaEvent
        val errorState = FormErrorState(
            nim = if(event.nim.isNotEmpty()) null else "NIM tidak boleh kosong",
            nama = if(event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if(event.jeniskelamin.isNotEmpty()) null else "Jenis kelamin tidak boleh kosong",
            alamat = if(event.alamat.isNotEmpty()) null else "Alamat tidak boleh kosong",
            angkatan = if(event.angkatan.isNotEmpty()) null else "Angkatan tidak boleh kosong"
        )

        updateUIState = updateUIState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData(){
        val currentEvent = updateUIState.mahasiswaEvent

        if(validateFields()){
            viewModelScope.launch {
                try {
                    repositoryMhs.updateMhs(currentEvent.toMahasiswaEntity())
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data berhasil di update",
                        mahasiswaEvent = MahasiswaEvent(),
                        isEntryValid = FormErrorState()
                    )
                    println("snackBarMessage diatur: ${updateUIState.snackBarMessage}")
                }
                catch (e: Exception){
                    updateUIState = updateUIState.copy(
                        snackBarMessage = "Data gagal diupdate"
                    )
                }
            }
        }else{
            updateUIState = updateUIState.copy(
                snackBarMessage = "Data gagal diupdate"
            )
        }
    }
}