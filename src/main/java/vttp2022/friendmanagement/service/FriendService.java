package vttp2022.friendmanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.friendmanagement.model.Update;
import vttp2022.friendmanagement.respository.FriendRepository;

@Service
public class FriendService {
    
    @Autowired
    private FriendRepository repo;

    public List<String> getAllEmails (){
        List<String> emailList = repo.getEmailList();
        return emailList;
    }

    public List<String> getFriendList (String email){
        List<String> friendList = repo.getFriendList(email);
        return friendList;
    }

    public List<Update> getUpdateList (String email){
        List<Update> updateList = repo.getFriendUpdateList(email);
        List<Update> subscribeList = repo.getSubscribeUpdateList(email);
        for (Update subscriber : subscribeList){
            if (!updateList.contains(subscriber))
                updateList.add(subscriber);
        }
        
        for (Update update: updateList){
            if (repo.verifyBlock(email,update.getSenderEmail())){
                updateList.remove(update);
            }
        }

        return updateList;
    }

    public boolean addFriendConnection (String primaryEmail, String friendEmail){
        if (repo.verifyBlock(primaryEmail,friendEmail) || repo.verifyBlock(friendEmail,primaryEmail)){
            return false;
        }
        
        if (primaryEmail.equals(friendEmail))
            return false;

        List<String> emailList = getAllEmails();

        if (!emailList.contains(primaryEmail))
            repo.setFriendInfo(primaryEmail);

        if (!emailList.contains(friendEmail))
            repo.setFriendInfo(friendEmail);
        return repo.setFriendConnection(primaryEmail, friendEmail) && repo.setFriendConnection(friendEmail, primaryEmail);
    }

    public boolean subscribeToUpdates (String primaryEmail, String friendEmail){
        if (repo.verifyBlock(primaryEmail,friendEmail) || repo.verifyBlock(friendEmail,primaryEmail))
            return false;
        
        if (repo.verifySubscribe(primaryEmail, friendEmail))
            return false;

        return repo.setSubscribeConnection(primaryEmail, friendEmail);
    }

    public boolean blockUpdates (String primaryEmail, String friendEmail){
        return repo.setBlockConnection(primaryEmail, friendEmail);
    }

    public List<String> getCommonFriends (String firstEmail, String secondEmail) {
        List<String> firstFriendList = repo.getFriendList(firstEmail);
        List<String> secondFriendList = repo.getFriendList(secondEmail);
        List<String> commonFriendList = new ArrayList<>();
        for (String friend : firstFriendList){
            if (secondFriendList.contains(friend) && !commonFriendList.contains(friend))
                commonFriendList.add(friend);
        }
        return commonFriendList;
    }

    public boolean postUpdate (String content, String senderEmail){
        return repo.setUpdates(content, senderEmail);
    }

    public List<String> getStatus (String primaryEmail, String friendEmail){
        List<String> status = new ArrayList<>();

        if (repo.verifyFriend(primaryEmail, friendEmail))
            status.add("You are friends with this email");
        
        if (repo.verifySubscribe(primaryEmail, friendEmail))
            status.add("You have subscribed to this email");

        if (repo.verifyBlock(primaryEmail, friendEmail))
            status.add("You have blocked this email");

        return status;
    }
}
