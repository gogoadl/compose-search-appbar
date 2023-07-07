package com.example.compose_search_appbar

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
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
import androidx.compose.material3.TopAppBarColors
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
                val title = "title of your app"
                var query by remember { mutableStateOf("") }
                var targetState by remember { mutableStateOf(false) }
                val context = LocalContext.current
                Scaffold(topBar = {
                    SearchAppBar(
                        title = title,
                        query = query,
                        placeholder = "search anything!",
                        targetState = targetState,
                        onSearch = {
                            if (targetState && query.isNotBlank() && query.isNotEmpty()) {
                                Toast.makeText(context, "search start", Toast.LENGTH_SHORT).show()
                                query = ""
                            }
                            targetState = targetState.not()
                        },
                        onQueryChange = { query = it },
                        onQueryDone = {
                            if (targetState && query.isNotBlank() && query.isNotEmpty()) {
                                Toast.makeText(context, "search start", Toast.LENGTH_SHORT).show()
                                query = ""
                            }
                            targetState = targetState.not()
                        },
                        onClose = { query = "" }
                        )
                }
                   ) {
                    Column(modifier = Modifier
                        .padding(it)
                        .addFocusCleaner(focusManager = focusManager)) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Text(text = "Search App Bar")
                        }
                    }
            }
        }
    }
}
    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun SearchAppBar(
        modifier: Modifier = Modifier,
        title: String,
        query: String,
        placeholder: String,
        targetState: Boolean,
        onSearch: () -> Unit,
        onQueryChange: (String) -> Unit,
        onQueryDone: (KeyboardActionScope.() -> Unit)?,
        onClose: () -> Unit,
        backgroundColor: TopAppBarColors = TopAppBarDefaults.smallTopAppBarColors(),
        textFieldColor: Color = textFieldBackgroundColor
    ) {
        val focusManager = LocalFocusManager.current
        val focusRequester by remember { mutableStateOf(FocusRequester()) }
        TopAppBar(
            modifier = modifier.addFocusCleaner(focusManager = focusManager),
            colors = backgroundColor,
            actions = {
                IconButton(onClick = onSearch) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = searchIconContentDescription,
                    )
                }
            },
            title = {
                SearchAppBarTitle(
                    targetState = targetState,
                    title = title,
                    query = query,
                    focusRequester = focusRequester,
                    onQueryChange = onQueryChange,
                    onQueryDone = onQueryDone,
                    onClose = onClose,
                    textFieldColor = textFieldColor
                )
            })

    }
}
const val textFieldWidthWeight = 0.7f
const val textFieldHeightWeight = 0.05f

const val searchIconContentDescription = "Search Icon"
const val closeIconContentDescription = "Close Icon"

val textFieldCornerShape = 24.dp
val textFieldBackgroundColor = Color.LightGray

val textFieldPaddingStart = 10.dp

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
            .padding(start = textFieldPaddingStart)
            .focusRequester(focusRequester = focusRequester),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = onQueryDone)
    )
}
@Composable
private fun SearchTextFieldWrapper(
    title: String,
    isSearch: Boolean,
    query: String,
    focusRequester: FocusRequester,
    onQueryChange: (String) -> Unit,
    onQueryDone: (KeyboardActionScope.() -> Unit)?,
    onClose: () -> Unit,
    textFieldColor: Color
) {
    if (isSearch) {
        Row(
            modifier = Modifier
                .fillMaxWidth(textFieldWidthWeight)
                .fillMaxHeight(textFieldHeightWeight)
                .border(
                    border = BorderStroke(1.dp, textFieldColor),
                    shape = RoundedCornerShape(textFieldCornerShape)
                )
                .clip(shape = RoundedCornerShape(textFieldCornerShape))
                .background(textFieldColor)
        ) {
            SearchTextField(
                modifier = Modifier
                    .weight(1f)
                    .align(CenterVertically),
                query = query,
                focusRequester = focusRequester,
                onQueryChange = onQueryChange,
                onQueryDone = onQueryDone
            )
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = closeIconContentDescription,
                )
            }
        }
    } else {
        Text(text = title)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun SearchAppBarTitle(
    targetState: Boolean,
    title: String,
    query: String,
    focusRequester: FocusRequester,
    onQueryChange: (String) -> Unit,
    onQueryDone: (KeyboardActionScope.() -> Unit)?,
    onClose: () -> Unit,
    textFieldColor: Color
    ) {
    Row() {
        AnimatedContent(
            targetState = targetState,
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
            SearchTextFieldWrapper(
                title = title,
                query = query,
                isSearch = isSearch,
                focusRequester = focusRequester,
                onQueryChange = onQueryChange,
                onQueryDone = onQueryDone,
                onClose = onClose,
                textFieldColor = textFieldColor
            )
        }
    }
}
fun Modifier.addFocusCleaner(focusManager: FocusManager, doOnClear: () -> Unit = {}): Modifier {
    return this.pointerInput(Unit) {
        detectTapGestures(onTap = {
            doOnClear()
            focusManager.clearFocus()
        })
    }
}