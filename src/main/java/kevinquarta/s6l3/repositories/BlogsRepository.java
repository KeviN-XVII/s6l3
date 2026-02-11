package kevinquarta.s6l3.repositories;

import kevinquarta.s6l3.entities.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogsRepository extends JpaRepository<Blog,Long> {

}
