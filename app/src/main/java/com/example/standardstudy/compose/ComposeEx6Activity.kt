package com.example.standardstudy.compose

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.standardstudy.R
import com.example.standardstudy.compose.util.ui.theme.StandardStudyTheme
import com.skydoves.landscapist.glide.GlideImage

class ComposeEx6Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StandardStudyTheme {
                Ex6Container()
            }
        }
    }
}

@Composable
fun Ex6Container() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
//        ColumnContainer()
//        RowContainer()
//        BoxContainer()
//        Ex6TextContainer()
//        ImageContainer()
        LazyColumnContainer()
    }
}


@Composable
fun ColumnContainer() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ColorBox()
        ColorBox(color = Color(0xFF5C6BC0))
        ColorBox(color = Color(0xFF26A69A))
    }
}

@Composable
fun RowContainer() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        ColorBox()
        ColorBox(color = Color(0xFF5C6BC0), modifier = Modifier.align(Alignment.Top))
        ColorBox(color = Color(0xFF26A69A))
    }
}

@Composable
fun BoxContainer() {
    Box(
        contentAlignment = Alignment.CenterStart,

        ) {
        ColorBox(modifier = Modifier.size(70.dp), color = Color(0xFF26A69A))
        ColorBox(
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.Center), color = Color(0xFF5C6BC0)
        )
        ColorBox()
    }
}

@Composable
fun ColorBox(modifier: Modifier = Modifier, color: Color? = null) {
    Box(
        modifier = modifier
            .size(30.dp)
            .background(color ?: Color(0xFFEF5350))
    )
}

@Composable
fun Ex6TextContainer() {

    Column {
        Text(text = "Hello Android")
        Text(
            text = stringResource(id = R.string.dummy_short_text),
            maxLines = 2,
            overflow = TextOverflow.Visible,
            color = Color.White,
            style = TextStyle(
                textAlign = TextAlign.Start,
                textDecoration = TextDecoration.combine(
                    listOf(
                        TextDecoration.Underline,
                        TextDecoration.LineThrough
                    )
                )
            ),
            letterSpacing = 5.sp,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 22.sp,
            fontFamily = FontFamily(Font(R.font.nanum_gothic_bold)),
            lineHeight = 40.sp,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
            onTextLayout = {
                Log.d("TAG", "${it.lineCount}")
            }
        )
    }

}

@Composable
fun ImageContainer() {
    Column {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "description",
        )
        Image(
            imageVector = Icons.Default.Edit,
            contentDescription = "description",
            modifier = Modifier
                .size(100.dp)
                .background(Color(0xFF7E57C2))
        )

        val state = loadPicture(url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/133.png")
        when (state.value) {
            is UiState.Loading -> {
                CircularProgressIndicator()
            }
            is UiState.Success -> {
                Image(bitmap = (state.value as UiState.Success).data.asImageBitmap(), contentDescription = null)
            }
        }
        GlideImage(
            imageModel = "https://user-images.githubusercontent.com/24237865/127760344-bb042fe8-23e1-4014-b208-b7b549d32086.png",
            loading = { CircularProgressIndicator() },
            failure = {
                Text(text = "이미지 로드에 실패하였습니다.")
            }
        )

    }
}

@Composable
fun loadPicture(url: String): MutableState<UiState> {
    val state : MutableState<UiState> = mutableStateOf(UiState.Loading)

    Glide.with(LocalContext.current)
        .asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                state.value = UiState.Success(resource)
            }
            override fun onLoadCleared(placeholder: Drawable?) {}
        })

    return state
}

sealed class UiState {
    object Loading : UiState()
    data class Success(val data: Bitmap) : UiState()
}

@Composable
fun LazyColumnContainer() {

    val list = (1..10).map { "${it}번째 아이템" }
    mutableStateOf(list)

    Column() {
        LazyRow(content = {
            items(list) {
                ListItem(it)
            }
        })

        LazyColumn(
            content = {
                items(list) { item ->
                    ListItem(item)
                }
            },
            verticalArrangement = Arrangement.spacedBy(10.dp),
        )

    }

}

@Composable
fun ListItem(name: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xFFFF7043))
                    .size(width = 50.dp, height = 50.dp)
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = name)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview6() {
    StandardStudyTheme {
        Container()
    }
}