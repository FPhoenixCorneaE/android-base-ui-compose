package com.fphoenixcorneae.baseui.ui.basic

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fphoenixcorneae.baseui.RatingBar

@Preview
@Composable
fun RatingBarScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("步进为0.1：")
        Spacer(modifier = Modifier.height(8.dp))
        RatingBar(ratingStep = 0.1f, filledColor = Color.Cyan) {
            Log.d("BaseUi", "RatingBar: $it")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text("步进为0.2：")
        Spacer(modifier = Modifier.height(8.dp))
        RatingBar(ratingStep = 0.2f, filledColor = Color.Magenta) {
            Log.d("BaseUi", "RatingBar: $it")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text("步进为0.5：")
        Spacer(modifier = Modifier.height(8.dp))
        RatingBar(ratingStep = 0.5f, filledColor = Color.Yellow) {
            Log.d("BaseUi", "RatingBar: $it")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text("不可点击触摸：")
        Spacer(modifier = Modifier.height(8.dp))
        RatingBar(rating = 2.5f, touchEnable = false)
    }
}