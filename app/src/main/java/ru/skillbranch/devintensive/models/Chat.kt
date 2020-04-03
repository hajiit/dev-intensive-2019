package ru.skillbranch.devintensive.models

class Chat(val id: String) {
    companion object ChatFactory{
        private var lastId = -1
        fun makeChat(): Chat {
            lastId ++
            return Chat("$lastId")
        }
    }
}