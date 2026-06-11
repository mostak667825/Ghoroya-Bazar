package com.example.ui

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.data.*

// --------------------------------------------------------------------
// Stylized Custom Grocery Image Drawing using Canvas for ultra-realism
// --------------------------------------------------------------------
@Composable
fun ProductImagePlaceholder(productType: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(PureWhite, GreenLight),
                    center = Offset(0.5f, 0.5f)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize().padding(12.dp)) {
            val w = size.width
            val h = size.height
            val primaryColor = GreenPrimary
            val accentColor = GoldPrimary
            val satinColor = GoldSatin

            when (productType) {
                "rice" -> {
                    val path = Path().apply {
                        moveTo(w * 0.3f, h * 0.85f)
                        lineTo(w * 0.7f, h * 0.85f)
                        quadraticTo(w * 0.82f, h * 0.5f, w * 0.75f, h * 0.35f)
                        lineTo(w * 0.65f, h * 0.25f)
                        quadraticTo(w * 0.5f, h * 0.32f, w * 0.35f, h * 0.25f)
                        lineTo(w * 0.25f, h * 0.35f)
                        quadraticTo(w * 0.18f, h * 0.5f, w * 0.3f, h * 0.85f)
                    }
                    drawPath(path = path, color = primaryColor)
                    
                    drawCircle(color = accentColor, radius = w * 0.05f, center = Offset(w * 0.5f, h * 0.3f))
                    drawRoundRect(
                        color = accentColor,
                        topLeft = Offset(w * 0.35f, h * 0.45f),
                        size = androidx.compose.ui.geometry.Size(w * 0.3f, h * 0.22f),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(12f, 12f)
                    )
                    drawCircle(color = PureWhite, radius = 6f, center = Offset(w * 0.5f, h * 0.18f))
                    drawCircle(color = PureWhite, radius = 5f, center = Offset(w * 0.44f, h * 0.15f))
                    drawCircle(color = PureWhite, radius = 7f, center = Offset(w * 0.56f, h * 0.16f))
                }
                "oil" -> {
                    drawRoundRect(color = satinColor, topLeft = Offset(w * 0.43f, h * 0.15f), size = androidx.compose.ui.geometry.Size(w * 0.14f, h * 0.07f))
                    drawRect(color = primaryColor, topLeft = Offset(w * 0.45f, h * 0.22f), size = androidx.compose.ui.geometry.Size(w * 0.1f, h * 0.12f))
                    val bodyPath = Path().apply {
                        moveTo(w * 0.45f, h * 0.34f)
                        lineTo(w * 0.28f, h * 0.45f)
                        quadraticTo(w * 0.23f, h * 0.55f, w * 0.25f, h * 0.85f)
                        lineTo(w * 0.75f, h * 0.85f)
                        quadraticTo(w * 0.77f, h * 0.55f, w * 0.72f, h * 0.34f)
                        lineTo(w * 0.55f, h * 0.34f)
                    }
                    drawPath(path = bodyPath, color = primaryColor)
                    
                    val liquidPath = Path().apply {
                        moveTo(w * 0.32f, h * 0.52f)
                        lineTo(w * 0.28f, h * 0.62f)
                        lineTo(w * 0.26f, h * 0.83f)
                        lineTo(w * 0.74f, h * 0.83f)
                        lineTo(w * 0.72f, h * 0.62f)
                        lineTo(w * 0.68f, h * 0.52f)
                    }
                    drawPath(path = liquidPath, color = accentColor)
                    
                    drawRoundRect(
                        color = primaryColor,
                        topLeft = Offset(w * 0.18f, h * 0.44f),
                        size = androidx.compose.ui.geometry.Size(w * 0.12f, h * 0.22f),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f),
                        style = Stroke(width = 8f)
                    )
                }
                "lentils" -> {
                    drawArc(
                        color = accentColor,
                        startAngle = 0f,
                        sweepAngle = 180f,
                        useCenter = true,
                        topLeft = Offset(w * 0.2f, h * 0.45f),
                        size = androidx.compose.ui.geometry.Size(w * 0.6f, h * 0.4f)
                    )
                    drawCircle(color = satinColor, radius = w * 0.28f, center = Offset(w * 0.5f, h * 0.45f))
                    val spoonPath = Path().apply {
                        moveTo(w * 0.75f, h * 0.25f)
                        lineTo(w * 0.55f, h * 0.48f)
                        quadraticTo(w * 0.48f, h * 0.55f, w * 0.45f, h * 0.52f)
                        quadraticTo(w * 0.42f, h * 0.49f, w * 0.48f, h * 0.42f)
                        lineTo(w * 0.68f, h * 0.2f)
                    }
                    drawPath(path = spoonPath, color = primaryColor)
                }
                "flour" -> {
                    drawRoundRect(
                        color = primaryColor,
                        topLeft = Offset(w * 0.25f, h * 0.25f),
                        size = androidx.compose.ui.geometry.Size(w * 0.5f, h * 0.6f),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(16f, 16f)
                    )
                    drawRoundRect(
                        color = PureWhite,
                        topLeft = Offset(w * 0.3f, h * 0.3f),
                        size = androidx.compose.ui.geometry.Size(w * 0.4f, h * 0.45f),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f)
                    )
                    drawCircle(color = satinColor, radius = w * 0.04f, center = Offset(w * 0.5f, h * 0.42f))
                    drawLine(color = satinColor, start = Offset(w * 0.5f, h * 0.42f), end = Offset(w * 0.5f, h * 0.58f), strokeWidth = 6f)
                    drawLine(color = satinColor, start = Offset(w * 0.5f, h * 0.45f), end = Offset(w * 0.42f, h * 0.4f), strokeWidth = 5f)
                    drawLine(color = satinColor, start = Offset(w * 0.5f, h * 0.5f), end = Offset(w * 0.58f, h * 0.45f), strokeWidth = 5f)
                }
                "sugar" -> {
                    drawRoundRect(
                        color = primaryColor,
                        topLeft = Offset(w * 0.28f, h * 0.28f),
                        size = androidx.compose.ui.geometry.Size(w * 0.44f, h * 0.56f),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(20f, 20f)
                    )
                    drawRoundRect(
                        color = satinColor,
                        topLeft = Offset(w * 0.34f, h * 0.48f),
                        size = androidx.compose.ui.geometry.Size(w * 0.32f, h * 0.22f),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f)
                    )
                    drawCircle(color = PureWhite, radius = 10f, center = Offset(w * 0.5f, h * 0.4f))
                }
                "onion" -> {
                    val path = Path().apply {
                        moveTo(w * 0.5f, h * 0.2f)
                        quadraticTo(w * 0.2f, h * 0.45f, w * 0.3f, h * 0.72f)
                        quadraticTo(w * 0.5f, h * 0.88f, w * 0.7f, h * 0.72f)
                        quadraticTo(w * 0.8f, h * 0.45f, w * 0.5f, h * 0.2f)
                    }
                    drawPath(path = path, color = satinColor)
                    
                    drawLine(color = primaryColor, start = Offset(w * 0.5f, h * 0.85f), end = Offset(w * 0.42f, h * 0.92f), strokeWidth = 4f)
                    drawLine(color = primaryColor, start = Offset(w * 0.5f, h * 0.85f), end = Offset(w * 0.50f, h * 0.94f), strokeWidth = 4f)
                    drawLine(color = primaryColor, start = Offset(w * 0.5f, h * 0.85f), end = Offset(w * 0.58f, h * 0.92f), strokeWidth = 4f)
                    
                    drawLine(color = AlertSuccess, start = Offset(w * 0.5f, h * 0.22f), end = Offset(w * 0.52f, h * 0.12f), strokeWidth = 6f)
                }
                "milk" -> {
                    val cartonPath = Path().apply {
                        moveTo(w * 0.32f, h * 0.32f)
                        lineTo(w * 0.5f, h * 0.2f)
                        lineTo(w * 0.68f, h * 0.32f)
                        lineTo(w * 0.68f, h * 0.85f)
                        lineTo(w * 0.32f, h * 0.85f)
                        close()
                    }
                    drawPath(path = cartonPath, color = primaryColor)
                    
                    val foldPath = Path().apply {
                        moveTo(w * 0.5f, h * 0.2f)
                        lineTo(w * 0.68f, h * 0.32f)
                        lineTo(w * 0.5f, h * 0.32f)
                        close()
                    }
                    drawPath(path = foldPath, color = Color(0xFF042617))
                    
                    drawArc(
                        color = PureWhite,
                        startAngle = 180f,
                        sweepAngle = 180f,
                        useCenter = true,
                        topLeft = Offset(w * 0.35f, h * 0.52f),
                        size = androidx.compose.ui.geometry.Size(w * 0.3f, h * 0.2f)
                    )
                }
                "potato" -> {
                    val potatoPath = Path().apply {
                        moveTo(w * 0.3f, h * 0.45f)
                        quadraticTo(w * 0.28f, h * 0.32f, w * 0.55f, h * 0.35f)
                        quadraticTo(w * 0.78f, h * 0.38f, w * 0.72f, h * 0.62f)
                        quadraticTo(w * 0.65f, h * 0.85f, w * 0.42f, h * 0.80f)
                        quadraticTo(w * 0.22f, h * 0.65f, w * 0.3f, h * 0.45f)
                    }
                    drawPath(path = potatoPath, color = satinColor)
                    
                    drawCircle(color = primaryColor, radius = 6f, center = Offset(w * 0.45f, h * 0.48f))
                    drawCircle(color = primaryColor, radius = 5f, center = Offset(w * 0.62f, h * 0.55f))
                    drawCircle(color = primaryColor, radius = 7f, center = Offset(w * 0.38f, h * 0.65f))
                }
                "salt" -> {
                    drawRoundRect(
                        color = primaryColor,
                        topLeft = Offset(w * 0.32f, h * 0.25f),
                        size = androidx.compose.ui.geometry.Size(w * 0.36f, h * 0.6f),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(12f, 12f)
                    )
                    drawRoundRect(
                        color = PureWhite,
                        topLeft = Offset(w * 0.36f, h * 0.38f),
                        size = androidx.compose.ui.geometry.Size(w * 0.28f, h * 0.35f),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f)
                    )
                    drawCircle(color = accentColor, radius = 8f, center = Offset(w * 0.5f, h * 0.5f))
                }
                "soap" -> {
                    drawRoundRect(
                        color = primaryColor,
                        topLeft = Offset(w * 0.22f, h * 0.28f),
                        size = androidx.compose.ui.geometry.Size(w * 0.56f, h * 0.56f),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(16f, 16f)
                    )
                    drawCircle(color = satinColor, radius = w * 0.08f, center = Offset(w * 0.44f, h * 0.45f))
                    drawCircle(color = PureWhite, radius = 12f, center = Offset(w * 0.32f, h * 0.36f))
                    drawCircle(color = PureWhite, radius = 16f, center = Offset(w * 0.62f, h * 0.62f))
                }
                else -> {
                    drawCircle(color = primaryColor, radius = w * 0.3f, center = Offset(w * 0.5f, h * 0.5f))
                    drawCircle(color = accentColor, radius = w * 0.2f, center = Offset(w * 0.5f, h * 0.5f))
                }
            }
        }
    }
}

