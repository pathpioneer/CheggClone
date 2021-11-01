package com.example.cheggclone.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddBox
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.cheggclone.ui.theme.DeepOrange


/*
sealed class를 만들어서 그 안에 Screen 인스턴스 생성
왜 sealed class를 쓰는가? - https://stackoverflow.com/questions/69686087/why-use-sealed-class-and-make-object-in-navigation-kotlin-jetpack-compose
Singleton pattern - 인스턴스를 오직 한 개만 선언함
각 인스턴스마다 route(생성자 인자)를 받아 선언
Screen class는 route에 대한 정보를 담고 있음
*/
sealed class Screen(val route: String) {
    // 아래 네 개의 인스턴스는 Bottom Navigation
    object Home : Screen("home")
    object Search : Screen("search")
    object Create : Screen("create")
    object More : Screen("more")

    // HomeScreen의 DeckItem을 클릭 시 이동할 route의 뿌리(?)
    object Deck : Screen("deck")
}


/*
Bottom Navigation Bar를 만들기 위해 선언한 data class
BottomNavItem에는 따로 변수나 함수를 선언할 것이 없기 때문에 data class를 이용
route - 아이템을 눌렀을 때 이동할 스크린 경로
name - 아이콘과 함께 표시될 이름
icon - 이름과 함께 표시될 아이콘
*/
data class BottomNavItem(
    val route: String,
    val name: String,
    val icon: ImageVector
)


/*
BottomNavItem타입의 변수 4개를 BottomNav object 안에 listOf()로 선언
*/
object BottomNav {
    val items = listOf(
        BottomNavItem(Screen.Home.route, "Home", Icons.Outlined.Home),
        BottomNavItem(Screen.Search.route, "Search", Icons.Outlined.Search),
        BottomNavItem(Screen.Create.route, "Create", Icons.Outlined.AddBox),
        BottomNavItem(Screen.More.route, "More", Icons.Outlined.Menu)
    )
}


/*
Scaffold BottomBar로 이용될 컴포저블
navController를 활용해야 하기 때문에 인자로 전달받음
navController는 MainActivity.kt에서 가장 바깥 부분에 선언됨
따라서 아래의 컴포저블은 state hoisting을 통해서 올바르게 전달될 수 있음
*/
@Composable
fun BottomNavigationBar(navController: NavHostController) {

    // navController로부터 백스택의 상태를 가져와서 변수로 저장
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // 위 변수의 navBackStackEntry에서 route를 가져와 변수로 저장
    val currentRoute = navBackStackEntry?.destination?.route

    // 아래의 BottomNavigation과 BottomNavigationㅑtem은 사용자 정의 함수가 아님
    BottomNavigation(
        backgroundColor = Color.White,
        elevation = 4.dp,
        modifier = Modifier.padding(4.dp)
    ) {
        // 각각의 아이템들에 대해서 적용
        BottomNav.items.forEach { item ->
            BottomNavigationItem(
                selected = item.route == currentRoute, // 선택되었을 때 true 반환
                enabled = item.route != currentRoute, // 선택되지 않았을 때 false 반환
                onClick = { navController.navigate(item.route) }, // 클릭 시 item의 route로 이동
                icon = { Icon(imageVector = item.icon, contentDescription = item.name) },
                label = { Text(item.name) },
                selectedContentColor = DeepOrange, // 선택되었을 때의 color
                unselectedContentColor = Color.DarkGray
            )
        }
    }

}







