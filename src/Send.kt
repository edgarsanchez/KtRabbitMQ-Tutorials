import com.rabbitmq.client.ConnectionFactory
import java.nio.charset.StandardCharsets

fun main() {
    val queueName = "hello"
    val factory = ConnectionFactory()
    factory.host = "localhost"
    factory.newConnection().use { connection ->
        connection.createChannel().use { channel ->
            channel.queueDeclare(queueName, false, false, false, null)
            val message = "Hello World!"
            channel.basicPublish("", queueName, null, message.toByteArray(StandardCharsets.UTF_8))
            println(" [x] Sent '$message'")
        }
    }
}
