package com.logistics.service;

import com.logistics.model.Route;
import com.logistics.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {
    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public Route createRoute(Route route) {
        return routeRepository.save(route);
    }

//    public Route updateRoute(Long id, Route updatedRoute) {
//        Optional<Route> existingRoute = routeRepository.findById(id);
//        if (existingRoute.isPresent()) {
//            Route route = existingRoute.get();
//            route.setLocation(updatedRoute.getLocation());
//            route.setSequence(updatedRoute.getSequence());
//            return routeRepository.save(route);
//        } else {
//            throw new IllegalArgumentException("Route with ID " + id + " not found.");
//        }
//    }

    public Route getRouteById(Long id) {
        return routeRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Route with ID " + id + " not found.")
        );
    }

    public void deleteRoute(Long id) {
        routeRepository.deleteById(id);
    }

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }
}
