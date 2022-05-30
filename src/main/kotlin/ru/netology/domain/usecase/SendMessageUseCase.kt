package ru.netology.domain.usecase

import ru.netology.domain.objects.Chat
import ru.netology.domain.objects.DirectMessage

class SendMessageUseCase {

    fun sendMessage(
        chat: Chat,
        text: String,
        chatsMap: Map<Chat, List<DirectMessage>>
    ): Map<Chat, List<DirectMessage>> {
        if (!chatsMap.containsKey(chat)) {
            throw RuntimeException("Chat was not found")
        }
        val newMessage = DirectMessage(chat.ownerId, chat.companionId, chat.id, text)
        val currentListMessages = chatsMap.getOrDefault(chat, listOf()) + newMessage
        return mapOf(chat to currentListMessages)
    }
}