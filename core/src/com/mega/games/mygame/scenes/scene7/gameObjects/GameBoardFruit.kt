package com.mega.games.mygame.scenes.scene7.gameObjects

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.math.MathUtils
import com.mega.games.engine.dependency.DI
import com.mega.games.engine.gameObject.GameObject
import com.mega.games.engine.gameObject.addInputListener
import com.mega.games.mygame.scenes.scene6.gameObjects.AtomCell
import com.mega.games.mygame.utils.assets
import java.util.*
import java.util.logging.Handler
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule
import kotlin.math.abs

class GameBoardFruit(private val di: DI): GameObject() {

    private var rowSize = 0
    private var columnSize = 0

    private var blocks = ArrayList<ArrayList<colorCell>>()

    private var xDown = 0f
    private var yDown = 0f
    private var xUp = 0f
    private var yUp = 0f
    private var xIndex = 0
    private var yIndex = 0

    private var dRow = intArrayOf(-1, 0, 1, 0)//{-1, 0, 1, 0}

    private var dCol = intArrayOf(0, 1, 0, -1)//{0, 1, 0, -1}

    private var isCascard = false

    private var isValidMove = false
    private var isTouch = false

    private var isError = false

    private var elapsedTime = 0f

    private var directionMove = 0
    private var lastColor_1 = 0
    private var lastColor_2 = 0

    private var uCount = 0

    private var delayTime =0

    private var isStart = true

    init {
        this.addInputListener()
    }

    fun setRowSize(s: Int) {
        rowSize = s
    }

    fun setColumnSize(s: Int) {
        columnSize = s
    }

    fun generate() {
        val blockSize = width / rowSize

        for(i in 0 until rowSize){
            blocks.add(ArrayList())

            for(j in 0 until columnSize){
                val b = colorCell(di, i, j)
                b.setSize(blockSize, blockSize)
                b.setPosition(i * blockSize, j * blockSize)

                addActor(b)


                blocks[i].add(b)
                blocks[i][j].setColor(randomNumber())

            }
        }
        //addColor()
//        try {
//            Thread.sleep(2000)
//        } catch (e: InterruptedException) {
//            // this part is executed when an exception (in this example InterruptedException) occurs
//        }
//        Handler().({
//            TODO("Do something")
//        }, 2000)
        Timer().schedule(0) {
            //TODO("Do something")
            checkUpdate()
        }

        Timer().schedule(100) {
            //TODO("Do something")
            delayTime = 20
        }

        //delayTime = 20
        //checkUpdate()
    }

    fun randomNumber() : Int{
        var num = MathUtils.random(1, 5)
        return num
    }

    fun cellClicked(i: Int, j: Int, x:Float, y:Float){
        isStart = false
        xDown = x
        yDown = y
        xIndex = i
        yIndex = j
    }

