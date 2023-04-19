package com.example.map

import android.widget.ImageButton
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPass(navController: NavController?) {

    Column(modifier = Modifier
        .wrapContentHeight()
        .background(Color.White)) {
        val context = LocalContext.current

        Spacer(modifier = Modifier.height(15.dp))
        IconButton(
            onClick = {
                navController?.navigate("login_screen")
            }, modifier = Modifier.padding(start = 10.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.left_arrow), contentDescription = null,
                modifier = Modifier.size(45.dp),
                tint = colorResource(id = R.color.blue_100)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .background(Color.White)

        ) {

            var emailText by remember {
                mutableStateOf("")
            }

            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Forgot",
                fontSize = 34.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight(900),
            )
            Text(
                text = "Password?",
                fontSize = 34.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight(900),
                modifier = Modifier.padding(top = 5.dp)
            )
            Spacer(modifier = Modifier.height(90.dp))
            Text(
                text = "Email",
                fontSize = 14.sp,
                fontWeight = FontWeight(450),
                color = Color.Gray,
                fontFamily = FontFamily.Serif,
            )
            TextField(
                value = emailText,
                onValueChange = { emailText = it },
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
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.height(300.dp))
            OutlinedButton(
                onClick = {
                    val email = emailText
                    if (email.isEmpty()) {
                        Toast.makeText(context, "Please enter email address!", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.blue_100)),
                border = BorderStroke(0.5.dp, color = colorResource(id = R.color.blue_100)),
                shape = RoundedCornerShape(7.dp)
            ) {
                Text(
                    text = "Reset",
                    fontWeight = FontWeight(600),
                    fontSize = 17.sp
                )
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun ForgotPreview() {
    ForgotPass(null)
}