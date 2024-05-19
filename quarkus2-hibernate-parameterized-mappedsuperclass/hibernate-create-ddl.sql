create sequence hibernate_sequence start 1 increment 1;

    create table four (
       id int8 not null,
        four_concrete_prop varchar(255),
        primary key (id)
    );

    create table one (
       id int8 not null,
        abs_one_string_prop varchar(255),
        one_concrete_prop varchar(255),
        two_id int8,
        primary key (id)
    );

    create table three (
       id int8 not null,
        abs_three_string_prop varchar(255),
        three_concrete_prop varchar(255),
        four_id int8,
        primary key (id)
    );

    create table two (
       id int8 not null,
        abs_two_string_prop varchar(255),
        two_concrete_prop varchar(255),
        three_id int8,
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
       add constraint FK3i6tu8g51qeuegifndje6ig5j 
       foreign key (three_id) 
       references three;
