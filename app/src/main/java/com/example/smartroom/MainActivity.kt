package com.example.smartroom

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.smartroom.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainActivityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        mainActivityMainBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        super.onCreate(savedInstanceState)
        setContentView(mainActivityMainBinding.root)

        mainActivityMainBinding.button1.setOnClickListener {
            goToFragmentOne()
        }

        mainActivityMainBinding.button2.setOnClickListener {
            goToFragmentTwo()
        }
    }

    private fun goToFragmentOne() {
        val fragmentOne = FragmentOne()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flFrament, fragmentOne)
            .commit()
    }

    private fun goToFragmentTwo() {
        val fragmentTwo = FragmentTwo()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flFrament, fragmentTwo)
            .commit()
    }
}