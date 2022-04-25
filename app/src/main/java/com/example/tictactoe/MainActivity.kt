package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    enum class Turn{
        Ring, Cross
    }

    private var firstTurn = Turn.Cross
    private var currentTurn = Turn.Cross
    private var boardList = mutableListOf<Button>()
    private var gameOver = false

    private var crossScore = 0
    private var ringScore = 0

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()

        binding.scoreBtn.setOnClickListener {
            showResult()
        }

    }


    private fun initBoard() {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }


    fun boardTapped(view: View) {

        if(view !is Button){
            return
        }
        markBoard(view)

        if(hasWon("O")){
            ringScore++
            result("Player 2 wins!")
            gameOver=true
        }

        else if(hasWon("X")){
            crossScore++
            result("Player 1 wins!")
            gameOver = true
        }

        if(allMarked() ){
            result("Draw")
            gameOver = true
        }
        if(!gameOver)
        setTurnLabel()

    }

    private fun hasWon(s: String): Boolean {

        // rows
        if( match(binding.a1, s) && match(binding.a2, s) && match(binding.a3, s)){
            return true
        }
        if( match(binding.b1, s) && match(binding.b2, s) && match(binding.b3, s)){
            return true
        }
        if( match(binding.c1, s) && match(binding.c2, s) && match(binding.c3, s)){
            return true
        }

        // columns
        if( match(binding.a1, s) && match(binding.b1, s) && match(binding.c1, s)){
            return true
        }
        if( match(binding.a2, s) && match(binding.b2, s) && match(binding.c2, s)){
            return true
        }
        if( match(binding.a3, s) && match(binding.b3, s) && match(binding.c3, s)){
            return true
        }

        // diagonal
        if( match(binding.a1, s) && match(binding.b2, s) && match(binding.c3, s)){
            return true
        }
        if( match(binding.a3, s) && match(binding.b2, s) && match(binding.c1, s)) {
            return true
        }

        return false
    }



    // return true or false, if it's a match or not
    private fun match(button: Button , symbol: String): Boolean{
        return(button.text == symbol)
    }

    private fun result(title: String) {

        val message = "\nPlayer 2: $ringScore\n\nPlayer 1: $crossScore"

        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Reset")
            {_,_ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()
    }

    // ok this isn't nice, but I wanted to be able to also just show the score at any point
    // without risking to interfere with the function above.

    private fun showResult() {

        val message = "\nPlayer 2: $ringScore\n\nPlayer 1: $crossScore"

        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(true)
            .show()
    }

    // clears the text onn the buttons
    private fun resetBoard() {
        for(button in boardList){
            button.text=""
        }
        if (firstTurn == Turn.Ring) Turn.Cross else Turn.Ring

        gameOver = false
        setTurnLabel()
    }

    // check to see if all the squares have been marked
    private fun allMarked(): Boolean {
        for(button in boardList){
            if (button.text == ""){
                return false
            }
        }
        return true
    }


    private fun markBoard(button: Button) {
        if(button.text != "")
            return

        if(currentTurn == Turn.Ring){

            button.visibility = View.VISIBLE
            //loading our custom made animations
            val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
            //starting the animation
            button.startAnimation(animation)

            button.text = "O"
            currentTurn = Turn.Cross
            //binding.turnTV.text = "Player 1"

        }
        else if(currentTurn == Turn.Cross) {

            button.visibility = View.VISIBLE
            //loading our custom made animations
            val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
            //starting the animation
            button.startAnimation(animation)

            button.text = "X"
            currentTurn = Turn.Ring
            //binding.turnTV.text = "Player 2"

        }


    }


    private fun setTurnLabel(){
        var turnTex = ""
        if(currentTurn == Turn.Cross){
            turnTex = "Player 1"
        }

        if(currentTurn == Turn.Ring){
            turnTex = "Player 2"
        }

        binding.turnTV.visibility = View.VISIBLE
        //loading our custom made animations
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        //starting the animation
        binding.turnTV.startAnimation(animation)
        binding.turnTV.text = turnTex
    }


}