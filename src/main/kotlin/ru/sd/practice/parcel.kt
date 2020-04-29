package ru.sd.practice

import java.nio.ByteBuffer

data class User(val name: String)

data class Parcel(val time: Long, val user: User, val text: String) {
    fun toBytes(): ByteArray {
        return ByteBuffer.allocate(8 + 4 + user.name.length + text.length)
            .putLong(time)
            .putInt(user.name.length)
            .put(user.name.toByteArray())
            .put(text.toByteArray())
            .array()
    }

    companion object {
        fun fromBytes(bytes: ByteArray): Parcel {
            val buffer = ByteBuffer.wrap(bytes)
            val time = buffer.long
            val userNameLength = buffer.int
            var tmpArray = byteArrayOf()
            buffer.get(tmpArray, 0, userNameLength)
            val userName = String(tmpArray)
            tmpArray = byteArrayOf()
            buffer.get(tmpArray)
            val text = String(tmpArray)
            return Parcel(time, User(userName), text)
        }
    }
}
