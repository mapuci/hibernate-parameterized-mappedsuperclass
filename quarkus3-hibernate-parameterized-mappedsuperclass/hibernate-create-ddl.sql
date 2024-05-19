
    create sequence four_seq start with 1 increment by 50;

    create sequence one_seq start with 1 increment by 50;

    create sequence three_seq start with 1 increment by 50;

    create sequence two_seq start with 1 increment by 50;

    create table four (
        id bigint not null,
        four_concrete_prop varchar(255),
        primary key (id)
    );

    create table one (
        id bigint not null,
        two_id bigint,
        abs_one_string_prop varchar(255),
        one_concrete_prop varchar(255),
        primary key (id)
    );

    create table three (
        four_id bigint,
        id bigint not null,
        abs_tree_string_prop varchar(255),
        three_concrete_prop varchar(255),
        primary key (id)
    );

    create table two (
        id bigint not null,
        tree_id bigint,
        abs_two_string_prop varchar(255),
        two_concrete_prop varchar(255),
        primary key (id)
    );

    alter table if exists one 
       add constraint FKyaf53hrpvmr0pusr4729byi8 
       foreign key (two_id) 
       references two;

    alter table if exists three 
       add constraint FKitkt4ttj44vmu4wyfhfagsd5v 
       foreign key (four_id) 
       references four;

    alter table if exists two 
       add constraint FKa2u0tw7m87xxdkmb2v93r51v2 
       foreign key (tree_id) 
       references three;
