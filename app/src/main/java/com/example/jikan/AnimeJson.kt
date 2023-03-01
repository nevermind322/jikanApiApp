package com.example.jikan


import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class AnimeJson(
    @SerializedName("data")
    val data: Data
) {
    data class Data(
        @SerializedName("aired")
        val aired: Aired,
        @SerializedName("airing")
        val airing: Boolean,
        @SerializedName("approved")
        val approved: Boolean,
        @SerializedName("background")
        val background: String?,
        @SerializedName("broadcast")
        val broadcast: Broadcast,
        @SerializedName("demographics")
        val demographics: List<MalUrl>,
        @SerializedName("duration")
        val duration: String?,
        @SerializedName("episodes")
        val episodes: Int?,
        @SerializedName("explicit_genres")
        val explicitGenres: List<MalUrl>,
        @SerializedName("external")
        val `external`: List<External>,
        @SerializedName("favorites")
        val favorites: Int?,
        @SerializedName("genres")
        val genres: List<MalUrl>,
        @SerializedName("images")
        val images: Images,
        @SerializedName("licensors")
        val licensors: List<MalUrl>,
        @SerializedName("mal_id")
        val malId: Int,
        @SerializedName("members")
        val members: Int?,
        @SerializedName("popularity")
        val popularity: Int?,
        @SerializedName("producers")
        val producers: List<Producer>,
        @SerializedName("rank")
        val rank: Int?,
        @SerializedName("rating")
        val rating: String?,
        @SerializedName("relations")
        val relations: List<Relation>,
        @SerializedName("score")
        val score: Double?,
        @SerializedName("scored_by")
        val scoredBy: Int?,
        @SerializedName("season")
        val season: String?,
        @SerializedName("source")
        val source: String?,
        @SerializedName("status")
        val status: String?,
        @SerializedName("streaming")
        val streaming: List<Streaming>,
        @SerializedName("studios")
        val studios: List<MalUrl>,
        @SerializedName("synopsis")
        val synopsis: String?,
        @SerializedName("theme")
        val theme: Theme,
        @SerializedName("themes")
        val themes: List<MalUrl>,
        @SerializedName("title")
        val title: String,
        @SerializedName("title_english")
        val titleEnglish: String?,
        @SerializedName("title_japanese")
        val titleJapanese: String?,
        @SerializedName("title_synonyms")
        val titleSynonyms: List<Any>,
        @SerializedName("titles")
        val titles: List<Title>,
        @SerializedName("trailer")
        val trailer: Trailer,
        @SerializedName("type")
        val type: String?,
        @SerializedName("url")
        val url: String,
        @SerializedName("year")
        val year: Int?
    ) {
        data class Aired(
            @SerializedName("from")
            val from: String?,
            @SerializedName("prop")
            val prop: Prop,
            @SerializedName("string")
            val string: String,
            @SerializedName("to")
            val to: String?
        ) {
            data class Prop(
                @SerializedName("from")
                val from: From,
                @SerializedName("to")
                val to: To
            ) {
                data class From(
                    @SerializedName("day")
                    val day: Int?,
                    @SerializedName("month")
                    val month: Int?,
                    @SerializedName("year")
                    val year: Int?
                )

                data class To(
                    @SerializedName("day")
                    val day: Int?,
                    @SerializedName("month")
                    val month: Int?,
                    @SerializedName("year")
                    val year: Int?
                )
            }
        }

        data class Broadcast(
            @SerializedName("day")
            val day: String?,
            @SerializedName("string")
            val string: String?,
            @SerializedName("time")
            val time: String?,
            @SerializedName("timezone")
            val timezone: String?
        )

        data class External(
            @SerializedName("name")
            val name: String,
            @SerializedName("url")
            val url: String
        )

        data class MalUrl(
            @SerializedName("mal_id")
            val malId: Int,
            @SerializedName("name")
            val name: String,
            @SerializedName("type")
            val type: String,
            @SerializedName("url")
            val url: String
        )

        data class Images(
            @SerializedName("jpg")
            val jpg: Jpg,
            @SerializedName("webp")
            val webp: Webp
        ) {
            data class Jpg(
                @SerializedName("image_url")
                val imageUrl: String?,
                @SerializedName("large_image_url")
                val largeImageUrl: String?,
                @SerializedName("small_image_url")
                val smallImageUrl: String?
            )

            data class Webp(
                @SerializedName("image_url")
                val imageUrl: String?,
                @SerializedName("large_image_url")
                val largeImageUrl: String?,
                @SerializedName("small_image_url")
                val smallImageUrl: String?
            )
        }

        data class Producer(
            @SerializedName("mal_id")
            val malId: Int?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("type")
            val type: String?,
            @SerializedName("url")
            val url: String?
        )

        data class Relation(
            @SerializedName("entry")
            val entry: List<Entry>,
            @SerializedName("relation")
            val relation: String
        ) {
            data class Entry(
                @SerializedName("mal_id")
                val malId: Int,
                @SerializedName("name")
                val name: String,
                @SerializedName("type")
                val type: String,
                @SerializedName("url")
                val url: String
            )
        }

        data class Streaming(
            @SerializedName("name")
            val name: String,
            @SerializedName("url")
            val url: String
        )

        data class Theme(
            @SerializedName("endings")
            val endings: List<String>,
            @SerializedName("openings")
            val openings: List<String>
        )

        data class Title(
            @SerializedName("title")
            val title: String,
            @SerializedName("type")
            val type: String
        )

        data class Trailer(
            @SerializedName("embed_url")
            val embedUrl: String?,
            @SerializedName("images")
            val images: Images,
            @SerializedName("url")
            val url: String?,
            @SerializedName("youtube_id")
            val youtubeId: String?
        ) {
            data class Images(
                @SerializedName("image_url")
                val imageUrl: String,
                @SerializedName("large_image_url")
                val largeImageUrl: String,
                @SerializedName("maximum_image_url")
                val maximumImageUrl: String,
                @SerializedName("medium_image_url")
                val mediumImageUrl: String,
                @SerializedName("small_image_url")
                val smallImageUrl: String
            )
        }
    }
}