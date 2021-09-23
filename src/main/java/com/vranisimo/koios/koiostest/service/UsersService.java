package com.vranisimo.koios.koiostest.service;

import com.vranisimo.koios.koiostest.controller.UserRepository;
import com.vranisimo.koios.koiostest.model.User;
import com.vranisimo.koios.koiostest.model.UsersComparators;
import com.vranisimo.koios.koiostest.model.paging.Column;
import com.vranisimo.koios.koiostest.model.paging.Order;
import com.vranisimo.koios.koiostest.model.paging.Page;
import com.vranisimo.koios.koiostest.model.paging.PagingRequest;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class UsersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersService.class);

    @Autowired
    UserRepository userRepository;

    private static final Comparator<User> EMPTY_COMPARATOR = (e1, e2) -> 0;

    public Page<User> getUsers(PagingRequest pagingRequest) {
        return getPage(userRepository.findAll(), pagingRequest);
    }

    private Page<User> getPage(List<User> users, PagingRequest pagingRequest) {
        List<User> filtered = users.stream()
                .sorted(sortUsers(pagingRequest))
                .filter(filterUsers(pagingRequest))
                .skip(pagingRequest.getStart())
                .limit(pagingRequest.getLength())
                .collect(Collectors.toList());

        long count = users.stream()
                .filter(filterUsers(pagingRequest))
                .count();

        Page<User> page = new Page<>(filtered);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());

        return page;
    }

    private Predicate<User> filterUsers(PagingRequest pagingRequest) {
        if (pagingRequest.getSearch() == null || Strings.isEmpty(pagingRequest.getSearch().getValue())) {
            return user -> true;
        }
        String filterValue = pagingRequest.getSearch().getValue().toLowerCase();

        return user -> user.getFirstname().toLowerCase().contains(filterValue)
                || user.getLastname().toLowerCase().contains(filterValue)
                || user.getEmail().toLowerCase().contains(filterValue);
    }

    private Comparator<User> sortUsers(PagingRequest pagingRequest) {
        if (pagingRequest.getOrder() == null) {
            return EMPTY_COMPARATOR;
        }

        try {
            Order order = pagingRequest.getOrder().get(0);
            int columnIndex = order.getColumn();
            Column column = pagingRequest.getColumns().get(columnIndex);

            Comparator<User> comparator = UsersComparators.getComparator(column.getData(), order.getDir());
            if (comparator == null) {
                return EMPTY_COMPARATOR;
            }
            return comparator;
        } catch (Exception e) {
            LOGGER.error("Error while sorting users", e);
        }
        return EMPTY_COMPARATOR;
    }
}