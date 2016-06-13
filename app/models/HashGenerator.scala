package models


object HashGenerator {

  def getSha1(string: String): Array[Byte] = {
    val md = java.security.MessageDigest.getInstance("SHA-1")
    md.digest(string.getBytes("UTF-8"))
  }
}
