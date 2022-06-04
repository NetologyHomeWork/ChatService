package ru.netology.domain.usecase

import ru.netology.domain.objects.Chat
import ru.netology.domain.objects.DirectMessage

class GetChatByIdUseCase {

    fun getChatById(chatId: Int, chatsMap: Map<Chat, List<DirectMessage>>): Chat {
        val listChat = chatsMap.asSequence().map { it.key }.toList()
        return listChat.find { it.id == chatId } ?: throw RuntimeException("Chat was not found")
    }
}