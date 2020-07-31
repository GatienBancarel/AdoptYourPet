package com.gbancarel.adoptyourpet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.tooling.preview.Preview
import com.gbancarel.adoptyourpet.ui.FindYourPetTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindYourPetTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FindYourPetTheme {
        Greeting("Android")
    }
}