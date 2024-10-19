package com.fphoenixcorneae.baseui.ui.basic

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.baseui.TypewriterText

@Preview
@Composable
fun TypewriterTextScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TypewriterText(
            animatedText = "蜀 道 难\n" +
                    "\u200B唐·李白\n" +
                    "噫吁嚱（yī xūxī），危乎高哉！蜀道之难，难于上青天！蚕丛及鱼凫（fú），开国何茫然！尔来四万八千岁，不与秦塞（sài）通人烟。西当太白有鸟道，可以横绝峨眉巅（diān)。地崩山摧壮士死，然后天梯石栈（zhàn）相钩连。上有六龙回日之高标，下有冲波逆折之回川。黄鹤之飞尚不得过，猿猱（náo）欲度愁攀援。青泥何盘盘，百步九折萦（yíng）岩峦。扪参（mén shēn）历井仰胁息，以手抚膺（yīng）坐长叹。\n" +
                    "问君西游何时还？畏途巉（chán）岩不可攀。但见悲鸟号（háo）古木，雄飞雌从绕林间。又闻子规啼夜月，愁空山。蜀道之难，难于上青天，使人听此凋朱颜。连峰去天不盈尺，枯松倒挂倚绝壁。飞湍瀑（tuān pù）流争喧豗（huī），砯（pīng）崖转石万壑（hè）雷。其险也如此，嗟（jiē）尔远道之人胡为乎来哉！\n" +
                    "剑阁峥嵘而崔嵬（wéi），一夫当关，万夫莫开。所守或匪（fěi）亲，化为狼与豺。朝避猛虎，夕避长蛇；磨牙吮（shǔn）血（xuè），杀人如麻。锦城虽云乐，不如早还（huán）家。蜀道之难，难于上青天，侧身西望长咨嗟（zī jiē）！",
            typingInterval = 10,
        )
    }
    Column(modifier = Modifier.padding(25.dp)) {
        Text("TypewriterText", color = Color.Black.copy(0.8f), fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text("打字机文本", color = Color.Black.copy(0.55f), fontSize = 14.sp)
    }
}