package com.vranisimo.koios.koiostest.model;

import com.vranisimo.koios.koiostest.model.paging.Direction;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class UsersComparators {

    static class Key {
        String name;
        Direction dir;

        public Key(String name, Direction dir) {
            this.name = name;
            this.dir = dir;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Key key = (Key) o;
            return Objects.equals(name, key.name) && dir == key.dir;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, dir);
        }
    }

    static Map<Key, Comparator<User>> map = new HashMap<>();

    static {
        map.put(new Key("firstname", Direction.asc), Comparator.comparing(User::getFirstname));
        map.put(new Key("firstname", Direction.desc), Comparator.comparing(User::getFirstname).reversed());

        map.put(new Key("lastname", Direction.asc), Comparator.comparing(User::getLastname));
        map.put(new Key("lastname", Direction.desc), Comparator.comparing(User::getLastname).reversed());

        map.put(new Key("email", Direction.asc), Comparator.comparing(User::getEmail));
        map.put(new Key("email", Direction.desc), Comparator.comparing(User::getEmail).reversed());

        map.put(new Key("birthday", Direction.asc), Comparator.comparing(User::getBirthdate));
        map.put(new Key("birthday", Direction.desc), Comparator.comparing(User::getBirthdate).reversed());
    }

    public static Comparator<User> getComparator(String name, Direction dir) {
        return map.get(new Key(name, dir));
    }

    private UsersComparators() {
    }

    public static Map<Key, Comparator<User>> getMap() {
        return map;
    }

}
