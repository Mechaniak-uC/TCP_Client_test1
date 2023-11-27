package pl.mechaniak.tcp_client_test1

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.DisposableEffectScope
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.mechaniak.tcp_client_test1.ui.theme.TCP_Client_test1Theme
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.Socket

class MainActivity : ComponentActivity() {
    private var socket: Socket? = null
//    val serverAddress = "10.0.1.1"  // Adres IP serwera
//    val serverPort = 5555           // Port serwera

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("LIFECYCLE", "onCreate")

        setContent {
            TCP_Client_test1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TcpClientApp()
                }

            }
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("LIFECYCLE", "onStop")
        socket?.close()
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d("LIFECYCLE", "onDestroy")
        socket?.close()
    }


}


//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}


@Composable
fun TcpClientApp() {
    var receivedData by remember { mutableStateOf("") }
//    var socket by remember { mutableStateOf<Socket?>(null) }
//    var messageToSend by remember { mutableStateOf("") }
    val serverAddress = "10.0.1.1"  // Adres IP serwera
    val serverPort = 5555           // Port serwera

    LaunchedEffect(Unit) {

        GlobalScope.launch(Dispatchers.IO) {


            var socket = Socket(serverAddress, serverPort)
            val reader = BufferedReader(InputStreamReader(socket.getInputStream()))

            try {
                while (true) {
                    val data = reader.readLine()
                    receivedData = data ?: ""
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                socket.close()
            }
        }
    }

//    DisposableEffect(Unit) {
//        onDispose {
//            // Wywoływane po zamknięciu aplikacji
//            // Zamknij połączenie TCP
//            // Jeśli połączenie jest już zamknięte, ta operacja nie ma wpływu
//            // Możesz również obsłużyć inne zasoby i czynności związane z zamknięciem aplikacji
//            // na przykład anulować korutynę lub zatrzymać inne działania wątkowe
//            socket?.close()
//        }
//    }

    

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Received Data:",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = receivedData,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
    }
}


//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = receivedData, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(16.dp))

//        TextField(
//            value = messageToSend,
//            onValueChange = { messageToSend = it },
//            label = { Text("Enter message") },
//            keyboardOptions = KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Send),
//            keyboardActions = KeyboardActions(onSend = { sendMessage() }),
//            modifier = Modifier.padding(horizontal = 16.dp)
//        )

//        Button(onClick = { sendMessage() }, modifier = Modifier.padding(16.dp)) {
//            Text(text = "Send")
//        }
//    }


//    //Tworzy nowy lekki wątkek, które umożliwiają asynchroniczne wykonywanie operacji bez blokowania wątku w globalnym zakresie
//    // i uruchamia ją w wątku I/O, co umożliwia asynchroniczne wykonanie operacji wejścia/wyjścia.
//    GlobalScope.launch(Dispatchers.IO) {
//
//        try {
//            var socket = Socket("10.0.1.1", 5555)
//            //tworzy obiekt BufferedReader dla strumienia wejściowego socket.inputStream, umożliwiając odczyt danych z serwera połączonego z danym socketem
//            var inputStream = BufferedReader( InputStreamReader( socket.getInputStream() ))
//            //tworzy obiekt OutputStreamWriter dla strumienia wyjściowego socket.outputStream, umożliwiając zapisywanie danych do serwera połączonego z danym socketem.
//            var outputStream = OutputStreamWriter(socket.outputStream)
//
//        } catch (e: Exception) {
//
//        }
//
//
//        //tworzy obiekt BufferedReader dla strumienia wejściowego socket.inputStream, umożliwiając odczyt danych z serwera połączonego z danym socketem
//    }

//    DisposableEffect(Unit) {
//        var socket = Socket("10.0.1.1", 5555)
//        val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
//
//        GlobalScope.launch(Dispatchers.IO) {
//            while (true) {
//                val data = reader.readLine()
//                receivedData = "Received data: $data"
////                println("Otrzymane dane: $data")
//            }
//        }
//
//        onDispose {
//            socket.close()
//        }
//    }
//}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TCP_Client_test1Theme {
        TcpClientApp()
    }

}