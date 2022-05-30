package ru.netology.data

data class User(
    val name: String,
    val id: Int = userIdAutoIncrement,

) {
    companion object {
        private var userIdAutoIncrement = 1
    }
}