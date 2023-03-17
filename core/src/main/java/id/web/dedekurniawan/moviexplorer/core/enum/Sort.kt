package id.web.dedekurniawan.moviexplorer.core.enum


interface Sort{

    enum class Popularity(private val string: String): Sort{
        ASCENDING("asc"),
        DESCENDING("desc");

        override fun toString() = "popularity.$string"
    }

    enum class Vote(private val string: String): Sort{
        ASCENDING("asc"),
        DESCENDING("desc");

        override fun toString() = "vote_average.$string"
    }

    enum class Revenue(private val string: String): Sort{
        ASCENDING("asc"),
        DESCENDING("desc");

        override fun toString() = "revenue.$string"
    }
}