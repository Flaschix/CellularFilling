package com.example.cellularfilling.domain.entity

data class Cell(
    val state: CellState
)

enum class CellState {
    LIVE,
    ALIVE,
    DEAD
}