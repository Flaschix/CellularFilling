package com.example.cellularfilling.domain.repository

import com.example.cellularfilling.domain.entity.Cell
import kotlinx.coroutines.flow.StateFlow

interface CellRepository {

    fun getCells(): StateFlow<List<Cell>>
    suspend fun addCell(cell: Cell)
}