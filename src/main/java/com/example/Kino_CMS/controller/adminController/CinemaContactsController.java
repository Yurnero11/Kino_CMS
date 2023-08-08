package com.example.Kino_CMS.controller.adminController;

import com.example.Kino_CMS.entity.*;
import com.example.Kino_CMS.repository.CinemaContactsRepository;
import com.example.Kino_CMS.repository.ContactForTableRepository;
import com.example.Kino_CMS.repository.SeoBlockCinemaContactRepository;
import com.example.Kino_CMS.repository.SeoBlocksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class CinemaContactsController {
    private final CinemaContactsRepository contactsRepository;
    private final SeoBlocksRepository seoBlocksRepository;
    private final SeoBlockCinemaContactRepository seoBlockCinemaContactRepository;
    private final ContactForTableRepository contactForTableRepository;

    @Autowired
    public CinemaContactsController(CinemaContactsRepository contactsRepository, SeoBlocksRepository seoBlocksRepository, SeoBlockCinemaContactRepository seoBlockCinemaContactRepository, ContactForTableRepository contactForTableRepository) {
        this.contactsRepository = contactsRepository;
        this.seoBlocksRepository = seoBlocksRepository;
        this.seoBlockCinemaContactRepository = seoBlockCinemaContactRepository;
        this.contactForTableRepository = contactForTableRepository;
    }

    @GetMapping("/admin/pages/contacts")
    public String showContactsPage(Model model) {
        Iterable<CinemaContacts> contactsList = contactsRepository.findAll();
        model.addAttribute("contactsList", contactsList);

        List<Contact_for_table> contactForTables = contactForTableRepository.findAll();
        model.addAttribute("cinemaContactsForTable", contactForTables);

        SeoBlockCinemaContact seoBlockCinemaContact = seoBlockCinemaContactRepository.findById(1L).orElse(new SeoBlockCinemaContact());
        model.addAttribute("seoBlock", seoBlockCinemaContact);

        // Создаем пустой объект для формы контакта
        CinemaContacts contact = new CinemaContacts();
        model.addAttribute("contact", contact);

        return "admin/pages/contacts";
    }

    @PostMapping ("/admin/pages/contacts/{cinema_id}/remove")
    public String deleteContact(@PathVariable("cinema_id") Long id) {
        // Find the contact by id
        CinemaContacts contactToDelete = contactsRepository.findById(id).orElse(null);

        // Check if the contact exists
        if (contactToDelete != null) {
            // Delete the contact from the repository
            contactsRepository.delete(contactToDelete);
        }

        // Redirect to the contacts page after deletion
        return "redirect:/admin/pages/contacts";
    }

    @PostMapping("/admin/pages/contacts/add")
    public String addContact(@ModelAttribute("contact") CinemaContacts newContact,
                             @RequestParam("logo") MultipartFile logoFile) {

        // Сохраняем логотип и получаем путь к сохраненному файлу
        String logoPath = saveImage(logoFile);

        // Проверяем, удалось ли сохранить логотип, и сохраняем информацию о контакте
        if (logoPath != null) {
            newContact.setLogo_path(logoPath);
            contactsRepository.save(newContact);
        }

        return "redirect:/admin/pages/contacts";
    }

    @PostMapping("/admin/pages/contacts/updateSeo")
    public String editSeo(@RequestParam("url") String url,
                                 @RequestParam("title") String title,
                                 @RequestParam("keywords") String keywords,
                                 @RequestParam("description_seo") String descriptionSeo,
                                 RedirectAttributes redirectAttributes)
    {
        Optional<SeoBlockCinemaContact> existingMainPage = seoBlockCinemaContactRepository.findById(1L);

        if (existingMainPage.isPresent()) {
            SeoBlockCinemaContact currentPage = existingMainPage.get();

            // Обновите поля существующего объекта MainPage данными из формы.
            currentPage.setUrl(url);
            currentPage.setKeywords(keywords);
            currentPage.setTitle(title);
            currentPage.setDescription(descriptionSeo);

            seoBlockCinemaContactRepository.save(currentPage);

            // Вы можете добавить атрибуты Flash для отображения сообщений об успешном обновлении, если хотите.
            redirectAttributes.addFlashAttribute("successMessage", "Данные успешно обновлены.");
        } else {
            // Если объект MainPage с заданным id не найден, выполните соответствующие действия.
            // Например, можно создать новый объект и сохранить его, если это приемлемо.
            // Или вы можете выбрать другую логику обработки в этом случае.
        }

        return "redirect:/admin/pages";
    }

    @PostMapping("/admin/pages/contacts/updateStatus")
    public String updateStatus(@RequestParam("status") String status) {
        // Получаем контакт по его идентификатору из базы данных
        Optional<Contact_for_table> optionalContact = contactForTableRepository.findById(1L);

        // Проверяем, что контакт найден
        if (optionalContact.isPresent()) {
            // Обновляем статус контакта
            Contact_for_table contact = optionalContact.get();
            contact.setStatus(status);
            contactForTableRepository.save(contact);
        }

        // После обновления статуса, вы можете перенаправить пользователя на страницу со списком контактов или на ту же страницу, где была форма
        return "redirect:/admin/pages/contacts";
    }


    private final String uploadDir = "upload";

    private String saveImage(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
                String fileExtension = getFileExtension(originalFileName);
                String uniqueFileName = generateUniqueFileName(fileExtension);

                Path filePath = Paths.get(uploadDir, uniqueFileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                return uniqueFileName;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return null;
        }
    }

    private String generateUniqueFileName(String fileExtension) {
        String timeStamp = UUID.randomUUID().toString();
        return timeStamp + "." + fileExtension;
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        } else {
            return "";
        }
    }
}
