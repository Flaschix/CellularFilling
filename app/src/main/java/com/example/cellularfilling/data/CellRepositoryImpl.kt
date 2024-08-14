package com.example.cellularfilling.data

import android.util.Log
import com.example.cellularfilling.domain.entity.Cell
import com.example.cellularfilling.domain.entity.CellState
import com.example.cellularfilling.domain.repository.CellRepository
import com.example.cellularfilling.util.mergeWith
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class CellRepositoryImpl @Inject constructor(

): CellRepository {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val refreshedListFlow = MutableSharedFlow<List<Cell>>()

    private val _cellsList = mutableListOf<Cell>()
    private val cellsList
        get() = _cellsList.toList()

    override fun getCells(): StateFlow<List<Cell>> = flow {
        emit(cellsList)
    }
        .mergeWith(refreshedListFlow)
        .stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )

    override suspend fun addCell(cell: Cell) {
        _cellsList.add(cell)
        refreshedListFlow.emit(cellsList)

        val lastThreeCells = cellsList.takeLast(3)

        if(cellsList.size > 2){
            if (lastThreeCells.all { it.state == CellState.ALIVE }) {
                CellState.LIVE
                _cellsList.add(Cell(CellState.LIVE))
            } else if (lastThreeCells.all { it.state == CellState.DEAD }) {
                val liveIndex = cellsList.indexOfLast { it.state == CellState.LIVE }
                if (liveIndex != -1) {
                    _cellsList[liveIndex] = _cellsList[liveIndex].copy(state = CellState.DEAD)
                }
            }
        }

        refreshedListFlow.emit(cellsList)
    }
}