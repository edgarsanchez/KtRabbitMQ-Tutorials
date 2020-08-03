import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.MessageProperties
import java.nio.charset.StandardCharsets

fun main(argv: Array<String>) {
    val taskQueueName = "task_queue"
    val factory = ConnectionFactory()
    factory.host = "localhost"
    factory.newConnection().use { connection ->
        connection.createChannel().use { channel ->
            channel.queueDeclare(taskQueueName, true, false, false, null)

            val message = argv.joinToString(" ")
            channel.basicPublish("", taskQueueName, MessageProperties.PERSISTENT_TEXT_PLAIN, message.toByteArray(StandardCharsets.UTF_8))

            println(" [x] Sent '$message'")
        }
    }
}
