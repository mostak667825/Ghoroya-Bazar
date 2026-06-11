package com.example

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.CartItem
import com.example.ui.*
import com.example.ui.theme.*
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen() {
    val context = LocalContext.current
    val viewModel: MainViewModel = viewModel()

    val currentTab by viewModel.currentTab.collectAsState()
    val isPresentationMode by viewModel.isPresentationMode.collectAsState()
    val cartItems by viewModel.cartItems.collectAsState()
    val profile by viewModel.memberProfile.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    // Interactive role perspective collection
    val currentUserRole by viewModel.currentUserRole.collectAsState()
    val isPaymentModalOpen by viewModel.isPaymentModalOpen.collectAsState()
    val isPaswoFormOpen by viewModel.isPaswoFormOpen.collectAsState()

    var showCartSheet by remember { mutableStateOf(false) }
    var showRoleDropdown by remember { mutableStateOf(false) }
    var showAiAssistant by remember { mutableStateOf(false) }
    var showQrDialog by remember { mutableStateOf(false) }

    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    val isCloudSyncing by viewModel.isCloudSyncing.collectAsState()

    // Toast flow channel subscriber
    LaunchedEffect(key1 = true) {
        viewModel.eventMessage.collectLatest { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

    if (isPresentationMode) {
        // Fullscreen Mode for UI/UX case study presentation board
        AppPresentationBoard(viewModel = viewModel)
    } else if (!isLoggedIn) {
        // Otp Login and Register Gateway
        OtpLoginScreen(viewModel = viewModel)
    } else {
        // Standard interactive premium mobile app
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(Icons.Default.ShoppingCart, "ShopLogo", tint = GoldPrimary, modifier = Modifier.size(24.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "ঘরোয়া বাজার ডট কম",
                                        fontWeight = FontWeight.Black,
                                        fontSize = 16.sp,
                                        color = PureWhite
                                    )
                                    if (isCloudSyncing) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Icon(
                                            Icons.Default.Refresh,
                                            contentDescription = "Sync",
                                            tint = GoldPrimary,
                                            modifier = Modifier.size(12.dp)
                                        )
                                    }
                                }
                                Text(
                                    text = "স্বল্প মূল্যে পরিবারের বাজার",
                                    fontSize = 10.sp,
                                    color = GoldLight,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    },
                    actions = {
                        // Compact Switcher Dropdown Action
                        Box {
                            IconButton(onClick = { showRoleDropdown = true }) {
                                Icon(
                                    Icons.Default.Settings,
                                    contentDescription = "রোল নির্বাচন",
                                    tint = GoldPrimary
                                )
                            }
                            DropdownMenu(
                                expanded = showRoleDropdown,
                                onDismissRequest = { showRoleDropdown = false },
                                modifier = Modifier.background(PureWhite)
                            ) {
                                DropdownMenuItem(
                                    text = { Text("🛒 গ্রাহক অ্যাপ (Customer)") },
                                    onClick = {
                                        viewModel.switchUserRole("USER")
                                        showRoleDropdown = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("🛠️ অ্যাডমিন প্যানেল (System Admin)") },
                                    onClick = {
                                        viewModel.switchUserRole("ADMIN")
                                        showRoleDropdown = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("🚚 ডিস্ট্রিবিউটর (Distributor)") },
                                    onClick = {
                                        viewModel.switchUserRole("DISTRIBUTOR")
                                        showRoleDropdown = false
                                    }
                                )
                            }
                        }

                        // Presentation case study toggle button
                        IconButton(
                            onClick = { viewModel.togglePresentationMode() }
                        ) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = "Behance Board",
                                tint = GoldPrimary
                            )
                        }

                        // Helpline phone dialer button
                        IconButton(
                            onClick = {
                                try {
                                    val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                                        data = Uri.parse("tel:01518489080")
                                    }
                                    context.startActivity(dialIntent)
                                } catch (e: Exception) {
                                    Toast.makeText(context, "ডায়ালার ওপেন করা যাচ্ছে না", Toast.LENGTH_SHORT).show()
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.Call,
                                contentDescription = "Call Support",
                                tint = PureWhite
                            )
                        }

                        // Shopping Cart with badge count
                        BadgedBox(
                            badge = {
                                if (cartItems.isNotEmpty()) {
                                    Badge(
                                        containerColor = GoldPrimary,
                                        contentColor = GreenPrimaryDark
                                    ) {
                                        Text("${cartItems.sumOf { it.quantity }}", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            },
                            modifier = Modifier.padding(end = 12.dp)
                        ) {
                            IconButton(onClick = { showCartSheet = true }) {
                                Icon(
                                    Icons.Default.ShoppingCart,
                                    contentDescription = "Cart",
                                    tint = PureWhite
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = GreenPrimary,
                        titleContentColor = PureWhite
                    )
                )
            },
            floatingActionButton = {
                if (currentUserRole == "USER" && currentTab in listOf("Home", "Shop")) {
                    FloatingActionButton(
                        onClick = { showAiAssistant = true },
                        containerColor = GoldPrimary,
                        contentColor = GreenPrimaryDark,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.padding(bottom = 12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(Icons.Default.Star, contentDescription = "AI", tint = GreenPrimaryDark, modifier = Modifier.size(16.dp))
                            Text("এআই বাজেট সহকারী", fontSize = 11.sp, fontWeight = FontWeight.Black, color = GreenPrimaryDark)
                        }
                    }
                }
            },
            bottomBar = {
                if (currentUserRole == "USER") {
                    NavigationBar(
                        containerColor = GreenPrimary,
                        tonalElevation = 8.dp,
                        windowInsets = WindowInsets.navigationBars
                    ) {
                        val navItems = listOf(
                            Triple("Home", Icons.Default.Home, Icons.Default.Home),
                            Triple("Shop", Icons.Default.ShoppingCart, Icons.Default.ShoppingCart),
                            Triple("Member", Icons.Default.Star, Icons.Default.Star),
                            Triple("Notice", Icons.Default.Notifications, Icons.Default.Notifications),
                            Triple("Profile", Icons.Default.Person, Icons.Default.Person)
                        )

                        navItems.forEach { (tabName, filledIcon, _) ->
                            val isSelected = (currentTab == tabName)
                            NavigationBarItem(
                                selected = isSelected,
                                onClick = { viewModel.selectTab(tabName) },
                                icon = {
                                    Icon(
                                        imageVector = filledIcon,
                                        contentDescription = tabName
                                    )
                                },
                                label = {
                                    Text(
                                        text = when (tabName) {
                                            "Home" -> "হোম"
                                            "Shop" -> "শপ"
                                            "Member" -> "মেম্বার"
                                            "Notice" -> "নোটিশ"
                                            "Profile" -> "প্রোফাইল"
                                            else -> tabName
                                        },
                                        fontSize = 11.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = GreenPrimaryDark,
                                    selectedTextColor = GoldPrimary,
                                    indicatorColor = GoldPrimary,
                                    unselectedIconColor = PureWhite.copy(alpha = 0.6f),
                                    unselectedTextColor = PureWhite.copy(alpha = 0.6f)
                                )
                            )
                        }
                    }
                } else {
                    BottomAppBar(
                        containerColor = GreenPrimaryDark,
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = if(currentUserRole == "ADMIN") Icons.Default.Settings else Icons.Default.ShoppingCart,
                                    contentDescription = "Status",
                                    tint = GoldPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if(currentUserRole == "ADMIN") "🛠️ অ্যাডমিন অপারেশন পোর্টালে আছেন" else "🚚 ডেলিভারি ট্র্যাকিং পোর্টালে আছেন",
                                    color = PureWhite,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Button(
                                onClick = { viewModel.switchUserRole("USER") },
                                colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary),
                                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                modifier = Modifier.height(28.dp)
                            ) {
                                Text("গ্রাহক অ্যাপে ফিরুন", color = GreenPrimaryDark, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(LightSurface)
            ) {
                if (currentUserRole != "USER") {
                    Box(modifier = Modifier.fillMaxSize()) {
                        if (currentUserRole == "ADMIN") {
                            AdminDashboardScreen(viewModel = viewModel)
                        } else {
                            DistributorDashboardScreen(viewModel = viewModel)
                        }
                    }
                } else {
                    // Main Tab Navigation Engine
                    AnimatedContent(
                        targetState = currentTab,
                        transitionSpec = {
                            fadeIn() togetherWith fadeOut()
                        },
                        label = "tab_transition"
                    ) { tab ->
                        when (tab) {
                            "Home" -> HomeScreenContent(viewModel)
                            "Shop" -> ShopScreenContent(viewModel)
                            "Member" -> ScreenMembershipMirror(viewModel)
                            "Notice" -> NotificationCenterScreen(viewModel)
                            "Profile" -> DashboardScreenContent(viewModel)
                        }
                    }
                }

                // Beautiful Bottom Cart Sheet Dialog Overlay
                if (showCartSheet) {
                    CartDialogOverlay(
                        cartItems = cartItems,
                        onClose = { showCartSheet = false },
                        onQtyChange = { item, isInc -> viewModel.modifyCartQty(item, isInc) },
                        onRemove = { viewModel.removeCartItem(it) },
                        onCheckout = {
                            viewModel.checkout()
                            showCartSheet = false
                        }
                    )
                }

                // Payment gateway overlay
                if (isPaymentModalOpen) {
                    PaymentGatewayModal(
                        viewModel = viewModel,
                        onClose = { viewModel.closePaymentModal() }
                    )
                }

                // PASWO subsidized application onboarding form
                if (isPaswoFormOpen) {
                    PaswoFormModal(
                        viewModel = viewModel,
                        onClose = { viewModel.openPaswoForm(false) }
                    )
                }

                if (showAiAssistant) {
                    AiShoppingAssistantDialog(
                        viewModel = viewModel,
                        onClose = { showAiAssistant = false }
                    )
                }

                if (showQrDialog) {
                    SimulatedQrDialog(
                        memberId = profile.memberId.ifEmpty { "GB-1033-26" },
                        memberName = profile.name.ifEmpty { viewModel.currentUserName.value.ifEmpty { "মেম্বার" } },
                        onClose = { showQrDialog = false }
                    )
                }
            }
        }
    }
}

// --------------------------------------------------------------------
// HOME TAB DETAILED LAYOUT
// --------------------------------------------------------------------
@Composable
fun HomeScreenContent(viewModel: MainViewModel) {
    val searchQuery by viewModel.searchQuery.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        // Modern Premium Search Bar Block
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(GreenPrimary)
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    viewModel.updateSearchQuery(it)
                    if (it.isNotEmpty()) {
                        viewModel.selectTab("Shop")
                    }
                },
                placeholder = {
                    Text("চাল, তেল, ডাল ইত্যাদি সাশ্রয়ী মূল্যে খুঁজুন...", color = PureWhite.copy(alpha = 0.6f), fontSize = 12.sp)
                },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = GoldPrimary)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GoldPrimary,
                    unfocusedBorderColor = PureWhite.copy(alpha = 0.4f)
                ),
                singleLine = true
            )
        }

        // Mirror standard home content
        ScreenHomeContentMirror(viewModel = viewModel)
    }
}