// --------------------------------------------------------------------
// Digital Gold Membership Card (FINTECH VIP GLAMOUR STYLE)
// --------------------------------------------------------------------
@Composable
fun GoldMemberCard(
    memberId: String,
    memberName: String,
    savingsAmount: Double,
    rewardPoints: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(210.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(GreenPrimaryDark, GreenMedium),
                        start = Offset(0f, 0f),
                        end = Offset(1000f, 1000f)
                    )
                )
                .drawBehind {
                    drawCircle(
                        color = GoldPrimary.copy(alpha = 0.15f),
                        radius = size.width * 0.5f,
                        center = Offset(size.width * 0.9f, size.height * 0.1f),
                        style = Stroke(width = 3f)
                    )
                    drawCircle(
                        color = GoldPrimary.copy(alpha = 0.08f),
                        radius = size.width * 0.7f,
                        center = Offset(size.width * 0.9f, size.height * 0.1f),
                        style = Stroke(width = 2f)
                    )
                }
                .padding(20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "ঘরোয়া বাজার ডট কম",
                        color = GoldPrimary,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "স্বল্প মূল্যে পরিবারের বাজার",
                        color = PureWhite.copy(alpha = 0.7f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light
                    )
                }
                Box(
                    modifier = Modifier
                        .size(34.dp, 26.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(GoldLight, GoldSatin)
                            )
                        )
                        .padding(4.dp)
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawLine(color = GreenPrimaryDark.copy(alpha = 0.4f), start = Offset(size.width * 0.5f, 0f), end = Offset(size.width * 0.5f, size.height))
                        drawLine(color = GreenPrimaryDark.copy(alpha = 0.4f), start = Offset(0f, size.height * 0.5f), end = Offset(size.width, size.height * 0.5f))
                        drawCircle(color = GreenPrimaryDark.copy(alpha = 0.3f), radius = 4f, center = Offset(size.width * 0.5f, size.height * 0.5f))
                    }
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(bottom = 2.dp)
            ) {
                Text(
                    text = memberId,
                    color = GoldLight,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "সদস্য নাম",
                    color = PureWhite.copy(alpha = 0.5f),
                    fontSize = 9.sp
                )
                Text(
                    text = memberName,
                    color = PureWhite,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(14.dp))
                    .background(PureWhite.copy(alpha = 0.08f))
                    .border(1.dp, GoldPrimary.copy(alpha = 0.25f), RoundedCornerShape(14.dp))
                    .padding(horizontal = 12.dp, vertical = 10.dp)
            ) {
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "মোট সাশ্রয়",
                        color = PureWhite.copy(alpha = 0.6f),
                        fontSize = 9.sp
                    )
                    Text(
                        text = "৳ ${savingsAmount.toInt()}",
                        color = GoldPrimary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "পয়েন্টস: $rewardPoints PTS",
                        color = PureWhite,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

// --------------------------------------------------------------------
// Fintech dynamic performance graph of Member's monthly saved limits!
// --------------------------------------------------------------------
@Composable
fun PerformanceSavingsChart(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp),
        colors = CardDefaults.cardColors(containerColor = GreenPrimaryDark),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "সাশ্রয় ও লয়ালিটি গ্রাফ",
                        color = GoldPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "সদস্য হবার পর থেকে ক্রমান্বয়ে সঞ্চয় বৃদ্ধি",
                        color = LightSurface.copy(alpha = 0.7f),
                        fontSize = 9.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(GoldPrimary.copy(alpha = 0.15f))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "+২৮% বৃদ্ধি",
                        color = GoldLight,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))

            Canvas(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                val cw = size.width
                val ch = size.height
                val gridLines = 4
                
                for (i in 0..gridLines) {
                    val y = ch * (i.toFloat() / gridLines)
                    drawLine(
                        color = PureWhite.copy(alpha = 0.05f),
                        start = Offset(0f, y),
                        end = Offset(cw, y),
                        strokeWidth = 2f
                    )
                }

                val points = listOf(
                    Offset(cw * 0.02f, ch * 0.85f),
                    Offset(cw * 0.2f, ch * 0.75f),
                    Offset(cw * 0.4f, ch * 0.55f),
                    Offset(cw * 0.6f, ch * 0.45f),
                    Offset(cw * 0.8f, ch * 0.25f),
                    Offset(cw * 0.98f, ch * 0.12f)
                )

                val strokePath = Path().apply {
                    moveTo(points[0].x, points[0].y)
                    for (i in 1 until points.size) {
                        val pPrev = points[i - 1]
                        val pCurr = points[i]
                        val controlPointX = (pPrev.x + pCurr.x) / 2
                        quadraticTo(controlPointX, pPrev.y, pCurr.x, pCurr.y)
                    }
                }

                val fillPath = Path().apply {
                    addPath(strokePath)
                    lineTo(points.last().x, ch)
                    lineTo(points.first().x, ch)
                    close()
                }

                drawPath(
                    path = fillPath,
                    brush = Brush.linearGradient(
                        colors = listOf(GoldPrimary.copy(alpha = 0.25f), Color.Transparent),
                        start = Offset(0f, 0f),
                        end = Offset(0f, ch)
                    )
                )

                drawPath(
                    path = strokePath,
                    color = GoldPrimary,
                    style = Stroke(width = 5f, cap = StrokeCap.Round)
                )

                for (pt in points) {
                    drawCircle(color = GreenMedium, radius = 10f, center = pt)
                    drawCircle(color = GoldPrimary, radius = 6f, center = pt)
                }
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val months = listOf("জানু", "ফেব্রু", "মার্চ", "এপ্রিল", "মে", "জুন")
                for (m in months) {
                    Text(
                        text = m,
                        color = PureWhite.copy(alpha = 0.5f),
                        fontSize = 9.sp
                    )
                }
            }
        }
    }
}

