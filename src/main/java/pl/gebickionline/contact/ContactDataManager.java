package pl.gebickionline.contact;

import pl.gebickionline.communication.*;

/**
 * Created by Łukasz on 2016-01-16.
 */
public class ContactDataManager {
    private static ContactDataManager instance;
    private final CommunicationManager communicationManager;

    private ContactDataManager() {
        communicationManager = CommunicationManagerImpl.getInstance();
    }

    public static ContactDataManager getInstance() {
        if (instance == null)
            instance = new ContactDataManager();
        return instance;
    }

    public void updateContentOfContactDataPage(String contactData) {
        communicationManager.updateContentOfContactDataPage(contactData);
    }

    public String getContentOfContactDataPage() {
        return communicationManager.getContentOfContactDataPage();
    }
}
