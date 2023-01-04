import kotlinx.serialization.*

@Serializable
data class User(
    var id: Int,
    val firstname: String,
    val lastname: String,
    val email: String,
    var phone:String,
    var password: String
) {
    override fun toString(): String {
        return "User(id=$id, firstname='$firstname', lastname='$lastname', email='$email', phone=$phone, password='$password')"
    }
}