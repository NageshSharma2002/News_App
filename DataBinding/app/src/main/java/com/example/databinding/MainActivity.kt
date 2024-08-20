package com.example.databinding

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.databinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        setContentView(R.layout.activity_main) as bimding will manage the views

//        val quoteTextView = findViewById<TextView>(R.id.quoteText)
//        val quoteAuthView = findViewById<TextView>(R.id.quoteAuth)

//        binding.quoteText.text = "Just do it. There's no try"
//        binding.quoteAuth.text = "Shaktiman"

          val quoteObj = Quote("Just do it", "Shaktiman")

          binding.quote = quoteObj

        }
    }

