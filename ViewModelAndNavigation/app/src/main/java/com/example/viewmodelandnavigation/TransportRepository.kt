package com.example.viewmodelandnavigation

import java.text.FieldPosition

class TransportRepository {
fun getEmptyTransportList():List<Transport>{
    return emptyList()
}
    fun deleteTransport(transports: List<Transport>,position: Int): List<Transport>{
        return transports.filterIndexed { index, _ -> index != position }
    }
}