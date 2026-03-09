package com.project.tugasakhirinventory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.tugasakhirinventory.ui.theme.TugasAkhirInventoryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TugasAkhirInventoryTheme {
                InventoryDashboard()
            }
        }
    }
}

// Model Data Sederhana
data class InvSummary(val title: String, val count: String, val icon: ImageVector, val color: Color)
data class InvItem(val name: String, val stock: Int, val category: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryDashboard() {
    // Data Dummy untuk Dashboard
    val summaryList = listOf(
        InvSummary("Total Barang", "1,240", Icons.Default.Home, Color(0xFF6200EE)),
        InvSummary("Stok Menipis", "8", Icons.Default.Warning, Color(0xFFFF9800)),
        InvSummary("Masuk Hari Ini", "45", Icons.Default.ArrowDropDown, Color(0xFF4CAF50))
    )

    val inventoryItems = listOf(
        InvItem("Laptop Acer Nitro V16", 5, "Elektronik"),
        InvItem("Mouse Gaming RGB", 15, "Aksesoris"),
        InvItem("Keyboard Mechanical", 3, "Aksesoris"),
        InvItem("Monitor 24 Inch", 10, "Elektronik"),
        InvItem("Kabel HDMI 2.1", 50, "Kabel"),
        InvItem("SSD NVMe 1TB", 2, "Hardware")
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Inventory System", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Aksi Tambah Barang */ }) {
                Icon(Icons.Default.Add, contentDescription = "Add Item")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF8F9FA))
        ) {
            // --- BAGIAN RINGKASAN (Summary) ---
            item {
                Text(
                    text = "Ringkasan Gudang",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(summaryList) { summary ->
                        SummaryCard(summary)
                    }
                }
            }

            // --- BAGIAN DAFTAR BARANG ---
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Stok Barang Terbaru",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Lihat Semua",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            items(inventoryItems) { item ->
                InventoryItemRow(item)
            }
        }
    }
}

@Composable
fun SummaryCard(summary: InvSummary) {
    Card(
        modifier = Modifier.size(width = 140.dp, height = 110.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp).fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(summary.icon, contentDescription = null, tint = summary.color)
            Column {
                Text(text = summary.count, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
                Text(text = summary.title, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
            }
        }
    }
}

@Composable
fun InventoryItemRow(item: InvItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Placeholder
            Box(
                modifier = Modifier
                    .size(45.dp)
                    .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = item.name.take(1), fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.name, fontWeight = FontWeight.SemiBold)
                Text(text = item.category, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }

            // Status Stok
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${item.stock} unit",
                    fontWeight = FontWeight.Bold,
                    color = if (item.stock <= 5) Color.Red else Color.Black
                )
                if (item.stock <= 5) {
                    Text(text = "Limit!", style = MaterialTheme.typography.labelSmall, color = Color.Red)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    TugasAkhirInventoryTheme {
        InventoryDashboard()
    }
}