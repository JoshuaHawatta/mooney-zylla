package com.joshuahawatta.moneyzilla.enums;

import lombok.Getter;

@Getter
public enum PublicRoutes {
    CREATE_ACCOUNT("/user"),
    LOGIN("/user/login"),
    INDEX("/");

    private final String route;

    PublicRoutes(String route) { this.route = route; }
}
