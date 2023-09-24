package com.kiitracker.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp


@Composable
fun ExpandMoreButton(
    modifier: Modifier,
    arrowIconState: Float
) {
        Icon(
            imageVector = Icons.Default.ExpandMore,
            contentDescription = "Dropdown Arrow Icon",
            modifier = modifier
                .rotate(arrowIconState)
                .size(48.dp)
                .alpha(0.5f),
            tint = MaterialTheme.colorScheme.secondary
        )
}