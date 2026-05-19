package G7_TTN.reponsitory;

import G7_TTN.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository
        extends JpaRepository<Category, Long> {

    // lấy category chưa xóa
    List<Category> findByIsdelete(Integer isdelete);

}