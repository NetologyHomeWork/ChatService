package ru.netology.domain.usecase

import ru.netology.domain.objects.Chat
import ru.netology.domain.objects.DirectMessage

class ReceiveIncomingMessageUseCase {

    fun receiveIncomingMessage(
        message: DirectMessage,
        getChatById: GetChatByIdUseCase,
        chatsMap: Map<Chat, List<DirectMessage>>
    ): Map<Chat, List<DirectMessage>> {
        return try {
            val chat = getChatById.getChatById(message.chatId, chatsMap)
            val newListMessages =
                chatsMap.getOrDefault(chat, listOf()) + message.copy(
                    ownerId = message.companionId,
                    companionId = message.ownerId,
                    isIncoming = true,
                    isRead = false
                )
            mapOf(chat to newListMessages)
        } catch (e: RuntimeException) {
            val newMessage = message.copy(
                ownerId = message.companionId,
                companionId = message.ownerId,
                isIncoming = true,
                isRead = false
            )
            val newListMessage = listOf(newMessage)
            val newChat = Chat(message.companionId, message.ownerId)
            mapOf(newChat to newListMessage)
        }
    }
}