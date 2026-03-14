package com.example.turchaninov.intentapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
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

val phoneNumberPattern = "\\+79\\d{9}".toRegex()

@Composable
fun PhoneButton(phone : String, isValidPhoneNumber : Boolean, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Button(
        onClick = {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = "tel:$phone".toUri()
            }
            context.startActivity(intent)
        },
        enabled = isValidPhoneNumber,
        modifier = modifier,
    ) {
        Text(stringResource(R.string.phone_button_text))
    }
}

@Composable
fun TextInput(value : String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.input_placeholder_text)) },
        modifier = modifier,
    )
}

@Composable
fun ShareTextButton(text : String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Button(
        onClick = {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
            if (text != "") {
                context.startActivity(
                    Intent.createChooser(intent, context.getString(R.string.share_through_text))
                )
            } else {
                Toast.makeText(context,
                    context.getString(R.string.share_empty_text_error), Toast.LENGTH_SHORT)
                    .show()
            }
        },
        modifier = modifier,
    ) {
        Text(stringResource(R.string.share_text))
    }
}

@Composable
fun SecondActivityButton(text : String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    Button(
        modifier = modifier,
        onClick = {
            val intent = Intent(context, SecondActivity::class.java).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
            context.startActivity(intent)
        }
    ) {
        Text(stringResource(R.string.open_second_activity_text))
    }
}
@Composable
fun MainMenu(modifier: Modifier = Modifier) {
    var textState by remember { mutableStateOf("") }
    var isValidPhoneNumber by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        TextInput(textState, { newText ->
            textState = newText
            isValidPhoneNumber = phoneNumberPattern.matches(newText)
        })
        Spacer(modifier = Modifier.height(16.dp))
        Column(Modifier.width(IntrinsicSize.Max)) {
            PhoneButton(
                textState, isValidPhoneNumber,
                Modifier.fillMaxWidth(),
            )
            ShareTextButton(
                textState,
                Modifier.fillMaxWidth(),
            )
            SecondActivityButton(
                textState,
                Modifier.fillMaxWidth(),
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun MainMenuPreview() {
    IntentAppTheme {
        MainMenu()
    }
}