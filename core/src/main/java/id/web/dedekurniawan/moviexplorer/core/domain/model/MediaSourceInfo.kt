package id.web.dedekurniawan.moviexplorer.core.domain.model

import android.os.Parcel
import android.os.Parcelable

class MediaSourceInfo(
    val data: String,
    val source: String = MEDIA_SOURCE_IMAGE,
    val name: String? = null
    ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(data)
        parcel.writeString(source)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MediaSourceInfo> {
        override fun createFromParcel(parcel: Parcel): MediaSourceInfo {
            return MediaSourceInfo(parcel)
        }

        override fun newArray(size: Int): Array<MediaSourceInfo?> {
            return arrayOfNulls(size)
        }

        const val MEDIA_SOURCE_IMAGE = "image"
        const val MEDIA_SOURCE_VIDEO = "video"
        const val MEDIA_SOURCE_YOUTUBE = "youtube"
    }
}
