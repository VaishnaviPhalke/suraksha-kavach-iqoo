package com.suraksha.kavach

import org.junit.Assert.*
import org.junit.Test
import java.security.MessageDigest

/**
 * Unit tests validating cryptographic SHA-256 hash chaining
 * for the tamper-proof SQLite audit ledger.
 */
class AuditHashChainTest {

    private fun computeSha256Hash(prevHash: String, timestamp: String, eventType: String, detail: String): String {
        val input = "$prevHash|$timestamp|$eventType|$detail"
        val digest = MessageDigest.getInstance("SHA-256")
        return digest.digest(input.toByteArray(Charsets.UTF_8)).joinToString("") { "%02x".format(it) }
    }

    @Test
    fun testHashChain_genesisToSubsequentBlocks() {
        val genesisHash = "0000000000000000000000000000000000000000000000000000000000000000"

        // Block 1
        val hash1 = computeSha256Hash(genesisHash, "14:00:00", "AIR_GAP_CHECK", "Baseband isolated")
        assertNotNull(hash1)
        assertEquals(64, hash1.length)

        // Block 2
        val hash2 = computeSha256Hash(hash1, "14:05:00", "VAULT_ENCRYPT", "CAD blueprints encrypted")
        assertNotNull(hash2)
        assertEquals(64, hash2.length)
        assertNotEquals("Block 2 hash must differ from Block 1", hash1, hash2)

        // Block 3
        val hash3 = computeSha256Hash(hash2, "14:10:00", "GATE_VERIFY", "3-factor gate verified")
        assertNotNull(hash3)
        assertEquals(64, hash3.length)
    }

    @Test
    fun testHashChain_tamperDetection() {
        val genesisHash = "0000000000000000000000000000000000000000000000000000000000000000"
        val hash1 = computeSha256Hash(genesisHash, "14:00:00", "AIR_GAP_CHECK", "Baseband isolated")
        val hash2 = computeSha256Hash(hash1, "14:05:00", "VAULT_ENCRYPT", "CAD blueprints encrypted")

        // If an attacker alters Block 1's details
        val tamperedHash1 = computeSha256Hash(genesisHash, "14:00:00", "AIR_GAP_CHECK", "Tampered detail string!")

        // Then re-verifying Block 2 with tamperedHash1 will fail to match stored hash2
        val recomputedHash2 = computeSha256Hash(tamperedHash1, "14:05:00", "VAULT_ENCRYPT", "CAD blueprints encrypted")
        assertNotEquals("Tampering with previous block breaks subsequent block verification", hash2, recomputedHash2)
    }
}
