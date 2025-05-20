package web.aurela.aurelaweb.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import web.aurela.aurelaweb.Entities.Order;
import web.aurela.aurelaweb.Entities.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
