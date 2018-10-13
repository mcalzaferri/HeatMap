# HeatMap
## Messages Client -> Server
* postIdRequest(GeoLocation) Data = "messageType=idRequest&geoLocation=latitude,longitude"
* postData(id, currentTemperature, timestamp) Data = "messageType=temperatureDataPost&id=AE3378ECF65B34D2C46E12BAC6CA1962C&timestamp=1539430885"

## Messages Server -> Client
* postRegisterResponse(id) Data = "messageType=idResponse&id=AE3378ECF65B34D2C46E12BAC6CA1962C"
* postDataResponse() Data = "messageType=dataPostResponse"
