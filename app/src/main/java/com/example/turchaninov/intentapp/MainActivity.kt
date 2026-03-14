package com.example.turchaninov.intentapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.turchaninov.intentapp.ui.theme.IntentAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntentAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainMenu(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ShareTextScreen(modifier : Modifier = Modifier) {
    var textState by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = modifier
    ) {
        TextField(
            value = textState,
            onValueChange = { newText ->
                textState = newText
            },
            label = { Text("Введите текст") },
        )
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, textState)
                }
                if (textState != "")
                {
                    context.startActivity(
                        Intent.createChooser(intent, "Поделиться через...")
                    )
                } else {
                    Toast.makeText(context, "Нельзя отправить пустой текст", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Поделиться текстом")
        }
    }
}

@Composable
fun MainMenu(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier,
    ) {
        ShareTextScreen(modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IntentAppTheme {
        MainMenu(
            modifier = Modifier.fillMaxSize()
        )
    }
}