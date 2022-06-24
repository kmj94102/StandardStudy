package com.example.standardstudy.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.standardstudy.compose.ui.theme.StandardStudyTheme

class ComposeEx5Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StandardStudyTheme {
                ContainerEx5()
            }
        }
    }
}

@Composable
fun ContainerEx5() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavigationGraph()
    }
}

// 네비게이션 라우트 이넘 (값을 가지는 이넘)
enum class NAV_ROUTE(val routeName: String, val description: String, val btnColor: Color) {
    MAIN("MAIN", "메인 화면", Color(0xFFF06292)),
    LOGIN("LOGIN", "로그인 화면", Color(0xFF7986CB)),
    REGISTER("REGISTER", "회원가입 화면", Color(0xFF4FC3F7)),
    USER_PROFILE("USER_PROFILE", "유저 프로필 화면", Color(0xFFFFF176)),
    SETTING("SETTING", "설정 화면", Color(0xFF81C784)),
}

// 네비게이션 라우트 액션
class RouteAction(navHostController: NavHostController){

    // 특정 라우트로 이동
    val navTo: (NAV_ROUTE) -> Unit = { route ->
        navHostController.navigate(route.routeName)
    }

    // 뒤로가기 이동
    val gotoBack: () -> Unit = {
        navHostController.navigateUp()
    }
}

@Composable
fun NavigationGraph(startRoute: NAV_ROUTE = NAV_ROUTE.MAIN) {
    // 네비게이션 컨트롤러
    val navController = rememberNavController()

    // 네비게이션 라우트 액션
    val routeAction = remember(navController) {
        RouteAction(navController)
    }

    // NavHost 로 네비게이션 결정
    // 네비게이션 역녈할 녀석들들 설정한다.
    NavHost(navController = navController, startDestination = startRoute.routeName) {
        // 라우트 이름 = 화면의 키
        composable(NAV_ROUTE.MAIN.routeName) {
            // 화면 = 값
            MainScreen(routeAction = routeAction)
        }
        composable(NAV_ROUTE.LOGIN.routeName) {
            SubScreen(routeAction = routeAction, NAV_ROUTE.LOGIN.description, NAV_ROUTE.LOGIN.btnColor)
        }
        composable(NAV_ROUTE.REGISTER.routeName) {
            SubScreen(routeAction = routeAction, NAV_ROUTE.REGISTER.description, NAV_ROUTE.REGISTER.btnColor)
        }
        composable(NAV_ROUTE.USER_PROFILE.routeName) {
            SubScreen(routeAction = routeAction, NAV_ROUTE.USER_PROFILE.description, NAV_ROUTE.USER_PROFILE.btnColor)
        }
        composable(NAV_ROUTE.SETTING.routeName) {
            SubScreen(routeAction = routeAction, NAV_ROUTE.SETTING.description, NAV_ROUTE.SETTING.btnColor)
        }
    }
}

// 메인 화면
@Composable
fun MainScreen(routeAction: RouteAction) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.padding(16.dp)) {
            NavButton(route = NAV_ROUTE.LOGIN, routeAction = routeAction)
            NavButton(route = NAV_ROUTE.REGISTER, routeAction = routeAction)
            NavButton(route = NAV_ROUTE.USER_PROFILE, routeAction = routeAction)
            NavButton(route = NAV_ROUTE.SETTING, routeAction = routeAction)
        }
    }
}

// 서브 화면
@Composable
fun SubScreen(routeAction: RouteAction, description: String, btnColor: Color) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = description, style = TextStyle(Color.Black, 22.sp, FontWeight.Medium))
                Button(
                    onClick = { routeAction.gotoBack.invoke() },
                    modifier = Modifier
                        .padding(16.dp)
                        .offset(y = 100.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = btnColor),
                ) {
                    Text(text = "뒤로가기")
                }
            }
        }
    }
}

// 콜롬에 있는 네비게이션 버튼
@Composable
fun ColumnScope.NavButton(route: NAV_ROUTE, routeAction: RouteAction) {
    Button(
        onClick = { routeAction.navTo(route) },
        colors = ButtonDefaults.buttonColors(containerColor = route.btnColor),
        modifier = Modifier
            .weight(1f)
            .padding(8.dp)
            .fillMaxSize()
    ) {
        Text(text = route.description, style = TextStyle(Color.White, 22.sp, FontWeight.Medium))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview5() {
    StandardStudyTheme {
        ContainerEx5()
    }
}