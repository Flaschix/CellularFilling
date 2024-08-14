package com.example.cellularfilling.domain.UseCase

import com.example.cellularfilling.domain.entity.Cell
import com.example.cellularfilling.domain.repository.CellRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetCellsUseCase @Inject constructor(private val repository: CellRepository) {

    operator fun invoke(): StateFlow<List<Cell>> {
        return repository.getCells()
    }
}