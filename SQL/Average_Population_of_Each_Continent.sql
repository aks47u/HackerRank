SELECT Continent, FLOOR(AVG(City.Population)) AvgPop
  FROM Country
  JOIN City
    ON Country.Code = City.CountryCode
    GROUP BY Continent;