// --------------------------------------------------------------------
// UI/UX CASE STUDY PRESENTATION BOARD - SIMULATING 6 HIGH END SCREENS
// --------------------------------------------------------------------
@Composable
fun AppPresentationBoard(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val zoomedScreenIdx by viewModel.boardDetailScreenIdx.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DarkGray)
    ) {
        if (zoomedScreenIdx != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(0.92f).fillMaxHeight(0.85f),
                    colors = CardDefaults.cardColors(containerColor = GreenPrimaryDark),
                    elevation = CardDefaults.cardElevation(16.dp),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(GreenPrimary)
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "স্ক্রিন প্রিভিউ: " + when (zoomedScreenIdx) {
                                    0 -> "১. হোম মার্কেটপ্লেস"
                                    1 -> "২. ডিজিটাল মেম্বারশিপ ওয়ালেট"
                                    2 -> "৩. ফিনটেক সেভিংস ড্যাশবোর্ড"
                                    3 -> "৪. PASWO প্রোগ্রাম সেন্টার"
                                    4 -> "৫. নোটিশ নোটিফিকেশন"
                                    5 -> "৬. প্রিমিয়াম প্রোডাক্ট শপ"
                                    else -> ""
                                },
                                color = GoldPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            IconButton(onClick = { viewModel.selectBoardDetailScreen(null) }) {
                                Icon(Icons.Default.Close, contentDescription = "Close", tint = PureWhite)
                            }
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .background(LightSurface)
                                .padding(12.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .border(1.dp, GreenPrimary.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                        ) {
                            when (zoomedScreenIdx) {
                                0 -> ScreenHomeContentMirror(viewModel)
                                1 -> ScreenMembershipMirror(viewModel)
                                2 -> ScreenDashboardMirror(viewModel)
                                3 -> ScreenPASWOMirror(viewModel)
                                4 -> ScreenNoticeMirror(viewModel)
                                5 -> ScreenShopMirror(viewModel)
                            }
                        }
                        
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            colors = CardDefaults.cardColors(containerColor = GreenPrimary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = when (zoomedScreenIdx) {
                                    0 -> "ডিজাইন নোট: হোম স্ক্রিনে একটি মসৃণ সার্চ ও ক্যারোসেল ব্যানার যুক্ত করা হয়েছে। এটি গ্রাহকদের সহজেই পণ্য ও আমাদের বিশেষ PASWO প্রজেক্ট অন্বেষণ করতে দেয়।"
                                    1 -> "ডিজাইন নোট: মেম্বারশিপ কার্ডটিকে একটি সোনালী হলোগ্রাফিক ফিনটেক কার্ডের আদল দেয়া হয়েছে যাতে গ্রাহকরা নিজেদের এলিট পরিবার হিসেবে অনুভব করতে পারেন।"
                                    2 -> "ডিজাইন নোট: ড্যাশবোর্ড স্ক্রিনে সঞ্চয় ও কেনাকাটার পরিসংখ্যান সহ ক্যানভাস নির্মিত একটি প্রগতিশীল প্রবৃদ্ধি রেখাগ্ৰাফ রয়েছে।"
                                    3 -> "ডিজাইন নোট: PASWO প্রোগ্রাম হচ্ছে একটি সামাজিক কল্যাণমুখী উদ্যোগ যা সাধারণ পরিবারের সাহায্যার্থে চাল, ডাল, তেল নামমাত্র পাইকারি মূল্যে সরবরাহ করে।"
                                    4 -> "ডিজাইন নোট: বুলেটিন বোর্ড বিভাগে ইউনিয়ন ও কুরিগ্রাম অঞ্চলের নোটিশসমূহ একটি সুন্দর কার্ড বিন্যাসে ক্রমান্বয়ে রিয়েল-টাইমে প্রকাশিত হয়।"
                                    5 -> "ডিজাইন নোট: শপ স্ক্রিনে দ্রুত ক্যাটাগরি নির্বাচনের বাটন, পণ্য অনুসন্ধান এবং সহজ মেম্বার বনাম স্ট্যান্ডার্ড মূল্যের সুস্পষ্ট তুলনা রয়েছে।"
                                    else -> ""
                                },
                                color = PureWhite,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(12.dp),
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { viewModel.togglePresentationMode() },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "App Mode", tint = GreenPrimaryDark)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("ইন্টারেক্টিভ মোবাইল অ্যাপে ফিরে যান", color = GreenPrimaryDark, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = GreenPrimary),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.5.dp, GoldPrimary)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "ঘরোয়া বাজার ডট কম",
                            color = GoldPrimary,
                            fontWeight = FontWeight.Black,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "স্বল্প মূল্যে পরিবারের বাজার",
                            color = PureWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        HorizontalDivider(color = GoldPrimary.copy(alpha = 0.3f), thickness = 1.dp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "PREMIUM UI/UX CASE STUDY PRESENTATION",
                            color = GoldLight,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "A modern high-fidelity fintech design built with Jetpack Compose using Material Design 3, custom Canvas illustrations, glassmorphism shadows and 100% responsive grids.",
                            color = PureWhite.copy(alpha = 0.8f),
                            fontSize = 11.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 16.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = GreenPrimaryDark),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("ব্র্যান্ড কালারস:", color = PureWhite, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                        ColorSwatch(Color(0xFF084128), "Dark Green")
                        ColorSwatch(Color(0xFFD4AF37), "Gold")
                        ColorSwatch(Color(0xFFFFFFFF), "White")
                        ColorSwatch(Color(0xFFF4F7F5), "Soft Gray")
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "৬টি প্রিমিয়াম মোবাইল স্ক্রিনস (জুম করতে ক্লিক করুন)",
                    color = GoldPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(bottom = 8.dp)
                )

                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    val screens = listOf(
                        Triple(0, "১. হোম মার্কেটপ্লেস", "সার্চ ক্যারোসেল এবং মূল PASWO অফার"),
                        Triple(1, "২. গোল্ড মেম্বারশিপ কার্ড", "ডিজিটাল সোনালী কার্ড ও অনুগ্ৰহ রসদ"),
                        Triple(2, "৩. ফিনটেক ড্যাশবোর্ড", "সাশ্রয়ের বিবরণী এবং বিশ্লেষণাত্বক গ্রাফ"),
                        Triple(3, "৪. PASWO সোশাল হাব", "দরিদ্রতা বিমোচনে নামমাত্র মূল্যের রিলীফ"),
                        Triple(4, "৫. খবর ও নোটিশ বোর্ড", "এলাকা ভিত্তিক সার্বিক বিজ্ঞপ্তি ও বার্তা"),
                        Triple(5, "৬. পণ্য শপ ও ক্যাটাগরি", "সহজ তুলনামূলক সদস্য মূল্যের বাজার গ্রিড")
                    )

                    for (row in 0 until 3) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            val idx1 = row * 2
                            val idx2 = row * 2 + 1

                            Box(modifier = Modifier.weight(1f)) {
                                PresentationScreenMock(
                                    title = screens[idx1].second,
                                    subtitle = screens[idx1].third,
                                    onClick = { viewModel.selectBoardDetailScreen(screens[idx1].first) }
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(LightSurface)
                                    ) {
                                        when (screens[idx1].first) {
                                            0 -> ScreenHomeContentMirror(viewModel)
                                            1 -> ScreenMembershipMirror(viewModel)
                                            2 -> ScreenDashboardMirror(viewModel)
                                        }
                                    }
                                }
                            }

                            Box(modifier = Modifier.weight(1f)) {
                                PresentationScreenMock(
                                    title = screens[idx2].second,
                                    subtitle = screens[idx2].third,
                                    onClick = { viewModel.selectBoardDetailScreen(screens[idx2].first) }
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(LightSurface)
                                    ) {
                                        when (screens[idx2].first) {
                                            3 -> ScreenPASWOMirror(viewModel)
                                            4 -> ScreenNoticeMirror(viewModel)
                                            5 -> ScreenShopMirror(viewModel)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ColorSwatch(color: Color, name: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .clip(CircleShape)
                .background(color)
                .border(0.5.dp, GoldPrimary, CircleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = name, color = PureWhite, fontSize = 9.sp)
    }
}

@Composable
fun PresentationScreenMock(
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = GreenPrimary),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = title,
                color = GoldPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = subtitle,
                color = PureWhite.copy(alpha = 0.7f),
                fontSize = 8.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(260.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(2.5.dp, GoldPrimary.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            ) {
                content()
                
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                )
            }
        }
    }
}

