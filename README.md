# HeatMap
## Messages Client -> Server
* requestId(GeoLocation)
* startDataTransfer(id)
* putData(currentTemperature)

## Messages Server -> Client
* assignId(id)
* startDataTransfer()
* endDataTransfer(reason)
