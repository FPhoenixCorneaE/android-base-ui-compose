package com.fphoenixcorneae.baseui.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.baseui.R
import com.fphoenixcorneae.baseui.ext.clickableNoRipple
import com.fphoenixcorneae.baseui.model.HomeMenuData
import com.fphoenixcorneae.baseui.model.MenuGroup
import com.fphoenixcorneae.baseui.nav.NavController

@Preview
@Composable
fun HomeScreen() {
    var current by rememberSaveable { mutableStateOf<Int?>(null) }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff5f5f5))
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        itemsIndexed(HomeMenuData.groups) { index, group ->
            HomeMenuGroupItem(
                group = group,
                isExpanded = index == current,
            ) {
                current = if (current == index) null else index
            }
        }
    }
}

@Composable
fun HomeMenuGroupItem(
    group: MenuGroup,
    isExpanded: Boolean,
    onToggle: () -> Unit,
) {
    val rotate by animateFloatAsState(if (isExpanded) 90f else 0f, label = "IconRotate")
    val currentComposer = currentComposer
    Column(
        Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(Color.White)
    ) {
        Row(
            Modifier
                .clickableNoRipple { onToggle() }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = group.name.orEmpty(),
                color = Color.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(R.drawable.ic_arrow_right),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .rotate(rotate)
            )
        }
        if (group.children != null) {
            AnimatedVisibility(visible = isExpanded) {
                Column {
                    val children = remember { group.children.sortedBy { it.name } }
                    children.forEachIndexed { index, item ->
                        HorizontalDivider(
                            Modifier.padding(horizontal = 16.dp),
                            thickness = 0.5.dp,
                            color = Color.LightGray
                        )
                        Row(
                            Modifier
                                .clickableNoRipple {
                                    NavController.get(currentComposer).navigate(item.router.orEmpty())
                                }
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item.name.orEmpty(),
                                color = Color(0xff222222),
                                fontSize = 16.sp,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_right),
                                contentDescription = null,
                                tint = Color(0xff222222),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}