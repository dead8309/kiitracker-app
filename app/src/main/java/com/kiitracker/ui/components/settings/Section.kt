package com.kiitracker.ui.components.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kiitracker.ui.theme.AppTheme

@Composable
fun Section(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier.padding(15.dp, 5.dp).then(modifier),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SectionTitle(text = title)
        content()
    }
}

@Composable
fun SectionTitle(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.secondary,
    style: TextStyle = MaterialTheme.typography.labelLarge
) {

    Text(
        text = text,
        modifier = modifier,
        color = color,
        style = style
    )
}

@Preview
@Composable
fun SectionPreview() {
    AppTheme {
        Section(
            modifier = Modifier.background(Color.White),
            title = "Account"
        ) {
            Text(text = "Hello World!")
        }
    }
}
