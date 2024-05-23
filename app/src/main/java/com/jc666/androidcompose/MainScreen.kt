package com.jc666.androidcompose

import android.util.Log
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jc666.androidcompose.ui.theme.AndroidComposeTheme
import androidx.compose.runtime.saveable.rememberSaveable

@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingPreview() {
    AndroidComposeTheme {
        Greetings()
    }
}

@Preview
@Composable
fun MyAppPreview() {
    AndroidComposeTheme {
        MyApp(Modifier.fillMaxSize())
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    Log.d("MyApp", "MyApp")
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
//    var shouldShowOnboarding by remember { mutableStateOf(true) }

    Surface(modifier) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Greetings()
        }
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
//    Column(modifier = modifier.padding(vertical = 4.dp)) {
//        for (name in names) {
//            Greeting(name = name)
////            Greeting2(name = name)
////            Greeting3(name = name)
//        }
//    }
}

/**
 * 在這個情況下，Surface 瞭解如果背景設定為 primary 顏色，那上面的文字就應該使用 onPrimary 顏色，主題裡也有這個設定。
 */
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    var expanded by rememberSaveable { mutableStateOf(false) }
//    val expanded = remember { mutableStateOf(false) }

//    val extraPadding = if (expanded) 48.dp else 0.dp
//    val extraPadding by animateDpAsState(if (expanded) 48.dp else 0.dp, label = "")
    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = ""
    )

    Surface(color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp)) //不能為負值，要不然會發生crashed
            ) {
                Text(text = "Hello ")
                Text(text = name)
            }
            ElevatedButton(
                onClick = { expanded = !expanded }
            ) {
                Text(if (expanded) "Show less" else "Show more")
            }
        }
    }
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Surface(color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
            Column(modifier = Modifier.weight(2f)) {
                Text(text = "Hello ")
                Text(text = name)
            }
            ElevatedButton(
                modifier = Modifier.weight(1f),
                onClick = {  }
            ) {
                Text("Show more")
            }
        }
    }
}

@Composable
fun Greeting3(name: String, modifier: Modifier = Modifier) {
    Surface(color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp)) {
        Column(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
            Text(text = "Hello ")
            Text(text = name)
        }
    }
}