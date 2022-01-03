# Time Team Locations

Locations of Time Team digs displayed on an interactive map.

## Data source

All data is taken directly from the Wikipedia pages and inserted into an
Excel table. The data was cleaned up and themes added (Roman, Neolithic, etc).

A small Java app reads the Excel file and creates a JSON file. This file is picked
up by `index.html` and used to place markers on a map.

## Acknowlegements

Map JS Library: https://leafletjs.com/

Map tiles: https://www.mapbox.com/
