package ru.netology

import org.junit.After
import org.junit.Assert
import org.junit.Test
import ru.netology.data.ChatService
import ru.netology.domain.objects.Chat
import ru.netology.domain.objects.DirectMessage
import ru.netology.domain.usecase.GetMessageByIdUseCase

class ChatServiceTest {

    @Test
    fun createNewChat_test() {
        val chatService = ChatService()
        chatService.createNewChat(123, 321, "Hello!")
        val expectedSize = chatService.chatMap.size
        Assert.assertNotEquals(0, expectedSize)
    }

    @After
    fun clear() {
        Chat.autoIncrementChatId = 1
        DirectMessage.autoIncrementDirectMessageId = 1
    }

    @Test(expected = RuntimeException::class)
    fun createNewChat_testException() {
        val chatService = ChatService()
        chatService.createNewChat(123, 321, "Hello!")
        chatService.createNewChat(123, 321, "How are you?")
    }

    @Test
    fun sendMessage_test() {
        val chatService = ChatService()
        chatService.createNewChat(123, 321, "Hello!")
        chatService.sendMessage(Chat(123, 321), "How are you?")
        val expectedSize = chatService.chatMap[Chat(123, 321)]?.size ?: 0
        Assert.assertEquals(2, expectedSize)
    }

    @Test(expected = RuntimeException::class)
    fun sendMessage_testException() {
        val chatService = ChatService()
        chatService.createNewChat(123, 321, "Hello!")
        chatService.sendMessage(Chat(123, 456), "How are you?")
    }

    @Test
    fun editMessage_test() {
        val chatService = ChatService()
        val message = DirectMessage(123, 321, 1, "Hello", id = 2)
        chatService.createNewChat(message.ownerId, message.companionId, message.text)
        val text = "Hi!"
        chatService.editMessage(message, text)
        val expectedText = chatService.chatMap.values.flatten().find { it.id == 2 }
        Assert.assertEquals(expectedText?.text, text)
    }

    @Test(expected = RuntimeException::class)
    fun editMessage_testException() {
        val chatService = ChatService()
        val message = DirectMessage(123, 321, 1, "Hello", id = 55)
        chatService.createNewChat(message.ownerId, message.companionId, message.text)
        val text = "Hi!"
        chatService.editMessage(message, text)
    }

    @Test
    fun deleteChat_test() {
        val chatService = ChatService()
        val chat = Chat(123, 321, id = 1)
        chatService.createNewChat(chat.ownerId, chat.companionId, "Hello!")
        chatService.deleteChat(chat)
        val expectedSize = chatService.chatMap.size
        Assert.assertEquals(0, expectedSize)
    }

    @Test(expected = RuntimeException::class)
    fun deleteChat_testException() {
        val chatService = ChatService()
        val chat = Chat(123, 321, id = 1)
        chatService.createNewChat(321, 254, "Hello!")
        chatService.deleteChat(chat)
    }

    @Test
    fun deleteMessage_test() {
        val chatService = ChatService()
        val getMessageByIdUseCase = GetMessageByIdUseCase()
        chatService.createNewChat(123, 321, "Hello!")
        chatService.sendMessage(Chat(123, 321, id = 1), "How there?")
        val message = getMessageByIdUseCase.getMessageById(2, chatService.chatMap)
        chatService.deleteMessage(message)
        val expectedSize = chatService.chatMap[Chat(123, 321)]?.size ?: 0
        Assert.assertEquals(1, expectedSize)
    }

    @Test
    fun deleteMessage_testDeleteChat() {
        val chatService = ChatService()
        val getMessageByIdUseCase = GetMessageByIdUseCase()
        chatService.createNewChat(123, 321, "Hello!")
        val message = getMessageByIdUseCase.getMessageById(1, chatService.chatMap)
        chatService.deleteMessage(message)
        val expectedSize = chatService.chatMap.size
        Assert.assertEquals(0, expectedSize)
    }

    @Test(expected = RuntimeException::class)
    fun deleteMessage_testException() {
        val chatService = ChatService()
        chatService.createNewChat(123, 321, "Hello!")
        val message = DirectMessage(123, 321, 1, "Hello!", id = 88)
        chatService.deleteMessage(message)
    }

    @Test
    fun receiveIncomingMessage_Test() {
        val chatService = ChatService()
        val getMessageById = GetMessageByIdUseCase()
        chatService.createNewChat(123, 321, "Hello Sergey!")
        chatService.receiveIncomingMessage(DirectMessage(321, 123, 1, "Hi Andrey!"))
        val message = getMessageById.getMessageById(2, chatService.chatMap)
        val expectedMessage = DirectMessage(123, 321, 1, "Hi Andrey!", 2)
        Assert.assertEquals(message, expectedMessage)
    }

    @Test
    fun receiveIncomingMessage_TestCreateNewChat() {
        val chatService = ChatService()
        chatService.receiveIncomingMessage(DirectMessage(321, 123, 1, "Hi Andrey!"))
        val expectedSize = chatService.chatMap.size
        Assert.assertEquals(1, expectedSize)
    }

    @Test
    fun getUnreadChatCountTest() {
        val chatService = ChatService()
        chatService.receiveIncomingMessage(DirectMessage(321, 123, 1, "Hi Andrey!"))
        val expectedResult = chatService.getUnreadChatsCount()
        Assert.assertEquals(1, expectedResult)
    }

    @Test
    fun getListChat_test() {
        val chatService = ChatService()
        chatService.createNewChat(123, 321, "Hello!")
        val expectedResult = chatService.getListChats().size
        Assert.assertEquals(1, expectedResult)
    }

    @Test
    fun getListMessagesFromChat_test() {
        val chatService = ChatService()
        chatService.createNewChat(123, 321, "Hello Sergey")
        chatService.receiveIncomingMessage(DirectMessage(321, 123, 1, "Hi Andrey!"))

        val expectedValue = chatService.getListMessagesFromChat(Chat(123, 321, id = 1)).count { !it.isRead }
        Assert.assertEquals(0, expectedValue)
    }
}