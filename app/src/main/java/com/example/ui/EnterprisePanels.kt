package com.example.ui

import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CartItem
import com.example.data.OrderEntity
import com.example.data.ProductEntity
import com.example.data.PaswoApplicationEntity
import com.example.ui.theme.*
import androidx.compose.ui.geometry.Offset
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// --------------------------------------------------------------------
// BKASH / NAGAD PAYMENT SIMULATOR MODAL DIALOG
// --------------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentGatewayModal(
    viewModel: MainViewModel,
    onClose: () -> Unit
) {
    val step by viewModel.checkoutStep.collectAsState()
    val channel by viewModel.selectedPaymentChannel.collectAsState()
    val walletNo by viewModel.walletNumber.collectAsState()
    val otpVal by viewModel.paymentOtp.collectAsState()
    val pinVal by viewModel.paymentPin.collectAsState()
    val totalAmt by viewModel.checkoutTotal.collectAsState()
    val savedAmt by viewModel.checkoutSavings.collectAsState()
    val invoiceId by viewModel.activeInvoiceId.collectAsState()

    val primaryBrandColor = if (channel == "bKash") Color(0xFFE2125D) else Color(0xFFF15A22)
    val brandLightColor = if (channel == "bKash") Color(0xFFFFE6EF) else Color(0xFFFFF1EB)
    val brandName = if (channel == "bKash") "বিকাশ পেমেন্ট গেটওয়ে" else "নগদ পেমেন্ট গেটওয়ে"

    AlertDialog(
        onDismissRequest = { /* Force complete transaction or cancel */ },
        properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .wrapContentHeight()
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            border = BorderStroke(2.dp, primaryBrandColor),
            modifier = Modifier.padding(8.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Header Panel
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(primaryBrandColor)
                        .padding(14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = brandName,
                            color = PureWhite,
                            fontWeight = FontWeight.Black,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Secure Financial Compliance Gateway",
                            color = PureWhite.copy(alpha = 0.8f),
                            fontSize = 8.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                }

                // Billing Info Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(brandLightColor)
                        .padding(horizontal = 14.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("অনলাইন মার্চেন্ট: ঘরোয়া বাজার", fontSize = 10.sp, color = DarkGray, fontWeight = FontWeight.Bold)
                    Text("মোট প্রদেয়: ৳${totalAmt.toInt()}", fontSize = 11.sp, color = primaryBrandColor, fontWeight = FontWeight.Black)
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    AnimatedContent(
                        targetState = step,
                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                        label = "payment_gate_transition"
                    ) { currStep ->
                        when (currStep) {
                            "WALLET_INPUT" -> {
                                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                    Icon(
                                        Icons.Default.Phone,
                                        "phone",
                                        tint = primaryBrandColor,
                                        modifier = Modifier.size(36.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        "আপনার $channel পার্সোনাল ওয়ালেট নম্বর দিন",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = DarkGray,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    OutlinedTextField(
                                        value = walletNo,
                                        onValueChange = { if(it.length <= 11) viewModel.updateWalletNumber(it) },
                                        placeholder = { Text("01XXXXXXXXX", fontSize = 13.sp) },
                                        singleLine = true,
                                        shape = RoundedCornerShape(8.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = primaryBrandColor,
                                            unfocusedBorderColor = Color.LightGray
                                        ),
                                        modifier = Modifier.fillMaxWidth(0.9f)
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        "যাচাইকরণের জন্য একটি ওটিপি (OTP) ওয়ান টাইম পাসকোড এসএমএস করা হবে।",
                                        fontSize = 8.sp,
                                        color = Color.Gray,
                                        textAlign = TextAlign.Center,
                                        lineHeight = 12.sp
                                    )
                                }
                            }
                            "OTP_INPUT" -> {
                                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                    Icon(
                                        Icons.Default.Lock,
                                        "lock",
                                        tint = primaryBrandColor,
                                        modifier = Modifier.size(36.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        "এসএমএস ওটিপি (Verification OTP) দিন",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = DarkGray,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        "নম্বর: $walletNo এ প্রেরিত কোডটি লিখুন",
                                        fontSize = 9.sp,
                                        color = Color.Gray
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    OutlinedTextField(
                                        value = otpVal,
                                        onValueChange = { if(it.length <= 6) viewModel.updatePaymentOtp(it) },
                                        placeholder = { Text("6-Digit OTP Code", fontSize = 13.sp, textAlign = TextAlign.Center) },
                                        singleLine = true,
                                        shape = RoundedCornerShape(8.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = primaryBrandColor,
                                            unfocusedBorderColor = Color.LightGray
                                        ),
                                        modifier = Modifier.fillMaxWidth(0.8f)
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        "কোড না পেলে পুনরায় অনুরোধ করতে ৬০ সেকেন্ড অপেক্ষা করুন",
                                        fontSize = 8.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                            "PIN_INPUT" -> {
                                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                    Icon(
                                        Icons.Default.Star,
                                        "star",
                                        tint = primaryBrandColor,
                                        modifier = Modifier.size(36.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        "আপনার ৫-ডিজিট গোপন পিন নম্বর দিন",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = DarkGray,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        "আপনার নিরাপত্তা সম্পূর্ণ সুরক্ষিত ও এনক্রিপ্টেড।",
                                        fontSize = 8.sp,
                                        color = Color.Gray
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    OutlinedTextField(
                                        value = pinVal,
                                        onValueChange = { if(it.length <= 5) viewModel.updatePaymentPin(it) },
                                        placeholder = { Text("• • • • •", fontSize = 18.sp, textAlign = TextAlign.Center) },
                                        singleLine = true,
                                        shape = RoundedCornerShape(8.dp),
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedBorderColor = primaryBrandColor,
                                            unfocusedBorderColor = Color.LightGray
                                        ),
                                        modifier = Modifier.fillMaxWidth(0.7f)
                                    )
                                }
                            }
                            "PROCESSING" -> {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                    modifier = Modifier.fillMaxWidth().padding(14.dp)
                                ) {
                                    CircularProgressIndicator(color = primaryBrandColor, modifier = Modifier.size(42.dp))
                                    Spacer(modifier = Modifier.height(14.dp))
                                    Text(
                                        "পেমেন্ট ও অর্ডার ভেরিফিকেশন এনালিটিক্স যাচাই করা হচ্ছে...",
                                        fontSize = 11.sp,
                                        color = DarkGray,
                                        fontWeight = FontWeight.Medium,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        "দোহাই করে ব্রাউজার রিফ্রেশ করবেন না!",
                                        fontSize = 8.sp,
                                        color = Color.Red,
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                }
                            }
                            "RECEIPT" -> {
                                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        "success",
                                        tint = AlertSuccess,
                                        modifier = Modifier.size(48.dp)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        "আলহামদুলিল্লাহ! কেনাকাটা সম্পূর্ণ হয়েছে",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp,
                                        color = GreenPrimaryDark,
                                        textAlign = TextAlign.Center
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(LightSurface)
                                            .padding(10.dp)
                                    ) {
                                        Column(horizontalAlignment = Alignment.Start) {
                                            Text("ফাইন্যান্স ইনভয়েস: $invoiceId", fontSize = 9.sp, color = DarkGray, fontWeight = FontWeight.Bold)
                                            Text("প্রদত্ত পেমেন্ট মোড: $channel", fontSize = 9.sp, color = Color.Gray)
                                            Text("ওয়ালেট অ্যাকাউন্ট: $walletNo", fontSize = 9.sp, color = Color.Gray)
                                            HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = Color.LightGray)
                                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                Text("পরিশোধিত মূল্য:", fontSize = 9.sp, color = DarkGray, fontWeight = FontWeight.Bold)
                                                Text("৳ ${totalAmt.toInt()}", fontSize = 10.sp, color = GreenPrimary, fontWeight = FontWeight.Black)
                                            }
                                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                Text("সদস্যতা সাশ্রয় লাভ:", fontSize = 9.sp, color = GoldSatin, fontWeight = FontWeight.Bold)
                                                Text("৳ ${savedAmt.toInt()}", fontSize = 9.sp, color = GoldSatin, fontWeight = FontWeight.Black)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))

                // Footer Actions
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (step != "RECEIPT" && step != "PROCESSING") {
                        TextButton(onClick = { onClose() }) {
                            Text("বাতিল করুন", color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = { viewModel.advanceCheckoutStep() },
                            colors = ButtonDefaults.buttonColors(containerColor = primaryBrandColor),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("পরবর্তী ধাপ", color = PureWhite, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    } else if (step == "RECEIPT") {
                        Spacer(modifier = Modifier.width(1.dp))
                        Button(
                            onClick = {
                                onClose()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("বাজার থলি বন্ধ করুন", color = PureWhite, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    } else {
                        // processing space
                        Spacer(modifier = Modifier.height(1.dp))
                    }
                }
            }
        }
    }
}

// --------------------------------------------------------------------
// PASWO REGISTRATION FORM MODAL
// --------------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaswoFormModal(
    viewModel: MainViewModel,
    onClose: () -> Unit
) {
    var familySize by remember { mutableStateOf(4) }
    var income by remember { mutableStateOf(8000.0) }
    var unionSelected by remember { mutableStateOf("১নং রাজিবপুর সদর") }

    val unionOptions = listOf(
        "১নং রাজিবপুর সদর",
        "২নং মোহনগঞ্জ",
        "৩নং কোদালকাটি",
        "৪নং নয়ারহাট",
        "চর রাজিবপুর"
    )

    AlertDialog(
        onDismissRequest = onClose,
        properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = true)
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            border = BorderStroke(1.5.dp, GoldPrimary),
            modifier = Modifier.padding(10.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "PASWO খাদ্য কল্যাণ ফর্ম",
                        fontWeight = FontWeight.Bold,
                        color = GreenPrimaryDark,
                        fontSize = 14.sp
                    )
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.Close, "close", tint = Color.Gray)
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))

                Text(
                    "ভর্তুকি আওতাভুক্ত কম-মূল্যের রেশন সামগ্রীর বিশেষ রেশন কার্ড লাভ করতে নিম্নের তথ্যসমূহ যত্নসহকারে পূরণ করুন।",
                    fontSize = 8.sp,
                    color = Color.Gray,
                    lineHeight = 12.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Union selection dropdown
                Text("আপনার বাসস্থল ইউনিয়ন বাছাই করুন:", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = DarkGray)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    for (option in unionOptions) {
                        val isSel = option == unionSelected
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSel) GreenMedium else LightSurface)
                                .clickable { unionSelected = option }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                .border(1.dp, if (isSel) GoldPrimary else Color.LightGray, RoundedCornerShape(8.dp))
                        ) {
                            Text(option, fontSize = 8.sp, color = if (isSel) PureWhite else DarkGray)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Family Size slider
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("পরিবারের সদস্য সংখ্যা:", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = DarkGray)
                    Text("$familySize জন", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GreenPrimary)
                }
                Slider(
                    value = familySize.toFloat(),
                    onValueChange = { familySize = it.toInt() },
                    valueRange = 1f..12f,
                    steps = 10,
                    colors = SliderDefaults.colors(thumbColor = GreenPrimary, activeTrackColor = GreenMedium)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Income slider
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("মাসিক যৌথ পরিবার আয়:", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = DarkGray)
                    Text("৳ ${income.toInt()}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = AlertSuccess)
                }
                Slider(
                    value = income.toFloat(),
                    onValueChange = { income = it.toDouble() },
                    valueRange = 2000f..25000f,
                    steps = 22,
                    colors = SliderDefaults.colors(thumbColor = AlertSuccess, activeTrackColor = GreenMedium)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = {
                        viewModel.submitPaswoApplication(familySize, income, unionSelected)
                        onClose()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("আবেদনপত্র জমা দিন", color = GreenPrimaryDark, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                }
            }
        }
    }
}

// --------------------------------------------------------------------
// NOTIFICATION CENTER SCREEN
// --------------------------------------------------------------------
@Composable
fun NotificationCenterScreen(viewModel: MainViewModel) {
    val notifs by viewModel.notifications.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "বার্তা ও পুশ নোটিফিকেশন সেন্টার",
                fontWeight = FontWeight.Bold,
                color = GreenPrimaryDark,
                fontSize = 13.sp
            )
            if (notifs.isNotEmpty()) {
                TextButton(onClick = { viewModel.clearNotifications() }) {
                    Text("সব পরিষ্কার করুন", color = Color.Red, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

        if (notifs.isEmpty()) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Notifications, "empty", tint = Color.LightGray, modifier = Modifier.size(48.dp))
                    Text("কোনো নতুন নোটিফিকেশন বার্তা নেই!", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.padding(top = 6.dp))
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (notif in notifs) {
                    val isErp = notif.title.contains("ERP") || notif.title.contains("স্টক")
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(2.dp),
                        border = if (isErp) BorderStroke(1.dp, Color.Red.copy(alpha = 0.3f)) else null
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        if (isErp) Icons.Default.Warning else Icons.Default.Notifications,
                                        "alert",
                                        tint = if (isErp) Color.Red else GreenMedium,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        notif.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp,
                                        color = if (isErp) Color.Red else DarkGray
                                    )
                                }
                                Text(notif.timestamp, fontSize = 7.5.sp, color = Color.Gray)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                notif.message,
                                fontSize = 9.sp,
                                color = DarkGray.copy(alpha = 0.8f),
                                lineHeight = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

// --------------------------------------------------------------------
// ADMIN PANEL COMPREHENSIVE VIEW
// --------------------------------------------------------------------
@Composable
fun AdminDashboardScreen(viewModel: MainViewModel) {
    var adminActiveTab by remember { mutableStateOf("INVENTORY") } // INVENTORY, ORDERS, PASWO, ERP

    Column(modifier = Modifier.fillMaxSize()) {
        // Admin Tab Bar Navigation (Chips style)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(GreenPrimaryDark)
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 10.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val adminTabs = listOf(
                Pair("INVENTORY", "১. স্টক ইনভেন্টরি"),
                Pair("ORDERS", "২. ডেলিভারি অর্ডারস"),
                Pair("PASWO", "৩. PASWO যাচাই"),
                Pair("ERP", "৪. ERP সিঙ্ক ও লগ")
            )
            for (tab in adminTabs) {
                val isSel = adminActiveTab == tab.first
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSel) GoldPrimary else PureWhite.copy(alpha = 0.12f))
                        .clickable { adminActiveTab = tab.first }
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        tab.second,
                        color = if (isSel) GreenPrimaryDark else PureWhite,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Active admin page selection
        Box(modifier = Modifier.weight(1f).background(LightSurface)) {
            when (adminActiveTab) {
                "INVENTORY" -> AdminInventoryTab(viewModel)
                "ORDERS" -> AdminOrdersTab(viewModel)
                "PASWO" -> AdminPaswoTab(viewModel)
                "ERP" -> AdminErpTab(viewModel)
            }
        }
    }
}

@Composable
fun AdminInventoryTab(viewModel: MainViewModel) {
    val prods by viewModel.products.collectAsState()
    var showAddForm by remember { mutableStateOf(false) }

    // Add Form Fields state
    var pName by remember { mutableStateOf("") }
    var pRegPrice by remember { mutableStateOf("") }
    var pMemPrice by remember { mutableStateOf("") }
    var pPaswoPrice by remember { mutableStateOf("") }
    var pUnit by remember { mutableStateOf("केजी") }
    var pCat by remember { mutableStateOf("মুদি") }
    var pImgType by remember { mutableStateOf("rice") }
    var pStock by remember { mutableStateOf("50") }

    Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("পণ্যের ক্যাটালগ সংশোধন প্যানেল", fontWeight = FontWeight.Bold, color = GreenPrimaryDark, fontSize = 12.sp)
            Button(
                onClick = { showAddForm = !showAddForm },
                colors = ButtonDefaults.buttonColors(containerColor = if(showAddForm) Color.Red else GreenMedium),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                modifier = Modifier.height(28.dp),
                shape = RoundedCornerShape(6.dp)
            ) {
                Icon(if(showAddForm) Icons.Default.Close else Icons.Default.Add, "add", tint = PureWhite, modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(if(showAddForm) "বন্ধ করুন" else "নতুন পণ্য যোগ", color = PureWhite, fontSize = 9.sp)
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))

        if (showAddForm) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .verticalScroll(rememberScrollState()),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                border = BorderStroke(1.dp, GoldSatin),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("নতুন পণ্য ফরম তথ্য", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GoldSatin)
                    
                    OutlinedTextField(
                        value = pName,
                        onValueChange = { pName = it },
                        label = { Text("পণ্যের নাম (বাংলায়)", fontSize = 10.sp) },
                        modifier = Modifier.fillMaxWidth().height(42.dp),
                        textStyle = LocalTextStyle.current.copy(fontSize = 11.sp),
                        singleLine = true
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = pRegPrice,
                            onValueChange = { pRegPrice = it },
                            label = { Text("বাজার মূল্য", fontSize = 8.sp) },
                            modifier = Modifier.weight(1f).height(42.dp),
                            textStyle = LocalTextStyle.current.copy(fontSize = 10.sp),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = pMemPrice,
                            onValueChange = { pMemPrice = it },
                            label = { Text("সদস্য মূল্য", fontSize = 8.sp) },
                            modifier = Modifier.weight(1f).height(42.dp),
                            textStyle = LocalTextStyle.current.copy(fontSize = 10.sp),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = pPaswoPrice,
                            onValueChange = { pPaswoPrice = it },
                            label = { Text("PASWO মূল্য", fontSize = 8.sp) },
                            modifier = Modifier.weight(1f).height(42.dp),
                            textStyle = LocalTextStyle.current.copy(fontSize = 10.sp),
                            singleLine = true
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = pUnit,
                            onValueChange = { pUnit = it },
                            label = { Text("একক (কেজি/বোতল)", fontSize = 8.sp) },
                            modifier = Modifier.weight(1f).height(42.dp),
                            textStyle = LocalTextStyle.current.copy(fontSize = 10.sp),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = pStock,
                            onValueChange = { pStock = it },
                            label = { Text("প্রারম্ভিক স্টক", fontSize = 8.sp) },
                            modifier = Modifier.weight(1f).height(42.dp),
                            textStyle = LocalTextStyle.current.copy(fontSize = 10.sp),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = pCat,
                            onValueChange = { pCat = it },
                            label = { Text("ক্যাটাগরি", fontSize = 8.sp) },
                            modifier = Modifier.weight(1f).height(42.dp),
                            textStyle = LocalTextStyle.current.copy(fontSize = 10.sp),
                            singleLine = true
                        )
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier.fillMaxWidth()) {
                        val types = listOf("rice", "oil", "lentils", "flour", "sugar", "onion")
                        for (type in types) {
                            val isChosen = pImgType == type
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(if (isChosen) GoldPrimary else LightSurface)
                                    .clickable { pImgType = type }
                                    .padding(horizontal = 4.dp, vertical = 2.dp)
                            ) {
                                Text(type, fontSize = 8.sp, color = if (isChosen) GreenPrimaryDark else DarkGray)
                            }
                        }
                    }

                    Button(
                        onClick = {
                            if(pName.isNotEmpty() && pRegPrice.isNotEmpty() && pMemPrice.isNotEmpty()){
                                viewModel.addNewCatalogProduct(
                                    name = pName,
                                    rPrice = pRegPrice.toDoubleOrNull() ?: 100.0,
                                    mPrice = pMemPrice.toDoubleOrNull() ?: 80.0,
                                    pPrice = pPaswoPrice.toDoubleOrNull() ?: 20.0,
                                    unit = pUnit,
                                    category = pCat,
                                    imageType = pImgType,
                                    stock = pStock.toIntOrNull() ?: 50,
                                    supplier = "ঢাকা হোলসেল ডিপো"
                                )
                                showAddForm = false
                                pName = ""
                                pRegPrice = ""
                                pMemPrice = ""
                                pPaswoPrice = ""
                            } else {
                                viewModel.postMessage("সঠিক তথ্য ইনপুট করুন")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("ক্যাটালগে অফিশিয়ালি যোগ করুন", color = GreenPrimaryDark, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Products lists
        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (p in prods) {
                val isLow = p.stock <= p.alertThreshold
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = PureWhite),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(2.dp),
                    border = if (isLow) BorderStroke(1.dp, Color.Red) else null
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ProductImagePlaceholder(p.imageType, modifier = Modifier.size(36.dp).clip(RoundedCornerShape(4.dp)))
                        Spacer(modifier = Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(p.name, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = DarkGray)
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("বাজার: ৳${p.regularPrice.toInt()}", fontSize = 7.5.sp, color = Color.Gray)
                                Text("সদস্য: ৳${p.memberPrice.toInt()}", fontSize = 7.5.sp, color = GreenPrimary, fontWeight = FontWeight.Bold)
                                Text("PASWO: ৳${p.paswoPrice.toInt()}", fontSize = 7.5.sp, color = AlertSuccess, fontWeight = FontWeight.Bold)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("মজুদ স্টক: ", fontSize = 8.sp, color = DarkGray)
                                Text("${p.stock} ${p.unit}", fontSize = 9.sp, color = if(isLow) Color.Red else GreenMedium, fontWeight = FontWeight.Bold)
                                if (isLow) {
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(3.dp))
                                            .background(Color.Red.copy(alpha = 0.15f))
                                            .padding(horizontal = 4.dp, vertical = 1.dp)
                                    ) {
                                        Text("স্টক ফুরিয়েছে!", color = Color.Red, fontSize = 6.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }

                        // Adjusting stocks buttons directly
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            IconButton(
                                onClick = { viewModel.updateProductStockDirect(p.id, (p.stock - 5).coerceAtLeast(0)) },
                                modifier = Modifier.size(24.dp).background(LightSurface, RoundedCornerShape(4.dp))
                            ) {
                                Icon(Icons.Default.Delete, "dec", tint = DarkGray, modifier = Modifier.size(14.dp))
                            }
                            IconButton(
                                onClick = { viewModel.updateProductStockDirect(p.id, p.stock + 20) },
                                modifier = Modifier.size(24.dp).background(GoldPrimary.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                            ) {
                                Icon(Icons.Default.Add, "inc", tint = GreenPrimaryDark, modifier = Modifier.size(14.dp))
                            }
                            IconButton(
                                onClick = { viewModel.deleteProductFromCatalog(p.id) },
                                modifier = Modifier.size(24.dp).background(Color.Red.copy(alpha = 0.1f), RoundedCornerShape(4.dp))
                            ) {
                                Icon(Icons.Default.Delete, "del", tint = Color.Red, modifier = Modifier.size(12.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminOrdersTab(viewModel: MainViewModel) {
    val orderList by viewModel.orders.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {
        Text("গ্রাহকদের অর্ডার ট্র্যাকিং বুক", fontWeight = FontWeight.Bold, color = GreenPrimaryDark, fontSize = 12.sp)
        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

        if (orderList.isEmpty()) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Check, "ch", tint = Color.LightGray, modifier = Modifier.size(42.dp))
                    Text("কোনো অর্ডার পেন্ডিং বা বুকড হয়নি!", fontSize = 10.sp, color = Color.Gray)
                }
            }
        } else {
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (ord in orderList) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("অর্ডার আইডি: ${ord.orderId}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = GreenPrimaryDark)
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(
                                            when (ord.status) {
                                                "PENDING" -> Color.LightGray
                                                "DISPATCHED" -> GoldPrimary.copy(alpha = 0.2f)
                                                "ON_THE_WAY" -> AlertInfo.copy(alpha = 0.15f)
                                                "DELIVERED" -> AlertSuccess.copy(alpha = 0.15f)
                                                else -> Color.LightGray
                                            }
                                        )
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = when(ord.status) {
                                            "PENDING" -> "প্রক্রিয়াধীন"
                                            "DISPATCHED" -> "ডিপো ডিসপ্যাচ"
                                            "ON_THE_WAY" -> "কুরিয়ার পথে"
                                            "DELIVERED" -> "ডেলিভারি সম্পূর্ণ"
                                            else -> ord.status
                                        },
                                        color = when(ord.status) {
                                            "PENDING" -> DarkGray
                                            "DISPATCHED" -> GreenPrimaryDark
                                            "ON_THE_WAY" -> AlertInfo
                                            "DELIVERED" -> AlertSuccess
                                            else -> DarkGray
                                        },
                                        fontSize = 7.5.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text("গ্রাহক নাম: ${ord.customerName}", fontSize = 9.sp, color = DarkGray, fontWeight = FontWeight.Bold)
                            Text("বাজার প্যাকস: ${ord.itemsSummary}", fontSize = 9.sp, color = DarkGray)
                            Text("ঠিকানা: ${ord.address}", fontSize = 8.sp, color = Color.Gray)

                            Row(
                                modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("টোটাল: ৳${ord.totalAmount.toInt()}", fontSize = 10.sp, color = GreenPrimary, fontWeight = FontWeight.Black)
                                    Text("গেটওয়ে: ${ord.paymentChannel} (TxId: ${ord.txId.take(12)})", fontSize = 7.5.sp, color = Color.Gray)
                                }

                                // Interactive Dispatch Trigger Buttons
                                if (ord.status == "PENDING") {
                                    Button(
                                        onClick = { viewModel.setOrderStatus(ord.orderId, "DISPATCHED") },
                                        colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                        shape = RoundedCornerShape(4.dp),
                                        modifier = Modifier.height(26.dp)
                                    ) {
                                        Text("ডিপো ডিসপ্যাচ করুন", color = GreenPrimaryDark, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                                    }
                                } else if (ord.status == "DISPATCHED") {
                                    Button(
                                        onClick = { viewModel.setOrderStatus(ord.orderId, "ON_THE_WAY") },
                                        colors = ButtonDefaults.buttonColors(containerColor = AlertInfo),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                        shape = RoundedCornerShape(4.dp),
                                        modifier = Modifier.height(26.dp)
                                    ) {
                                        Text("ডেলিভারি ম্যানকে দিন", color = PureWhite, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                                    }
                                } else {
                                    Text("ডেলিভারি ওটিপি: ${ord.otp}", fontSize = 9.sp, color = GoldSatin, fontWeight = FontWeight.Bold)
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
fun AdminPaswoTab(viewModel: MainViewModel) {
    val appsList by viewModel.paswoApplications.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {
        Text("PASWO ভর্তুকি রেশন আবেদন যাচাই প্যানেল", fontWeight = FontWeight.Bold, color = GreenPrimaryDark, fontSize = 12.sp)
        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

        if (appsList.isEmpty()) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("কোনো রেশন কার্ড আবেদন জমা পড়েনি!", fontSize = 10.sp, color = Color.Gray)
            }
        } else {
            Column(
                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (app in appsList) {
                    val eligible = app.monthlyIncome < 12000.0 && app.familySize >= 3
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("আবেদনকারী: ${app.name}", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = DarkGray)
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(3.dp))
                                        .background(if(eligible) AlertSuccess.copy(alpha = 0.15f) else Color.LightGray)
                                        .padding(horizontal = 4.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = if(eligible) "স্বয়ংক্রিয় যোগ্য" else "কম যোগ্য (যাচাই প্রয়োজন)",
                                        color = if(eligible) AlertSuccess else Color.Gray,
                                        fontSize = 7.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(3.dp))
                            Text("মোবাইল: ${app.mobile}", fontSize = 8.sp, color = Color.Gray)
                            Text("ইউনিয়ন বাসস্থল: ${app.unionName}", fontSize = 9.sp, color = DarkGray)
                            Text("পরিবার সদস্য: ${app.familySize} জন | মাসিক আয়: ৳${app.monthlyIncome.toInt()}", fontSize = 9.sp, color = DarkGray)
                            Text("স্থতি: ${app.status}", fontSize = 8.sp, color = Color.Gray, fontWeight = FontWeight.Bold)

                            if (app.status == "PENDING") {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(top = 6.dp),
                                    horizontalArrangement = Arrangement.End,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    TextButton(onClick = { viewModel.processPaswoApplicationApproval(app.id, false) }) {
                                        Text("বাতিল", color = Color.Red, fontSize = 9.sp)
                                    }
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Button(
                                        onClick = { viewModel.processPaswoApplicationApproval(app.id, true) },
                                        colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                                        shape = RoundedCornerShape(4.dp),
                                        modifier = Modifier.height(26.dp)
                                    ) {
                                        Text("রেশন মঞ্জুর করুন", color = PureWhite, fontSize = 9.sp, fontWeight = FontWeight.Bold)
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
fun AdminErpTab(viewModel: MainViewModel) {
    val logs by viewModel.erpLogs.collectAsState()
    val isSyncing by viewModel.isErpSyncing.collectAsState()
    val prods by viewModel.products.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("ERP (SAP/Oracle standard API) ইন্টিগ্রেশন ডেক", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = GreenPrimaryDark)
            Button(
                onClick = { viewModel.syncWithBackendERP() },
                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                enabled = !isSyncing,
                shape = RoundedCornerShape(6.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                modifier = Modifier.height(26.dp)
            ) {
                if (isSyncing) {
                    CircularProgressIndicator(color = GreenPrimaryDark, modifier = Modifier.size(10.dp))
                } else {
                    Icon(Icons.Default.Refresh, "sync", tint = GreenPrimaryDark, modifier = Modifier.size(12.dp))
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text("OAUTH সিঙ্ক করুন", color = GreenPrimaryDark, fontSize = 8.sp, fontWeight = FontWeight.Bold)
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

        // System terminal black screen
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f),
            colors = CardDefaults.cardColors(containerColor = Color.Black),
            shape = RoundedCornerShape(6.dp)
        ) {
            val termScroll = rememberScrollState()
            LaunchedEffect(logs.size) {
                termScroll.animateScrollTo(termScroll.maxValue)
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .verticalScroll(termScroll)
            ) {
                for (log in logs) {
                    Text(
                        text = log,
                        color = if (log.contains("WARNING") || log.contains("CRITICAL")) Color.Yellow else Color(0xFF00FF66),
                        fontSize = 8.sp,
                        fontFamily = FontFamily.Monospace,
                        lineHeight = 11.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // --------------------------------------------------------------------
        // INTERACTIVE ERP MANUAL ENTRY VOUCHER DESK
        // --------------------------------------------------------------------
        var selectedProductIndex by remember { mutableStateOf(0) }
        var transactionQtyStr by remember { mutableStateOf("10") }
        var tradePartnerStr by remember { mutableStateOf("Rajibpur Warehouse Co.") }
        var tradeValueStr by remember { mutableStateOf("500") }

        if (prods.isNotEmpty()) {
            val selectedProduct = prods[selectedProductIndex.coerceIn(0, prods.lastIndex)]
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB)),
                border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("📥📤 ম্যানুয়াল ইনভেন্টরি ভাউচার অ্যান্ড অডিট (ERP Local Sync Node)", fontSize = 9.sp, fontWeight = FontWeight.Black, color = GreenPrimaryDark)
                    
                    // Product Picker Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("পণ্য:", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = DarkGray)
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            prods.forEachIndexed { idx, p ->
                                val isSel = selectedProductIndex == idx
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (isSel) GoldPrimary else PureWhite)
                                        .border(1.dp, if (isSel) GoldSatin else Color.LightGray, RoundedCornerShape(6.dp))
                                        .clickable { selectedProductIndex = idx }
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "${p.name} (স্টক: ${p.stock})",
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSel) GreenPrimaryDark else DarkGray
                                    )
                                }
                            }
                        }
                    }

                    // Form Entries
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // Qty
                        OutlinedTextField(
                            value = transactionQtyStr,
                            onValueChange = { transactionQtyStr = it },
                            label = { Text("পরিমাণ (Qty)", fontSize = 8.sp) },
                            textStyle = LocalTextStyle.current.copy(fontSize = 9.sp),
                            singleLine = true,
                            modifier = Modifier
                                .weight(1f)
                                .height(46.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GreenPrimary)
                        )

                        // Trade Partner
                        OutlinedTextField(
                            value = tradePartnerStr,
                            onValueChange = { tradePartnerStr = it },
                            label = { Text("সরবরাহকারী/ক্রেতা", fontSize = 8.sp) },
                            textStyle = LocalTextStyle.current.copy(fontSize = 9.sp),
                            singleLine = true,
                            modifier = Modifier
                                .weight(2f)
                                .height(46.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GreenPrimary)
                        )

                        // Trade Value
                        OutlinedTextField(
                            value = tradeValueStr,
                            onValueChange = { tradeValueStr = it },
                            label = { Text("সর্বমোট মূল্য (৳)", fontSize = 8.sp) },
                            textStyle = LocalTextStyle.current.copy(fontSize = 9.sp),
                            singleLine = true,
                            modifier = Modifier
                                .weight(1.5f)
                                .height(46.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = GreenPrimary)
                        )
                    }

                    // Action Buttons Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                val qty = transactionQtyStr.toIntOrNull() ?: 10
                                val cost = tradeValueStr.toDoubleOrNull() ?: 300.0
                                viewModel.erpStockIn(selectedProduct.id, qty, tradePartnerStr, cost)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(30.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(Icons.Default.KeyboardArrowDown, "stockin", tint = GoldPrimary, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("স্টক-ইন (ক্রয় ভাউচার)", color = PureWhite, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = {
                                val qty = transactionQtyStr.toIntOrNull() ?: 10
                                val revenue = tradeValueStr.toDoubleOrNull() ?: 300.0
                                viewModel.erpStockOut(selectedProduct.id, qty, tradePartnerStr, revenue)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626)),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(30.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(Icons.Default.KeyboardArrowUp, "stockout", tint = PureWhite, modifier = Modifier.size(12.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("স্টক-আউট (বিক্রয় ভাউচার)", color = PureWhite, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        Text("ERP ইনভেন্টরি অটোমেশন চ্যানেল", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = DarkGray)

        // ERP restocking prompts lists
        Column(
            modifier = Modifier
                .weight(0.5f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val filterLows = prods.filter { it.stock <= it.alertThreshold }
            if (filterLows.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().padding(14.dp), contentAlignment = Alignment.Center) {
                    Text("সব পণ্যের পর্যাপ্ত স্টক রয়েছে। ERP অটোমেশন শান্ত আছে।", fontSize = 8.sp, color = Color.Gray)
                }
            } else {
                for (lp in filterLows) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        border = BorderStroke(1.dp, Color.Red),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(lp.name, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = DarkGray)
                                Text("স্টক: ${lp.stock} ${lp.unit} (অ্যালার্ট: ${lp.alertThreshold})", fontSize = 8.sp, color = Color.Red)
                            }
                            Button(
                                onClick = { viewModel.triggerAutomationErpSupplierRestock(lp.id, lp.name) },
                                colors = ButtonDefaults.buttonColors(containerColor = AlertSuccess),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier.height(24.dp)
                            ) {
                                Text("Supplier রিস্টক (+১৫০)", color = PureWhite, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

// --------------------------------------------------------------------
// DISTRIBUTOR / DELIVERY BOARD PERSPECTIVE
// --------------------------------------------------------------------
@Composable
fun DistributorDashboardScreen(viewModel: MainViewModel) {
    val ordersList by viewModel.orders.collectAsState()
    val activeDeliveries = ordersList.filter { it.status == "DISPATCHED" || it.status == "ON_THE_WAY" }

    var selectedOrderForRoute by remember { mutableStateOf<OrderEntity?>(null) }
    var otpVerifyInput by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {
        Text("ডিস্ট্রিবিউটর ও ডেলিভারি কন্ট্রোল ডেক (রাজিবপুর জোন)", fontWeight = FontWeight.Bold, color = GreenPrimaryDark, fontSize = 12.sp)
        Text("কুড়িগ্রাম জেলা স্থানীয় সরবরাহ ও কুরিয়ার ট্র্যাকিং", fontSize = 8.sp, color = Color.Gray)
        
        HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))

        if (activeDeliveries.isEmpty()) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.ShoppingCart, "done", tint = Color.LightGray, modifier = Modifier.size(48.dp))
                    Text("বর্তমানে কোনো ডেলিভারি খালি নেই!", fontSize = 10.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
                    Text("অ্যাডমিন প্যানেল থেকে অর্ডার 'ডিসপ্যাচ' করুন তা এখানে আসবে।", fontSize = 8.sp, color = Color.Gray)
                }
            }
        } else {
            Column(modifier = Modifier.weight(1f)) {
                // active deliveries lists
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.45f)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        for (del in activeDeliveries) {
                            val isSel = selectedOrderForRoute?.orderId == del.orderId
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .then(if(isSel) Modifier.border(1.5.dp, GoldPrimary, RoundedCornerShape(8.dp)) else Modifier)
                                    .clickable { selectedOrderForRoute = del },
                                colors = CardDefaults.cardColors(containerColor = PureWhite),
                                shape = RoundedCornerShape(8.dp),
                                elevation = CardDefaults.cardElevation(2.dp)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text("অর্ডার: ${del.orderId}", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = GreenPrimaryDark)
                                        Text("৳ ${del.totalAmount.toInt()}", fontSize = 10.sp, fontWeight = FontWeight.Black, color = GreenMedium)
                                    }
                                    Text("গ্রাহক: ${del.customerName} (${del.paymentChannel})", fontSize = 9.sp, color = DarkGray, fontWeight = FontWeight.Bold)
                                    Text("ঠিকানা: ${del.address}", fontSize = 8.sp, color = Color.Gray, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                    Text("প্যাকস: ${del.itemsSummary}", fontSize = 8.sp, color = DarkGray, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                }
                            }
                        }
                    }
                }

                // If Selected Delivery displays Delivery Route simulation & OTP Verification Form
                selectedOrderForRoute?.let { sOrd ->
                    HorizontalDivider(modifier = Modifier.padding(vertical = 6.dp))
                    
                    Text("অন-ডিমান্ড রাজিবপুর ম্যাপ রাডার সিমুলেশন:", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = DarkGray)
                    
                    // Maps Canvas Simulation
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.35f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFE3F2FD)) // Sky light map background
                            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val w = size.width
                            val h = size.height

                            // Draw Kurigram rivers or borders
                            val river = Path().apply {
                                moveTo(w * 0.1f, 0f)
                                quadraticTo(w * 0.25f, h * 0.4f, w * 0.15f, h)
                            }
                            drawPath(river, Color(0xFF90CAF9), style = Stroke(width = 12f))

                            // Draw Rajibpur bypass highways road grid
                            drawLine(Color.White, Offset(w * 0.1f, h * 0.4f), Offset(w * 0.9f, h * 0.4f), strokeWidth = 8f)
                            drawLine(Color.White, Offset(w * 0.5f, 0f), Offset(w * 0.5f, h), strokeWidth = 6f)

                            // Label points
                            drawCircle(GreenPrimaryDark, radius = 6f, center = Offset(w * 0.12f, h * 0.42f)) // Depot
                            drawCircle(Color.Red, radius = 8f, center = Offset(w * 0.65f, h * 0.35f)) // Customer Address

                            // Navigation connection path
                            drawLine(
                                color = GoldPrimary,
                                start = Offset(w * 0.12f, h * 0.42f),
                                end = Offset(w * 0.5f, h * 0.4f),
                                strokeWidth = 4f,
                                cap = androidx.compose.ui.graphics.StrokeCap.Round
                            )
                            drawLine(
                                color = GoldPrimary,
                                start = Offset(w * 0.5f, h * 0.4f),
                                end = Offset(w * 0.65f, h * 0.35f),
                                strokeWidth = 4f,
                                cap = androidx.compose.ui.graphics.StrokeCap.Round
                            )
                        }

                        // Labels floats overlay
                        Box(modifier = Modifier.fillMaxSize()) {
                            Text("১ম রাজিবপুর মূল ডিপো", fontSize = 6.5.sp, color = GreenPrimaryDark, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 14.dp, top = 46.dp))
                            Text("ডেলিভারি গন্তব্য: ${sOrd.customerName}", fontSize = 7.sp, color = Color.Red, fontWeight = FontWeight.Bold, modifier = Modifier.align(Alignment.Center).padding(start = 75.dp, bottom = 32.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // OTP customer validation panel
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, GoldPrimary)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("গ্রাহকের ওটিপি ভেরিফিকেশন (OTP)", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = DarkGray)
                                Text("সেফ কোড: (সিমুলেশন টিপস: ${sOrd.otp})", fontSize = 8.sp, color = Color.Gray)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = otpVerifyInput,
                                    onValueChange = { if(it.length <= 4) otpVerifyInput = it },
                                    placeholder = { Text("৪-ডিজিট কোড", fontSize = 10.sp) },
                                    singleLine = true,
                                    modifier = Modifier.weight(1f).height(42.dp),
                                    textStyle = LocalTextStyle.current.copy(fontSize = 11.sp)
                                )
                                Button(
                                    onClick = {
                                        if (otpVerifyInput == sOrd.otp) {
                                            viewModel.setOrderStatus(sOrd.orderId, "DELIVERED")
                                            selectedOrderForRoute = null
                                            otpVerifyInput = ""
                                        } else {
                                            viewModel.postMessage("দুঃখিত, দেওয়া ওটিপি (OTP) সঠিক নয়!")
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = AlertSuccess),
                                    contentPadding = PaddingValues(horizontal = 8.dp),
                                    shape = RoundedCornerShape(6.dp),
                                    modifier = Modifier.height(36.dp)
                                ) {
                                    Text("ডেলিভারি সফল করুন", color = PureWhite, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
