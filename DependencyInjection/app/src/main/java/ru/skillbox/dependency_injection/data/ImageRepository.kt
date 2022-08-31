package ru.skillbox.dependency_injection.data

interface ImageRepository {
    fun observeImages(onChange: () -> Unit)
    fun unregisterObserver()
    suspend fun getImages(): List<Image>
    suspend fun saveImage(name: String, url: String)
    suspend fun deleteImage(id: Long)
}