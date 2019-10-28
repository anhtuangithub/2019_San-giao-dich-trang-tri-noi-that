

insert into order_status(name) values("Chờ xác nhận");
insert into order_status(name) values("Xác nhận");
insert into order_status(name) values("Đang vận chuyển");
insert into order_status(name) values("Đã thanh toán");
insert into order_status(name) values("Đã giao");
insert into order_status(name) values("Khách hàng hủy");
insert into order_status(name) values("Cửa hàng hủy");

insert into role(name) values("ROLE_ADMIN");
insert into role(name) values("ROLE_STORE");
insert into role(name) values("ROLE_USER");

insert into member_type(name,price) values("Thẻ thành viên đăng kí 1 tháng", 500000);
insert into member_type(name,price) values("Thẻ thành viên đăng kí 3 tháng", 1400000);
insert into member_type(name,price) values("Thẻ thành viên đăng kí 6 tháng", 2700000);
insert into member_type(name,price) values("Thẻ thành viên đăng kí 1 năm", 5000000);