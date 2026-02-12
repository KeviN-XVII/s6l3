package kevinquarta.s6l3.services;



import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import kevinquarta.s6l3.entities.User;
import kevinquarta.s6l3.exceptions.BadRequestException;
import kevinquarta.s6l3.exceptions.NotFoundException;
import kevinquarta.s6l3.payloads.UserDTO;
import kevinquarta.s6l3.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class UsersService {
    private final UsersRepository usersRepository;
    private final Cloudinary cloudinaryUploader;

    @Autowired
    public UsersService(UsersRepository usersRepository,Cloudinary cloudinaryUploader) {
        this.usersRepository = usersRepository;
        this.cloudinaryUploader = cloudinaryUploader;
    }


    public Page<User> findAll(int page, int size,String orderBy, String sortCriteria) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size,
                sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.usersRepository.findAll(pageable);

    }

// SALVA NUOVO UTENTE
    public User saveUser(UserDTO payload){
//        CONTROLLO EMAIL
        this.usersRepository.findByEmail(payload.email()).ifPresent(user -> {
            throw new BadRequestException("L'email "+ user.getEmail() + " è già registrata!");});
//        NUOVO USER
        User newUser = new User(payload.name(), payload.surname(), payload.email(), payload.dateOfBirth());
        newUser.setAvatar("https://ui-avatars.com/api/?name="+payload.name()+"+"+payload.surname());
//        SALVO
       User savedUser = usersRepository.save(newUser);
//       LOG
        log.info("L'utente " + newUser.getName() +" "+ newUser.getSurname() + " è stato salvato correttamente!");
        return savedUser;
    }


//    RICERCA UTENTE PER ID
    public User findById(long userId){
        return this.usersRepository.findById(userId)
                .orElseThrow(()-> new NotFoundException(userId));
    }


//    MODIFICA UTENTE
    public User findByIdAndUpdate(long userId, UserDTO payload){
//       CERCO UTENTE
        User found = this.findById(userId);
//        VALIDAZIONE DATI
        if(!found.getEmail().equals(payload.email()))this.usersRepository.findByEmail(payload.email()).ifPresent(user -> {
            throw new BadRequestException("L'email "+user.getEmail()+" è già in uso!");
        });
//        MODIFICO UTENTE
        found.setName(payload.name());
        found.setSurname(payload.surname());
        found.setEmail(payload.email());
        found.setDateOfBirth(payload.dateOfBirth());
        found.setAvatar("https://ui-avatars.com/api?name=" + payload.name() + "+" + payload.surname());
//        SALVO
        User modifiedUser = usersRepository.save(found);
//        LOG
        log.info("L'utente con id "+modifiedUser.getId()+" è stato modificato con successo!");
//  RETURN USER MODIFICATO
        return modifiedUser;
    }


//    ELIMINA UTENTE
    public void findByIdAndDelete(long userId){
       User found = this.findById(userId);
       this.usersRepository.delete(found);
    }


//    UPLOAD AVATAR UTENTE
    public User uploadAvatar(long userId,MultipartFile file){
//        controlli...
//        find by id utente
        User found = this.findById(userId);
//        upload del file cloudinary
      try {
          Map result = cloudinaryUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

          String imageUrl = (String) result.get("secure_url");

          found.setAvatar(imageUrl);


          return found;


      }catch (IOException e){
          throw new RuntimeException(e);
      }
    }
}
