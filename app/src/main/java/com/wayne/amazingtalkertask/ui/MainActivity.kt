package com.wayne.amazingtalkertask.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wayne.amazingtalkertask.databinding.ActivityMainBinding
import org.koin.core.component.KoinComponent

class MainActivity : AppCompatActivity(), KoinComponent {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
