package com.mega.games.mygame.scenes.scene3.gameObjects

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Align
import com.mega.games.engine.dependency.DI
import com.mega.games.engine.gameObject.GameObject
import com.mega.games.engine.values.Values
import com.mega.games.engine.widgets.TextLabel
import com.mega.games.mygame.asset.FontSize
import com.mega.games.mygame.utils.assets
import kotlin.math.min

class GameBoard(private val di: DI): GameObject() {
    private var rowSize = 0
    private var numMines = 0

    private var cells = ArrayList<ArrayList<Cell>>()

    private val assets = di.assets
    var nearbyMinesLabel: TextLabel = di.assets.createLabel("", FontSize.F12, Color.BLACK)

    var isGameOver = false
    var isGameCompleted = false

    private var numberOfReveledCells = 0

    fun setRowSize(sz: Int) {
        rowSize = sz
    }

    fun setNumMines(num: Int) {
        numMines = num
    }

    fun generate() {
        val cellSize = width / rowSize

        for(i in 0 until rowSize){
            cells.add(ArrayList())

            for(j in 0 until rowSize){
                val c = Cell(di, i, j)
                c.setSize(cellSize, cellSize)
                c.setPosition(i * cellSize, j*cellSize)
                addActor(c)

                cells[i].add(c)
            }
        }

        addMines()
        addNumbers()
    }

    private fun addMines() {
        var minesLeft = numMines
        while (minesLeft > 0){
            val r = MathUtils.random(rowSize-1)
            val c = MathUtils.random(rowSize-1)

            if(!cells[r][c].hasMine) {
                cells[r][c].addMine()
                minesLeft -= 1
            }
        }
    }

    private fun addNumbers() {
        for(i in 0 until rowSize){
            for(j in 0 until rowSize){
                if(!cells[i][j].hasMine){
                    var mineDetected = 0;
                    if(i-1 >=0 && j-1 >= 0){
                        if(cells[i-1][j-1].hasMine){
                            mineDetected++
                        }
//                        if (cells[i-1][j].hasMine){
//                            mineDetected++
//                        }
                    }
                    if (i-1 >=0 && j+1 <rowSize ){
                        if(cells[i-1][j+1].hasMine){
                            mineDetected++
                        }
//                        if (cells[i][j+1].hasMine){
//                            mineDetected++
//                        }
                    }
                    if (i+1 <rowSize && j+1 < rowSize){
                        if(cells[i+1][j+1].hasMine){
                            mineDetected++
                        }
//                        if (cells[i+1][j].hasMine){
//                            mineDetected++
//                        }
                    }
                    if (i+1 <rowSize && j-1 >= 0){
                        if(cells[i+1][j-1].hasMine){
                            mineDetected++
                        }
//                        if (cells[i][j-1].hasMine){
//                            mineDetected++
//                        }
                    }

                    if(i-1 >=0){
                        if (cells[i-1][j].hasMine){
                            mineDetected++
                        }
                    }
                    if(j+1 < rowSize){
                        if (cells[i][j+1].hasMine){
                            mineDetected++
                        }
                    }
                    if(i+1 < rowSize){
                        if (cells[i+1][j].hasMine){
                            mineDetected++
                        }
                    }
                    if(j-1 >= 0){
                        if (cells[i][j-1].hasMine){
                            mineDetected++
                        }
                    }

                    if(mineDetected > 0){
                        cells[i][j].addNumber(mineDetected)
                    }
                    //cells[i][j].addNumber(1)
                }
            }
        }
    }

    fun cellClicked(i:Int, j:Int){
        cells[i][j].reveal()
        //numberOfReveledCells++
        if(cells[i][j].isEmpty /*&& cells[i][j].isFlaged*/){
            revealEmptyCell(i,j)
        }
        if(cells[i][j].hasMine){
            //GameLose()
            isGameOver = true
        }
        else{
            numberOfReveledCells++
        }
    }

    fun cellFlaged(i: Int,j: Int){
        cells[i][j].redFlag()
    }


    fun revealEmptyCell(i: Int,j: Int){
        if(i-1 >=0 && j-1 >= 0){
            if(!cells[i-1][j-1].hasMine && !cells[i-1][j-1].isRevealed){
                //mineDetected++
                cells[i-1][j-1].reveal()
                numberOfReveledCells++
                if (cells[i-1][j-1].isEmpty){
                    revealEmptyCell(i-1, j-1)
                }
            }
            if (!cells[i-1][j].hasMine && !cells[i-1][j].isRevealed){
                //mineDetected++
                cells[i-1][j].reveal()
                numberOfReveledCells++
                if (cells[i-1][j].isEmpty){
                    revealEmptyCell(i-1, j)
                }
            }
        }
        if (i-1 >=0 && j+1 <rowSize ){
            if(!cells[i-1][j+1].hasMine && !cells[i-1][j+1].isRevealed){
                //mineDetected++
                cells[i-1][j+1].reveal()
                numberOfReveledCells++
                if (cells[i-1][j+1].isEmpty){
                    revealEmptyCell(i-1, j+1)
                }
            }
            if (!cells[i][j+1].hasMine && !cells[i][j+1].isRevealed){
                //mineDetected++
                cells[i][j+1].reveal()
                numberOfReveledCells++
                if (cells[i][j+1].isEmpty){
                    revealEmptyCell(i, j+1)
                }
            }
        }
        if (i+1 <rowSize && j+1 < rowSize){
            if(!cells[i+1][j+1].hasMine && !cells[i+1][j+1].isRevealed){
                //mineDetected++
                cells[i+1][j+1].reveal()
                numberOfReveledCells++
                if (cells[i+1][j+1].isEmpty){
                    revealEmptyCell(i+1, j+1)
                }
            }
            if (!cells[i+1][j].hasMine && !cells[i+1][j].isRevealed){
                //mineDetected++
                cells[i+1][j].reveal()
                numberOfReveledCells++
                if (cells[i+1][j].isEmpty){
                    revealEmptyCell(i+1, j)
                }
            }
        }
        if (i+1 <rowSize && j-1 >= 0){
            if(!cells[i+1][j-1].hasMine && !cells[i+1][j-1].isRevealed){
                //mineDetected++
                cells[i+1][j-1].reveal()
                numberOfReveledCells++
                if (cells[i+1][j-1].isEmpty){
                    revealEmptyCell(i+1, j-1)
                }
            }
            if (!cells[i][j-1].hasMine && !cells[i][j-1].isRevealed){
                //mineDetected++
                cells[i][j-1].reveal()
                numberOfReveledCells++
                if (cells[i][j-1].isEmpty){
                    revealEmptyCell(i, j-1)
                }
            }
        }

    }

    fun gameCompleted(){
        isGameCompleted = true;
        revealAll()
    }

    fun checkGameStats(){
        if(((rowSize * rowSize) - numberOfReveledCells).equals(numMines)){
            gameCompleted()
        }
    }

    fun revealAll(){
        for(i in 0 until rowSize) {
            for (j in 0 until rowSize) {
                cells[i][j].reveal()
            }
        }
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.color = Color(0.9f, 0.9f, 0.9f, 1f)
        batch.draw(di.assets.rect, x, y, width, height)
        batch.color = Color.WHITE

        super.draw(batch, parentAlpha)
    }
}