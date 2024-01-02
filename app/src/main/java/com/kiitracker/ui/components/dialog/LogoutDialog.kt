package com.kiitracker.ui.components.dialog

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kiitracker.ui.theme.AppTheme

@Composable
fun LogoutDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Text(
                text = "Log Out",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        onConfirm()
                    }

            )
        },
        title = {
            Text(
                text = "Log Out?",
            )
        },
        text = {
            Text(text = "Are you sure you want to logout?")
        },
        dismissButton = {
            Text(
                text = "Cancel",
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        onDismiss()
                    }
            )
        }
    )
}

@Preview
@Composable
fun LogoutDialogPreview() {
    AppTheme {
        LogoutDialog(
            onDismiss = {},
            onConfirm = {}
        )
    }
}
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LogoutDialogPreviewDark() {
    AppTheme {
        LogoutDialog(
            onDismiss = {},
            onConfirm = {}
        )
    }
}