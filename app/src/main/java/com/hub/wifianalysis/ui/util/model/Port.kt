package com.hub.wifianalysis.ui.util.model

/**
 * Data class representing a description of a network port.
 *
 * @property port The port number.
 * @property protocol The protocol used by the port (TCP or UDP).
 * @property serviceName The name of the service running on the port.
 * @property urlSchema The URL schema associated with the port, if any.
 */
data class PortDescription(
    val port: Int,
    val protocol: Protocol,
    val serviceName: String,
    val urlSchema: String?,
) {
    companion object {
        /**
         * A list of common ports and their descriptions.
         */
        val commonPorts = listOf(
                PortDescription( 20, Protocol.TCP, "FTP", "ftp"),
                PortDescription( 21, Protocol.TCP, "FTP", "ftp"),
                PortDescription( 22, Protocol.TCP, "SSH",  "ssh"),
                PortDescription( 23, Protocol.TCP, "Telnet",  "telnet"),
                PortDescription( 25, Protocol.TCP, "SMTP", "smtp"),
                PortDescription( 53, Protocol.TCP, "DNS",  "dns"),
                PortDescription( 80, Protocol.TCP, "HTTP",  "http"),
                PortDescription( 110, Protocol.TCP, "POP3", "pop3"),
                PortDescription( 115, Protocol.TCP, "SFTP", "sftp"),
                PortDescription( 123, Protocol.TCP, "NTP", "ntp"),
                PortDescription( 143, Protocol.TCP, "IMAP", "imap"),
                PortDescription( 161, Protocol.TCP, "SNMP", "snmp"),
                PortDescription( 194, Protocol.TCP, "IRC", "irc"),
                PortDescription( 443, Protocol.TCP, "HTTPS",  "https"),
                PortDescription( 445, Protocol.TCP, "SMB",  "smb"),
                PortDescription( 465, Protocol.TCP, "SMTPS", "smtps"),
                PortDescription( 554, Protocol.TCP, "RTSP",  "rtsp"),
                PortDescription( 873, Protocol.TCP, "RSYNC", "rsync"),
                PortDescription( 993, Protocol.TCP, "IMAPS", "imaps"),
                PortDescription( 995, Protocol.TCP, "POP3S", "pop3s"),
                PortDescription( 3389, Protocol.TCP, "RDP", "rdp"),
                PortDescription( 5631, Protocol.TCP, "PC Anywhere",  "pc-anywhere"),
                PortDescription( 3306, Protocol.TCP, "MySQL", "mysql"),
                PortDescription( 5432, Protocol.TCP, "PostgreSQL", "postgresql"),
                PortDescription( 5900, Protocol.TCP, "VNC", "vnc"),
                PortDescription( 6379, Protocol.TCP, "Redis", "redis"),
                PortDescription( 8333, Protocol.TCP, "Bitcoin", "bitcoin"),
                PortDescription( 11211, Protocol.TCP, "Memcached", "memcached"),
                PortDescription( 25565, Protocol.TCP, "Minecraft", "minecraft")
        )
    }

    /**
     * Enum representing the network protocols.
     */
    enum class Protocol {
        TCP, UDP
    }
}