# Time Team Locations

Locations of Time Team digs displayed on an interactive map.

## Data source

All data is taken directly from the Wikipedia pages and inserted into an
Excel table. The data was cleaned up and themes added (Roman, Neolithic, etc).

A small Java app reads the Excel file and creates a JSON file. This file is picked
up by `index.html` and used to place markers on a map.

## YouTube links

I did try to fetch the links from the official (?) Time Team channel but they are not
well organised and it would have meant a lot of work to 
merge with the information I aquired from Wikipedia. As a result I used the links created
by `Reijer Zaaijer` as these all contained a SnnEnn code in the title.

I will by happy to change the links, if necessary.

I scraped the links from the various playlists and saved them in `ttc_n.html`. `ParseLinks`
is used to extract the links and titles from these files.

## Acknowlegements

Map JS Library: https://leafletjs.com/

Map tiles: https://www.mapbox.com/
