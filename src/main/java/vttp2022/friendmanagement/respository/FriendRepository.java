package vttp2022.friendmanagement.respository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.friendmanagement.model.Update;

import static vttp2022.friendmanagement.respository.Queries.*;

import java.util.ArrayList;
import java.util.List;


@Repository
public class FriendRepository {

    @Autowired
    private JdbcTemplate template;

    public boolean setFriendInfo(String email){
        int added = template.update(SQL_INSERT_FRIEND, email);
        return added > 0;
    } 

    public boolean setFriendConnection(String primaryEmail, String friendEmail){
        int added = template.update(SQL_INSERT_FRIEND_CONNECTION, primaryEmail, friendEmail);
        return added > 0;
    } 

    public boolean setBlockConnection(String primaryEmail, String friendEmail){
        int added = template.update(SQL_INSERT_BLOCK_CONNECTION, primaryEmail, friendEmail);
        return added > 0;
    } 

    public boolean setSubscribeConnection(String primaryEmail, String friendEmail){
        int added = template.update(SQL_INSERT_SUBSCRIBE_CONNECTION, primaryEmail, friendEmail);
        return added > 0;
    } 

    public boolean setUpdates(String content, String senderEmail){
        int added = template.update(SQL_INSERT_UPDATE, content, senderEmail);
        return added > 0;
    }



    public boolean verifyFriend(String primaryEmail, String friendEmail){
        final SqlRowSet result = template.queryForRowSet(SQL_VERIFY_FRIEND, primaryEmail, friendEmail);
        result.next();
        return result.getInt("count(*)") > 0;
    }

    public boolean verifyBlock(String primaryEmail, String friendEmail){
        final SqlRowSet result = template.queryForRowSet(SQL_VERIFY_BLOCK, primaryEmail, friendEmail);
        result.next();
        return result.getInt("count(*)") > 0;
    }

    public boolean verifySubscribe(String primaryEmail, String friendEmail){
        final SqlRowSet result = template.queryForRowSet(SQL_VERIFY_SUBSCRIBE, primaryEmail, friendEmail);
        result.next();
        return result.getInt("count(*)") > 0;
    }



    public List<Update> getFriendUpdateList(String primaryEmail){
        final SqlRowSet result = template.queryForRowSet(SQL_SELECT_UPDATES_BY_FRIEND, primaryEmail);
        List<Update> updateList = new ArrayList<>();
        while (result.next()){
            Update update = Update.createUpdate(result);
            updateList.add(update);
        };
        return updateList;
    }

    public List<Update> getSubscribeUpdateList(String primaryEmail){
        final SqlRowSet result = template.queryForRowSet(SQL_SELECT_UPDATES_BY_SUBSCRIBE, primaryEmail);
        List<Update> updateList = new ArrayList<>();
        while (result.next()){
            Update update = Update.createUpdate(result);
            updateList.add(update);
        };
        return updateList;
    }

    public List<String> getFriendList(String primaryEmail){
        final SqlRowSet result = template.queryForRowSet(SQL_SELECT_FRIENDS_BY_EMAIL, primaryEmail);
        List<String> friendList = new ArrayList<>();
        while (result.next()){
            friendList.add(result.getString("friend_email"));
        };
        return friendList;
    }

    public List<String> getEmailList(){
        final SqlRowSet result = template.queryForRowSet(SQL_SELECT_EMAILS);
        List<String> emailList = new ArrayList<>();
        while (result.next()){
            emailList.add(result.getString("email"));
        };
        return emailList;
    }



}
