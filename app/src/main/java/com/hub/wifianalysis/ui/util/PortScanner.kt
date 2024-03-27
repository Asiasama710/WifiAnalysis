package com.hub.wifianalysis.ui.util

import android.util.Log
import com.hub.wifianalysis.ui.util.model.PortDescription
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InterruptedIOException
import java.net.*

/**
 * PortScanner is a class used to scan the ports of a given IP address.
 *
 * @property ip The IP address to scan.
 * @property TAG The tag used for logging.
 */
class PortScanner(val ip:String ) {
    companion object {
        val TAG = PortScanner::class.java.name
    }

    /**
     * Checks if a UDP port is open.
     *
     * @param port The port to check.
     * @return A Boolean indicating whether the port is open.
     */
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

    /**
     * Checks if a TCP port is open.
     *
     * @param port The port to check.
     * @return A Boolean indicating whether the port is open.
     */
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

    /**
     * Scans the common ports for both TCP and UDP protocols.
     *
     * @return A list of PortResult objects representing the result of the port scan.
     */
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

    /**
     * PortResult is a data class that represents the result of a port scan.
     *
     * @property port The port that was scanned.
     * @property protocol The protocol that was used for the scan.
     * @property isOpen A Boolean indicating whether the port is open.
     */
    data class PortResult(val port: Int, val protocol: PortDescription.Protocol, val isOpen: Boolean)
}