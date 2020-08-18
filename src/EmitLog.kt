import com.rabbitmq.client.BuiltinExchangeType
import com.rabbitmq.client.ConnectionFactory
import java.nio.charset.StandardCharsets

fun main(argv: Array<String>) {
    val exchangeName = "logs"

    val factory = ConnectionFactory()
    factory.host = "localhost"
    factory.newConnection().use { connection ->
        connection.createChannel().use { channel ->
            channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT)

            val message = if (argv.isEmpty()) "info: Hello World!" else argv.joinToString(" ")

            channel.basicPublish(exchangeName, "", null, message.toByteArray(StandardCharsets.UTF_8))
            println(" [x] Sent '$message'")
        }
    }
}
