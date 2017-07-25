package com.grudus.snake.game

data class Index(val row: Int, val col: Int) {
    init {
        require(row >= 0 && col >= 0) {"Index cannot have negative values"}
    }
    constructor(position: Index) : this(position.row, position.col)

    operator fun plus(index: Index) = Index(row + index.row, col + index.col)
    operator fun minus(index: Index) = Index(row - index.row, col - index.col)

}