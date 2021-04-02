package com.wednesday.itunesapi.data

import java.io.Serializable

class Results {
    var wrapperType: String? = null
    var kind: String? = null
    var artistId = 0f
    var collectionId = 0f
    var trackId = 0f
    var artistName: String? = null
    var collectionName: String? = null
    var trackName: String? = null
    var collectionCensoredName: String? = null
    var trackCensoredName: String? = null
    var artistViewUrl: String? = null
    var collectionViewUrl: String? = null
    var trackViewUrl: String? = null
    var previewUrl: String? = null
    var artworkUrl30: String? = null
    var artworkUrl60: String? = null
    var artworkUrl100: String? = null
    var collectionPrice = 0f
    var trackPrice = 0f
    var releaseDate: String? = null
    var collectionExplicitness: String? = null
    var trackExplicitness: String? = null
    var discCount = 0f
    var discNumber = 0f
    var trackCount = 0f
    var trackNumber = 0f
    var trackTimeMillis = 0f
    var country: String? = null
    var currency: String? = null
    var primaryGenreName: String? = null
    var isStreamable = false
}