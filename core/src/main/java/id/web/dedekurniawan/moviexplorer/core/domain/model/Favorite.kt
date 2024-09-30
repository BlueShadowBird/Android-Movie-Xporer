package id.web.dedekurniawan.moviexplorer.core.domain.model

data class Favorite(
    val moduleName: String,
    val moduleItemId: Int,
    val searchKey: String? = null,
    val data: String? = null
)
