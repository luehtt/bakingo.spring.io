CREATE TABLE public.roles
(
    role_id integer DEFAULT nextval('roles_role_id_seq'::regclass) PRIMARY KEY NOT NULL,
    name varchar(255)
);
INSERT INTO public.roles (role_id, name) VALUES (1, 'ADMIN');
INSERT INTO public.roles (role_id, name) VALUES (2, 'USER');

CREATE TABLE public.users
(
    user_id integer DEFAULT nextval('users_user_id_seq'::regclass) PRIMARY KEY NOT NULL,
    address varchar(255),
    email varchar(255),
    enabled boolean,
    name varchar(255),
    password varchar(255),
    phone varchar(255),
    created_at timestamp,
    updated_at timestamp
);
INSERT INTO public.users (user_id, address, email, enabled, name, password, phone, created_at, updated_at) VALUES (6, 'Tiểu thuyết Trung Quốc', 'haau@demo.com', true, 'Hạ Âu', '$2a$10$GX2JdpNnR8cAiowQtbyFxODH2149qaht1dwJ7DTcXDQNsaIczj/wK', '1998.808.1080', '2018-10-04 10:04:31.763000', '2018-10-04 10:04:35.964000');
INSERT INTO public.users (user_id, address, email, enabled, name, password, phone, created_at, updated_at) VALUES (1, 'Khu vực 51', 'thanhtoan@demo.com', true, 'Lue Bakingo', '$2a$10$RR5bPqdxvMs2xGQPa9J0HensSuHSAhNED9E9kQeIt1eJiPjmyGYdS', '1992.008.0120', '2018-10-04 10:04:32.639000', '2018-10-04 10:04:36.829000');
INSERT INTO public.users (user_id, address, email, enabled, name, password, phone, created_at, updated_at) VALUES (2, 'Gầm cầu đâu đó', 'hieuthao@demo.com', true, 'Hiếu Thảo', '$2a$10$RR5bPqdxvMs2xGQPa9J0HensSuHSAhNED9E9kQeIt1eJiPjmyGYdS', '1920.011.0150', '2018-10-04 10:04:28.767000', '2018-10-04 10:04:34.369000');
INSERT INTO public.users (user_id, address, email, enabled, name, password, phone, created_at, updated_at) VALUES (7, 'Bán nước tương Bình Dương', 'trantran@demo.com', true, 'Trân Trân', '$2a$10$yNIx0EdCLqswZTgyyHUqauu8CFWjUda8hWQ1sTxs8dJvHfBFrr032', '1998.888.8080', null, null);
INSERT INTO public.users (user_id, address, email, enabled, name, password, phone, created_at, updated_at) VALUES (3, 'Khu phố đèn đỏ', 'tachuong@demo.com', true, 'Tạ Thị Chương', '$2a$10$RR5bPqdxvMs2xGQPa9J0HensSuHSAhNED9E9kQeIt1eJiPjmyGYdS', '1936.007.0001', '2018-10-04 10:04:30.805000', '2018-10-04 10:04:35.173000');

CREATE TABLE public.user_roles
(
    user_id integer NOT NULL,
    role_id integer NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES public.users (user_id),
    CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES public.roles (role_id)
);
INSERT INTO public.user_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO public.user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO public.user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO public.user_roles (user_id, role_id) VALUES (3, 2);
INSERT INTO public.user_roles (user_id, role_id) VALUES (6, 2);
INSERT INTO public.user_roles (user_id, role_id) VALUES (7, 2);

CREATE TABLE public.item_groups
(
    group_id integer DEFAULT nextval('item_groups_group_id_seq'::regclass) PRIMARY KEY NOT NULL,
    name varchar(255)
);
INSERT INTO public.item_groups (group_id, name) VALUES (2, 'Bánh bột');
INSERT INTO public.item_groups (group_id, name) VALUES (1, 'Bánh kem');
INSERT INTO public.item_groups (group_id, name) VALUES (3, 'Bánh mứt');

