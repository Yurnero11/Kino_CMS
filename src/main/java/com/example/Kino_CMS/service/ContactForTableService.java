package com.example.Kino_CMS.service;

import com.example.Kino_CMS.entity.Cinemas;
import com.example.Kino_CMS.entity.Contact_for_table;
import com.example.Kino_CMS.entity.SeoBlockCinemaContact;

import java.util.Optional;

public interface ContactForTableService {
    Optional<Contact_for_table> getContactForTable(Long contact_for_table_id);

    Contact_for_table saveContact_for_table(Contact_for_table contactForTable);

    Iterable<Contact_for_table> getAllContacts();
}
