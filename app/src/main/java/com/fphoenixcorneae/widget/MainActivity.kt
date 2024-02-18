package com.fphoenixcorneae.widget

import android.graphics.BlurMaskFilter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fphoenixcorneae.compose.clickableNoRipple
import com.fphoenixcorneae.widget.chart.PieChart
import com.fphoenixcorneae.widget.progressbar.CircleProgressBar
import com.fphoenixcorneae.widget.progressbar.HorizontalProgressBar
import com.fphoenixcorneae.widget.progressbar.VerticalProgressBar
import com.fphoenixcorneae.widget.ui.theme.ComposeCustomWidgetTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.fphoenixcorneae.widget.R

class MainActivity : ComponentActivity() {

    private val switchEnabled = MutableStateFlow(false)

    @OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeCustomWidgetTheme {
                SystemUiScaffold {
                    // A surface container using the 'background' color from the theme
                    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                        val coroutineScope = rememberCoroutineScope()
//                        RandomlyRollBallLayout()
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                var visibleBasicNumberKeyboard by remember { mutableStateOf(false) }
                                var visibleSidebarNumberKeyboard by remember { mutableStateOf(false) }
                                var visibleIdCardNumberKeyboard by remember { mutableStateOf(false) }
                                var visibleTitleNumberKeyboard by remember { mutableStateOf(false) }
                                var visibleNumberKeyboardWithKeys by remember { mutableStateOf(false) }
                                var visibleRandomNumberKeyboard by remember { mutableStateOf(false) }
                                Text(
                                    text = "弹出默认键盘",
                                    modifier = Modifier.clickableNoRipple {
                                        visibleBasicNumberKeyboard = true
                                        visibleSidebarNumberKeyboard = false
                                        visibleIdCardNumberKeyboard = false
                                        visibleTitleNumberKeyboard = false
                                        visibleNumberKeyboardWithKeys = false
                                        visibleRandomNumberKeyboard = false
                                    },
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "弹出带右侧栏的键盘",
                                    modifier = Modifier.clickableNoRipple {
                                        visibleBasicNumberKeyboard = false
                                        visibleSidebarNumberKeyboard = true
                                        visibleIdCardNumberKeyboard = false
                                        visibleTitleNumberKeyboard = false
                                        visibleNumberKeyboardWithKeys = false
                                        visibleRandomNumberKeyboard = false
                                    },
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "弹出身份证号键盘",
                                    modifier = Modifier.clickableNoRipple {
                                        visibleBasicNumberKeyboard = false
                                        visibleSidebarNumberKeyboard = false
                                        visibleIdCardNumberKeyboard = true
                                        visibleTitleNumberKeyboard = false
                                        visibleNumberKeyboardWithKeys = false
                                        visibleRandomNumberKeyboard = false
                                    },
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "弹出带标题的键盘",
                                    modifier = Modifier.clickableNoRipple {
                                        visibleBasicNumberKeyboard = false
                                        visibleSidebarNumberKeyboard = false
                                        visibleIdCardNumberKeyboard = false
                                        visibleTitleNumberKeyboard = true
                                        visibleNumberKeyboardWithKeys = false
                                        visibleRandomNumberKeyboard = false
                                    },
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "弹出配置多个按键的键盘",
                                    modifier = Modifier.clickableNoRipple {
                                        visibleBasicNumberKeyboard = false
                                        visibleSidebarNumberKeyboard = false
                                        visibleIdCardNumberKeyboard = false
                                        visibleTitleNumberKeyboard = false
                                        visibleNumberKeyboardWithKeys = true
                                        visibleRandomNumberKeyboard = false
                                    },
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "弹出随机数字键盘",
                                    modifier = Modifier.clickableNoRipple {
                                        visibleBasicNumberKeyboard = false
                                        visibleSidebarNumberKeyboard = false
                                        visibleIdCardNumberKeyboard = false
                                        visibleTitleNumberKeyboard = false
                                        visibleNumberKeyboardWithKeys = false
                                        visibleRandomNumberKeyboard = true
                                    },
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                BasicNumberKeyboard(
                                    modifier = Modifier.fillMaxWidth(),
                                    visible = visibleBasicNumberKeyboard
                                ) { key ->
                                    Toast.makeText(this@MainActivity, "$key", Toast.LENGTH_SHORT).show()
                                    if (key.second == KeyboardKeyType.Hide) {
                                        visibleBasicNumberKeyboard = false
                                    }
                                }
                                SidebarNumberKeyboard(
                                    modifier = Modifier.fillMaxWidth(),
                                    visible = visibleSidebarNumberKeyboard
                                ) { key ->
                                    Toast.makeText(this@MainActivity, "$key", Toast.LENGTH_SHORT).show()
                                    if (key.second == KeyboardKeyType.Complete) {
                                        visibleSidebarNumberKeyboard = false
                                    }
                                }
                                IdCardNumberKeyboard(
                                    modifier = Modifier.fillMaxWidth(),
                                    visible = visibleIdCardNumberKeyboard
                                ) { key ->
                                    Toast.makeText(this@MainActivity, "$key", Toast.LENGTH_SHORT).show()
                                    if (key.second == KeyboardKeyType.Complete) {
                                        visibleIdCardNumberKeyboard = false
                                    }
                                }
                                TitleNumberKeyboard(
                                    modifier = Modifier.fillMaxWidth(),
                                    visible = visibleTitleNumberKeyboard
                                ) { key ->
                                    Toast.makeText(this@MainActivity, "$key", Toast.LENGTH_SHORT).show()
                                    if (key.second == KeyboardKeyType.Complete) {
                                        visibleTitleNumberKeyboard = false
                                    }
                                }
                                NumberKeyboardWithKeys(
                                    modifier = Modifier.fillMaxWidth(),
                                    visible = visibleNumberKeyboardWithKeys
                                ) { key ->
                                    Toast.makeText(this@MainActivity, "$key", Toast.LENGTH_SHORT).show()
                                    if (key.second == KeyboardKeyType.Complete) {
                                        visibleNumberKeyboardWithKeys = false
                                    }
                                }
                                RandomNumberKeyboard(
                                    modifier = Modifier.fillMaxWidth(),
                                    visible = visibleRandomNumberKeyboard
                                ) { key ->
                                    Toast.makeText(this@MainActivity, "$key", Toast.LENGTH_SHORT).show()
                                    if (key.second == KeyboardKeyType.Hide) {
                                        visibleRandomNumberKeyboard = false
                                    }
                                }
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "PieChart：")
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
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(120.dp)) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .fillMaxHeight(0.5f)
                                        .weight(1f),
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = "HorizontalProgressBar：")
                                    HorizontalProgressBar(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(5.dp),
                                        progress = 80f,
                                        colorStops = listOf(0f to Color.Blue, 0.5f to Color.Magenta, 1f to Color.Red),
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    HorizontalProgressBar(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(5.dp),
                                        progress = 80f,
                                        showAnim = false,
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    HorizontalProgressBar(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(5.dp),
                                        progress = 100f,
                                        colorStops = listOf(0f to Color.Blue, 0.5f to Color.Magenta, 1f to Color.Red),
                                        showAnim = false,
                                    )
                                }
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .weight(1f)
                                ) {
                                    Text(text = "VerticalProgressBar：")
                                    Row(
                                        modifier = Modifier.fillMaxWidth(0.3f),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        VerticalProgressBar(
                                            modifier = Modifier
                                                .width(5.dp)
                                                .fillMaxHeight(),
                                            progress = 30f,
                                            colorStops = listOf(
                                                0f to Color.Blue,
                                                0.5f to Color.Magenta,
                                                1f to Color.Red
                                            ),
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        VerticalProgressBar(
                                            modifier = Modifier
                                                .width(5.dp)
                                                .fillMaxHeight(),
                                            progress = 50f,
                                            showAnim = false,
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        VerticalProgressBar(
                                            modifier = Modifier
                                                .width(5.dp)
                                                .fillMaxHeight(),
                                            progress = 100f,
                                            colorStops = listOf(
                                                0f to Color.Blue,
                                                0.5f to Color.Magenta,
                                                1f to Color.Red
                                            ),
                                            showAnim = false,
                                        )
                                    }
                                }
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "AuthCodeTextField：")
                                AuthCodeTextField(
                                    length = 4,
                                    cursorCharacter = "|",
                                )
                            }
                            Text(text = "CircleProgressBar：")
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                CircleProgressBar(
                                    radius = 30.dp,
                                    progress = 100f,
                                    colorStops = listOf(0f to Color.Blue, 0.5f to Color.Yellow, 1f to Color.Red),
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                CircleProgressBar(
                                    modifier = Modifier.background(Color.White),
                                    progress = 100f,
                                    radius = 30.dp,
                                    color = Color.Red,
                                    colorStops = listOf(0f to Color.Blue, 0.5f to Color.Yellow, 1f to Color.Red),
                                    startAngle = -210f,
                                    maxAngle = 240f,
                                    centerText = buildAnnotatedString { append("环形进度条") },
                                    centerTextStyle = TextStyle(fontSize = 12.sp),
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                CircleProgressBar(
                                    radius = 30.dp,
                                    progress = 75f,
                                    clockwise = false,
                                    colorStops = listOf(0f to Color.Blue, 0.5f to Color.Yellow, 1f to Color.Red)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                CircleProgressBar(
                                    modifier = Modifier.background(Color.White),
                                    progress = 100f,
                                    radius = 30.dp,
                                    color = Color.Red,
                                    startAngle = -150f,
                                    maxAngle = 240f,
                                    centerText = buildAnnotatedString { append("环形进度条") },
                                    centerTextStyle = TextStyle(fontSize = 12.sp),
                                    clockwise = false,
                                )
                            }
                            Text(text = "CustomSeekbar：")
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(96.dp)
                                        .background(Color.White, RoundedCornerShape(6.dp))
                                        .padding(horizontal = 20.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.mipmap.ic_voice_silence),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(35.dp)
                                            .align(Alignment.CenterStart)
                                    )
                                    Image(
                                        painter = painterResource(id = R.mipmap.ic_voice),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(35.dp)
                                            .align(Alignment.CenterEnd)
                                    )
                                    CustomSeekbar(
                                        modifier = Modifier
                                            .padding(horizontal = 70.dp)
                                            .align(Alignment.CenterStart)
                                    )
                                }
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "Shadow 绘制：")
                                Spacer(modifier = Modifier.height(8.dp))
                                Divider(
                                    color = Color.Black.copy(0.05f),
                                    modifier = Modifier.drawColoredShadow(
                                        color = Color.Black,
                                        alpha = 0.05f,
                                        offsetY = (-1.6).dp,
                                        shadowRadius = 4.dp,
                                    ),
                                    thickness = 2.dp,
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text = "GradientButton：")
                                GradientButton(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(30.dp),
                                    text = "Gradient Button",
                                    textStyle = TextStyle(
                                        color = Color.White,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.W500
                                    ),
                                    gradientBrush = Brush.linearGradient(
                                        listOf(
                                            Color(0xFF4185F2),
                                            Color(0xFF3362F7)
                                        )
                                    ),
                                    shape = RoundedCornerShape(6.dp),
                                )
                            }
                            Text(text = "GlowCircle：")
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                GlowCircle(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .background(Color.Black),
                                    blurStyle = BlurMaskFilter.Blur.NORMAL,
                                )
                                GlowCircle(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .background(Color.Black),
                                    glowColor = Color.Magenta,
                                    blurStyle = BlurMaskFilter.Blur.SOLID,
                                )
                                GlowCircle(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .background(Color.Black),
                                    blurStyle = BlurMaskFilter.Blur.INNER,
                                )
                                GlowCircle(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .background(Color.Black),
                                    glowColor = Color.Magenta,
                                    blurStyle = BlurMaskFilter.Blur.OUTER,
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text = "RatingBar：")
                                RatingBar(touchEnable = true) {
                                    Log.d("CustomWidget", "RatingBar: $it")
                                }
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Star：")
                                Star()
                                Spacer(modifier = Modifier.width(8.dp))
                                Star(borderWidth = 1.dp, borderColor = Color.Magenta, filledColor = Color.Gray)
                                Spacer(modifier = Modifier.width(8.dp))
                                Star(backgroundColor = Color.Gray, filledFraction = 0.5f)
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Heart：")
                                Heart()
                                Spacer(modifier = Modifier.width(8.dp))
                                Heart(color = Color.Magenta)
                                Spacer(modifier = Modifier.width(8.dp))
                                Heart(isFill = false)
                            }
                            Text(text = "MarqueeAperture：")
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                MarqueeAperture(modifier = Modifier.size(60.dp), radius = 8.dp)
                                Spacer(modifier = Modifier.width(8.dp))
                                MarqueeAperture(modifier = Modifier.size(60.dp), radius = 30.dp, color2Stops = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                MarqueeAperture(
                                    modifier = Modifier
                                        .width(80.dp)
                                        .height(60.dp),
                                    radius = 0.dp,
                                    color1Stops = null
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                val pagerState = rememberPagerState(
                                    initialPage = 1,
                                    initialPageOffsetFraction = 0f
                                ) { 3 }
                                Text(text = "TabRow：")
                                val tabs = listOf("发现", "推荐", "关注")
                                TabRow(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(30.dp)
                                        .background(Color.White),
                                    tabs = tabs,
                                    pagerState = pagerState,
                                    containerColor = Color(0xFFFAFAFA),
                                    contentColor = Color(0xFF888888),
                                    selectedContentColor = Color(0xFF444444),
                                    dividerColor = Color(0xFFCCCCCC),
                                )
                                HorizontalPager(
                                    modifier = Modifier,
                                    state = pagerState,
                                    pageSpacing = 0.dp,
                                    userScrollEnabled = true,
                                    reverseLayout = false,
                                    contentPadding = PaddingValues(0.dp),
                                    beyondBoundsPageCount = 0,
                                    pageSize = PageSize.Fill,
                                    flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
                                    key = null,
                                    pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
                                        Orientation.Horizontal
                                    ),
                                    pageContent =  {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(60.dp),
                                            contentAlignment = Alignment.Center,
                                        ) {
                                            Text(text = tabs[it])
                                        }
                                    }
                                )
                            }
                            val isChecked by switchEnabled.collectAsState()
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "Switch：")
                                Switch(
                                    checked = isChecked,
                                    width = 40.dp,
                                    height = 20.dp,
                                ) { checked ->
                                    Log.d("CustomWidget", "Switch: $checked")
                                    coroutineScope.launch {
                                        switchEnabled.emit(checked)
                                    }
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Switch(
                                    checked = false,
                                    enabled = false,
                                    width = 40.dp,
                                    height = 20.dp,
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = "Triangle：")
                                Triangle(width = 30.dp, height = 20.dp, color = Color.Blue, cornerRadius = 20f)
                                Triangle(
                                    width = 30.dp,
                                    height = 20.dp,
                                    color = Color.Red,
                                    cornerRadius = 20f,
                                    rotateDegree = 180f
                                )
                            }
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(30.dp)) {
                                Text(text = "VerticalDivider：")
                                VerticalDivider(thickness = 2.dp, color = Color.Red)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(30.dp)) {
                                Text(text = "Square：")
                                Square(modifier = Modifier.width(30.dp)) {
                                    Box(modifier = Modifier.background(Color.Gray))
                                }
                            }
                            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(30.dp)) {
                                Text(text = "CustomEditText：")
                                var text by remember { mutableStateOf("") }
                                CustomEditText(
                                    text = text,
                                    onValueChange = {
                                        text = it
                                        Log.d("CustomWidget", "CustomEditText: $it")
                                    },
                                    textStyle = TextStyle(color = Color.Black, fontSize = 14.sp),
                                    backgroundColor = Color(0xFFF7F7F7),
                                    paddingStart = 8.dp,
                                    paddingEnd = 8.dp,
                                    hint = "请输入账号",
                                    hintColor = Color(0xFF256842),
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "TypewriterText：")
                                TypewriterText(
                                    animatedText = "蜀 道 难\n" +
                                            "\u200B唐·李白\n" +
                                            "噫吁嚱（yī xūxī），危乎高哉！蜀道之难，难于上青天！蚕丛及鱼凫（fú），开国何茫然！尔来四万八千岁，不与秦塞（sài）通人烟。西当太白有鸟道，可以横绝峨眉巅（diān)。地崩山摧壮士死，然后天梯石栈（zhàn）相钩连。上有六龙回日之高标，下有冲波逆折之回川。黄鹤之飞尚不得过，猿猱（náo）欲度愁攀援。青泥何盘盘，百步九折萦（yíng）岩峦。扪参（mén shēn）历井仰胁息，以手抚膺（yīng）坐长叹。\n" +
                                            "问君西游何时还？畏途巉（chán）岩不可攀。但见悲鸟号（háo）古木，雄飞雌从绕林间。又闻子规啼夜月，愁空山。蜀道之难，难于上青天，使人听此凋朱颜。连峰去天不盈尺，枯松倒挂倚绝壁。飞湍瀑（tuān pù）流争喧豗（huī），砯（pīng）崖转石万壑（hè）雷。其险也如此，嗟（jiē）尔远道之人胡为乎来哉！\n" +
                                            "剑阁峥嵘而崔嵬（wéi），一夫当关，万夫莫开。所守或匪（fěi）亲，化为狼与豺。朝避猛虎，夕避长蛇；磨牙吮（shǔn）血（xuè），杀人如麻。锦城虽云乐，不如早还（huán）家。蜀道之难，难于上青天，侧身西望长咨嗟（zī jiē）！",
                                    typingInterval = 10,
                                )
                            }
                        }

                        LaunchedEffect(key1 = Unit) {
                            coroutineScope.launch {
                                delay(1000)
                                switchEnabled.emit(true)
                            }
                        }
                    }
                }
            }
        }
    }
}