package com.logistics.service;

import com.logistics.model.DriverFilters;
import com.logistics.model.Orders;
import com.logistics.repository.DriverFiltersRepository;
import com.logistics.repository.OrdersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverFiltersService {
    private static final Logger logger = LoggerFactory.getLogger(DriverFiltersService.class);


    private final DriverFiltersRepository driverFiltersRepository;
    private final OrdersRepository ordersRepository;
    private final TelegramBot telegramBot;


    public DriverFiltersService(DriverFiltersRepository driverFiltersRepository, OrdersRepository ordersRepository, TelegramBot telegramBot) {
        this.driverFiltersRepository = driverFiltersRepository;
        this.ordersRepository = ordersRepository;
        this.telegramBot = telegramBot;
    }

    public DriverFilters saveDriverFilters(DriverFilters filters) {
        // Проверка, существует ли запись с таким driver_id
        DriverFilters existingFilters = driverFiltersRepository.findByDriverId(filters.getDriver().getId());

        if (existingFilters != null) {
            // Обновляем существующие фильтры
            existingFilters.setMinCargoWeight(filters.getMinCargoWeight());
            existingFilters.setMaxCargoWeight(filters.getMaxCargoWeight());
            existingFilters.setCargoType(filters.getCargoType());
            existingFilters.setPrice(filters.getPrice());
            existingFilters.setDistance(filters.getDistance());
            existingFilters.setLength(filters.getLength());
            existingFilters.setWidth(filters.getWidth());
            existingFilters.setHeight(filters.getHeight());
            existingFilters.setCurrency(filters.getCurrency());
            existingFilters.setRoundTrip(filters.getRoundTrip());

            return driverFiltersRepository.save(existingFilters);
        } else {
            // Если запись не найдена, сохраняем новые фильтры
            return driverFiltersRepository.save(filters);
        }
    }


    public DriverFilters getDriverFilters(Long driverId) {
        return driverFiltersRepository.findByDriverId(driverId);
    }

    @Scheduled(fixedRate = 60000) // 60 секунд
    public void autoBookOrders() {
        List<DriverFilters> filtersList = driverFiltersRepository.findAll();
        for (DriverFilters filters : filtersList) {
            List<Orders> matchingOrders = ordersRepository.findOrdersByDriverFilters(filters);
            if (matchingOrders.isEmpty()) {
                logger.warn("No matching orders found for driver: {}", filters.getDriver().getId());
                continue; // Переходим к следующему водителю
            }
            for (Orders order : matchingOrders) {
                if (order.getExecutor() == null) { // Проверяем, что заказ не забронирован
                    // Отправляем сообщение с инлайн-кнопками
                    String messageText = "У вас новый заказ 🌏\n" +
                            "ID заказа: " + order.getId() + "\n" +
                            "Вес: " + order.getWeight() + " тонн\n" +
                            "Цена: " + order.getPrice() + " " + order.getCurrency();

                    telegramBot.sendMessageWithInlineButtons(filters.getDriver().getChatId(), messageText, order.getId());
                }
            }
        }
    }

}
