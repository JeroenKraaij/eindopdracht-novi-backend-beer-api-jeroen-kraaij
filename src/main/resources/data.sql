-- Insert users and authorities
INSERT INTO users(username, password, email, enabled, apikey)
VALUES
    ('AdminJeroen', '$2a$12$.jC/HjuwYi7z5zsB9kEnrexBnFkbuyXqRGDj2iUmQX1XS5CKcNJfK', 'jeroen.admin@test.nl', true, 'abc123'),
    ('UserJohn', '$2a$12$sl54ycJtVsSzKfvDs49piO6zFonIeqgaLnnyPhNimsKOLA7mD.jV2', 'john@test.nl', true, 'def456'),
    ('UserEmily', '$2a$12$OyDNiuGFD0lb7KYtbuEhqeeqixHn15A3LjKVFRhqDsuhBm7vbCa1W', 'emily@test.nl', true, 'jkl012'),
    ('EditorMichael', '$2a$12$nVVDIHXDFsSX5Pr.kwaebeD/ncEATCxqkPfPqaPWebhr3wRYd6KBO', 'michael@test.nl', true, 'mno345'),
    ('EditorOlivia', '$2a$12$g.NpGHFmoH3IuCrTenXyv.5cFNtGruRhJVZiXDDawWgXu4w4j/vaC', 'olivia@test.nl', true, 'pqr678');

INSERT INTO authorities(username, authority)
VALUES
    ('AdminJeroen', 'ROLE_ADMIN'),
    ('UserJohn', 'ROLE_USER'),
    ('UserEmily', 'ROLE_USER'),
    ('EditorMichael', 'ROLE_EDITOR'),
    ('EditorOlivia', 'ROLE_EDITOR');

-- Insert customers
INSERT INTO customers (firstname, surname, address, house_number, zipcode, city, phone, date_of_birth, user_customer)
VALUES
    ('John', 'Doe', '123 Maple Street', '10A', '12345', 'Springfield', '06-26754684', '1980-01-15', 'UserJohn'),
    ('Emily', 'Johnson', '789 Pine Road', '3C', '24680', 'Lakeside', '06-26754684', '1990-10-25', 'UserEmily'),
    ('Michael', 'Williams', '101 Elm Drive', '7D', '13579', 'Riverside', '06-26754684', '1975-03-30', 'EditorMichael'),
    ('Olivia', 'Brown', '202 Birch Boulevard', '2E', '97531', 'Meadowview', '06-26754684', '1988-08-10', 'EditorOlivia');

INSERT INTO taste (name)
VALUES
    ('Mango'), ('Koffie'), ('Vanille'), ('Noten'), ('Karamel'), ('Gember'), ('Banaan'), ('Citrus'), ('Chocola'), ('Kaneel'),
    ('Honing'), ('Peper'), ('Crisp'), ('Malty'), ('Bitter'), ('Creamy'), ('Roasted'), ('Hoppy'), ('Pine'), ('Light'),
    ('Smooth'), ('Rich'), ('Fruity'), ('Spicy'), ('Refreshing'), ('Balanced'), ('Dry'), ('Sweet'), ('Floral');

