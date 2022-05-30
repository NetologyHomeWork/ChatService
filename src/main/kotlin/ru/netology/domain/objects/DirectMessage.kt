package ru.netology.domain.objects

import java.util.*

data class DirectMessage(
    val ownerId: Int,
    val companionId: Int,
    val chatId: Int,
    val text: String,
    val id: Int = autoIncrementDirectMessageId,
    val isIncoming: Boolean = false,
    val isRead: Boolean = true,
    val created: Long = Date().time
) {
    companion object {
        var autoIncrementDirectMessageId = 1
    }

    init {
        ++autoIncrementDirectMessageId
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this.hashCode() != other.hashCode() || other !is DirectMessage) return false
        return this.chatId == other.chatId && this.ownerId == other.ownerId && this.companionId == other.companionId
                && this.text == other.text && this.id == other.id
    }

    override fun hashCode(): Int {
        var result = ownerId
        result = 31 * result + companionId
        result = 31 * result + chatId
        result = 31 * result + text.hashCode()
        result = 31 * result + id
        return result
    }


}