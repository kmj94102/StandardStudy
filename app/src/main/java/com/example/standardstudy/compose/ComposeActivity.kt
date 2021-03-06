package com.example.standardstudy.compose

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
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
import com.example.standardstudy.compose.ui.theme.StandardStudyTheme
import kotlin.random.Random

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StandardStudyTheme {
                Greeting("Android")
//                ContentView()
            }
        }

    }
}

// Ex 1)
@Composable
fun Greeting(name: String) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        val context = LocalContext.current
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "????????? ??????????????????") },
                    backgroundColor = Color(0x86F25287)
                )
            },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    Log.e(
                        "++++",
                        "${System.currentTimeMillis()} ?????????"
                    )
                    context.startActivity(Intent(context, ComposeEx5Activity::class.java))
                }) {
                    Text(text = "+")
                }
            }
        ) {
//            Text(text = "???????????????")
//            ColumnTest()
//            RowDummyBoxTest()
//            ColumnDummyBoxTest()
//            BoxDummyBoxTest()
//            BoxWithConstraintDummyBoxTest()
            TextContainer()
        }
    }

}

@Composable
fun ColumnTest() {
    // Linear Vertical ??? ??????
    Column(
        Modifier
            .background(Color(0xFF009688))
            .verticalScroll(rememberScrollState())
    ) {
        for (i in 0..40) {
            RowTest()
        }
    }
}

@Composable
fun RowTest() {
    Row(
        Modifier
            .padding(10.dp)
            .background(Color(0xffeaffd0))
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "MyComposableView",
            Modifier
                .padding(all = 10.dp)
                .background(Color.Yellow)
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "Row ??? ?????????")
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "????????? ?????????.")
    }
}

// LinearLayout horizontal ??? ??????
// arrangement : Row, Column ?????? ???????????? ???????????? ???????????? ????????? ?????????????????? ???????????? ???????????? ????????? ??? ??????
// Arrangement.SpaceBetween : ?????? ?????? ??????
// Arrangement.Start : ???????????? ?????? ??????
// Arrangement.End : ???????????? ????????? ??????
// Arrangement.Center : ???????????? ???????????? ??????
// Arrangement.SpaceAround : ??? ????????? ?????? ??????
// Arrangement.SpaceBetween : ????????? ????????? ????????????
// Arrangement.SpaceEvenly : ????????? ????????? ????????? ????????? ??????

// alignment ?????? ???????????? ?????? ???????????? ???????????? ????????? ????????? ???????????? ???????????? ?????? ??? ??????
// Alignment.Bottom : ??????????????? ????????? ??????
// Alignment.Top : ??????????????? ?????? ??????
// Alignment.CenterVertically : ??????????????? ?????????????????? ????????? ??????
// ????????? Row ???????????? ????????? align ??? ???????????? ?????? CenterVertically
@Composable
fun RowDummyBoxTest() {
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DummyBox()
        DummyBox()
        DummyBox()
    }
}

@Composable
fun ColumnDummyBoxTest() {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DummyBox()
        DummyBox()
        DummyBox()
    }
}

@Composable
fun BoxDummyBoxTest() {
    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        DummyBox(modifier = Modifier.size(200.dp), color = Color.Green)
        DummyBox(modifier = Modifier.size(150.dp), color = Color.Yellow)
        DummyBox(color = Color.Blue)
    }

}

@Composable
fun BoxWithConstraintDummyBoxTest() {
    BoxWithConstraints(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        if (this.minWidth > 400.dp) {
            DummyBox(modifier = Modifier.size(200.dp), color = Color.Green)
        } else {
            DummyBox(modifier = Modifier.size(200.dp), color = Color.Yellow)
        }

        Column {
            BoxWithConstraintItem(
                modifier = Modifier
                    .size(300.dp)
                    .background(Color.Green)
            )
            BoxWithConstraintItem(
                modifier = Modifier
                    .size(200.dp)
                    .background(Color.Yellow)
            )
        }
//        DummyBox(modifier = Modifier.size(200.dp), color = Color.Green)
//        DummyBox(modifier = Modifier.size(150.dp), color = Color.Yellow)
//        DummyBox(color = Color.Blue)
    }

}

@Composable
fun BoxWithConstraintItem(modifier: Modifier = Modifier) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        if (this.minWidth > 200.dp) {
            Text(text = "????????? ??? ????????????.")
        } else {
            Text(text = "????????? ?????? ????????????.")
        }
    }
}