    fun cellDirection(x:Float, y:Float){
        isTouch = true
        var diffX = abs(xDown - x)
        var diffY = abs(yDown - y)
        System.out.print("direction ")

        if (diffX > diffY){
            if (xDown - x >0){
                //Move to left
                System.out.print("left ")
                if(xIndex!=0){
                    swap(xIndex,yIndex,0)
                    bfs(xIndex,yIndex)
                    bfs(xIndex-1,yIndex)
                    if (!isValidMove){
                        System.out.print("not a valid move left ")
                        swap(xIndex,yIndex,0)

                        lastColor_1 = blocks[xIndex-1][yIndex].numberAllocated
                        lastColor_2 = blocks[xIndex][yIndex].numberAllocated
                        directionMove = 0

//                        blocks[xIndex-1][yIndex].isRed = true
//                        blocks[xIndex][yIndex].isRed = true
                        blocks[xIndex-1][yIndex].setColor(8)// = true
                        blocks[xIndex][yIndex].setColor(8)// = true

                        isError = true

                        isTouch = false

                    }
//                    try {
//                        Thread.sleep(200)
//                    } catch (e: InterruptedException) {
//                        // this part is executed when an exception (in this example InterruptedException) occurs
//                    }
                    cascade()
                }
                else{
                    System.out.print("left Not possible")
                }

            }
            else{
                //move to right
                System.out.print("right ")
                //swap(xIndex,yIndex,2)
                if(xIndex!=(rowSize-1)){
                    swap(xIndex,yIndex,2)
                    bfs(xIndex,yIndex)
                    bfs(xIndex+1,yIndex)
                    if (!isValidMove){
                        System.out.print("not a valid move right ")
                        swap(xIndex,yIndex,2)

                        lastColor_1 = blocks[xIndex+1][yIndex].numberAllocated
                        lastColor_2 = blocks[xIndex][yIndex].numberAllocated
                        directionMove = 2

//                        blocks[xIndex+1][yIndex].isRed = true
//                        blocks[xIndex][yIndex].isRed = true
                        blocks[xIndex+1][yIndex].setColor(8)// = true
                        blocks[xIndex][yIndex].setColor(8)// = true

                        isError = true

                        isTouch = false

                    }
//                    try {
//                        Thread.sleep(200)
//                    } catch (e: InterruptedException) {
//                        // this part is executed when an exception (in this example InterruptedException) occurs
//                    }
                    cascade()
                }
                else{
                    System.out.print("right Not possible")
                }
            }
        }
        else{
            if (yDown - y >0){
                //Move to down
                System.out.print("down ")
//                swap(xIndex,yIndex,3)
                if(yIndex!=0){
                    swap(xIndex,yIndex,3)
                    bfs(xIndex,yIndex)
                    bfs(xIndex,yIndex-1)
                    if (!isValidMove){
                        System.out.print("not a valid move down ")
                        swap(xIndex,yIndex,3)

                        lastColor_1 = blocks[xIndex][yIndex-1].numberAllocated
                        lastColor_2 = blocks[xIndex][yIndex].numberAllocated
                        directionMove = 3

//                        blocks[xIndex][yIndex-1].isRed = true
//                        blocks[xIndex][yIndex].isRed = true
                        blocks[xIndex][yIndex-1].setColor(8)// = true
                        blocks[xIndex][yIndex].setColor(8)//isRed = true

                        isError = true

                        isTouch = false

                    }
//                    try {
//                        Thread.sleep(200)
//                    } catch (e: InterruptedException) {
//                        // this part is executed when an exception (in this example InterruptedException) occurs
//                    }
                    cascade()
                }
                else{
                    System.out.print("down Not possible")
                }
            }
            else{
                //move to up
                System.out.print("up ")
                //swap(xIndex,yIndex,1)
                if(yIndex!=(columnSize-1)){
                    swap(xIndex,yIndex,1)
                    bfs(xIndex,yIndex)
                    bfs(xIndex,yIndex+1)
                    if (!isValidMove){
                        System.out.print("not a valid move up ")
                        swap(xIndex,yIndex,1)

                        lastColor_1 = blocks[xIndex][yIndex+1].numberAllocated
                        lastColor_2 = blocks[xIndex][yIndex].numberAllocated
                        directionMove = 1

//                        blocks[xIndex][yIndex+1].isRed = true
//                        blocks[xIndex][yIndex].isRed = true
                        blocks[xIndex][yIndex+1].setColor(8)//isRed = true
                        blocks[xIndex][yIndex].setColor(8)//isRed = true

                        isError = true

                        isTouch = false

                    }
//                    try {
//                        Thread.sleep(200)
//                    } catch (e: InterruptedException) {
//                        // this part is executed when an exception (in this example InterruptedException) occurs
//                    }
                    cascade()
                }
                else{
                    System.out.print("up Not possible")
                }
            }
        }
    }

