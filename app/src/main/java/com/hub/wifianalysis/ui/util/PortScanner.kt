package com.hub.wifianalysis.ui.util

import android.util.Log
import com.hub.wifianalysis.ui.util.model.PortDescription
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InterruptedIOException
import java.net.*

class PortScanner(val ip:String ) {
    companion object {
        val TAG = PortScanner::class.java.name
    }


    suspend fun isUdpPortOpen(port: Int) = withContext(Dispatchers.IO) {
        try {
            val bytes = ByteArray(128)
            val ds = DatagramSocket()
            val dp = DatagramPacket(bytes, bytes.size, InetAddress.getByName(ip), port)
            ds.soTimeout = 1000
            ds.send(dp)
            val dp2 = DatagramPacket(bytes, bytes.size)
            ds.receive(dp2)
            ds.close()
        } catch (e: InterruptedIOException) {
            return@withContext false
        } catch (e: IOException) {
            return@withContext false
        }
        true

    }

    suspend fun isTcpPortOpen(port: Int) = withContext(Dispatchers.IO) {
        var socket: Socket? = null
        try {
            Log.d(TAG, "trying socket: $ip : $port")
            socket = Socket(InetAddress.getByName(ip), port)
            return@withContext true
        } catch (ex: ConnectException) {
            Log.d(TAG, "Got connection error: $ex")
            return@withContext false
        } catch (ex: NoRouteToHostException) {
            Log.d(TAG, "No Route to Host: $ex")
            return@withContext  false
        } finally {
            socket?.close()
        }
    }

    suspend fun scanPorts() = withContext(Dispatchers.Main) {
        PortDescription.commonPorts.flatMap {
            listOf(
                async {
                    PortResult(it.port, PortDescription.Protocol.TCP, isTcpPortOpen(it.port))
                },
                async {
                    PortResult(it.port, PortDescription.Protocol.UDP, isUdpPortOpen(it.port))
                }
            )
        }
    }

    data class PortResult(val port: Int, val protocol: PortDescription.Protocol, val isOpen: Boolean)
}