// --------------------------------------------------------------------
// DYNAMIC LIVE CONTENT SCREENS (USED BOTH FOR MIRRORS AND APP PANELS)
// --------------------------------------------------------------------

@Composable
fun ScreenHomeContentMirror(viewModel: MainViewModel) {
    val searchVal by viewModel.searchQuery.collectAsState()
    
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(GreenPrimary)
                .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, "Loc", tint = GoldPrimary, modifier = Modifier.size(12.dp))
                    Spacer(modifier = Modifier.width(2.dp))
                    Text("রাজিবপুর, কুরিগ্রাম", color = PureWhite, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                }
                Text("হটলাইন: ০১৫১৮৪৮৯০৮০", color = GoldLight, fontSize = 8.sp)
            }
        }

        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(GreenMedium, GreenPrimaryDark)
                        )
                    )
            ) {
                Image(
                    painter = painterResource(id = com.example.R.drawable.premium_banner),
                    contentDescription = "Offer Banner",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(GreenPrimaryDark.copy(alpha = 0.45f))
                        .padding(8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Column {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(GoldPrimary)
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            Text("ঈদ মোবারক অফার", color = GreenPrimaryDark, fontSize = 7.sp, fontWeight = FontWeight.Bold)
                        }
                        Text("স্বল্প মূল্যে পরিবারের সামগ্রী", color = PureWhite, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text("গোল্ড মেম্বারদের জন্য ১০% ছাড়", color = GoldLight, fontSize = 8.sp)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val itemsQuick = listOf(
                    Pair("PASWO বাজার", Icons.Default.Star),
                    Pair("ক্যাশব্যাক", Icons.Default.ShoppingCart),
                    Pair("গোল্ড মেম্বার", Icons.Default.Star),
                    Pair("হেল্প", Icons.Default.Call)
                )
                for (it in itemsQuick) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            if (it.first == "PASWO বাজার") {
                                viewModel.selectCategory("PASWO")
                                viewModel.selectTab("Shop")
                            } else if (it.first == "গোল্ড মেম্বার") {
                                viewModel.selectTab("Member")
                            } else if (it.first == "ক্যাশব্যাক") {
                                viewModel.selectTab("Profile")
                            }
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(GreenPrimary.copy(alpha = 0.1f))
                                        .border(1.dp, GoldPrimary.copy(alpha = 0.3f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(it.second, "icon", tint = GreenPrimary, modifier = Modifier.size(16.dp))
                        }
                        Text(it.first, color = DarkGray, fontSize = 8.sp, modifier = Modifier.padding(top = 2.dp), fontWeight = FontWeight.Medium)
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(containerColor = GreenPrimaryDark),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "PASWO সামাজিক কল্যাণ উদ্যোগ",
                            color = GoldPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                        Text(
                            text = "কুরিগ্রাম এলাকার পরিবারের সাহায্যার্থে পাইকারি রেটে চাল, ডাল আটার বিশেষ সোশাল রিলীফ বাজার কার্যক্রম।",
                            color = PureWhite.copy(alpha = 0.8f),
                            fontSize = 8.sp,
                            lineHeight = 11.sp
                        )
                    }
                    Button(
                        onClick = { viewModel.applyForPaswo() },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                        modifier = Modifier.height(28.dp)
                    ) {
                        Text("আবেদন করুন", color = GreenPrimaryDark, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Text(
                text = "বিশেষ মেম্বার অফার সমূহ",
                color = GreenPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )

            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val featured = viewModel.products.value.take(4)
                for (prod in featured) {
                    Card(
                        modifier = Modifier
                            .width(115.dp)
                            .padding(bottom = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column {
                            ProductImagePlaceholder(
                                productType = prod.imageType,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(70.dp)
                            )
                            Column(modifier = Modifier.padding(6.dp)) {
                                Text(
                                    text = prod.name,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1,
                                    color = DarkGray
                                )
                                Text(
                                    text = "বাজার দাম: ৳${prod.regularPrice.toInt()}",
                                    fontSize = 7.sp,
                                    textDecoration = TextDecoration.LineThrough,
                                    color = Color.Gray
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "৳${prod.memberPrice.toInt()}/${prod.unit}",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black,
                                        color = GreenPrimary
                                    )
                                    IconButton(
                                        onClick = { viewModel.addProductToCart(prod) },
                                        modifier = Modifier.size(20.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Add,
                                            contentDescription = "Add",
                                            tint = GoldPrimary,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ScreenMembershipMirror(viewModel: MainViewModel) {
    val profile by viewModel.memberProfile.collectAsState()
    var showQrDialogLocal by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showQrDialogLocal = true }
        ) {
            GoldMemberCard(
                memberId = profile.memberId.ifEmpty { "GB-1033-26" },
                memberName = profile.name.ifEmpty { viewModel.currentUserName.value.ifEmpty { "মেম্বার" } },
                savingsAmount = profile.savingsAmount,
                rewardPoints = profile.rewardPoints,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Text(
            text = "💡 অফলাইন স্ক্যান ও ডিপো সুবিধা পেতে কার্ডে স্পর্শ করুন (QR কোড)",
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = GoldSatin,
            modifier = Modifier.padding(bottom = 12.dp, start = 4.dp)
        )

        if (showQrDialogLocal) {
            SimulatedQrDialog(
                memberId = profile.memberId.ifEmpty { "GB-1033-26" },
                memberName = profile.name.ifEmpty { viewModel.currentUserName.value.ifEmpty { "মেম্বার" } },
                onClose = { showQrDialogLocal = false }
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "সদস্য হবার সুবিধা সমূহ",
            color = GreenPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        val benefits = listOf<Triple<ImageVector, String, String>>(
            Triple(Icons.Default.Star, "সর্বনিম্ন মূল্যের গ্যারান্টি", "অন্য যেকোনো বাজারের থেকে কম খরচে বাজার করতে পারবেন।"),
            Triple(Icons.Default.Favorite, "PASWO সোশাল ডিল", "আপনার পরিবারের আতা, চাল বিতরণ স্কিমে অগ্রাধিকার লাভ।"),
            Triple(Icons.Default.Star, "১০% পর্যন্ত ইন্সট্যান্ট ক্যাশব্যাক", "প্রতিটি অর্ডারে লয়ালটি ক্যাশব্যাক সঞ্চয় ও উত্তোলন।")
        )

        for (benefit in benefits) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(1.dp)
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(GoldPrimary.copy(alpha = 0.15f))
                    ) {
                        Icon(benefit.first, "ben", tint = GreenMedium, modifier = Modifier.size(14.dp).align(Alignment.Center))
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(benefit.second, color = DarkGray, fontWeight = FontWeight.Bold, fontSize = 9.sp)
                        Text(benefit.third, color = Color.Gray, fontSize = 7.sp, lineHeight = 10.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ScreenDashboardMirror(viewModel: MainViewModel) {
    val profile by viewModel.memberProfile.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.weight(1f).padding(4.dp)) {
                StatPanelCard("মোট সাশ্রয়", "৳ ${profile.savingsAmount.toInt()}", Icons.Default.Star, GoldPrimary)
            }
            Box(modifier = Modifier.weight(1f).padding(4.dp)) {
                StatPanelCard("অর্ডার সমূহ", "${profile.ordersCount} টি", Icons.Default.ShoppingCart, GreenPrimary)
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.weight(1f).padding(4.dp)) {
                StatPanelCard("রেফারাল কাউন্ট", "${profile.referralCount} জন", Icons.Default.Send, AlertInfo)
            }
            Box(modifier = Modifier.weight(1f).padding(4.dp)) {
                StatPanelCard("ক্যাশব্যাক লাভ", "৳ ${profile.cashbackAmount.toInt()}", Icons.Default.ShoppingCart, AlertSuccess)
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        PerformanceSavingsChart(modifier = Modifier.padding(4.dp))
    }
}

@Composable
fun StatPanelCard(title: String, value: String, icon: ImageVector, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(title, color = Color.Gray, fontSize = 8.sp)
                Text(value, color = DarkGray, fontWeight = FontWeight.Black, fontSize = 13.sp)
            }
            Icon(icon, "statIdx", tint = color, modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun ScreenPASWOMirror(viewModel: MainViewModel) {
    val profile by viewModel.memberProfile.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(10.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = GreenPrimary),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, GoldPrimary)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "PASWO দারিদ্র বিমোচন ও খাদ্য কল্যাণ",
                    color = GoldPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "কুরিগ্রাম এলাকার দারিদ্রপীড়িত পরিবারের চাল, ডাল, তেলসহ দৈনন্দিন বাজার সামগ্রী নামমাত্র খরচে বিতরণ সহায়তা কর্মসূচি।",
                    color = PureWhite,
                    fontSize = 9.sp,
                    lineHeight = 13.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "ব্যালেন্স ও তালিকা সামগ্রী (৳)",
            color = GreenPrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 11.sp,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        val listItems = viewModel.products.value.filter { it.category == "PASWO" }
        for (prod in listItems) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 3.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        ProductImagePlaceholder(prod.imageType, modifier = Modifier.size(24.dp).clip(RoundedCornerShape(4.dp)))
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(prod.name, color = DarkGray, fontWeight = FontWeight.Bold, fontSize = 9.sp)
                            Text("বাজার দর: ৳${prod.regularPrice.toInt()}/কেজি", textDecoration = TextDecoration.LineThrough, color = Color.Gray, fontSize = 7.sp)
                        }
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("PASWO মূল্য", color = Color.Gray, fontSize = 7.sp)
                        Text("৳${prod.memberPrice.toInt()}", color = AlertSuccess, fontWeight = FontWeight.Black, fontSize = 11.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (profile.isPaswoApplied) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(AlertSuccess.copy(alpha = 0.15f))
                    .border(1.dp, AlertSuccess, RoundedCornerShape(8.dp))
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "✓ আপনার আবেদন প্রক্রিয়াধীন রয়েছে।",
                    color = AlertSuccess,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        } else {
            Button(
                onClick = { viewModel.applyForPaswo() },
                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("PASWO কার্ডের জন্য আবেদন করুন", color = GreenPrimaryDark, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ScreenNoticeMirror(viewModel: MainViewModel) {
    val notices = listOf(
        Triple("জরুরী নোটিশ", "চাল বিতরণ কার্যক্রম", "আগামী ১২ই জুন সকাল ১০টা হতে রাজিবপুর ইউনিয়নে আমাদের গোল্ড সদস্যদের মাঝে কার্ড প্রতি ১০কেজি চাল বিতরণ শুরু হবে। অনুগ্রহপূর্বক মেম্বার আইডি সাথে রাখুন।"),
        Triple("আপডেট", "রেফারাল বোনাস বৃদ্ধি!", "আপনার প্রতিবেশিকে মেম্বারশিপে আমন্ত্রন জানালে ক্যাশব্যাক বোনাস বৃদ্ধি করে ৳১০০ করা হয়েছে। রেফার করুন আজই!"),
        Triple("নোটিশ", "শাকসবজির বিশেষ ডিসকাউন্ট", "গোল্ড কার্ডের মাধ্যমে প্রতিটি কেনাকাটায় কুরিগ্রাম অঞ্চলের সতেজ তরতাজা সবজিতে অতিরিক্ত ৫% ক্যাশব্যাক ছাড়।")
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        for (notice in notices) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                shape = RoundedCornerShape(8.dp),
                border = if (notice.first == "জরুরী নোটিশ") BorderStroke(1.dp, GoldSatin) else null,
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (notice.first == "জরুরী নোটিশ") GoldPrimary else GreenPrimary.copy(alpha = 0.15f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = notice.first,
                                color = if (notice.first == "জরুরী নোটিশ") GreenPrimaryDark else GreenPrimary,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(text = "২০২৬-০৬-১০", color = Color.Gray, fontSize = 7.sp)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = notice.second, color = DarkGray, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = notice.third, color = DarkGray.copy(alpha = 0.8f), fontSize = 9.sp, lineHeight = 13.sp)
                }
            }
        }
    }
}

@Composable
fun ScreenShopMirror(viewModel: MainViewModel) {
    val selectedCat by viewModel.selectedCategory.collectAsState()
    val searchVal by viewModel.searchQuery.collectAsState()

    val fileteredProds = viewModel.products.value.filter {
        (selectedCat == "All" || it.category == selectedCat) &&
        (it.name.contains(searchVal) || it.category.contains(searchVal))
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val categories = listOf("All", "PASWO", "মুদি", "ডেইরি", "পরিষ্কারক")
            for (cat in categories) {
                val isSelected = (selectedCat == cat)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(if (isSelected) GreenPrimary else PureWhite)
                        .border(1.dp, if (isSelected) GreenPrimary else Color.LightGray, RoundedCornerShape(14.dp))
                        .clickable { viewModel.selectCategory(cat) }
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = when (cat) {
                            "All" -> "সব পণ্য"
                            "PASWO" -> "PASWO অফার"
                            else -> cat
                        },
                        color = if (isSelected) PureWhite else DarkGray,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 8.dp)
        ) {
            for (i in fileteredProds.indices step 2) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        ProductShopCard(fileteredProds[i], viewModel)
                    }
                    if (i + 1 < fileteredProds.size) {
                        Box(modifier = Modifier.weight(1f)) {
                            ProductShopCard(fileteredProds[i + 1], viewModel)
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
fun ProductShopCard(prod: ProductEntity, viewModel: MainViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 6.dp),
        colors = CardDefaults.cardColors(containerColor = PureWhite),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            ProductImagePlaceholder(
                productType = prod.imageType,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            )
            Column(modifier = Modifier.padding(6.dp)) {
                Text(
                    text = prod.name,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    color = DarkGray
                )
                Text(
                    text = "সাধারণ মূল্য: ৳${prod.regularPrice.toInt()}",
                    fontSize = 7.5.sp,
                    textDecoration = TextDecoration.LineThrough,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(2.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("সদস্য মূল্য", color = GoldSatin, fontSize = 7.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = "৳ ${prod.memberPrice.toInt()}/${prod.unit}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Black,
                            color = GreenPrimary
                        )
                    }
                    IconButton(
                        onClick = { viewModel.addProductToCart(prod) },
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(GoldPrimary)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add Cart",
                            tint = GreenPrimaryDark,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
    }
}

// --------------------------------------------------------------------
// OTP REGISTRATION & AUTHENTICATION GATEWAY
// --------------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpLoginScreen(viewModel: MainViewModel) {
    var phoneInput by remember { mutableStateOf("") }
    var nameInput by remember { mutableStateOf("") }
    var otpInput by remember { mutableStateOf("") }
    var isRegisterMode by remember { mutableStateOf(false) }

    val smsOtpSent by viewModel.smsOtpSent.collectAsState()
    val isSyncing by viewModel.isCloudSyncing.collectAsState()
    val selectedUnion by viewModel.selectedUnion.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GreenPrimaryDark, GreenPrimary, GreenMedium)
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Decorative background geometric shapes
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = GoldPrimary.copy(alpha = 0.08f),
                radius = size.width * 0.4f,
                center = Offset(size.width * 0.1f, size.height * 0.2f)
            )
            drawCircle(
                color = GoldPrimary.copy(alpha = 0.05f),
                radius = size.width * 0.6f,
                center = Offset(size.width * 0.9f, size.height * 0.8f)
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // App Logo Header
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(GreenPrimary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = "Logo",
                        tint = GreenPrimary,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "ঘরোয়া বাজার ডট কম",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        color = GreenPrimaryDark
                    )
                    Text(
                        text = "স্বল্প মূল্যে পরিবারের কুরিগ্রাম জোনাল বাজার",
                        fontSize = 11.sp,
                        color = GoldSatin,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

                if (isSyncing) {
                    CircularProgressIndicator(color = GreenPrimary, modifier = Modifier.size(30.dp))
                    Text(
                        "ক্লাউড সিকিউরিটি কি হ্যান্ডশেক হচ্ছে...",
                        color = Color.Gray,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    )
                } else if (!smsOtpSent) {
                    // Enter credentials screen
                    Text(
                        text = if (isRegisterMode) "নতুন মেম্বারশিপ অ্যাকাউন্ট তৈরি" else "আপনার মেম্বার অ্যাকাউন্টে সাইন-ইন",
                        fontWeight = FontWeight.Bold,
                        color = DarkGray,
                        fontSize = 13.sp
                    )

                    OutlinedTextField(
                        value = phoneInput,
                        onValueChange = { if (it.length <= 11) phoneInput = it },
                        label = { Text("মোবাইল নম্বর লিখুন", fontSize = 11.sp) },
                        placeholder = { Text("01XXXXXXXXX") },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GreenPrimary,
                            unfocusedBorderColor = Color.LightGray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (isRegisterMode) {
                        OutlinedTextField(
                            value = nameInput,
                            onValueChange = { nameInput = it },
                            label = { Text("আপনার সম্পূর্ণ নাম লিখুন", fontSize = 11.sp) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GreenPrimary,
                                unfocusedBorderColor = Color.LightGray
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )

                        // Union selection dropdown
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text("আপনার এলাকা (Region Union) নির্বাচন করুন:", fontSize = 9.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                val unions = listOf("Rajibpur", "Mohanganj", "Kodimri")
                                for (u in unions) {
                                    val isSel = selectedUnion == u
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(if (isSel) GoldPrimary else LightSurface)
                                            .clickable { viewModel.updateSelectedUnion(u) }
                                            .padding(vertical = 6.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = if(u == "Rajibpur") "চর রাজিবপুর" else if(u == "Mohanganj") "মোহনগঞ্জ" else "কোডিমরি",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSel) GreenPrimaryDark else DarkGray
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Button(
                        onClick = {
                            viewModel.requestOtp(phoneInput, nameInput, isRegisterMode)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("সিকিউর ওটিপি (OTP) পাঠান", color = PureWhite, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }

                    Text(
                        text = if (isRegisterMode) "অলরেডি অ্যাকাউন্ট আছে? লগইন করুন" else "নতুন মেম্বার? রেজিস্ট্রেশন করুন",
                        color = GreenPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .clickable { isRegisterMode = !isRegisterMode }
                            .padding(top = 4.dp),
                        textDecoration = TextDecoration.Underline
                    )
                } else {
                    // OTP Verification inputs screen
                    Text(
                        text = "ওটিপি (SMS OTP) কোড নিশ্চিত করুন",
                        fontWeight = FontWeight.Bold,
                        color = DarkGray,
                        fontSize = 13.sp
                    )

                    Text(
                        text = "নম্বর: $phoneInput এ প্রেরিত ৪-সংখ্যার কোডটি প্রবেশ করান। ডেমো স্যান্ডবক্স সুবিধার্থে '1234' কোডটিও সক্রিয় রয়েছে।",
                        fontSize = 9.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        lineHeight = 13.sp
                    )

                    OutlinedTextField(
                        value = otpInput,
                        onValueChange = { if (it.length <= 4) otpInput = it },
                        placeholder = { Text("Enter 4-Digit OTP", textAlign = TextAlign.Center) },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = GreenPrimary,
                            unfocusedBorderColor = Color.LightGray
                        ),
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )

                    Button(
                        onClick = {
                            viewModel.verifyOtp(otpInput)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("যাচাই এবং প্রবেশ করুন", color = GreenPrimaryDark, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }

                    Text(
                        text = "নম্বর পরিবর্তন করুন",
                        color = Color.Gray,
                        fontSize = 10.sp,
                        modifier = Modifier
                            .clickable { viewModel.logOut() }
                            .padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

// --------------------------------------------------------------------
// DYNAMIC QR CODE DISPLAY DIALOG (PASWO VERIFIED COMPLIANCE)
// --------------------------------------------------------------------
@Composable
fun SimulatedQrDialog(
    memberId: String,
    memberName: String,
    onClose: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onClose,
        confirmButton = {
            Button(
                onClick = onClose,
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
            ) {
                Text("বন্ধ করুন", color = PureWhite)
            }
        },
        title = {
            Text(
                "ডিজিটাল গোল্ড মেম্বারশিপ QR",
                color = GreenPrimaryDark,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "$memberName ($memberId)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGray
                )

                // High-fidelity rendered QR Code
                Card(
                    modifier = Modifier
                        .size(160.dp)
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    border = BorderStroke(1.dp, Color.LightGray)
                ) {
                    Canvas(modifier = Modifier.fillMaxSize().padding(10.dp)) {
                        // QR corners
                        drawRect(Color.Black, size = androidx.compose.ui.geometry.Size(35f, 35f), topLeft = Offset(0f, 0f))
                        drawRect(Color.White, size = androidx.compose.ui.geometry.Size(19f, 19f), topLeft = Offset(8f, 8f))
                        drawRect(Color.Black, size = androidx.compose.ui.geometry.Size(11f, 11f), topLeft = Offset(12f, 12f))

                        drawRect(Color.Black, size = androidx.compose.ui.geometry.Size(35f, 35f), topLeft = Offset(size.width - 35f, 0f))
                        drawRect(Color.White, size = androidx.compose.ui.geometry.Size(19f, 19f), topLeft = Offset(size.width - 27f, 8f))
                        drawRect(Color.Black, size = androidx.compose.ui.geometry.Size(11f, 11f), topLeft = Offset(size.width - 23f, 12f))

                        drawRect(Color.Black, size = androidx.compose.ui.geometry.Size(35f, 35f), topLeft = Offset(0f, size.height - 35f))
                        drawRect(Color.White, size = androidx.compose.ui.geometry.Size(19f, 19f), topLeft = Offset(8f, size.height - 27f))
                        drawRect(Color.Black, size = androidx.compose.ui.geometry.Size(11f, 11f), topLeft = Offset(12f, size.height - 23f))

                        // Random seed QR cryptographic lines
                        val r = java.util.Random(memberId.hashCode().toLong())
                        for (i in 0..18) {
                            for (j in 0..18) {
                                if (i < 5 && j < 5) continue
                                if (i > 13 && j < 5) continue
                                if (i < 5 && j > 13) continue
                                if (r.nextBoolean()) {
                                    val blockW = size.width / 19f
                                    val blockH = size.height / 19f
                                    drawRect(
                                        Color.Black,
                                        size = androidx.compose.ui.geometry.Size(blockW + 1f, blockH + 1f),
                                        topLeft = Offset(i * blockW, j * blockH)
                                    )
                                }
                            }
                        }
                    }
                }

                Text(
                    text = " গোল্ড মেম্বারশিপ: সক্রিয় ও ভেরিফাইড (REAL-TIME CLOUD SYNC)",
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = GreenPrimary
                )

                Text(
                    text = "কুড়িগ্রাম রাজিবপুর অফলাইন ডিপোতে এই কিউআরটি স্ক্যান করে আপনার ভর্তুকি রেশন ও স্পেশাল মেম্বার ডিসকাউন্ট বুঝে নিন।",
                    fontSize = 8.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    lineHeight = 12.sp
                )
            }
        }
    )
}

// --------------------------------------------------------------------
// COOPERATIVE GEMINI AI GROCERY LIST GENERATOR DIALOG
// --------------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AiShoppingAssistantDialog(
    viewModel: MainViewModel,
    onClose: () -> Unit
) {
    var familySize by remember { mutableStateOf(4) }
    var budgetInput by remember { mutableStateOf("5000") }
    var activePreference by remember { mutableStateOf("সাশ্রয়ী রেশন") } // সাশ্রয়ী রেশন, ব্যালেন্সড, স্পেশাল প্রিমিয়াম

    val aiResponse by viewModel.aiGroceryResponse.collectAsState()
    val isLoading by viewModel.isAiLoading.collectAsState()

    AlertDialog(
        onDismissRequest = onClose,
        properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .fillMaxHeight(0.85f),
        confirmButton = {
            Button(
                onClick = onClose,
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
            ) {
                Text("বন্ধ করুন", color = PureWhite)
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(GoldPrimary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Star, "ai", tint = GoldSatin, modifier = Modifier.size(18.dp))
                    }
                    Column {
                        Text(
                            text = "ঘরোয়া বাজার AI সহকারী",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Black,
                            color = GreenPrimaryDark
                        )
                        Text(
                            text = "পরিবারের সাইজ ও বাজেট অনুযায়ী অপ্টিমাইজড এআই তালিকা",
                            fontSize = 8.sp,
                            color = Color.Gray
                        )
                    }
                }

                HorizontalDivider()

                Card(
                    colors = CardDefaults.cardColors(containerColor = LightSurface),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        // Selector 1: Family size
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("👪 পরিবারের সদস্য সংখ্যা:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DarkGray)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(
                                    onClick = { if (familySize > 1) familySize-- },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(Icons.Default.KeyboardArrowDown, "minus", tint = GreenPrimary)
                                }
                                Text("$familySize জন", fontSize = 12.sp, fontWeight = FontWeight.Black, color = GreenPrimary, modifier = Modifier.padding(horizontal = 6.dp))
                                IconButton(
                                    onClick = { if (familySize < 12) familySize++ },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(Icons.Default.Add, "add", tint = GreenPrimary)
                                }
                            }
                        }

                        // Selector 2: Budget
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("৳ সর্বোচ্চ মাসিক বাজেট (টাকা):", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DarkGray)
                            OutlinedTextField(
                                value = budgetInput,
                                onValueChange = { budgetInput = it },
                                singleLine = true,
                                modifier = Modifier
                                    .width(90.dp)
                                    .height(48.dp),
                                textStyle = LocalTextStyle.current.copy(fontSize = 11.sp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = GreenPrimary,
                                    unfocusedBorderColor = Color.LightGray
                                )
                            )
                        }

                        // Selector 3: Style
                        Column {
                            Text("🔄 জীবনযাত্রার ধরণ নির্বাচন:", fontSize = 9.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                val styles = listOf("সাশ্রয়ী রেশন", "সাধারণ ব্যালেন্সড", "প্রিমিয়াম চয়েস")
                                for (style in styles) {
                                    val isSel = activePreference == style
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(if (isSel) GoldPrimary else PureWhite)
                                            .clickable { activePreference = style }
                                            .padding(vertical = 6.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = style,
                                            fontSize = 8.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSel) GreenPrimaryDark else DarkGray
                                        )
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = {
                                val budget = budgetInput.toDoubleOrNull() ?: 5000.0
                                viewModel.generateAiGroceryList(familySize, budget, activePreference)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                            shape = RoundedCornerShape(8.dp),
                            enabled = !isLoading,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(color = PureWhite, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("তালিকা জেনারেট হচ্ছে...", color = PureWhite, fontSize = 10.sp)
                            } else {
                                Icon(Icons.Default.Star, "generate", tint = GoldPrimary, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("AI মাসিক রেকমেন্ডেশন তালিকা বানান", color = PureWhite, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // AI Response card panel
                if (aiResponse.isNotEmpty()) {
                    Text("🤖 জেনারেটেড তালিকা এবং বিশ্লেষণ:", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
                        border = BorderStroke(1.dp, GreenMedium.copy(alpha = 0.2f))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = aiResponse,
                                fontSize = 10.sp,
                                color = DarkGray,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }
        }
    )
}
