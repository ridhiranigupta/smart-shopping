-- TAGGY Sample Data
USE taggy_db;

-- Clear existing data
DELETE FROM cart;
DELETE FROM orders;
DELETE FROM wallet;
DELETE FROM users;
DELETE FROM products;

-- Reset auto increment
ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE products AUTO_INCREMENT = 1;
ALTER TABLE wallet AUTO_INCREMENT = 1;

-- Insert Products with Real-World Data

-- ELECTRONICS CATEGORY
INSERT INTO products (name, category, amazon_price, flipkart_price, myntra_price, image_url) VALUES
('Apple iPhone 15 Pro Max 256GB', 'Electronics', 159900.00, 159900.00, NULL, 'assets/iphone15promax.png'),
('Samsung Galaxy S24 Ultra 512GB', 'Electronics', 129999.00, 127999.00, NULL, 'assets/s24ultra.png'),
('OnePlus 12 5G 256GB', 'Electronics', 64999.00, 63999.00, NULL, 'assets/oneplus12.png'),
('Google Pixel 8 Pro 256GB', 'Electronics', 106999.00, 105999.00, NULL, 'assets/pixel8.png'),
('Xiaomi 14 Pro 512GB', 'Electronics', 74999.00, 73999.00, NULL, 'assets/xiaomi14.png'),

-- LAPTOPS
('MacBook Air M2 15-inch', 'Electronics', 134900.00, NULL, NULL, 'assets/macbookair.png'),
('Dell XPS 15 9530 i7 16GB', 'Electronics', 189990.00, 187990.00, NULL, 'assets/dellxps15.png'),
('HP Spectre x360 14-inch', 'Electronics', 159990.00, 157990.00, NULL, 'assets/spectreх360.png'),
('Lenovo ThinkPad X1 Carbon Gen 11', 'Electronics', 174990.00, 172990.00, NULL, 'assets/thinkpad.png'),
('ASUS ROG Zephyrus G14 2024', 'Electronics', 164990.00, 162990.00, NULL, 'assets/rogg14.png'),
('Microsoft Surface Laptop 5', 'Electronics', 119990.00, NULL, NULL, 'assets/surface.png'),

-- TABLETS & WEARABLES
('iPad Pro 12.9 M2 256GB', 'Electronics', 112900.00, 111900.00, NULL, 'assets/ipadpro.png'),
('Samsung Galaxy Tab S9 Ultra', 'Electronics', 109999.00, 107999.00, NULL, 'assets/tabs9.png'),
('Apple Watch Series 9 GPS 45mm', 'Electronics', 46900.00, 45900.00, NULL, 'assets/watch9.png'),
('Samsung Galaxy Watch 6 Classic', 'Electronics', 39999.00, 38999.00, NULL, 'assets/galaxywatch6.png'),

-- AUDIO
('Sony WH-1000XM5 Headphones', 'Electronics', 29990.00, 28990.00, NULL, 'assets/sony1000xm5.png'),
('Bose QuietComfort Ultra', 'Electronics', 34990.00, 33990.00, NULL, 'assets/boseqc.png'),
('AirPods Pro 2nd Gen USB-C', 'Electronics', 24900.00, 24500.00, NULL, 'assets/airpodspro2.png'),
('JBL Flip 6 Portable Speaker', 'Electronics', 12999.00, 11999.00, NULL, 'assets/jblflip6.png'),
('Marshall Emberton II Speaker', 'Electronics', 16999.00, 15999.00, NULL, 'assets/marshall.png'),
('Boat Rockerz 450 Pro', 'Electronics', 1799.00, 1599.00, NULL, 'assets/boatrockerz.png'),

-- CAMERAS & ACCESSORIES
('Canon EOS R6 Mark II Body', 'Electronics', 239995.00, 237995.00, NULL, 'assets/canonr6.png'),
('Sony Alpha A7 IV Full Frame', 'Electronics', 219990.00, 217990.00, NULL, 'assets/sonya7iv.png'),
('GoPro Hero 12 Black', 'Electronics', 44990.00, 43990.00, NULL, 'assets/gopro12.png'),
('DJI Mini 4 Pro Drone', 'Electronics', 89900.00, 87900.00, NULL, 'assets/djimini4.png'),

-- FASHION - MEN'S CLOTHING
('Levi''s 511 Slim Fit Jeans', 'Fashion', 3999.00, 3799.00, 3899.00, 'assets/levis511.png'),
('Tommy Hilfiger Classic Polo', 'Fashion', 3499.00, 3299.00, 3399.00, 'assets/tommypolo.png'),
('Zara Premium Cotton Shirt', 'Fashion', 2999.00, NULL, 2899.00, 'assets/zarashirt.png'),
('H&M Slim Fit Chinos', 'Fashion', 1999.00, NULL, 1899.00, 'assets/hmchinos.png'),
('Allen Solly Formal Blazer', 'Fashion', 7999.00, 7599.00, 7799.00, 'assets/allensolly.png'),
('Van Heusen Dress Shirt', 'Fashion', 2499.00, 2299.00, 2399.00, 'assets/vanheusen.png'),
('Peter England Trousers', 'Fashion', 1899.00, 1699.00, 1799.00, 'assets/peterengland.png'),

-- FASHION - WOMEN'S CLOTHING
('Forever 21 Floral Dress', 'Fashion', 2499.00, NULL, 2299.00, 'assets/forever21.png'),
('Mango Denim Jacket', 'Fashion', 3999.00, NULL, 3799.00, 'assets/mangojacket.png'),
('Vero Moda Casual Top', 'Fashion', 1499.00, NULL, 1399.00, 'assets/veromoda.png'),
('Only Women Skinny Jeans', 'Fashion', 2299.00, NULL, 2199.00, 'assets/onlyjeans.png'),
('FabIndia Ethnic Kurta Set', 'Fashion', 3499.00, 3299.00, 3399.00, 'assets/fabindia.png'),
('W for Woman Palazzo Pants', 'Fashion', 1999.00, 1799.00, 1899.00, 'assets/wforwoman.png'),

