package ru.sd.practice

import java.nio.ByteBuffer

data class User(val name: String)

data class Parcel(val time: Long, val user: User, val text: String) {
    fun toBytes(): ByteArray {
        val userBytes = user.name.toByteArray()
        val textBytes = text.toByteArray()
        return ByteBuffer.allocate(8 + 4 + userBytes.size + textBytes.size)
            .putLong(time)
            .putInt(user.name.length)
            .put(userBytes)
            .put(textBytes)
            .array()
    }

    companion object {
        fun fromBytes(bytes: ByteArray): Parcel {
            val buffer = ByteBuffer.wrap(bytes)
            val time = buffer.long
            val userNameLength = buffer.int
            var tmpArray = ByteArray(userNameLength)
            buffer.get(tmpArray, 0, userNameLength)
            val userName = String(tmpArray)
            tmpArray = ByteArray(buffer.remaining())
            buffer.get(tmpArray)
            val text = String(tmpArray)
            return Parcel(time, User(userName), text)
        }
    }
}
