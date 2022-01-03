var map = null;

initMap();

function initMap() {
	map = L.map('map').setView([53, -0.29], 7);

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

	data.forEach(function(item, i) {
		item.locations.forEach(function(locItem, locIdx) {
			console.log(item);
			if (locItem.lat) {
				var marker = L.marker([locItem.lat, locItem.lng]);
				marker.bindPopup('<div class="title">' + item.code + ' ' + item.title + '</div><div class="location">' + locItem.text +'</div><div class="description">' + item.longTitle + "</div>").openPopup();
				marker.addTo(map);
			}
		});
	});
}
