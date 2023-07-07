package com.example.myapplication

import android.app.Service
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.data.remote.dto.PostService
import com.example.myapplication.data.remote.dto.PostServiceImpl
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val service = PostService.create()
    private val mainScope = MainScope()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val resultTextView = findViewById<TextView>(R.id.resultTextView)

        val button1 = findViewById<Button>(R.id.button1)
        val checkuser_tv = findViewById<TextView>(R.id.checkuser_tv)

        val button2 = findViewById<Button>(R.id.button2)
        val resulttoken = findViewById<TextView>(R.id.token_tv)



        button.setOnClickListener {
            mainScope.launch {
                val version=service.getVersion()
                println(version)
                resultTextView.text = version
            }
        }

        button1.setOnClickListener {
            mainScope.launch {
//                Toast.makeText(applicationContext, "In", Toast.LENGTH_SHORT).show()
                val response=service.checkProfile()
                if (response != null) {
                    checkuser_tv.text = response.user_exists.toString()
                }
            }
        }

        button2.setOnClickListener {
            mainScope.launch {
//                Toast.makeText(applicationContext, "In", Toast.LENGTH_SHORT).show()
                val response=service.validateToken()
                if (response != null) {
                    if (response.success){
                        resulttoken.text = response.user_id.toString()
                    }
                }
            }
        }

    }
}
