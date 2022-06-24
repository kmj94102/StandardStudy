package com.example.standardstudy.compose

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.standardstudy.R
import com.example.standardstudy.compose.ui.theme.StandardStudyTheme
import kotlin.math.cos
import kotlin.math.sin

class ComposeEx2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StandardStudyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Container()
                }
            }
        }
    }
}

@Composable
fun Container() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
//        ShapeContainer()
//        ButtonContainer()
        CheckboxContainer()
    }
}

@Composable
fun ShapeContainer() {
    var polySides by remember {
        mutableStateOf(3)
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DummyBox(modifier = Modifier.clip(RectangleShape))
        DummyBox(modifier = Modifier.clip(CircleShape))
        DummyBox(modifier = Modifier.clip(RoundedCornerShape(10.dp)))
        DummyBox(modifier = Modifier.clip(CutCornerShape(10.dp)))
        DummyBox(modifier = Modifier.clip(TriangleShape()))
        DummyBox(modifier = Modifier.clip(PolyShape(sides = polySides, radius = 70f)))
        Text(text = "polySides: $polySides")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                polySides++
            }) {
                Text(text = "polySize + 1")
            }

            Button(onClick = {
                polySides = 3
            }) {
                Text(text = "초기화")
            }
        }
    }
}

class TriangleShape() : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            moveTo(size.width / 2f, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}

class PolyShape(private val sides: Int, private val radius: Float) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(path = Path().also {
            it.polygon(sides, radius, size.center)
        })
    }
}

fun Path.polygon(sides: Int, radius: Float, center: Offset) {
    val angle = 2.0 * Math.PI / sides
    moveTo(
        x = center.x + (radius * cos(0.0)).toFloat(),
        y = center.y + (radius * sin(0.0)).toFloat()
    )
    for (i in 1 until sides) {
        lineTo(
            x = center.x + (radius * cos(angle * i)).toFloat(),
            y = center.y + (radius * sin(angle * i)).toFloat()
        )
    }
    close()
}

@Composable
fun ButtonContainer() {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val context = LocalContext.current

        Button(
            onClick = { context.makeToast("버튼이 눌렸습니당") },
            enabled = true,
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            )
        ) {
            Text(text = "버튼 1")
        }

        Button(
            onClick = { context.makeToast("버튼이 눌렸습니당") },
            enabled = true,
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            shape = CircleShape
        ) {
            Text(text = "버튼 2")
        }

        Button(
            onClick = { context.makeToast("버튼이 눌렸습니당") },
            enabled = true,
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, Color.Cyan),
        ) {
            Text(text = "버튼 3")
        }

        val buttonBorderGradient = Brush.horizontalGradient(listOf(Color.Yellow, Color.Red))

        Button(
            onClick = { context.makeToast("버튼이 눌렸습니당") },
            enabled = true,
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black,
                disabledBackgroundColor = Color.LightGray
            ),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, buttonBorderGradient),
            contentPadding = PaddingValues(40.dp)
        ) {
            Text(text = "버튼 4", color = Color.White)
        }

        val interactionSource = remember {
            MutableInteractionSource()
        }

        val isPressed by interactionSource.collectIsPressedAsState()

        val interactionSource2 = remember {
            MutableInteractionSource()
        }

        val isPressed2 by interactionSource2.collectIsPressedAsState()

        val pressStatusTitle = if (isPressed) "버튼을 누르고 있다" else "버튼에서 손을 땠다"

        Button(
            onClick = { },
            enabled = true,
            elevation = ButtonDefaults.elevation(
                defaultElevation = 10.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black,
                disabledBackgroundColor = Color.LightGray
            ),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, buttonBorderGradient),
            contentPadding = PaddingValues(40.dp),
            interactionSource = interactionSource
        ) {
            Text(text = "버튼 5", color = Color.White)
        }

        Text(text = pressStatusTitle)

        val pressedRadiusWithAnimation: Dp by animateDpAsState(targetValue = if (isPressed2) 0.dp else 20.dp)
        val pressedRadiusWithAnimation2: Dp by animateDpAsState(targetValue = if (isPressed2) 0.dp else 30.dp)

        Button(
            onClick = { },
            enabled = true,
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp,
                disabledElevation = 0.dp
            ),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black,
                disabledBackgroundColor = Color.LightGray
            ),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(2.dp, buttonBorderGradient),
            contentPadding = PaddingValues(40.dp),
            interactionSource = interactionSource2,
            modifier = Modifier.drawColoredShadow(
                Color.Red,
                alpha = 0.5f,
                borderRadius = 10.dp,
                shadowRadius = pressedRadiusWithAnimation,
                offsetY = pressedRadiusWithAnimation2,
                offsetX = 0.dp
            )
        ) {
            Text(text = "버튼 5", color = Color.White)
        }

    }
}

