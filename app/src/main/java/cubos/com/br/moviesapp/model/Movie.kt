package cubos.com.br.moviesapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class Movie(
        @SerializedName("adult") val adult: Boolean,
        @SerializedName("backdrop_path") val backdropPath: String?,
        @SerializedName("genre_ids") val genreIds: ArrayList<Any>,
        @SerializedName("id") val id: Int,
        @SerializedName("original_language") val originalLanguage: String?,
        @SerializedName("original_title") val originalTitle: String?,
        @SerializedName("overview") val overview: String?,
        @SerializedName("popularity") val popularity: Double?,
        @SerializedName("poster_path") val posterPath: String?,
        @SerializedName("release_date") val releaseDate: String?,
        @SerializedName("title") val title: String?,
        @SerializedName("video") val video: Boolean?,
        @SerializedName("vote_average") val voteAverage: Double?,
        @SerializedName("vote_count") val voteCount: Int?
) : Parcelable{

    constructor(parcel: Parcel) : this(
            parcel.readInt() == 1,
            parcel.readString(),
            parcel.readArrayList(Int.javaClass.classLoader),
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readDouble(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt() == 1,
            parcel.readDouble(),
            parcel.readInt()
    )

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(if(adult) 1 else 0)
        dest?.writeString(backdropPath)
        dest?.writeList(genreIds)
        dest?.writeInt(id)
        dest?.writeString(originalLanguage)
        dest?.writeString(originalTitle)
        dest?.writeString(overview)
        dest?.writeDouble(popularity ?: 0.0)
        dest?.writeString(posterPath)
        dest?.writeString(releaseDate)
        dest?.writeString(title)
        dest?.writeInt(if(video ?: false) 1 else 0)
        dest?.writeDouble(voteAverage ?: 0.0)
        dest?.writeInt(voteCount ?: 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }

        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}