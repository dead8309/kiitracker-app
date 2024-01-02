package com.kiitracker.ui.components

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kiitracker.ui.components.subjects.SubjectClassWithDirectionsCard
import com.kiitracker.ui.components.subjects.SubjectTimeSlot
import com.kiitracker.ui.components.subjects.SubjectTitle
import com.kiitracker.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubjectCard(
    selected: Boolean = false,
    onSelect: () -> Unit = {},
    title: String = "BME-08",
    timeSlot: String = "8:00 - 9:00",
    classRoom: String = "CL-1",
    campus: String = "Campus12",
) {
    val arrowIconState by animateFloatAsState(
        targetValue = if (selected) 180f else 0f,
        label = "Dropdown Arrow Icon"
    )
    val context = LocalContext.current
    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clip(RoundedCornerShape(20.dp))
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 350,
                    easing = LinearOutSlowInEasing
                )
            )
            .then(
                if (selected)
                    Modifier.border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(20.dp)
                    )
                else Modifier
            )
        ,
        onClick = onSelect,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp, 22.dp, 22.dp, 20.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(6f)
                ) {
                    SubjectTitle(text = title)
                    SubjectTimeSlot(timeSlot = timeSlot)
                }
                ExpandMoreButton(
                    modifier = Modifier.weight(1f),
                    arrowIconState = arrowIconState
                )
            }
            if (selected ) {
                SubjectClassWithDirectionsCard(
                    `class` = classRoom,
                    campus = campus
                ) {
                    val gmmIntentUri = Uri.parse("google.navigation:q=Kiit+University,$campus&mode=w")
                    Toast.makeText(context, "Opening $campus", Toast.LENGTH_SHORT).show()
                    val mapIntent = Intent().apply {
                        action = Intent.ACTION_VIEW
                        data = gmmIntentUri
                        setPackage("com.google.android.apps.maps")
                    }
                    context.startActivity(mapIntent)
                }
            }
        }
    }
}


@Composable
@Preview
fun SubjectCardPreview() {
    AppTheme {
        Column(Modifier.background(Color.White)) {
            SubjectCard()
        }
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun SubjectCardPreviewDark() {
    AppTheme {
        SubjectCard()
    }
}