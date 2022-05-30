package ru.netology.data

import ru.netology.domain.objects.Chat
import ru.netology.domain.objects.DirectMessage
import ru.netology.domain.usecase.*

class ChatService {
    private val _chatsMap = mutableMapOf<Chat, List<DirectMessage>>()
    val chatMap: Map<Chat, List<DirectMessage>>
        get() = _chatsMap

    private val createNewChatUseCase = CreateNewChatUseCase()
    private val sendMessageUseCase = SendMessageUseCase()
    private val editMessageUseCase = EditMessageUseCase()
    private val getChatByIdUseCase = GetChatByIdUseCase()
    private val deleteChatUseCase = DeleteChatUseCase()
    private val deleteMessageUseCase = DeleteMessageUseCase()
    private val receiveIncomingMessageUseCase = ReceiveIncomingMessageUseCase()
    private val getMessageByIdUseCase = GetMessageByIdUseCase()

    fun createNewChat(userId: Int, companionId: Int, text: String) {
        _chatsMap.putAll(createNewChatUseCase.createNewChat(userId, companionId, text, _chatsMap))
    }

    fun sendMessage(chat: Chat, text: String) {
        _chatsMap.putAll(sendMessageUseCase.sendMessage(chat, text, _chatsMap))
    }

    fun editMessage(message: DirectMessage, text: String) {
        _chatsMap.putAll(editMessageUseCase.editMessage(message, text, getChatByIdUseCase, _chatsMap, getMessageByIdUseCase))
    }

    fun deleteChat(chat: Chat) {
        _chatsMap.remove(deleteChatUseCase.deleteChat(chat, _chatsMap))
    }

    fun deleteMessage(message: DirectMessage) {
        deleteMessageUseCase.deleteMessage(message, getChatByIdUseCase, _chatsMap, getMessageByIdUseCase)
    }

    fun receiveIncomingMessage(message: DirectMessage) {
        _chatsMap.putAll(receiveIncomingMessageUseCase.receiveIncomingMessage(message, getChatByIdUseCase, _chatsMap))
    }

    fun getUnreadChatsCount(): Int {
        return _chatsMap.filter { it.value.all { item -> !item.isRead } }.size
    }

    fun getListChats(): List<Chat> {
        return _chatsMap.map { it.key }
    }

    fun getListMessagesFromChat(chat: Chat): List<DirectMessage> {
        val listMessages = _chatsMap.getOrDefault(chat, listOf())
        return listMessages.map { it.copy(isRead = true) }
    }
}