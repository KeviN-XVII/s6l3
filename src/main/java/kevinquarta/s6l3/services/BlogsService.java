package kevinquarta.s6l3.services;


import kevinquarta.s6l3.entities.Blog;
import kevinquarta.s6l3.entities.User;
import kevinquarta.s6l3.exceptions.NotFoundException;
import kevinquarta.s6l3.payloads.BlogDTO;
import kevinquarta.s6l3.payloads.BlogPayload;
import kevinquarta.s6l3.payloads.BlogUpdateDTO;
import kevinquarta.s6l3.repositories.BlogsRepository;
import kevinquarta.s6l3.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class BlogsService {

    private final BlogsRepository blogsRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public BlogsService(BlogsRepository blogsRepository,UsersRepository usersRepository) {
        this.blogsRepository = blogsRepository;
        this.usersRepository = usersRepository;
    }

    public Page<Blog> findAll(int page, int size, String orderBy, String sortCriteria) {
        if (size > 100 || size < 0) size = 10;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size,
                sortCriteria.equals("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.blogsRepository.findAll(pageable);

    }

//    SALVA BLOG
    public Blog saveBlog (BlogDTO payload) {
//        RICERCO UTENTE
        User user = usersRepository.findById(payload.userId())
                .orElseThrow(()->new NotFoundException(payload.userId()));
//        NUOVO BLOG
        Blog newBlog = new Blog(
                payload.categoria(),
                payload.title(),
                payload.content(),
                payload.tempoDiLettura(),
//                QUI PASSO L'UTENTE A CUI DEVE ESSERE COLLEGATO IL BLOG
                user);
//       SALVA BLOG
        Blog savedBlog = blogsRepository.save(newBlog);
//        LOG
        log.info("Il blog " + newBlog.getTitle() + " è stato salvato correttamente");
        return savedBlog;
    }


//    CERCA BLOG
    public Blog findById(long blogId){
       return this.blogsRepository.findById(blogId).orElseThrow(()-> new NotFoundException(blogId));
    }


//    CERCA E MODIFICA BLOG
    public Blog findByIdAndUpdate(long blogId, BlogUpdateDTO payload){
//        CERCO BLOG
        Blog found = this.findById(blogId);
//        MODIFICA BLOG
        found.setCategoria(payload.categoria());
        found.setTitle(payload.title());
        found.setContent(payload.content());
        found.setTempoDiLettura(payload.tempoDiLettura());
//       SALVO
        Blog modifiedBlog = blogsRepository.save(found);
//        LOG
        log.info("Il Blog "+modifiedBlog.getTitle()+" è stato modificato correttamente");
        return modifiedBlog;
    }

//    ELIMINA BLOG
    public void deleteBlog(long blogId){
        Blog found = findById(blogId);
        blogsRepository.delete(found);
    }


}
