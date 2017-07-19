package com.grudus.snake.game

import com.grudus.snake.Window
import com.grudus.snake.game.board.BoardPanel
import com.grudus.snake.game.board.generator.SnMapGenerator
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.event.ComponentEvent
import java.awt.event.ComponentListener
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.io.File
import javax.swing.JPanel
import kotlin.properties.Delegates

class GamePanel(private val window: Window) : JPanel(), KeyListener, ComponentListener {
    private val backgroundColor = Color.RED
    private var isPaused = false
    private val mapGenerator = SnMapGenerator()

    private var boardPanel by Delegates.notNull<BoardPanel>()

    init {
        val file = File(javaClass.classLoader.getResource("map/simple_map.sn").toURI())
        boardPanel = BoardPanel(this, mapGenerator.generate(file), Dimension(30, 30))
        background = backgroundColor
        addKeyListener(this)
        addComponentListener(this)
        isFocusable = true

        val borderLayout = BorderLayout()
        super.setLayout(borderLayout)
        add(boardPanel, BorderLayout.CENTER)
        boardPanel.size = Dimension(200, 200)
    }

    fun start() {
        requestFocus()
        boardPanel.startSnakeMovement()
    }

    override fun keyPressed(e: KeyEvent?) {
        when(e!!.keyCode) {
            KeyEvent.VK_ESCAPE -> togglePause()
        }
        if (!isPaused) boardPanel.keyPressed(e)
    }

    private fun togglePause() {
        if (isPaused)
            boardPanel.startSnakeMovement()
        else
            boardPanel.stopSnakeMovement()
        isPaused = !isPaused
    }

    override fun keyReleased(e: KeyEvent?) {
        boardPanel.keyReleased(e!!)
    }


    override fun keyTyped(e: KeyEvent?) {}

    override fun componentMoved(e: ComponentEvent?) {}
    override fun componentResized(e: ComponentEvent?) {
        boardPanel.changeSize()
    }
    override fun componentHidden(e: ComponentEvent?) {}
    override fun componentShown(e: ComponentEvent?) {}
}