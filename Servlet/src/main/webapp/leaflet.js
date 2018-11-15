var mymap = L.map('mapid').setView([51.505, -0.09], 3);

window.onload = function () {
    $.get("https://heatmap-219120.appspot.com/api/sensors", function(data, status){
        for(var i = 0; i < data.length; i++){
            var marker = L.marker([data[i].location.latitude, data[i].location.longitude]).addTo(mymap);
            marker.bindPopup();
            marker.id = data[i].id;
            marker.on('click', onMarkerClick );
            function onMarkerClick(e) {
                var popup = e.target.getPopup();
                popup.setContent("" +  e.target.id );
            }
        }
    });
}

L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
    maxZoom: 9,
    minZoom: 1,
    zoomDelta: 0.2,
    noWrap: true,
    bounds: [
        [-90, -180],
        [90, 180]
      ],
    maxBounds: [
        [-90, -180],
        [90, 180]
      ],
    attribution: 'Map data &copy; <a href="https://www.openstreetmap.org/">OpenStreetMap</a> contributors, ' +
        '<a href="https://creativecommons.org/licenses/by-sa/2.0/">CC-BY-SA</a>, ' +
        'Imagery � <a href="https://www.mapbox.com/">Mapbox</a>',
    id: 'mapbox.streets'
}).addTo(mymap);

mymap.on('resize', function(event){
    calculateMinZoom(event.newSize);
});

mymap.on('zoomend', function(){
    calculateMinZoom(mymap.getSize());
});

var moveEndTimeout;
var mouseDown = false;
mymap.on('moveend', function(){
    if(!mouseDown){
        clearTimeout(moveEndTimeout);
        moveEndTimeout = setTimeout(panToCenter,100);
    }
});
mymap.on('mousedown', function(){
    mouseDown = true;
    clearTimeout(moveEndTimeout);
});
mymap.on('mouseup', function(){
    mouseDown = false;
    clearTimeout(moveEndTimeout);
    moveEndTimeout = setTimeout(panToCenter,100);
})

function calculateMinZoom(newSize){
  //Only called when resizing the window
  var factor = Math.pow(2,0.2);
  var size = 256;
  var minZoomLevel = 0;
  while(size < newSize.x || size < newSize.y){
      size *= factor;
      minZoomLevel += 0.2;
  }
  mymap.setMinZoom(minZoomLevel);
}

function panToCenter(){
    mymap.panInsideBounds([
        [-90, -180],
        [90, 180]
      ], animate = true);
}