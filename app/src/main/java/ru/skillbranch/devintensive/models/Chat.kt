package ru.skillbranch.devintensive.models

class Chat(
    val id: String,
    val members: MutableList<User> = mutableListOf(),
    val messages: MutableList<BaseMessage> = mutableListOf()
) {
    companion object ChatFactory {
        private var lastId = -1
        fun makeChat(): Chat {
            lastId++
            return Chat("$lastId")
        }
    }
}