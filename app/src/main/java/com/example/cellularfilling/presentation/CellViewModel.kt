package com.example.cellularfilling.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cellularfilling.domain.UseCase.AddCellUseCase
import com.example.cellularfilling.domain.UseCase.GetCellsUseCase
import com.example.cellularfilling.domain.entity.Cell
import com.example.cellularfilling.domain.entity.CellState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CellViewModel @Inject constructor(
    private val addCellUseCase: AddCellUseCase,
    private val getCellsUseCase: GetCellsUseCase,
) : ViewModel() {


    private val cells = getCellsUseCase()
    val state = cells
        .filter {
            it.isNotEmpty()
        }
        .map {
            CellScreenState.Success(it) as CellScreenState
        }
        .onStart { emit(CellScreenState.Loading) }

    fun addCell() {
        viewModelScope.launch {
            val newCell = generateCell()
            addCellUseCase(newCell)
        }
    }

    private fun generateCell(): Cell {
        val isAlive = if ((0..1).random() == 1) CellState.ALIVE else CellState.DEAD
        return Cell(isAlive)
    }


}