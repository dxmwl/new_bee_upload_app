package com.dxmwl.newbee.widget

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun TwoPage(
    leftPage: @Composable ColumnScope.() -> Unit,
    rightPage: @Composable ColumnScope.() -> Unit
) {
    Row(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxHeight().weight(4.0f)
                .padding(20.dp),
            content = leftPage
        )
        Column(
            modifier = Modifier.fillMaxHeight().weight(6.0f)
                .padding(20.dp),
            content = rightPage
        )
    }
}