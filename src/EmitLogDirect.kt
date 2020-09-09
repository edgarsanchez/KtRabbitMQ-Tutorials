import com.rabbitmq.client.BuiltinExchangeType
import com.rabbitmq.client.ConnectionFactory
import java.nio.charset.StandardCharsets

fun main(argv: Array<String>) {
    val exchangeName = "direct_logs"

    val factory = ConnectionFactory()
    factory.host = "localhost"
    factory.newConnection().use { connection ->
        connection.createChannel().use { channel ->
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT)
            val severity = getSeverity(argv)
            val message = getMessage(argv)
            channel.basicPublish(exchangeName, severity, null, message.toByteArray(StandardCharsets.UTF_8))
            println(" [x] Sent '$severity':'$message'")
        }
    }
}

private fun getSeverity(strings: Array<String>): String = if (strings.isEmpty()) "info" else strings[0]

private fun getMessage(strings: Array<String>): String =
        if (strings.size < 2) "Hello World!" else strings.slice(1 until strings.size).joinToString(" ")
