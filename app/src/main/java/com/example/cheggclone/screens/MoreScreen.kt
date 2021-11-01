package com.example.cheggclone.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cheggclone.ui.theme.DeepOrange
import com.example.cheggclone.ui.theme.LightOrange

@Composable
fun MoreScreen(navController: NavHostController) {
    // Push notifications 상태 변수 저장
    // default는 true(switch on)
    val (notification, unNotification) = remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .padding(
                        top = 8.dp,
                        bottom = 4.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
            ) {
                Text(
                    text = "CheggPrep",
                    style = MaterialTheme.typography.h5,
                    color = DeepOrange,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) {
        Column() {
            val notClickableModifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp)

            AccountSection(
                name = "GDSC",
                signOut = {  },
                modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)
            )
            Divider()
            Row(
                modifier = notClickableModifier, // 앞서 선언한 custom modifier 적용
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MoreItem(
                    icon = Icons.Outlined.Notifications,
                    iconDesc = "notification",
                    text = "Push notifications"
                )
                Switch(
                    checked = notification, // default: true
                    onCheckedChange = unNotification,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = DeepOrange,
                        checkedTrackColor = LightOrange
                    )
                )
            }
            Divider()
            Row(modifier = Modifier.moreModifier { }) {
                MoreItem(
                    icon = Icons.Outlined.Feedback,
                    iconDesc = "give feedback",
                    text = "Give feedback",
                )
            }
            Divider()
            Row(modifier = notClickableModifier) {
                Text(
                    text = "Other Chegg services",
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier.moreModifier { }, // 아래에서 선언한 extended 함수 적용
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MoreItem(
                    icon = Icons.Outlined.Biotech,
                    iconDesc = "Chegg Study",
                    text = "Chegg Study"
                )
                Icon(
                    imageVector = Icons.Outlined.FileDownload,
                    contentDescription = "download",
                    tint = MaterialTheme.colors.secondaryVariant
                )
            }
            Row(
                modifier = Modifier.moreModifier { },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MoreItem(
                    icon = Icons.Outlined.Calculate,
                    iconDesc = "Chegg Math",
                    text = "Chegg Math"
                )
                Icon(
                    imageVector = Icons.Outlined.FileDownload,
                    contentDescription = "download",
                    tint = MaterialTheme.colors.secondaryVariant
                )
            }
            Divider()
            Row(modifier = Modifier.moreModifier { }) {
                MoreItem(
                    icon = Icons.Outlined.HelpOutline,
                    iconDesc = "help",
                    text = "Help"
                )
            }
            Row(modifier = Modifier.moreModifier { }) {
                MoreItem(
                    icon = Icons.Outlined.Info,
                    iconDesc = "info",
                    text = "About the app"
                )
            }
        }
    }
}

// 프로필 이미지, 이메일이 보여지는 section
// 누가 로그인을 하냐에 따라 달라지므로 인자를 받아서 사용
@Composable
fun AccountSection(
    name: String,
    signOut: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Outlined.AccountCircle,
            contentDescription = "account img",
            Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column() {
            Text(
                text = "Hello ${name}@gmail.com",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold
            )
            Text(
                "Sign out",
                color = MaterialTheme.colors.secondaryVariant,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable(onClick = signOut)
            )
        }
    }
}

// extended 함수
fun Modifier.moreModifier(onClick: () -> Unit) = this
    .fillMaxWidth()
    .clickable(onClick = onClick)
    .padding(horizontal = 8.dp, vertical = 12.dp)


// 요소들의 layout
@Composable
fun MoreItem(
    icon: ImageVector,
    iconDesc: String,
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = iconDesc)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontWeight = FontWeight.Bold)
    }
}
