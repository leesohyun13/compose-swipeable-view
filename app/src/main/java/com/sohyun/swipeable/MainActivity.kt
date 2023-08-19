package com.sohyun.swipeable

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sohyun.swipeable.ui.theme.LayoutSampleTheme
import com.sohyun.core.SoSwipeableView
import com.sohyun.core.SwipeDirection

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen() {
        Scaffold(topBar = {
            TopAppBar(
                title = { Text("Swipeable View List", fontWeight = FontWeight.Bold, fontSize = 30.sp) },
            )
        }) {
            Column(modifier = Modifier
                .fillMaxHeight()
                .padding(it)
            ) {
                Text("Left Swipeable View", modifier = Modifier.padding(4.dp), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                LeftSwipeableButton()

                Spacer(modifier = Modifier.fillMaxWidth().height(24.dp))

                Text("Right Swipeable View", modifier = Modifier.padding(4.dp), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                RightSwipeableButton()

                Spacer(modifier = Modifier.fillMaxWidth().height(24.dp))

                Text("Both Swipeable View", modifier = Modifier.padding(4.dp), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                BothSwipeableButton()
            }
        }
    }

    @Composable
    fun LeftSwipeableButton() {
        SoSwipeableView(
            modifier = Modifier.height(56.dp),
            swipeDirection = SwipeDirection.LEFT,
            leftDismissDistance = CANCEL_BUTTON_SIZE,
            content = {
                ContentButton("Left Swipeable Button")
            },
            background = {
                CancelButton()
            }
        )
    }

    @Composable
    fun RightSwipeableButton() {
        SoSwipeableView(
            modifier = Modifier.height(56.dp),
            swipeDirection = SwipeDirection.RIGHT,
            rightDismissDistance = CANCEL_BUTTON_SIZE,
            content = {
                ContentButton("Right Swipeable Button")
            },
            background = {
                CancelButton(modifier = Modifier.align(Alignment.TopEnd))
            }
        )
    }

    @Composable
    fun BothSwipeableButton() {
        SoSwipeableView(
            modifier = Modifier.height(56.dp),
            swipeDirection = SwipeDirection.BOTH,
            rightDismissDistance = CANCEL_BUTTON_SIZE,
            leftDismissDistance = CANCEL_BUTTON_SIZE,
            content = {
                ContentButton("Both Swipeable Button")
            },
            background = {
                CancelButton()
                CancelButton(modifier = Modifier.align(Alignment.TopEnd))
            }
        )
    }
    
    @Composable
    fun ContentButton(content: String) {
        Row(modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
            .padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                Icons.Filled.Favorite,
                contentDescription = "Favorite",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(content)
        }
    }

    @Composable
    fun CancelButton(modifier: Modifier = Modifier) {
        Box(
            modifier
                .width(CANCEL_BUTTON_SIZE)
                .fillMaxHeight()
                .background(Color.Red)
                .clickable {
                    Toast
                        .makeText(this, "clicked button", Toast.LENGTH_LONG)
                        .show()
                },
            contentAlignment = Alignment.Center
        ) {
            Text("Remove", color = Color.White)
        }
    }

    companion object {
        val CANCEL_BUTTON_SIZE = 80.dp
    }
}
