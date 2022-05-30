package ru.netology.domain.usecase

import ru.netology.domain.objects.Chat
import ru.netology.domain.objects.DirectMessage

class DeleteChatUseCase {

    fun deleteChat(chat: Chat, chatMap: Map<Chat, List<DirectMessage>>): Chat {
        return if (chatMap.containsKey(chat)) chat
        else throw RuntimeException("Chat was not found")
    }
}