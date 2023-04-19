package com.example.map

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Space
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 30.dp)
    ) {

        var usernameText by remember { mutableStateOf("") }
        var passwordText by remember { mutableStateOf("") }
        val showPassword = remember { mutableStateOf(false) }
        val context = LocalContext.current
        val launcherMultiplePermissions = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissionsMap ->
            val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
            if (areGranted) {
                navController.navigate("map_screen")
                Toast.makeText(context, "granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "denied", Toast.LENGTH_SHORT).show()
            }
        }

        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = "Hello.",
            fontSize = 34.sp,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight(900),
        )
        Text(
            text = "Welcome Back",
            fontSize = 34.sp,
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight(900),
            modifier = Modifier.padding(top = 5.dp)
        )
        Spacer(modifier = Modifier.height(90.dp))
        Text(
            text = "USERNAME",
            fontSize = 14.sp,
            fontWeight = FontWeight(450),
            color = Color.Gray,
            fontFamily = FontFamily.Serif,
        )
        TextField(
            value = usernameText,
            onValueChange = { usernameText = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                unfocusedIndicatorColor = colorResource(id = R.color.red_200)
            ),
            modifier = Modifier
                .padding(top = 0.dp)
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.White),
            placeholder = {
                Text(
                    text = "abc@gmail.com",
                    color = colorResource(id = R.color.gray)
                )
            },
        )
        Spacer(modifier = Modifier.height(90.dp))
        Text(
            text = "PASSWORD",
            fontSize = 14.sp,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight(450),
            color = Color.Gray,
        )
        TextField(
            value = passwordText,
            onValueChange = { passwordText = it },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = colorResource(
                    id = R.color.red_200
                ),
                unfocusedIndicatorColor = colorResource(id = R.color.red_200)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.White),
            visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = {
                    showPassword.value = !showPassword.value
                }) {
                    val string = if (showPassword.value) "HIDE" else "SHOW"
                    Text(
                        text = string,
                        color = colorResource(id = R.color.blue_100),
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight(500),
                        fontSize = 11.sp
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.NumberPassword
            ),
            placeholder = {
                Text(
                    text = "1234", color = colorResource(id = R.color.gray)
                )
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .background(Color.White)
                .height(50.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            ClickableText(
                text = AnnotatedString(text = "Forgot password ?"),
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.SansSerif
                ),
                onClick = {
                    navController.navigate("forgot_password_screen")
                }
            )
        }
        Spacer(modifier = Modifier.height(60.dp))
        OutlinedButton(
            onClick = {
                val username = usernameText
                val password = passwordText

                if (username.isEmpty() or password.isEmpty()) {
                    Toast.makeText(context, "Both fields are required!", Toast.LENGTH_SHORT).show()
                } else {
                    checkAndRequestLocationPermissions(
                        context,
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        launcherMultiplePermissions
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .padding(horizontal = 10.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue_100)),
            border = BorderStroke(0.5.dp, color = colorResource(id = R.color.blue_100)),
            shape = RoundedCornerShape(7.dp)
        ) {
            Text(
                text = "LOGIN",
                fontWeight = FontWeight(600),
                fontSize = 16.sp
            )
        }
    }
}

fun checkAndRequestLocationPermissions(
    context: Context,
    permissions: Array<String>,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>
) {
    if (
        permissions.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    ) {
    } else {
        launcher.launch(permissions)
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    //  Login()
}