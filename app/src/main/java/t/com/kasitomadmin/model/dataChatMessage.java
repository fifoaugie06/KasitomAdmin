package t.com.kasitomadmin.model;

import java.util.Date;

public class dataChatMessage {
    private String messageText;
    private String messageUser;
    private String messageUserId;
    private String messageUserPhotoUri;
    private String key;
    private long messageUserTime;

    public dataChatMessage(){

    }

    public dataChatMessage(String messageText, String messageUser, String messageUserId, String messageUserPhotoUri, String key) {
         this.messageText = messageText;
         this.messageUser = messageUser;
         this.messageUserId = messageUserId;
         this.messageUserPhotoUri = messageUserPhotoUri;
         this.key = key;
         messageUserTime = new Date().getTime();
    }


    public String getMessageUserId() {
        return messageUserId;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public String getMessageUserPhotoUri() {
        return messageUserPhotoUri;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public long getMessageUserTime() {
        return messageUserTime;
    }
}
