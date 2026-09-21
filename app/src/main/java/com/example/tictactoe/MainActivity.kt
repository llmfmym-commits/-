package com.example.tictactoe

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var cells: List<Button>
    private lateinit var statusText: TextView
    private var board = Array(9) { "" }
    private var currentPlayer = "X"
    private var gameOver = false

    private val winPatterns = listOf(
        intArrayOf(0, 1, 2), intArrayOf(3, 4, 5), intArrayOf(6, 7, 8),
        intArrayOf(0, 3, 6), intArrayOf(1, 4, 7), intArrayOf(2, 5, 8),
        intArrayOf(0, 4, 8), intArrayOf(2, 4, 6)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.statusText)
        cells = listOf(
            findViewById(R.id.cell_0), findViewById(R.id.cell_1), findViewById(R.id.cell_2),
            findViewById(R.id.cell_3), findViewById(R.id.cell_4), findViewById(R.id.cell_5),
            findViewById(R.id.cell_6), findViewById(R.id.cell_7), findViewById(R.id.cell_8)
        )

        cells.forEachIndexed { index, button ->
            button.setOnClickListener { onCellClicked(index) }
        }

        findViewById<Button>(R.id.resetButton).setOnClickListener { resetGame() }
    }

    private fun onCellClicked(index: Int) {
        if (gameOver || board[index].isNotEmpty()) return

        board[index] = currentPlayer
        cells[index].text = currentPlayer

        val winner = checkWinner()
        if (winner != null) {
            statusText.text = "برنده: $winner"
            gameOver = true
            return
        }

        if (board.none { it.isEmpty() }) {
            statusText.text = "مساوی شد!"
            gameOver = true
            return
        }

        currentPlayer = if (currentPlayer == "X") "O" else "X"
        statusText.text = "نوبت $currentPlayer"
    }

    private fun checkWinner(): String? {
        for (pattern in winPatterns) {
            val (a, b, c) = Triple(pattern[0], pattern[1], pattern[2])
            if (board[a].isNotEmpty() && board[a] == board[b] && board[b] == board[c]) {
                return board[a]
            }
        }
        return null
    }

    private fun resetGame() {
        board = Array(9) { "" }
        cells.forEach { it.text = "" }
        currentPlayer = "X"
        gameOver = false
        statusText.text = "نوبت X"
    }
}
