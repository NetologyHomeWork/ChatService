package ru.netology.domain.usecase

import ru.netology.domain.objects.Chat
import ru.netology.domain.objects.DirectMessage

class EditMessageUseCase {

    fun editMessage(
        message: DirectMessage,
        text: String,
        useCase: GetChatByIdUseCase,
        chatsMap: Map<Chat, List<DirectMessage>>,
        getMessageById: GetMessageByIdUseCase
    ): Map<Chat, List<DirectMessage>> {
        val chat = useCase.getChatById(message.chatId, chatsMap)
        val listCurrentMessage = chatsMap.getOrDefault(chat, listOf())
        val updateMessage = getMessageById.getMessageById(message.id, chatsMap).copy(text = text)
        val newListMessage = listCurrentMessage.filter { it.id != message.id } + updateMessage
        return mapOf(chat to newListMessage)
    }
}