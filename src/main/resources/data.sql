
INSERT INTO category (id, beer_category_name, beer_category_type, beer_category_description)

VALUES
    (1, 'Wheat Beer', 'Hefeweizen', 'Hefeweizen is a traditional wheat beer from Germany, known for its fruity and spicy flavor.'),
    (2, 'Wheat Beer', 'Witbier', 'Witbier is a Belgian style wheat beer, often brewed with coriander and orange peel.'),
    (3, 'Lambic & Sour Ale', 'Lambic', 'Lambic is a Belgian sour beer that is fermented spontaneously.'),
    (4, 'Lambic & Sour Ale', 'Gueuze', 'Gueuze is a blend of young and old Lambics, resulting in a complex sour beer.'),
    (5, 'Lambic & Sour Ale', 'Flanders Red Ale', 'Flanders Red Ale is a Belgian style sour ale, known for its deep red color and tart flavor.'),
    (6, 'Belgian Ale', 'Belgian Dubbel', 'Belgian Dubbel is a rich, malty beer with dark fruit flavors.'),
    (7, 'Belgian Ale', 'Belgian Tripel', 'Belgian Tripel is a strong, golden ale with a balance of spicy, fruity, and sweet flavors.'),
    (8, 'Belgian Ale', 'Belgian Quadrupel', 'Belgian Quadrupel is a dark, strong ale with intense malty sweetness and flavors of caramel, dried fruits, and alcohol.'),
    (9, 'Belgian Ale', 'Belgian Strong Dark Ale', 'Belgian Strong Dark Ale is a strong, complex beer with rich malty flavors and dark fruit notes.'),
    (10, 'Belgian Ale', 'Belgian Blonde Ale', 'Belgian Blonde Ale is a smooth, easy-drinking beer with a mild malty sweetness.'),
    (11, 'Pale Ale', 'American Pale Ale (APA)', 'American Pale Ale is a hoppy, yet balanced beer, often with citrus and pine notes.'),
    (12, 'Pale Ale', 'English Pale Ale', 'English Pale Ale is a traditional British beer with a malty backbone and balanced bitterness.'),
    (13, 'English Bitter', 'English Bitter', 'English Bitter is a low-alcohol, flavorful beer with a focus on malt and hop balance.'),
    (14, 'Scottish Ale', 'Scottish Light', 'Scottish Light is a light-bodied ale with a malty sweetness and low bitterness.'),
    (15, 'Scottish Ale', 'Scottish Heavy', 'Scottish Heavy is a medium-bodied ale with a rich malt character.'),
    (16, 'Scottish Ale', 'Scottish Export', 'Scottish Export is a strong, malty beer with a full body and a hint of caramel.'),
    (17, 'Brown Ale', 'American Brown Ale', 'American Brown Ale is a malty beer with a slightly hoppy edge.'),
    (18, 'Brown Ale', 'English Brown Ale', 'English Brown Ale is a traditional British ale with a rich malt flavor and a smooth finish.'),
    (19, 'Porter', 'Porter', 'Porter is a dark, robust beer with flavors of chocolate, coffee, and roasted malt.'),
    (20, 'Stout', 'Dry Stout', 'Dry Stout is a dark, bitter beer with a dry finish and roasted flavors.'),
    (21, 'Stout', 'Imperial Stout', 'Imperial Stout is a strong, rich beer with intense flavors of dark chocolate, coffee, and roasted malt.'),
    (22, 'Stout', 'Milk Stout', 'Milk Stout is a creamy, sweet stout made with lactose, giving it a smooth mouthfeel.'),
    (23, 'Stout', 'Oatmeal Stout', 'Oatmeal Stout is a smooth, full-bodied beer with a velvety texture from the use of oats.'),
    (24, 'Pilsner', 'German Pilsner', 'German Pilsner is a crisp, refreshing beer with a strong hop character.'),
    (25, 'Pilsner', 'Czech Pilsner', 'Czech Pilsner is a pale, golden beer with a slightly sweet, malty flavor and a crisp finish.'),
    (26, 'American Lager', 'American Lager', 'American Lager is a light, crisp, and highly carbonated beer with a clean finish.'),
    (27, 'European Lager', 'German Helles', 'German Helles is a pale, malty beer with a balanced bitterness.'),
    (28, 'European Lager', 'Dortmunder Export', 'Dortmunder Export is a balanced, golden lager with a slight malty sweetness.'),
    (29, 'Bock', 'Traditional Bock', 'Traditional Bock is a strong, malty beer with a rich, toasted flavor.'),
    (30, 'Bock', 'Doppelbock', 'Doppelbock is a stronger version of Bock with more intense malt flavors.'),
    (31, 'Bock', 'Eisbock', 'Eisbock is an even stronger, freeze-distilled version of Doppelbock.'),
    (32, 'Bock', 'Maibock', 'Maibock is a lighter, more hoppy version of Bock, brewed for springtime.'),
    (33, 'Altbier', 'Altbier', 'Altbier is a German-style beer with a balance of malt and hop bitterness, and a smooth finish.'),
    (34, 'French Ale', 'Bière de Garde', 'Bière de Garde is a strong, malty French ale traditionally brewed in farmhouses.'),
    (35, 'German Amber Ale', 'Märzen (Oktoberfest)', 'Märzen is a traditional German amber lager, known for its rich malt flavor, brewed for Oktoberfest.'),
    (36, 'American Specialty', 'IPA', 'IPA (India Pale Ale) is a hoppy beer style known for its strong bitterness and bold flavors.'),
    (37, 'American Specialty', 'Black IPA', 'Black IPA is a dark, hoppy beer with roasted malt flavors and a bitter finish.'),
    (38, 'American Specialty', 'New England IPA', 'New England IPA is a hazy, juicy, and fruity version of IPA with a smooth mouthfeel.'),
    (39, 'American Specialty', 'Experimental Beers', 'Experimental beers are unique and creative brews that often push the boundaries of traditional beer styles.'),
    (40, 'Smoked Beer', 'Rauchbier', 'Rauchbier is a German smoked beer, known for its distinctive smoky flavor.'),
    (41, 'Barleywine', 'American Barleywine', 'American Barleywine is a strong, hoppy beer with intense malt flavors and high alcohol content.'),
    (42, 'Barleywine', 'English Barleywine', 'English Barleywine is a rich, malty beer with sweet, fruity, and caramel flavors.'),
    (43, 'Strong Ale', 'Old Ale', 'Old Ale is a strong, malty beer, often aged to develop complex flavors.'),
    (44, 'Strong Ale', 'American Strong Ale', 'American Strong Ale is a bold, high-alcohol beer with strong malt and hop flavors.');



