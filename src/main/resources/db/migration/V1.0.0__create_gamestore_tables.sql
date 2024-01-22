create table "board_game" ("id" int8 generated by default as identity, "cost" float8, "name" varchar(255), "image_url" varchar(255), "players" int4, primary key ("id"));
create table "booking" ("id" int8 generated by default as identity, "days" int4, "user_id" int8 not null, primary key ("id"));
create table "product" ("game_id" int8 not null, "count" int4, "in_stock" boolean, primary key ("game_id"));
create table "user" ("id" int8 generated by default as identity, "password" varchar(64) not null, "username" varchar(50) not null, primary key ("id"));
create table "boardgame_booking" ("game_id" int8 not null, "booking_id" int8 not null, primary key ("game_id", "booking_id"));
alter table if exists "user" add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique ("username");
alter table if exists "booking" add constraint "FKpr2wacfs0vxh9v61dni9ugonn" foreign key ("user_id") references "user";
alter table if exists "product" add constraint "FKojdegdu3y5hd47n6iahllk0v8" foreign key ("game_id") references "board_game";
alter table if exists "boardgame_booking" add constraint "FKprcpytj0kj1ieduo9r5irloet" foreign key ("booking_id") references "booking";
alter table if exists "boardgame_booking" add constraint "FKfalypnaycpbg09mblborxeqs5" foreign key ("game_id") references "board_game";