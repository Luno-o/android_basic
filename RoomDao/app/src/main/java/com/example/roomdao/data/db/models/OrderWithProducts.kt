package com.example.roomdao.data.db.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class OrderWithProducts(
    @Embedded
    val order: Order,
    @Relation(
        parentColumn = OrderContract.Columns.ID,
        entityColumn = ProductContract.Columns.ID,
    associateBy = Junction(OrderAndProductsCrossRef::class)
    )
    val product: List<Product>
)
//data class PlaylistWithSongs(
//    @Embedded val playlist: Playlist,
//    @Relation(
//        parentColumn = "playlistId",
//        entityColumn = "songId",
//        associateBy = Junction(PlaylistSongCrossRef::class)
//    )
//    val songs: List<Song>
//)