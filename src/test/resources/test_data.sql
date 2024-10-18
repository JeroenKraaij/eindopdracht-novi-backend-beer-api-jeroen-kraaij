
CREATE TABLE image_uploads (
                               id BIGINT PRIMARY KEY,
                               file_name VARCHAR(255),
                               image_data BLOB,
                               size BIGINT,
                               content_type VARCHAR(255),
                               upload_date TIMESTAMP,
                               alt_text VARCHAR(255),
                               width INT,
                               height INT,
                               hash VARCHAR(255),
                               is_featured BOOLEAN,
                               status VARCHAR(50),
                               file_extension VARCHAR(10),
                               beer_id BIGINT,
                               FOREIGN KEY (beer_id) REFERENCES beers(id)
);

-- Insert into categories
INSERT INTO categories (id, beer_category_name, beer_category_type, beer_category_description)
VALUES
    (1,'Wheat Beer', 'Hefeweizen', 'Hefeweizen is a traditional wheat beer from Germany, known for its fruity and spicy flavor.'),
    (2,'Wheat Beer', 'Witbier', 'Witbier is a Belgian style wheat beer, often brewed with coriander and orange peel.'),
    (3,'Lambic & Sour Ale', 'Lambic', 'Lambic is a Belgian sour beer that is fermented spontaneously.'),
    (4,'Lambic & Sour Ale', 'Gueuze', 'Gueuze is a blend of young and old Lambics, resulting in a complex sour beer.'),
    (5,'Lambic & Sour Ale', 'Flanders Red Ale', 'Flanders Red Ale is a Belgian style sour ale, known for its deep red color and tart flavor.'),
    (6,'B,elgian Ale', 'Belgian Dubbel', 'Belgian Dubbel is a rich, malty beer with dark fruit flavors.'),
    (7,'Belgian Ale', 'Belgian Tripel', 'Belgian Tripel is a strong, golden ale with a balance of spicy, fruity, and sweet flavors.'),
    (9,'Belgian Ale', 'Belgian Quadrupel', 'Belgian Quadrupel is a dark, strong ale with intense malty sweetness and flavors of caramel, dried fruits, and alcohol.'),
    (10,'Belgian Ale', 'Belgian Strong Dark Ale', 'Belgian Strong Dark Ale is a strong, complex beer with rich malty flavors and dark fruit notes.'),
    (11,'Belgian Ale', 'Belgian Blonde Ale', 'Belgian Blonde Ale is a smooth, easy-drinking beer with a mild malty sweetness.'),
    (12,'Pale Ale', 'American Pale Ale (APA)', 'American Pale Ale is a hoppy, yet balanced beer, often with citrus and pine notes.'),
    (13,'Pale Ale', 'English Pale Ale', 'English Pale Ale is a traditional British beer with a malty backbone and balanced bitterness.');

-- Insert into beers
INSERT INTO beers (id ,name, brand, description, color, brewery, country, abv, ibu, food, temperature, glassware, taste, price, in_stock, category_id)
VALUES
    (100,'Pilsner Urquell', 'Plzensky Prazdroj', 'A crisp, golden lager with a slightly sweet, malty flavor.', 'Golden', 'Pilsner Urquell Brewery', 'Czech Republic', 4.4, 40, 'Grilled Chicken, Cheese', '6-8°C', 'Pilsner Glass', 'Crisp, Malty, Bitter', 2.99, 100, 1),
    (300,'Guinness Draught', 'Guinness', 'Rich and creamy with coffee and chocolate notes.', 'Dark', 'Guinness Brewery', 'Ireland', 4.2, 45, 'Stew, Shellfish', '6-8°C', 'Stout Glass', 'Creamy, Roasted, Bitter, Coffee, Chocolate', 3.99, 50, 2),
    (200,'Sierra Nevada Pale Ale', 'Sierra Nevada', 'A classic American Pale Ale with a bold hop flavor.', 'Amber', 'Sierra Nevada Brewing Co.', 'USA', 5.6, 38, 'Burgers, Spicy Food', '8-10°C', 'Pint Glass', 'Hoppy, Citrus, Pine', 4.49, 75, 3),
    (400,'Budweiser', 'Anheuser-Busch', 'A smooth and crisp lager with a clean finish.', 'Golden', 'Anheuser-Busch', 'USA', 5.0, 12, 'Pizza, Salad', '4-6°C', 'Pilsner Glass', 'Smooth, Light, Crisp', 1.99, 200, 4),
    (500,'Chimay Blue', 'Chimay', 'A strong, dark Belgian ale with rich fruit flavors.', 'Dark', 'Bières de Chimay', 'Belgium', 9.0, 35, 'Cheese, Stew', '10-12°C', 'Trappist Glass', 'Rich, Fruity, Malty', 5.99, 30, 5);


-- Insert data into image_uploads
INSERT INTO image_uploads (id, file_name, image_data, size, content_type, upload_date, alt_text, width, height, hash, is_featured, status, file_extension, beer_id)
VALUES
    (1, 'pilsner_urquell.png', NULL, 204800, 'image/png', NOW(), 'Pilsner Urquell Image', 800, 600, 'hash1', TRUE, 'ACTIVE', '.png', 100),
    (2, 'guinness_draught.jpg', NULL, 204800, 'image/jpeg', NOW(), 'Guinness Draught Image', 800, 600, 'hash2', TRUE, 'ACTIVE', '.jpg', 300),
    (3, 'sierra_nevada_pale_ale.jpg', NULL, 204800, 'image/jpeg', NOW(), 'Sierra Nevada Pale Ale Image', 800, 600, 'hash3', FALSE, 'ACTIVE', '.jpg', 200),
    (4, 'budweiser.png', NULL, 204800, 'image/png', NOW(), 'Budweiser Image', 800, 600, 'hash4', FALSE, 'ACTIVE', '.png', 400),
    (5, 'chimay_blue.png', NULL, 204800, 'image/png', NOW(), 'Chimay Blue Image', 800, 600, 'hash5', TRUE, 'ACTIVE', '.png', 500);