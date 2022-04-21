package vttp2022.friendmanagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import vttp2022.friendmanagement.model.Update;
import vttp2022.friendmanagement.respository.FriendRepository;
import vttp2022.friendmanagement.service.FriendService;

@Controller
@RequestMapping(path="/")
public class FriendController {
    
    @Autowired
    private FriendService svc;

    @Autowired
    private FriendRepository repo;

    @GetMapping("/")
    public ModelAndView showIndex(){
        ModelAndView mvc = new ModelAndView("index");
        if (svc.getAllEmails().isEmpty()){
            List<String> list = new ArrayList<>();
            list.add("No users found.");
            mvc.addObject("emailList", list);
        }            
        else{
            mvc.addObject("emailList", svc.getAllEmails());
        }
        System.out.println(svc.getAllEmails().toString());
        return mvc;
    } 

    @PostMapping("/addFriend")
    public ModelAndView showAddFriend(
            @RequestParam String primaryEmail, 
            @RequestParam String friendEmail
        ){
        svc.addFriendConnection(primaryEmail, friendEmail);
        ModelAndView mvc = new ModelAndView("index");
        mvc.addObject("emailList", svc.getAllEmails());
        return mvc;
    } 

    @GetMapping("/friends")
    public ModelAndView getfriends(){
        ModelAndView mvc = new ModelAndView("friends");
        mvc.addObject("emailList", new ArrayList<>());
        return mvc;
    } 

    @PostMapping("/friends")
    public ModelAndView showfriendList(@RequestParam String email){
        ModelAndView mvc = new ModelAndView("friends");

        if (!svc.getAllEmails().contains(email)){
            List<String> list = new ArrayList<>();
            list.add("Email not found.");
            mvc.addObject("emailList", list);
            return mvc;
        }
        if (svc.getFriendList(email).isEmpty()){
            List<String> list = new ArrayList<>();
            list.add("No friends found.");
            mvc.addObject("emailList", list);
            return mvc;
        }            
        
        mvc.addObject("emailList", svc.getFriendList(email));
    
        return mvc;
    } 

    @GetMapping("/mutualFriends")
    public ModelAndView getMutualFriends(){
        ModelAndView mvc = new ModelAndView("mutualFriends");
        mvc.addObject("emailList", new ArrayList<>());
        return mvc;
    } 

    @PostMapping("/mutualFriends")
    public ModelAndView showMutualFriends(@RequestParam String firstEmail, @RequestParam String secondEmail){
        ModelAndView mvc = new ModelAndView("mutualFriends");

        if (!svc.getAllEmails().contains(firstEmail) || !svc.getAllEmails().contains(secondEmail)){
            List<String> list = new ArrayList<>();
            list.add("One of the emails do not exist in the system.");
            mvc.addObject("emailList", list);
            return mvc;
        }
        if (svc.getCommonFriends(firstEmail, secondEmail).isEmpty()){
            List<String> list = new ArrayList<>();
            list.add("No mutual friends found.");
            mvc.addObject("emailList", list);
            return mvc;
        }            
        
        mvc.addObject("emailList", svc.getCommonFriends(firstEmail, secondEmail));
    
        return mvc;
    } 

    @GetMapping("/postUpdates")
    public ModelAndView getPostUpdates(){
        ModelAndView mvc = new ModelAndView("postUpdates");
        mvc.addObject("emailList", new ArrayList<>());
        return mvc;
    } 

    @PostMapping("/postUpdates")
    public ModelAndView showPostUpdates(
            @RequestParam String updateContent, 
            @RequestParam String email
        ){
        ModelAndView mvc = new ModelAndView("postUpdates");
        if (!svc.getAllEmails().contains(email)){
            List<String> list = new ArrayList<>();
            list.add("Email do not exist in the system.");
            mvc.addObject("emailList", list);
            return mvc;
        }

        if (!svc.postUpdate(updateContent, email)){
            List<String> list = new ArrayList<>();
            list.add("Update Failed To Post.");
            mvc.addObject("emailList", list);
            return mvc;
        }

        List<String> list = new ArrayList<>();
        list.add("Updated Posted.");
        mvc.addObject("emailList", list);       
        return mvc;
    } 

    @GetMapping("/updates")
    public ModelAndView getUpdates(){
        ModelAndView mvc = new ModelAndView("updates");
        mvc.addObject("email", "");
        mvc.addObject("updateList", new ArrayList<>());
        return mvc;
    } 

