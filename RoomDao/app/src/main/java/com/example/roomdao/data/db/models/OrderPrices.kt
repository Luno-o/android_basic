package com.example.roomdao.data.db.models

import androidx.room.*

@Entity(tableName = OrderPricesContract.TABLE_NAME
    , foreignKeys = [ForeignKey(
    entity = Order::class,
    parentColumns = [OrderContract.Columns.ID,],
    childColumns = [OrderPricesContract.Columns.ORDER_ID]
),ForeignKey(
    entity = Product::class,
    parentColumns = [ProductContract.Columns.ID],
    childColumns = [OrderPricesContract.Columns.PRODUCT_ID]
)],
    indices = [Index(value = [OrderPricesContract.Columns.ORDER_ID,OrderPricesContract.Columns.PRODUCT_ID],
        unique = true)]
    , primaryKeys =[OrderPricesContract.Columns.ORDER_ID,OrderPricesContract.Columns.PRODUCT_ID]
)
data class OrderPrices(
    @ColumnInfo(name = OrderPricesContract.Columns.ORDER_ID)
    val orderId: Long,
    @ColumnInfo(name = OrderPricesContract.Columns.PRODUCT_ID)
    val productId: Long,
    @ColumnInfo(name = OrderPricesContract.Columns.COUNT)
    val count: Int
)

//@Entity
//data class Playlist(
//    @PrimaryKey val playlistId: Long,
//    val playlistName: String
//)
//
//@Entity
//data class Song(
//    @PrimaryKey val songId: Long,
//    val songName: String,
//    val artist: String
//)
//
//@Entity(primaryKeys = ["playlistId", "songId"])
//data class PlaylistSongCrossRef(
//    val playlistId: Long,
//    val songId: Long
//)
//data class PlaylistWithSongs(
//    @Embedded val playlist: Playlist,
//    @Relation(
//        parentColumn = "playlistId",
//        entityColumn = "songId",
//        associateBy = Junction(PlaylistSongCrossRef::class)
//    )
//    val songs: List<Song>
//)
//
//data class SongWithPlaylists(
//    @Embedded val song: Song,
//    @Relation(
//        parentColumn = "songId",
//        entityColumn = "playlistId",
//        associateBy = Junction(PlaylistSongCrossRef::class)
//    )
//    val playlists: List<Playlist>
//)