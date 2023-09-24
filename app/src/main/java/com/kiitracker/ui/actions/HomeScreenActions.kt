package com.kiitracker.ui.actions

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable


@Composable
fun RowScope.HomeScreenActions() {
    IconButton(onClick = {}) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Menu"
        )
    }
}