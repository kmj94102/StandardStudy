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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.standardstudy.R
import com.example.standardstudy.compose.ui.theme.StandardStudyTheme
import com.example.standardstudy.compose.util.DummyDataProvider
import com.example.standardstudy.compose.util.RandomUser
import kotlin.random.Random

class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            StandardStudyTheme {
//                    Greeting("Android")
                ContentView()
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
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "컴포즈 테스트입니다") },
                    backgroundColor = Color(0x86F25287)
                )
            },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    Log.e(
                        "++++",
                        "${System.currentTimeMillis()} 눌렸다"
                    )
                }) {
                    Text(text = "+")
                }
            }
        ) {
//            Text(text = "안녕하세요")
//            ColumnTest()
//            RowBoxTest()
            ColumnBoxTest()
        }
    }

}

@Composable
fun ColumnTest() {
    // Linear Vertical 과 유사
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
        Text(text = "Row 로 작성된")
        Spacer(modifier = Modifier.size(10.dp))
        Text(text = "텍스트 입니다.")
    }
}

// LinearLayout horizontal 과 유사
// arrangement : Row, Column 같은 요소들이 들어가는 컨테이너 성격의 콤포저블에서 요소들의 아이템을 정렬할 때 사용
// Arrangement.SpaceBetween : 공간 모두 차지
// Arrangement.Start : 요소들을 왼쪽 넣기
// Arrangement.End : 요소들을 오른쪽 넣기
// Arrangement.Center : 요소들을 가운데에 넣기
// Arrangement.SpaceAround : 빈 공간을 남겨 두기
// Arrangement.SpaceBetween : 사이에 공간을 밀어넣기
// Arrangement.SpaceEvenly : 요소들 사이에 공간을 똑같이 하기

// alignment 해당 컨테이너 안에 들어있는 요소들의 위치를 어떠한 방향으로 설정할지 정할 때 사용
// Alignment.Bottom : 컨테이너의 아래에 두기
// Alignment.Top : 컨테이너의 위에 두기
// Alignment.CenterVertically : 컨테이너의 수직방향으로 중앙에 두기
// 현재는 Row 콤퍼저블 안에서 align 이 들어가기 때문 CenterVertically
@Composable
fun RowBoxTest() {
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
fun ColumnBoxTest() {
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
fun DummyBox(modifier: Modifier = Modifier) {
    val red = Random.nextInt(256)
    val green = Random.nextInt(256)
    val blue = Random.nextInt(256)

    val color = Color(red, green, blue)

    Box(
        modifier = modifier
            .size(100.dp)
            .background(color)
    ) {}

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
    // 메모리 관리가 들어간 Column
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
    // 이미지 비트맵
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