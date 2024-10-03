package com.logistics.service;

import com.logistics.model.Orders;
import com.logistics.model.Route;
import com.logistics.model.User;
import com.logistics.repository.OrdersRepository;
import com.logistics.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;

    public OrdersService(OrdersRepository ordersRepository, UserRepository userRepository) {
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
    }

    public Orders createOrder(Orders order) {
        // Обрабатываем многостоповую поездку
        if (order.getWorkType().getType().equals("Многостоповая поездка")) {
            List<Route> routes = order.getRoutes();
            if (routes.size() > 3) {
                throw new IllegalArgumentException("Максимум 3 маршрута для многостоповой поездки");
            }
            for (Route route : routes) {
                route.setOrder(order); // Связываем маршрут с заказом
            }
        }

        // Обрабатываем габариты груза
        if (order.getCargoType().equals("габариты")) {
            if (order.getLength() == null || order.getWidth() == null || order.getHeight() == null) {
                throw new IllegalArgumentException("Для габаритного груза необходимо указать длину, ширину и высоту");
            }
        }

        return ordersRepository.save(order);
    }

    public Orders updateOrder(Long id, Orders updatedOrder) {
        Optional<Orders> existingOrder = ordersRepository.findById(id);
        if (existingOrder.isPresent()) {
            Orders order = existingOrder.get();
            // Обновляем поля
            order.setStartDate(updatedOrder.getStartDate());
            order.setEndDate(updatedOrder.getEndDate());
            order.setWeight(updatedOrder.getWeight());
            order.setPrice(updatedOrder.getPrice());
            order.setDistance(updatedOrder.getDistance());
            // Обновляем маршруты, если многостоповая поездка
            if (updatedOrder.getWorkType().getType().equals("Многостоповая поездка")) {
                List<Route> routes = updatedOrder.getRoutes();
                if (routes.size() > 3) {
                    throw new IllegalArgumentException("Максимум 3 маршрута для многостоповой поездки");
                }
                order.setRoutes(routes);
            }
            return ordersRepository.save(order);
        } else {
            throw new IllegalArgumentException("Order with ID " + id + " not found.");
        }
    }

    // Метод для бронирования заказа водителем (назначение executor)
    public Orders bookOrder(Long orderId, Long driverId) {
        Optional<Orders> optionalOrder = ordersRepository.findById(orderId);
        Optional<User> optionalDriver = userRepository.findById(driverId);

        if (optionalOrder.isPresent() && optionalDriver.isPresent()) {
            Orders order = optionalOrder.get();
            User driver = optionalDriver.get();

            // Назначаем водителя на заказ
            order.setExecutor(driver);

            return ordersRepository.save(order); // Сохраняем изменения
        } else {
            throw new IllegalArgumentException("Заказ или водитель с указанными ID не найдены");
        }
    }

    // Получение всех заказов, где пользователь — заказчик
    public List<Orders> getOrdersByCustomer(Long customerId) {
        return ordersRepository.findAllByCustomerId(customerId);
    }

    // Получение всех заказов, где пользователь — исполнитель
    public List<Orders> getOrdersByExecutor(Long executorId) {
        return ordersRepository.findAllByExecutorId(executorId);
    }

    // Получение детальной информации о заказе для заказчика
    public Optional<Orders> getOrderDetailsForCustomer(Long customerId, Long orderId) {
        return ordersRepository.findByIdAndCustomerId(orderId, customerId);
    }

    // Получение детальной информации о заказе для исполнителя
    public Optional<Orders> getOrderDetailsForExecutor(Long executorId, Long orderId) {
        return ordersRepository.findByIdAndExecutorId(orderId, executorId);
    }

    // Получение всех заказов для администратора
    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    // Удаление заказа
    public void deleteOrder(Long orderId) {
        Optional<Orders> order = ordersRepository.findById(orderId);
        if (order.isPresent()) {
            ordersRepository.deleteById(orderId);
        } else {
            throw new IllegalArgumentException("Order with ID " + orderId + " not found.");
        }
    }

}

