import Map from "ol/Map";
import OSM from "ol/source/OSM";
import View from "ol/View";
import Tile from "ol/layer/Tile";
import {fromLonLat} from "ol/proj";

var map = new Map({
  target: 'map',
  layers: [
    new Tile({
      source: new OSM()
    })
  ],
  view: new View({
    center: fromLonLat([2.41, -8.82]),
    zoom: 4
  })
});