fun Modifier.drawColoredShadow(
    color: Color,
    alpha: Float = 0.2f,
    borderRadius: Dp = 0.dp,
    shadowRadius: Dp = 20.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = this.drawBehind {
    val transparentColor = android.graphics.Color.toArgb(color.copy(alpha = 0.0f).value.toLong())
    val shadowColor = android.graphics.Color.toArgb(color.copy(alpha = alpha).value.toLong())
    this.drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            borderRadius.toPx(),
            borderRadius.toPx(),
            paint
        )
    }
}

fun Context.makeToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

@Composable
fun CheckboxContainer() {
    val checkedStatusForFirst = remember {
        mutableStateOf(false)
    }
    val checkedStatusForSecond = remember {
        mutableStateOf(false)
    }
    val checkedStatusForThird = remember {
        mutableStateOf(false)
    }

    val checkedStateArray =
        listOf(checkedStatusForFirst, checkedStatusForSecond, checkedStatusForThird)
    val allBoxChecked: (Boolean) -> Unit = { isAllChecked ->
        checkedStateArray.forEach { it.value = isAllChecked }
    }
//    var checkedStatusForSecond by remember {
//        mutableStateOf(false)
//    }
//    val (checkedStatusForThird, setCheckedStatusForThird) = remember {
//        mutableStateOf(false)
//    }
    val checkedStatusForForth = checkedStateArray.all { it.value }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CheckBoxWithTitle("1번 확인사항", checkedStatusForFirst)
        CheckBoxWithTitle("2번 확인사항", checkedStatusForSecond)
        CheckBoxWithTitle("3번 확인사항", checkedStatusForThird)
        Spacer(modifier = Modifier.height(20.dp))
        AllAgreeCheckBox("모두 동의하십니까?", checkedStatusForForth, allBoxChecked)
//        Checkbox(checked = checkedStatusForFirst.value, onCheckedChange = {
//            checkedStatusForFirst.value = it
//        })
//        Checkbox(checked = checkedStatusForSecond, onCheckedChange = {
//            checkedStatusForSecond = it
//        })
//        Spacer(modifier = Modifier.height(30.dp))
//        Checkbox(
//            enabled = true,
//            checked = checkedStatusForThird,
//            colors = CheckboxDefaults.colors(
//                checkedColor = Color.Red,
//                uncheckedColor = Color.Blue,
//                checkmarkColor = Color.Yellow,
//                disabledColor = Color.Gray
//            ),
//            onCheckedChange = {
//                setCheckedStatusForThird.invoke(it)
//            })
    }
}

@Composable
fun CheckBoxWithTitle(title: String, isCheckedState: MutableState<Boolean>) {
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            enabled = true,
            checked = isCheckedState.value,
            onCheckedChange = {
                isCheckedState.value = it
            })
        Text(text = title)

    }
}

@Composable
fun AllAgreeCheckBox(
    title: String,
    isCheckedState: Boolean,
    allBoxChecked: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

//        Checkbox(
//            enabled = true,
//            checked = isCheckedState,
//            colors = CheckboxDefaults.colors(
//                checkedColor = Color.Red,
//                uncheckedColor = Color.Blue,
//                checkmarkColor = Color.Yellow,
//                disabledColor = Color.Gray
//            ),
//            onCheckedChange = {
////                isCheckedState.value = item
//                allBoxChecked(it)
//            })
        MyCheckBox(isCheckedState, allBoxChecked)
        Text(text = title)

    }
}

@Composable
fun MyCheckBox(isCheckedState: Boolean, allBoxChecked: (Boolean) -> Unit) {
    val togglePainter =
        if (isCheckedState) R.drawable.ic_battery_charge else R.drawable.ic_battery_row

    Image(
        painter = painterResource(id = togglePainter),
        contentDescription = null,
        modifier = Modifier
            .padding(start = 20.dp)
            .clickable(indication = null, interactionSource = remember {
                MutableInteractionSource()
            }) {
                allBoxChecked(isCheckedState.not())
            })
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    StandardStudyTheme {
        Container()
    }
}