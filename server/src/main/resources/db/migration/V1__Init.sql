create table members
(
    member_id           integer not null unique,
    joined_at    timestamp,
    name         varchar(255),
    phone_number varchar(255),
    primary key (member_id)
);

create table games
(
    game_id        integer not null unique,
    played_at timestamp,
    played_in varchar(255),
    primary key (game_id)
);

create table member_game
(
    member_game_id integer     not null unique,
    member_id      integer     not null,
    game_id        integer     not null,
    game_result    varchar(10) not null,
    score          numeric check (score >= 0),
    primary key (member_game_id),
    CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES members (member_id),
    CONSTRAINT fk_game FOREIGN KEY (game_id) REFERENCES games (game_id)
);
