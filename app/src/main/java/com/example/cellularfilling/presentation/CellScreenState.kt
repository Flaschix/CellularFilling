package com.example.cellularfilling.presentation

import com.example.cellularfilling.domain.entity.Cell

sealed class CellScreenState{

    object Initial: CellScreenState()

    object Loading: CellScreenState()

    data class Success(
        val data: List<Cell> = emptyList(),
    ): CellScreenState()

}