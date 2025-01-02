package com.link.apache.camel.demo.routes.soap.v3;

import java.util.Collection;

public class GetContactsResponse {
    private Collection<Contact> contacts;

    public Collection<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(Collection<Contact> contacts) {
        this.contacts = contacts;
    }
}