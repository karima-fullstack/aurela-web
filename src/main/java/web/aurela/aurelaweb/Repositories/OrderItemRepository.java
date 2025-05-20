package web.aurela.aurelaweb.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import web.aurela.aurelaweb.Entities.OrderItem;
import web.aurela.aurelaweb.Entities.User;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Page<OrderItem> findByOrder_UserOrderByOrder_CreatedAtDesc(User user, Pageable pageable);

}
