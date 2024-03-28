create table robot
(
    id               uuid not null,
    name             varchar(255),
    direction        varchar(255),
    coordinate_x     integer,
    coordinate_y     integer,
    start_operation  timestamp(6),
    finish_operation timestamp(6),
    active           boolean,
    primary key (id)
);
