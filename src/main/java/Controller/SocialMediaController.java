package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {

    private MessageService messageService;
    private AccountService accountService;

    public SocialMediaController() {
        this.messageService = new MessageService();
        this.accountService = new AccountService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.post("/messages", this::postNewMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesByAccountIdHandler);
        app.post("/login", this::accountLoginHandler);
        app.post("/register", this::postNewAccountHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /**
     * Handler to retrieve all messages.
     * @param context
     */
    private void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages).status(200);
    }

    /**
     * Handler for getting a message by ID.
     * @param context a context object.
     */
    private void getMessageByIdHandler(Context context) {
        Message message = messageService.getMessageById(Integer.parseInt(context.pathParam("message_id")));
        if (message == null) {
            context.json("");
        } else {
            context.json(message).status(200);
        }
    }

    /**
     * Handler for adding new message.
     * @param context a context object.
     * @throws JsonProcessingException exception thrown if json not converted to object properly. 
     */
    private void postNewMessageHandler(Context context) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(context.body(), Message.class);
        Message addedMessage = messageService.addMessage(message);
        if (addedMessage == null) {
            context.status(400);
        } else {
            context.json(addedMessage).status(200);
        }
    }

    /**
     * Handler for updating a message by ID. 
     * @param context a context object.
     * @throws JsonProcessingException exception thrown if json not converted to object properly.
     */
    private void updateMessageByIdHandler(Context context) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Integer id = Integer.parseInt(context.pathParam("message_id"));
        Message message = om.readValue(context.body(), Message.class);
        Message updatedMessage = messageService.updateMessageById(id, message.getMessage_text());
        if (updatedMessage == null) {
            context.status(400);
        } else {
            context.json(updatedMessage).status(200);
        }
    }

    /**
     * Handler to delete a message by ID.
     * @param context a context object.
     */
    private void deleteMessageByIdHandler(Context context) {
        Message message = messageService.deleteMessageById(Integer.parseInt(context.pathParam("message_id")));
        if (message == null) {
            context.json("").status(200);
        } else {
            context.json(message).status(200);
        }
    }

    /**
     * Handler for retrieving all messages using account ID.
     * @param context a context object.
     */
    private void getAllMessagesByAccountIdHandler(Context context) {
        List<Message> messages = messageService.getAllMessagesByAccountId(Integer.parseInt(context.pathParam("account_id")));
        if (messages == null) {
            context.json("").status(200);
        } else {
            context.json(messages);
        }
    }

    /**
     * Handler for account login.
     * @param context a context object.
     * @throws JsonProcessingException exception thrown if json not converted to object properly.
     */
    private void accountLoginHandler(Context context) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(context.body(), Account.class);
        Account validAccount = accountService.accountLogin(account);
        if (validAccount == null) {
            context.status(401);
        } else {
            context.json(validAccount).status(200);
        }
    }

    /**
     * Handler for new account creating.
     * @param context a context object.
     * @throws JsonProcessingException exception thrown if json not converted to object properly.
     */
    private void postNewAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Account account = om.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        if (addedAccount == null) {
            context.status(400);
        } else {
            context.json(addedAccount).status(200);
        }  
    }
}