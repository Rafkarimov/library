package com.nv.sberschool.library.constants;

import java.util.List;

public interface SecurityConstants {
    List<String> RESOURCES_WHITE_LIST = List.of(
            "/resources/**",
            "/js/**",
            "/css/**",
            "/",
            // -- Swagger UI v3 (OpenAPI)
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/login",
            "/rest/users/auth",
            "/users/registration",
            "/users/remember-password",
            "/users/change-password",
            "/error");

    //TODO  "/books/{bookId}", "/books/add",
    List<String> BOOKS_WHITE_LIST = List.of(
            //TODO /books/{\\d+}
            "/books/{bookId}",
            "/books/search",
            "/books/search/author",
            "/books"
    );

    List<String> RENT_WHITE_LIST = List.of(
            "/rent/get-book/*",
            "/rent/get-book",
            "/rent/user-books",
            "/rent/return-book/{id}"
    );

    List<String> AUTHORS_WHITE_LIST = List.of(
            "/authors/{id}",
            "/authors/search",
            "/authors"
    );

    List<String> AUTHORS_PERMISSION_LIST = List.of(
            "/authors/add",
            "/authors/update",
            "/authors/delete",
            "/authors/get-book/*",
            "/authors/add",
            "/authors/download/*",
            "/rest/authors/soft-delete/{id}"
    );

    List<String> BOOKS_PERMISSION_LIST = List.of(
            "/books/add",
            "/books/update",
            "/books/delete",
            "/publish/get-book/*",
            "/books/add",
            "/books/download/*"
    );

    List<String> USERS_PERMISSION_LIST = List.of(
            "/users",
            "/users/add",
            "/users/delete",
            "/users/add-librarian"
    );

    List<String> USERS_WHITE_LIST = List.of(
            "/users/profile/{id}",
            "/users/profile/update/{id}"

    );

    List<String> USERS_REST_WHITE_LIST = List.of("/users/auth");
}
