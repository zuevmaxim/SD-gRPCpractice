package ru.sd.practice

import com.rabbitmq.client.ConnectionFactory
import java.io.FileInputStream

import java.security.KeyStore
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory


class Chat(private val name: String,
           private val interactor: Interactor,
           private val hostname: String,
           private val initPort: Int = 5671) {
    private var factory: ConnectionFactory

    init {
        val keyPassphrase = "password".toCharArray()
        val ks = KeyStore.getInstance("PKCS12")
        ks.load(FileInputStream("/home/vitalii/tls-gen/basic/result/client_key.p12"), keyPassphrase)

        val kmf: KeyManagerFactory = KeyManagerFactory.getInstance("SunX509")
        kmf.init(ks, keyPassphrase)

        val trustPassphrase = "trusted".toCharArray()
        val tks = KeyStore.getInstance("JKS")
        tks.load(FileInputStream("/home/vitalii/tls-gen/basic/trusted"), trustPassphrase)

        val tmf: TrustManagerFactory = TrustManagerFactory.getInstance("SunX509")
        tmf.init(tks)

        val c: SSLContext = SSLContext.getInstance("TLSv1.3")
        c.init(kmf.keyManagers, tmf.trustManagers, null)

        interactor.addConsumer { parcel ->
            send(parcel)
        }
        factory = ConnectionFactory().apply {
            host = hostname
            port = initPort
            useSslProtocol(c)
            enableHostnameVerification();
        }
        receive()
    }

    fun send(parcel: Parcel) {
        factory.newConnection().use { connection ->
            connection.createChannel().use { channel ->
                channel.exchangeDeclare(name, "fanout")
                channel.basicPublish(name, "", null, parcel.toBytes())
            }
        }
    }

    private fun receive() {
        factory.newConnection().use { connection ->
            connection.createChannel().use { channel ->
                channel.exchangeDeclare(name, "fanout")
                val queueName: String = channel.queueDeclare().queue
                channel.queueBind(queueName, name, "")

                channel.basicConsume(queueName, { _, delivery ->
                    val parcel = Parcel.fromBytes(delivery.body)
                    interactor.addMessage(parcel)
                }) { _ -> }
            }
        }
    }
}
