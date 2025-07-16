package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Model.Message;
import Util.ConnectionUtil;

public class MessageDAO {

    /**
     * Function to retrieve all messages from the database.
     * @return list containing all messages or null if query failed.
     */
    public List<Message> getAllMessages() {

        Connection connection = ConnectionUtil.getConnection();
        List<Message> listOfMessages = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Message;";

            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                listOfMessages.add(message);
            }
            
            return listOfMessages;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Function to retrieve message by specific ID.
     * @param id the message id.
     * @return the message or null if not found.
     */
    public Message getMessageById(int id) {

        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "SELECT * FROM Message WHERE message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, id);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return message;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Function to insert message into database.
     * @param message a message object.
     * @return the inserted message or null if query failed.
     */
    public Message insertMessage(Message message) {

        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "INSERT INTO Message(posted_by, message_text, time_posted_epoch) VALUES(?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, message.getPosted_by());
            ps.setString(2, message.getMessage_text());
            ps.setLong(3, message.getTime_posted_epoch());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                int generated_pkey = rs.getInt(1);
                return new Message(generated_pkey, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }   

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Function to update a specified message from given id.
     * @param id the specific message id.
     * @param message_text the updated text.
     * @return the updated record or null if query failed.
     */
    public Message updateMessageById(int id, String message_text) {

        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, message_text);
            ps.setInt(2, id);
            ps.executeUpdate();

            String selectSql = "SELECT * FROM Message WHERE message_id = ?;";
            PreparedStatement selectPs = connection.prepareStatement(selectSql);

            selectPs.setInt(1, id);

            ResultSet rs = selectPs.executeQuery();

            while (rs.next()) {
                Message updatedMessage = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                return updatedMessage;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Function to delete message given specified ID. 
     * @param id the specific message ID.
     */
    public void deleteMessageById(int id) {

        Connection connection = ConnectionUtil.getConnection();

        try {
            String sql = "DELETE * FROM Message WHERE message_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, id);
            ps.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Function to retrieve all messages for the specified account.
     * @param account_id the specific account ID.
     * @return list of all messages or null if query failed.
     */
    public List<Message> getAllMessagesByAccountId(int account_id) {

        Connection connection = ConnectionUtil.getConnection();
        List<Message> listOfMessages = new ArrayList<Message>();

        try {
            String sql = "SELECT * FROM Message WHERE posted_by = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, account_id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                listOfMessages.add(message);
            }
            return listOfMessages;
     
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
