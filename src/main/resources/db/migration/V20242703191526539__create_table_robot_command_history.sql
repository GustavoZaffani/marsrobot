create table robot_command_history
(
    id           uuid not null,
    robot_id     uuid,
    sequence     bigint,
    direction    varchar(255),
    coordinate_x integer,
    coordinate_y integer,
    created_at   timestamp(6),
    command      varchar(255),
    primary key (id),
    constraint fk_robot_command_history_robot_id foreign key (robot_id) references robot
);
