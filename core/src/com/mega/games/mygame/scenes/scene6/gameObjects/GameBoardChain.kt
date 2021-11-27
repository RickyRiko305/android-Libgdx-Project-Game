package com.mega.games.mygame.scenes.scene6.gameObjects

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.mega.games.engine.dependency.DI
import com.mega.games.engine.gameObject.GameObject
import com.mega.games.engine.gameObject.addInputListener
import com.mega.games.mygame.scenes.scene6.Scene6
import com.mega.games.mygame.utils.assets
import com.mega.games.mygame.utils.scene
import java.util.*
import kotlin.collections.ArrayList

class GameBoardChain(private val di: DI): GameObject() {

    class pair{
        var first:Int = 0
        var second:Int = 0

        fun pair(first: Int, second: Int){
            this.first = first
            this.second = second
        }

    }

    private var rowSize = 0
    private var columnSize = 0

    private var blocks = ArrayList<ArrayList<AtomCell>>()

    private var gameStart = false

    var isGameOver = false

    private var elapsedTime = 0f

    var currentPlayer = 1

    private var backGroundColor = Color.RED

    private var dRow = intArrayOf(-1, 0, 1, 0)//{-1, 0, 1, 0}

    private var dCol = intArrayOf(0, 1, 0, -1)//{0, 1, 0, -1}

    private var flag = false

    private var rowx = 0
    private var coly = 0

    private var currentP = 0

    private var lastPlayer = 0
    private var countMove = 0


    init {
        this.addInputListener()
    }

    fun setRowSize(s: Int) {
        rowSize = s
    }

    fun setColumnSize(s: Int) {
        columnSize = s
    }

    private fun setBackGroundColor(num: Int) {
        //numberAllocated = num

        when(num) {
            0 -> backGroundColor = Color(0.95f, 0.95f, 0.95f, 1f)
            1 -> backGroundColor = Color.RED
            2 -> backGroundColor = Color.GREEN
            3 -> backGroundColor = Color.BLUE
            4 -> backGroundColor = Color.ORANGE
            5 -> backGroundColor = Color.OLIVE
            6 -> backGroundColor = Color.LIME
            7 -> backGroundColor = Color.GOLDENROD
            8 -> backGroundColor = Color.LIGHT_GRAY
            else -> {
                backGroundColor = Color(0.95f, 0.95f, 0.95f, 1f)//Color.BLUE
            }

        }
    }

    fun generate() {
        val blockSize = width / rowSize

        for(i in 0 until rowSize){
            blocks.add(ArrayList())

            for(j in 0 until columnSize){
                val b = AtomCell(di, i, j)
                b.setSize(blockSize, blockSize)
                b.setPosition(i * blockSize, j * blockSize)

                addActor(b)


                blocks[i].add(b)
            }
        }
        //
    }

    fun cellClicked(i: Int, j: Int){

        if(((backGroundColor == blocks[i][j].blockColor) || (blocks[i][j].number == 0)) && !isGameOver){
            if (blocks[i][j].number == 3){
                blocks[i][j].addNumber(1)
                blocks[i][j].setColor(0)
                bfs(i, j)
//                rowx = i
//                coly = j
//                currentP = currentPlayer
//                flag = true
//                blocks[i][j].addNumber(1)
//                blocks[i][j].setColor(0)
            }
            else if (((i == 0 && j == 0) || (i == 0 && j == (rowSize-1)) || (i == (rowSize-1) && j == 0) || (i == (rowSize-1) && j == (rowSize-1))) && (blocks[i][j].number == 1)){
                blocks[i][j].addNumber(3)
                blocks[i][j].setColor(0)
                bfs(i, j)
            }
            else if ((i == 0 || i == (rowSize-1) || j == 0 || j == (rowSize-1)) && (blocks[i][j].number == 2)){
                blocks[i][j].addNumber(2)
                blocks[i][j].setColor(0)
                bfs(i, j)
            }
            else{
                blocks[i][j].addNumber(1)
                blocks[i][j].setColor(currentPlayer)
            }


            playerTurn()
        }
//        else if (blocks[i][j].blockColor == Color(0.95f, 0.95f, 0.95f, 1f)){
//
//        }

    }

    fun isValid(vis: Array<BooleanArray>, row: Int, col: Int):Boolean {
        if (row < 0 || col < 0 || row>=rowSize || col >=columnSize){
            return false
        }
//        if (vis[row][col]){
//            return false
//        }

//        if (blocks[row][col].number <3){
//            return false
//        }

        return true
    }

