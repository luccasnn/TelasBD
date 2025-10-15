package com.example.myapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.screens.TwitchProfileScreen
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                TwitchApp()
            }
        }
    }
}

data class Categoria(
    val nome: String,
    val imagemRes: Int,
    val tipo: String,
    val espectadores: String
)

data class LiveStream(
    val streamerName: String,
    val streamerProfilePicRes: Int,
    val streamPreviewRes: Int,
    val streamTitle: String,
    val gameName: String,
    val viewerCount: String,
    val tags: List<String>
)

data class TwitchClip(
    val thumbnailUrlRes: Int,
    val duration: String,
    val views: String,
    val age: String,
    val creatorLogoUrlRes: Int,
    val title: String,
    val channelName: String,
    val clipperName: String
)

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search")
    object Activities : Screen("activities")
    object Profile : Screen("profile")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TwitchApp() {
    val navController = rememberNavController()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black
    ) {
        Scaffold(
            containerColor = Color.Black,
            bottomBar = { TwitchBottomBar(navController = navController) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screen.Home.route) {
                    TwitchHomeScreen(navController = navController)
                }
                composable(Screen.Search.route) {
                    TwitchSearchScreen(navController = navController)
                }
                composable(Screen.Activities.route) {
                    TwitchActivitiesScreen(navController = navController)
                }
                composable(Screen.Profile.route) {
                    TwitchProfileScreen(navController = navController)
                }
            }
        }
    }
}

@Composable
fun TwitchBottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(Color(0xFF111111))
            .padding(horizontal = 16.dp)
    ) {
        val icones = listOf("⌂", "🔍", "+", "🔔")
        val rotas = listOf(Screen.Home.route, Screen.Search.route, null, Screen.Activities.route)

        icones.forEachIndexed { index, icon ->
            val rota = rotas[index]
            val isSelected = rota == currentRoute
            Text(
                text = icon,
                fontSize = 28.sp,
                color = if (isSelected) Color(0xFF9147FF) else Color.White,
                modifier = if (rota != null) Modifier.clickable {
                    navController.navigate(rota) {
                        popUpTo(Screen.Home.route) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                } else Modifier
            )
        }
        Image(
            painter = painterResource(id = R.drawable.fotoperfil),
            contentDescription = "Foto do Perfil",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .border(width = 2.dp, color = if (currentRoute == Screen.Profile.route) Color(0xFF9147FF) else Color.Gray, shape = CircleShape)
                .clickable { navController.navigate(Screen.Profile.route) }
        )
    }
}



@Composable
fun HeaderTabs(selectedTab: String, onTabSelected: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color(0x33AAAAAA), shape = RoundedCornerShape(16.dp))
                .padding(vertical = 4.dp, horizontal = 12.dp)
        ) {
            Text("❤", fontSize = 16.sp)
            Text("Seguindo", fontSize = 12.sp, color = Color.White)
        }
        Spacer(modifier = Modifier.width(24.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { onTabSelected("Ao vivo") }
        ) {
            Text(
                "Ao vivo",
                fontSize = 18.sp,
                color = if (selectedTab == "Ao vivo") Color.White else Color.Gray,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(modifier = Modifier.height(3.dp).width(50.dp).background(if (selectedTab == "Ao vivo") Color(0xFF9147FF) else Color.Transparent))
        }
        Spacer(modifier = Modifier.width(24.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { onTabSelected("Clipes") }
        ) {
            Text(
                "Clipes",
                fontSize = 18.sp,
                color = if (selectedTab == "Clipes") Color.White else Color.Gray,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(modifier = Modifier.height(3.dp).width(50.dp).background(if (selectedTab == "Clipes") Color(0xFF9147FF) else Color.Transparent))
        }
    }
}

@Composable
fun StreamPreviewItem(stream: LiveStream) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Box(contentAlignment = Alignment.BottomStart) {
            Image(
                painter = painterResource(id = stream.streamPreviewRes),
                contentDescription = "Prévia da live de ${stream.streamerName}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(8.dp))
            )
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.Red, CircleShape)
                        .size(10.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("AO VIVO", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            Text(
                text = "${stream.viewerCount} espectadores",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .background(Color(0x99000000), RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(id = stream.streamerProfilePicRes),
                contentDescription = "Foto de perfil de ${stream.streamerName}",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(stream.streamerName, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(stream.streamTitle, color = Color.White, fontSize = 14.sp)
                Text(stream.gameName, color = Color.Gray, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    stream.tags.forEach { tag ->
                        Text(
                            text = tag,
                            color = Color.White,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .background(Color.DarkGray, RoundedCornerShape(10.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Mais opções",
                tint = Color.White
            )
        }
    }
}

@Composable
fun ClipPreviewItem(clip: TwitchClip) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Box {
            Image(
                painter = painterResource(id = clip.thumbnailUrlRes),
                contentDescription = "Thumbnail do clipe: ${clip.title}",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(8.dp))
            )
            Text(
                text = clip.duration,
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .background(Color(0x99000000), RoundedCornerShape(4.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp)
            ) {
                Text("${clip.views} visualizações", color = Color.White, fontWeight = FontWeight.Bold)
                Text(clip.age, color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Image(
                painter = painterResource(id = clip.creatorLogoUrlRes),
                contentDescription = "Logo de ${clip.channelName}",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(clip.title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(clip.channelName, color = Color.Gray, fontSize = 14.sp)
                Text("Clipe criado por ${clip.clipperName}", color = Color.Gray, fontSize = 14.sp)
            }
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Mais opções",
                tint = Color.White
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        TwitchApp()
    }
}

