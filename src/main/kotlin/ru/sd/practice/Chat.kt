package ru.sd.practice

import com.rabbitmq.client.ConnectionFactory

class Chat(private val name: String, private val interactor: Interactor, private val hostname: String) {
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
        val factory = ConnectionFactory().apply {
            host = hostname
        }
        val connection = factory.newConnection()
        val channel = connection.createChannel()
        channel.exchangeDeclare(name, "fanout")
        val queueName: String = channel.queueDeclare().getQueue()
        channel.queueBind(queueName, name, "")

        channel.basicConsume(queueName, { _, delivery ->
            val parcel = Parcel.fromBytes(delivery.body)
            interactor.addMessage(parcel)
        }) { _ -> }
    }
}
