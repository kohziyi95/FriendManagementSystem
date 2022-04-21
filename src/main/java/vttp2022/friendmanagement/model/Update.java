package vttp2022.friendmanagement.model;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Update {

    private String senderEmail;
    private String content;

    public String getSenderEmail() {
        return senderEmail;
    }
    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }
  

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public static Update createUpdate(SqlRowSet result){
        Update update = new Update();
        update.setSenderEmail(result.getString("sender_email"));
        update.setContent(result.getString("update_content"));
        return update;
    }
    
}
