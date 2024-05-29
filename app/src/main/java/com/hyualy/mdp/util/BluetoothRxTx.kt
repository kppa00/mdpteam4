
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

class BluetoothRxTx(
    private val socket: BluetoothSocket,
    private val callback: (String) -> Unit
) : Thread() {
    private val inputStream: InputStream? = socket.inputStream

    override fun run() {
        val buffer = ByteArray(1024)
        var bytes: Int

        try {
            val input = inputStream ?: throw IOException("InputStream is null")

            bytes = input.read(buffer)
            val readMessage = String(buffer, 0, bytes)

            callback(readMessage)

        } catch (e: IOException) {
            Log.e("BluetoothThread", "Input stream was disconnected", e)
        } finally {
            try {
                socket.close()
            } catch (e: IOException) {
                Log.e("BluetoothThread", "Could not close the client socket", e)
            }
        }
    }
}

@SuppressLint("MissingPermission")
fun receiveBluetoothData(device: BluetoothDevice, callback: (String) -> Unit) {
    val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    val socket = device.createRfcommSocketToServiceRecord(uuid)

    try {
        socket.connect()
        val bluetoothThread = BluetoothRxTx(socket, callback)
        bluetoothThread.start()
    } catch (e: IOException) {
        Log.e("BluetoothConnection", "Unable to connect; closing the socket", e)
        try {
            socket.close()
        } catch (e2: IOException) {
            Log.e("BluetoothConnection", "Could not close the client socket", e2)
        }
    }
}

@SuppressLint("MissingPermission")
fun sendData(device: BluetoothDevice, data: String) {
    val uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    val socket = device.createRfcommSocketToServiceRecord(uuid)

    try {
        socket.connect()
        val outputStream: OutputStream = socket.outputStream
        outputStream.write(data.toByteArray())
        outputStream.close()
    } catch (e: IOException) {
        Log.e("BluetoothManager", "Error sending data", e)
    } finally {
        try {
            socket.close()
        } catch (e: IOException) {
            Log.e("BluetoothManager", "Could not close the client socket", e)
        }
    }
}