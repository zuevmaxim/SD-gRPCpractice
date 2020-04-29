package ru.sd.practice


class Chat(private val name: String, private val interactor: Interactor, private val host: String) {
    fun send() {
//    val factory = ConnectionFactory()
//    factory.setHost("localhost")
//    factory.newConnection().use({ connection ->
//        connection.createChannel().use({ channel ->
//            channel.exchangeDeclare(EXCHANGE_NAME, "fanout")
//            val message = if (argv.length < 1) "info: Hello World!" else java.lang.String.join(" ", argv)
//            channel.basicPublish(EXCHANGE_NAME, "", null, message.toByteArray(charset("UTF-8")))
//            println(" [x] Sent '$message'")
//        })
//    })
    }

    fun receive() {
//        val factory = ConnectionFactory()
//        factory.host = "localhost"
//        val connection: Connection = factory.newConnection()
//        val channel: Channel = connection.createChannel()
//
//        channel.exchangeDeclare(EXCHANGE_NAME, "fanout")
//        val queueName: String = channel.queueDeclare().getQueue()
//        channel.queueBind(queueName, EXCHANGE_NAME, "")
//
//        println(" [*] Waiting for messages. To exit press CTRL+C")
//
//        val deliverCallback = DeliverCallback { consumerTag: String?, delivery: Delivery ->
//            val message = String(delivery.body, "UTF-8")
//            println(" [x] Received '$message'")
//        }
//        channel.basicConsume(queueName, true, deliverCallback, { consumerTag -> })
    }
}