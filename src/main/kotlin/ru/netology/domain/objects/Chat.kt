package ru.netology.domain.objects

import java.util.*

data class Chat(
    val ownerId: Int,
    val companionId: Int,
    val id: Int = autoIncrementChatId,
    val created: Long = Date().time
) {
    companion object {
        var autoIncrementChatId = 1
    }

    init {
        ++autoIncrementChatId
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other == null || this.hashCode() != other.hashCode() || other !is Chat) return false
        return this.ownerId == other.ownerId && this.companionId == other.companionId
    }

    override fun hashCode(): Int {
        var result = ownerId
        result = 31 * result + companionId
        return result
    }
}