CREATE TABLE public.items
(
    product_id integer DEFAULT nextval('items_product_id_seq'::regclass) PRIMARY KEY NOT NULL,
    discount integer,
    enabled boolean,
    info text,
    name varchar(255),
    photo varchar(255),
    price integer,
    group_id integer NOT NULL,
    created_at timestamp,
    updated_at timestamp,
    CONSTRAINT fkow3cnyigcyx84c9mbihqjcm2c FOREIGN KEY (group_id) REFERENCES public.item_groups (group_id)
);
INSERT INTO public.items (product_id, discount, enabled, info, name, photo, price, group_id, created_at, updated_at) VALUES (1, 10, true, 'Bánh Macaron là một loại bánh ngọt của Pháp được làm từ lòng trắng trứng, đường bột, đường cát, bột hạnh nhân và thêm màu thực phẩm. Nhân bánh thường được lấp đầy với mứt, ganache hoặc kem bơ kẹp giữa hai mặt bánh. Bánh Macaron được làm từ nguyên liệu chính là trứng, đường cát, bột hạnh nhân, và một chút màu thực phẩm. Phủ bên trong hai lớp bánh giòn tan là lớp nhân được làm từ kem bơ hoặc mứt.', 'Hộp bánh Macaron Raspberries', 'cupcakes-3395293_1920.jpg', 200000, 1, null, null);
INSERT INTO public.items (product_id, discount, enabled, info, name, photo, price, group_id, created_at, updated_at) VALUES (4, 5, true, 'Bánh Cupcakes thường có kích thước nhỏ, thường được nướng trong những chiếc cốc nhỏ bao gồm 2 phần là phần cakes và phần topping là kem được trang trí bên trên. Bánh Cupcakes thường được làm dựa trên công thức điển hình: 1 cup bơ, 2 cup đường, 3 cup bột, 4 trứng, 1 cup sữa và 1 thìa bột nở. Phần topping đa dạng về mùi vị như cacao, trà xanh, vanilla, lá dứa, fondant, gum paste...', 'Hộp bánh Cupcakes Chocolate', 'cupcakes-1452178_1920.jpg', 160000, 1, null, null);
INSERT INTO public.items (product_id, discount, enabled, info, name, photo, price, group_id, created_at, updated_at) VALUES (5, 15, true, 'Bánh pho mát là một loại bánh ngọt có thể có một hoặc nhiều lớp. Lớp chính, cũng là lớp dày nhất, bao gồm một hỗn hợp gồm pho mát tươi và mềm (thường là pho mát kem hoặc ricotta), trứng, và đường. Nếu bánh có lớp đế thì lớp này thường bao gồm một lớp vỏ hoặc bánh vụn (hoặc digestive biscuit), graham crackers, pastry, hoặc sponge cake. Nó được nướng hoặc không (thường được giữ lạnh). Bánh pho mát thường được làm từ đường và hương vị hoặc với trái cây, whipping cream, nuts, fruit sauce, hoặc xi rô sôcôla. Bánh pho mát có thể có nhiều hương vị, ví dụ như dâu, bí ngô, chanh ta, hạt dẻ hoặc kẹo bơ cứng.', 'Bánh Phô mai Dâu tây', 'food-1985666_1920.jpg', 180000, 3, null, null);
INSERT INTO public.items (product_id, discount, enabled, info, name, photo, price, group_id, created_at, updated_at) VALUES (7, 5, true, 'Bánh ngọt là một loại bánh mì từ bột nhào, được nướng lên dùng để tráng miệng. Bánh ngọt có nhiều loại, có thể phân loại dựa theo nguyên liệu và kỹ thuật chế biến như bánh ngọt làm từ lúa mì, bơ, bánh ngọt dạng bọt biển. Bánh ngọt được làm từ các nguyên liệu: bột nở làm bánh, trái anh đào, sôcôla, trứng gà, bột mì...', 'Hộp bánh Chocolate Hoa đào', 'sakura-cakes-1803074_1920.jpg', 160000, 1, null, '2018-10-27 08:59:42.451000');
INSERT INTO public.items (product_id, discount, enabled, info, name, photo, price, group_id, created_at, updated_at) VALUES (8, 5, true, 'Bánh Cupcakes thường có kích thước nhỏ, thường được nướng trong những chiếc cốc nhỏ bao gồm 2 phần là phần cakes và phần topping là kem được trang trí bên trên. Bánh Cupcakes thường được làm dựa trên công thức điển hình: 1 cup bơ, 2 cup đường, 3 cup bột, 4 trứng, 1 cup sữa và 1 thìa bột nở. Phần topping đa dạng về mùi vị như cacao, trà xanh, vanilla, lá dứa, fondant, gum paste...', 'Hộp bánh Cupcake Kem', 'cupcakes-2285209_1280.jpg', 120000, 1, '2018-10-04 15:55:20.625000', '2018-10-04 15:58:33.728000');
INSERT INTO public.items (product_id, discount, enabled, info, name, photo, price, group_id, created_at, updated_at) VALUES (9, 0, true, 'Muffin là một món bánh tráng miệng cực kì quen thuộc và phổ biến trên thế giới. Thông thường, muffin thường được làm với kết cấu đặc ẩm, hương vị mặn, ngọt tùy theo công thức và sở thích của người làm bánh.', 'Hộp bánh Muffin Chocolate', 'gugelhupf-3643259_1280.jpg', 100000, 1, '2018-10-04 16:00:04.446000', '2018-10-04 16:00:04.446000');
INSERT INTO public.items (product_id, discount, enabled, info, name, photo, price, group_id, created_at, updated_at) VALUES (6, 15, true, 'Bánh Tart là loại bánh có từ lâu đời ở phương Tây xuất hiện từ thế kỉ 14 như một món tráng miệng cao cấp, nghệ thuật. Từ thế kỉ 19 trở đi, bánh Tart vươn lên trở thành món bánh không thể thiếu trong các buổi tiệc trà sang trọng của người dân nước Anh. Bánh Tart có kiểu dáng có phần tao nhã cùng lớp vỏ mỏng và phần nhân đầy màu sắc, có thể được làm từ dâu tây, dâu đen, mơ, sôcôla... Bánh Tart có nhiều kích cỡ khác nhau nhưng loại thông dụng nhất là loại bánh có kích thước nhỏ ăn cùng ly trà chiều ấm nóng thực sự đem lại cảm giác thoải mái, nhẹ nhàng cho người thưởng thức.', 'Bánh Tart Mơ', 'quark-tart-2549438_1920.jpg', 200000, 3, null, null);
INSERT INTO public.items (product_id, discount, enabled, info, name, photo, price, group_id, created_at, updated_at) VALUES (2, 20, true, 'Bánh Tiramisu là một loại bánh ngọt tráng miệng vị cà phê rất nổi tiếng của nước Ý, gồm các lớp bánh quy Savoiardi, nhúng cà phê xen kẽ với hỗn hợp trứng, đường, phô mai mascarpone đánh bông, thêm một ít bột cacao. Bánh Tiramisu được làm từ nguyên liệu cà phê hoặc một số nguyên liệu khác tạo nên nhiều biến thể như tiramisu sô cô la, tiramisu rượu hạnh nhân Ý (rượu amaretto), tiramisu dâu, tiramisu chanh, tiramisu thơm (dứa), tiramisu sữa chua, tiramisu chuối, tiramisu phúc bồn tử, tiramisu dừa, thậm chí có cả tiramisu bia.', 'Bánh Tiramisu Chocolate', 'tiramisu-1996424_1920.jpg', 180000, 1, null, null);
INSERT INTO public.items (product_id, discount, enabled, info, name, photo, price, group_id, created_at, updated_at) VALUES (11, 15, true, 'Bánh Tiramisu là một loại bánh ngọt tráng miệng vị cà phê rất nổi tiếng của nước Ý, gồm các lớp bánh quy Savoiardi, nhúng cà phê xen kẽ với hỗn hợp trứng, đường, phô mai mascarpone đánh bông, thêm một ít bột cacao. Bánh Tiramisu được làm từ nguyên liệu cà phê hoặc một số nguyên liệu khác tạo nên nhiều biến thể như tiramisu sô cô la, tiramisu rượu hạnh nhân Ý (rượu amaretto), tiramisu dâu, tiramisu chanh, tiramisu thơm (dứa), tiramisu sữa chua, tiramisu chuối, tiramisu phúc bồn tử, tiramisu dừa, thậm chí có cả tiramisu bia.', 'Bánh Tiramisu Cà phê Đường đen', 'cake-3640503_1280.jpg', 100000, 1, '2018-10-04 16:05:05.866000', '2018-10-04 16:05:05.866000');
INSERT INTO public.items (product_id, discount, enabled, info, name, photo, price, group_id, created_at, updated_at) VALUES (12, 5, true, 'Bánh khúc hay xôi khúc, xôi cúc là loại bánh có nguồn gốc từ vùng đồng bằng Bắc Bộ, làm từ lá rau khúc (có hai loại là rau khúc tẻ và rau khúc nếp có khi còn được gọi là khúc Ông và khúc Bà, nhưng khi làm bánh, người ta thường chọn lá rau khúc nếp, bởi lá khúc nếp thơm ngon hơn nhiều), gạo nếp, nhân đậu xanh, thịt lợn mỡ. Bánh thường được làm vào mùa rau khúc - dịp tháng 2, tháng 3 Âm lịch.', 'Bánh Khúc xôi cúc', 'recipe-1302-635575158948174690.jpg', 80000, 2, '2018-10-04 16:10:17.218000', '2018-10-04 16:10:17.218000');
INSERT INTO public.items (product_id, discount, enabled, info, name, photo, price, group_id, created_at, updated_at) VALUES (13, 0, true, 'Bánh đậu xanh là một loại thức ăn ngọt làm từ bột đậu xanh quết nhuyễn với đường và dầu thực vật hay mỡ động vật, thường là mỡ heo. Bánh được cắt thành từng khối vuông nhỏ, gói giấy bạc thành hộp nhỏ hay gói giấy thấm mỡ thành từng thỏi. Bánh thường được dùng khi uống trà tàu hay chè xanh, khi đó sẽ tạo cảm giác thư thái. Địa phương làm bánh đậu xanh nổi tiếng là Hải Dương. Nguyên liệu để chế biến bánh gồm: Đậu xanh, đường kết tinh, mỡ lợn, tinh dầu của hoa bưởi. Tất cả đều phải được chọn lọc và được chế biến tinh khiết. Bốn nguyên liệu trên pha trộn với nhau theo một tỉ lệ hợp lý, vượt tỉ lệ đó bánh sẽ kém chất lượng. Giấy gói bánh, màu sắc của nhãn, phải được xem xét cẩn thận để bánh giữ được lâu và tôn vẻ đẹp của bánh. Bánh từ lâu đã được đóng theo một cách ổn định riêng: 10 khẩu mỏng xếp 5 hàng (8,5 x 3,2 x1,1 cm) nặng 45 gam, gần đây đã có những cải tiến, nhưng quy cách của khẩu không thay đổi.', 'Bánh Đậu xanh dẻo', 'banh-dau-xanh-deo-mem-trang-ngan.jpg', 80000, 2, '2018-10-04 16:13:06.574000', '2018-10-04 16:13:06.574000');
INSERT INTO public.items (product_id, discount, enabled, info, name, photo, price, group_id, created_at, updated_at) VALUES (10, 0, true, 'Muffin là một món bánh tráng miệng cực kì quen thuộc và phổ biến trên thế giới. Thông thường, muffin thường được làm với kết cấu đặc ẩm, hương vị mặn, ngọt tùy theo công thức và sở thích của người làm bánh.', 'Hộp bánh Muffin Schokomuffins', 'muffins-1844538_1280.jpg', 180000, 1, '2018-10-04 16:01:49.024000', '2018-10-27 08:45:46.684000');
INSERT INTO public.items (product_id, discount, enabled, info, name, photo, price, group_id, created_at, updated_at) VALUES (16, 0, true, 'Bánh bèo gồm có ba phần chính là bánh làm từ bột gạo, nhân để rắc lên bánh làm bằng tôm xay nhuyễn, và nước chấm, một hỗn hợp mà nước mắm là thành phần chính và thường đổ trực tiếp vào bánh chứ không cần chấm. Thành phần phụ của bánh bèo thường là mỡ hành, đậu phộng rang giã nhỏ. Tuỳ theo địa phương, có những cách thêm bớt khác nhau cho món bánh này, ví dụ ở Sài Gòn thường bỏ đậu xanh, đồ chua, lại cho ăn kèm bánh đúc, bánh ít, bánh bột lọc.', 'Bánh Bèo bèo nhèo', 'banh-beo.jpg', 100000, 2, '2018-10-26 16:00:14.764000', '2018-10-26 16:01:04.129000');
INSERT INTO public.items (product_id, discount, enabled, info, name, photo, price, group_id, created_at, updated_at) VALUES (14, 0, true, 'Bánh phu thuê. Cái tên kỳ khôi này ở đâu mà ra? Thứ bột vàng và trong như hổ phách ấy, dẻo và quánh dưới hàm răng, là một thứ bánh rất ngon. Dù sao, cũng là một thứ bột thẳng thắn, vì nó dễ cho ta đoán trước để mà thèm thuồng những cái ngon ngọt hơn ẩn náu bên trong. Qua cái màu vàng óng ánh ấy, màu trắng của sợi dừa và màu vàng nhạt của đậu thêm một sắc nóng ấm và thân mật. Tôi bao giờ cũng ưa thức ăn nào có một hình sắc đẹp đẽ cái đẹp lúc trông ngắm giúp nhiều cho cái thưởng thức lúc ăn lắm.', 'Bánh Phu thuê xu xê', 'banh-phu-thue.jpg', 80000, 2, '2018-10-04 16:14:55.554000', '2018-10-27 08:42:07.248000');
INSERT INTO public.items (product_id, discount, enabled, info, name, photo, price, group_id, created_at, updated_at) VALUES (20, 0, true, 'Bánh dứa với cách làm đơn giản và vị ngon bất ngờ sẽ là một món bánh điểm tâm chống đói vô cùng hấp dẫn vào buổi xế chiều', 'Bánh Dứa hoa mai', 'pineapplepokecake.jpg', 120000, 3, '2018-10-27 08:53:26.318000', '2018-10-27 08:53:59.709000');
INSERT INTO public.items (product_id, discount, enabled, info, name, photo, price, group_id, created_at, updated_at) VALUES (15, 15, true, 'Bánh táo tapioca là một món ăn tráng miệng truyền thống của người Mỹ đặc biệt là ở các nhà hàng miền Bắc Mỹ. Bánh táo tapioca có phần vỏ bánh mỏng, giòn dai, nhưng ẩn chứa bên trong là cả một phần nhân ngọt ngào quyến rũ. Nhân bánh có vị ngọt dịu lại pha thêm một chút vị chua nhẹ của táo, thêm vào đó là vị béo của bơ, mùi thơm của bột quế khiến ai đã từng được nếm thử một miếng bánh thôi cũng không thể nào quên được.', 'Bánh Táo tapioca', 'caramel-apple-cake-sl.jpg', 180000, 3, '2018-10-04 16:17:31.325000', '2018-10-27 08:57:34.317000');
INSERT INTO public.items (product_id, discount, enabled, info, name, photo, price, group_id, created_at, updated_at) VALUES (19, 5, true, 'Bánh bông lan chocolate dâu tây ngọt ngào này xứng đáng là biểu tượng của một tình yêu nồng nàn và say đắm. Cốt bánh bông lan chocolate mềm ẩm, thoáng chút vị mứt Nutella, được phủ lên lớp dâu tây tươi ngon và lớp kem béo ngậy.  Bộ ba ấy khiến cho chiếc bánh trở nên hấp dẫn hơn bao giờ hết!', 'Bánh Sôcôla dâu tây xanh', 'chocolate-cherry-berry.jpg', 250000, 3, '2018-10-27 08:50:40.075000', '2018-10-27 09:02:36.856000');
INSERT INTO public.items (product_id, discount, enabled, info, name, photo, price, group_id, created_at, updated_at) VALUES (17, 10, true, 'Bánh ống tròn, dài béo béo giòn và thơm mùi cốt dừa lẫn mùi lá dừa. Đây là món ăn chơi thú vị vào các buổi chợ sáng và các buổi xế chiều.Bánh ngọt vừa phải, có độ dẻo mềm nên ăn nhiều cũng không thấy ngán. Bánh ống được bán rất phổ biến ở Sóc Trăng, và được trẻ nhỏ rất yêu thích.', 'Bánh Ống siêu nóng', 'banh-ong.jpg', 60000, 2, '2018-10-26 16:22:19.086000', '2018-11-14 15:28:38.634000');

