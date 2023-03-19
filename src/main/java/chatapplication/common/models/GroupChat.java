package chatapplication.common.models;

import chatapplication.server.ChatRoomType;

import java.util.ArrayList;

public class GroupChat extends Chat {

    private ArrayList<User> receivers;
    private String groupName;
    private int groupID;
    private User groupChatOwner;
    private static int lastAssignedID = 1;

    public GroupChat(ArrayList<User> receivers) {
        this.receivers = receivers;
        this.groupChatOwner = receivers.get(0); // Set groupchat owner
        this.groupID = lastAssignedID++;

        for(User user : receivers){
            if(user != null){
                user.getGroupChats().add(this);
            }
        }
    }

    public void addUserToGroup(User user){
        this.receivers.add(user);
        user.getGroupChats().add(this);
    }

    public String getGroupName(){
        return this.groupName;
    }


    public void broadcast(User sender, String message){
        for(User user : receivers){
            System.out.println(sender.equals(user));
            if(user != null){
                user.getHandler().sendMessage(String.format("[%s] %s: %s", groupName, sender.getUsername(), message));
            }
        }
    }

    public void kickUser(User user){
        user.getGroupChats().remove(this); // Remove them from the group
        user.setChatRoomType(ChatRoomType.GLOBAL); // Automatically put them in global chat
        user.getHandler().sendMessage("You have been kicked from the " + this.groupName + " chat.");
    }

    public void setGroupName(String newGroupName){
        this.groupName = newGroupName;
    }

    public String toString(){
        return this.groupName;
    }

    public void setGroupID(int id){
        this.groupID = id;
    }

    public int getGroupID(){
        return this.groupID;
    }

    public ArrayList<User> getReceivers(){
        return this.receivers;
    }

}
