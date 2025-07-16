package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {

    private MessageDAO messageDAO;

    /**
     * No-arg constructor.
     */
    public MessageService() {
        this.messageDAO = new MessageDAO();
    }

    /**
     * Function to retrieve all messages.
     * @return list of messages
     */
    public List<Message> getAllMessages() {
        List<Message> listOfMessages = messageDAO.getAllMessages();
        return listOfMessages;
    }

    /**
     * Function to retieve message by message ID.
     * @param id message ID.
     * @return message from given ID or null if not found.
     */
    public Message getMessageById(int id) {
        Message message = messageDAO.getMessageById(id);
        if (message == null) {
            return null;
        } else {
            return message;
        }
    }

    /**
     * Function to add message. Checks if message text is blank and the message is not greater than 255
     * characters. 
     * @param message a message object.
     * @return added message or null if failed above checks.
     */
    public Message addMessage(Message message) {
        if (!message.getMessage_text().isBlank() && message.getMessage_text().length() < 255) {
            Message addedMessage = this.messageDAO.insertMessage(message);
            return addedMessage;
        } else {
            return null;
        }
    }

    /**
     * Function to update message by ID. Will update only if the message is not blank, the 
     * message length is less than 255 characters and the message exists in the database.
     * @param id the specific message id.
     * @param message_text the updated text.
     * @return the updated message or null if the message fails to update.
     */
    public Message updateMessageById(int id, String message_text) {
        Message message = messageDAO.getMessageById(id);
        if (!message_text.isBlank() && message_text.length() < 255 && message != null) {
            Message updatedMessage = messageDAO.updateMessageById(id, message_text);
            return updatedMessage;
        } else {
            return null;
        }
    }

    /**
     * Function to delete a message by ID.
     * @param id the specific message id.
     * @return the deleted message or null if message did not exist.
     */
    public Message deleteMessageById(int id) {
        Message message = messageDAO.getMessageById(id);
        if (message == null) {
            return null;
        } else {
            messageDAO.deleteMessageById(id);
            return message;
        }
    }

    /**
     * Function to retrieve all messages using account_id.
     * @param account_id the specific account id.
     * @return list of messages for the specified account.
     */
    public List<Message> getAllMessagesByAccountId(int account_id) {
        List<Message> messages = messageDAO.getAllMessagesByAccountId(account_id);
        return messages;
    }
}
