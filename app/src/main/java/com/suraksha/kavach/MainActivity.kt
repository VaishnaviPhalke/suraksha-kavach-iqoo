package com.suraksha.kavach

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cable
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Psychology
import androidx.compose.material.icons.rounded.Shield
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.suraksha.kavach.ui.screens.AccessGateScreen
import com.suraksha.kavach.ui.screens.DashboardScreen
import com.suraksha.kavach.ui.screens.SyncBridgeScreen
import com.suraksha.kavach.ui.screens.ThreatSentinelScreen
import com.suraksha.kavach.ui.theme.*
import com.suraksha.kavach.viewmodel.SurakshaViewModel

/**
 * Suraksha Kavach (सुरक्षा कवच)
 * ~70% Functional On-Device Security Application
 * 
 * CORE FEATURES IMPLEMENTED:
 * 1. Air-Gapped Verification: Active "Air-Gapped | 0 Outbound Requests" badge.
 * 2. Real Hardware KeyStore Cryptography: AES-256-GCM file encryption/decryption.
 * 3. Real BiometricPrompt: Hardware fingerprint/face gating on FragmentActivity.
 * 4. Real Text-To-Speech: Audible security challenge alerts.
 * 5. iQOO Office Kit Sentinel Tunnel: Real embedded HTTP server on port 8888 for Laptop Sentinel Console.
 * 6. Tamper-Proof Audit Ledger: SQLite with cryptographic SHA-256 hash chaining.
 */
class MainActivity : FragmentActivity() {

    private val viewModel: SurakshaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SurakshaKavachTheme {
                MainAppHost(
                    viewModel = viewModel,
                    activity = this
                )
            }
        }
    }
}

enum class KavachScreen(val title: String, val icon: ImageVector) {
    DASHBOARD("My Shield", Icons.Rounded.Shield),
    ACCESS_GATE("Access Gate", Icons.Rounded.Lock),
    SENTINEL("Sentinel", Icons.Rounded.Psychology),
    SYNC_BRIDGE("Sync Bridge", Icons.Rounded.Cable)
}

@Composable
fun MainAppHost(
    viewModel: SurakshaViewModel,
    activity: FragmentActivity
) {
    var selectedScreen by remember { mutableStateOf(KavachScreen.DASHBOARD) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = WarmOffWhite,
        bottomBar = {
            NavigationBar(
                containerColor = CardWhite,
                tonalElevation = 4.dp
            ) {
                KavachScreen.values().forEach { screen ->
                    val isSelected = selectedScreen == screen
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { selectedScreen = screen },
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                contentDescription = screen.title,
                                modifier = Modifier.size(22.dp)
                            )
                        },
                        label = {
                            Text(
                                text = screen.title,
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = SageGreenSafe,
                            selectedTextColor = TextCharcoal,
                            indicatorColor = SageGreenLight,
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        when (selectedScreen) {
            KavachScreen.DASHBOARD -> DashboardScreen(
                viewModel = viewModel,
                modifier = Modifier.padding(innerPadding)
            )
            KavachScreen.ACCESS_GATE -> AccessGateScreen(
                viewModel = viewModel,
                activity = activity,
                modifier = Modifier.padding(innerPadding)
            )
            KavachScreen.SENTINEL -> ThreatSentinelScreen(
                viewModel = viewModel,
                modifier = Modifier.padding(innerPadding)
            )
            KavachScreen.SYNC_BRIDGE -> SyncBridgeScreen(
                viewModel = viewModel,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
