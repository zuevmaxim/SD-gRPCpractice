package ru.sd.practice

data class User(val name: String)

data class Parcel(val time: Long, val user: User, val text: String) {
    fun toBytes(): ByteArray {
        return ByteArray(2)
    }

    companion object {
        fun fromBytes(bytes: ByteArray): Parcel {
            return Parcel(0, User(""), "")
        }
    }
}
