package com.example.compose_search_appbar

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_search_appbar.ui.theme.ComposesearchappbarTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposesearchappbarTheme {
                // A surface container using the 'background' color from the theme
                val focusManager = LocalFocusManager.current
                Scaffold(topBar = { SearchAppBar() }
                   ) {
                    Column(modifier = Modifier
                        .padding(it)
                        .addFocusCleaner(focusManager = focusManager)
                        .background(Color.Cyan)
                        .height(300.dp)
                        .fillMaxWidth()) {
                    }
            }
        }
    }
}
    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun SearchAppBar() {
        val focusManager = LocalFocusManager.current
        val context = LocalContext.current
        val focusRequester by remember { mutableStateOf(FocusRequester()) }
        var text by remember { mutableStateOf("") }
        var showTextField by remember { mutableStateOf(false) }
        TopAppBar(
            modifier = Modifier.addFocusCleaner(focusManager = focusManager),
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Blue),
            actions = {
                IconButton(onClick = {
                    if (showTextField && text.isNotBlank() && text.isNotEmpty()) {
                        Toast.makeText(context, "search start", Toast.LENGTH_SHORT).show()
                        text = ""
                    }
                    showTextField = showTextField.not()
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                    )
                }
            },
            title = {
                Row() {
                    AnimatedContent(
                        targetState = showTextField,
                        transitionSpec = {
                            if ( targetState > initialState ){
                                ContentTransform(
                                    targetContentEnter = slideInHorizontally { height -> height } + fadeIn(),
                                    initialContentExit = slideOutHorizontally { height -> -height } + fadeOut()
                                )
                            } else {
                                ContentTransform(
                                    targetContentEnter = slideInHorizontally { height -> -height } + fadeIn(),
                                    initialContentExit = slideOutHorizontally { height -> height } + fadeOut()
                                )
                            }
                        }
                    ) {isSearch ->
                        if (isSearch) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(.7f)
                                    .fillMaxHeight(.05f)
                                    .border(
                                        border = BorderStroke(1.dp, Color.LightGray),
                                        shape = RoundedCornerShape(24.dp)
                                    )
                                    .clip(shape = RoundedCornerShape(24.dp))
                                    .background(Color.LightGray)
                                ,

                                ) {
                                SearchTextField(
                                    modifier = Modifier.weight(1f).align(CenterVertically),
                                    query = text,
                                    focusRequester = focusRequester,
                                    onQueryChange = {
                                        text = it
                                    },
                                    onQueryDone = {
                                        if (showTextField && text.isNotBlank() && text.isNotEmpty()) {
                                            Toast.makeText(context, "search start", Toast.LENGTH_SHORT).show()
                                            text = ""
                                        }
                                        showTextField = showTextField.not()
                                    }
                                )
                                IconButton(onClick = { text = "" }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Close Icon",
                                    )
                                }
                            }
                        } else {
                            Text(text = "text")
                        }
                    }
                }

            })

    }
}

@Composable
private fun SearchTextField(
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    onQueryChange: (String) -> Unit,
    onQueryDone: (KeyboardActionScope.() -> Unit)?,
    query: String
    ) {
    BasicTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .padding(start = 10.dp)
            .focusRequester(focusRequester = focusRequester),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = onQueryDone)
    )
}

fun Modifier.addFocusCleaner(focusManager: FocusManager, doOnClear: () -> Unit = {}): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            doOnClear()
            focusManager.clearFocus()
        })
    }
}