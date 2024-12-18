//package webapp.resumebuilder.domain.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import webapp.resumebuilder.domain.model.BlockElement;
//
//import java.util.List;
//import java.util.UUID;
//
//@Repository
//public interface BlockElementRepository extends JpaRepository<BlockElement, UUID> {
//
//    // Кастомный запрос для поиска по имени
//    List<BlockElement> findByName(String name);
//}