    //left --0
    //up -- 1
    //right -- 2
    //down -- 3
    fun swap(i: Int,j: Int, dir:Int){
        val blockSize = width / rowSize
        System.out.print(" swap")
        if (dir == 0){
            System.out.print(" swap1")
//            var b = colorCell(di, i, j)
//            b.setSize(blockSize, blockSize)
//            b.setPosition(i * blockSize, j * blockSize)
//            //addActor(b)
//            b = blocks[i][j]
//            blocks[i][j] = blocks[i-1][j]
//            blocks[i-1][j] = b
//            //blocks[i][j]
            var num = 0
            num = blocks[i][j].numberAllocated
            //blocks[i][j].numberAllocated = blocks[i-1][j].numberAllocated
            blocks[i][j].setColor(blocks[i-1][j].numberAllocated)
            blocks[i-1][j].setColor(num)
            //blocks[i-1][j].numberAllocated = num
            System.out.print(" Left Moved ")
        }
        if (dir == 1){
            var num = 0
            num = blocks[i][j].numberAllocated
            //blocks[i][j].numberAllocated = blocks[i-1][j].numberAllocated
            blocks[i][j].setColor(blocks[i][j+1].numberAllocated)
            blocks[i][j+1].setColor(num)
            //blocks[i-1][j].numberAllocated = num
            System.out.print(" up Moved ")
        }
        if (dir == 2){
            var num = 0
            num = blocks[i][j].numberAllocated
            //blocks[i][j].numberAllocated = blocks[i-1][j].numberAllocated
            blocks[i][j].setColor(blocks[i+1][j].numberAllocated)
            blocks[i+1][j].setColor(num)
            //blocks[i-1][j].numberAllocated = num
            System.out.print(" Right Moved ")
        }
        if (dir == 3){
            var num = 0
            num = blocks[i][j].numberAllocated

            blocks[i][j].setColor(blocks[i][j-1].numberAllocated)
            blocks[i][j-1].setColor(num)

            System.out.print(" down Moved ")
        }
    }

    fun isValid(vis: Array<BooleanArray>, row: Int, col: Int):Boolean {
        if (row < 0 || col < 0 || row>=rowSize || col >=columnSize){
            return false
        }

        if (vis[row][col]){
            return false
        }

        //if (blocks[row][col] == 0)
        return true
    }

    fun bfs(row: Int, col: Int){

        var count = 1

        var num = blocks[row][col].numberAllocated

        val vis = Array(rowSize) { BooleanArray(columnSize) }

        var q: Queue<String> = LinkedList<String>()

        var r: Queue<String> = LinkedList<String>() //remove

        vis[row][col] = true

        q.add(row.toString() + "," + col.toString())

        blocks[row][col].debug = true

        //println(blocks[row][col].numberAllocated)
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

                if(isValid(vis, adjx, adjy) && (blocks[adjx][adjy].numberAllocated == num)){
                    //q.add(Pair(adjx,adjy)
                    q.add(adjx.toString() + "," + adjy.toString())
                    r.add(adjx.toString() + "," + adjy.toString())
                    count++

                    vis[adjx][adjy] = true
                }
            }
        }

