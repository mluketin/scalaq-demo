package models

import java.nio.charset.Charset
import java.util.zip.{CRC32, Checksum}


object HashGenerator {

  def getSha1(string: String): Array[Byte] = {
    val md = java.security.MessageDigest.getInstance("SHA-1")
    md.digest(string.getBytes("UTF-8"))
  }

  def getCrc32(string: String): Long = {
    val bytes = string.getBytes()
    val checksum: Checksum = new CRC32()
    checksum.update(bytes, 0, bytes.length)
    checksum.getValue()
  }
}
