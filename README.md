# compose-search-appbar
Jetpack Compose Search AppBar based on Material AppBar 

## Overview

compose-search-appbar is search Appbar with animation.

![녹화_2023_07_10_20_33_00_634](https://github.com/gogoadl/compose-search-appbar/assets/49335446/b822d772-ffae-41cd-8d31-1c88a5d2000a)

## Usage

A simple usage example

```
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposesearchappbarTheme {
                val focusManager = LocalFocusManager.current
                val title = "title of your app"
                var query by remember { mutableStateOf("") }
                var targetState by remember { mutableStateOf(false) }
                val context = LocalContext.current
                Scaffold(topBar = {
                    SearchAppBar(
                        modifier = Modifier,
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
                    Column(
                        modifier = Modifier
                            .padding(it)
                            .addFocusCleaner(focusManager = focusManager)
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Text(text = "Search App Bar")
                        }
                    }
                }
            }
        }
    }
}
```

