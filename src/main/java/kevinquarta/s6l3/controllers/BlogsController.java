package kevinquarta.s6l3.controllers;



import kevinquarta.s6l3.entities.Blog;
import kevinquarta.s6l3.payloads.BlogPayload;
import kevinquarta.s6l3.services.BlogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogsController {

    private BlogsService blogsService;

    @Autowired
    public  BlogsController(BlogsService blogsService) {
        this.blogsService = blogsService;
    }

//1 GET /blogs ritorna lista di blogs
@GetMapping
public List<Blog> findAll() {
    return blogsService.findAll();
}

//2 GET /blogs/123 ritorna una singolo blog
@GetMapping("/{blogId}")
public Blog getBlogById(@PathVariable long blogId) {
    return blogsService.getBlogById(blogId);
}

//3 POST /blogs crea un nuovo blog post
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public Blog createBlog(@RequestBody BlogPayload payload) {
    return this.blogsService.saveBlog(payload);
}

//4 PUT /blogs/123 modifica lo specifico blog
@PutMapping("/{blogId}")
public Blog  updateBlog(@PathVariable long blogId, @RequestBody BlogPayload payload) {
    return this.blogsService.updateBlog(blogId, payload);
}

//5 DELETE /blogs/123 elimina uno specifico blog post
@DeleteMapping("/{blogId}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void deleteBlog(@PathVariable long blogId) {
    this.blogsService.deleteBlog(blogId);
}

}