@Composable
fun DummyBox(modifier: Modifier = Modifier, color: Color? = null) {
    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)

    val resultColor = color ?: Color(red, green, blue)

    val context = LocalContext.current
    Box(
        modifier = modifier
            .size(100.dp)
            .background(resultColor)
            .clickable {
                context.startActivity(
                    Intent(context, ComposeEx2Activity::class.java)
                )
            }
    ) {}

}

@Composable
fun TextContainer() {
    val name = "??????"
    val scrollState = rememberScrollState()

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "??????~~~ ???????????? $name ?????????.",
            style = TextStyle(textAlign = TextAlign.Center),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        )
        Text(
            text = "??????~~~ ???????????? $name ?????????.",
            style = TextStyle(textAlign = TextAlign.End),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        )
        Text(
            text = "??????~~~ ???????????? $name ?????????.",
            style = TextStyle(textAlign = TextAlign.Start),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        )
        Text(
            text = stringResource(id = R.string.dummy_short_text),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                textAlign = TextAlign.Start,
                textDecoration = TextDecoration.combine(
                    listOf(
                        TextDecoration.Underline,
                        TextDecoration.LineThrough
                    )
                )
            ),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 22.sp,
            fontFamily = FontFamily(Font(R.font.nanum_gothic_bold)),
            lineHeight = 45.sp,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Yellow)
        )
        Text(text = buildAnnotatedString {
            append("????????????????")
            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            ) {
                append("????????? ?????????.")
            }
            append("\n??? ??????????????????~")
        })

        val context = LocalContext.current
        ClickableText(text = AnnotatedString("?????????"), onClick = {
            Toast.makeText(context, "????????? ?????????", Toast.LENGTH_SHORT).show()
        })

        val word = stringResource(id = R.string.dummy_long_text)
        val wordList = word.split(" ")

        Text(text = buildAnnotatedString {
            wordList.forEach {
                if (it.contains("???")) {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Blue,
                            fontWeight = FontWeight.ExtraBold
                        )
                    ) {
                        append(it)
                    }
                } else {
                    append(it)
                }
            }
        })
    }
}

// Ex 2)
@Composable
fun ContentView() {
    Surface(color = MaterialTheme.colors.background) {
        Scaffold(backgroundColor = Color.White, topBar = { MyAppBar() }) {
            RandomUserListView(randomUsers = DummyDataProvider.userList)
        }
    }
}

@Composable
fun MyAppBar() {
    TopAppBar(elevation = 10.dp, backgroundColor = Color(0xFF673AB7)) {
        Text(
            text = stringResource(id = R.string.list), modifier = Modifier
                .padding(8.dp)
                .align(
                    Alignment.CenterVertically
                ),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@Composable
fun RandomUserListView(randomUsers: List<RandomUser>) {
    // ????????? ????????? ????????? Column
    LazyColumn {
        items(randomUsers) { user ->
            RandomUserView(randomUser = user)
        }
    }
}

@Composable
fun RandomUserView(randomUser: RandomUser) {
    val typography = MaterialTheme.typography
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        elevation = 10.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
//            Box(
//                modifier = Modifier
//                    .size(width = 100.dp, height = 100.dp)
//                    .clip(CircleShape)
//                    .background(
//                        Color(
//                            0xFFFFEB3B
//                        )
//                    )
//            )
            ProfileImg(imageUrl = randomUser.profileImage)
            Column() {
                Text(text = randomUser.name, style = typography.subtitle1)
                Text(text = randomUser.description, style = typography.body1)
            }
        }
    }
}

@Composable
fun ProfileImg(imageUrl: String, modifier: Modifier = Modifier) {
    // ????????? ?????????
    val bitmap: MutableState<Bitmap?> = mutableStateOf(null)
    val imageModifier = modifier
        .size(width = 50.dp, height = 50.dp)
        .clip(CircleShape)
//        .clip(RoundedCornerShape(10.dp))

    Glide.with(LocalContext.current)
        .asBitmap()
        .load(imageUrl)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmap.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })

    bitmap.value?.asImageBitmap()?.let {
        Image(
            bitmap = it,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = imageModifier
        )
    } ?: Image(
        painter = painterResource(id = R.drawable.ic_empty_user_img),
        contentDescription = null,
        contentScale = ContentScale.Fit,
        modifier = imageModifier
    )

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StandardStudyTheme {
        Greeting("Android")
//        ContentView()
    }
}