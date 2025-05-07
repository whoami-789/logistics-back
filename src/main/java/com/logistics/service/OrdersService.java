package com.logistics.service;

import com.logistics.config.OrdersSpecifications;
import com.logistics.dto.*;
import com.logistics.exception.ResourceNotFoundException;
import com.logistics.model.Orders;
import com.logistics.model.Route;
import com.logistics.model.User;
import com.logistics.model.enums.CustomerStatus;
import com.logistics.model.enums.DriverStatus;
import com.logistics.model.enums.OrderStatus;
import com.logistics.repository.OrdersRepository;
import com.logistics.repository.UserRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final UserRepository userRepository;

    public OrdersService(OrdersRepository ordersRepository, UserRepository userRepository) {
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
    }

    public OrdersDTO createOrder(OrdersDTO orderDTO) {
        Orders order = new Orders();

        // Присваиваем заказчика
        User customer = userRepository.findById(orderDTO.getCustomerNum())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        order.setCustomer(customer);

        // Остальная логика сохранения заказа
        order.setStartDate(orderDTO.getStartDate());
        order.setWeight(orderDTO.getWeight());
        order.setPrice(orderDTO.getPrice());
        order.setDistance(orderDTO.getDistance());
        order.setCargoType(orderDTO.getCargoType());
        order.setLength(orderDTO.getLength());
        order.setWidth(orderDTO.getWidth());
        order.setHeight(orderDTO.getHeight());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        order.setAdvancePaymentPercentage(orderDTO.getAdvancePaymentPercentage());
        order.setAdvancePaymentMethod(orderDTO.getAdvancePaymentMethod());
        order.setCurrency(orderDTO.getCurrency());
        order.setCarBody(orderDTO.getCarBody());
        order.setAdr(orderDTO.getAdr());
        order.setCarType(orderDTO.getCarType());
        order.setTripType(orderDTO.getTripType());
        order.setMin(orderDTO.getMin() != null ? Integer.valueOf(orderDTO.getMin()) : null);
        order.setMax(orderDTO.getMax() != null ? Integer.valueOf(orderDTO.getMax()) : null);
        order.setNds(orderDTO.getNds());
        order.setTelegram(orderDTO.getTelegram());
        order.setPnumber(orderDTO.getPnumber());
        order.setStatus(OrderStatus.CREATED); // Устанавливаем статус "Новый"


        // Присваиваем маршруты
        List<Route> routes = orderDTO.getRoutes().stream()
                .map(routeDTO -> {
                    Route route = new Route();
                    route.setCountryFrom(routeDTO.getCountryFrom());
                    route.setCityFrom(routeDTO.getCityFrom());
                    route.setAddressFrom(routeDTO.getAddressFrom());
                    route.setCountryTo(routeDTO.getCountryTo());
                    route.setCityTo(routeDTO.getCityTo());
                    route.setAddressTo(routeDTO.getAddressTo());
                    route.setOrder(order); // Связываем маршрут с заказом
                    return route;
                })
                .collect(Collectors.toList());
        order.setRoutes(routes);

        // Сохраняем заказ
        Orders savedOrder = ordersRepository.save(order);
        return convertToDTO(savedOrder);
    }

    public OrdersDTO updateOrder(Long orderId, OrdersDTO updatedOrderDTO) {
        // Логика обновления заказа
        Orders existingOrder = ordersRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        // Обновление полей существующего заказа
        existingOrder.setCargoType(updatedOrderDTO.getCargoType());
        existingOrder.setStartDate(updatedOrderDTO.getStartDate());
        existingOrder.setDistance(updatedOrderDTO.getDistance());
        existingOrder.setWeight(updatedOrderDTO.getWeight());
        existingOrder.setPrice(updatedOrderDTO.getPrice());
        existingOrder.setCargoType(updatedOrderDTO.getCargoType());
        existingOrder.setPaymentMethod(updatedOrderDTO.getPaymentMethod());
        existingOrder.setCarType(updatedOrderDTO.getCarType());
        existingOrder.setAdvancePaymentPercentage(updatedOrderDTO.getAdvancePaymentPercentage());
        existingOrder.setWidth(updatedOrderDTO.getWidth());
        existingOrder.setHeight(updatedOrderDTO.getHeight());
        existingOrder.setLength(updatedOrderDTO.getLength());
        // Добавьте остальные обновляемые поля...

        // Сохранение обновленного заказа
        ordersRepository.save(existingOrder);

        // Преобразование в DTO для ответа
        return convertToDTO(existingOrder);
    }


    public List<OrdersDTO> getOrdersByCustomer(Long customerId) {
        return ordersRepository.findAllByCustomerId(customerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<OrdersDTO> getUnbookedOrdersByCustomer(Long customerId) {
        return ordersRepository.findAllByCustomerId(customerId).stream()
                .map(this::convertToDTO)
                .filter(orderDTO -> orderDTO.getStatus().equals("Создан") || orderDTO.getStatus().equals("Ожидает подтверждения"))
                .collect(Collectors.toList());
    }

    public List<OrdersDTO> getActiveOrdersByCustomer(Long customerId) {
        return ordersRepository.findAllByCustomerIdAndStatusIsNot(customerId, "Завершен").stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<OrdersDTO> getActiveOrders() {
        return ordersRepository.findAllByStatusIsNot("Создан").stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<OrdersDTO> getOrdersByExecutor(Long executorId) {
        return ordersRepository.findAllByExecutorId(executorId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<OrdersDTO> getOrderDetailsForCustomer(Long customerId, Long orderId) {
        return ordersRepository.findByIdAndCustomerId(orderId, customerId)
                .map(this::convertToDTO);
    }

    public Optional<OrdersDTO> getOrderDetailsForExecutor(Long executorId, Long orderId) {
        return ordersRepository.findByIdAndExecutorId(orderId, executorId)
                .map(this::convertToDTO);
    }

    public List<OrdersDTO> getAllOrders() {
        return ordersRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private OrdersDTO convertToDTO(Orders order) {
        OrdersDTO dto = new OrdersDTO();
        dto.setId(order.getId());
        dto.setStartDate(order.getStartDate());
        dto.setEndDate(order.getEndDate());
        dto.setWeight(order.getWeight());
        dto.setPrice(order.getPrice());
        dto.setDistance(order.getDistance());
        dto.setCargoType(order.getCargoType());
        dto.setLength(order.getLength());
        dto.setWidth(order.getWidth());
        dto.setHeight(order.getHeight());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setAdvancePaymentPercentage(order.getAdvancePaymentPercentage());
        dto.setAdvancePaymentMethod(order.getAdvancePaymentMethod());
        dto.setCurrency(order.getCurrency());
        dto.setStatus(String.valueOf(order.getStatus()));
        dto.setDriverStatus(String.valueOf(order.getDriverStatus()));
        dto.setCustomerStatus(String.valueOf(order.getCustomerStatus()));
        dto.setCustomerNum(order.getCustomer().getId());
        dto.setCarBody(order.getCarBody()); // Исправлено: у
        dto.setAdr(order.getAdr()); // Исправлено: у
        dto.setCarType(order.getCarType()); // Исправлено: у
        dto.setTripType(order.getTripType()); // Исправлено: у
        dto.setMin(String.valueOf(order.getMin())); // Исправлено: у
        dto.setMax(String.valueOf(order.getMax())); // Исправлено: у
        dto.setNds(order.getNds()); // Исправлено: у
        dto.setTelegram(order.getTelegram()); // Исправлено: у
        dto.setPnumber(order.getPnumber()); // Исправлено: у

        // Конвертируем маршруты в RouteDTO
        List<RouteDTO> routeDTOs = order.getRoutes().stream()
                .map(route -> new RouteDTO(route.getCountryFrom(), route.getCityFrom(), route.getAddressFrom(), route.getCountryTo(), route.getCityTo(), route.getAddressTo()))
                .collect(Collectors.toList());
        dto.setRoutes(routeDTOs);

        return dto;
    }


    // Метод для бронирования заказа водителем (назначение executor)
    public OrdersDTO bookOrder(Long orderId, Long driverId) {
        return driverStatus(orderId, DriverStatus.BOOKED, driverId);
    }

    // Сервисный метод для изменения статуса со стороны водителя
    public OrdersDTO driverStatus(Long orderId, DriverStatus newDriverStatus, Long driverId) {
        // Находим заказ
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Заказ не найден"));

        // Если статус "BOOKED" (Забронирован) — назначаем водителя исполнителем
        if (newDriverStatus == DriverStatus.BOOKED) {
            User driver = userRepository.findById(driverId)
                    .orElseThrow(() -> new ResourceNotFoundException("Водитель не найден"));
            order.setExecutor(driver);
        }
        // Если статус "CANCELLED" (Водитель отменил) — освобождаем заказ
        else if (newDriverStatus == DriverStatus.CANCELLED) {
            order.setExecutor(null);
        }
        // Можно добавить логику для DELIVERED и т.д. по вашему бизнес-процессу

        // Устанавливаем статус водителя
        order.setDriverStatus(newDriverStatus);

        // setDriverStatus(...) автоматически вызывает order.updateStatus(),
        // если вы реализовали это в сеттере, либо вы сами можете вызвать:
        // order.updateStatus();

        Orders saved = ordersRepository.save(order);
        return convertToDTO(saved);
    }

    // Сервисный метод для изменения статуса со стороны заказчика
    public OrdersDTO customerStatus(Long orderId, CustomerStatus newCustomerStatus) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Заказ не найден"));

        // Логика: если заказчик отменяет — "CANCELLED", можно проверить бизнес-условия...
        // if (newCustomerStatus == CustomerStatus.CANCELLED && ... ) { ... }

        order.setCustomerStatus(newCustomerStatus);
        // Аналогично, setCustomerStatus(...) внутри может вызвать updateStatus()
        // или вы сами вызовите:
        // order.updateStatus();

        Orders saved = ordersRepository.save(order);
        return convertToDTO(saved);
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

    public List<OrdersDTO> getAllOrdersExcludingCustomer(Long customerId) {
        return ordersRepository.findAll().stream()
                .filter(order -> !order.getCustomer().getId().equals(customerId))
                .filter(order -> order.getStatus().equals("Создан"))// Исключаем ордера с заданным customerId
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public UserStatisticsDTO getUserStatistics(Long userId) {
        Integer totalIncome = 0;
        List<MonthlyIncomeDTO> monthlyIncome = new ArrayList<>();
        int availableOrdersCount = ordersRepository.countAvailableOrdersForUser(); // Метод, который нужно будет создать

        // Получите все заказы для пользователя и вычислите общий доход
        List<Orders> orders = ordersRepository.findAllByExecutorId(userId);
        for (Orders order : orders) {
            totalIncome += order.getPrice(); // Предполагается, что price - это заработок за заказ
        }

//        // Вычислите доход по месяцам
//        for (int month = 0; month < 12; month++) {
//            int finalMonth = month;
//            double monthlyTotal = orders.stream()
//                    .filter(order -> order.getStartDate().getMonthValue() == finalMonth + 1)
//                    .mapToDouble(Orders::getPrice)
//                    .sum();
//
//            String monthName = Month.of(month + 1).getDisplayName(TextStyle.FULL, Locale.forLanguageTag("ru"));
//            monthlyIncome.add(new MonthlyIncomeDTO(monthName, monthlyTotal));
//        }

        // Получите доступные заказы по странам
        List<AvailableOrderDTO> availableOrdersByCountry = ordersRepository.findAvailableOrdersByCountry(userId);

        return new UserStatisticsDTO(totalIncome, availableOrdersCount, monthlyIncome, availableOrdersByCountry);
    }

    public List<OrdersDTO> filterOrders(
            LocalDate startDate,
            String carBody,
            Integer minWeight,
            Integer maxWeight,
            Integer priceFrom,
            Integer priceTo,
            String currency,
            String countryFrom,
            String cityFrom,
            String countryTo,
            String cityTo,
            Long userId
    ) {
        // Создаем спецификацию для поиска через JpaSpecificationExecutor
        Specification<Orders> spec = Specification.where(OrdersSpecifications.hasStatus())
                .and(OrdersSpecifications.isNotCustomer(userId));

        // Добавляем фильтрацию по параметрам, если они присутствуют
        if (startDate != null) spec = spec.and(OrdersSpecifications.hasStartDate(startDate));
        if (carBody != null && !carBody.isEmpty()) spec = spec.and(OrdersSpecifications.hasCarBody(carBody));
        if (minWeight != null) spec = spec.and(OrdersSpecifications.hasMinWeight(minWeight));
        if (maxWeight != null) spec = spec.and(OrdersSpecifications.hasMaxWeight(maxWeight));
        if (priceFrom != null || priceTo != null) spec = spec.and(OrdersSpecifications.hasPriceRange(priceFrom, priceTo));
        if (currency != null && !currency.isEmpty()) spec = spec.and(OrdersSpecifications.hasCurrency(currency));
        if (countryFrom != null || cityFrom != null || countryTo != null || cityTo != null) {
            spec = spec.and(OrdersSpecifications.hasRoute(countryFrom, cityFrom, countryTo, cityTo));
        }

        // Получаем отфильтрованные заказы из репозитория
        List<Orders> orders = ordersRepository.findAll(spec);

        // Преобразуем сущности Orders в DTO и возвращаем
        return orders.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


}
