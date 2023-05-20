package com.wise.resource.professionals.marketplace.controller;

import com.wise.resource.professionals.marketplace.to.LogInAccountTO;

/**
 * Used for controllers that only exist after valid authentication in {@link LogInController}
 */
public interface MainView {

    /**
     * Method to help pass the authentication details of the user to other views
     *
     * @param logInAccountTO the authenticated user.
     */
    void setAccountTO(LogInAccountTO logInAccountTO);
}
