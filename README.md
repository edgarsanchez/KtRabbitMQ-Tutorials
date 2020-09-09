# KtRabbitMQ-Tutorials
Kotlin implementation of [RabbitMQ tutorials](https://www.rabbitmq.com/getstarted.html), specifically based on the [Java samples](https://github.com/rabbitmq/rabbitmq-tutorials/tree/master/java) available in github. So far I've got implemented:

* The ["Hello World!" tutorial](https://www.rabbitmq.com/tutorials/tutorial-one-java.html)
  * The src/Send.kt file contains the Sender console app
  * The src/Recv.kt folder contains the Receive console app
* The [Work queues tutorial](https://www.rabbitmq.com/tutorials/tutorial-two-java.html)
  * The src/NewTask.kt file contains a console app that publishes a message to the task_queue
  * The src/Worker.kt file contains a console app that subscribes to the task_queue. We can have several workers active processing the messages in the queue.
* The [Publish/Subscribe tutorial](https://www.rabbitmq.com/tutorials/tutorial-three-java.html)
  * The src/EmitLog.kt file contains a console app that publishes a message to the logs exchange
  * The src/ReceiveLogs.kt file contains a console app that subscribes to the exchange with a temporary queue (many ReceiveLogs apps can be run simultaneously, each one will get its own temporary queue)
* The [Routing tutorial](https://www.rabbitmq.com/tutorials/tutorial-four-java.html)
  * The src/EmitLogDirect.kt file contains a console app that publishes a message to a direct exchange with a specific routing key
  * The src/ReceiveLogsDirect.kt file contains a console app that subscribes to the exchange with a temporary queue binded to a specific routing key (many ReceiveLogsDirect apps can be run simultaneously, each one will get its own temporary queue)

All the examples assume a RabbitMQ server installation on the local computer.

Comments and feedback welcomed!