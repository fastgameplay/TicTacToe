package com.asl.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asl.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnWithBot.setOnClickListener(){
            startGame(true)
        }
        binding.btnWithPlayer.setOnClickListener(){
            startGame(false)
        }
    }

    fun startGame(isBotActive : Boolean){
        val intent = Intent(this, PlayField::class.java)
        intent.putExtra("BotStatus",isBotActive)
        startActivity(intent)
    }
}