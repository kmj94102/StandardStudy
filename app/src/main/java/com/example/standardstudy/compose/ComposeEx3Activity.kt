package com.example.standardstudy.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.standardstudy.R
import com.example.standardstudy.compose.ui.theme.StandardStudyTheme
import kotlinx.coroutines.launch

class ComposeEx3Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StandardStudyTheme {
                ContainerEx3()
            }
        }
    }
}

@Composable
fun ContainerEx3() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
//        MySnackBar()
        TextFieldTest()
    }
}

@Composable
fun MySnackBar() {

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    val coroutineScope = rememberCoroutineScope()

    val buttonTitle: (SnackbarData?) -> String = {
        if (it != null) {
            "스낵바 숨기기"
        } else {
            "스낵바 보여주기"
        }
    }

    val buttonColor: (SnackbarData?) -> Color = {
        if (it != null) {
            Color.Black
        } else {
            Color.Blue
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                backgroundColor = buttonColor(snackBarHostState.currentSnackbarData),
                contentColor = Color.White
            ),
            onClick = {
                if (snackBarHostState.currentSnackbarData != null) {
                    snackBarHostState.currentSnackbarData?.dismiss()
                    return@Button
                }
                coroutineScope.launch {
                    snackBarHostState.showSnackbar(
                        message = "오늘도 빡코딩",
                        actionLabel = "확인",
                        SnackbarDuration.Short
                    ).let {
                        when (it) {
                            SnackbarResult.ActionPerformed -> {

                            }
                            SnackbarResult.Dismissed -> {

                            }
                        }
                    }
                }
            }) {
            Text(text = buttonTitle(snackBarHostState.currentSnackbarData))
        }

        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun TextFieldTest() {

    var userInput by remember {
        mutableStateOf(TextFieldValue())
    }

    var phoneNumberInput by remember {
        mutableStateOf(TextFieldValue())
    }

    var emailInput by remember {
        mutableStateOf(TextFieldValue())
    }

    var passwordInput by remember {
        mutableStateOf(TextFieldValue())
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = userInput,
            onValueChange = { newValue ->
                userInput = newValue
            },
            singleLine = false,
            maxLines = 2,
            label = { Text(text = "사용자 입력") },
            placeholder = { Text(text = "작성해 주세요") }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = phoneNumberInput,
            onValueChange = { newValue ->
                phoneNumberInput = newValue
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            label = { Text(text = "전화번호 입력") },
            placeholder = { Text(text = "작성해 주세요") }
        )

        val context = LocalContext.current

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = emailInput,
            onValueChange = { newValue ->
                emailInput = newValue
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            label = { Text(text = "이메일 주소 입력") },
            placeholder = { Text(text = "작성해 주세요") },
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = {
                    context.makeToast("아이콘 누름")
                }) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
                }
            }
        )

        val shouldShowPassword = remember {
            mutableStateOf(false)
        }

        val passwordRes: (Boolean) -> Int = {
            if (it) {
                R.drawable.ic_baseline_visibility_24
            } else {
                R.drawable.ic_baseline_visibility_off_24
            }
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = passwordInput,
            onValueChange = { newValue ->
                passwordInput = newValue
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            label = { Text(text = "비밀번호 입력") },
            placeholder = { Text(text = "작성해 주세요") },
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
            visualTransformation = if (shouldShowPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = {
                    shouldShowPassword.value = shouldShowPassword.value.not()
                }) {
                    Icon(
                        painter = painterResource(id = passwordRes(shouldShowPassword.value)),
                        contentDescription = null
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview3() {
    StandardStudyTheme {
        ContainerEx3()
    }
}