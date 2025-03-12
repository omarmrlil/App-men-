package com.example.mymenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview // Importación necesaria para @Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Actividad principal de la aplicación
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilita el modo de pantalla completa sin bordes
        setContent {
            MainScreen() // Establece el contenido de la actividad
        }
    }
}

// Clase de datos que representa un elemento del menú
data class MenuItem(
    val name: String, // Nombre del ítem
    val price: String, // Precio del ítem
    val imageRes: Int, // Recurso de imagen asociado
    val description: String // Descripción del ítem
)

// Enumeración que define las diferentes pantallas de la aplicación
enum class Screen {
    Home, Menu, About, Contact
}

@Composable
fun MainScreen() {
    var showMenu by remember { mutableStateOf(false) } // Estado para mostrar el menú
    var selectedItem by remember { mutableStateOf<MenuItem?>(null) } // Estado para el ítem seleccionado
    var currentScreen by remember { mutableStateOf(Screen.Home) } // Estado para la pantalla actual

    // Lógica de navegación
    when {
        selectedItem != null -> {
            MenuItemDetailScreen(menuItem = selectedItem!!, onBackClick = { selectedItem = null })
        }
        showMenu -> {
            FastFoodMenuScreen(onBackClick = { showMenu = false }, onItemClick = { selectedItem = it })
        }
        else -> {
            when (currentScreen) {
                Screen.Home -> HomeScreen(
                    onStartClick = { currentScreen = Screen.Menu },
                    onAboutClick = { currentScreen = Screen.About },
                    onContactClick = { currentScreen = Screen.Contact }
                )
                Screen.Menu -> FastFoodMenuScreen(
                    onBackClick = { currentScreen = Screen.Home },
                    onItemClick = { selectedItem = it }
                )
                Screen.About -> AboutScreen(onBackClick = { currentScreen = Screen.Home })
                Screen.Contact -> ContactScreen(onBackClick = { currentScreen = Screen.Home })
            }
        }
    }
}

@Composable
fun HomeScreen(onStartClick: () -> Unit, onAboutClick: () -> Unit, onContactClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Bienvenido a FastFood App", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        MyImageAbout()
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onAboutClick, modifier = Modifier.width(200.dp)) {
            Text(text = "Nosotros")
        }
        Button(onClick = onStartClick, modifier = Modifier.width(200.dp)) {
            Text(text = "Ver Menú")
        }
        Button(onClick = onContactClick, modifier = Modifier.width(200.dp)) {
            Text(text = "Contáctanos")
        }
    }
}

@Composable
fun AboutScreen(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Acerca de Nosotros", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        MyImageAbout()
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Somos una aplicación dedicada a ofrecer la mejor comida rápida a nuestros clientes.",
            fontSize = 16.sp,
            modifier = Modifier.padding(50.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBackClick, modifier = Modifier.width(200.dp)) {
            Text(text = "Volver al Inicio")
        }
    }
}

@Composable
fun ContactScreen(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Contáctanos", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        MyImageContact()
        Text(
            text = "Puedes contactarnos a través de nuestro Whatsapp: 809-555-555",
            fontSize = 16.sp,
            modifier = Modifier.padding(20.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Correo: contacto@fastfoodapp.com", fontSize = 16.sp, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Redes: @fastfoodapp", fontSize = 16.sp, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBackClick, modifier = Modifier.width(200.dp)) {
            Text(text = "Volver al Inicio")
        }
    }
}

@Composable
fun MyImageContact() {
    Image(
        painter = painterResource(R.drawable.contact),
        contentDescription = "Imagen de contacto"
    )
}

@Composable
fun MyImageAbout() {
    Image(
        painter = painterResource(R.drawable.logo),
        contentDescription = "Imagen del logo"
    )
}

@Composable
fun FastFoodMenuScreen(onBackClick: () -> Unit, onItemClick: (MenuItem) -> Unit) {
    val menuItems = remember {
        listOf(
            MenuItem("Hamburguesa Clásica", "$5.99", R.drawable.burger, "Deliciosa hamburguesa con carne jugosa y pan suave."),
            MenuItem("Papas Fritas", "$2.99", R.drawable.fries, "Crujientes papas fritas doradas a la perfección."),
            MenuItem("Refresco", "$1.99", R.drawable.soda, "Bebida refrescante para acompañar tu comida."),
            MenuItem("Pollo Frito", "$6.49", R.drawable.chicken, "Trozos de pollo crujiente con especias especiales.")
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Nuestro Menú", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(menuItems) { item ->
                MenuItemCard(menuItem = item, onItemClick = onItemClick)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBackClick, modifier = Modifier.width(200.dp)) {
            Text(text = "Volver al Inicio")
        }
    }
}

@Composable
fun MenuItemCard(menuItem: MenuItem, onItemClick: (MenuItem) -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onItemClick(menuItem) }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = menuItem.imageRes),
                contentDescription = menuItem.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)), // Aquí se usa la función clip
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = menuItem.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = menuItem.price, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun MenuItemDetailScreen(menuItem: MenuItem, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = menuItem.imageRes),
            contentDescription = menuItem.name,
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = menuItem.name, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = menuItem.price, fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = menuItem.description, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBackClick, modifier = Modifier.width(200.dp)) {
            Text(text = "Volver al Menú")
        }
    }
}

@Preview
@Composable
fun PreviewMenu() {
    MainScreen()
}