// --------------------------------------------------------------------
// SHOP TAB DETAILED LAYOUT
// --------------------------------------------------------------------
@Composable
fun ShopScreenContent(viewModel: MainViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(GreenPrimary)
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            val query by viewModel.searchQuery.collectAsState()
            OutlinedTextField(
                value = query,
                onValueChange = { viewModel.updateSearchQuery(it) },
                placeholder = {
                    Text("খুঁজুন...", color = PureWhite.copy(alpha = 0.6f), fontSize = 12.sp)
                },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Filter", tint = GoldPrimary)
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { viewModel.updateSearchQuery("") }) {
                            Icon(Icons.Default.Clear, "clear", tint = PureWhite)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GoldPrimary,
                    unfocusedBorderColor = PureWhite.copy(alpha = 0.3f)
                ),
                singleLine = true
            )
        }

        ScreenShopMirror(viewModel = viewModel)
    }
}

// --------------------------------------------------------------------
// DASHBOARD / PROFILE TAB DETAILED LAYOUT
// --------------------------------------------------------------------
@Composable
fun DashboardScreenContent(viewModel: MainViewModel) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        // Screen Header Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(GreenPrimary)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("ডিটেইলস ফিনান্স ড্যাশবোর্ড", color = GoldPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text("সাশ্রয় হিসেব ও রেফারাল কন্ট্রোল", color = PureWhite.copy(alpha = 0.7f), fontSize = 10.sp)
                }
                IconButton(
                    onClick = { viewModel.incrementReferral() }
                ) {
                    Icon(Icons.Default.Add, "Refer", tint = GoldPrimary)
                }
            }
        }

        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            ScreenDashboardMirror(viewModel = viewModel)

            Spacer(modifier = Modifier.height(6.dp))

            // Brand & Business Details Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                colors = CardDefaults.cardColors(containerColor = PureWhite),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "ঘরোয়া বাজার ব্যবসায়িক তথ্য",
                        color = GreenPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )
                    
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 2.dp)) {
                        Icon(Icons.Default.Info, "info", tint = GoldSatin, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "অনলাইন ও স্থানীয় মেম্বারশিপ ভিত্তিক সস্তা বাজার ব্যবস্থা।", fontSize = 10.sp, color = DarkGray)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 2.dp)) {
                        Icon(Icons.Default.Home, "addr", tint = GoldSatin, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "ঠিকানা: রাজিবপুর, কুরিগ্রাম, কুড়িগ্রাম, বাংলাদেশ", fontSize = 10.sp, color = DarkGray)
                    }

                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 2.dp)) {
                        Icon(Icons.Default.Phone, "ph", tint = GoldSatin, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "মোবাইল: ০১৫১৮৪৮৯০৮০ (০১৫১৮-৪৮৯০৮০)", fontSize = 10.sp, color = DarkGray)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = {
                            try {
                                val intent = Intent(Intent.ACTION_DIAL).apply {
                                    data = Uri.parse("tel:01518489080")
                                }
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "ডায়াল করা যায়নি", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("সরাসরি হটলাইনে কল করুন", color = PureWhite, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedButton(
                        onClick = { viewModel.logOut() },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.DarkGray),
                        border = BorderStroke(1.dp, Color.Red.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "logout", tint = Color.Red, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("আপনার অ্যাকাউন্ট লগআউট করুন", color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

// --------------------------------------------------------------------
// FULL-FEATURES CHECKOUT SHOPPING CART OVERLAY DIALOG (GLOSSY POPUP)
// --------------------------------------------------------------------
@Composable
fun CartDialogOverlay(
    cartItems: List<CartItem>,
    onClose: () -> Unit,
    onQtyChange: (CartItem, Boolean) -> Unit,
    onRemove: (CartItem) -> Unit,
    onCheckout: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable { onClose() },
        contentAlignment = Alignment.BottomCenter
    ) {
        // Card container
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.65f)
                .clickable(enabled = false) {}, // Prevent closing when tapping inside
            colors = CardDefaults.cardColors(containerColor = PureWhite),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(18.dp)
            ) {
                // Cart Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ShoppingCart, "cart", tint = GreenPrimary, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("আপনার বাজার থলি (কার্ট)", fontWeight = FontWeight.Bold, fontSize = 15.sp, color = GreenPrimaryDark)
                    }
                    IconButton(onClick = onClose) {
                        Icon(Icons.Default.Close, "close", tint = Color.Gray)
                    }
                }

                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f), modifier = Modifier.padding(vertical = 8.dp))

                if (cartItems.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.ShoppingCart, "empty", tint = Color.LightGray, modifier = Modifier.size(48.dp))
                            Text("আপনার কার্ট খালি রয়েছে!", color = Color.Gray, fontSize = 12.sp, modifier = Modifier.padding(top = 8.dp))
                        }
                    }
                } else {
                    // Cart lists
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        for (item in cartItems) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    modifier = Modifier.weight(1f),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Bullet / generic graphic representation
                                    ProductImagePlaceholder(
                                        productType = when {
                                            item.name.contains("চাল") -> "rice"
                                            item.name.contains("তেল") -> "oil"
                                            item.name.contains("ডাল") -> "lentils"
                                            item.name.contains("আটা") -> "flour"
                                            item.name.contains("চিনি") -> "sugar"
                                            item.name.contains("পেঁয়াজ") -> "onion"
                                            item.name.contains("দুধ") -> "milk"
                                            item.name.contains("আলু") -> "potato"
                                            item.name.contains("লবন") -> "salt"
                                            else -> "soap"
                                        },
                                        modifier = Modifier.size(36.dp).clip(RoundedCornerShape(4.dp))
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(item.name, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = DarkGray, maxLines = 1, overflow = TextOverflow.Ellipsis)
                                        Text("সদস্য মূল্য: ৳${item.memberPrice.toInt()} / ${item.unit}", fontSize = 8.sp, color = GreenMedium)
                                        Text("বাজার মূল্য: ৳${item.price.toInt()}", textDecoration = TextDecoration.LineThrough, color = Color.Gray, fontSize = 7.5.sp)
                                    }
                                }

                                // Interactive quantity adjuster
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    IconButton(
                                        onClick = { onQtyChange(item, false) },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(Icons.Default.Delete, "Dec", tint = GreenPrimary, modifier = Modifier.size(18.dp))
                                    }
                                    Text("${item.quantity}", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = DarkGray)
                                    IconButton(
                                        onClick = { onQtyChange(item, true) },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(Icons.Default.Add, "Inc", tint = GreenPrimary, modifier = Modifier.size(18.dp))
                                    }
                                    IconButton(
                                        onClick = { onRemove(item) },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(Icons.Default.Delete, "del", tint = Color.Red.copy(alpha = 0.7f), modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }
                    }

                    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f), modifier = Modifier.padding(vertical = 8.dp))

                    // Totals block standard vs members savings
                    val totalReg = cartItems.sumOf { it.price * it.quantity }
                    val totalMem = cartItems.sumOf { it.memberPrice * it.quantity }
                    val saved = totalReg - totalMem

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("সাধারণ বাজার মূল্য:", color = Color.Gray, fontSize = 10.sp)
                            Text("৳ ${totalReg.toInt()}", color = Color.Gray, textDecoration = TextDecoration.LineThrough, fontSize = 10.sp)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("ঘরোয়া গোল্ড মেম্বার মূল্য:", color = DarkGray, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            Text("৳ ${totalMem.toInt()}", color = GreenPrimary, fontWeight = FontWeight.Black, fontSize = 12.sp)
                        }
                        
                        // Savings accent bar
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(GoldPrimary.copy(alpha = 0.15f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text("আপনার মেম্বারশিপে সাশ্রয় হচ্ছে:", color = GoldSatin, fontWeight = FontWeight.Bold, fontSize = 9.sp)
                                Text("৳ ${saved.toInt()}", color = GoldSatin, fontWeight = FontWeight.Black, fontSize = 10.sp)
                            }
                        }
                    }

                    // Direct Checkout Button
                    Button(
                        onCheckout,
                        colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("চেকআউট সম্পূর্ণ করুন (৳${totalMem.toInt()})", color = PureWhite, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
