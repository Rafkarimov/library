package com.nv.sberschool.library.service;

import static com.nv.sberschool.library.constants.UserRolesConstants.LIBRARIAN;
import static com.nv.sberschool.library.constants.UserRolesConstants.USER;

import com.nv.sberschool.library.constants.MailConstants;
import com.nv.sberschool.library.model.User;
import com.nv.sberschool.library.repository.UserRepository;
import com.nv.sberschool.library.utils.MailUtils;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends GenericService<User> {

    private final RoleService roleService;
    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JavaMailSender javaMailSender;

    protected UserService(RoleService roleService, UserRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder, JavaMailSender javaMailSender) {
        super(repository);
        this.roleService = roleService;
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public User create(User object) {
        object.setRole(roleService.getByTitle(USER));
        object.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        object.setCreatedWhen(LocalDateTime.now());
        object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
        return super.create(object);
    }

    @Override
    public User update(User object) {
        User foundUser = getOne(object.getId());
        object.setRole(foundUser.getRole());
        object.setPassword(foundUser.getPassword());
        object.setBookRentInfos(foundUser.getBookRentInfos());
        object.setDeleted(foundUser.isDeleted());
        object.setDeletedWhen(foundUser.getDeletedWhen());
        object.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        object.setUpdatedWhen(LocalDateTime.now());
        object.setCreatedBy(foundUser.getCreatedBy());
        object.setCreatedWhen(foundUser.getCreatedWhen());
        return super.update(object);
    }

    public User getUserByLogin(String login) {
        return repository.findUserByLogin(login);
    }

    public User getUserByEmail(String email) {
        return repository.findUserByEmail(email);
    }

    public User createLibrarian(User object) {
        object.setRole(roleService.getByTitle(LIBRARIAN));
        object.setPassword(bCryptPasswordEncoder.encode(object.getPassword()));
        object.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        object.setCreatedWhen(LocalDateTime.now());
        return repository.save(object);
    }

    public boolean checkPassword(String password, UserDetails foundUser) {
        return bCryptPasswordEncoder.matches(password, foundUser.getPassword());
    }

    public void sendChangePasswordEmail(User user) {
        UUID uuid = UUID.randomUUID();
        user.setChangePasswordToken(uuid.toString());
        update(user);
        SimpleMailMessage message = MailUtils.createEmailMessage(
                user.getEmail(),
                MailConstants.MAIL_SUBJECT_FOR_REMEMBER_PASSWORD,
                MailConstants.MAIL_MESSAGE_FOR_REMEMBER_PASSWORD
        );
        javaMailSender.send(message);
    }

    public void changePassword(String uuid, String password) {
        User user = repository.findUserByChangePasswordToken(uuid);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setChangePasswordToken(null);
        update(user);
    }
}

