package vttp2022.friendmanagement.respository;

public interface Queries {

    // Verify if friend/block/subscribe relationship exists
    public static final String SQL_VERIFY_FRIEND = 
        "select count(*) from friend_relation where primary_email=? and friend_email=?";

    public static final String SQL_VERIFY_BLOCK = 
        "select count(*) from block_relation where primary_email=? and friend_email=?";

    public static final String SQL_VERIFY_SUBSCRIBE = 
        "select count(*) from subscribe_relation where primary_email=? and friend_email=?";


    // Select data from database

    public static final String SQL_SELECT_UPDATES_BY_FRIEND = 
        "select * from updates join friend_relation on updates.sender_email = friend_relation.friend_email where friend_relation.primary_email=?";    
    
    public static final String SQL_SELECT_UPDATES_BY_SUBSCRIBE = 
        "select * from updates join subscribe_relation on updates.sender_email = subscribe_relation.friend_email where subscribe_relation.primary_email=?";  
    
    public static final String SQL_SELECT_UPDATES = 
        "select * from updates join friend_relation on updates.sender_email = friend_relation.friend_email join subscribe_relation on updates.sender_email = subscribe_relation.friend_email where friend_relation.primary_email=?";
        
    public static final String SQL_SELECT_FRIENDS_BY_EMAIL = 
        "select * from friend_relation where primary_email=?";

    public static final String SQL_SELECT_EMAILS =
        "select * from friend_info";


    // Insert/Update database
    public static final String SQL_INSERT_FRIEND = 
        "insert into friend_info (email) values (?)";

    public static final String SQL_INSERT_FRIEND_CONNECTION = 
        "insert into friend_relation (primary_email, friend_email) values (? , ?)";

    public static final String SQL_INSERT_BLOCK_CONNECTION = 
        "insert into block_relation (primary_email, friend_email) values (? , ?)";

    public static final String SQL_INSERT_SUBSCRIBE_CONNECTION = 
        "insert into subscribe_relation (primary_email, friend_email) values (? , ?)";

    public static final String SQL_INSERT_UPDATE = 
        "insert into updates (update_content, sender_email) values (? , ?)";
}
  