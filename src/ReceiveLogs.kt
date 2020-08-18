import com.rabbitmq.client.*
import java.nio.charset.StandardCharsets

fun main() {
    val exchangeName = "logs"

    val factory = ConnectionFactory()
    factory.host = "localhost"
    val connection = factory.newConnection()
    val channel = connection.createChannel()

    channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT)
    val queueName = channel.queueDeclare().queue
    channel.queueBind(queueName, exchangeName, "")

    println(" [*] Waiting for messages. To exit press CTRL+C")

    val deliverCallback = DeliverCallback { _, delivery ->
        val message = String(delivery.body, StandardCharsets.UTF_8)
        println(" [x] Received '$message'")
    }
    channel.basicConsume(queueName, true, deliverCallback, CancelCallback { })
}
