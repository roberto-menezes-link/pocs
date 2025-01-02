package com.link.apache.camel.demo.routes.soap.v3;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service("inMemoryContactService")
public class ContactServiceInMemoryImpl implements ContactService {

    private Map<String, Contact> contacts = new ConcurrentHashMap<>();

    @Override
    public void addContact(Contact contact) {
        contacts.put(contact.getName(), contact);
    }

    @Override
    public Contact getContact(String name) throws NoSuchContactException {
        if (Strings.isBlank(name) || !contacts.containsKey(name)) {
            throw new NoSuchContactException(name);
        }

        return contacts.get(name);
    }

    @Override
    public GetContactsResponse getContacts() {
        GetContactsResponse result = new GetContactsResponse();
        result.setContacts(contacts.values());

        return result;
    }

    @Override
    public void updateContact(String name, Contact contact) throws NoSuchContactException {
        if (!contacts.containsKey(name)) {
            throw new NoSuchContactException(name);
        }
        if (!contacts.get(name).equals(contact.getName())) {
            contacts.remove(name);
        }
        contacts.put(contact.getName(), contact);
    }

    @Override
    public void removeContact(String name) throws NoSuchContactException {
        if (!contacts.containsKey(name)) {
            throw new NoSuchContactException(name);
        }
        contacts.remove(name);
    }
}