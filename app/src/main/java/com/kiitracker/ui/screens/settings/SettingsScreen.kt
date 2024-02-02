package com.kiitracker.ui.screens.settings

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.kiitracker.BuildConfig
import com.kiitracker.Config
import com.kiitracker.data.local.Prefs
import com.kiitracker.domain.models.UserData
import com.kiitracker.ui.components.dialog.LogoutDialog
import com.kiitracker.ui.components.icons.Github
import com.kiitracker.ui.components.settings.Section
import com.kiitracker.ui.components.settings.SectionTitle
import com.kiitracker.ui.components.settings.SettingItem
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    user: UserData?,
    onNavigateBack: () -> Unit = {},
    onLogoutClicked: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Go Back",
                        modifier = Modifier
                            .padding(5.dp)
                            .clip(CircleShape)
                            .clickable { onNavigateBack() }
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant.copy(
                                    alpha = 0.4f
                                )
                            )
                            .padding(4.dp)
                    )
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                AccountSection(
                    user = user, onLogoutClicked = onLogoutClicked
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { Divider() }
            item { RoutineSection(user = user) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { Divider() }
            item { TroubleShootSection() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { Divider() }
            item { AboutSection() }
        }
    }
}

@Composable
fun AccountSection(
    user: UserData?,
    onLogoutClicked: () -> Unit = {}
) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    Section(
        title = "Account",
        modifier = Modifier.fillMaxWidth(),
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant.copy(
                            alpha = 0.2f
                        )
                    )
                    .clickable {
                        showLogoutDialog = true
                    }
                    .padding(10.dp),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    model = user?.photoUrl,
                    contentDescription = "${user?.username}'s avatar",
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = user?.username ?: "Unknown",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = user?.email ?: "Unknown",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.surfaceTint
                        )
                    )
                }
            }
        }
    )
    if (showLogoutDialog) {
        LogoutDialog(
            onDismiss = { showLogoutDialog = false },
            onConfirm = { onLogoutClicked() }
        )
    }
}

@Composable
fun RoutineSection(
    user: UserData?
) {
    var openSaturdayRoutineChooserDialog by remember { mutableStateOf(false) }
    val routineUrl = "${Config.SITE_URL}/dashboard?routine=${user?.uid}"
    val shareRoutineIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, Config.shareRoutineContent(routineUrl))
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(shareRoutineIntent, null)
    val ctx = LocalContext.current
    val handler = LocalUriHandler.current
    Section(
        title = "Routine",
        modifier = Modifier.fillMaxWidth(),
        content = {
            SettingItem(
                icon = Icons.Default.OpenInNew,
                title = "Edit Routine",
                description = "Edit your routine",
                onClick = {
                    handler.openUri("${Config.SITE_URL}/dashboard")
                },
                modifier = Modifier.clip(RoundedCornerShape(15.dp))
            )
            SettingItem(
                icon = Icons.Default.Share,
                title = "Share Routine",
                description = "Share your routine with others",
                onClick = {
                    ctx.startActivity(shareIntent)
                },
                modifier = Modifier.clip(RoundedCornerShape(15.dp))
            )
            SettingItem(
                icon = Icons.Default.Class,
                title = "Saturday Routine",
                description = "Set your Saturday routine",
                onClick = {
                    openSaturdayRoutineChooserDialog = true
                },
                modifier = Modifier.clip(RoundedCornerShape(15.dp))
            )
        }
    )
    if (openSaturdayRoutineChooserDialog) {
        SaturdayRoutineChooserDialog(
            onDismiss = { openSaturdayRoutineChooserDialog = false }
        )
    }
}

@Composable
fun TroubleShootSection() {
    val handler = LocalUriHandler.current
    Section(
        title = "Troubleshoot",
        modifier = Modifier.fillMaxWidth(),
        content = {
            SettingItem(
                icon = Icons.Default.Report,
                title = "Report a bug",
                description = "Report a bug or request a feature",
                onClick = {
                    handler.openUri("${Config.GITHUB_URL}/issues/new")
                },
                modifier = Modifier.clip(RoundedCornerShape(15.dp))
            )
            SettingItem(
                icon = Icons.Default.Mail,
                title = "Support",
                description = "Still facing issues? Contact us",
                onClick = {
                    handler.openUri("mailto:${Config.OWNER_MAIL}")
                },
                modifier = Modifier.clip(RoundedCornerShape(15.dp))
            )
        }
    )
}

@Composable
fun AboutSection() {
    val version = BuildConfig.VERSION_NAME
    val handler = LocalUriHandler.current
    Section(
        title = "About",
        modifier = Modifier.fillMaxWidth(),
        content = {
            SettingItem(
                icon = Icons.Default.PrivacyTip,
                title = "Privacy Policy",
                description = "Read our privacy policy",
                onClick = {
                    handler.openUri("${Config.SITE_URL}/privacy-policy")
                },
                modifier = Modifier.clip(RoundedCornerShape(15.dp))
            )
            SettingItem(
                icon = Icons.Default.Public,
                title = "Website",
                description = "Visit our website",
                onClick = {
                    handler.openUri(Config.SITE_URL)
                },
                modifier = Modifier.clip(RoundedCornerShape(15.dp))
            )
            SettingItem(
                icon = Github,
                title = "GitHub",
                description = "View the source code",
                onClick = {
                    handler.openUri(Config.GITHUB_URL)
                },
                modifier = Modifier.clip(RoundedCornerShape(15.dp))
            )
            SectionTitle(text = "v$version")
        }
    )
}

@Composable
fun SaturdayRoutineChooserDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {}
) {
    var selectedDay by remember { mutableStateOf(Prefs[Prefs.SATURDAY_KEY, "saturday"]) }

    val selectableOptions =
        listOf("monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "No Class")
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(
                    text = "Saturday Routine",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Choose your Saturday routine",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.surfaceTint
                    )
                )
            }

        },
        text = {
            LazyColumn {
                selectableOptions.forEach { day ->
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedDay = day
                                    Prefs[Prefs.SATURDAY_KEY] = day
                                    onDismiss()
                                },
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = day.replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(Locale.ROOT)
                                    else it.toString()
                                },
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.surfaceTint
                                ),
                                modifier = Modifier.padding(8.dp)
                            )
                            RadioButton(
                                selected = day == selectedDay,
                                onClick = {
                                    selectedDay = day
                                    Prefs[Prefs.SATURDAY_KEY] = day
                                    onDismiss()
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {},
        modifier = modifier
    )
}

@Preview
@Composable
fun PreviewSettingsScreen() {
    val user = UserData(
        username = "John Doe",
        email = "johndoe@gmail.com",
        photoUrl = "",
        uid = ""
    )
    SettingsScreen(
        user = user
    )
    SaturdayRoutineChooserDialog()
}

