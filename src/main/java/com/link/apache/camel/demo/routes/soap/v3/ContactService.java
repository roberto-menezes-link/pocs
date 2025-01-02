package com.link.apache.camel.demo.routes.soap.v3;

public interface ContactService {

    void addContact(Contact contact);

    Contact getContact(String name) throws NoSuchContactException;

    GetContactsResponse getContacts();

    void updateContact(String name, Contact contact)
            throws NoSuchContactException;

    void removeContact(String name) throws NoSuchContactException;
}