CREATE TABLE public.comments
(
    comment_id integer DEFAULT nextval('comments_comment_id_seq'::regclass) PRIMARY KEY NOT NULL,
    content text,
    created_at timestamp,
    updated_at timestamp,
    item_id integer NOT NULL,
    user_id integer NOT NULL,
    CONSTRAINT fkkbkydvf8j8tfuego2iqxntwv2 FOREIGN KEY (item_id) REFERENCES public.items (product_id),
    CONSTRAINT fk8omq0tc18jd43bu5tjh6jvraq FOREIGN KEY (user_id) REFERENCES public.users (user_id)
);
INSERT INTO public.comments (comment_id, content, created_at, updated_at, item_id, user_id) VALUES (1, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus faucibus, tellus vitae euismod malesuada, risus est pretium leo, a pulvinar ex velit rutrum libero. Donec pharetra nec ante maximus vehicula.', '2018-10-01 02:55:21.378000', '2018-10-01 02:55:22.936000', 1, 2);
INSERT INTO public.comments (comment_id, content, created_at, updated_at, item_id, user_id) VALUES (9, 'Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...', '2018-10-04 12:32:42.111000', '2018-10-04 12:32:42.111000', 7, 2);
INSERT INTO public.comments (comment_id, content, created_at, updated_at, item_id, user_id) VALUES (11, 'Aenean eget vulputate metus, ut porta massa. Aenean a lacus quis leo sollicitudin posuere ut eget mauris. Etiam at sodales diam. Vestibulum sollicitudin purus arcu, vitae mattis risus eleifend eu.', '2018-10-04 12:53:14.091000', '2018-10-04 12:53:14.091000', 4, 2);
INSERT INTO public.comments (comment_id, content, created_at, updated_at, item_id, user_id) VALUES (13, 'Donec tincidunt porttitor laoreet. Sed sed commodo neque. Duis commodo ligula a velit tempor ultricies. Sed sed justo sed arcu rhoncus commodo. Suspendisse metus diam, faucibus sed placerat gravida, luctus quis erat. Nullam id odio et mi bibendum aliquet sit amet eget erat. Donec fringilla tempor nunc et aliquam. Maecenas sodales mollis tortor sed posuere. Vestibulum tempus mi a ipsum commodo malesuada. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Phasellus facilisis iaculis justo sit amet sagittis. Nunc aliquet risus non nulla vestibulum eleifend. Nullam eu magna sed dui mollis interdum. Sed velit purus, dictum sit amet augue nec, ornare blandit augue. Donec eget ante nulla. Morbi sed dui et quam semper rutrum.', '2018-10-04 13:03:37.606000', '2018-10-04 13:03:37.606000', 7, 3);
INSERT INTO public.comments (comment_id, content, created_at, updated_at, item_id, user_id) VALUES (16, 'Phasellus aliquet molestie aliquam. Nam finibus enim et urna ultrices bibendum. Donec a nulla sem. Curabitur sed nunc diam. Curabitur cursus tristique justo sed tincidunt.', '2018-10-08 08:54:17.890000', '2018-10-08 08:54:17.890000', 13, 6);
INSERT INTO public.comments (comment_id, content, created_at, updated_at, item_id, user_id) VALUES (17, 'Vivamus ornare accumsan diam, eget gravida odio tempus vitae. Nunc semper imperdiet porttitor. Cras at maximus sapien. Sed sit amet nibh id elit congue tempus sit amet vitae nulla.', '2018-10-08 08:55:52.466000', '2018-10-08 08:55:52.466000', 13, 3);
INSERT INTO public.comments (comment_id, content, created_at, updated_at, item_id, user_id) VALUES (18, 'Nulla facilisi. Ut eu nisl ac magna venenatis tristique a quis mi. Mauris ultrices, urna mollis efficitur commodo, tellus ante porta risus, a rhoncus mi elit vel dui. Suspendisse eleifend facilisis diam vel accumsan. Curabitur eu urna aliquet sem fringilla suscipit ac et elit. Nunc congue, ex at iaculis viverra, lectus nulla scelerisque neque, ac consequat dui odio quis dolor. Quisque id risus sed quam condimentum elementum consectetur nec ex.', '2018-10-20 16:10:32.511000', '2018-10-20 16:10:32.511000', 1, 7);
INSERT INTO public.comments (comment_id, content, created_at, updated_at, item_id, user_id) VALUES (19, 'Nulla sit amet augue id quam consequat pharetra. Aenean dictum scelerisque arcu, id porta mauris consectetur ut. Nulla rutrum, mi id venenatis convallis, turpis urna semper purus, vel viverra purus ipsum a magna. Maecenas sodales faucibus odio, et tempor lacus dignissim quis. Nulla eu dui metus. Donec dignissim fringilla maximus.', '2018-10-20 16:11:00.378000', '2018-10-20 16:11:00.378000', 2, 7);
INSERT INTO public.comments (comment_id, content, created_at, updated_at, item_id, user_id) VALUES (20, 'Nulla facilisi. Ut eu nisl ac magna venenatis tristique a quis mi. Mauris ultrices, urna mollis efficitur commodo, tellus ante porta risus, a rhoncus mi elit vel dui. Suspendisse eleifend facilisis diam vel accumsan.', '2018-11-14 15:11:25.493000', '2018-11-14 15:11:25.493000', 17, 3);
INSERT INTO public.comments (comment_id, content, created_at, updated_at, item_id, user_id) VALUES (21, 'Curabitur eu urna aliquet sem fringilla suscipit ac et elit. Nunc congue, ex at iaculis viverra, lectus nulla scelerisque neque, ac consequat dui odio quis dolor.', '2018-11-14 15:11:36.223000', '2018-11-14 15:11:36.223000', 17, 3);
INSERT INTO public.comments (comment_id, content, created_at, updated_at, item_id, user_id) VALUES (22, 'Curabitur eu urna aliquet sem fringilla suscipit ac et elit. Nunc congue, ex at iaculis viverra, lectus nulla scelerisque neque, ac consequat dui odio quis dolor.', '2018-11-14 15:11:48.773000', '2018-11-14 15:11:48.773000', 10, 3);

CREATE TABLE public.order_statuses
(
    status_id integer DEFAULT nextval('order_statuses_status_id_seq'::regclass) PRIMARY KEY NOT NULL,
    name varchar(255)
);
INSERT INTO public.order_statuses (status_id, name) VALUES (1, 'Pending');
INSERT INTO public.order_statuses (status_id, name) VALUES (2, 'Shipping');
INSERT INTO public.order_statuses (status_id, name) VALUES (3, 'Succeeded');
INSERT INTO public.order_statuses (status_id, name) VALUES (4, 'Failed');

CREATE TABLE public.orders
(
    order_id integer DEFAULT nextval('orders_order_id_seq'::regclass) PRIMARY KEY NOT NULL,
    address varchar(255),
    created_at timestamp,
    phone varchar(255),
    updated_at timestamp,
    user_id integer NOT NULL,
    status_id integer NOT NULL,
    total_items integer,
    total_price integer,
    CONSTRAINT fk32ql8ubntj5uh44ph9659tiih FOREIGN KEY (user_id) REFERENCES public.users (user_id),
    CONSTRAINT fkm5letvn141v0flinbgcc1t74q FOREIGN KEY (status_id) REFERENCES public.order_statuses (status_id)
);
INSERT INTO public.orders (order_id, address, created_at, phone, updated_at, user_id, status_id, total_items, total_price) VALUES (28, 'Phân xưởng Blohm & Voss', '2018-10-04 16:18:56.507000', '1936.007.0001', '2018-10-04 16:18:56.507000', 3, 1, 4, 330000);
INSERT INTO public.orders (order_id, address, created_at, phone, updated_at, user_id, status_id, total_items, total_price) VALUES (29, 'Tiểu thuyết Trung Quốc', '2018-10-04 17:01:05.810000', '1998.808.1080', '2018-10-04 17:01:58.351000', 6, 2, 5, 738000);
INSERT INTO public.orders (order_id, address, created_at, phone, updated_at, user_id, status_id, total_items, total_price) VALUES (27, 'Phân xưởng Blohm & Voss', '2018-10-04 16:18:43.206000', '1936.007.0001', '2018-10-04 17:02:07.029000', 3, 3, 5, 392000);
INSERT INTO public.orders (order_id, address, created_at, phone, updated_at, user_id, status_id, total_items, total_price) VALUES (30, 'Tiểu thuyết Trung Quốc', '2018-10-04 17:01:19.601000', '1998.808.1080', '2018-10-08 08:57:00.912000', 6, 4, 4, 455000);
INSERT INTO public.orders (order_id, address, created_at, phone, updated_at, user_id, status_id, total_items, total_price) VALUES (26, 'Phân xưởng đóng tàu Kure', '2018-10-03 16:39:06.946000', '1920.011.0150', '2018-10-08 08:57:13.203000', 2, 3, 4, 608000);
INSERT INTO public.orders (order_id, address, created_at, phone, updated_at, user_id, status_id, total_items, total_price) VALUES (32, 'Tiểu thuyết Trung Quốc', '2018-10-08 09:05:12.920000', '1998.808.1080', '2018-10-08 09:05:12.920000', 6, 1, 2, 160000);
INSERT INTO public.orders (order_id, address, created_at, phone, updated_at, user_id, status_id, total_items, total_price) VALUES (34, 'Khu phố đèn đỏ', '2018-10-22 14:40:38.699000', '1936.007.0001', '2018-10-22 14:40:38.699000', 3, 1, 9, 997000);
INSERT INTO public.orders (order_id, address, created_at, phone, updated_at, user_id, status_id, total_items, total_price) VALUES (31, 'Tiểu thuyết Trung Quốc', '2018-10-08 08:52:05.843000', '1998.808.1080', '2018-11-07 14:53:19.262000', 6, 3, 9, 726000);
INSERT INTO public.orders (order_id, address, created_at, phone, updated_at, user_id, status_id, total_items, total_price) VALUES (36, 'Tiểu thuyết Trung Quốc', '2018-11-14 15:15:35.950000', '1998.808.1080', '2018-11-14 15:15:35.950000', 6, 1, 3, 1180000);
INSERT INTO public.orders (order_id, address, created_at, phone, updated_at, user_id, status_id, total_items, total_price) VALUES (35, 'Tiểu thuyết Trung Quốc', '2018-11-14 15:02:44.099000', '1998.808.1080', '2018-11-14 15:24:38.736000', 6, 2, 8, 786000);
INSERT INTO public.orders (order_id, address, created_at, phone, updated_at, user_id, status_id, total_items, total_price) VALUES (33, 'Bán nước tương Bình Dương', '2018-10-20 16:09:01.095000', '1998.888.8080', '2018-11-14 15:24:47.270000', 7, 4, 8, 960000);
INSERT INTO public.orders (order_id, address, created_at, phone, updated_at, user_id, status_id, total_items, total_price) VALUES (21, 'Phân xưởng đóng tàu Kure', '2018-10-02 10:11:01.629000', '1920.011.0150', '2018-11-14 15:24:55.089000', 2, 4, 4, 608000);
INSERT INTO public.orders (order_id, address, created_at, phone, updated_at, user_id, status_id, total_items, total_price) VALUES (37, 'Bán nước tương Bình Dương', '2018-11-14 15:51:43.843000', '1998.888.8080', '2018-11-14 15:51:43.843000', 7, 1, 8, 986000);
INSERT INTO public.orders (order_id, address, created_at, phone, updated_at, user_id, status_id, total_items, total_price) VALUES (38, 'Bán nước tương Bình Dương', '2018-11-14 15:52:28.873000', '1998.888.8080', '2018-11-14 15:52:28.873000', 7, 1, 4, 260000);
INSERT INTO public.orders (order_id, address, created_at, phone, updated_at, user_id, status_id, total_items, total_price) VALUES (22, 'Phân xưởng đóng tàu Kure', '2018-10-02 13:28:43.145000', '1920.011.0150', '2018-10-03 16:39:39.767000', 2, 4, 4, 664000);
INSERT INTO public.orders (order_id, address, created_at, phone, updated_at, user_id, status_id, total_items, total_price) VALUES (25, 'Phân xưởng đóng tàu Kure', '2018-10-03 16:38:55.829000', '1920.011.0150', '2018-10-03 16:41:14.776000', 2, 3, 2, 323000);

CREATE TABLE public.order_details
(
    detail_id integer DEFAULT nextval('order_details_detail_id_seq'::regclass) PRIMARY KEY NOT NULL,
    amount integer,
    price integer,
    item_id integer NOT NULL,
    order_id integer NOT NULL,
    CONSTRAINT fknfrrgu0scdkwpptvs5gx6m6o9 FOREIGN KEY (item_id) REFERENCES public.items (product_id),
    CONSTRAINT fkjyu2qbqt8gnvno9oe9j2s2ldk FOREIGN KEY (order_id) REFERENCES public.orders (order_id)
);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (44, 4, 152000, 7, 21);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (45, 2, 152000, 4, 22);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (46, 2, 180000, 1, 22);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (51, 1, 170000, 6, 25);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (52, 1, 153000, 5, 25);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (53, 1, 152000, 7, 26);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (54, 3, 152000, 4, 26);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (55, 2, 76000, 12, 27);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (56, 1, 80000, 14, 27);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (57, 2, 80000, 13, 27);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (58, 2, 85000, 11, 28);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (59, 2, 80000, 14, 28);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (60, 3, 144000, 2, 29);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (61, 2, 153000, 5, 29);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (62, 2, 85000, 15, 30);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (63, 1, 114000, 8, 30);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (64, 1, 171000, 10, 30);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (65, 2, 80000, 13, 31);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (66, 4, 80000, 14, 31);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (67, 1, 76000, 12, 31);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (68, 2, 85000, 15, 31);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (69, 1, 80000, 13, 32);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (70, 1, 80000, 14, 32);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (71, 2, 80000, 13, 33);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (72, 2, 76000, 12, 33);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (73, 2, 144000, 2, 33);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (74, 2, 180000, 1, 33);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (75, 1, 85000, 11, 34);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (76, 8, 114000, 8, 34);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (77, 2, 153000, 15, 35);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (78, 5, 76000, 12, 35);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (79, 1, 100000, 16, 35);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (80, 1, 100000, 16, 36);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (81, 2, 540000, 17, 36);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (82, 2, 152000, 7, 37);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (83, 2, 85000, 11, 37);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (84, 2, 76000, 12, 37);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (85, 2, 180000, 10, 37);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (86, 2, 54000, 17, 38);
INSERT INTO public.order_details (detail_id, amount, price, item_id, order_id) VALUES (87, 2, 76000, 12, 38);

SELECT SETVAL('public.comments_comment_id_seq', COALESCE(MAX(comment_id), 1) ) FROM public.comments;
SELECT SETVAL('public.item_groups_group_id_seq', COALESCE(MAX(group_id), 1) ) FROM public.item_groups;
SELECT SETVAL('public.items_product_id_seq', COALESCE(MAX(product_id), 1) ) FROM public.items;
SELECT SETVAL('public.order_details_detail_id_seq', COALESCE(MAX(detail_id), 1) ) FROM public.order_details;
SELECT SETVAL('public.order_statuses_status_id_seq', COALESCE(MAX(status_id), 1) ) FROM public.order_statuses;
SELECT SETVAL('public.orders_order_id_seq', COALESCE(MAX(order_id), 1) ) FROM public.orders;
SELECT SETVAL('public.roles_role_id_seq', COALESCE(MAX(role_id), 1) ) FROM public.roles;
SELECT SETVAL('public.users_user_id_seq', COALESCE(MAX(user_id), 1) ) FROM public.users;
