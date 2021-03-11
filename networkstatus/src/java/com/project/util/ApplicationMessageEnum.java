/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.project.util;

/**
 *
 * @author Samrit
 */
public enum ApplicationMessageEnum {
    // SUCCESS MESSAGES
    SUCCESS_ADD_RECORD("Added successfully."),
    SUCCESS_DELETE_RECORD("Deleted successfully."),
    SUCCESS_UPDATE_RECORD("Updated successfully."),
    SUCCESS_LOAD_RECORD("Loaded successfully."),
    SUCCESS_PROFILE_PICTURE_UPLOAD("Uploaded successfully."),
    SUCCESS_CSV_UPLOAD("Uploaded successfully."),
    SUCCESS_SYNC_RECORD("Synced successfully."),
    SUCCESS_SEARCH_RECORD("Searched successfully."),
    SUCCESS_SYNC_BACKGROUND_RECORDS("Syincing process started in background."),
    SUCCESS_SEARCH_PROCESS("Search process completed."),
    SUCCESS_EMAIL_SENT("Email sent successfully."),
    SUCCESS_NEWS_PUBLISHED_RECORD("NEWS published successfully."),
    SUCCESS_NEWS_UNPUBLISHED_RECORD("NEWS unpublished successfully."),
    // ERROR MESSAGES
    ERROR_FAILED_PROFILE_PICTURE_UPLOAD("Failed to upload file."),
    ERROR_EMPTY_PROFILE_PICTURE_UPLOAD("Empty file."),
    ERROR_INVALID_PROFILE_PICTURE_UPLOAD("Invalid file."),
    ERROR_FAILED_CSV_UPLOAD("Failed to upload file."),
    ERROR_EMPTY_CSV_UPLOAD("Empty file."),
    ERROR_INVALID_CSV_UPLOAD("Invalid file."),
    ERROR_RECORD_PROCESS("Process cannot be completed."),
    ERROR_RECORD_INCOMPLETE_PROCESS("Data is not completly filled. Process cannot be completed."),
    ERROR_INTERNAL_WEB_SERVER("Internal web server error! Process cannot be completed."),
    ERROR_UNAUTHORIZED_WEB_SERVER("Un-Authorized user to proceed in web server! Process cannot be completed."),
    ERROR_BAD_REQUEST("Bad request error from web server! Process cannot be completed."),
    ERROR_WEB_SERVER_OTHER("Unkown error from web server! Process cannot be completed."),
    ERROR_PAGE("Error in page."),
    ERROR_RESOUCES_IN_USE_FOR_DELETE("Process cannot be completed. The resource you want to delete is shared by other records. "),
    // UNKNOWN MESSAGES
    UNKNOWN("");

    public String message;

    ApplicationMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