-- FASHION - FOOTWEAR
('Nike Air Max 270', 'Fashion', 12995.00, 12495.00, 12795.00, 'assets/nikeairmax270.png'),
('Adidas Ultraboost 23', 'Fashion', 16999.00, 16499.00, 16799.00, 'assets/ultraboost23.png'),
('Puma Suede Classic XXI', 'Fashion', 5999.00, 5799.00, 5899.00, 'assets/pumasuede.png'),
('Converse Chuck Taylor All Star', 'Fashion', 4499.00, 4299.00, 4399.00, 'assets/converse.png'),
('Skechers Go Walk 6', 'Fashion', 5499.00, 5299.00, 5399.00, 'assets/skechers.png'),
('Woodland Leather Boots', 'Fashion', 6999.00, 6799.00, 6899.00, 'assets/woodland.png'),
('Bata Red Label Formal Shoes', 'Fashion', 3499.00, 3299.00, 3399.00, 'assets/bata.png'),
('Crocs Classic Clog', 'Fashion', 2999.00, 2799.00, 2899.00, 'assets/crocs.png'),

-- FASHION - ACCESSORIES
('Ray-Ban Aviator Sunglasses', 'Fashion', 7999.00, 7799.00, 7899.00, 'assets/rayban.png'),
('Fossil Gen 6 Smartwatch', 'Fashion', 21995.00, 20995.00, 21495.00, 'assets/fossil.png'),
('Michael Kors Leather Wallet', 'Fashion', 6999.00, NULL, 6799.00, 'assets/mkwallet.png'),
('Tommy Hilfiger Backpack', 'Fashion', 5499.00, 5299.00, 5399.00, 'assets/tommybackpack.png'),
('Titan Analog Watch Men', 'Fashion', 4995.00, 4795.00, 4895.00, 'assets/titan.png'),
('Wildcraft Travel Duffel 65L', 'Fashion', 3499.00, 3299.00, 3399.00, 'assets/wildcraft.png'),

-- HOME & LIVING
('Philips Air Fryer XXL 7.3L', 'Home', 18995.00, 17995.00, NULL, 'assets/philipsairfryer.png'),
('Dyson V15 Detect Vacuum', 'Home', 59900.00, NULL, NULL, 'assets/dysonv15.png'),
('Prestige Induction Cooktop', 'Home', 3499.00, 3299.00, NULL, 'assets/prestige.png'),
('Amazon Echo Dot 5th Gen', 'Home', 5499.00, NULL, NULL, 'assets/echodot.png'),
('Mi Smart LED Bulb 12W', 'Home', 799.00, 699.00, NULL, 'assets/mibulb.png'),
('Urban Ladder Sofa 3 Seater', 'Home', 34999.00, 33999.00, NULL, 'assets/urbanladder.png'),
('IKEA Study Table Desk', 'Home', 8999.00, NULL, NULL, 'assets/ikea.png'),
('Sleepwell Mattress Queen Size', 'Home', 28999.00, 27999.00, NULL, 'assets/sleepwell.png'),

-- SPORTS & FITNESS
('Decathlon Cycle Rockrider', 'Sports', 24999.00, 23999.00, NULL, 'assets/rockrider.png'),
('Nivia Dominator Football', 'Sports', 1299.00, 1199.00, NULL, 'assets/nivia.png'),
('Yonex Badminton Racket', 'Sports', 4999.00, 4799.00, NULL, 'assets/yonex.png'),
('Cosco Cricket Bat Kashmir Willow', 'Sports', 3499.00, 3299.00, NULL, 'assets/cosco.png'),
('Adidas Gym Bag Medium', 'Sports', 1999.00, 1799.00, 1899.00, 'assets/adidasbag.png'),
('Nike Dri-FIT Training T-Shirt', 'Sports', 1795.00, 1695.00, 1745.00, 'assets/nikedrifit.png'),
('Reebok Yoga Mat 6mm', 'Sports', 1299.00, 1199.00, 1249.00, 'assets/reebokyoga.png'),
('Fitbit Charge 6 Fitness Tracker', 'Sports', 14999.00, 13999.00, NULL, 'assets/fitbit.png'),

-- BOOKS & MEDIA
('Atomic Habits - James Clear', 'Books', 599.00, 549.00, NULL, 'assets/atomichabits.png'),
('Think Like a Monk - Jay Shetty', 'Books', 399.00, 349.00, NULL, 'assets/thinklikemonk.png'),
('The Psychology of Money', 'Books', 449.00, 399.00, NULL, 'assets/psychologymoney.png'),
('Rich Dad Poor Dad', 'Books', 349.00, 299.00, NULL, 'assets/richdad.png'),
('Ikigai Japanese Secret', 'Books', 299.00, 249.00, NULL, 'assets/ikigai.png');

-- Insert User
INSERT INTO users (username, email, password) VALUES
('ridhi', 'ridhi@email.com', 'ridhi123');

-- Insert Wallet for User
INSERT INTO wallet (user_id, balance) VALUES
(1, 75000.00);

SELECT 'Sample data inserted successfully!' as Status;
SELECT COUNT(*) as 'Total Products' FROM products;
SELECT COUNT(*) as 'Total Users' FROM users;
SELECT DISTINCT category as 'Categories' FROM products ORDER BY category;
