package com.example.cellularfilling.domain.UseCase

import com.example.cellularfilling.domain.entity.Cell
import com.example.cellularfilling.domain.repository.CellRepository
import javax.inject.Inject

class AddCellUseCase @Inject constructor(private val repository: CellRepository) {

    suspend operator fun invoke(cell: Cell){
        return repository.addCell(cell)
    }
}