INSERT INTO beer (id, name, brand, description, color, brewery, country, abv, ibu, food, temperature, glassware, taste, price, image_url, category_id)
VALUES
    (1, 'Pilsner Urquell', 'Plzensky Prazdroj', 'A crisp, golden lager with a slightly sweet, malty flavor.', 'Golden', 'Pilsner Urquell Brewery', 'Czech Republic', 4.4, 40, 'Grilled Chicken, Cheese', '6-8°C', 'Pilsner Glass', 'Crisp, Malty, Bitter', 2.99, 'https://example.com/pilsner_urquell.jpg', 25),
    (2, 'Guinness Draught', 'Guinness', 'Rich and creamy with coffee and chocolate notes.', 'Dark', 'Guinness Brewery', 'Ireland', 4.2, 45, 'Stew, Shellfish', '6-8°C', 'Stout Glass', 'Creamy, Roasted, Bitter', 3.99, 'https://example.com/guinness_draught.jpg', 20),
    (3, 'Sierra Nevada Pale Ale', 'Sierra Nevada', 'A classic American Pale Ale with a bold hop flavor.', 'Amber', 'Sierra Nevada Brewing Co.', 'USA', 5.6, 38, 'Burgers, Spicy Food', '8-10°C', 'Pint Glass', 'Hoppy, Citrus, Pine', 4.49, 'https://example.com/sierra_nevada_pale_ale.jpg', 11),
    (4, 'Budweiser', 'Anheuser-Busch', 'A smooth and crisp lager with a clean finish.', 'Golden', 'Anheuser-Busch', 'USA', 5.0, 12, 'Pizza, Salad', '4-6°C', 'Pilsner Glass', 'Smooth, Light, Crisp', 1.99, 'https://example.com/budweiser.jpg', 26),
    (5, 'Chimay Blue', 'Chimay', 'A strong, dark Belgian ale with rich fruit flavors.', 'Dark', 'Bières de Chimay', 'Belgium', 9.0, 35, 'Cheese, Stew', '10-12°C', 'Trappist Glass', 'Rich, Fruity, Malty', 5.99, 'https://example.com/chimay_blue.jpg', 8),
    (6, 'Hoegaarden', 'Hoegaarden Brewery', 'A refreshing Belgian witbier with hints of orange peel and coriander.', 'Pale', 'Hoegaarden Brewery', 'Belgium', 4.9, 15, 'Seafood, Salad', '4-6°C', 'Witbier Glass', 'Citrusy, Spicy, Refreshing', 3.49, 'https://example.com/hoegaarden.jpg', 2),
    (7, 'Heineken', 'Heineken', 'A well-known Dutch lager with a slightly bitter taste.', 'Pale', 'Heineken Brewery', 'Netherlands', 5.0, 19, 'Salads, Seafood', '5-7°C', 'Lager Glass', 'Light, Crisp, Bitter', 2.49, 'https://example.com/heineken.jpg', 26),
    (8, 'Samuel Adams Boston Lager', 'Boston Beer Company', 'A full-bodied amber lager with a rich, malty flavor.', 'Amber', 'Boston Beer Company', 'USA', 5.0, 30, 'Burgers, Chicken', '6-8°C', 'Pint Glass', 'Malty, Hoppy, Balanced', 4.49, 'https://example.com/samuel_adams_boston_lager.jpg', 10),
    (9, 'Duvel', 'Duvel Moortgat', 'A strong, golden Belgian ale with a fruity aroma and dry finish.', 'Golden', 'Duvel Moortgat', 'Belgium', 8.5, 32, 'Fish, Spicy Food', '6-8°C', 'Tulip Glass', 'Fruity, Dry, Strong', 6.99, 'https://example.com/duvel.jpg', 7),
    (10, 'Rochefort 10', 'Rochefort Brewery', 'A dark, strong Belgian ale with complex flavors of dark fruit and caramel.', 'Dark Brown', 'Rochefort Brewery', 'Belgium', 11.3, 27, 'Cheese, Desserts', '12-14°C', 'Trappist Glass', 'Rich, Fruity, Sweet', 7.99, 'https://example.com/rochefort_10.jpg', 9),
    (11, 'Newcastle Brown Ale', 'Heineken', 'A smooth, malty brown ale with a hint of caramel.', 'Brown', 'Newcastle Brewery', 'England', 4.7, 18, 'Grilled Meats, Cheese', '8-10°C', 'Pint Glass', 'Malty, Caramel, Smooth', 3.29, 'https://example.com/newcastle_brown_ale.jpg', 18),
    (12, 'Leffe Blonde', 'Abbaye de Leffe', 'A Belgian blonde ale with a slightly sweet and fruity flavor.', 'Golden', 'Abbaye de Leffe', 'Belgium', 6.6, 20, 'Fish, Poultry', '6-8°C', 'Goblet', 'Sweet, Fruity, Light', 4.99, 'https://example.com/leffe_blonde.jpg', 10),
    (13, 'Anchor Steam Beer', 'Anchor Brewing Company', 'A unique American beer with a rich, malty flavor and a hint of fruitiness.', 'Amber', 'Anchor Brewing Company', 'USA', 4.9, 35, 'Burgers, BBQ', '8-10°C', 'Pint Glass', 'Malty, Fruity, Balanced', 3.99, 'https://example.com/anchor_steam.jpg', 35),
    (14, 'Stella Artois', 'Anheuser-Busch InBev', 'A crisp, slightly bitter Belgian lager.', 'Pale', 'Stella Artois Brewery', 'Belgium', 5.2, 25, 'Salads, Seafood', '3-5°C', 'Chalice', 'Crisp, Bitter, Refreshing', 2.79, 'https://example.com/stella_artois.jpg', 26),
    (15, 'Brooklyn Lager', 'Brooklyn Brewery', 'A hoppy amber lager with a floral aroma and a dry finish.', 'Amber', 'Brooklyn Brewery', 'USA', 5.2, 30, 'Pizza, Burgers', '6-8°C', 'Pint Glass', 'Hoppy, Floral, Dry', 4.29, 'https://example.com/brooklyn_lager.jpg', 17);



INSERT INTO customers (firstname, surname, address, house_number, zipcode, city, email, phone, date_of_birth)
VALUES
    ('John', 'Doe', '123 Maple Street', '10A', '12345', 'Springfield', 'john.doe@example.com', '555-1234', '1980-01-15'),
    ('Jane', 'Smith', '456 Oak Avenue', '22B', '67890', 'Greenville', 'jane.smith@example.com', '555-5678', '1985-02-20'),
    ('Emily', 'Johnson', '789 Pine Road', '3C', '54321', 'Hilltown', 'emily.johnson@example.com', '555-9101', '1990-03-25'),
    ('Michael', 'Brown', '101 Cedar Lane', '4D', '98765', 'Rivercity', 'michael.brown@example.com', '555-1122', '1975-04-30'),
    ('Olivia', 'Davis', '202 Birch Boulevard', '5E', '87654', 'Lakeside', 'olivia.davis@example.com', '555-1314', '1992-05-10');

