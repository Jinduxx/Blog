
DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users (
                         id serial PRIMARY KEY,
                         user_name VARCHAR(130) NOT NULL UNIQUE,
                         first_name VARCHAR(150) NOT NULL,
                         last_name VARCHAR(150) NOT NULL,
                         email VARCHAR(36) NOT NULL UNIQUE,
                         password VARCHAR(50) NOT NULL,
                         create_time TIMESTAMP,
                         contact VARCHAR(50)
);


DROP TABLE IF EXISTS posts CASCADE;

CREATE TABLE IF NOT EXISTS posts (
                         id serial PRIMARY KEY,
                         title VARCHAR(130) NOT NULL,
                         body VARCHAR(150) NOT NULL,
                         create_time TIMESTAMP,
                         user_id BIGINT NOT NULL,
                         FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS favourite_posts CASCADE;

CREATE TABLE IF NOT EXISTS favourite_posts (
                                     original_post_id BIGINT NOT NULL,
                                     title VARCHAR(130) NOT NULL,
                                     body VARCHAR(150) NOT NULL,
                                     create_time TIMESTAMP,
                                     post_user_id BIGINT NOT NULL,
                                     user_id BIGINT NOT NULL,
                                     FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);


DROP TABLE IF EXISTS likes CASCADE;

CREATE TABLE IF NOT EXISTS likes (
                         id serial PRIMARY KEY,
                         user_id BIGINT NOT NULL,
                         post_id BIGINT NOT NULL,
                         FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
                         FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE ON UPDATE CASCADE
);


DROP TABLE IF EXISTS likes_comment CASCADE;

CREATE TABLE IF NOT EXISTS likes_comment (
                         id serial PRIMARY KEY,
                         user_id BIGINT NOT NULL,
                         comment_id BIGINT NOT NULL,
                         FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
                         FOREIGN KEY (comment_id) REFERENCES comments (id) ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS friendDtos CASCADE;

CREATE TABLE IF NOT EXISTS friendDtos (
                         id serial PRIMARY KEY,
                         friends_id BIGINT NOT NULL,
                         user_id BIGINT NOT NULL,
                         FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE
);





