import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import java.nio.charset.StandardCharsets

fun main() {
    val queueName = "hello"

    val factory = ConnectionFactory()
    factory.host = "localhost"
    val connection = factory.newConnection()
    val channel = connection.createChannel()

    channel.queueDeclare(queueName, false, false, false, null)
    println(" [*] Waiting for messages. To exit press CTRL+C")

    val deliverCallback = DeliverCallback { _, delivery ->
        val message = String(delivery.body, StandardCharsets.UTF_8)
        println(" [x] Received '$message'")
    }
    channel.basicConsume(queueName, true, deliverCallback, CancelCallback { })
}
