package ru.sd.practice

data class User(val name: String)

data class Parcel(val time: Long, val user: User, val text: String)
