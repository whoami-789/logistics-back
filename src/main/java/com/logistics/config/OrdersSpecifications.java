package com.logistics.config;

import com.logistics.model.Orders;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class OrdersSpecifications {

    // Фильтр по дате начала
    public static Specification<Orders> hasStartDate(LocalDate startDate) {
        return (root, query, cb) ->
                startDate != null ? cb.equal(root.get("startDate"), startDate) : null;
    }

    // Фильтр по типу кузова
    public static Specification<Orders> hasCarBody(String carBody) {
        return (root, query, cb) ->
                carBody != null && !carBody.isEmpty() ? cb.equal(root.get("carBody"), carBody) : null;
    }

    // Фильтр по статусу
    public static Specification<Orders> hasStatus() {
        return (root, query, cb) ->
                cb.equal(root.get("status"), "Создан");
    }

    // Фильтр по тому, что customer не равен userId
    public static Specification<Orders> isNotCustomer(Long userId) {
        return (root, query, cb) ->
                userId != null ? cb.notEqual(root.get("customer").get("id"), userId) : null;
    }

    // Фильтр по минимальному весу
    public static Specification<Orders> hasMinWeight(Integer minWeight) {
        return (root, query, cb) ->
                minWeight != null ? cb.greaterThanOrEqualTo(root.get("weight"), minWeight) : null;
    }

    // Фильтр по максимальному весу
    public static Specification<Orders> hasMaxWeight(Integer maxWeight) {
        return (root, query, cb) ->
                maxWeight != null ? cb.lessThanOrEqualTo(root.get("weight"), maxWeight) : null;
    }

    // Фильтр по диапазону цены
    public static Specification<Orders> hasPriceRange(Integer priceFrom, Integer priceTo) {
        return (root, query, cb) -> {
            if (priceFrom != null && priceTo != null) {
                return cb.between(root.get("price"), priceFrom, priceTo);
            } else if (priceFrom != null) {
                return cb.greaterThanOrEqualTo(root.get("price"), priceFrom);
            } else if (priceTo != null) {
                return cb.lessThanOrEqualTo(root.get("price"), priceTo);
            } else {
                return null; // если оба параметра пустые, то фильтр не применяется
            }
        };
    }

    // Фильтр по валюте
    public static Specification<Orders> hasCurrency(String currency) {
        return (root, query, cb) ->
                currency != null && !currency.isEmpty() ? cb.equal(root.get("currency"), currency) : null;
    }

    // Фильтр по маршруту (проверка вхождения в список маршрутов)
    public static Specification<Orders> hasRoute(String countryFrom, String cityFrom, String countryTo, String cityTo) {
        return (root, query, cb) -> {
            // Добавляем фильтр по маршруту только если хотя бы один из параметров не null или пуст
            if (countryFrom != null || cityFrom != null || countryTo != null || cityTo != null) {
                Join<Object, Object> routes = root.join("routes"); // Соединение с таблицей маршрутов
                Predicate predicate = cb.conjunction(); // Начинаем с пустого предиката

                if (countryFrom != null && !countryFrom.isEmpty()) {
                    predicate = cb.and(predicate, cb.equal(routes.get("countryFrom"), countryFrom));
                }
                if (cityFrom != null && !cityFrom.isEmpty()) {
                    predicate = cb.and(predicate, cb.equal(routes.get("cityFrom"), cityFrom));
                }
                if (countryTo != null && !countryTo.isEmpty()) {
                    predicate = cb.and(predicate, cb.equal(routes.get("countryTo"), countryTo));
                }
                if (cityTo != null && !cityTo.isEmpty()) {
                    predicate = cb.and(predicate, cb.equal(routes.get("cityTo"), cityTo));
                }

                return predicate; // возвращаем собранный предикат
            }
            return null; // если все параметры маршрута пусты, возвращаем null
        };
    }
}
