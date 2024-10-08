-- Insert categories (Assuming you have a category table and the id is auto-generated)
INSERT INTO categories (name)
VALUES
    ('Lager'),
    ('Pale Ale'),
    ('Stout'),
    ('IPA'),
    ('Witbier'),
    ('Porter'),
    ('Saison'),
    ('Trappist'),
    ('Pilsner'),
    ('Amber Ale'),
    ('Brown Ale'),
    ('Blonde Ale');

-- Insert beers (Without specifying the id field)
INSERT INTO beers (name, brand, description, color, brewery, country, abv, ibu, food, temperature, glassware, taste, price, in_stock, category_id)
VALUES
    ('Pilsner Urquell', 'Plzensky Prazdroj', 'A crisp, golden lager with a slightly sweet, malty flavor.', 'Golden', 'Pilsner Urquell Brewery', 'Czech Republic', 4.4, 40, 'Grilled Chicken, Cheese', '6-8°C', 'Pilsner Glass', 'Crisp, Malty, Bitter', 2.99, 100, 9),
    ('Guinness Draught', 'Guinness', 'Rich and creamy with coffee and chocolate notes.', 'Dark', 'Guinness Brewery', 'Ireland', 4.2, 45, 'Stew, Shellfish', '6-8°C', 'Stout Glass', 'Creamy, Roasted, Bitter, Coffee, Chocolate', 3.99, 50, 3),
    ('Sierra Nevada Pale Ale', 'Sierra Nevada', 'A classic American Pale Ale with a bold hop flavor.', 'Amber', 'Sierra Nevada Brewing Co.', 'USA', 5.6, 38, 'Burgers, Spicy Food', '8-10°C', 'Pint Glass', 'Hoppy, Citrus, Pine', 4.49, 75, 2),
    ('Budweiser', 'Anheuser-Busch', 'A smooth and crisp lager with a clean finish.', 'Golden', 'Anheuser-Busch', 'USA', 5.0, 12, 'Pizza, Salad', '4-6°C', 'Pilsner Glass', 'Smooth, Light, Crisp', 1.99, 200, 9),
    ('Chimay Blue', 'Chimay', 'A strong, dark Belgian ale with rich fruit flavors.', 'Dark', 'Bières de Chimay', 'Belgium', 9.0, 35, 'Cheese, Stew', '10-12°C', 'Trappist Glass', 'Rich, Fruity, Malty', 5.99, 30, 8),
    ('Hoegaarden', 'Hoegaarden Brewery', 'A refreshing Belgian witbier with hints of orange peel and coriander.', 'Pale', 'Hoegaarden Brewery', 'Belgium', 4.9, 15, 'Seafood, Salad', '4-6°C', 'Witbier Glass', 'Citrusy, Spicy, Refreshing', 3.49, 150, 5),
    ('Heineken', 'Heineken', 'A well-known Dutch lager with a slightly bitter taste.', 'Pale', 'Heineken Brewery', 'Netherlands', 5.0, 19, 'Salads, Seafood', '5-7°C', 'Lager Glass', 'Light, Crisp, Bitter', 2.49, 180, 9),
    ('Samuel Adams Boston Lager', 'Boston Beer Company', 'A full-bodied amber lager with a rich, malty flavor.', 'Amber', 'Boston Beer Company', 'USA', 5.0, 30, 'Burgers, Chicken', '6-8°C', 'Pint Glass', 'Malty, Hoppy, Balanced', 4.49, 60, 10),
    ('Duvel', 'Duvel Moortgat', 'A strong, golden Belgian ale with a fruity aroma and dry finish.', 'Golden', 'Duvel Moortgat', 'Belgium', 8.5, 32, 'Fish, Spicy Food', '6-8°C', 'Tulip Glass', 'Fruity, Dry, Strong', 6.99, 45, 7),
    ('Rochefort 10', 'Rochefort Brewery', 'A dark, strong Belgian ale with complex flavors of dark fruit and caramel.', 'Dark Brown', 'Rochefort Brewery', 'Belgium', 11.3, 27, 'Cheese, Desserts', '12-14°C', 'Trappist Glass', 'Rich, Fruity, Sweet, Caramel', 7.99, 25, 8),
    ('Newcastle Brown Ale', 'Heineken', 'A smooth, malty brown ale with a hint of caramel.', 'Brown', 'Newcastle Brewery', 'England', 4.7, 18, 'Grilled Meats, Cheese', '8-10°C', 'Pint Glass', 'Malty, Caramel, Smooth', 3.29, 80, 11),
    ('Leffe Blonde', 'Abbaye de Leffe', 'A Belgian blonde ale with a slightly sweet and fruity flavor.', 'Golden', 'Abbaye de Leffe', 'Belgium', 6.6, 20, 'Fish, Poultry', '6-8°C', 'Goblet', 'Sweet, Fruity, Light', 4.99, 70, 12),
    ('Anchor Steam Beer', 'Anchor Brewing Company', 'A unique American beer with a rich, malty flavor and a hint of fruitiness.', 'Amber', 'Anchor Brewing Company', 'USA', 4.9, 35, 'Burgers, BBQ', '8-10°C', 'Pint Glass', 'Malty, Fruity, Balanced', 3.99, 100, 10),
    ('Stella Artois', 'Anheuser-Busch InBev', 'A crisp, slightly bitter Belgian lager.', 'Pale', 'Stella Artois Brewery', 'Belgium', 5.2, 25, 'Salads, Seafood', '3-5°C', 'Chalice', 'Crisp, Bitter, Refreshing', 2.79, 200, 9),
    ('Brooklyn Lager', 'Brooklyn Brewery', 'A hoppy amber lager with a floral aroma and a dry finish.', 'Amber', 'Brooklyn Brewery', 'USA', 5.2, 30, 'Pizza, Burgers', '6-8°C', 'Pint Glass', 'Hoppy, Floral, Dry', 4.29, 90, 10);

INSERT INTO image_uploads (beer_id, file_name, file_type, file_size, upload_time)
VALUES
    (1, 'pilsner_urquell.jpg', 'image/jpeg', 150000, '2023-01-01 12:00:00'),
    (2, 'guinness_draught.jpg', 'image/jpeg', 200000, '2023-01-02 14:00:00'),
    (3, 'sierra_nevada_pale_ale.jpg', 'image/jpeg', 180000, '2023-01-03 15:00:00'),
    (4, 'budweiser.jpg', 'image/jpeg', 170000, '2023-01-04 16:00:00'),
    (5, 'chimay_blue.jpg', 'image/jpeg', 200000, '2023-01-05 17:00:00');
