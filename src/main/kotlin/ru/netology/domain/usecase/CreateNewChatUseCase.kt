package ru.netology.domain.usecase

import ru.netology.domain.objects.Chat
import ru.netology.domain.objects.DirectMessage

class CreateNewChatUseCase {

    fun createNewChat(
        userId: Int,
        companionId: Int,
        text: String,
        chatsMap: Map< Chat, List<DirectMessage>>
    ): Map<Chat, List<DirectMessage>> {
        val newChat = Chat(userId, companionId)
        if (chatsMap.containsKey(newChat)) throw RuntimeException("Chat already exists")
        val message = DirectMessage(userId, companionId, newChat.id, text)
        val listMessage = listOf(message)
        return mapOf(newChat to listMessage)
    }
}