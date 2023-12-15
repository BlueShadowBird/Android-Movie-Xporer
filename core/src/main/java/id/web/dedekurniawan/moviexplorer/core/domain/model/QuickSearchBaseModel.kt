package id.web.dedekurniawan.moviexplorer.core.domain.model

abstract class QuickSearchBaseModel(val id: String, val name: String, val additionalInfo: String){
    abstract val iconId: Int

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as QuickSearchBaseModel

        if (id != other.id) return false
        if (name != other.name) return false
        if (additionalInfo != other.additionalInfo) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + additionalInfo.hashCode()
        return result
    }
}