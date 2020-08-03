import com.rabbitmq.client.CancelCallback
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.DeliverCallback
import java.nio.charset.StandardCharsets

fun main() {
    val taskQueueName = "task_queue"
    val factory = ConnectionFactory()
    factory.host = "localhost"
    val connection = factory.newConnection()
    val channel = connection.createChannel()

    channel.queueDeclare(taskQueueName, true, false, false, null)
    channel.basicQos(1)

    val deliverCallback = DeliverCallback { _, delivery ->
        val message = delivery.body.toString(StandardCharsets.UTF_8)
        println(" [x] Received '$message'")
        try {
            doWork(message)
        } finally {
            println(" [x] Done")
            channel.basicAck(delivery.envelope.deliveryTag, false)
        }
    }

    println(" [*] Waiting for messages. To exit press CTRL+C")
    channel.basicConsume(taskQueueName, false, deliverCallback, CancelCallback { })
}

private fun doWork(task: String) = task.forEach { if (it == '.') Thread.sleep(1000) }
