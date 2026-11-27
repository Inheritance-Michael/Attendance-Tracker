package com.example.attendance.data.service

import com.example.attendance.domain.service.PinHasher
import com.google.android.gms.common.util.Base64Utils.encode
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.inject.Inject

class Pbkdf2PinHasher @Inject constructor(): PinHasher {
    override fun hash(pin: String): String {
        val salt = ByteArray(16)
            .also { SecureRandom().nextBytes(it) }

        return "${encode(salt)}:${encode(derive(pin, salt))}"
    }

    override fun verify(pin: String, stored: String): Boolean {
        val parts = stored.split(":")
        if (parts.size != 2) return false
        val salt = Base64.getDecoder().decode(parts[0])
        val expected = Base64.getDecoder().decode(parts[1])
        return MessageDigest.isEqual(expected, derive(pin, salt))
    }
    private fun derive(pin: String, salt: ByteArray): ByteArray =
        SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
            .generateSecret(PBEKeySpec(pin.toCharArray(), salt, 100_000, 256))
            .encoded

    private fun encode(bytes: ByteArray): String = Base64.getEncoder().encodeToString(bytes)
}