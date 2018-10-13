# HeatMap
## Messages Client -> Server
* postIdRequest(GeoLocation) Data = "messageType=idRequest&geoLocation=latitude,longitude"
* postData(id, currentTemperature, timestamp) Data = "messageType=temperatureDataPost&id=AE33-78EC-F65B-C46E&timestamp=1539430885"

## Messages Server -> Client
* postRegisterResponse(id) Data = "messageType=idResponse&id=AE33 78ECF 65B3 4D2C 46E1 2BAC 6CA1 962C"
* postDataResponse() Data = "messageType=dataPostResponse"
