package com.fphoenixcorneae.baseui.ui.advanced

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.baseui.chart.PieChart

@Preview
@Composable
fun PieChartScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier
                .background(Color(0x50888888))
                .padding(20.dp)
        ) {
            PieChart(
                modifier = Modifier.size(120.dp),
                datas = listOf(
                    Triple(Color.Red, 2f, Triple("红色", Color.Black, 10.sp)),
                    Triple(Color.Green, 3f, Triple("绿色", Color.Black, 10.sp)),
                    Triple(Color.Blue, 5f, Triple("蓝色", Color.Black, 10.sp)),
                    Triple(Color.Yellow, 7f, Triple("黄色", Color.Black, 10.sp)),
                ),
                enabled = true,
            )
            Spacer(modifier = Modifier.width(20.dp))
            PieChart(
                modifier = Modifier.size(120.dp),
                datas = listOf(
                    Triple(Color.Red, 2f, Triple("红色", Color.Black, 10.sp)),
                    Triple(Color.Green, 3f, Triple("绿色", Color.Black, 10.sp)),
                    Triple(Color.Blue, 5f, Triple("蓝色", Color.Black, 10.sp)),
                    Triple(Color.Yellow, 7f, Triple("黄色", Color.Black, 10.sp)),
                ),
                enabled = false,
            )
        }
    }
}