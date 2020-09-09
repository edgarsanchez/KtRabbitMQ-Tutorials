import com.rabbitmq.client.*
import java.nio.charset.StandardCharsets
import kotlin.system.exitProcess

fun main(argv: Array<String>) {
    val exchangeName = "direct_logs"

    val factory = ConnectionFactory()
    factory.host = "localhost"
    val connection = factory.newConnection()
    val channel = connection.createChannel()

    channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT)
    val queueName = channel.queueDeclare().queue
    if (argv.isEmpty()) {
        System.err.println("Usage: ReceiveLogsDirect [info] [warning] [error]")
        exitProcess(1)
    }
    for (severity in argv) {
        channel.queueBind(queueName, exchangeName, severity)
    }

    println(" [*] Waiting for messages. To exit press CTRL+C")

    val deliverCallback = DeliverCallback { _, delivery ->
        val message = String(delivery.body, StandardCharsets.UTF_8)
        println(" [x] Received '" + delivery.envelope.routingKey + "':'" + message + "'")
    }
    channel.basicConsume(queueName, true, deliverCallback, CancelCallback { })
}
