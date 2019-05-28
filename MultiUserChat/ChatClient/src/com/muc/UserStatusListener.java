package com.muc;

/**
 * provides the abstraction methods for listening to a user's status
 */

public interface UserStatusListener {
    public void online(String login);
    public void offline(String login);
}