-- Insert categories for beers
INSERT INTO categories (beer_category_name, beer_category_type, beer_category_description)
VALUES
    ('Wheat Beer', 'Hefeweizen', 'Hefeweizen is a traditional wheat beer from Germany, known for its fruity and spicy flavor.'),
    ('Wheat Beer', 'Witbier', 'Witbier is a Belgian style wheat beer, often brewed with coriander and orange peel.'),
    ('Lambic & Sour Ale', 'Lambic', 'Lambic is a Belgian sour beer that is fermented spontaneously.'),
    ('Lambic & Sour Ale', 'Gueuze', 'Gueuze is a blend of young and old Lambics, resulting in a complex sour beer.'),
    ('Lambic & Sour Ale', 'Flanders Red Ale', 'Flanders Red Ale is a Belgian style sour ale, known for its deep red color and tart flavor.'),
    ('Belgian Ale', 'Belgian Dubbel', 'Belgian Dubbel is a rich, malty beer with dark fruit flavors.'),
    ('Belgian Ale', 'Belgian Tripel', 'Belgian Tripel is a strong, golden ale with a balance of spicy, fruity, and sweet flavors.'),
    ('Belgian Ale', 'Belgian Quadrupel', 'Belgian Quadrupel is a dark, strong ale with intense malty sweetness and flavors of caramel, dried fruits, and alcohol.'),
    ('Belgian Ale', 'Belgian Strong Dark Ale', 'Belgian Strong Dark Ale is a strong, complex beer with rich malty flavors and dark fruit notes.'),
    ('Belgian Ale', 'Belgian Blonde Ale', 'Belgian Blonde Ale is a smooth, easy-drinking beer with a mild malty sweetness.'),
    ('Pale Ale', 'American Pale Ale (APA)', 'American Pale Ale is a hoppy, yet balanced beer, often with citrus and pine notes.'),
    ('Pale Ale', 'English Pale Ale', 'English Pale Ale is a traditional British beer with a malty backbone and balanced bitterness.'),
    ('English Bitter', 'English Bitter', 'English Bitter is a low-alcohol, flavorful beer with a focus on malt and hop balance.'),
    ('Scottish Ale', 'Scottish Light', 'Scottish Light is a light-bodied ale with a malty sweetness and low bitterness.'),
    ('Scottish Ale', 'Scottish Heavy', 'Scottish Heavy is a medium-bodied ale with a rich malt character.'),
    ('Scottish Ale', 'Scottish Export', 'Scottish Export is a strong, malty beer with a full body and a hint of caramel.'),
    ('Brown Ale', 'American Brown Ale', 'American Brown Ale is a malty beer with a slightly hoppy edge.'),
    ('Brown Ale', 'English Brown Ale', 'English Brown Ale is a traditional British ale with a rich malt flavor and a smooth finish.'),
    ('Porter', 'Porter', 'Porter is a dark, robust beer with flavors of chocolate, coffee, and roasted malt.'),
    ('Stout', 'Dry Stout', 'Dry Stout is a dark, bitter beer with a dry finish and roasted flavors.'),
    ('Stout', 'Imperial Stout', 'Imperial Stout is a strong, rich beer with intense flavors of dark chocolate, coffee, and roasted malt.'),
    ('Stout', 'Milk Stout', 'Milk Stout is a creamy, sweet stout made with lactose, giving it a smooth mouthfeel.'),
    ('Stout', 'Oatmeal Stout', 'Oatmeal Stout is a smooth, full-bodied beer with a velvety texture from the use of oats.'),
    ('Pilsener', 'German Pilsner', 'German Pilsner is a crisp, refreshing beer with a strong hop character.'),
    ('Pilsener', 'Czech Pilsner', 'Czech Pilsner is a pale, golden beer with a slightly sweet, malty flavor and a crisp finish.'),
    ('American Lager', 'American Lager', 'American Lager is a light, crisp, and highly carbonated beer with a clean finish.'),
    ('European Lager', 'German Helles', 'German Helles is a pale, malty beer with a balanced bitterness.'),
    ('European Lager', 'Dortmunder Export', 'Dortmunder Export is a balanced, golden lager with a slight malty sweetness.'),
    ('Bock', 'Traditional Bock', 'Traditional Bock is a strong, malty beer with a rich, toasted flavor.'),
    ('Bock', 'Doppelbock', 'Doppelbock is a stronger version of Bock with more intense malt flavors.'),
    ('Bock', 'Eisbock', 'Eisbock is an even stronger, freeze-distilled version of Doppelbock.'),
    ('Bock', 'Maibock', 'Maibock is a lighter, more hoppy version of Bock, brewed for springtime.'),
    ('Altbier', 'Altbier', 'Altbier is a German-style beer with a balance of malt and hop bitterness, and a smooth finish.'),
    ('French Ale', 'Bière de Garde', 'Bière de Garde is a strong, malty French ale traditionally brewed in farmhouses.'),
    ('German Amber Ale', 'Märzen (Oktoberfest)', 'Märzen is a traditional German amber lager, known for its rich malt flavor, brewed for Oktoberfest.'),
    ('American Specialty', 'IPA', 'IPA (India Pale Ale) is a hoppy beer style known for its strong bitterness and bold flavors.'),
    ('American Specialty', 'Black IPA', 'Black IPA is a dark, hoppy beer with roasted malt flavors and a bitter finish.'),
    ('American Specialty', 'New England IPA', 'New England IPA is a hazy, juicy, and fruity version of IPA with a smooth mouthfeel.'),
    ('American Specialty', 'Experimental Beers', 'Experimental beers are unique and creative brews that often push the boundaries of traditional beer styles.'),
    ('Smoked Beer', 'Rauchbier', 'Rauchbier is a German smoked beer, known for its distinctive smoky flavor.'),
    ('Barleywine', 'American Barleywine', 'American Barleywine is a strong, hoppy beer with intense malt flavors and high alcohol content.');

