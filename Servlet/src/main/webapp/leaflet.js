var mymap = L.map('mapid').setView([51.505, -0.09], 3);

window.onload = addMarkersToMap();

L.tileLayer('https://api.tiles.mapbox.com/v4/{id}/{z}/{x}/{y}.png?access_token=pk.eyJ1IjoibWFwYm94IiwiYSI6ImNpejY4NXVycTA2emYycXBndHRqcmZ3N3gifQ.rJcFIG214AriISLbB6B5aw', {
    maxZoom: 9,
    minZoom: 1,
    zoomDelta: 0.2,
    //noWrap: true,
    bounds: [
        [-90, -210],
        [90, 210]
      ],
    maxBounds: [
        [-90, -210],
        [90, 210]
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
        [-90, -210],
        [90, 210]
      ], animate = true);
}

function addMarkersToMap(){
    $.get("https://heatmap-219120.appspot.com/api/sensors", function(data, status){
        for(var i = 0; i < data.length; i++){
            var marker = L.marker([data[i].location.latitude, data[i].location.longitude]).addTo(mymap);
            marker.bindPopup();
            marker.id = data[i].id;
            marker.on('click', onMarkerClick );
            function onMarkerClick(e) {
                var popup = e.target.getPopup();
                var chartId = "chart" + e.target.id;
                popup.setContent(
                    '<canvas id="' + chartId + '" width="400" height="400"><p>ERROR: Could not load data!</p></canvas>'
                );
                drawChart(document.getElementById(chartId), e.target.id);
            }
        }
    });
}

function drawChart(ctx, sensorId){
    var url = "https://heatmap-219120.appspot.com/api/sensors/" + sensorId + "/measurements?orderBy=+timestamp";
    var measurements = [];
    $.get(url, function(data, status){
        for(var i = 0; i < data.length; i++){
            var point = {
                x : new Date(data[i].timestamp),
                y : data[i].temperature
            };
            
            measurements.push(point);
        }
        new Chart(ctx, {
            type: 'line',
            data: {
                datasets: [{
                    label:"Temperature",
                    data: measurements
                }]
            },
            options: {
                scales: {
                    xAxes: [{
                        type: 'time',
                        position: 'bottom'
                    }]
                }
            }
        });
    });
}