    fun bfs(row: Int, col: Int){
        //var vis = ArrayList<ArrayList<Boolean>>()
        val vis = Array(rowSize) { BooleanArray(columnSize) }

        var q: Queue<String> = LinkedList<String>()

        vis[row][col] = true
        //q.add(new pair(row, col))
        //q.add(row + "," + col)
        q.add(row.toString() + "," + col.toString())

        blocks[row][col].debug = true
        //System.out.print()
        println(blocks[row][col].number)
        //println(nextPos)

        while (!q.isEmpty()){
            //var cell:Pair<Int,Int> = q.peek()
            var cell: String? = q.remove()//q.peek()
//            var x = cell.first
//            var y = cell.second
            var x = Integer.parseInt(cell?.split(",")?.get(0))//cell.first
            var y = Integer.parseInt(cell?.split(",")?.get(1))//cell.second
            //blocks[x][y].number = 0
            //System.out.print(blocks[x][y] + " ")

            //q.remove()

            for (i in 0 until 4){
                var adjx = x + dRow[i]
                var adjy = y + dCol[i]

                if(isValid(vis, adjx, adjy)){
                    //q.add(Pair(adjx,adjy)
                    if (blocks[adjx][adjy].number == 3){
                        q.add(adjx.toString() + "," + adjy.toString())
                        blocks[adjx][adjy].addNumber(1)
                        blocks[adjx][adjy].setColor(0)
//                        Handler().postDelayed({
//                            //doSomethingHere()
//                        }, 1000)
                        //Timer().schedule(10000){
                            println("yes")
                        //}
                        //delay(2000f)
                        try {
                            Thread.sleep(2000)
                        } catch (e: InterruptedException) {
                            // this part is executed when an exception (in this example InterruptedException) occurs
                        }
                        println("No")
                    }
                    else if (((adjx == 0 && adjy == 0) || (adjx == 0 && adjy == (rowSize-1)) || (adjx == (rowSize-1) && adjy == 0) || (adjx == (rowSize-1) && adjy == (rowSize-1))) && (blocks[adjx][adjy].number == 1)){
                        if (blocks[adjx][adjy].number == 1){
                            q.add(adjx.toString() + "," + adjy.toString())
                            blocks[adjx][adjy].addNumber(3)
                            blocks[adjx][adjy].setColor(0)
//                        Handler().postDelayed({
//                            //doSomethingHere()
//                        }, 1000)
                            //Timer().schedule(10000){
                            println("yes")
                            //}
                            //delay(2000f)
                            try {
                                Thread.sleep(2000)
                            } catch (e: InterruptedException) {
                                // this part is executed when an exception (in this example InterruptedException) occurs
                            }
                            println("No")
                        }
                    }
                    else if ((adjx == 0 || adjx == (rowSize-1) || adjy == 0 || adjy == (rowSize-1)) && (blocks[adjx][adjy].number == 2)){
                        if (blocks[adjx][adjy].number == 2){
                            q.add(adjx.toString() + "," + adjy.toString())
                            blocks[adjx][adjy].addNumber(2)
                            blocks[adjx][adjy].setColor(0)
//                        Handler().postDelayed({
//                            //doSomethingHere()
//                        }, 1000)
                            //Timer().schedule(10000){
                            println("yes")
                            //}
                            //delay(2000f)
                            try {
                                Thread.sleep(2000)
                            } catch (e: InterruptedException) {
                                // this part is executed when an exception (in this example InterruptedException) occurs
                            }
                            println("No")
                        }
                    }
                    else{
                        blocks[adjx][adjy].addNumber(1) ///////////////////////
                        blocks[adjx][adjy].setColor(currentPlayer)
                    }


                    //vis[adjx][adjy] = true
                }
            }
        }

    }

    fun playerTurn(){
        val scene = di.scene
        if(scene is Scene6){
            if (scene.players == currentPlayer){
                currentPlayer = 1
            }
            else{
                currentPlayer += 1
            }

            //scene.players
            setBackGroundColor(currentPlayer)

            if(countMove == scene.players){
                if(isCheck(currentPlayer)){
                    if (currentPlayer == lastPlayer){
                        println(" Player Won ")
                        println(currentPlayer)
                        scene.addGameCompleted(currentPlayer)
                        isGameOver = true
                    }
                    lastPlayer = currentPlayer
                }
                else{
                    playerTurn()
                }
            }
            else{
                countMove += 1
                lastPlayer = currentPlayer
            }

        }


    }

    fun isCheck(c:Int):Boolean{
        for (i in 0 until rowSize){
            for (j in 0 until rowSize){
                if (blocks[i][j].blockColor == backGroundColor){
                    return true
                }
            }
        }
        return false
    }

    override fun act(dt: Float) {
//        if(flag){
//            flag = false
//            currentP = currentPlayer
//            val vis = Array(rowSize) { BooleanArray(columnSize) }
//
//            var q: Queue<String> = LinkedList<String>()
//
//            vis[rowx][coly] = true                     /// x, y
//            //q.add(new pair(row, col))
//            //q.add(row + "," + col)
//            q.add(rowx.toString() + "," + coly.toString()) /// x, y
//
//            blocks[rowx][coly].debug = true              /// x, y
//            //System.out.print()
//            println(blocks[rowx][coly].number)           /// x, y
//            //println(nextPos)
//
//            while (!q.isEmpty()){
//                //var cell:Pair<Int,Int> = q.peek()
//                var cell: String? = q.remove()//q.peek()
//
//                var x = Integer.parseInt(cell?.split(",")?.get(0))//cell.first
//                var y = Integer.parseInt(cell?.split(",")?.get(1))//cell.second
//                //blocks[x][y].number = 0
//                //System.out.print(blocks[x][y] + " ")
//
//                //q.remove()
//
//                for (i in 0 until 4){
//                    var adjx = x + dRow[i]
//                    var adjy = y + dCol[i]
//
//                    if(isValid(vis, adjx, adjy)){
//                        //q.add(Pair(adjx,adjy)
//                        if (blocks[adjx][adjy].number == 3){
//                            q.add(adjx.toString() + "," + adjy.toString())
//                            blocks[adjx][adjy].addNumber(1)
//                            blocks[adjx][adjy].setColor(0)
//
//                            println("yes")
//
//                            try {
//                                Thread.sleep(2000)
//                            } catch (e: InterruptedException) {
//                                // this part is executed when an exception (in this example InterruptedException) occurs
//                            }
//                            println("No")
//                        }
//                        else{
//                            blocks[adjx][adjy].addNumber(1) ///////////////////////
//                            blocks[adjx][adjy].setColor(currentP)
//                        }
//                    }
//                }
//            }
//        }
    }




    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.color = backGroundColor//Color(0.9f, 0.9f, 0.9f, 1f)
        batch.draw(di.assets.rect, x, y, width, height)
        batch.color = Color.WHITE

        super.draw(batch, parentAlpha)
    }
}