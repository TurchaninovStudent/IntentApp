package com.example.turchaninov.intentapp

import android.content.Intent
import android.os.Bundle
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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

var currentText = ""

@Composable
fun TextInput(modifier: Modifier) {
    var textState by remember { mutableStateOf(currentText) }

    TextField(
        value = textState,
        onValueChange = { newText ->
            textState = newText
            currentText = newText
        },
        label = { Text(stringResource(R.string.input_placeholder_text)) },
        modifier = modifier,
    )
}

@Composable
fun ShareTextScreen(modifier : Modifier = Modifier) {
    val context = LocalContext.current

    Button(
        modifier = modifier,
        onClick = {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, currentText)
            }
            if (currentText != "") {
                context.startActivity(
                    Intent.createChooser(intent, "Поделиться через...")
                )
            } else {
                Toast.makeText(context, "Нельзя отправить пустой текст", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    ) {
        Text("Поделиться текстом")
    }
}

@Composable
fun SecondActivityButton(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Button(
        modifier = modifier,
        onClick = {
            val intent = Intent(context, SecondActivity::class.java).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, currentText)
            }
            context.startActivity(intent)
        }
    ) {
        Text(stringResource(R.string.open_second_activity_text))
    }
}
@Composable
fun MainMenu(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        TextInput(modifier)
        ShareTextScreen(modifier)
        SecondActivityButton(modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IntentAppTheme {
        MainMenu()
    }
}