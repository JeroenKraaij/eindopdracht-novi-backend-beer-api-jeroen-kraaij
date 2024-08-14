INSERT INTO category (id, beer_category_name, beer_category_type, beer_category_description) VALUES (1, 'Lager', 'Belgium White', 'Best Belgium beer');
INSERT INTO category (id, beer_category_name, beer_category_type, beer_category_description) VALUES (2, 'Ale', 'Belgium White', 'Best Belgium beer');
INSERT INTO category (id, beer_category_name, beer_category_type, beer_category_description) VALUES (3, 'Stout', 'Belgium White', 'Best Belgium beer');
INSERT INTO category (id, beer_category_name, beer_category_type, beer_category_description) VALUES (4, 'Pilsener', 'Belgium White', 'Best Belgium beer');

INSERT INTO beer (id, name, brand, description, color, brewery, country, abv, ibu, food, temperature, glassware, taste, price, image_url, category_id)
VALUES
    (1, 'Pilsner Urquell', 'Plzensky Prazdroj', 'A crisp, golden lager with a slightly sweet, malty flavor.', 'Golden', 'Pilsner Urquell Brewery', 'Czech Republic', 4.4, 40, 'Grilled Chicken, Cheese', '6-8°C', 'Pilsner Glass', 'Crisp, Malty, Bitter', 2.99, 'https://example.com/pilsner_urquell.jpg', 4),
    (2, 'Guinness Draught', 'Guinness', 'Rich and creamy with coffee and chocolate notes.', 'Dark', 'Guinness Brewery', 'Ireland', 4.2, 45, 'Stew, Shellfish', '6-8°C', 'Stout Glass', 'Creamy, Roasted, Bitter', 3.99, 'https://example.com/guinness_draught.jpg', 2),
    (3, 'Sierra Nevada Pale Ale', 'Sierra Nevada', 'A classic American Pale Ale with a bold hop flavor.', 'Amber', 'Sierra Nevada Brewing Co.', 'USA', 5.6, 38, 'Burgers, Spicy Food', '8-10°C', 'Pint Glass', 'Hoppy, Citrus, Pine', 4.49, 'https://example.com/sierra_nevada_pale_ale.jpg', 2),
    (4, 'Budweiser', 'Anheuser-Busch', 'A smooth and crisp lager with a clean finish.', 'Golden', 'Anheuser-Busch', 'USA', 5.0, 12, 'Pizza, Salad', '4-6°C', 'Pilsner Glass', 'Smooth, Light, Crisp', 1.99, 'https://example.com/budweiser.jpg', 1);

