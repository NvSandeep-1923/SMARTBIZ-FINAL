package com.example.smartbiz.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartbiz.ui.theme.Indigo900
import com.example.smartbiz.ui.theme.LightBg
import com.example.smartbiz.ui.theme.SmartBizTheme
import androidx.compose.foundation.BorderStroke

@Composable
fun BusinessProfileScreen() {
    Scaffold(
        topBar = { ProfileTopBar() },
        bottomBar = { ProfileBottomActions() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LightBg)
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            ProfileHeroSection()
            
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                AddItemSectionCard(title = "Business Details", icon = Icons.Default.Business) {
                    AddItemField(label = "Business Name", placeholder = "SmartBiz Store")
                    AddItemDropdown(label = "Category", options = listOf("Retail / Kirana", "Wholesale", "Manufacturing", "Services"))
                    AddItemField(label = "GSTIN", placeholder = "22AAAAA0000A1Z5")
                }
                
                AddItemSectionCard(title = "Contact Info", icon = Icons.Default.Person) {
                    AddItemField(label = "Phone Number", placeholder = "+91 98765 43210")
                    AddItemField(label = "Email Address", placeholder = "contact@smartbizstore.com")
                    Text(text = "Business Address", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF666666), modifier = Modifier.padding(bottom = 6.dp))
                    Surface(
                        modifier = Modifier.fillMaxWidth().height(90.dp),
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFF8F9FF),
                        border = BorderStroke(1.5.dp, Color(0xFFE0E3EF))
                    ) {
                        Text(
                            text = "123 Market Square, Central Avenue, Bangalore, Karnataka - 560001",
                            fontSize = 14.sp,
                            color = Indigo900,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(13.dp, 14.dp)
                        )
                    }
                }
                
                Card(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "Display GST on Invoices", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF222222))
                            Text(text = "Automatically include your GSTIN and tax breakup on every invoice.", fontSize = 12.sp, color = Color(0xFF888888), modifier = Modifier.padding(top = 2.dp))
                        }
                        Switch(
                            checked = true,
                            onCheckedChange = {},
                            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Indigo900)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(180.dp))
        }
    }
}

@Composable
fun ProfileTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightBg)
            .padding(top = 52.dp, start = 20.dp, end = 20.dp, bottom = 14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = Icons.Default.Menu, contentDescription = null, tint = Indigo900)
        Text(text = "SmartBiz", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
        Icon(imageVector = Icons.Default.NotificationsNone, contentDescription = null, tint = Indigo900)
    }
}

@Composable
fun ProfileHeroSection() {
    Column(modifier = Modifier.fillMaxWidth().padding(top = 4.dp, bottom = 24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.padding(bottom = 12.dp)) {
            Box(
                modifier = Modifier
                    .size(84.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE8EAF6))
                    .border(3.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.Store, contentDescription = null, tint = Indigo900, modifier = Modifier.size(40.dp))
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = (-2).dp, y = (-2).dp)
                    .size(26.dp)
                    .clip(CircleShape)
                    .background(Indigo900)
                    .border(2.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
            }
        }
        Text(text = "SmartBiz Store", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
        Text(text = "GST: 22AAAAA0000A1Z5", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF888888), modifier = Modifier.padding(top = 3.dp))
    }
}

@Composable
fun ProfileBottomActions() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 14.dp)
            .padding(bottom = 72.dp)
    ) {
        Button(
            onClick = { /* TODO */ },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Indigo900),
            shape = RoundedCornerShape(50.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(18.dp))
                Text(text = "Update Profile", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
        }
        TextButton(
            onClick = { /* TODO */ },
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        ) {
            Text(text = "Discard Changes", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color(0xFF888888))
        }
    }
}

@Composable
private fun AddItemSectionCard(title: String, icon: ImageVector, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 14.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(bottom = 16.dp)) {
                Icon(imageVector = icon, contentDescription = null, tint = Indigo900, modifier = Modifier.size(20.dp))
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.ExtraBold, color = Indigo900)
            }
            content()
        }
    }
}

@Composable
private fun AddItemField(label: String, placeholder: String) {
    Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF666666), modifier = Modifier.padding(bottom = 6.dp))
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF8F9FF),
        border = BorderStroke(1.5.dp, Color(0xFFE0E3EF))
    ) {
        Text(
            text = placeholder,
            modifier = Modifier.padding(13.dp, 14.dp),
            fontSize = 14.sp,
            color = Indigo900,
            fontWeight = FontWeight.SemiBold
        )
    }
    Spacer(modifier = Modifier.height(14.dp))
}

@Composable
private fun AddItemDropdown(label: String, options: List<String>) {
    Text(text = label, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF666666), modifier = Modifier.padding(bottom = 6.dp))
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF8F9FF),
        border = BorderStroke(1.5.dp, Color(0xFFE0E3EF))
    ) {
        Row(
            modifier = Modifier.padding(13.dp, 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = options.first(), fontSize = 14.sp, color = Indigo900, fontWeight = FontWeight.SemiBold)
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null, tint = Indigo900, modifier = Modifier.size(16.dp))
        }
    }
    Spacer(modifier = Modifier.height(14.dp))
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
fun BusinessProfileScreenPreview() {
    SmartBizTheme {
        BusinessProfileScreen()
    }
}
