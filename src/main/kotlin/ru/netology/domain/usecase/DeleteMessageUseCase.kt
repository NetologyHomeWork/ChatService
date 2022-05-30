package ru.netology.domain.usecase

import ru.netology.domain.objects.Chat
import ru.netology.domain.objects.DirectMessage

class DeleteMessageUseCase {

    fun deleteMessage(
        message: DirectMessage,
        getChatById: GetChatByIdUseCase,
        chatsMap: MutableMap<Chat, List<DirectMessage>>,
        getMessageById: GetMessageByIdUseCase
    ) {
        val chat = getChatById.getChatById(message.chatId, chatsMap)
        val check = getMessageById.getMessageById(message.id, chatsMap)
        val currentList = chatsMap.getOrDefault(chat, listOf()) - check
        if (currentList.isEmpty()) {
            chatsMap.remove(chat)
        } else {
            chatsMap[chat] = currentList
        }
    }
}