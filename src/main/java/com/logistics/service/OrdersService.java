package com.logistics.service;

import com.logistics.dto.*;
import com.logistics.model.Orders;
import com.logistics.model.Route;
import com.logistics.model.User;
import com.logistics.repository.OrdersRepository;
import com.logistics.repository.UserRepository;
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
        order.setMin(Integer.valueOf(orderDTO.getMin()));
        order.setMax(Integer.valueOf(orderDTO.getMax()));
        order.setNds(orderDTO.getNds());
        order.setTelegram(orderDTO.getTelegram());
        order.setPnumber(orderDTO.getPnumber());
        order.setStatus("Создан"); // Устанавливаем статус "Новый"


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


    public List<OrdersDTO> getOrdersByCustomer(Long customerId) {
        return ordersRepository.findAllByCustomerId(customerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<OrdersDTO> getUnbookedOrdersByCustomer(Long customerId) {
        return ordersRepository.findAllByCustomerId(customerId).stream()
                .map(this::convertToDTO)
                .filter(orderDTO -> orderDTO.getStatus().equals("Создан"))
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
        dto.setStatus(order.getStatus());
        dto.setDriverStatus(order.getDriverStatus());
        dto.setCustomerStatus(order.getCustomerStatus());
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
        Optional<Orders> optionalOrder = ordersRepository.findById(orderId);
        Optional<User> optionalDriver = userRepository.findById(driverId);

        if (optionalOrder.isPresent() && optionalDriver.isPresent()) {
            Orders order = optionalOrder.get();
            User driver = optionalDriver.get();

            order.setExecutor(driver);
            order.setDriverStatus("Забронирован");
            Orders savedOrder = ordersRepository.save(order);
            return convertToDTO(savedOrder);
        } else {
            throw new IllegalArgumentException("Заказ или водитель не найдены");
        }
    }

    public OrdersDTO customerStatus(Long orderId, String status) {
        Optional<Orders> optionalOrder = ordersRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            Orders order = optionalOrder.get();

            order.setCustomerStatus(status); // Устанавливаем исполнителя
            Orders savedOrder = ordersRepository.save(order);
            return convertToDTO(savedOrder);
        } else {
            throw new IllegalArgumentException("Заказ или водитель не найдены");
        }
    }

    public OrdersDTO driverStatus(Long orderId, String status) {
        Optional<Orders> optionalOrder = ordersRepository.findById(orderId);

        if (optionalOrder.isPresent()) {
            Orders order = optionalOrder.get();

            order.setDriverStatus(status); // Устанавливаем исполнителя
            Orders savedOrder = ordersRepository.save(order);
            return convertToDTO(savedOrder);
        } else {
            throw new IllegalArgumentException("Заказ или водитель не найдены");
        }
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

        // Вычислите доход по месяцам
        for (int month = 0; month < 12; month++) {
            int finalMonth = month;
            double monthlyTotal = orders.stream()
                    .filter(order -> order.getStartDate().getMonthValue() == finalMonth + 1)
                    .mapToDouble(Orders::getPrice)
                    .sum();

            String monthName = Month.of(month + 1).getDisplayName(TextStyle.FULL, Locale.forLanguageTag("ru"));
            monthlyIncome.add(new MonthlyIncomeDTO(monthName, monthlyTotal));
        }

        // Получите доступные заказы по странам
        List<AvailableOrderDTO> availableOrdersByCountry = ordersRepository.findAvailableOrdersByCountry(userId);

        return new UserStatisticsDTO(totalIncome, availableOrdersCount, monthlyIncome, availableOrdersByCountry);
    }




}
