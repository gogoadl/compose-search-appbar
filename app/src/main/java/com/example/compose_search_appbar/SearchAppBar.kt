package com.example.compose_search_appbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    title: String = "",
    modifier: Modifier = Modifier,
    placeholder: String = "",
    onSearch: () -> Unit,
    onSearchTextChanged: () -> Unit,
    onClickSearchIcon: () -> Unit
) {

    var showTextField by remember { mutableStateOf(false) }
    Row(modifier = modifier
        .fillMaxWidth()
        .background(color = Color.White)
        .height(75.dp)
        .padding(10.dp),) {
        Row(
            Modifier
                .weight(1f)
                .height(75.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedVisibility(
                visible = !showTextField,
                enter = slideInHorizontally(animationSpec = tween(500)),
                exit = slideOutHorizontally(animationSpec = tween(500))
            ) {
                Text(modifier = Modifier.fillMaxSize(), text = "title of your app", color = Color.Black)
            }

            AnimatedVisibility(
                visible = showTextField,
                enter = slideInHorizontally(animationSpec = tween(500)),
                exit = slideOutHorizontally(animationSpec = tween(500))
            ) {
//                TextField(modifier = Modifier.fillMaxSize(),value = "", onValueChange = {})
                OutlinedTextField(
                    value = "text",
                    onValueChange = {
                    },
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }
        IconButton(onClick = { showTextField = showTextField.not()}) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
            )
        }
    }

}

