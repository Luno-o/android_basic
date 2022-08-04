package com.example.roomdao.data.db.models

//import androidx.room.Embedded
//import androidx.room.Relation

//data class CustomerWithOrders(
//    @Embedded
//    val customer: Customer,
//    @Relation(
//        parentColumn = CustomerContract.Columns.ID,
//        entityColumn = OrderContract.Columns.CUSTOMER_ID
//    )
//    val orders: List<Order>
//)
//data class UserAndLibrary(
//    @Embedded val user: User,
//    @Relation(
//        parentColumn = "userId",
//        entityColumn = "userOwnerId"
//    )
//    val library: Library
//)
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