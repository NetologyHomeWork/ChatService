package ru.netology.domain.usecase

import ru.netology.domain.objects.Chat
import ru.netology.domain.objects.DirectMessage

class GetMessageByIdUseCase {

    fun getMessageById(messageId: Int, chatsMap: Map<Chat, List<DirectMessage>>): DirectMessage {
        val listMessage = chatsMap.asSequence().flatMap { it.value }.toList()
        return listMessage.find { it.id ==  messageId} ?: throw RuntimeException("Message was not found")
    }
}