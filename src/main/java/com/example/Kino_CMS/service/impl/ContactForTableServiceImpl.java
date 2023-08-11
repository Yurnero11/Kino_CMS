package com.example.Kino_CMS.service.impl;

import com.example.Kino_CMS.entity.Contact_for_table;
import com.example.Kino_CMS.repository.ContactForTableRepository;
import com.example.Kino_CMS.service.ContactForTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

@Service
public class ContactForTableServiceImpl implements ContactForTableService {
    private static final Logger log = LogManager.getLogger(ContactForTableServiceImpl.class);

    @Autowired
    private ContactForTableRepository contactForTableRepository;

    @Override
    public Optional<Contact_for_table> getContactForTable(Long contact_for_table_id) {
        try {
            log.info("Getting Contact_for_table by ID: {}", contact_for_table_id);
            Optional<Contact_for_table> result = contactForTableRepository.findById(contact_for_table_id);
            if (result.isPresent()) {
                log.info("Successfully retrieved Contact_for_table by ID: {}", contact_for_table_id);
            } else {
                log.info("Contact_for_table with ID {} not found", contact_for_table_id);
            }
            return result;
        } catch (Exception e) {
            log.error("Error while getting Contact_for_table by ID: {}", contact_for_table_id, e);
            throw e;
        }
    }

    @Override
    public Contact_for_table saveContact_for_table(Contact_for_table contactForTable) {
        try {
            log.info("Saving Contact_for_table: {}", contactForTable);
            Contact_for_table result = contactForTableRepository.save(contactForTable);
            log.info("Successfully saved Contact_for_table: {}", contactForTable);
            return result;
        } catch (Exception e) {
            log.error("Error while saving Contact_for_table: {}", contactForTable, e);
            throw e;
        }
    }

    @Override
    public Iterable<Contact_for_table> getAllContacts() {
        try {
            log.info("Getting all Contact_for_table");
            Iterable<Contact_for_table> result = contactForTableRepository.findAll();
            log.info("Successfully retrieved all Contact_for_table");
            return result;
        } catch (Exception e) {
            log.error("Error while getting all Contact_for_table", e);
            throw e;
        }
    }
}
