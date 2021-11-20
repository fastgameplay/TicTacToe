package com.asl.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.asl.tictactoe.databinding.ActivityPlayfieldBinding
import android.os.Handler
import java.util.Random

class PlayField : AppCompatActivity() {


    val random = Random()
    private lateinit var binding: ActivityPlayfieldBinding
    private lateinit var buttonArray : Array<Button>
    var isBotActive : Boolean = false
    private var isCircleStart: Boolean = false
    private var isCirclesTurn : Boolean = false
    private var counter : Int = 0
    private var circleScore : Int = 0
    private var crossScore : Int = 0
    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        isBotActive= intent.getBooleanExtra("BotStatus",false)

        super.onCreate(savedInstanceState)
        binding = ActivityPlayfieldBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        buttonArray = arrayOf(  binding.btn1,binding.btn2,binding.btn3,
                                binding.btn4,binding.btn5,binding.btn6,
                                binding.btn7,binding.btn8,binding.btn9)

        binding.textTurn.text = "✕"
        modifyResult()

        //region Listeners
        binding.btn1.setOnClickListener(){
            onBPress(binding.btn1,isCirclesTurn)
        }
        binding.btn2.setOnClickListener(){
            onBPress(binding.btn2,isCirclesTurn)
        }
        binding.btn3.setOnClickListener(){
            onBPress(binding.btn3,isCirclesTurn)
        }
        binding.btn4.setOnClickListener(){
            onBPress(binding.btn4,isCirclesTurn)
        }
        binding.btn5.setOnClickListener(){
            onBPress(binding.btn5,isCirclesTurn)
        }
        binding.btn6.setOnClickListener(){
            onBPress(binding.btn6,isCirclesTurn)
        }
        binding.btn7.setOnClickListener(){
            onBPress(binding.btn7,isCirclesTurn)
        }
        binding.btn8.setOnClickListener(){
            onBPress(binding.btn8,isCirclesTurn)
        }
        binding.btn9.setOnClickListener(){
            onBPress(binding.btn9,isCirclesTurn)
        }
        binding.btnMenu.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        //endregion
    }

    //function that called when button is pressed
    private fun onBPress(btn : Button, isCircle: Boolean){
        if(equals(btn,"")){
            if(isCircle && !isBotActive) {
                btn.text = "◯"

                if(checkForWin("◯")){
                    isCircleStart = false
                    circleScore++
                    timedMessage("◯ Wins",1000)
                    resetGame()
                }
                counter++
                nextTurn()

            }

            if (!isCircle) {
                btn.text = "✕"

                if(checkForWin("✕")){
                    isCircleStart = true
                    crossScore++
                    timedMessage("✕ Wins",1000)
                    resetGame()
                }
                counter++
                nextTurn()
            }

        }


    }

    //fun that changes the turn of player
    private fun nextTurn(){
        if(isCirclesTurn) {
            isCirclesTurn = false
            binding.textTurn.text = "✕"
        }
        else if( !isCirclesTurn) {
            isCirclesTurn = true
            binding.textTurn.text = "◯"
        }
        if(isBotActive && isCirclesTurn){
            handler.postDelayed({
                generateBotMovement(random.nextInt(8))
            }, 1000)

        }

        }
    fun equals(btn: Button, symbol : String): Boolean{
        return btn.text.toString() == symbol
    }

    fun checkForWin(symbol: String) : Boolean{
        //horizontal
        if (equals(binding.btn1,symbol) && equals(binding.btn2, symbol) && equals(binding.btn3,symbol)) return true
        if (equals(binding.btn4,symbol) && equals(binding.btn5, symbol) && equals(binding.btn6,symbol)) return true
        if (equals(binding.btn7,symbol) && equals(binding.btn8, symbol) && equals(binding.btn9,symbol)) return true

        //Vertical
        if (equals(binding.btn1,symbol) && equals(binding.btn4, symbol) && equals(binding.btn7,symbol)) return true
        if (equals(binding.btn2,symbol) && equals(binding.btn5, symbol) && equals(binding.btn8,symbol)) return true
        if (equals(binding.btn3,symbol) && equals(binding.btn6, symbol) && equals(binding.btn9,symbol)) return true

        //Diagonal
        if (equals(binding.btn1,symbol) && equals(binding.btn5, symbol) && equals(binding.btn9,symbol)) return true
        if (equals(binding.btn3,symbol) && equals(binding.btn5, symbol) && equals(binding.btn7,symbol)) return true

        if(counter == 9) {
            timedMessage("Draw",1000)
            resetGame()
        }
        return false
    }

    fun resetGame(){
        if (isCircleStart) {
            isCirclesTurn = false
        }
        else if (!isCircleStart){
            isCirclesTurn = true
        }
        binding.btn1.text = ""
        binding.btn2.text = ""
        binding.btn3.text = ""
        binding.btn4.text = ""
        binding.btn5.text = ""
        binding.btn6.text = ""
        binding.btn7.text = ""
        binding.btn8.text = ""
        binding.btn9.text = ""
        counter = 0


    }

    fun timedMessage(message: String,miliSeconds : Long){
        modifyResult(message)
        handler.postDelayed({
            modifyResult()
        }, miliSeconds) //1 second

    }

    fun generateBotMovement(startPos: Int){
        //bot logic Needed here
        if(equals(buttonArray[startPos],"")){
             buttonArray[startPos].text = "◯"


            if(checkForWin("◯")){
                isCircleStart = false
                circleScore++
                timedMessage("Bot Wins",1000)
                resetGame()
            }
            counter++
            nextTurn()
        }
        else{
            if (startPos == 8){
                generateBotMovement(0)
            }
            else{
                generateBotMovement(startPos+1)
            }
        }
    }

    fun modifyResult(){
        binding.textResult.text = "${circleScore.toString()} : ${crossScore.toString()}"
    }

    fun modifyResult(message :String){
        binding.textResult.text = message
    }


}