//        if (count > 2 && count < 5){
//            var isValidPair = false
//            var temp: Queue<String> = LinkedList<String>()
//            if (count == 3){
////                var temp: Queue<String> = LinkedList<String>()
//                var cell: String? = r.remove()//
//
//                var x1 = Integer.parseInt(cell?.split(",")?.get(0))//cell.first
//                var y1 = Integer.parseInt(cell?.split(",")?.get(1))//cell.second
//                temp.add(x1.toString() + "," + y1.toString())
//
//
//                cell =  r.remove()//
//
//                var x2 = Integer.parseInt(cell?.split(",")?.get(0))//cell.first
//                var y2 = Integer.parseInt(cell?.split(",")?.get(1))//cell.second
//                temp.add(x2.toString() + "," + y2.toString())
//
//                cell =  r.remove()//
//
//                var x3 = Integer.parseInt(cell?.split(",")?.get(0))//cell.first
//                var y3 = Integer.parseInt(cell?.split(",")?.get(1))//cell.second
//                temp.add(x3.toString() + "," + y3.toString())
//                if (((x1==x2) && (x2==x3)) || ((y1==y2) && (y2==y3))){
//                    isValidPair = true
//                }
//            }
//            if (count == 4){
//                //var temp: Queue<String> = LinkedList<String>()
//                var cell: String? = r.remove()//
//
//                var x1 = Integer.parseInt(cell?.split(",")?.get(0))//cell.first
//                var y1 = Integer.parseInt(cell?.split(",")?.get(1))//cell.second
//                temp.add(x1.toString() + "," + y1.toString())
//
//
//                cell =  r.remove()//
//
//                var x2 = Integer.parseInt(cell?.split(",")?.get(0))//cell.first
//                var y2 = Integer.parseInt(cell?.split(",")?.get(1))//cell.second
//                temp.add(x2.toString() + "," + y2.toString())
//
//                cell =  r.remove()//
//
//                var x3 = Integer.parseInt(cell?.split(",")?.get(0))//cell.first
//                var y3 = Integer.parseInt(cell?.split(",")?.get(1))//cell.second
//                temp.add(x3.toString() + "," + y3.toString())
//
//                cell =  r.remove()//
//
//                var x4 = Integer.parseInt(cell?.split(",")?.get(0))//cell.first
//                var y4 = Integer.parseInt(cell?.split(",")?.get(1))//cell.second
//                temp.add(x3.toString() + "," + y3.toString())
//
//                if (((x1==x2) && (x2==x3) && (x4==x3)) || ((y1==y2) && (y2==y3) && (y4==y3))){
//                    isValidPair = true
//                }
//            }
//            if (isValidPair){
//                while (!temp.isEmpty()){
//                    var cell: String? = temp.remove()//
//
//                    var x = Integer.parseInt(cell?.split(",")?.get(0))//cell.first
//                    var y = Integer.parseInt(cell?.split(",")?.get(1))//cell.second
//
//                    blocks[x][y].numberAllocated = 0
//                    blocks[x][y].setColor( 0 )
//                }
//                isValidMove = true
//                uCount += count
//            }
//
//        }

        if(count > 2){
            while (!r.isEmpty()){
                var cell: String? = r.remove()//

                var x = Integer.parseInt(cell?.split(",")?.get(0))//cell.first
                var y = Integer.parseInt(cell?.split(",")?.get(1))//cell.second

                blocks[x][y].numberAllocated = 0
                blocks[x][y].setColor( 0 )
            }
            isValidMove = true
            uCount += count
        }

        if(count > 4){
            //addBomb
//            if(!isStart){
//                blocks[row][col].setColor(7)
//            }

//            while (!r.isEmpty()){
//                var cell: String? = r.remove()//
//
//                var x = Integer.parseInt(cell?.split(",")?.get(0))//cell.first
//                var y = Integer.parseInt(cell?.split(",")?.get(1))//cell.second
//
//                blocks[x][y].numberAllocated = 0
//                blocks[x][y].setColor( 0 )
//            }
//            isValidMove = true
//            uCount += count
        }

        if (count<3 && isTouch){
            isValidMove = false
        }


    }

    fun match(i: Int,j: Int){
        bfs(i,j)
    }

    fun checkUpdate(){
        for(i in 0 until rowSize){
            for(j in 0 until columnSize){
                if (blocks[i][j].numberAllocated!=0){
                    bfs(i,j)
                    //match()
                }
            }
        }
        try {
            Thread.sleep(200)
        } catch (e: InterruptedException) {
            // this part is executed when an exception (in this example InterruptedException) occurs
        }
        cascade()
    }

    fun cascade(){
        for (i in 0 until rowSize) {
            for (j in 1 until rowSize) {
                var nextPos = j

                if (blocks[i][nextPos].numberAllocated==0){ nextPos = 0}
                while (nextPos > 0){
                    //blocks[i][nextPos].debug = true
                    if (blocks[i][nextPos-1].numberAllocated==0){ blocks[i][nextPos].debug = true
                        blocks[i][nextPos-1].numberAllocated = blocks[i][nextPos].numberAllocated
                        blocks[i][nextPos-1].setColor(blocks[i][nextPos].numberAllocated)
                        //blocks[i][nextPos-1].isBlock = true
                        blocks[i][nextPos].numberAllocated = 0
                        blocks[i][nextPos].setColor(0)
                        //blocks[i][nextPos].isBlock = false
                        try {
                            Thread.sleep(200)
                        } catch (e: InterruptedException) {
                            // this part is executed when an exception (in this example InterruptedException) occurs
                        }
                    }
                    else{
//                        if(blocks[i][nextPos].numberAllocated == blocks[i][nextPos - 1].numberAllocated){
//                            blocks[i][nextPos].addNumber(0)
//                            blocks[i][nextPos].isBlock = false
//                            blocks[i][nextPos - 1].addNumber(blocks[i][nextPos - 1].numberAllocated * 2)
//                        }
//                        nextPos = 0
                    }

                    nextPos -= 1
//                    Timer().schedule(200) {
//                        //TODO("Do something")
//                        checkUpdate()
//                    }

                }
            }
        }
        var updateCount = 0

        for (i in 0 until rowSize){

            //var updateCount = 0
            while (blocks[i][columnSize-1].numberAllocated==0){
                updateCount++
                blocks[i][columnSize-1].setColor(randomNumber())
                var nextPos = columnSize-1
                while (nextPos > 0){
                    //blocks[i][nextPos].debug = true
                    if (blocks[i][nextPos-1].numberAllocated==0){ blocks[i][nextPos].debug = true
                        blocks[i][nextPos-1].numberAllocated = blocks[i][nextPos].numberAllocated
                        blocks[i][nextPos-1].setColor(blocks[i][nextPos].numberAllocated)
                        //blocks[i][nextPos-1].isBlock = true
                        blocks[i][nextPos].numberAllocated = 0
                        blocks[i][nextPos].setColor(0)
                        //blocks[i][nextPos].isBlock = false
                        try {
                            Thread.sleep(200)
                        } catch (e: InterruptedException) {
                            // this part is executed when an exception (in this example InterruptedException) occurs
                        }
                    }
                    else{
//                        if(blocks[i][nextPos].numberAllocated == blocks[i][nextPos - 1].numberAllocated){
//                            blocks[i][nextPos].addNumber(0)
//                            blocks[i][nextPos].isBlock = false
//                            blocks[i][nextPos - 1].addNumber(blocks[i][nextPos - 1].numberAllocated * 2)
//                        }
//                        nextPos = 0
                    }

                    nextPos -= 1

                }
            }


        }
        if (updateCount > 0){
            checkUpdate()
            //cascade()
        }

        System.out.print("over")
    }




    fun match(){

    }

    //fun move

    fun addColor(){

    }

    override fun act(dt: Float) {

        if (isError){
            //System.out.println("Red")
            //blockColor = Color.RED
//            lastColor_1 = blocks[xIndex][yIndex].numberAllocated
//            lastColor_2 = blocks[xIndex][yIndex].numberAllocated
            elapsedTime += dt
            if(elapsedTime > 1f && elapsedTime <2f){
                blocks[xIndex][yIndex].setColor(9)// = lastColor_2
                if (directionMove==0){
                    blocks[xIndex-1][yIndex].setColor(9)// = lastColor_1
                }
                if(directionMove==1){
                    blocks[xIndex][yIndex+1].setColor(9)
                }
                if (directionMove == 2){
                    blocks[xIndex+1][yIndex].setColor(9)
                }
                if (directionMove == 3){
                    blocks[xIndex][yIndex-1].setColor(9)
                }
            }

            if(elapsedTime > 2f && elapsedTime <3f){
                blocks[xIndex][yIndex].setColor(8)// = lastColor_2
                if (directionMove==0){
                    blocks[xIndex-1][yIndex].setColor(8)// = lastColor_1
                }
                if(directionMove==1){
                    blocks[xIndex][yIndex+1].setColor(8)
                }
                if (directionMove == 2){
                    blocks[xIndex+1][yIndex].setColor(8)
                }
                if (directionMove == 3){
                    blocks[xIndex][yIndex-1].setColor(8)
                }
            }


            if(elapsedTime > 3f){
                System.out.print("Red")
                //elapsedTime -= 0.5f
                blocks[xIndex][yIndex].setColor(lastColor_2)// = lastColor_2
                if (directionMove==0){
                    blocks[xIndex-1][yIndex].setColor(lastColor_1)// = lastColor_1
                }
                if(directionMove==1){
                    blocks[xIndex][yIndex+1].setColor(lastColor_1)
                }
                if (directionMove == 2){
                    blocks[xIndex+1][yIndex].setColor(lastColor_1)
                }
                if (directionMove == 3){
                    blocks[xIndex][yIndex-1].setColor(lastColor_1)
                }
                elapsedTime = 0f
                isError = false

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