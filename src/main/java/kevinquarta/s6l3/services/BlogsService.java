package kevinquarta.s6l3.services;


import kevinquarta.s6l3.entities.Blog;
import kevinquarta.s6l3.exceptions.NotFoundException;
import kevinquarta.s6l3.payloads.BlogPayload;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BlogsService {

    private List<Blog> blogDB = new ArrayList<>();

    public List<Blog> findAll(){
        return this.blogDB;
    }

    public Blog saveBlog (BlogPayload payload){
        Blog newBlog = new Blog(payload.getCategoria(),payload.getTitle(), payload.getContent(), payload.getTempoDiLettura(),payload.getUser());
        this.blogDB.add(newBlog);
        log.info("Il blog " + newBlog.getId() + " è stato salvato correttamente");
        return newBlog;
    }

    public Blog getBlogById(long blogId){
        Blog found = null;
        for (Blog blog : this.blogDB) {
            if (blog.getId() == blogId) found = blog;
        }
        if (found == null) throw new NotFoundException(blogId);
        return found;
    }

    public Blog updateBlog(long blogId, BlogPayload payload){
        Blog found = null;
        for (Blog blog : this.blogDB) {
            if (blog.getId() == blogId) {
                found = blog;
                found.setCategoria(payload.getCategoria());
                found.setTitle(payload.getTitle());
                found.setContent(payload.getContent());
                found.setTempoDiLettura(payload.getTempoDiLettura());
            }
        }
        if (found == null) throw new NotFoundException(blogId);
        return found;
    }

    public void deleteBlog(long blogId){
        Blog found = null;
        for (Blog blog : this.blogDB) {
            if (blog.getId() == blogId) found = blog;
        }
        if (found == null) throw new NotFoundException(blogId);
        this.blogDB.remove(found);
    }







}
