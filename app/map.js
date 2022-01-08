var map = null;
var themes = null;

initThemes();

initMap();

initActions();

function initActions() {
	$('.filtertext').on('keyup', function() {
		updateMarkers();
	});
}

function initThemes() {
	themes = [];
	data.forEach(function(item) {
		item.themes.forEach(function(theme) {
			themes.push(theme);
		});
	});

	themes = themes.sort().filter(function(item, pos, ary) {
		return !pos || item != ary[pos - 1];
	});

	let sel = $('select#theme-select');
	themes.forEach(function(theme) {
		let opt = $('<option></option>');
		$(sel).append(opt);
		$(opt).text(theme);
	});

	$(sel).on('change', function() {
		themes = $('select#theme-select').val();
		updateMarkers();
	});
	
	$('#only-video').on('change', function() {
		updateMarkers();
	});
}

function initMap() {
	map = L.map('map').setView([54, -1.3], 6);

	L.tileLayer('https://api.mapbox.com/styles/v1/{id}/tiles/{z}/{x}/{y}?access_token={accessToken}', {
		attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors, Imagery © <a href="https://www.mapbox.com/">Mapbox</a>',
		maxZoom: 18,
		id: 'mapbox/streets-v11',
		tileSize: 512,
		zoomOffset: -1,
		accessToken: 'pk.eyJ1IjoibmVkc21hcnAiLCJhIjoiY2t4ejFlejRxNDJjbzJva282eGJiYnp2YSJ9.x25niOmRem0st6gVS6f_ow'
	}).addTo(map);

	updateMarkers();
}

function updateMarkers() {
	let matches = 0;
	let episode = $('#episode').val();
	let descr = $('#description').val();
	let youtube = $('#only-video').prop('checked');

	map.eachLayer((layer) => {
		if (layer['_latlng'] != undefined)
			layer.remove();
	});

	data.forEach(function(item, i) {
		let textMatch = false;
		if (episode == '' && descr == '') {
			textMatch = true;
		} else {
			if (episode.trim().length > 0)
				textMatch = item.code.toLowerCase().includes(episode.toLowerCase());

			if (descr.trim().length > 0) {
				try {
					textMatch |= item.title.toLowerCase().includes(descr.toLowerCase())
						|| item.longTitle.toLowerCase().includes(descr.toLowerCase());
				} catch (e) { }
			}
		}
		if (textMatch && (youtube === false || item.youtube.length > 0)) {
			item.locations.forEach(function(locItem, locIdx) {
				let hasTheme = false;
				if (themes != null && themes.length > 0) {
					themes.forEach(function(theme) {
						if (theme === '') {
							hasTheme = true;
						} else if (item.themes && item.themes.includes(theme)) {
							hasTheme = true;
						}
					});
				} else {
					hasTheme = true;
				}

				if (hasTheme) {
					console.log(item);
					if (locItem.lat) {
						matches++;
						var marker = L.marker([locItem.lat, locItem.lng]);
						let html = '<div class="title">' + item.code + ' '
							+ item.title + '</div><div class="location">'
							+ locItem.text + '</div><div class="description">'
							+ item.longTitle + "</div><div>"
							+ item.themes + "</div>";
						if (item.youtube.length > 0) {
							html += '<div><a href="https://www.youtube.com/watch?v=' + item.youtube + '" target="YOUTUBE">Watch on youtube</a></div>';
						}
						marker.bindPopup(html).openPopup();
						marker.addTo(map);
					}
				}
				
				$('#matches').text(matches + ' matches');
			});
		}
	});
}