-- Insert beers
INSERT INTO beers (name, brand, description, color, brewery, country, abv, ibu, food, temperature, glassware, taste, price, in_stock, category_id)
VALUES
    ('Pilsner Urquell', 'Plzensky Prazdroj', 'A crisp, golden lager with a slightly sweet, malty flavor.', 'Golden', 'Pilsner Urquell Brewery', 'Czech Republic', 4.4, 40, 'Grilled Chicken, Cheese', '6-8°C', 'Pilsner Glass', 'Crisp, Malty, Bitter', 2.99, 100, 25),
    ('Guinness Draught', 'Guinness', 'Rich and creamy with coffee and chocolate notes.', 'Dark', 'Guinness Brewery', 'Ireland', 4.2, 45, 'Stew, Shellfish', '6-8°C', 'Stout Glass', 'Creamy, Roasted, Bitter, Coffee, Chocolate', 3.99, 50, 20),
    ('Sierra Nevada Pale Ale', 'Sierra Nevada', 'A classic American Pale Ale with a bold hop flavor.', 'Amber', 'Sierra Nevada Brewing Co.', 'USA', 5.6, 38, 'Burgers, Spicy Food', '8-10°C', 'Pint Glass', 'Hoppy, Citrus, Pine', 4.49, 75, 11),
    ('Budweiser', 'Anheuser-Busch', 'A smooth and crisp lager with a clean finish.', 'Golden', 'Anheuser-Busch', 'USA', 5.0, 12, 'Pizza, Salad', '4-6°C', 'Pilsner Glass', 'Smooth, Light, Crisp', 1.99, 200, 26),
    ('Chimay Blue', 'Chimay', 'A strong, dark Belgian ale with rich fruit flavors.', 'Dark', 'Bières de Chimay', 'Belgium', 9.0, 35, 'Cheese, Stew', '10-12°C', 'Trappist Glass', 'Rich, Fruity, Malty', 5.99, 30, 8),
    ('Hoegaarden', 'Hoegaarden Brewery', 'A refreshing Belgian witbier with hints of orange peel and coriander.', 'Pale', 'Hoegaarden Brewery', 'Belgium', 4.9, 15, 'Seafood, Salad', '4-6°C', 'Witbier Glass', 'Citrusy, Spicy, Refreshing', 3.49, 150, 2),
    ('Heineken', 'Heineken', 'A well-known Dutch lager with a slightly bitter taste.', 'Pale', 'Heineken Brewery', 'Netherlands', 5.0, 19, 'Salads, Seafood', '5-7°C', 'Lager Glass', 'Light, Crisp, Bitter', 2.49, 180, 26),
    ('Samuel Adams Boston Lager', 'Boston Beer Company', 'A full-bodied amber lager with a rich, malty flavor.', 'Amber', 'Boston Beer Company', 'USA', 5.0, 30, 'Burgers, Chicken', '6-8°C', 'Pint Glass', 'Malty, Hoppy, Balanced', 4.49, 60, 10),
    ('Duvel', 'Duvel Moortgat', 'A strong, golden Belgian ale with a fruity aroma and dry finish.', 'Golden', 'Duvel Moortgat', 'Belgium', 8.5, 32, 'Fish, Spicy Food', '6-8°C', 'Tulip Glass', 'Fruity, Dry, Strong', 6.99, 45, 7),
    ('Rochefort 10', 'Rochefort Brewery', 'A dark, strong Belgian ale with complex flavors of dark fruit and caramel.', 'Dark Brown', 'Rochefort Brewery', 'Belgium', 11.3, 27, 'Cheese, Desserts', '12-14°C', 'Trappist Glass', 'Rich, Fruity, Sweet, Caramel', 7.99, 25, 9),
    ('Newcastle Brown Ale', 'Heineken', 'A smooth, malty brown ale with a hint of caramel.', 'Brown', 'Newcastle Brewery', 'England', 4.7, 18, 'Grilled Meats, Cheese', '8-10°C', 'Pint Glass', 'Malty, Caramel, Smooth', 3.29, 80, 18),
    ('Leffe Blonde', 'Abbaye de Leffe', 'A Belgian blonde ale with a slightly sweet and fruity flavor.', 'Golden', 'Abbaye de Leffe', 'Belgium', 6.6, 20, 'Fish, Poultry', '6-8°C', 'Goblet', 'Sweet, Fruity, Light', 4.99, 70, 10),
    ('Anchor Steam Beer', 'Anchor Brewing Company', 'A unique American beer with a rich, malty flavor and a hint of fruitiness.', 'Amber', 'Anchor Brewing Company', 'USA', 4.9, 35, 'Burgers, BBQ', '8-10°C', 'Pint Glass', 'Malty, Fruity, Balanced', 3.99, 100, 35),
    ('Stella Artois', 'Anheuser-Busch InBev', 'A crisp, slightly bitter Belgian lager.', 'Pale', 'Stella Artois Brewery', 'Belgium', 5.2, 25, 'Salads, Seafood', '3-5°C', 'Chalice', 'Crisp, Bitter, Refreshing', 2.79, 200, 26),
    ('Brooklyn Lager', 'Brooklyn Brewery', 'A hoppy amber lager with a floral aroma and a dry finish.', 'Amber', 'Brooklyn Brewery', 'USA', 5.2, 30, 'Pizza, Burgers', '6-8°C', 'Pint Glass', 'Hoppy, Floral, Dry', 4.29, 90, 17);

-- Insert orders
INSERT INTO orders (customer_id, order_date, order_status, payment_method, delivery_address)
VALUES
    (1, '2023-01-01', 'PENDING', 'CREDIT_CARD', '123 Maple Street, Springfield'),
    (2, '2023-01-02', 'PROCESSING', 'PAYPAL', '789 Pine Road, Hilltown'),
    (3, '2023-01-03', 'SHIPPED', 'CREDIT_CARD', '202 Birch Boulevard, Lakeside');

-- Insert order lines
INSERT INTO order_line (beer_id, order_id, quantity, price_at_purchase)
VALUES
    (1, 1, 2, 2.99),
    (2, 1, 1, 3.99),
    (3, 2, 3, 4.49),
    (4, 2, 1, 1.99),
    (5, 3, 2, 5.99),
    (6, 3, 1, 3.49);

-- Update the orders table with calculated total amounts
UPDATE orders o
SET total_amount_excluding_vat = (
    SELECT SUM(ol.price_at_purchase * ol.quantity)
    FROM order_line ol
    WHERE ol.order_id = o.id
),
    total_amount_including_vat = (
        SELECT SUM(ol.price_at_purchase * ol.quantity * 1.21)
        FROM order_line ol
        WHERE ol.order_id = o.id
    );