    @PostMapping("/updates")
    public ModelAndView showUpdates(@RequestParam String email){
        ModelAndView mvc = new ModelAndView("updates");
        if (!svc.getAllEmails().contains(email)){
            List<Update> list = new ArrayList<>();
            Update u = new Update();
            u.setContent("");
            u.setSenderEmail("Email do not exist in the system.");
            list.add(u);
            mvc.addObject("email", email);
            mvc.addObject("updateList", list);
            return mvc;
        }

        if (svc.getUpdateList(email).isEmpty()){
            List<Update> list = new ArrayList<>();
            Update u = new Update();
            u.setSenderEmail("No updates found for " + email + ".");
            u.setContent("");
            list.add(u);
            mvc.addObject("email", email);
            mvc.addObject("updateList", list);
            return mvc;
        }

        mvc.addObject("email", email);
        List<Update> list = svc.getUpdateList(email);
        mvc.addObject("updateList", list);       
        return mvc;
    } 

    @GetMapping("/searchContacts")
    public ModelAndView getSearchForm(){
        ModelAndView mvc = new ModelAndView("search");
        mvc.addObject("primaryEmail", "");
        mvc.addObject("searchEmail", "");
        mvc.addObject("statusList", new ArrayList<>());
        return mvc;
    } 

    @PostMapping("/searchContacts")
    public ModelAndView showSearch(
            @RequestParam String primaryEmail, 
            @RequestParam String searchEmail
        ){
        ModelAndView mvc = new ModelAndView("search");
        if (!svc.getAllEmails().contains(primaryEmail) || !svc.getAllEmails().contains(searchEmail)){
            List<String> list = new ArrayList<>();
            list.add("One of the emails do not exist in the system.");
            mvc.addObject("queryStatus", "failed");
            mvc.addObject("primaryEmail", primaryEmail);
            mvc.addObject("searchEmail", searchEmail);
            mvc.addObject("statusList", list);
            return mvc;
        }

        if (svc.getStatus(primaryEmail, searchEmail).isEmpty()){
            List<String> list = new ArrayList<>();
            list.add("No relation found between the emails");
            mvc.addObject("queryStatus", "failed");
            mvc.addObject("primaryEmail", primaryEmail);
            mvc.addObject("searchEmail", searchEmail);
            mvc.addObject("statusList", list);
            return mvc;
        }
        
        List<String> list = svc.getStatus(primaryEmail, searchEmail);
        mvc.addObject("queryStatus", "passed");
        mvc.addObject("primaryEmail", primaryEmail);
        mvc.addObject("searchEmail", searchEmail);
        mvc.addObject("statusList", list);       
        return mvc;
    } 

    @PostMapping("/updateStatus")
    public ModelAndView updateStatus(
            @RequestParam String status,
            @RequestParam String primaryEmail,
            @RequestParam String searchEmail
        ){
        ModelAndView mvc = new ModelAndView("search");
        String updateStatus = null;
        switch (status) {
            case "friend":
                if (svc.addFriendConnection(primaryEmail,searchEmail)){
                    updateStatus = "Friend added.";
                } else if (repo.verifyFriend(primaryEmail, searchEmail)){
                    updateStatus = "You are already friends.";
                } else {
                    updateStatus = "Failed to add friend.";
                }
                break;

            case "subscribe":
                if (svc.subscribeToUpdates(primaryEmail,searchEmail)){
                    updateStatus = "Successfully subscribed.";
                } else if (repo.verifySubscribe(primaryEmail, searchEmail)) {
                    updateStatus = "You have already subscribed to this email.";
                } else {
                    updateStatus = "Failed to subscribe.";
                }
                break;

            case "block":
                if (svc.blockUpdates(primaryEmail,searchEmail)){
                    updateStatus = "Successfully blocked.";
                } else if (repo.verifyBlock(primaryEmail, searchEmail)) {
                    updateStatus = "You have already blocked this email.";
                } else {
                    updateStatus = "Failed to block this email.";
                }
                break;
            
            default:
                updateStatus =  "Failed to Update";
        }

        List<String> list = svc.getStatus(primaryEmail, searchEmail);
        mvc.addObject("queryStatus", "passed");
        mvc.addObject("updateStatus", updateStatus);
        mvc.addObject("primaryEmail", primaryEmail);
        mvc.addObject("searchEmail", searchEmail);
        mvc.addObject("statusList", list);  
        return mvc;
    } 
}
